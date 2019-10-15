import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        VBox box = new VBox();
        stage.setScene(new Scene(box,500,500));
        stage.show();
    }
}
