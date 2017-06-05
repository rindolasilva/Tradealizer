package com.tradealizer.blabla;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Created by Alex on 01.03.2017.
 */
/*
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
LocalDate localDate = LocalDate.now();
System.out.println(dtf.format(localDate)); //2016/11/16



 Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df.format(c.getTime());
		System.out.println(formattedDate);
 */


public class AllesDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "project.db";
    public static final String TABLE_ALLES = "alles";
    public static final String _id  = "_id";
    //public static final String COLUMN_ID = "id";
    public static final String COLUMN_Kosten = "kosten";
    public static final String COLUMN_Beschreibung  = "beschreibung";
    public static final String COLUMN_Datum = "datum";
    public static final String COLUMN_Art  = "art";
    public static final String COLUMN_Kostenart = "kostenart";
    public static final String COLUMN_Ort  = "ort";
    public static final String COLUMN_Adresse = "adresse";
    public static final String COLUMN_Person  = "person";

    private static final String TAG = "AllesDBHandler";

    public AllesDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ALLES + "("
                + _id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_Kosten + " TEXT, "
                + COLUMN_Beschreibung + " TEXT, "
                + COLUMN_Datum + " TEXT, "
                + COLUMN_Art + " TEXT, "
                + COLUMN_Kostenart + " TEXT, "
                + COLUMN_Ort + " TEXT, "
                + COLUMN_Adresse + " TEXT, "
                + COLUMN_Person + " TEXT "
                + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLES);
        onCreate(db);
    }

    public void addProduct(Alles alles){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());


        ContentValues values = new ContentValues();

        // Fehler durch logs gefunden: Typecast von Nullobjekt (die beiden Enums)
        Log.d(TAG, "addProduct: ");

        values.put(COLUMN_Kosten, alles.getKosten());

        Log.d(TAG, "nach kosten ");
        values.put(COLUMN_Beschreibung, alles.getBeschreibung());

        Log.d(TAG, "nach beschreibung ");
        values.put(COLUMN_Datum, formattedDate);

        Log.d(TAG, "nach datum ");
        values.put(COLUMN_Art, alles.getArt()); //Datentyp Enum, returnt aber String

        Log.d(TAG, "nach art ");
        values.put(COLUMN_Kostenart, alles.getKostenart()); //Datentyp Enum, returnt aber String

        Log.d(TAG, "nach kostenart ");
        values.put(COLUMN_Ort,alles.getOrt());

        Log.d(TAG, "nach ort ");
        values.put(COLUMN_Adresse,alles.getAdresse());

        Log.d(TAG, "nach adresse ");
        values.put(COLUMN_Person,alles.getPerson());

        Log.d(TAG, "nach person ");
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ALLES, null, values);
        db.close();
    }
    public void deleteProduct(int productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ALLES + " WHERE " + _id + "=\"" + productName + "\";");
    }
    public void modifyProduct(Alles product){
        SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_Kosten, product.getKosten());
        values.put(COLUMN_Beschreibung, product.getBeschreibung());
        values.put(COLUMN_Datum, product.getDatum());
        values.put(COLUMN_Art, product.getArt()); //Datentyp Enum, returnt aber String
        values.put(COLUMN_Kostenart, product.getKostenart()); //Datentyp Enum, returnt aber String
        values.put(COLUMN_Ort,product.getOrt());
        values.put(COLUMN_Adresse,product.getAdresse());
        values.put(COLUMN_Person,product.getPerson());
        db.update(TABLE_ALLES,values,_id + " = " + product.getID(),null);
    }

    public Cursor getAllRows(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALLES + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        if (c != null)
        {
            c.moveToFirst();
        }
        return c;
    }
    public ArrayList getArrayList(){
        ArrayList<Alles> aAlles = new ArrayList<Alles>();
        Cursor c = getAllRows();
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        do
        {
            String[] data = new String[9];
            data[8]=c.getString(0);
            data[0]=c.getString(1);
            data[1]=c.getString(2);
            data[2] = c.getString(3);

            if (c.getString(4)==null)
            {
                aAlles.add(new Alles(Integer.parseInt(data[0]),data[1],data[2]));
            }
            else
            {
                data[3] = c.getString(4);
                data[4] = c.getString(5);
                data[5] = c.getString(6);
                data[6] = c.getString(7);
                data[7] = c.getString(8);

                aAlles.add(new Alles(Integer.parseInt(data[8]),Integer.parseInt(data[0]),data[1],data[2],data[3],data[4],data[5],data[6],data[7]));
            }

        } while (c.moveToNext());
        c.close();
        return aAlles;
    }

    public String[] databaseToString(){
        String dbString[]={" ", " "};
        int Gesamtkosten = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALLES + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(COLUMN_Kosten))!= null){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Kosten));
                dbString[0] += "\n";
                Gesamtkosten+=c.getInt(c.getColumnIndex(COLUMN_Kosten));
            }
            if (c.getString(c.getColumnIndex(COLUMN_Beschreibung))!= null && c.getString(c.getColumnIndex(COLUMN_Beschreibung)) != ""){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Beschreibung));
                dbString[0] += "\n";
            }
            if (c.getString(c.getColumnIndex(COLUMN_Datum))!= null && c.getString(c.getColumnIndex(COLUMN_Datum)) != ""){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Datum));
                dbString[0] += "\n";
            }
            if (c.getString(c.getColumnIndex(COLUMN_Art))!= null && c.getString(c.getColumnIndex(COLUMN_Art)) != ""){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Art));
                dbString[0] += "\n";
            }
            if (c.getString(c.getColumnIndex(COLUMN_Kostenart))!= null && c.getString(c.getColumnIndex(COLUMN_Kostenart)) != ""){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Kostenart));
                dbString[0] += "\n";
            }
            if (c.getString(c.getColumnIndex(COLUMN_Ort))!= null && c.getString(c.getColumnIndex(COLUMN_Ort)) != ""){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Ort));
                dbString[0] += "\n";
            }
            if (c.getString(c.getColumnIndex(COLUMN_Adresse))!= null && c.getString(c.getColumnIndex(COLUMN_Adresse)) != ""){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Adresse));
                dbString[0] += "\n";
            }
            if (c.getString(c.getColumnIndex(COLUMN_Person))!= null && c.getString(c.getColumnIndex(COLUMN_Person)) != ""){
                dbString[0] += c.getString(c.getColumnIndex(COLUMN_Person));
                dbString[0] += "\n";
            }
            c.moveToNext();
        }
        db.close();
        dbString[1]=Integer.toString(Gesamtkosten);
        return dbString;
    }
}
