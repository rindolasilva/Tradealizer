package com.tradealizer.blabla;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;

//import java.sql.Date;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.StreamHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment {


    public GraphFragment() {
        // Required empty public constructor
    }
AllesDBHandler dbhandler;
Button Bar_Chart_Button;
    //BarGraphSeries<DataPoint> series;
    SQLiteDatabase sqLiteDatabase;

   GraphView graph;

LineGraphSeries<DataPoint> series;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        dbhandler = new AllesDBHandler(getActivity(),null,null,1);

        sqLiteDatabase = dbhandler.getReadableDatabase();

        Bar_Chart_Button = (Button) v.findViewById(R.id.Bar_Chart);

        graph = (GraphView) v.findViewById(R.id.graph);






       //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));


        Bar_Chart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Graphmaker();

            }
        });

        return v;
    }

    public void Graphmaker(){

      /*  LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
*/
        //series = new BarGraphSeries<DataPoint>(getData());
        series = new LineGraphSeries<DataPoint>(getData());
        //series.setSpacing(50);

      //  graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity())); // Damit x-Achse Datum versteht
        //graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        //graph.setHorizontalScrollBarEnabled(true);

        //graph.getGridLabelRenderer().setHumanRounding(false);

        //graph.getViewport().setMinY(0);
        graph.getViewport().setScrollable(true);


        graph.addSeries(series);
    }



    private DataPoint[] getData() {

        String[] columns = new String[]{AllesDBHandler.COLUMN_Datum, AllesDBHandler.COLUMN_Kosten};
        Cursor cursor = sqLiteDatabase.query("alles",columns,null,null,null,null,null,null);

        DataPoint[] dp = new DataPoint[cursor.getCount()];
        cursor.moveToFirst();

        int sum = 0;
        for (int i=0; i<cursor.getCount(); i++)
        {
            sum +=  cursor.getInt(1);
            String[] sep = cursor.getString(0).split("-");
           Date d = new Date(Integer.parseInt(sep[0])-1900,Integer.parseInt(sep[1])-1,Integer.parseInt(sep[2])); // -1900 und -1 wegen daemlichem Konstruktor

            //dp[i]=new DataPoint(d, cursor.getInt(1) );
            dp[i]= new DataPoint(i,sum);
            cursor.moveToNext();
        }


return dp;

    }

}
