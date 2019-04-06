package rossnoonan.menuatbottom.graphs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import rossnoonan.menuatbottom.R;
import rossnoonan.menuatbottom.groupandbills.add_adapter;
import rossnoonan.menuatbottom.groupandbills.additem;
import rossnoonan.menuatbottom.groupandbills.balance_adapter;
//import rossnoonan.testtwentytwo.graph_adapter;

public class Main_graph_activity extends AppCompatActivity {

    PieChart pieChart;

    Long k;
    int j;
    FloatingActionButton fb;
    String j1;
    TextView noDet;
    SQLiteDatabase dbgroup=null;
    FloatingActionButton f;
    ArrayList<String> notes = new ArrayList<String>();
    ArrayList<String> list_item = new ArrayList<>();
    Vector<Integer> vector = new Vector<>();
    Vector<Integer> vec = new Vector<>();
    int count=0;
    add_adapter adapter;
    //graph_adapter adaptergraphdata;
    balance_adapter adapter_balance;
    List<additem> niitemlist;
    ArrayAdapter adapter1;
    ListView listView;
    ListView listView1;
    private ArrayList<PieEntry> yValues;
    String p;

    //this is to check the logs for hidden information
    // Log.d("ROSS", "D4:"+ d4 + "--- " + d1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_graph_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fillActivity();
        setTitle("Graphs");

        listView=(ListView) findViewById(R.id.list_below_2);

        pieChart = (PieChart) findViewById(R.id.piechart);
        Legend legend = pieChart.getLegend();
        legend.setTextColor(Color.WHITE);

        legend.setTextSize(15f);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.15f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(50f);

        yValues = new ArrayList<>();
        //  yValues.add(new PieEntry(66, "bill"));
        //yValues.add(new PieEntry(45, "sam"));
        // yValues.add(new PieEntry(54, "julie"));

        plotter();

        // Description description = new Description();
        //description.setText(" This graph shows all bills");
        //description.setTextSize(15);
        //pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, ": Names");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);


        PieData data = new PieData((dataSet));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);


        pieChart.setData(data);

        //this is code to click into different groups
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView temp = (TextView) view.findViewById(R.id.name);
                        String str = temp.getText().toString();
                        Intent in = new Intent(getApplicationContext(), Main_graph_activity.class);
                        in.putExtra("id", str);
                        startActivity(in);
                        finish();

                    }
                }
        );



    }

    //method to populate graph
    public void plotter() {


        dbgroup=openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
        dbgroup.execSQL("create table if not exists friend " + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,friend_name TEXT,note TEXT,amount TEXT)");
        Cursor c = dbgroup.rawQuery("SELECT * FROM testerkings" + ";", null);
        if (c != null) {
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    //create list view from table group_name
                    String d2 = c.getString(c.getColumnIndex("friend_name"));
                    String d1 = c.getString(c.getColumnIndex("note"));
                    String d4 = c.getString(c.getColumnIndex("amount"));
                    vec.add(1);
                   // Log.d("ROSS", "*"+ ";");
                    Log.d("ROSS", "D4:"+ d4 + "--- " + d1);
                    yValues.add(new PieEntry(Integer.parseInt(d4), d1));

                    //add to chart...

                } while (c.moveToNext());

            }

            pieChart.getData();
        }
    }


    //function to fill up the activitys list view to show groups to click
    void fillActivity() {
        dbgroup = openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
        niitemlist = new ArrayList<>();
        listView =(ListView) findViewById(R.id.list_below_2);
        dbgroup.execSQL("CREATE TABLE IF NOT EXISTS Group_details (id INTEGER PRIMARY KEY AUTOINCREMENT ,group_name TEXT NOT NULL, date_go DATE NOT NULL, " + "friend_no INTEGER NOT NULL DEFAULT 0)");
        Cursor c = dbgroup.rawQuery("SELECT * FROM Group_details ORDER BY date_go DESC;", null);
        if (c != null) {
            if (c.moveToFirst())

                do {

                    String d4 = c.getString(c.getColumnIndex("group_name"));
                    final Long d1 = c.getLong((c.getColumnIndex("date_go")));
                    String d2 = c.getString(c.getColumnIndex("date_go"));
                    String temp = d4;
                    niitemlist.add(new additem(d4, d2));
                    vec.add(1);
                    j++;
                } while (c.moveToNext());

        }
        adapter = new add_adapter(getApplicationContext(), R.layout.add_text, niitemlist);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

    }












    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
