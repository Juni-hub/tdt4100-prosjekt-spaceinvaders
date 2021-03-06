package spaceInvaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppFX extends Application {
	private static Stage primaryStage; // **Declare static Stage**

    private void setPrimaryStage(Stage stage) {
        AppFX.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return AppFX.primaryStage;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception{
		setPrimaryStage(primaryStage);
		Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
