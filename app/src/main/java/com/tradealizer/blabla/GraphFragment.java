package com.tradealizer.blabla;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment {


    public GraphFragment() {
        // Required empty public constructor
    }
AllesDBHandler dbhandler;
Button Line_Chart;
    BarGraphSeries<DataPoint> series;
    SQLiteDatabase sqLiteDatabase;
    GraphView graph;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        dbhandler = new AllesDBHandler(getActivity(),null,null,1);

        sqLiteDatabase = dbhandler.getReadableDatabase();

        Line_Chart = (Button) v.findViewById(R.id.Line_Chart);

        graph = (GraphView) v.findViewById(R.id.graph);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        Line_Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Graphmaker();

            }
        });

        return v;
    }

    public void Graphmaker(){

       /* LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        */

         series = new BarGraphSeries<DataPoint>(getData());

        graph.addSeries(series);



    }


    private DataPoint[] getData() {
        String[] columns = new String[]{AllesDBHandler.COLUMN_Datum, AllesDBHandler.COLUMN_Kosten};
        Cursor cursor = sqLiteDatabase.query("alles",columns,null,null,null,null,null);

        DataPoint[] dp = new DataPoint[cursor.getCount()];

        for (int i=0; i<cursor.getCount(); i++)
        {
            cursor.moveToNext();
            dp[i]=new DataPoint(cursor.getInt(0),cursor.getInt(1));
        }

return dp;

    }

}
