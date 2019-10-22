package com.sullivan;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Editor.fxml"));
		stage.setScene(new Scene(parent));
		stage.setTitle("Luidle");
		stage.show();
	}
}
