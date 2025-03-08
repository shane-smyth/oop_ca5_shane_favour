package MoviesDaoGui.DTOs;

public class Movie {
    private int id;
    private String title;
    private int release_year;
    private double rating;
    private String genre;
    private int duration;
    private int director_id;

    public Movie(int id, String title, int release_year, String genre, double rating, int duration, int director_id) {
        this.id = id;
        this.title = title;
        this.release_year = release_year;
        this.rating = rating;
        this.genre = genre;
        this.duration = duration;
        this.director_id = director_id;
    }
    public Movie(String title, int release_year, String genre, double rating, int duration, int director_id) {
        this.id = 0;
        this.title = title;
        this.release_year = release_year;
        this.rating = rating;
        this.genre = genre;
        this.duration = duration;
        this.director_id = director_id;
    }
    public Movie(String title, int year, int rating, String genre, int duration, int director) {

    }

    public int getDirector_id() {
        return director_id;
    }

    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", release_year=" + release_year +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", director_id=" + director_id +
                '}';
    }
}