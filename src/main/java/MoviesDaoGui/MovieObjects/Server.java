package MoviesDaoGui.MovieObjects;

import MoviesDaoGui.Converters.JsonConverter;
import MoviesDaoGui.DAOs.MovieDao;
import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    final static int SERVER_PORT_NUMBER = 49000;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT_NUMBER)) {
            System.out.println("Server started on port " + SERVER_PORT_NUMBER);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    String request = in.readLine();
                    System.out.println("Received request: " + request);

                    String response = processRequest(request);
                    out.println(response);

                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private String processRequest(String request) {
        try {
            if (request == null || request.isEmpty()) {
                return "Empty request";
            }

            if (request.startsWith("displayMovieById:")) {
                int id = Integer.parseInt(request.substring(17));
                Movie movie = new MovieDao().getMovieById(id);
                return movie != null ? JsonConverter.movieToJsonObject(movie) : "Movie not found";

            }
            else if (request.equals("displayAllMovies")) {
                List<Movie> movies = new MovieDao().getMovies();
                return !movies.isEmpty() ? JsonConverter.moviesListToJsonString(movies) : "No movies found";
            }
            else if (request.startsWith("addMovie:")) {
                Movie movie = JsonConverter.jsonToMovie(request.substring(9));
                Movie addedMovie = new MovieDao().addMovie(movie);
                return addedMovie != null ? JsonConverter.movieToJsonObject(addedMovie) : "Failed to add movie";
            }
            else if (request.startsWith("deleteMovieById:")) {
                int id = Integer.parseInt(request.substring(16));
                new MovieDao().deleteMovieById(id);
                return "success";
            }
            else if (request.startsWith("updateMovie:")) {
                String[] parts = request.split(":");
                int id = Integer.parseInt(parts[1]);
                String newTitle = parts[2];
                new MovieDao().updateTitle(id, newTitle);
                return "success";
            }
            else if (request.startsWith("filterMovies:")) {
                String filter = request.substring(13);
                List<Movie> movies = new MovieDao().filterByTitle(filter);
                return !movies.isEmpty() ? JsonConverter.moviesListToJsonString(movies) : "No matching movies";
            }
            else if (request.equals("exit")) {
                return "Goodbye";
            }

            return "Unknown command";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}