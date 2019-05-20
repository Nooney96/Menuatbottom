package rossnoonan.menuatbottom.groupandbills;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import rossnoonan.menuatbottom.R;

public class Add_bill extends AppCompatActivity {

    Spinner spBusinessType;
    ArrayAdapter<String> adapterBusinessType;
    ArrayList<String> names = new ArrayList<String>();
    int flag=0;
    EditText title,amount;
    Button btn_add;
    String groupname;
    SQLiteDatabase dbgroup=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        dbgroup=openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
        Intent in=getIntent();
        Bundle bundle=in.getExtras();
        groupname=bundle.getString("id");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New expense");
        final String group="f"+groupname;
        Cursor cursor=dbgroup.rawQuery("SELECT * FROM "+group+";",null);
        if(cursor!=null) {
            int i=0;
            if(cursor.moveToFirst()){
                do{
                    String friendname=cursor.getString(cursor.getColumnIndex("friend"));
                    names.add(friendname);
                    i++;
                }while(cursor.moveToNext());
            }
        }
        //used for selecting name for bill
        spBusinessType = (Spinner) findViewById(R.id.spBussinessType);
        adapterBusinessType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,names);
        adapterBusinessType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spBusinessType.setAdapter(adapterBusinessType);
        title=(EditText)findViewById(R.id.editTitle);
        amount=(EditText)findViewById(R.id.editAmount);
        btn_add=(Button)findViewById(R.id.button_ok);
        OnClickButtonAdd();
    }
    //onback press method for user to get back to home screen with backpress
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),ViewBillDetails.class);
        intent.putExtra("id",groupname);
        startActivity(intent);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//function when clicked will add information to database
    public void OnClickButtonAdd(){
        final String selected="friend"+groupname;
        btn_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbgroup.execSQL("create table if not exists " + groupname+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,friend_name TEXT,note TEXT,amount TEXT)");
                        String COL_2="friend_name";
                        String COL_3="note";
                        String COL_4="amount";
                        String note=title.getText().toString();
                        String amountbill=amount.getText().toString();
                        String nameselected = spBusinessType.getSelectedItem().toString();
                        String table=groupname;

                        //EXCEPTION
                        try {
                            if (note.matches("") || amountbill.matches(""))
                                throw new ArithmeticException("string");

                            //check if the note entered is previously entered in the table
                            Cursor c1 = dbgroup.rawQuery("SELECT * FROM '" + groupname + "';", null);
                            if (c1 != null) {
                                Log.e("c1!=null", "ppppppppppp");
                                if (c1.moveToFirst()) {
                                    do {
                                        if (note.matches(c1.getString(c1.getColumnIndex("note"))) && nameselected.matches(c1.getString(c1.getColumnIndex("friend_name")))) {

                                            //update the value
                                            int a = Integer.parseInt(c1.getString(c1.getColumnIndex("amount")));
                                            a += Integer.parseInt(amountbill);
                                            String amount = String.valueOf(a);
                                            int index=c1.getInt(c1.getColumnIndex("ID"));
                                            dbgroup.execSQL("UPDATE '" + groupname + "' SET amount = '" + a + "' WHERE ID ='" + index + "';");
                                            flag = 1;

                                            Log.e(note, String.valueOf(a));
                                            break;
                                        }
                                    } while (c1.moveToNext());
                                }
                            }


                            if (flag == 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(COL_2, nameselected);
                                contentValues.put(COL_3, note);
                                contentValues.put(COL_4, amountbill);
                                long result = dbgroup.insert(table, null, contentValues);
                                if (result != -1) {
                                   //do
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"error data inserting", Toast.LENGTH_SHORT).show();
                                }
                            }
                            flag = 0;

                            //update database

                            String tb = "f" + groupname;

                            Cursor c = dbgroup.rawQuery("SELECT * FROM  " + tb + " ;", null);
                            if (c != null) {
                                if (c.moveToFirst()) {
                                    do {

                                        String ch = c.getString(c.getColumnIndex("friend"));
                                        if (ch.matches(nameselected)) {

                                            String temp = c.getString(c.getColumnIndex("amount"));
                                            int index = c.getInt(c.getColumnIndex("ID"));
                                            int amount = Integer.parseInt(temp);
                                            int money2 = Integer.parseInt(amountbill);
                                            int money = amount + money2;
                                            Log.e("money1", String.valueOf(amount));
                                            Log.e("money2", String.valueOf(money2));
                                            Log.e("amountbill", String.valueOf(money));

                                            dbgroup.execSQL("UPDATE '" + tb + "' SET amount = '" + money + "' WHERE ID ='" + index + "';");

                                            int x=c.getInt(c.getColumnIndex("amount"));
                                            Log.e("updated amount:::::::::", String.valueOf(x));
                                            c.moveToLast();
                                        }
                                    } while (c.moveToNext());
                                }
                            }


                            //update
                            Toast.makeText(getApplicationContext(), "Bill details updated!", Toast.LENGTH_SHORT).show();
                            Intent newscreen = new Intent(getApplicationContext(), ViewBillDetails.class);
                            newscreen.putExtra("id", groupname);
                            startActivity(newscreen);
                            finish();
                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"EMPTY FIELDS", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

        );
        flag=0;
    }
}
