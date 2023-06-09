package application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import models.Admin;
import models.Product;
import models.User;
import javafx.scene.shape.*;
import java.io.File;
import java.util.Optional;
import database.Store;

public class AdminView {

    public Scene adminScene;
    private TextField tfsearch;
	
    public Scene adminScene(User loginUser) {
		Admin admin = (Admin) loginUser;
		VBox layout = new VBox();
		tfsearch = new TextField();
		tfsearch.setPrefSize(430, 0);
		tfsearch.setAlignment(Pos.BOTTOM_LEFT);
		tfsearch.setOpacity(0.5);
		tfsearch.setPromptText("Search");
		tfsearch.setStyle("-fx-font-weight: bold;");
		
		Image image = new Image(new File("GalacticStoreBG.gif").toURI().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(450);
		imageview.setFitWidth(800);
		Group root = new Group(imageview);
	
		ListView<Product> productListView = new ListView<>(Store.getAllProducts());
		productListView.setStyle("-fx-font-weight: bold;");
		// productListView.getItems().addAll(Store.getAllProducts());
		productListView.setOpacity(0.5);

		//admin logout button 
		Button logoutButton = new Button("Admin");
		Tooltip logout = new Tooltip();
		logout.setShowDelay(new javafx.util.Duration(1));
		logout.setText("Log out");
		logoutButton.setTooltip(logout);
		logoutButton.setTranslateX(-50);
		logoutButton.setTranslateY(10);
		logoutButton.setOpacity(0);
		
		logoutButton.setOnAction(e -> {
			Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
			confirm.setTitle("Logout");
			confirm.setHeaderText(null);
			confirm.setContentText("Are you sure you want to logout?");
		
			Optional<ButtonType> result = confirm.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				Main.switchScenes(new ViewLogin().loginScene());
			}
		});
		
		Image adminImage = new Image(new File("logout.gif").toURI().toString());
		ImageView adminImageView = new ImageView(adminImage);
		adminImageView.setFitHeight(30);
		adminImageView.setFitWidth(30);
		adminImageView.setTranslateX(-365);
		adminImageView.setTranslateY(200);
		
		Button okButton = new Button("OK");
		okButton.setPrefSize(100, 20);

		HBox searchBox = new HBox(tfsearch, okButton);
		searchBox.setAlignment(Pos.BOTTOM_CENTER);
		searchBox.setSpacing(10);
		searchBox.setVisible(false);
		
		//for adding item page

		Label nameLabel = new Label("Name");
		nameLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
		
		TextField tfname = new TextField();
		tfname.setMaxSize(200, 20);
		

		Label priceLabel = new Label("Price");
		priceLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
		
		TextField tfprice = new TextField();
		tfprice.setMaxSize(200, 20);
		

		Label stockLabel = new Label("Stock");
		stockLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
		
		TextField tfstock = new TextField();
		tfstock.setMaxSize(200, 20);
		

		Label galaxyLabel = new Label("Galaxy");
		galaxyLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
		
		TextField tfgalaxy = new TextField();
		tfgalaxy.setMaxSize(200, 20);
		
		
		Label planetLabel = new Label("Planet");
		planetLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
		
		TextField tfplanet = new TextField();
		tfplanet.setMaxSize(200, 20);
		

		Label conditionLabel = new Label("Condition");
		conditionLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
		
		//text field and labels 
		
		TextField tfcondition = new TextField();
		tfcondition.setMaxSize(200, 20);
		
		Button addItemokbt = new Button("OK");
		Button goBackbt = new Button("Go back");
		addItemokbt.setPrefSize(200, 20);
		addItemokbt.setTranslateY(15);
		goBackbt.setPrefSize(200, 20);
		goBackbt.setTranslateY(15);
		
		
		VBox addItemVbox = new VBox();
		addItemVbox.getChildren().addAll(nameLabel, tfname, priceLabel, tfprice, stockLabel, tfstock, galaxyLabel, tfgalaxy, planetLabel, tfplanet, conditionLabel, tfcondition, addItemokbt,goBackbt);
		addItemVbox.setAlignment(Pos.CENTER_LEFT);
		//updated
		addItemVbox.setTranslateX(300);
		addItemVbox.setTranslateY(-15);
		
		//new
		Rectangle shadowBox = new Rectangle(800,450);
		shadowBox.setFill(Color.BLACK);
		shadowBox.setOpacity(0.7);
		Rectangle topBox = new Rectangle(300,350);
		topBox.setArcHeight(50);
		topBox.setArcWidth(50);
		topBox.setFill(Color.WHITESMOKE);
		topBox.setOpacity(0.8);
		
		
		StackPane addItemPane = new StackPane();
		addItemPane.getChildren().addAll(shadowBox,topBox, addItemVbox);
		addItemPane.setVisible(false);
		
		
		//view all page
		StackPane spane = new StackPane();
		spane.getChildren().addAll(root,adminImageView , layout,addItemPane);
		
		Button addButton = new Button("Add Item");
		addButton.setPrefSize(200, 20);
		addButton.setOnAction(e -> {
			searchBox.setVisible(false);
			addItemPane.setVisible(true);
			// Implementation here
			
			//okbt action 
			addItemokbt.setOnAction(eAddItem -> {
				if(tfname.getText().isEmpty() || tfprice.getText().isEmpty() || tfstock.getText().isEmpty() || tfgalaxy.getText().isEmpty() || tfplanet.getText().isEmpty() || tfcondition.getText().isEmpty()){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Empty Fields");
					alert.setHeaderText(null);
					alert.setContentText("Please fill in all fields");
					alert.showAndWait();
					return;
				}
				try {
					String name = tfname.getText();
					double price = Double.parseDouble(tfprice.getText());
					int stock = Integer.parseInt(tfstock.getText());
					String galaxy = tfgalaxy.getText();
					String planet = tfplanet.getText();
					String condition = tfcondition.getText();
					admin.addProduct(name, price, stock, galaxy, planet, condition);
					productListView.refresh();
					addItemPane.setVisible(false);
				} catch (NumberFormatException nfe) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Input Error");
					alert.setHeaderText(null);
					alert.setContentText("Please ensure you've entered the correct values in the fields.");
					alert.showAndWait();
				}
			});

			//gobakcbt action
			goBackbt.setOnAction(eGoBack -> {
				addItemPane.setVisible(false);
			});
		});


		
		Button removeButton = new Button("Remove Item");
		removeButton.setPrefSize(200, 20);
		removeButton.setOnAction(e -> {
			searchBox.setVisible(true);
			// Add a validation here to check if the item exists
			okButton.setOnAction(oke -> {
				if(tfsearch.getText().isEmpty()){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Empty Fields");
					alert.setHeaderText(null);
					alert.setContentText("Please fill in all fields");
					alert.showAndWait();
					return;
				}
				String searchInput = tfsearch.getText();
				admin.removeProduct(searchInput);
				productListView.refresh();
				searchBox.setVisible(false);
			});
			//spane.getChildren().addAll(searchBox);
		});
		
