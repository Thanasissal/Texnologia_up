package main.java;

public class School {
    private String name;
    private String city;
    private Integer score;

    public School(String name, String city, Integer score) {
        this.name = name;
        this.city = city;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
