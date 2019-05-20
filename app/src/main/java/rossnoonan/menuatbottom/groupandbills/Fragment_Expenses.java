package rossnoonan.menuatbottom.groupandbills;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import rossnoonan.menuatbottom.R;

public class Fragment_Expenses extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    String groupname;
    private ListView nlist;
    private adapter_Expenses adapter;
    private List<item> niitemlist;
    ArrayList<item> list_items= new ArrayList<>();
    Vector<Integer> vec =new Vector<>();

    int flag=0;
    SQLiteDatabase dbgroup=null;


    Button btn;
    int count=0;
    FloatingActionButton f;

    // new Instance constructor for creating fragment with arguments
    public static Fragment_Expenses newInstance(int page, String title) {
        Fragment_Expenses fragmentFirst = new Fragment_Expenses();
        Bundle args = new Bundle();
        Log.e("fragment show","1111111111111");
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.expenses_fragment, container, false);

        dbgroup = getActivity().openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
        Intent in = getActivity().getIntent();
        Bundle bundle = in.getExtras();
        groupname = bundle.getString("id");
        f = (FloatingActionButton) view.findViewById(R.id.myFAB);
        f.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Add_bill.class);
                        intent.putExtra("id", groupname);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
        );

// it is this code i need review at a later stage for improvements
        nlist = (ListView) view.findViewById(R.id.list_view);
        niitemlist = new ArrayList<>();
        int x=0;
        dbgroup.execSQL("create table if not exists " + groupname + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,friend_name TEXT,note TEXT,amount TEXT)");
        Cursor cursor = dbgroup.rawQuery("SELECT * FROM " + groupname + ";", null);
        if (cursor != null) {
            int i = 0;
            if (cursor.moveToFirst()) {
                do {
                    //create list view from table group_name
                    String friendname = cursor.getString(cursor.getColumnIndex("friend_name"));
                    String noteofbill = cursor.getString(cursor.getColumnIndex("note"));
                    String billamount = cursor.getString(cursor.getColumnIndex("amount"));
                    vec.add(1);
                    niitemlist.add(new item(noteofbill,billamount,friendname));

                    //add to chart...

                } while (cursor.moveToNext());
            }
        }
        try {
            //adapter=new adapter_Expenses(Fragment_Expenses.this,niitemlist);
            adapter =new adapter_Expenses(getActivity(),R.layout.textfile_bill,niitemlist);

            nlist.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            System.out.println(e);

        }

        nlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        nlist.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(vec.get(i)==0){
                    list_items.remove(niitemlist.get(i));
                    count -= 1;
                    nlist.getChildAt(i).setBackgroundColor(Color.WHITE);

                    vec.set(i,1);

                    String ii= Integer.toString(i);
                    Log.e("not select",ii);

                    actionMode.setTitle(count + " items selected");
                } else {
                    count += 1;
                    nlist.getChildAt(i).setBackgroundColor(Color.LTGRAY);

                    vec.set(i,0);
                    String ii= Integer.toString(i);
                    Log.e("select",ii);
                    actionMode.setTitle(count + " items selected");
                }

            }
            //Method for using context menu to delete bill from database
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

            //delete menu called to delete data
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                Log.e("1","enter switch");

                switch (menuItem.getItemId()) {
                    case R.id.delete_id:
                        for (int i=vec.size()-1;i>-1;i--) {

                            if (vec.get(i) == 0) {
                                TextView t = (TextView) view.findViewById(R.id.name);
                                String name=niitemlist.get(i).getName();
                                String amount=niitemlist.get(i).getAmount();
                                String note=niitemlist.get(i).getNote();

                                vec.set(i,1);
                                nlist.getChildAt(i).setBackgroundColor(Color.WHITE);
                                final String TABLE_NAME =groupname;
                                final String NOTE="note";
                                final String FRIEND = "friend_name";
                                Log.e("table name",TABLE_NAME);


                                dbgroup.delete(TABLE_NAME,
                                        FRIEND + " = ? AND " + NOTE + " = ?",
                                        new String[] {name, note+""});
                                //update f
                                String tb="f"+groupname;
                                //Log.e("sdhjbs",name);
                                Cursor cursor3 = dbgroup.rawQuery("SELECT * FROM " + tb + ";", null);
                                //tb is f + groupname
                                //f = floating action button
                                if (cursor3 != null) {
                                    if (cursor3.moveToFirst()) {
                                        do {
                                            String ch = cursor3.getString(cursor3.getColumnIndex("friend"));
                                            Log.e("friend name",ch);
                                            if (ch.matches(name)) {
                                                String temp = cursor3.getString(cursor3.getColumnIndex("amount"));
                                                int index = cursor3.getInt(cursor3.getColumnIndex("ID"));
                                                int billamount = Integer.parseInt(temp);
                                                int money2 = Integer.parseInt(amount);
                                                int money= billamount - money2;
                                                ContentValues cv=new ContentValues();
                                                cv.put("amount",money);
                                                cv.put("friend",name);
                                                dbgroup.update(tb,cv, "ID="+index, null);
                                                temp =cursor3.getString(cursor3.getColumnIndex("amount"));
                                                break;
                                            }

                                        } while (cursor3.moveToNext());
                                    }
                                }

                                niitemlist.remove(i);
                            }
                            //changing adapter on change
                            adapter.notifyDataSetChanged();
                            flag=1;
                            start();
                        }
                        //putting use back to activity with toast message
                        Toast.makeText(getActivity(), count + " items removed ", Toast.LENGTH_SHORT).show();
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

        return view;
    }
    //start function
    public void start(){
        Intent same=new Intent(getActivity(),ViewBillDetails.class);
        same.putExtra("id",groupname);
        startActivity(same);
        getActivity().finish();
    }
}




