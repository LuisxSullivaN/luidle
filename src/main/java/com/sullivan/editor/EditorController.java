package com.sullivan.editor;

import java.io.File;
import java.util.Optional;

import org.fxmisc.richtext.CodeArea;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditorController {
    @FXML
    private CodeArea codeArea;

    private FileChooser fileChooser;
    private File file;
    private String code;
    private EditorModel model;
    private final String EDITOR_NAME = "Luidle";

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
    	if (!code.equals(codeArea.getText())) {
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
}
