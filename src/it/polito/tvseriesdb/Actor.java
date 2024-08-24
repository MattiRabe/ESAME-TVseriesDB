package it.polito.tvseriesdb;

import java.util.TreeMap;

public class Actor {

    private String nameSurname;
    private String name;
    private String surname;
    private String nationality;
    private TreeMap<String, TVSeries> series = new TreeMap<>();

    
    public Actor(String nameSurname, String name, String surname, String nationality) {
        this.nameSurname = nameSurname;
        this.name = name;
        this.surname = surname;
        this.nationality = nationality;
    }

    
    public String getNameSurname() {
        return nameSurname;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getNationality() {
        return nationality;
    }


    public TreeMap<String, TVSeries> getSeries() {
        return series;
    }

    public void addSerie(TVSeries s){
        series.put(s.getName(), s);
    }

    public Boolean isBestActor(String tService){
        for(TVSeries s: series.values()){
             if(!s.gettService().equals(tService)) return false;
             if(s.getAverageScore()<=8) return false;
        }
        return true;
    }
    

}
