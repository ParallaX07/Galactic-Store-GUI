package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import database.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.CartProduct;
import models.Customer;
import models.Product;
import models.User;

public class CustomerView {
	
public Scene customerScene;
private TextField tfsearch;
private ListView<String> cartListView = new ListView<>();
private ListView<String> orderHistoryListView = new ListView<>();
	
	public Scene customerScene(User loginUser) {
		Customer customer = (Customer) loginUser;
		VBox layout = new VBox();
		tfsearch = new TextField();
		tfsearch.setPrefSize(430, 0);
		tfsearch.setAlignment(Pos.BOTTOM_LEFT);
		tfsearch.setOpacity(0.5);
		tfsearch.setPromptText("Search");
		tfsearch.setStyle("-fx-font-weight: bold;");
		
		//oka button next to search
		Button okButton = new Button("OK");
		okButton.setPrefSize(100, 20);
		
		HBox searchBox = new HBox(tfsearch, okButton);
		searchBox.setAlignment(Pos.BOTTOM_CENTER);
		searchBox.setSpacing(10);
		searchBox.setVisible(false);
		
		//buttons for layout
		Button addToCartbt = new Button("Add to Cart");
		addToCartbt.setPrefSize(150, 0);
		Button viewCartbt = new Button("View Cart");
		viewCartbt.setPrefSize(150, 0);
		Button viewOrderHistorybt = new Button("View Order History");
		viewOrderHistorybt.setPrefSize(150, 0);
		
		
		Image image = new Image(new File("GalacticStoreBG.gif").toURI().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(450);
		imageview.setFitWidth(800);
		Group root = new Group(imageview);
	
		ListView<Product> productListView = new ListView<>(Store.getAllProducts());
		productListView.setStyle("-fx-font-weight: bold;");
		productListView.setOpacity(0.5);
		
		//customer logout button 
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
		
		//customer logout button profile image
		Image userImage = new Image(new File("logout1.gif").toURI().toString());
		ImageView userImageView = new ImageView(userImage);
		userImageView.setFitHeight(30);
		userImageView.setFitWidth(30);
		userImageView.setTranslateX(-365);
		userImageView.setTranslateY(200);
		
		//black layer on screen used in add cart, update cart and view cart page
		Rectangle blackBox = new Rectangle(800, 450);
		blackBox.setOpacity(0.5);
		
		Rectangle blackBox2 = new Rectangle(800, 450);
		blackBox2.setOpacity(0.5);
		
		//add to cart and update cart page that will ask user for amount
		Rectangle amountRec = new Rectangle(600, 200);
		amountRec.setFill(Color.LIGHTGRAY);
		amountRec.setArcHeight(50);
		amountRec.setArcWidth(50);
		
		//Text field to ask for amount 
		TextField tfamount = new TextField();
		tfamount.setMinWidth(400);
		tfamount.setOpacity(0.5);
		tfamount.setPromptText("Enter amount");
		tfamount.setStyle("-fx-font-weight: bold;");
		//tfamount.setTranslateX(-50);
		
		//Label for product details 
		//Label amountLabel = new Label();
		
		//okay button next to amount textfield 
		Button amountOkbt = new Button("OK");
		amountOkbt.setPrefWidth(100);
		
		//Hbox to hold textfield and button
		HBox amountBox = new HBox(tfamount, amountOkbt);
		amountBox.setAlignment(Pos.CENTER);

		//Stack pane for add to cart and update cart page
		StackPane addUpdateSpane = new StackPane(blackBox2, amountRec, amountBox);
		addUpdateSpane.setVisible(false);
		
		//view cart page
		Rectangle viewCartRec = new Rectangle(600,350);
		viewCartRec.setFill(Color.LIGHTGRAY);
		viewCartRec.setTranslateY(-25);
		viewCartRec.setArcHeight(50);
		viewCartRec.setArcWidth(50);
		
		//buttons in view cart 
		Button removeCartbt = new Button("Remove from cart");
		removeCartbt.setPrefWidth(120);
		Button updateCartbt = new Button("Update cart");
		updateCartbt.setPrefWidth(120);
		Button goBackCartbt = new Button("Go back");
		goBackCartbt.setPrefWidth(120);
		Button confirmPurchasebt = new Button("Confirm purchase");
		confirmPurchasebt.setPrefWidth(120);
		//hbox for all buttons 
		HBox cartbt = new HBox(removeCartbt, updateCartbt, confirmPurchasebt, goBackCartbt);
		cartbt.setAlignment(Pos.BOTTOM_CENTER);
		cartbt.setTranslateY(315);
		
		//search text field and ok button
		TextField tfsearchCart = new TextField();
		tfsearchCart.setMaxWidth(400);
		tfsearchCart.setOpacity(0.5);
		tfsearchCart.setPromptText("Search");
		tfsearchCart.setStyle("-fx-font-weight: bold;");
		tfsearchCart.setTranslateY(316);
		tfsearchCart.setTranslateX(160);
		
		//okay button for cart
		Button okCartbt = new Button("Ok");
		okCartbt.setPrefWidth(80);
		okCartbt.setTranslateX(560);
		okCartbt.setTranslateY(290);
		
		//vbox for cart layout
		//add view cart view list here
		VBox cartLayout = new VBox(cartbt, tfsearchCart, okCartbt, cartListView);
		//view cart stack pane
		StackPane viewCartspane = new StackPane(blackBox, viewCartRec, cartLayout);
		viewCartspane.setVisible(false);
		
		//order history buttons and etc
		Button orderHistoryGobackbt = new Button("Go back");
		
		Rectangle orderHistoryRec = new Rectangle(600,350);
		orderHistoryRec.setFill(Color.LIGHTGRAY);
		orderHistoryRec.setTranslateY(-25);
		orderHistoryRec.setArcHeight(50);
		orderHistoryRec.setArcWidth(50);
		
		VBox orderHistoryLayout = new VBox(orderHistoryListView, orderHistoryGobackbt);
		orderHistoryLayout.setAlignment(Pos.CENTER);
		StackPane viewOrderHistorySpane = new StackPane(blackBox, orderHistoryRec, orderHistoryLayout);
		viewOrderHistorySpane.setVisible(false);

		// Create the sorting ComboBox
		ComboBox<String> sortOptions = new ComboBox<>();
		sortOptions.getItems().addAll(
			"Clear Filters",
			"View by Galaxy",
			"View by Planets",
			"View by Price",
			"View Alphabetically"
		);

		// Set initial selection
		sortOptions.setValue("Clear Filters");

		
		HBox buttonLayout = new HBox();
		buttonLayout.getChildren().addAll(logoutButton, addToCartbt, viewCartbt, viewOrderHistorybt, sortOptions);
		buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout.setTranslateX(-20);
		
		//Button pane
		layout.getChildren().addAll(productListView, buttonLayout, searchBox);
		//add box to update item
		StackPane customerSpane = new StackPane(root ,userImageView, layout, viewCartspane, addUpdateSpane, viewOrderHistorySpane);
		
		customerScene = new Scene(customerSpane, 800, 450);
		
		//Logic part 
		//action for addToCartbt
		addToCartbt.setOnAction(e -> {
			searchBox.setVisible(true);
			//add input inside tfsearch incart once ok is hit
			okButton.setOnAction(eadd -> {
				searchBox.setVisible(false);
				String name = tfsearch.getText();
				if (customer.itemExists(name)){
					addUpdateSpane.setVisible(true);
					amountOkbt.setOnAction(eamount ->{
						//get amount from textfield
						int amount = Integer.parseInt(tfamount.getText());
						customer.addToCart(name, amount);	
						productListView.refresh();
						addUpdateSpane.setVisible(false);
					});
				}
				//set visible to false once updateok button is clicked
				
			});
			
		});
		
		cartListView.setMaxWidth(480);
		cartListView.setMaxHeight(250);
		cartListView.setTranslateX(160);
		cartListView.setTranslateY(-15);
		
		orderHistoryListView.setMaxWidth(480);
		orderHistoryListView.setMaxHeight(250);
		orderHistoryListView.setTranslateY(-15);
		
		//action for viewCartbt
		viewCartbt.setOnAction(e ->{
			tfsearchCart.setVisible(false);
			okCartbt.setVisible(false);
			viewCartspane.setVisible(true);
			// Get the customer's cart
			ArrayList<models.CartProduct> cart = customer.getInCart();
			// Convert to observable list
			ObservableList<String> items = FXCollections.observableArrayList();
			for (models.CartProduct product : cart) {
				items.add("Name: " + product.getProduct().getName() + 
				"\nPrice: " + product.getProduct().getPrice() +
				"\nQuantity: " + product.getQuantity());
			}
			items.add("Total bill: " + customer.getTotalBill());
			// Update the ListView
			cartListView.setItems(items);	

			//action for removeCartbt, updateCartbt, goBackCartbt
			removeCartbt.setOnAction( erc -> {
				tfsearchCart.setPromptText("Search item to remove from cart");
				tfsearchCart.setVisible(true);
				okCartbt.setVisible(true);
				okCartbt.setOnAction(eok ->{
					String name = tfsearchCart.getText();
					customer.removeFromCart(name);
					productListView.refresh();
					addUpdateSpane.setVisible(false);
					viewCartspane.setVisible(false);
				});
				
			});
			
			updateCartbt.setOnAction(euc ->{
				tfsearchCart.setPromptText("Search item to update in cart");
				tfsearchCart.setVisible(true);
				okCartbt.setVisible(true);
				
				okCartbt.setOnAction(eUpdate -> {
					String name = tfsearch.getText();
					searchBox.setVisible(false);
					addUpdateSpane.setVisible(true);
					amountOkbt.setOnAction(eamount ->{
						//get amount from textfield
						int amount = Integer.parseInt(tfamount.getText());
						customer.updateCartItem(name, amount);	
						productListView.refresh();
						addUpdateSpane.setVisible(false);
					});
					tfsearchCart.setVisible(false);
					okCartbt.setVisible(false);
					viewCartspane.setVisible(false);
				});
				
					//set visible to false once updateok button is clicked
					
			});
				
		});
		
		//confirm purchase button action 
		confirmPurchasebt.setOnAction(ecp -> {
			customer.confirmPurchase();
		});
		
		goBackCartbt.setOnAction(egb ->{
			viewCartspane.setVisible(false);
		});

		//action for viewOrderHistorybt
		viewOrderHistorybt.setOnAction(e ->{
			// Get the customer's order history
			ArrayList<CartProduct> orders = customer.getOrderHistory();
			// Convert to observable list
			ObservableList<String> items = FXCollections.observableArrayList();
			for (CartProduct product : orders) {
				items.add("Name" + product.getProduct().getName() + 
				"\nPrice: " + product.getProduct().getPrice() +
				"\nQuantity Ordered: " + product.getQuantity());
			}
			// Update the ListView
			orderHistoryListView.setItems(items);	
			viewOrderHistorySpane.setVisible(true);
			
			orderHistoryGobackbt.setOnAction(egb ->{
				viewOrderHistorySpane.setVisible(false);
			});
			
		});

		// Set on action
		sortOptions.setOnAction(event -> {
			String selectedOption = sortOptions.getValue();

			switch (selectedOption) {
				case "Clear Filters":
					// Display all products
					productListView.getItems().clear();
					productListView.setItems(FXCollections.observableArrayList(Store.getAllProducts()));
					break;
				case "View by Galaxy":
					// Display all galaxies
					ChoiceDialog<String> dialog = new ChoiceDialog<>(Store.getAllGalaxies().get(0), Store.getAllGalaxies());
					dialog.setTitle("Select Galaxy");
					dialog.setHeaderText(null);
					dialog.setContentText("Choose your galaxy:");

					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()){
						//productListView.getItems().clear();
						productListView.setItems(FXCollections.observableArrayList(Store.getProductsByGalaxy(result.get())));
						productListView.refresh();
					}
					break;
				case "View by Planets":
					// Display all planets
					ChoiceDialog<String> dialog2 = new ChoiceDialog<>(Store.getAllPlanets().get(0), Store.getAllPlanets());
					dialog2.setTitle("Select Planet");
					dialog2.setHeaderText(null);
					dialog2.setContentText("Choose your Planet:");

					Optional<String> result2 = dialog2.showAndWait();
					if (result2.isPresent()){
						//productListView.getItems().clear();
						productListView.setItems(FXCollections.observableArrayList(Store.getProductsByPlanet(result2.get())));
						productListView.refresh();
					}
					break;
				case "View by Price":
					// Sort products by price
					ArrayList<Product> sortedByPrice = new ArrayList<>(Store.getAllProducts().stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList()));
					productListView.setItems(FXCollections.observableArrayList(sortedByPrice));
					productListView.refresh();
					break;
				case "View Alphabetically":
					// Sort products by name
					ArrayList<Product> sortedByName = new ArrayList<>(Store.getAllProducts().stream().sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList()));
					productListView.setItems(FXCollections.observableArrayList(sortedByName));
					productListView.refresh();
					break;
			}
		});

		
		return customerScene;
	}

}