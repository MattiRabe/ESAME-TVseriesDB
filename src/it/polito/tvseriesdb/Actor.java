package it.polito.tvseriesdb;

public class Actor {

    private String nameSurname;
    private String name;
    private String surname;
    private String nationality;

    
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

    

}
