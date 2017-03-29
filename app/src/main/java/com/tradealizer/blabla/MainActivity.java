package com.tradealizer.blabla;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    EditText myInput;
    EditText myBeschreibung;
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




        myInput=(EditText)findViewById(R.id.myInput);
        myBeschreibung =(EditText) findViewById(R.id.myBeschreibung);
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


    public void addButtonClicked(View v){
       /* Products product = new Products(myInput.getText().toString());
        dbHandler.addProduct(product);
        printDatabase();*/
        //myText.setText("Hallo");
        Alles alles = new Alles(Integer.parseInt(myInput.getText().toString()),myBeschreibung.getText().toString());
        dbHandler.addProduct(alles);
        printDatabase();

        populateListView();
    }
    public void deleteButtonClicked(View v){
        String inputText = myInput.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
        populateListView();

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
        GridView listv = (GridView) findViewById(R.id.listView);
        GridView gridVKosten = (GridView) findViewById(R.id.gridView_Kosten);
        GridView gridVPur = (GridView) findViewById(R.id.gridView_Pur);

        listv.setAdapter(null);
        gridVKosten.setAdapter(null);
        gridVPur.setAdapter(null);
    }
}
