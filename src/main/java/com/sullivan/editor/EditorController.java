package com.sullivan.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.fxmisc.richtext.CodeArea;

import com.sullivan.editor.table.LongSymbolTableController;
import com.sullivan.editor.table.ShortSymbolTableController;
import com.sullivan.editor.table.TableToken;
import com.sullivan.lexer.Category;
import com.sullivan.lexer.Lexer;
import com.sullivan.lexer.LexerCup;
import com.sullivan.lexer.Token;
import com.sullivan.parser.parser;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditorController {
    @FXML
    private CodeArea codeArea;
    @FXML
    private TextArea console;

    private FileChooser fileChooser;
    private File file;
    private String code;
    private EditorModel model;
    private final String EDITOR_NAME = "Luidle";
    
    private List<Token> tokens = new ArrayList<>();

    @FXML
    private void initialize() {
    	fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("PRG","*.prg"));
        file = null;
        code = "";
        model = new EditorModel();
    }
    
    @FXML
    private void newFile() {
    	boolean ok = lookForChanges();
    	if (ok) {
    		codeArea.clear();
    		codeArea.setVisible(true);
    		code = "";
    		changeTitle(EDITOR_NAME);
    	}
    }

    @FXML
    private void openFile() {
    	boolean ok = lookForChanges();
    	if (ok) {
    		file = fileChooser.showOpenDialog(retrieveStage());
    		if (file != null) {
    			String content = model.readFromFile(file);
    			codeArea.replaceText(content);
    			codeArea.setVisible(true);
    			code = codeArea.getText();
    			changeTitle(EDITOR_NAME.concat(" - ").concat(file.getName()));
    		}
    	}
    }

    @FXML
    private void closeFile() {
    	boolean ok = lookForChanges();
    	if (ok) {
    		codeArea.clear();
    		codeArea.setVisible(false);
    		file = null;
    		code = "";
    		changeTitle(EDITOR_NAME);
    	}
    }
    
    @FXML
    private void closeEditor() {
    	boolean ok = lookForChanges();
    	if (ok) {
    		Platform.exit();
    	}
    }

    private boolean lookForChanges() {
    	boolean keepGoing = true;
    	//If text hasn't changed, there's no need to save and it just proceeds
    	if (!code.equals(codeArea.getText())) {
    		//If Yes or No, the operation keeps going, if CANCEL, nothing is done
    		ButtonType response = askForSave();
    		if (response == ButtonType.YES)
    			saveFile();
    		else if (response == ButtonType.CANCEL)
    			keepGoing = false;
    	}
    	return keepGoing;
    }

    private ButtonType askForSave() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(codeArea.getScene().getWindow());
    	alert.setTitle("Guardar cambios");
    	alert.setHeaderText("¿Desea guardar los cambios?");
    	alert.getButtonTypes().clear();
    	alert.getButtonTypes().addAll(
    			ButtonType.NO,ButtonType.YES,ButtonType.CANCEL);
    	Optional<ButtonType> response = alert.showAndWait();
    	return response.get();
    }

    @FXML
    private void saveFileAs() {
    	file = fileChooser.showSaveDialog(retrieveStage());
    	if (file != null) {
    		model.writeToFile(file, codeArea.getText());
    		changeTitle(EDITOR_NAME.concat(" - ").concat(file.getName()));
    		code = codeArea.getText();
    	}
    }

    @FXML
    private void saveFile() {
    	if (file != null) {
    		model.writeToFile(file, codeArea.getText());
    		code = codeArea.getText();
    	} else
    		saveFileAs();
    }

    private Stage retrieveStage() {
    	return (Stage)codeArea.getScene().getWindow();
    }
    
    private void changeTitle(String title) {
    	Stage stage = (Stage)codeArea.getScene().getWindow();
    	stage.setTitle(title);
    }
    
    @FXML
    private void doLexicalAnalysis() {
    	if (codeArea.isVisible()) {
    		code = codeArea.getText();
    		Lexer lexer = new Lexer(new BufferedReader(new StringReader(code)));
    		tokens = model.retrieveTokens(lexer);
    		console.clear();
    		for (Token error : lexer.getErrors()) {
    			console.appendText("Símbolo " + error.getLexeme()
    					+ " desconocido en la línea " + error.getLine() + "\n");
    		}
    	}
    }
    
    @FXML
    private void doSyntaxAnalysis() {
    	if (codeArea.isVisible()) {
    		code = codeArea.getText();
    		console.clear();
    		LexerCup scanner = new LexerCup(new StringReader(code));
    		parser parser = new parser(scanner);
    		try {
    			parser.parse();
    			console.setText("Análisis sintáctico finalizado con éxito");
    		} catch(Exception e) {
    			e.printStackTrace();
    			console.setText("Error en el análisis sintáctico");
    		}
    	}
    }
    
    @FXML
    private void showSymbolsTable() {
    	List<TableToken> tableTokens = model.toTableTokens(tokens);
    	LongSymbolTableController controller = loadLongSymbolTable();
    	controller.setTableTokens(tableTokens);
    }
    
    @FXML
    private void showIdentifiersTable() {
    	List<TableToken> tableTokens = model.toTableTokens(tokens);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tabla de identificadores");
    	controller.setTableTokens(model.filterTableTokens(tableTokens, Category.IDENTIFIER));
    }

    @FXML
    private void showIntegersTable() {
    	List<TableToken> tableTokens = model.toTableTokens(tokens);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tabla de constantes numéricas");
    	controller.setTableTokens(model.filterTableTokens(tableTokens, Category.INTEGER));
    }

    @FXML
    private void showRealsTable() {
    	List<TableToken> tableTokens = model.toTableTokens(tokens);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tabla de constantes reales");
    	controller.setTableTokens(model.filterTableTokens(tableTokens, Category.REAL));
    }

    @FXML
    private void showCharactersTable() {
    	List<TableToken> tableTokens = model.toTableTokens(tokens);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tabla de caracteres");
    	controller.setTableTokens(model.filterTableTokens(tableTokens, Category.CHARACTER));
    }
    
    @FXML
    private void showStringsTable() {
    	List<TableToken> tableTokens = model.toTableTokens(tokens);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tabla de cadenas de caracteres");
    	controller.setTableTokens(model.filterTableTokens(tableTokens, Category.STRING));
    }
    
    @FXML
    private void showKeywordsTable() {
    	List<TableToken> tableTokens = model.staticTokens(Category.KEYWORD);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tablde de palabras reservadas");
    	controller.setTableTokens(tableTokens);
    }

    @FXML
    private void showSpecialCharsTable() {
    	List<TableToken> tableTokens = model.staticTokens(Category.SPECIAL_CHAR);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tablde de caracteres especiales");
    	controller.setTableTokens(tableTokens);
    }

    @FXML
    private void showAritOpTable() {
    	List<TableToken> tableTokens = model.staticTokens(Category.ARIT_OP);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tablde de operadores aritméticos");
    	controller.setTableTokens(tableTokens);
    }

    @FXML
    private void showRelOpTable() {
    	List<TableToken> tableTokens = model.staticTokens(Category.REL_OP);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tablde de operadores relacionales");
    	controller.setTableTokens(tableTokens);
    }

    @FXML
    private void showLogOpTable() {
    	List<TableToken> tableTokens = model.staticTokens(Category.LOG_OP);
    	ShortSymbolTableController controller = loadShortSymbolTable("Tablde de operadores lógicos");
    	controller.setTableTokens(tableTokens);
    }

    private ShortSymbolTableController loadShortSymbolTable(String title) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShortSymbolTable.fxml"));
    	try {
    		Parent parent = loader.load();
    		Stage stage = new Stage();
    		stage.setTitle(title);
    		stage.setScene(new Scene(parent));
    		stage.initOwner(codeArea.getScene().getWindow());
    		stage.show();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	return (ShortSymbolTableController)loader.getController();
    }

    private LongSymbolTableController loadLongSymbolTable() {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LongSymbolTable.fxml"));
    	try {
    		Parent parent = loader.load();
    		Stage stage = new Stage();
    		stage.setTitle("Tabla de símbolos");
    		stage.setScene(new Scene(parent));
    		stage.initOwner(codeArea.getScene().getWindow());
    		stage.show();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	return (LongSymbolTableController)loader.getController();
    }
}
