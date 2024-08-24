package it.polito.tvseriesdb;

import java.util.TreeMap;

public class User {

    private String username;
    private String favGenre;
    private TreeMap<String, TVSeries> favSeries = new TreeMap<>();

    
    public User(String username, String favGenre) {
        this.username = username;
        this.favGenre = favGenre;
    }

    public String getUsername() {
        return username;
    }
    public String getFavGenre() {
        return favGenre;
    }
    
    public void addFavSeries(TVSeries s){
        favSeries.put(s.getName(), s);
    }

    public TreeMap<String, TVSeries> getFavSeries() {
        return favSeries;
    }

}
