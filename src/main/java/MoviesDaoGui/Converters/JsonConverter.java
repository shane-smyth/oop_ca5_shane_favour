package MoviesDaoGui.Converters;

import MoviesDaoGui.DTOs.Director;
import MoviesDaoGui.DTOs.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {
    public static String moviesListToJsonString(List<Movie> movies) {
        JSONArray jsonArray = new JSONArray();
        for (Movie movie : movies) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", movie.getId());
            jsonObject.put("title", movie.getTitle());
            jsonObject.put("release_year", movie.getRelease_year());
            jsonObject.put("rating", movie.getRating());
            jsonObject.put("genre", movie.getGenre());
            jsonObject.put("duration", movie.getDuration());
            jsonObject.put("director", movie.getDirector_id());
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }


    public static String movieToJsonObject(Movie movie) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", movie.getId());
        jsonObject.put("title", movie.getTitle());
        jsonObject.put("release_year", movie.getRelease_year());
        jsonObject.put("rating", movie.getRating());
        jsonObject.put("genre", movie.getGenre());
        jsonObject.put("duration", movie.getDuration());
        jsonObject.put("director", movie.getDirector_id());
        return jsonObject.toString();
    }


    public static Movie jsonToMovie(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return new Movie(
                jsonObject.getInt("id"),
                jsonObject.getString("title"),
                jsonObject.getInt("release_year"),
                jsonObject.getString("genre"),
                jsonObject.getDouble("rating"),
                jsonObject.getInt("duration"),
                jsonObject.getInt("director")
        );
    }

    public static List<Movie> jsonToMovieList(String jsonString) {
        List<Movie> movies = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Movie movie = new Movie(
                    jsonObject.getInt("id"),
                    jsonObject.getString("title"),
                    jsonObject.getInt("release_year"),
                    jsonObject.getString("genre"),
                    jsonObject.getDouble("rating"),
                    jsonObject.getInt("duration"),
                    jsonObject.getInt("director")
            );
            movies.add(movie);
        }
        return movies;
    }

    public static String directorToJson(Director director) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("director_id", director.getDirector_id());
        jsonObject.put("name", director.getName());
        jsonObject.put("country", director.getCountry());
        return jsonObject.toString();
    }

    public static Director jsonToDirector(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return new Director(
                jsonObject.getString("name"),
                jsonObject.getString("country")
        );
    }
}