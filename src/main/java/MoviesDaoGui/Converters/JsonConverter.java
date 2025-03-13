package MoviesDaoGui.Converters;

import MoviesDaoGui.DTOs.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

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
}
