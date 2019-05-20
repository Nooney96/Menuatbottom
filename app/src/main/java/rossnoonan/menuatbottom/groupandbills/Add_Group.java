package rossnoonan.menuatbottom.groupandbills;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import rossnoonan.menuatbottom.MainActivity;
import rossnoonan.menuatbottom.R;

public class Add_Group extends AppCompatActivity {

    private static Button btn_go;
    TextView t;

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
    List<additem_adapter_fragExspense> niitemlist;
    ArrayAdapter adapter1;
    ListView listView;
    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fillActivity();
        fb=(FloatingActionButton)findViewById(R.id.mynew);
        setTitle("Groups");
        opennewwindow();
        listView=(ListView) findViewById(R.id.list_add);

        //check this
        //onclick listener to open group selected from list view
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
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Add_Group.this, MainActivity.class));
        finish();

    }

    //function is used to fill the activity with group details from database create grouptwo.db
    void fillActivity() {
        dbgroup = openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
        niitemlist = new ArrayList<>();
        listView =(ListView) findViewById(R.id.list_add);
        dbgroup.execSQL("CREATE TABLE IF NOT EXISTS Group_details (id INTEGER PRIMARY KEY AUTOINCREMENT ,group_name TEXT NOT NULL, date_go DATE NOT NULL, " + "friend_no INTEGER NOT NULL DEFAULT 0)");
        Cursor c = dbgroup.rawQuery("SELECT * FROM Group_details ORDER BY date_go DESC;", null);

        if (c != null) {
            if (c.moveToFirst())

                do {

                    String dgroupname = c.getString(c.getColumnIndex("group_name"));
                    final Long dyear = c.getLong((c.getColumnIndex("date_go")));
                    String ddate = c.getString(c.getColumnIndex("date_go"));
                    String temp = dgroupname;
                    niitemlist.add(new additem_adapter_fragExspense(dgroupname, ddate));
                    vec.add(1);
                    j++;
                } while (c.moveToNext());

        }
        adapter = new add_adapter(getApplicationContext(), R.layout.add_text_groupinfo, niitemlist);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);


        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if (vec.get(i) == 0) {
                    list_item.remove(niitemlist.get(i));
                    count -= 1;
                    listView.getChildAt(i).setBackgroundColor(Color.WHITE);

                    vec.set(i, 1);
                    String ii = Integer.toString(i);
                    Log.e("not select", ii);

                    actionMode.setTitle(count + " items selected");
                } else {
                    count += 1;
                    listView.getChildAt(i).setBackgroundColor(Color.LTGRAY);

                    vec.set(i, 0);
                    String ii = Integer.toString(i);
                    Log.e("select", ii);


                    actionMode.setTitle(count + " items selected");
                }
            }

            //Method for using context menu to delete group from database
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.delete_menu, menu);

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
                    //used for delete menu
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
                                dbgroup.execSQL("DELETE FROM " + TABLE_NAME + " WHERE group_name='" + name + "';");
                                String tbl = "f" + name;
                                dbgroup.execSQL("DROP TABLE IF EXISTS'" + tbl + "';");
                                dbgroup.execSQL("DROP TABLE IF EXISTS'" + name + "';");
                                niitemlist.remove(i);
                            }
                        }
                        //called to update adapter of changes
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
    //when user clicks on the floating action button opens AddGroupdetails
    public void opennewwindow(){
        fb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Opens new window for intent to Add_Group_Details Two
                        Intent intent= new Intent(Add_Group.this, Add_Group_Details.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
    //onback press method for user to get back to home screen with backpress
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
