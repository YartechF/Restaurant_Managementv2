package restaurant;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Parent parent;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.parent = FXMLLoader.load(getClass().getResource("/restaurant/views/auth_view.fxml"));
        this.scene = new Scene(parent);

        this.scene.getStylesheets().add(getClass().getResource("/restaurant/views/style.css").toExternalForm());
        primaryStage.setTitle("Restaurant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}