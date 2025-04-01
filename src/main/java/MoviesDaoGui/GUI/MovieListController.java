package MoviesDaoGui.GUI;

import MoviesDaoGui.Converters.JsonConverter;
import MoviesDaoGui.DAOs.DirectorDao;
import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import org.json.JSONArray;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class MovieListController {
    @FXML private TableView<Movie> movieTable;
    @FXML private TableColumn<Movie, Integer> idColumn;
    @FXML private TableColumn<Movie, String> titleColumn;
    @FXML private TableColumn<Movie, Integer> yearColumn;
    @FXML private TableColumn<Movie, Double> ratingColumn;
    @FXML private TableColumn<Movie, String> genreColumn;
    @FXML private TableColumn<Movie, Integer> durationColumn;
    @FXML private TableColumn<Movie, String> directorColumn;  // Changed to String type
    @FXML private Label messageLabel;

    private DirectorDao directorDao = new DirectorDao();
    private Map<Integer, String> directorNameCache = new HashMap<>();

    @FXML
    public void initialize() {
        setupTableColumns();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("release_year"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Custom cell value factory for director names
        directorColumn.setCellValueFactory(cellData -> {
            Movie movie = cellData.getValue();
            String directorName = getCachedDirectorName(movie.getDirector_id());
            return new SimpleStringProperty(directorName);
        });
    }

    private String getCachedDirectorName(int directorId) {
        // Check cache first
        if (directorNameCache.containsKey(directorId)) {
            return directorNameCache.get(directorId);
        }

        // If not in cache, fetch from DAO
        try {
            String name = directorDao.getDirectorNameById(directorId);
            directorNameCache.put(directorId, name);
            return name;
        } catch (DaoException e) {
            return "Unknown Director";
        }
    }

    @FXML
    private void onShowAllMovies() {
        new Thread(() -> {
            try {
                String response = sendRequestToServer("displayAllMovies");
                System.out.println("Server response: " + response);
                if (response != null && !response.isEmpty() && !response.equals("No movies found")) {
                    ObservableList<Movie> movies = FXCollections.observableArrayList(
                            JsonConverter.jsonToMovieList(response)
                    );

                    // Preload director names for all movies
                    preloadDirectorNames(movies);

                    System.out.println("Converted movies: " + movies);
                    javafx.application.Platform.runLater(() -> {
                        movieTable.setItems(movies);
                        updateMessage("Loaded " + movies.size() + " movies");
                    });
                } else {
                    updateMessage("No movies found in database");
                }
            } catch (Exception e) {
                updateMessage("Error loading movies: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void preloadDirectorNames(ObservableList<Movie> movies) {
        for (Movie movie : movies) {
            // This will populate the cache
            getCachedDirectorName(movie.getDirector_id());
        }
    }

    @FXML
    private void onFindById() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find Movie");
        dialog.setHeaderText("Enter Movie ID:");
        dialog.setContentText("ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> new Thread(() -> {
            try {
                String response = sendRequestToServer("displayMovieById:" + id);
                Movie movie = JsonConverter.jsonToMovie(response);
                if (movie != null) {
                    // Ensure director name is cached
                    getCachedDirectorName(movie.getDirector_id());

                    // Create a new list with just this movie
                    ObservableList<Movie> singleMovieList = FXCollections.observableArrayList();
                    singleMovieList.add(movie);

                    javafx.application.Platform.runLater(() -> {
                        movieTable.setItems(singleMovieList); // Replace all items with just this one
                        movieTable.getSelectionModel().select(movie);
                        movieTable.scrollTo(movie);
                        updateMessage("Found movie ID: " + id);
                    });
                } else {
                    javafx.application.Platform.runLater(() -> {
                        updateMessage("Movie not found!");
                    });
                }
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    updateMessage("Error finding movie: " + e.getMessage());
                });
            }
        }).start());
    }

    @FXML
    private void onAddMovie() {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Add New Movie");

        // Set up form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        TextField yearField = new TextField();
        TextField ratingField = new TextField();
        TextField genreField = new TextField();
        TextField durationField = new TextField();
        TextField directorField = new TextField();

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Year:"), 0, 1);
        grid.add(yearField, 1, 1);
        grid.add(new Label("Rating:"), 0, 2);
        grid.add(ratingField, 1, 2);
        grid.add(new Label("Genre:"), 0, 3);
        grid.add(genreField, 1, 3);
        grid.add(new Label("Duration (mins):"), 0, 4);
        grid.add(durationField, 1, 4);
        grid.add(new Label("Director ID:"), 0, 5);
        grid.add(directorField, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return new Movie(
                            titleField.getText(),
                            Integer.parseInt(yearField.getText()),
                            genreField.getText(),
                            Double.parseDouble(ratingField.getText()),
                            Integer.parseInt(durationField.getText()),
                            Integer.parseInt(directorField.getText())
                    );
                } catch (NumberFormatException e) {
                    updateMessage("Invalid number format");
                    return null;
                }
            }
            return null;
        });

        Optional<Movie> result = dialog.showAndWait();
        result.ifPresent(movie -> new Thread(() -> {
            try {
                String json = JsonConverter.movieToJsonObject(movie);
                String response = sendRequestToServer("addMovie:" + json);
                if (response != null && !response.isEmpty()) {
                    updateMessage("Movie added successfully");
                    onShowAllMovies(); // Refresh the list
                }
            } catch (Exception e) {
                updateMessage("Error adding movie: " + e.getMessage());
            }
        }).start());
    }

    @FXML
    private void onUpdateMovie() {
        Movie selected = movieTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            updateMessage("No movie selected!");
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Movie Title");
        dialog.setHeaderText("Update title for Movie ID: " + selected.getId());

        TextField titleField = new TextField(selected.getTitle());
        VBox content = new VBox(10, new Label("New Title:"), titleField);
        content.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return titleField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newTitle -> new Thread(() -> {
            try {
                String response = sendRequestToServer("updateMovie:" + selected.getId() + ":" + newTitle);
                if (response != null && response.equals("success")) {
                    updateMessage("Movie updated successfully");
                    onShowAllMovies(); // Refresh the list
                }
            } catch (Exception e) {
                updateMessage("Error updating movie: " + e.getMessage());
            }
        }).start());
    }

    @FXML
    private void onDeleteMovie() {
        Movie selected = movieTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            updateMessage("No movie selected!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Movie");
        confirm.setContentText("Are you sure you want to delete: " + selected.getTitle() + "?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            new Thread(() -> {
                try {
                    String response = sendRequestToServer("deleteMovieById:" + selected.getId());
                    if (response != null && response.equals("success")) {
                        updateMessage("Movie deleted successfully");
                        onShowAllMovies(); // Refresh the list
                    }
                } catch (Exception e) {
                    updateMessage("Error deleting movie: " + e.getMessage());
                }
            }).start();
        }
    }

    @FXML
    private void onFilterMovies() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter Movies");
        dialog.setHeaderText("Enter title filter:");
        dialog.setContentText("Title contains:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(filter -> new Thread(() -> {
            try {
                String response = sendRequestToServer("filterMovies:" + filter);
                if (response != null && !response.isEmpty()) {
                    ObservableList<Movie> movies = FXCollections.observableArrayList(
                            JsonConverter.jsonToMovieList(response)
                    );

                    // Preload director names for filtered movies
                    preloadDirectorNames(movies);

                    javafx.application.Platform.runLater(() -> {
                        movieTable.setItems(movies);
                        updateMessage("Found " + movies.size() + " matching movies");
                    });
                }
            } catch (Exception e) {
                updateMessage("Error filtering movies: " + e.getMessage());
            }
        }).start());
    }

    @FXML
    private void onGetImagesList() {
        new Thread(() -> {
            try {
                String response = sendRequestToServer("getImagesList");
                if (response != null && !response.isEmpty()) {
                    JSONArray imagesArray = new JSONArray(response);
                    List<String> imagesList = new ArrayList<>();
                    for (int i = 0; i < imagesArray.length(); i++) {
                        imagesList.add(imagesArray.getString(i));
                    }

                    javafx.application.Platform.runLater(() -> { // https://stackoverflow.com/questions/44850645/java-application-this-operation-is-permitted-on-the-event-thread-only-error
                        if (!imagesList.isEmpty()) {
                            showImageSelectionDialog(imagesList);
                        } else {
                            updateMessage("No images available on server");
                        }
                    });
                } else {
                    updateMessage("No images available on server");
                }
            } catch (Exception e) {
                updateMessage("Error getting images list: " + e.getMessage());
            }
        }).start();
    }

    private void showImageSelectionDialog(List<String> imagesList) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(imagesList.get(0), imagesList);
        dialog.setTitle("Select Image");
        dialog.setHeaderText("Available Images on Server");
        dialog.setContentText("Choose an image to download:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(imageName -> downloadAndDisplayImage(imageName));
    }

    private void downloadAndDisplayImage(String imageName) {
        new Thread(() -> {
            try {
                String response = sendRequestToServer("getImage:" + imageName);
                if (response != null && !response.isEmpty() && !response.startsWith("Error")) {
                    byte[] imageData = Base64.getDecoder().decode(response);

                    javafx.application.Platform.runLater(() -> { // https://stackoverflow.com/questions/44850645/java-application-this-operation-is-permitted-on-the-event-thread-only-error
                        // save to user's choice of location
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Image");
                        fileChooser.setInitialFileName(imageName);
                        File file = fileChooser.showSaveDialog(null);

                        if (file != null) {
                            new Thread(() -> {
                                try (FileOutputStream fos = new FileOutputStream(file)) {
                                    fos.write(imageData);
                                    updateMessage("Image saved successfully: " + file.getAbsolutePath());
                                } catch (IOException e) {
                                    updateMessage("Error saving image: " + e.getMessage());
                                }
                            }).start();
                        }
                    });
                } else {
                    updateMessage("Error downloading image: " + response);
                }
            } catch (Exception e) {
                updateMessage("Error processing image: " + e.getMessage());
            }
        }).start();
    }

    private String sendRequestToServer(String request) {
        try (Socket socket = new Socket("localhost", 49000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request);
            return in.readLine();

        } catch (IOException e) {
            updateMessage("Error communicating with server: " + e.getMessage());
            return null;
        }
    }

    private void updateMessage(String message) {
        javafx.application.Platform.runLater(() ->
                messageLabel.setText(message)
        );
    }
}