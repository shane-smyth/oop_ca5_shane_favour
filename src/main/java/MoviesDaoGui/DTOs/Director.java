package MoviesDaoGui.DTOs;

public class Director {
    private int director_id;
    private String name;
    private String country;

    public Director(int director_id, String name, String country) {
        this.director_id = director_id;
        this.name = name;
        this.country = country;
    }
    public Director(String name, String country) {
        this.director_id = 0;
        this.name = name;
        this.country = country;
    }
    public Director() {

    }

    public int getDirector_id() {
        return director_id;
    }

    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Director{" +
                "director_id=" + director_id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
