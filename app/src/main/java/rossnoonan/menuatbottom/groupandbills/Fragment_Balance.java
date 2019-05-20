package rossnoonan.menuatbottom.groupandbills;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rossnoonan.menuatbottom.R;

/**
 * show results of balanced amount to be shared between friends
 */
public class Fragment_Balance extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    String groupname;
    int nooffiends;
    SQLiteDatabase dbgroup=null;
    balance_adapter adapter;
    ArrayAdapter adapter2;
    ArrayList<balancedata> list_bal=new ArrayList<>();
    ArrayList<balancedata> list_items=new ArrayList<>();
    ListView list_a,list_b;
    ArrayList<String> list=new ArrayList<String>();
    ArrayList<String> list_ball=new ArrayList<String >();

    // newInstance constructor for creating fragment with arguments
    public static Fragment_Balance newInstance(int page, String title) {
        Fragment_Balance fragmentSecond = new Fragment_Balance();
        Bundle args = new Bundle();
        args.putInt("Int", page);
        args.putString("Title", title);
        Log.e("fragment balance","65");
        fragmentSecond.setArguments(args);
        return fragmentSecond;
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
        View view = inflater.inflate(R.layout.balance_fragment, container, false);
        List<calculate> list = new ArrayList<>();

        list_a=(ListView)view.findViewById(R.id.list_above);
        list_b=(ListView)view.findViewById(R.id.list_below);
        dbgroup=getActivity().openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
        Intent in=getActivity().getIntent();
        Bundle bundle=in.getExtras();
        groupname=bundle.getString("id");
        Cursor cursor=dbgroup.rawQuery("SELECT * FROM Group_details ORDER BY date_go DESC;",null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    String group_name=cursor.getString(cursor.getColumnIndex("group_name"));
                    if(group_name.matches(groupname)){
                        nooffiends=cursor.getInt(cursor.getColumnIndex("friend_no"));
                        break;
                    }

                }while(cursor.moveToNext());
            }
        }
        String table="f"+groupname;
        int i=0;
        //database query for getting friend name and amount from database
        cursor=dbgroup.rawQuery("SELECT * FROM "+table+";",null);
        if(cursor!=null){
            if (cursor.moveToFirst()) {
                do{
                    calculate assignment1 = new calculate();
                    assignment1.name = cursor.getString(cursor.getColumnIndex("friend"));
                    assignment1.money = cursor.getDouble(cursor.getColumnIndex("amount"));
                    list.add(assignment1);
                    i++;
                }while(cursor.moveToNext());
            }
        }

//avg = Total pf bills added up
        //used to calculate how much user is owed
        double avg=0.0;
        for(calculate balanceCaulate :list)
        {
            avg+=balanceCaulate.money;
        }
        avg=(1.0*avg)/nooffiends;
        DecimalFormat df = new DecimalFormat("####0.00");
        for(calculate balanceCaulate:list){
            balanceCaulate.money-=avg;
            String friendname=balanceCaulate.name;
            String friendmoney=df.format(balanceCaulate.money);
            list_items.add(new balancedata(friendname,friendmoney));
        }
        adapter= new balance_adapter(getActivity(),R.layout.balance_textview,list_items);
        list_a.setAdapter(adapter);

        for(calculate balancecalulate : list){
            Log.e(balancecalulate.name, String.valueOf(balancecalulate.money));
        }

        //update the second list for sorting out who owes who and how much money

        int listsize=list.size()-1;
        i=0;
        while(i<listsize){
            if (Math.abs(list.get(i).money)> Math.abs(list.get(listsize).money)){
                list_ball.add(list.get(i).name + " owes " + list.get(listsize).name + " :: £ " + df.format(Math.abs(list.get(listsize).money)));
                list.get(i).money += list.get(listsize).money;
                list.get(listsize).money = 0.0;
                listsize--;
            }
            else if(Math.abs(list.get(i).money)< Math.abs(list.get(listsize).money)){
                list_ball.add(list.get(i).name + " owes " + list.get(listsize).name + " :: £ " + df.format(Math.abs(list.get(i).money)));
                list.get(listsize).money += list.get(i).money;
                list.get(i).money = 0.0;
                i++;
            }
            else {
                list_ball.add(list.get(i).name + " owes " + list.get(listsize).name + " :: £ " + df.format(Math.abs(list.get(i).money)));
                list.get(i).money = 0.0;
                list.get(listsize).money = 0.0;
                i++;
                listsize--;
            }
        }
        //calling and setting adapters for screen
        adapter2=new ArrayAdapter<String>(getActivity(),R.layout.simple_text_list_test,list_ball);
        list_b.setAdapter(adapter2);



        return view;
    }


    public class calculate
    {
        String name;
        Double money;

    }

}
