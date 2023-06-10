package application;

import java.io.File;

import database.FileHandler;
import database.Store;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import models.Customer;
import models.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;

public class SignupView {

    public Scene signUpScene;

    public Scene signUpScene() {

        Image image = new Image(new File("loginSignup.gif").toURI().toString());
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(450);
        imageview.setFitWidth(800);

        Label nameLabel = new Label("Name ");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        nameLabel.setTranslateY(-30);
        nameLabel.setTranslateX(300);

        TextField tfname = new TextField();
		tfname.setPromptText("John Doe");
        tfname.setMaxSize(200, 20);
        tfname.setTranslateY(-25);
        tfname.setTranslateX(300);

        
        Label ageLabel = new Label("Age ");
        ageLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        ageLabel.setTranslateX(300);
        ageLabel.setTranslateY(-20);

        TextField tfage = new TextField();
		tfage.setPromptText("22");
        tfage.setMaxSize(200, 20);
        tfage.setTranslateY(-15);
        tfage.setTranslateX(300);

        Label genderLabel = new Label("Gender ");
        genderLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        genderLabel.setTranslateX(300);
        genderLabel.setTranslateY(-10);

        TextField tfgender = new TextField();
		tfgender.setPromptText("Male / Female / Other");
        tfgender.setMaxSize(200, 20);
        tfgender.setTranslateY(-5);
        tfgender.setTranslateX(300);

        Label emailLabel = new Label("Email ");
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        emailLabel.setTranslateX(300);
        emailLabel.setTranslateY(0);

        TextField tfemail = new TextField();
		tfemail.setPromptText("example@email.com");
        tfemail.setMaxSize(200, 20);
        tfemail.setTranslateY(5);
        tfemail.setTranslateX(300);

        Label passwordLabel = new Label("Password ");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        passwordLabel.setTranslateX(300);
        passwordLabel.setTranslateY(10);

        PasswordField pfpassword = new PasswordField();
        pfpassword.setMaxSize(200, 20);
        pfpassword.setTranslateY(15);
        pfpassword.setTranslateX(300);

        Button signUpbt = new Button("Sign Up");
        signUpbt.setPrefSize(200, 20);
        signUpbt.setTranslateY(25);
        signUpbt.setTranslateX(300);

        Button loginbt = new Button("Login");
        loginbt.setPrefSize(200, 20);
        loginbt.setTranslateY(30);
        loginbt.setTranslateX(300);

        Group root = new Group(imageview);

        Rectangle rec = new Rectangle(300,400);
        rec.setFill(Color.BLACK);
        rec.setOpacity(0.5);
        rec.setArcHeight(50);
        rec.setArcWidth(50);

        VBox rpane = new VBox();
        rpane.getChildren().add(rec);
        rpane.setAlignment(Pos.CENTER);

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().addAll(nameLabel, tfname, ageLabel, tfage, genderLabel, tfgender, emailLabel, tfemail, passwordLabel, pfpassword, signUpbt, loginbt);

        StackPane spane = new StackPane(root,rpane,pane);

        signUpScene = new Scene(spane, 800, 450);

		ViewLogin viewLogin = new ViewLogin();
        signUpbt.setOnAction(e ->{
			for (User user : Store.getAllUsers()){
				if (user.getEmail().equals(tfemail.getText())){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Email already exists");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a different email");
				alert.showAndWait();
				}
			}
			if(tfname.getText().isEmpty() || tfage.getText().isEmpty() || tfgender.getText().isEmpty() || tfemail.getText().isEmpty() || pfpassword.getText().isEmpty()){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please fill in all fields");
				alert.showAndWait();
			}
			else if(!tfemail.getText().contains("@")){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid email");
				alert.setContentText("Please enter a valid email in the form \"example@email.com\"");
				alert.showAndWait();
			}
			else{
				Customer customer = new Customer(tfname.getText(), Integer.parseInt(tfage.getText()), tfgender.getText(), tfemail.getText(), pfpassword.getText());
				Store.getAllUsers().add(customer);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Sign up");
				alert.setHeaderText(null);
				alert.setContentText("You have successfully signed up");
				alert.showAndWait();
				Main.switchScenes(viewLogin.loginScene());
				FileHandler.saveUsersToFile();
			}
		});

		loginbt.setOnAction(e -> {
			Main.switchScenes(viewLogin.loginScene());
		});
        return signUpScene;
    }

}
