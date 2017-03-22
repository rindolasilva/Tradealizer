package com.tradealizer.blabla;

import java.sql.Date;

/**
 * Created by Alex on 01.03.2017.
 */

public class Alles {
    public enum Arten{

    }
    public enum Kostenarten{

    }
    private int ID;
    private int Kosten;
    private String Beschreibung;
    private Date Datum;
    private Arten Art;
    private Kostenarten Kostenart;
    private String Ort;
    private String Adresse;
    private String Person;

    public Alles(int Kosten){
        this.Kosten=Kosten;
    }
    public Alles(int Kosten, String Beschreibung){
        this.Kosten=Kosten;
        this.Beschreibung=Beschreibung;
    }
    public Alles(int Kosten, String Beschreibung, Date Datum, Arten Art, Kostenarten Kostenart, String Ort, String Adresse, String Person){
        this.Kosten=Kosten;
        this.Beschreibung=Beschreibung;
        this.Datum=Datum;
        this.Art=Art;
        this.Kostenart=Kostenart;
        this.Ort=Ort;
        this.Adresse=Adresse;
        this.Person=Person;
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
            return Art.toString();
        }
        else
        {
            return "";
        }
    }

    public void setArt(Arten art) {
        Art = art;
    }

    public String getKostenart() {
        if (Kostenart != null)
        {
            return Kostenart.toString();
        }
        else
        {
            return "";
        }
    }

    public void setKostenart(Kostenarten kostenart) {
        Kostenart = kostenart;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }
}
