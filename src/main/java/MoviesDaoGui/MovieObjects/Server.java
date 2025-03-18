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

public class Server {
    final static int SERVER_PORT_NUMBER = 8888;  // could be any port from 1024 to 49151 (that doesn't clash with other Apps)

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT_NUMBER); ) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     // connection is made.
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Server Message: A Client has connected.");
                    String request = in.readLine(); // wait for input from the client, then read it.
                    System.out.println("Server message: Received from client : \"" + request + "\"");

                    // Implement our PROTOCOL
                    // The protocol is the logic that determines the responses given based on requests received.
                    //
                    if (request.startsWith("displayMovieById:")) {
                        int movieId = Integer.parseInt(request.split(":")[1]);
                        MovieDao movieDao = new MovieDao();
                        Movie movie = movieDao.getMovieById(movieId);

                        if (movie != null) {
                            String jsonResponse = JsonConverter.movieToJsonObject(movie);
                            out.println(jsonResponse);
                        }
                        else {
                            out.println("Movie not found !");
                        }
                    }
                }
                catch (DaoException | IOException e) {
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        }
        catch (IOException e) {
            System.out.println("Server Message: IOException: " + e.getMessage());
        }
    }
}