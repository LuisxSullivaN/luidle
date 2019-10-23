package com.sullivan.editor.table;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DynamicTableController {
	@FXML
	private TableView<DynamicToken> tblViewDynamic;
	@FXML
	private TableColumn<DynamicToken,Integer> tblColumnId;
	@FXML
	private TableColumn<DynamicToken, List<Integer>> tblColumnLine;
	@FXML
	private TableColumn<DynamicToken, String> tblColumnSymbol;
	@FXML
	private TableColumn<DynamicToken, Integer> tblColumnCategory;
	
	private ObservableList<DynamicToken> dynamicTokens = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		tblColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tblColumnLine.setCellValueFactory(new PropertyValueFactory<>("lines"));
		tblColumnSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		tblColumnCategory.setCellValueFactory(new PropertyValueFactory<>("id"));
		tblViewDynamic.setItems(dynamicTokens);
	}
	
	public ObservableList<DynamicToken> getDynamicTokens() {
		return dynamicTokens;
	}
}
