package it.polito.tvseriesdb;

public class Rate {

    private String username;
    private Integer score;


    public Rate(String username, Integer score) {
        this.username = username;
        this.score = score;
    }


    public String getUsername() {
        return username;
    }


    public Integer getScore() {
        return score;
    }
    

}
