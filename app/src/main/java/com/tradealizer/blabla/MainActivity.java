package com.tradealizer.blabla;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    TextView gesamtkosten;
    //TextView myText;
    AllesDBHandler dbHandler;
    private static final String TAG = "MainClass";
    int activeView = 2;

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
        populateListView2();
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
        String[] from = new String[] {AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung};
        int[] to = new int[] {R.id.ID_Kosten, R.id.ID_Beschreibung };
        ListView listv = (ListView) findViewById(R.id.listView);
        int layout = R.layout.item_layout;


        activeView = 1;
        //GridView funktioniert noch nicht!
        InstanceDataAdapter(from,to,listv,layout);
    }
    private void populateListView2(){
        String[] from = new String[] {AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung};
        int[] to = new int[] {R.id.ID_Kosten_kosten, R.id.ID_Beschreibung_kosten };
        ListView listv = (ListView) findViewById(R.id.gridView_Kosten);
        int layout = R.layout.item_layout_kosten;


        activeView = 2;
        InstanceDataAdapter(from,to,listv,layout);
    }
    private void populateListView3()
    {
        String[] from = new String[] {AllesDBHandler.COLUMN_Kosten};
        int[] to = new int[] {R.id.ID_Kosten_pur };
        ListView listv = (ListView) findViewById(R.id.gridView_Pur);
        int layout = R.layout.item_layout_pur;


        activeView = 3;
        InstanceDataAdapter(from, to, listv, layout);
    }

    private void InstanceDataAdapter(String[] from, int[] to, ListView listv, int layout) {
        Cursor cursor = dbHandler.getAllRows();
        SimpleCursorAdapter cursorAdapter;
        cursorAdapter = new SimpleCursorAdapter(this,layout,cursor,from,to,0);

        listv.setVisibility(View.VISIBLE);
        listv.setAdapter(cursorAdapter);





        listv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                                                                                            /*adb.setNegativeButton("Cancel", null);
                                                                                            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                                                                                                public void onClick(DialogInterface dialog, int which) {
                                                                                                    MyDataObject.remove(positionToRemove);
                                                                                                    adapter.notifyDataSetChanged();
                                                                                                }});
                                                                                            adb.show();*/
                ArrayList<Alles> allesArrayList = dbHandler.getArrayList();


                final Dialog d = new Dialog(MainActivity.this);
                d.setTitle("Details");
                d.setContentView(R.layout.item_layout);

                TextView kosten = (TextView) d.findViewById(R.id.ID_Kosten);
                TextView beschreibung = (TextView) d.findViewById(R.id.ID_Beschreibung);
                TextView datum = (TextView) d.findViewById(R.id.ID_Datum);
                TextView art = (TextView) d.findViewById(R.id.ID_Art);
                TextView kostenart = (TextView) d.findViewById(R.id.ID_Kostenart);
                TextView ort = (TextView) d.findViewById(R.id.ID_Ort);
                TextView adresse = (TextView) d.findViewById(R.id.ID_Adresse);
                TextView person = (TextView) d.findViewById(R.id.ID_Person);


                // Werte auf null checken!

                kosten.setText(Integer.toString(allesArrayList.get(positionToRemove).getKosten()));
                beschreibung.setText(allesArrayList.get(positionToRemove).getBeschreibung());
                datum.setText(allesArrayList.get(positionToRemove).getDatum().toString());
                art.setText(allesArrayList.get(positionToRemove).getArt());
                kostenart.setText(allesArrayList.get(positionToRemove).getKostenart());
                ort.setText(allesArrayList.get(positionToRemove).getOrt());
                adresse.setText(allesArrayList.get(positionToRemove).getAdresse());
                person.setText(allesArrayList.get(positionToRemove).getPerson());

                d.show();



                //dbHandler.deleteProduct(allesArrayList.get(positionToRemove).getBeschreibung());

                EmptyAllGrids();
                printDatabase();

                if (activeView == 1){
                    populateListView();
                }
                if (activeView == 2){
                    populateListView2();
                }
                if (activeView == 3){
                    populateListView3();
                }



            }
        });
    }



    public void Button_AllClicked(View v){
        /*EmptyAllGrids();
        populateListView();*/
        for (int i = 1; i < 11; i++){
            Alles alles = new Alles(i, String.valueOf(i));
            dbHandler.addProduct(alles);
        }
        EmptyAllGrids();
        printDatabase();
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
        ListView gridVAll = (ListView) findViewById(R.id.listView);
        ListView gridVKosten = (ListView) findViewById(R.id.gridView_Kosten);
        ListView gridVPur = (ListView) findViewById(R.id.gridView_Pur);

        gridVAll.setAdapter(null);
        gridVKosten.setAdapter(null);
        gridVPur.setAdapter(null);

        gridVAll.setVisibility(View.INVISIBLE);
        gridVKosten.setVisibility(View.INVISIBLE);
        gridVPur.setVisibility(View.INVISIBLE);
    }
}
