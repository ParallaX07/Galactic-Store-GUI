package application;

import java.io.File;

import database.Store;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import models.Admin;
import models.Customer;
import models.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.animation.*;
import javafx.util.Duration;

public class ViewLogin {
	
	Scene loginScene;
	
	public Scene loginScene() {
 
		Image image = new Image(new File("loginSignup.gif").toURI().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(450);
		imageview.setFitWidth(800);
		
		//welcome image
		Image welcomeImage = new Image(new File("welcome.png").toURI().toString());
		ImageView welcomeImageview = new ImageView(welcomeImage);
		Group welcomeRoot = new Group(welcomeImageview);
		welcomeRoot.setTranslateY(-100);
		welcomeImageview.setFitHeight(190);
		welcomeImageview.setFitWidth(300);
		
		Image welcomeGlow = new Image(new File("welcomeGlow.png").toURI().toString());
		ImageView welcomeGlowView = new ImageView(welcomeGlow);
		Group welcomeGlowRoot = new Group(welcomeGlowView);
		welcomeGlowRoot.setTranslateY(-100);
		welcomeGlowView.setFitHeight(190);
		welcomeGlowView.setFitWidth(300);
		
		FadeTransition ft = new FadeTransition(Duration.millis(1500), welcomeGlowRoot);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(Timeline.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();
		
		StackPane welcomeSpane = new StackPane(welcomeGlowRoot, welcomeRoot);
		
		Label emailLabel = new Label("Email ");
		emailLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		emailLabel.setTranslateX(-84);
		
		TextField tfemail = new TextField();
		tfemail.setMaxSize(200, 20);
		tfemail.setTranslateY(3);
		//remove
		tfemail.setText(tfemail.getText());
		
		Label passwordLabel = new Label("Password ");
		passwordLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		passwordLabel.setTranslateX(-72);
		passwordLabel.setTranslateY(15);
	
		PasswordField pfpassword = new PasswordField();
		pfpassword.setMaxSize(200, 20);
		pfpassword.setTranslateY(18);
		//remove
		pfpassword.setText(pfpassword.getText());
		
		Button loginbt = new Button("Login");
		loginbt.setPrefSize(200, 20);
		loginbt.setOpacity(1);
		loginbt.setTranslateY(40);
		loginbt.setShape(new Rectangle(1,1));
		//loginbt.setStyle("-fx-background-color: white");
		//loginbt.setTextFill();
		
		Button signupbt = new Button("Signup");
		signupbt.setPrefSize(200, 20);
		signupbt.setTranslateY(45);
		signupbt.setShape(new Rectangle(1,1));
		
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
		pane.setAlignment(Pos.CENTER);
		pane.setTranslateY(25);
		pane.getChildren().addAll(emailLabel,tfemail, passwordLabel,pfpassword,loginbt,signupbt);
		
		StackPane spane = new StackPane(root,rpane,welcomeSpane,pane);
		
        loginScene = new Scene(spane, 800, 450);
        
        SignupView signupview = new SignupView();
        signupbt.setOnAction(e -> Main.switchScenes(signupview.signUpScene()));
        
        loginbt.setOnAction(e -> {
        	
            String email = tfemail.getText();
            String password = pfpassword.getText();
			if (email.isEmpty() || password.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Empty Fields");
				alert.setHeaderText(null);
				alert.setContentText("Please fill in all fields");
				alert.showAndWait();
				return;
			}
            try {
            	AdminView adminview = new AdminView();
            	CustomerView customerview = new CustomerView();
                User loginUser = Store.login(email, password);
                
                if(loginUser instanceof Admin) {
                	Main.switchScenes(adminview.adminScene(loginUser));
                	
                }
                if (loginUser instanceof Customer) {
                	Main.switchScenes(customerview.customerScene(loginUser));
                }
                
            } catch (Exception ex) {
                // Show an error dialog instead of printing to the console
                Alert alert = new Alert(AlertType.ERROR, "User not found");
                alert.showAndWait();
            }
        });
        
        return loginScene;

    }
	
}

