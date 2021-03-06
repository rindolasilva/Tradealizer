package com.tradealizer.blabla;


import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Date;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;

    private FragmentTransaction fragmentTransaction;
    private NavigationView navigationView;

    Button BtnAlles;
    Button BtnKosten;
    Button BtnBeschreibung;

    FloatingActionButton AddFB;


    TextView gesamtkosten;
    //TextView myText;
    AllesDBHandler dbHandler;
    private static final String TAG = "MainClass";
    int activeView = 2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        gesamtkosten = (TextView) v.findViewById(R.id.tv_gesamtkosten);
        //myText =(TextView)findViewById(R.id.myText);
        // dbHandler = new AllesDBHandler(this, null, null, 1);
        dbHandler = new AllesDBHandler(getActivity(), null, null, 1);
        printDatabase();
        Log.d(TAG, "Vor populate ");

     //  populateListView2();
// client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        client = new GoogleApiClient.Builder(getActivity()).addApi(AppIndex.API).build();

        BtnAlles = (Button)v.findViewById(R.id.button_All);
        BtnKosten = (Button)v.findViewById(R.id.button_Kosten);
        BtnBeschreibung = (Button)v.findViewById(R.id.button_Pur);

        AddFB = (FloatingActionButton) v.findViewById(R.id.addFB);

        AddFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFBClicked(v);
            }
        });

        BtnAlles.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Button_AllClicked(v);
            }
        });
        BtnKosten.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Button_KostenClicked(v);
            }
        });
        BtnBeschreibung.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Button_PurClicked(v);
            }
        });


        return v;
    }
    public void addFBClicked(View v) {
        Log.d(TAG, "Methode wird ausgeführt");

        FloatingActionButton addEntry = (FloatingActionButton) v.findViewById(R.id.addFB);

        final Dialog d = new Dialog(/*MainActivity.this*/ getActivity());
        d.setTitle("Hinzufuegen");
        d.setContentView(R.layout.insert_dialog_kosten);
        d.show();

        final EditText kosten = (EditText) d.findViewById(R.id.dialog_kosten);
        final EditText beschreibung = (EditText) d.findViewById(R.id.dialog_beschreibung);
        Button submitB = (Button) d.findViewById(R.id.dialog_add);
        Button cancelB = (Button) d.findViewById(R.id.dialog_cancel);
        Button alleB = (Button) d.findViewById(R.id.dialog_alle);

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dKosten = kosten.getText().toString();
                String dBeschreibung = beschreibung.getText().toString();
                //getApplicationContext()
                Toast.makeText(getContext().getApplicationContext(), "Eingegebene Kosten: " + dKosten, Toast.LENGTH_SHORT).show();
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

                //populateListView();
                RefreshListviews();
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
        alleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();
                final Dialog dAlles = new Dialog(/*MainActivity.this*/ HomeFragment.this.getActivity());
                dAlles.setTitle("Hinzufuegen");
                dAlles.setContentView(R.layout.insert_dialog_all);
                dAlles.show();

                final EditText kostenAlles = (EditText) dAlles.findViewById(R.id.dialog_kosten_alles);
                final EditText beschreibungAlles = (EditText) dAlles.findViewById(R.id.dialog_beschreibung_alles);
                //final EditText DatumAlles = (EditText)d.findViewById(R.id.dialog_beschreibung_alles);
                final EditText artAlles = (EditText) dAlles.findViewById(R.id.dialog_art_alles);
                //final EditText kostenartAlles = (EditText)dAlles.findViewById(R.id.dialog_kostenart_alles);
                final EditText ortAlles = (EditText) dAlles.findViewById(R.id.dialog_ort_alles);
                final EditText adresseAlles = (EditText) dAlles.findViewById(R.id.dialog_adresse_alles);
                final EditText personAlles = (EditText) dAlles.findViewById(R.id.dialog_person_alles);

                final Spinner kostenartAlles = (Spinner) dAlles.findViewById(R.id.dialog_spinner_kostenart_alles);

                Button submitBAlles = (Button) dAlles.findViewById(R.id.dialog_add_alles);
                Button cancelBAlles = (Button) dAlles.findViewById(R.id.dialog_cancel_alles);
                //Button alleB = (Button)d.findViewById(R.id.dialog_alle);

                /*final String s;

                kostenartAlles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        s = parent.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

                submitBAlles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dKostenAlles = kostenAlles.getText().toString();
                        String dBeschreibungAlles = beschreibungAlles.getText().toString();
                        String dArtAlles = artAlles.getText().toString();
                        //String dKostenArtAlles= kostenartAlles.getText().toString();
                        String dOrtAlles = ortAlles.getText().toString();
                        String dAdresseAlles = adresseAlles.getText().toString();
                        String dPersonAlles = personAlles.getText().toString();


                        String dKostenArtAlles = kostenartAlles.getSelectedItem().toString();
                        Date Zwischendatum=new Date(System.currentTimeMillis());
                        String dDatumAlles = Zwischendatum.toString();

                        //Toast.makeText(getApplicationContext(), "Eingegebene Kostenart: " + dKostenArtAlles, Toast.LENGTH_SHORT).show();


                                            //getApplicationContext()
                        Toast.makeText(getContext().getApplicationContext(), "Eingegebene Kosten: " + dKostenAlles, Toast.LENGTH_SHORT).show();
                        dAlles.cancel();

                        Alles alles = new Alles(0, Integer.parseInt(dKostenAlles), dBeschreibungAlles, dDatumAlles, dArtAlles, dKostenArtAlles, dOrtAlles, dAdresseAlles, dPersonAlles );
                        dbHandler.addProduct(alles);
                        printDatabase();

                        //populateListView();
                        RefreshListviews();
                    }
                });
                cancelBAlles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dAlles.cancel();
                    }
                });
            }
        });

    }


    public void printDatabase() {
        String[] dbString = dbHandler.databaseToString();
        //myText.setText(dbString);

        //myInput.setText("");
        gesamtkosten.setText("Gesamtkosten: " + dbString[1] + " €");
    }

    private void populateListView() {
        String[] from = new String[]{AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung};
        int[] to = new int[]{R.id.ID_Kosten, R.id.ID_Beschreibung};
        ListView listv = (ListView) getView().findViewById(R.id.listView);
        int layout = R.layout.item_layout;


        activeView = 1;
        //GridView funktioniert noch nicht!
        InstanceDataAdapter(from, to, listv, layout);
    }

    private void populateListView2() {
        String[] from = new String[]{AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung};
        int[] to = new int[]{R.id.ID_Kosten_kosten, R.id.ID_Beschreibung_kosten};
        ListView listv = (ListView) getView().findViewById(R.id.gridView_Kosten);
        int layout = R.layout.item_layout_kosten;


        activeView = 2;
        InstanceDataAdapter(from, to, listv, layout);
    }

    private void populateListView3() {
        String[] from = new String[]{AllesDBHandler.COLUMN_Kosten};
        int[] to = new int[]{R.id.ID_Kosten_pur};
        ListView listv = (ListView) getView().findViewById(R.id.gridView_Pur);
        int layout = R.layout.item_layout_pur;


        activeView = 3;
        InstanceDataAdapter(from, to, listv, layout);
    }
    private void populateListViewAlle() {
        String[] from = new String[]{AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung, AllesDBHandler.COLUMN_Datum, AllesDBHandler.COLUMN_Art, AllesDBHandler.COLUMN_Kostenart, AllesDBHandler.COLUMN_Ort, AllesDBHandler.COLUMN_Adresse, AllesDBHandler.COLUMN_Person};
        int[] to = new int[]{R.id.ID_Kosten, R.id.ID_Beschreibung, R.id.ID_Datum, R.id.ID_Art, R.id.ID_Kostenart, R.id.ID_Ort, R.id.ID_Adresse, R.id.ID_Person};
        ListView listv = (ListView) getView().findViewById(R.id.listView);
        int layout = R.layout.item_layout;


        activeView = 4;
        //GridView funktioniert noch nicht!
        InstanceDataAdapter(from, to, listv, layout);
    }

    private void InstanceDataAdapter(String[] from, int[] to, ListView listv, int layout) {
        Cursor cursor = dbHandler.getAllRows();
        SimpleCursorAdapter cursorAdapter;      //this
        cursorAdapter = new SimpleCursorAdapter(getActivity(), layout, cursor, from, to, 0);

        listv.setVisibility(View.VISIBLE);
        listv.setAdapter(cursorAdapter);


        listv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {      //MainActivity.this
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
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
                final ArrayList<Alles> allesArrayList = dbHandler.getArrayList();

                                            //MainActivity.this
                final Dialog d = new Dialog(getActivity());
                d.setTitle("Details");
                d.setContentView(R.layout.item_layout_dialog);

                TextView kosten = (TextView) d.findViewById(R.id.ID_Kosten_Dialog);
                TextView beschreibung = (TextView) d.findViewById(R.id.ID_Beschreibung_Dialog);
                final TextView datum = (TextView) d.findViewById(R.id.ID_Datum_Dialog);
                TextView art = (TextView) d.findViewById(R.id.ID_Art_Dialog);
                TextView kostenart = (TextView) d.findViewById(R.id.ID_Kostenart_Dialog);
                TextView ort = (TextView) d.findViewById(R.id.ID_Ort_Dialog);
                TextView adresse = (TextView) d.findViewById(R.id.ID_Adresse_Dialog);
                TextView person = (TextView) d.findViewById(R.id.ID_Person_Dialog);

                Button editB = (Button) d.findViewById(R.id.dialogB_Edit);
                Button deleB = (Button) d.findViewById(R.id.dialogB_Delete);


                // Werte auf null checken!

                /*kosten.setText("Kosten: " + Integer.toString(allesArrayList.get(positionToRemove).getKosten()));
                beschreibung.setText("Beschreibung: " + allesArrayList.get(positionToRemove).getBeschreibung());
                datum.setText(allesArrayList.get(positionToRemove).getDatum().toString());
                art.setText("Art: " + allesArrayList.get(positionToRemove).getArt());
                kostenart.setText("Kostenart: " + allesArrayList.get(positionToRemove).getKostenart());
                ort.setText("Ort: " + allesArrayList.get(positionToRemove).getOrt());
                adresse.setText("Adresse: " + allesArrayList.get(positionToRemove).getAdresse());
                person.setText("Person: " + allesArrayList.get(positionToRemove).getPerson());*/

                kosten.setText(Integer.toString(allesArrayList.get(positionToRemove).getKosten()));
                beschreibung.setText(allesArrayList.get(positionToRemove).getBeschreibung());
                datum.setText(allesArrayList.get(positionToRemove).getDatum().toString());
                art.setText(allesArrayList.get(positionToRemove).getArt());
                kostenart.setText(allesArrayList.get(positionToRemove).getKostenart());
                ort.setText(allesArrayList.get(positionToRemove).getOrt());
                adresse.setText(allesArrayList.get(positionToRemove).getAdresse());
                person.setText(allesArrayList.get(positionToRemove).getPerson());

                d.show();

                Window w = d.getWindow();
                w.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


                //dbHandler.deleteProduct(allesArrayList.get(positionToRemove).getBeschreibung());


                editB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.cancel();
                        final Dialog dEdit = new Dialog(/*MainActivity.this*/getActivity());
                        dEdit.setTitle("Edit");
                        dEdit.setContentView(R.layout.insert_dialog_all);

                        final EditText kosten = (EditText) dEdit.findViewById(R.id.dialog_kosten_alles);
                        final EditText beschreibung = (EditText) dEdit.findViewById(R.id.dialog_beschreibung_alles);
                        //final EditText datum = (EditText) dEdit.findViewById(R.id.dialo_);
                        final EditText art = (EditText) dEdit.findViewById(R.id.dialog_art_alles);
                        final Spinner kostenart = (Spinner) dEdit.findViewById(R.id.dialog_spinner_kostenart_alles);
                        final EditText ort = (EditText) dEdit.findViewById(R.id.dialog_ort_alles);
                        final EditText adresse = (EditText) dEdit.findViewById(R.id.dialog_adresse_alles);
                        final EditText person = (EditText) dEdit.findViewById(R.id.dialog_person_alles);

                        final int idDel = allesArrayList.get(positionToRemove).getID();

                        kosten.setText(Integer.toString(allesArrayList.get(positionToRemove).getKosten()));
                        beschreibung.setText(allesArrayList.get(positionToRemove).getBeschreibung());
                        //datum.setText(allesArrayList.get(positionToRemove).getDatum().toString());
                        art.setText(allesArrayList.get(positionToRemove).getArt());

                        int kostenArtPosition = CheckKostenart(allesArrayList.get(positionToRemove).getKostenart());
                        kostenart.setSelection(kostenArtPosition);
                        //kostenart.setText("Kostenart: " + allesArrayList.get(positionToRemove).getKostenart());
                        //kostenart.setSelection();
                        ort.setText(allesArrayList.get(positionToRemove).getOrt());
                        adresse.setText(allesArrayList.get(positionToRemove).getAdresse());
                        person.setText(allesArrayList.get(positionToRemove).getPerson());

                        dEdit.show();

                        Button modifyB = (Button) dEdit.findViewById(R.id.dialog_add_alles);
                        Button cancelModifyB = (Button) dEdit.findViewById(R.id.dialog_cancel_alles);
                        final String modDatum = allesArrayList.get(positionToRemove).getDatum();


                        modifyB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String modKosten = kosten.getText().toString();
                                String modBeschreibung = beschreibung.getText().toString();
                                String modArt = art.getText().toString();
                                //String modKostenArts= kostenartAlles.getText().toString();
                                String modOrt = ort.getText().toString();
                                String modAdresse = adresse.getText().toString();
                                String modPerson = person.getText().toString();

                                String modKostenArt = kostenart.getSelectedItem().toString();

                                Alles alles = new Alles(idDel, Integer.parseInt(modKosten), modBeschreibung, modDatum, modArt, modKostenArt, modOrt, modAdresse, modPerson );
                                dbHandler.modifyProduct(alles);

                                RefreshListviews();
                                dEdit.cancel();
                            }
                        });

                        cancelModifyB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dEdit.cancel();
                            }
                        });
                    }
                });

                deleB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHandler.deleteProduct(allesArrayList.get(positionToRemove).getID());
                        d.cancel();
                        RefreshListviews();
                    }
                });
                RefreshListviews();
            }
        });
    }
    public void RefreshListviews(){
        EmptyAllGrids();
        printDatabase();

        if (activeView == 1) {
            populateListView();
        }
        if (activeView == 2) {
            populateListView2();
        }
        if (activeView == 3) {
            populateListView3();
        }
        if (activeView == 4){
            populateListViewAlle();
        }
    }

    public int CheckKostenart(String s){
        int position = 0;
        switch (s) {
            case "Bargeld":
                break;
            case "Kreditkarte":
                position = 1;
                break;
            case "Bankomatkarte":
                position = 2;
                break;
            case "Scheck":
                position = 3;
                break;
            case "Essensmarken":
                position = 4;
                break;
            case "Gold":
                position = 5;
                break;
        }
        return position;
    }

    public void Button_AllClicked(View v) {
       /* for (int i = 1; i < 11; i++) {
            Alles alles = new Alles(i, String.valueOf(i));
            dbHandler.addProduct(alles);
        }*/
        EmptyAllGrids();
        //printDatabase();
        //populateListView();
        populateListViewAlle();
    }

    public void Button_KostenClicked(View v) {
        EmptyAllGrids();
        populateListView2();
    }

    public void Button_PurClicked(View v) {
        EmptyAllGrids();
        populateListView3();
    }

    private void EmptyAllGrids() {
        ListView gridVAll = (ListView) getView().findViewById(R.id.listView);
        ListView gridVKosten = (ListView) getView().findViewById(R.id.gridView_Kosten);
        ListView gridVPur = (ListView) getView().findViewById(R.id.gridView_Pur);

        gridVAll.setAdapter(null);
        gridVKosten.setAdapter(null);
        gridVPur.setAdapter(null);

        gridVAll.setVisibility(View.INVISIBLE);
        gridVKosten.setVisibility(View.INVISIBLE);
        gridVPur.setVisibility(View.INVISIBLE);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