		Button updateButton = new Button("Update Item");
		updateButton.setPrefSize(200, 20);
		updateButton.setOnAction(e -> {
			searchBox.setVisible(true);
			// Implementation here
			okButton.setOnAction(oke -> {
				String searchInput = tfsearch.getText();
				// find product
				Product product = Store.searchItem(searchInput);

				// if search empty then show alert
				if (searchInput == null || searchInput.trim().isEmpty()) {
					Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
					emptyAlert.setTitle("Empty Fields");
					emptyAlert.setHeaderText(null);
					emptyAlert.setContentText("Please enter the name of the item to be removed.");
					emptyAlert.showAndWait();
					return;
				}
				// else if product not found in store then show alert
				if (product == null) {
					Alert productAlert = new Alert(Alert.AlertType.INFORMATION);
					productAlert.setTitle("Invalid Product Name");
					productAlert.setHeaderText(null);
					productAlert.setContentText("Product does not exist. Enter a valid name.");
					productAlert.showAndWait();
				}
				// else details of product to update
				else {
					// System.out.println(product.getName() + " found");
					addItemPane.setVisible(true);
					tfname.setPromptText(product.getName());
					tfprice.setPromptText(String.valueOf(product.getPrice()));
					tfstock.setPromptText(String.valueOf(product.getStock()));
					tfgalaxy.setPromptText(product.getGalaxy());
					tfplanet.setPromptText(product.getPlanet());
					tfcondition.setPromptText(product.getCondition());

					tfname.setText(null);
					// fix
					tfprice.setText(null);
					tfstock.setText(null);
					tfgalaxy.setText(null);
					tfplanet.setText(null);
					tfcondition.setText(null);

					addItemokbt.setOnAction(eupdate -> {
						admin.updateProduct(product, tfname, tfprice, tfstock, tfgalaxy, tfplanet, tfcondition);
						productListView.refresh();
						addItemPane.setVisible(false);
					});
					
				}
			});

			goBackbt.setOnAction(eGoBack -> {
				System.out.println("Go back clicked");
				addItemPane.setVisible(false);
			});
		});
		// Create an HBox for the buttons
		HBox buttonLayout = new HBox();
		buttonLayout.getChildren().addAll(logoutButton, addButton, removeButton, updateButton);
		buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout.setTranslateX(-20);

		
		layout.getChildren().addAll(productListView, buttonLayout, searchBox);
		adminScene = new Scene(spane, 800, 450);
	
		return adminScene;
	}
    
}