package it.polito.tvseriesdb;

import java.util.LinkedList;

public class Season {

    private Integer number;
    private String nameSerie;
    private Integer numEpisode;
    private Integer exitDate;
    private LinkedList<String> episodes = new LinkedList<>();


    public Season(Integer number, String nameSerie, Integer numEpisode, Integer exitDate) {
        this.number = number;
        this.nameSerie = nameSerie;
        this.numEpisode = numEpisode;
        this.exitDate = exitDate;
    }


    public Integer getNumEpisode() {
        return numEpisode;
    }


    public Integer getExitDate() {
        return exitDate;
    }


    public LinkedList<String> getEpisodes() {
        return episodes;
    }

    public void addEpisode(String title){
        episodes.addLast(title);
    }


    public Integer getNumber() {
        return number;
    }


    public String getNameSerie() {
        return nameSerie;
    }
}
