package it.polito.tvseriesdb;

import java.util.LinkedList;

public class TVSeries {

    private String name;
    private String tService;
    private String genre;
    private LinkedList<Actor> cast = new LinkedList<>();

    
    public TVSeries(String name, String tService, String genre) {
        this.name = name;
        this.tService = tService;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }
    public String gettService() {
        return tService;
    }
    public String getGenre() {
        return genre;
    }

    public LinkedList<Actor> getCast() {
        return cast;
    }

    public void addActorToCast(Actor a){
        cast.add(a);
    }
    


    

}
