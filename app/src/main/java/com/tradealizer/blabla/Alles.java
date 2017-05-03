package com.tradealizer.blabla;

import java.sql.Date;

/**
 * Created by Alex on 01.03.2017.
 */

public class Alles {
   /* public enum Arten{

    }
    public enum Kostenarten{

    }*/
    private int ID;
    private int Kosten;
    private String Beschreibung;
    //private Date Datum;
    //private Arten Art;
    //private Kostenarten Kostenart;
    private String Datum;
    private String Art;
    private String Kostenart;
    private String Ort;
    private String Adresse;
    private String Person;

    public Alles(int Kosten){
        this.Kosten=Kosten;

        Date ZwischenDatum = new Date(System.currentTimeMillis());
        this.Datum = ZwischenDatum.toString();

    }
    public Alles(int Kosten, String Beschreibung){
        this.Kosten=Kosten;
        this.Beschreibung=Beschreibung;

        Date ZwischenDatum = new Date(System.currentTimeMillis());
        this.Datum = ZwischenDatum.toString();
    }
    public Alles(int Kosten, String Beschreibung, String Datum){
        this.Kosten=Kosten;
        this.Beschreibung=Beschreibung;
        this.Datum = Datum;
    }
    public Alles(int ID,int Kosten, String Beschreibung, String Art, String Kostenart, String Ort, String Adresse, String Person){
        this.Kosten=Kosten;
        this.Beschreibung=Beschreibung;
        this.Datum=Datum;
        this.Art=Art;
        this.Kostenart=Kostenart;
        this.Ort=Ort;
        this.Adresse=Adresse;
        this.Person=Person;
        this.ID=ID;

        Date ZwischenDatum = new Date(System.currentTimeMillis());
        this.Datum = ZwischenDatum.toString();
    }
    public Alles(int ID, int Kosten, String Beschreibung, String Datum, String Art, String Kostenart, String Ort, String Adresse, String Person){
        this.Kosten=Kosten;
        this.Beschreibung=Beschreibung;
        this.Datum=Datum;
        this.Art=Art;
        this.Kostenart=Kostenart;
        this.Ort=Ort;
        this.Adresse=Adresse;
        this.Person=Person;
        this.ID=ID;
    }

    public int getKosten() {
        return Kosten;
    }

    public void setKosten(int kosten) {
        Kosten = kosten;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getArt() {
        if (Art != null)
        {
            return Art;
        }
        else
        {
            return " ";
        }
    }

    public void setArt(String art) {
        Art = art;
    }

    public String getKostenart() {
        if (Kostenart != null)
        {
            return Kostenart;
        }
        else
        {
            return " ";
        }
    }

    public void setKostenart(String kostenart) {
        Kostenart = kostenart;
    }

    public String getOrt() {
        //return Ort;
        return ((Ort == null) ? " " : Ort);
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getAdresse() {
        //return Adresse;
        return ((Adresse == null) ? " " : Adresse);
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getPerson() {
        //return Person;
        return ((Person == null) ? " " : Person);
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getDatum() {
        return Datum;
        //DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        //java.sql.Date datum = new Date(2000-01-01);
        //return datum;
        //return new Date(2000-01-01);
    }

    public int getID() {
        return ID;
    }
}
