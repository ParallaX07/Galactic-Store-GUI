package application;

import database.FileHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {

	private static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		try {

			ViewLogin viewLogin = new ViewLogin();

			primaryStage.setScene(viewLogin.loginScene());
			primaryStage.setTitle("Intergalactic Marketplace");
			primaryStage.show();
			stage = primaryStage;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void switchScenes(Scene scene) {
		stage.setScene(scene);
	}

	public static void main(String[] args) {
		FileHandler.loadUsersFromFile();
        FileHandler.loadProductFromFile();
       	Session.getSession();
        FileHandler.saveUsersToFile();
        FileHandler.saveProductsToFile();
		launch(args);
	}
}
