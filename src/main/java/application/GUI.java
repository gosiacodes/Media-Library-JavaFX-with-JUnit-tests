package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application {

	public ListView<String> theTextArea;
	LibraryController theController = new LibraryController(this);
	Stage window;
	Stage window2;
	Scene scene1, scene2;
	GridPane layout1;
	GridPane layout2;
	Label messageLabel;
	public boolean loginSuccessful;

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Media Library Login");
		window.setResizable(false);
		window.setScene(createScene());
		window.show();
	}

	public Scene createScene() {
		// Layout for scene 1
		layout1 = new GridPane();
		layout1.setPadding(new Insets(20));
		layout1.setVgap(10);
		layout1.setHgap(10);
		layout1.setAlignment(Pos.CENTER);

		scene1 = new Scene(layout1, 600, 300);

		// Welcome text 1
		Text welcomeText = new Text("Welcome on login page to Media Library");
		welcomeText.setFont(Font.font("Tahoma", FontWeight.LIGHT, 20));
		welcomeText.setFill(Color.DARKGOLDENROD);
		GridPane.setConstraints(welcomeText, 1, 1);

		// User label
		Label userLabel = new Label("SSN: ");
		GridPane.setConstraints(userLabel, 0, 2);

		// Name field input
		TextField userSSN = new TextField();
		userSSN.setPromptText("YYMMDD-XXXX");
		userSSN.setId("userSSN");
		GridPane.setConstraints(userSSN, 1, 2);

		// Password label
		Label passLabel = new Label("Password: ");
		GridPane.setConstraints(passLabel, 0, 3);

		// Password field input
		PasswordField password = new PasswordField();
		password.setPromptText("password");
		password.setId("password");
		GridPane.setConstraints(password, 1, 3);

		// "Login" button
		Button loginButton = new Button("Login");
		GridPane.setConstraints(loginButton, 1, 4);

		// Image with media library logo
		Image image = new Image("file:media_library.png");
		ImageView imageView = new ImageView();
		GridPane.setConstraints(imageView, 1, 0);
		imageView.setImage(image);
		imageView.setFitWidth(100);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setCache(true);

		// Message label
		messageLabel = new Label("Enter SSN (Social Security Number: YYMMDD-XXXX) and password to login");
		messageLabel.setId("messageLabel");
		GridPane.setConstraints(messageLabel, 1, 6);

		// Adding all elements to layout 1
		layout1.getChildren().addAll(welcomeText, userLabel, userSSN, passLabel, password, loginButton, imageView,
				messageLabel);

		scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		// "Login" button action event with lambda
		loginButton.setOnAction((event) -> {
			// Checking if user's personal number and password are valid ones
			if (userSSN.getText().equals("") || password.getText().equals(""))
				messageLabel.setText("SSN and password can't be empty");
			else if (!Validate.validateSSN(userSSN.getText()) && !Validate.validatePassword(password.getText()))
				messageLabel.setText("Please enter a valid SSN and password");
			else if (!Validate.validateSSN(userSSN.getText()))
				messageLabel.setText("Please enter a valid SSN");
			else if (!Validate.validatePassword(password.getText()))
				messageLabel.setText("Please enter a valid password");
			else if (!theController.checkIfBorrowerExist(userSSN.getText()))
				messageLabel.setText("This user does not exist in database");
			// If personal number and password are correct and user exist -> login
			else if (Validate.validateSSN(userSSN.getText()) && Validate.validatePassword(password.getText())
					&& theController.checkIfBorrowerExist(userSSN.getText())) {
				loginSuccessful = true;
				createAnotherStage();
				window.hide();
			}
		});
		return scene1;
	}

	public Stage createAnotherStage() {
		window2 = new Stage();
		window2.setScene(createScene2());
		window2.setTitle("Media Library");
		window2.setResizable(false);
		window2.show();
		return window2;
	}

	public Scene createScene2() {
		// Layout for scene 2
		layout2 = new GridPane();
		layout2.setPadding(new Insets(20));
		layout2.setVgap(10);
		layout2.setHgap(10);

		scene2 = new Scene(layout2, 600, 600);

		// HBox for welcome text
		HBox welcomeBox = new HBox(20);
		welcomeBox.setPadding(new Insets(10, 10, 10, 10));
		welcomeBox.setAlignment(Pos.CENTER);
		GridPane.setConstraints(welcomeBox, 0, 1);

		// Welcome text 2
		Text welcomeText2 = new Text("Welcome to the Media Library!");
		welcomeText2.setFont(Font.font("Tahoma", FontWeight.LIGHT, 20));
		welcomeText2.setFill(Color.DARKGOLDENROD);
		welcomeText2.setTextAlignment(TextAlignment.CENTER);

		// HBox for radio-buttons group
		HBox radioButtons = new HBox(20);
		radioButtons.setPadding(new Insets(10, 10, 10, 10));
		radioButtons.setAlignment(Pos.CENTER);
		GridPane.setConstraints(radioButtons, 0, 2);

		// Radio-buttons
		ToggleGroup group = new ToggleGroup();
		RadioButton radioAll = new RadioButton("All");
		radioAll.setToggleGroup(group);
		radioAll.setSelected(true);
		RadioButton radioTitle = new RadioButton("Title");
		radioTitle.setToggleGroup(group);
		RadioButton radioID = new RadioButton("ID");
		radioID.setToggleGroup(group);

		// HBox for search section
		HBox searchSection = new HBox(20);
		searchSection.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setConstraints(searchSection, 0, 3);

		// Search field input
		TextField searchField = new TextField();
		searchField.setPromptText("search text");
		searchField.setPrefWidth(400);

		// "Search" button
		Button searchButton = new Button("Search");

		// HBox for buttons group
		HBox buttons = new HBox(20);
		buttons.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setConstraints(buttons, 0, 4);

		// Buttons "Borrow / Return", "Borrowed", "Info", "Logout"
		Button borrowButton = new Button("Borrow");
		Button borrowedButton = new Button("Borrowed");
		Button infoButton = new Button("Info");
		Button logoutButton = new Button("Logout");

		// ListView for adding information about books and DVDs
		theTextArea = new ListView<String>();
		GridPane.setConstraints(theTextArea, 0, 5);

		// Adding all elements to layout 2
		welcomeBox.getChildren().addAll(welcomeText2);
		searchSection.getChildren().addAll(searchField, searchButton);
		buttons.getChildren().addAll(borrowButton, borrowedButton, infoButton, logoutButton);
		radioButtons.getChildren().addAll(radioAll, radioTitle, radioID);
		layout2.getChildren().addAll(welcomeBox, radioButtons, searchSection, buttons, theTextArea);

		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		// "Search" button action event with lambda - searching media
		searchButton.setOnAction((event) -> {
			borrowButton.setText("Borrow");
			String theInput = searchField.getText();
			System.out.println("Search text: " + theInput);
			if (theController.checkUserInput(theInput)) {
				clearTheTextArea();
				if (radioID.isSelected()) {
					if (theController.checkInputOnlyDigits(theInput)) {
						Media temp = theController.getMedia(theInput);
						theController.mediaSearchResults.add(temp);
						if (temp != null)
							setTheTextArea(temp.toString());
					}
				} else if (radioTitle.isSelected()) {
					theController.searchMediaTitleByString(theInput);
				} else if (radioAll.isSelected()) {
					theController.searchMediaAllByString(theInput);
				}
			}

			else {
				alertInfo("The search text can't be empty");
			}

			searchField.setText("");
		});

		// "Borrow" button action event with lambda - borrowing media
		borrowButton.setOnAction((event) -> {
			if (borrowButton.getText().equals("Borrow")) {
				String selectedText = theTextArea.getSelectionModel().getSelectedItem();
				Media selectedMedia = theController.getMediaFromSearchResult(selectedText);
				if (selectedText != null && selectedMedia != null) {
					if (selectedMedia.isBorrowed()) {
						alertInfo("Can't borrow, already borrowed");
					} else {
						theController.borrowMedia(selectedMedia);
						borrowButton.setText("Borrow");
						String theInput = searchField.getText();
						if (theController.checkUserInput(theInput)) {
							clearTheTextArea();
							if (radioID.isSelected()) {
								if (theController.checkInputOnlyDigits(theInput)) {
									Media temp = theController.getMedia(theInput);
									theController.mediaSearchResults.add(temp);
									if (temp != null)
										setTheTextArea(temp.toString());
								}
							} else if (radioTitle.isSelected()) {
								theController.searchMediaTitleByString(theInput);
							} else if (radioAll.isSelected()) {
								theController.searchMediaAllByString(theInput);
							}
						}
					}
				} else {
					alertInfo("Nothing is selected");
				}
			} else {
				String selectedText = theTextArea.getSelectionModel().getSelectedItem();
				Media selectedMedia = theController.getMediaFromSearchResult(selectedText);
				if (selectedText != null && selectedMedia != null) {
					if (selectedMedia.isBorrowed() == false) {
						alertInfo("Cannot return, already returned.");
					} else {
						theController.returnMedia(selectedMedia);
						clearTheTextArea();
						theController.searchBorrowed();
					}
				}
			}
		});

		// "Borrowed" button action event with lambda - checking borrowed media for user who is logged in
		borrowedButton.setOnAction((event) -> {
			clearTheTextArea();
			theController.searchBorrowed();
			borrowButton.setText("Return");
		});

		// "Info" button action event with lambda - information about media and their status
		infoButton.setOnAction((event) -> {
			String selectedText = theTextArea.getSelectionModel().getSelectedItem();
			if (selectedText != null)
				theController.showSelectedMediaInfo(selectedText);
		});

		// "Logout" button action event with lambda
		logoutButton.setOnAction((event) -> {
			loginSuccessful = false;
			window.show();
			window.setScene(createScene());
			window2.close();
			messageLabel.setText("Enter SSN (Social Security Number: YYMMDD-XXXX) and password to login");
		});

		return scene2;
	}

	// Clearing the ListView
	public void clearTheTextArea() {
		theTextArea.getItems().clear();
	}

	// Adding items to ListView
	public void setTheTextArea(String addText) {
		theTextArea.getItems().addAll(addText);
	}

	// Alert method for "info" and errors
	public void alertInfo(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Information");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
