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
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import rossnoonan.menuatbottom.groupandbills.ViewBillDetails;
import rossnoonan.menuatbottom.groupandbills.add_adapter;
import rossnoonan.menuatbottom.groupandbills.additem;

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
    List<additem> niitemlist;
    ArrayAdapter adapter1;
    ListView listView;
    ListView listView1;

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
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(34f, "bang"));
        yValues.add(new PieEntry(23f, "USA"));
        yValues.add(new PieEntry(14f, "UK"));
        yValues.add(new PieEntry(30, "India"));
        yValues.add(new PieEntry(40, "Russia"));
        yValues.add(new PieEntry(23, "Japan"));

       // Description description = new Description();
        //description.setText(" This graph shows all bills");
        //description.setTextSize(15);
        //pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, ": Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        //this is code to click into different groups
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView temp = (TextView) view.findViewById(R.id.name);
                        String str = temp.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), ViewBillDetails.class);
                        intent.putExtra("id", str);
                        startActivity(intent);
                        finish();
                    }
                }
        );








    }
    //function to fill up the activity with trip details from data base create trip.db
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

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if (vec.get(i) == 0) {
                    // if(list_items.contains(niitemlist.get(i))) {
                    list_item.remove(niitemlist.get(i));
                    count -= 1;
                    listView.getChildAt(i).setBackgroundColor(Color.WHITE);

                    vec.set(i, 1);
                    //c.setSelected(true);
                    //convertView.setPressed(true);
                    String ii = Integer.toString(i);
                    Log.e("not select", ii);

                    actionMode.setTitle(count + " items selected");
                } else {
                    count += 1;
                    listView.getChildAt(i).setBackgroundColor(Color.LTGRAY);

                    vec.set(i, 0);
                    String ii = Integer.toString(i);
                    Log.e("select", ii);
                    //   nlist.add(niitemlist.get(i));

                    actionMode.setTitle(count + " items selected");
                    // list_items.add(niitemlist.get(i));
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.my_context_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                Log.e("1", "enter switch");

                switch (menuItem.getItemId()) {
                    case R.id.delete_id:
                        for (int i = vec.size() - 1; i > -1; i--) {
                            if (vec.get(i) == 0) {
                                String name = niitemlist.get(i).getName();
                                String amount = niitemlist.get(i).getDate();
                                vec.set(i, 1);
                                listView.getChildAt(i).setBackgroundColor(Color.WHITE);
                                final String TABLE_NAME = "Group_details";
                                final String NOTE = "note";
                                Log.e("table name", TABLE_NAME);
                                dbgroup.execSQL("DELETE FROM" + TABLE_NAME + "WHERE group_name='" + name + "'");
                                String tbl = "f" + name;
                                dbgroup.execSQL("DROP TABLE IF EXISTS'" + tbl + "';");
                                dbgroup.execSQL("DROP TABLE IF EXISTS'" + name + "';");
                                niitemlist.remove(i);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), count + " items removed ", Toast.LENGTH_SHORT).show();
                        count = 0;
                        actionMode.finish();
                        return true;
                    default:
                        return false;

                }


            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
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

