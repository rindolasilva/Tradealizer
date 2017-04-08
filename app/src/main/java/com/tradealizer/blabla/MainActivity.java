package com.tradealizer.blabla;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    TextView gesamtkosten;
    //TextView myText;
    AllesDBHandler dbHandler;
    private static final String TAG = "MainClass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        gesamtkosten = (TextView) findViewById(R.id.tv_gesamtkosten);
        //myText =(TextView)findViewById(R.id.myText);
        dbHandler = new AllesDBHandler(this, null, null, 1);
        printDatabase();
        Log.d(TAG, "Vor populate ");
        populateListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item)){
            return true;


        }
        return super.onOptionsItemSelected(item);

    }

    public void addFBClicked(View v){
        Log.d(TAG, "Methode wird ausgeführt");

        FloatingActionButton addEntry = (FloatingActionButton) findViewById(R.id.addFB);
        final Dialog d = new Dialog(MainActivity.this);
        d.setTitle("Hinzufuegen");
        d.setContentView(R.layout.insert_dialog_kosten);
        d.show();

        final EditText kosten = (EditText)d.findViewById(R.id.dialog_kosten);
        final EditText beschreibung = (EditText)d.findViewById(R.id.dialog_beschreibung);
        Button submitB = (Button)d.findViewById(R.id.dialog_add);
        Button cancelB = (Button)d.findViewById(R.id.dialog_cancel);

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dKosten = kosten.getText().toString();
                String dBeschreibung = beschreibung.getText().toString();
                Toast.makeText(getApplicationContext(),"Eingegebene Kosten: " + dKosten,Toast.LENGTH_SHORT).show();
                d.cancel();

                //urspruenglicher Add Button
                        /* Products product = new Products(myInput.getText().toString());
                        dbHandler.addProduct(product);
                        printDatabase();*/
                //myText.setText("Hallo");
                        /*Alles alles = new Alles(Integer.parseInt(myInput.getText().toString()),myBeschreibung.getText().toString());
                        dbHandler.addProduct(alles);
                        printDatabase();

                        populateListView();*/

                Alles alles = new Alles(Integer.parseInt(dKosten), dBeschreibung);
                dbHandler.addProduct(alles);
                printDatabase();

                populateListView();
            }
        });
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();


                // uspruenglicher Delete Button
                       /* String inputText = myInput.getText().toString();
                        dbHandler.deleteProduct(inputText);
                        printDatabase();
                        populateListView();*/
                // muss von hier entfernt und in extra delete button gesetzt werden
            }
        });
    }


    public void printDatabase(){
        String[] dbString = dbHandler.databaseToString();
        //myText.setText(dbString);

        //myInput.setText("");
        gesamtkosten.setText("Gesamtkosten: " + dbString[1] + " €");
    }
    private void populateListView(){
        Log.d(TAG, "Vor cursor ");
        Cursor cursor = dbHandler.getAllRows();
        Log.d(TAG, "Vor arrays und adapter ");
        String[] from = new String[] {AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung};
        int[] to = new int[] {R.id.ID_Kosten, R.id.ID_Beschreibung };
        SimpleCursorAdapter cursorAdapter;
        Log.d(TAG, "Vor adapter instanz ");
        cursorAdapter = new SimpleCursorAdapter(this,R.layout.item_layout,cursor,from,to,0);
        Log.d(TAG, "Vor listview instanz ");
        GridView listv = (GridView) findViewById(R.id.listView);
        listv.setVisibility(View.VISIBLE);
        // listv.setEmptyView(findViewById(R.id.ID_Kostenart));

        Log.d(TAG, "Vor set adapter ");
        listv.setAdapter(cursorAdapter);
        Log.d(TAG, "alles gut gelaufen ");
    }
    private void populateListView2(){
        Log.d(TAG, "Vor cursor ");
        Cursor cursor = dbHandler.getAllRows();
        Log.d(TAG, "Vor arrays und adapter ");
        String[] from = new String[] {AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung};
        int[] to = new int[] {R.id.ID_Kosten_kosten, R.id.ID_Beschreibung_kosten };
        SimpleCursorAdapter cursorAdapter;
        Log.d(TAG, "Vor adapter instanz ");
        cursorAdapter = new SimpleCursorAdapter(this,R.layout.item_layout_kosten,cursor,from,to,0);
        Log.d(TAG, "Vor listview instanz ");
        GridView listv = (GridView) findViewById(R.id.gridView_Kosten);
        listv.setVisibility(View.VISIBLE);
        Log.d(TAG, "Vor set adapter ");
        listv.setAdapter(cursorAdapter);
        Log.d(TAG, "alles gut gelaufen ");
    }
    private void populateListView3(){
        Log.d(TAG, "Vor cursor ");
        Cursor cursor = dbHandler.getAllRows();
        Log.d(TAG, "Vor arrays und adapter ");
        String[] from = new String[] {AllesDBHandler.COLUMN_Kosten};
        int[] to = new int[] {R.id.ID_Kosten_pur };
        SimpleCursorAdapter cursorAdapter;
        Log.d(TAG, "Vor adapter instanz ");
        cursorAdapter = new SimpleCursorAdapter(this,R.layout.item_layout_pur,cursor,from,to,0);
        Log.d(TAG, "Vor listview instanz ");
        GridView listv = (GridView) findViewById(R.id.gridView_Pur);
        listv.setVisibility(View.VISIBLE);
        Log.d(TAG, "Vor set adapter ");
        listv.setAdapter(cursorAdapter);
        Log.d(TAG, "alles gut gelaufen ");
    }
    public void Button_AllClicked(View v){
        EmptyAllGrids();
        populateListView();
    }
    public void Button_KostenClicked(View v){
        EmptyAllGrids();
        populateListView2();
    }
    public void Button_PurClicked(View v){
        EmptyAllGrids();
        populateListView3();
    }
    private void EmptyAllGrids(){
        GridView gridVAll = (GridView) findViewById(R.id.listView);
        GridView gridVKosten = (GridView) findViewById(R.id.gridView_Kosten);
        GridView gridVPur = (GridView) findViewById(R.id.gridView_Pur);

        gridVAll.setAdapter(null);
        gridVKosten.setAdapter(null);
        gridVPur.setAdapter(null);

        gridVAll.setVisibility(View.INVISIBLE);
        gridVKosten.setVisibility(View.INVISIBLE);
        gridVPur.setVisibility(View.INVISIBLE);
        // setAdapter null loeschen
    }
}
