package MoviesDaoGui.DTOs;

public class Actor {
    private int actor_id;
    private String name;

    public Actor(int actor_id, String name) {
        this.actor_id = actor_id;
        this.name = name;
    }
    public Actor(String name) {
        this.actor_id = 0;
        this.name = name;
    }
    public Actor() {
    }

    public int getActor_id() {
        return actor_id;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actor_id=" + actor_id +
                ", name='" + name + '\'' +
                '}';
    }
}
