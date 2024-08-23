package it.polito.tvseriesdb;

import java.util.LinkedList;
import java.util.TreeMap;

public class TVSeries {

    private String name;
    private String tService;
    private String genre;
    private LinkedList<Actor> cast = new LinkedList<>();
    private TreeMap<Integer, Season> seasons = new TreeMap<>();


    
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

    public TreeMap<Integer, Season> getSeasons() {
        return seasons;
    }

    
    public void addSeason(Season s){
        seasons.put(s.getNumber(), s);
    }

    public Integer getLatestExitDate(){
        if(seasons.size()==0) return 0;
        return seasons.get(seasons.size()-1).getExitDate();
    }
    


    

}
