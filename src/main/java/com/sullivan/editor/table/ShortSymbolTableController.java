package com.sullivan.editor.table;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShortSymbolTableController {
	@FXML
	private TableView<TableToken> tblViewShortSymbol;
	@FXML
	private TableColumn<TableToken,Integer> tblColumnId;
	@FXML
	private TableColumn<TableToken, String> tblColumnSymbol;
	@FXML
	private TableColumn<TableToken, Integer> tblColumnAlias;

	private ObservableList<TableToken> tableTokens = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		tblColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tblColumnSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		tblColumnAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
		tblViewShortSymbol.setItems(tableTokens);
	}

	public ObservableList<TableToken> getTableTokens() {
		return tableTokens;
	}
	public void setTableTokens(List<TableToken> tableTokens) {
		this.tableTokens.addAll(tableTokens);
	}
}
