package MoviesDaoGui.MovieObjects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MovieGUIClient extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("movie-gui.fxml"));
        Scene scene = new Scene(root, 900, 545);
        primaryStage.setTitle("Movie Database Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}