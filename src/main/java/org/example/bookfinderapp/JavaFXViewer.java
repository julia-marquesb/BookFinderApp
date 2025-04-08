package org.example.bookfinderapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.List;


//This is the main class, it manages the app interfaces
public class JavaFXViewer extends Application {

    private VBox rootLayout;
    private VBox searchLayout;
    private VBox resultsLayout;

    private TextField titleField;
    private TextField authorField;
    private Button searchButton;


    @Override
    public void start(Stage primaryStage) throws IOException {
        //Initialize root layout
        rootLayout = new VBox(15);
        rootLayout.setStyle("-fx-padding: 20px; -fx-alignment: center; -fx-background-color: #121212;");

        //Create the search page and layout
        createSearchPage();
        rootLayout.getChildren().add(searchLayout);

        Scene scene = new Scene(rootLayout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/org/example/bookfinderapp/style.css").toExternalForm());

        primaryStage.setTitle("Book Finder");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void createSearchPage() {

        searchLayout = new VBox(10);
        searchLayout.setStyle("-fx-alignment: center; -fx-spacing: 10px;");

        Label appTitle = new Label("Book Finder");
        appTitle.setId("appTitle");

        titleField = new TextField();
        titleField.setPromptText("Enter a Book Title");
        titleField.setStyle("-fx-background-color: #1E1E1E; -fx-text-fill: white;");

        //Ensure placeholder remains visible until user types
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                titleField.setPromptText("Enter a book title");
            }
        });

        authorField = new TextField();
        authorField.setPromptText("Enter an Author Name");
        authorField.setStyle("-fx-background-color: #1E1E1E; -fx-text-fill: white;");

        //Ensure placeholder remains visible until user types
        authorField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                authorField.setPromptText("Enter author name");
            }
        });

        searchButton = new Button("Search Books");
        searchButton.setStyle("-fx-background-color: #00C853; -fx-text-fill: white; -fx-font-weight: bold;");
        searchButton.setOnAction(event -> handleSearch());

        searchLayout.getChildren().addAll(appTitle, titleField, authorField, searchButton);
        searchLayout.setAlignment(Pos.CENTER);

    }

    private void handleSearch() {
        String title = titleField.getText();
        String authors = authorField.getText();

        GoogleBooksApi api = new GoogleBooksApi();
        List<Book> books = api.getBooks(title, authors);

        if (books == null || books.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No books found for your query.");
            alert.show();
        } else {
            displayResults(books);
        }
    }

    private void displayResults(List<Book> books) {
        resultsLayout = new VBox(15);
        resultsLayout.setStyle("-fx-padding: 10px; -fx-alignment: top-left;");

        ScrollPane scrollPane = new ScrollPane();
        VBox bookCards = new VBox(10);
        for (Book book : books) {
            // Create a single book card
            VBox bookCard = new VBox(10);
            bookCard.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 10px; -fx-spacing: 5px; -fx-border-color: #444; -fx-border-radius: 5px;");

            Label titleLabel = new Label("Title: " + book.getTitle());
            titleLabel.setStyle("-fx-text-fill: white;");

            Label authorLabel = new Label("Author: " + book.getAuthors());
            authorLabel.setStyle("-fx-text-fill: white;");

            Label publisherLabel = new Label("Publisher: " + book.getPublisher());
            publisherLabel.setStyle("-fx-text-fill: white;");

            Label publishedDateLabel = new Label("Published Date: " + book.getPublishedDate());
            publishedDateLabel.setStyle("-fx-text-fill: white;");

            Label descriptionLabel = new Label("Description: " + book.getDescription());
            descriptionLabel.setStyle("-fx-text-fill: white;");

            ImageView coverImage = new ImageView();
            if (!book.getCoverImageUrl().isEmpty()) {
                coverImage.setImage(new Image(book.getCoverImageUrl()));
                coverImage.setFitWidth(80);
                coverImage.setFitHeight(100);
            }

            bookCard.getChildren().addAll(coverImage, titleLabel, authorLabel, publisherLabel, publishedDateLabel, descriptionLabel);
            bookCards.getChildren().add(bookCard);
        }

        scrollPane.setContent(bookCards);

        Button backButton = new Button("Back to Main Page");
        backButton.setStyle("-fx-background-color: #FF1744; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(event -> goBackToSearch());

        resultsLayout.getChildren().addAll(scrollPane, backButton);

        rootLayout.getChildren().setAll(resultsLayout);
    }

    private void goBackToSearch() {
        rootLayout.getChildren().setAll(searchLayout);
    }

    
    public static void main(String[] args) {
        launch();
    }//end of launch

}//end of class

