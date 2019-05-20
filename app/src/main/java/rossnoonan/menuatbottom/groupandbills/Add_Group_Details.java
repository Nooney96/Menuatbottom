package rossnoonan.menuatbottom.groupandbills;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import rossnoonan.menuatbottom.R;

public class Add_Group_Details extends AppCompatActivity {

    private static Button btn;
    private static Button b1;
    private static Button btnadd;
    public DatePickerDialog datepick = null;
    SQLiteDatabase dbgroup=null;
    EditText editgroupname,editnumber;
    Editable dgroupname,dnumberofpeople=null;
    TextView edselectdated=null;
    TextView edtxt;
    EditText f_add;
    EditText numofpeople;
    int check=0;
    ArrayList<String> fname= new ArrayList<String>();
    int size=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn = (Button) findViewById(R.id.button_SUBMIT);
        edtxt = (TextView) findViewById(R.id.daye);
        btnadd = (Button) findViewById(R.id.add_btn);
        b1 = (Button) findViewById(R.id.daypickbut);
        f_add = (EditText) findViewById(R.id.friend_name);
        numofpeople = (EditText) findViewById(R.id.editno);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        OnClickpickDate();
        OnClickButtonSubmit();
        setTitle("New Group");
        OnClickButtonAddFriend();
    }
    //onback press method for user to get back to home screen with backpress
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Add_Group_Details.this, Add_Group.class));
        finish();
    }
    //function for date of group created
    public void OnClickpickDate(){
        try{
            b1.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    datepick = new DatePickerDialog(v.getContext(), (DatePickerDialog.OnDateSetListener) new DatePickHandler(), Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                    datepick.show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
        }
    }
    public class DatePickHandler implements DatePickerDialog.OnDateSetListener {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            int months = month+1;
            if((months<10)&&(day<10))
                edtxt.setText(day + "-0" + (months) + "-0" + year);
            else if((months<10)&&(day>10))
                edtxt.setText(day + "-0" + (months) + "-" + year);
            else if((months>10)&&(day<10))
                edtxt.setText(day + "-" + (months) + "-0" + year);
            else
                edtxt.setText(day + "-" + (months) + "-" + year);
            datepick.hide();
        }
    }
//function to submit all data to database
    void OnClickButtonSubmit() {
        editgroupname = (EditText) findViewById(R.id.Textgroup);
        editnumber = (EditText) findViewById(R.id.editno);
        edselectdated = (TextView) findViewById(R.id.daye);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //opening or creating database to add information to group
                        dbgroup = openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
                        dgroupname = editgroupname.getText();
                        dnumberofpeople = editnumber.getText();
                        String ddate = edselectdated.getText().toString();
                        String groupname = dgroupname.toString().toLowerCase();
                        String numberofpeople = dnumberofpeople.toString();
                        groupname = groupname.trim();
                        numberofpeople = numberofpeople.trim();

                        try {
                            dbgroup.execSQL("CREATE TABLE IF NOT EXISTS Group_details (id INTERGER PRIMARY KEY AUTOINCREMENT ,group_name TEXT NOT NULL, date_go DATE NOT NULL," + " friend_no INTEGER NOT NULL DEFAULT 0);");
                            String COL_2 = "group_name";
                            String COL_3 = "date_go";
                            String COL_4 = "friend_no";
                            String table = "Group_details";
                            //check if any of the fields is not empty or friend no is not equal to zero
                            if (groupname.matches("") || numberofpeople.matches("") || ddate.matches("") || Integer.parseInt(numberofpeople) == 0)
                                throw new ArithmeticException("Needs More Details..\n Please Enter Again");
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COL_2, dgroupname.toString().toLowerCase());
                            contentValues.put(COL_3, ddate);
                            contentValues.put(COL_4, Integer.parseInt(numofpeople.getText().toString()));
                            int x = Integer.parseInt(numofpeople.getText().toString());

                            try {
                                Cursor cursor = dbgroup.rawQuery("SELECT * FROM Group_details ORDER BY date_go DESC;", null);
                                if (cursor != null) {
                                    int i = 0;
                                    if (cursor.moveToFirst()) {
                                        do {
                                            String compare = cursor.getString(cursor.getColumnIndex("group_name"));
                                            if (compare.matches(editgroupname.getText().toString().toLowerCase()))
                                                throw new ArithmeticException("Welcome");

                                        } while (cursor.moveToNext());
                                    }

                                }
                                //exceptions for errors
                                try {
                                    if (check == Integer.parseInt(numofpeople.getText().toString())) {
                                        long result = dbgroup.insert(table, null, contentValues);

                                        if (result != -1) {
                                            Toast.makeText(getApplicationContext(), "Group Added", Toast.LENGTH_SHORT).show();
                                            Intent lis = new Intent(getApplicationContext(), Add_Group.class);
                                            startActivity(lis);
                                            finish();
                                        } else
                                            throw new ArithmeticException("Needs More Details..\n Please Enter Again");

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please Add More Friends", Toast.LENGTH_SHORT).show();

                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Needs More Details..\n Please Enter Again", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "The Group Name Already Added", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Needs More Details..\n Please Enter Again", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    public void OnClickButtonAddFriend(){
        btnadd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String z = numofpeople.getText().toString().trim();
                            if (z.matches(""))
                                throw new ArithmeticException("hello");

                            int x = Integer.parseInt(numofpeople.getText().toString());

                            if (check < x) {
                                dbgroup = openOrCreateDatabase("grouptwo.db", Context.MODE_PRIVATE, null);
                                String TABLE_NAME;
                                editgroupname = (EditText) findViewById(R.id.Textgroup);
                                TABLE_NAME = "f" + editgroupname.getText().toString().toLowerCase();
                                try {
                                    Cursor c = dbgroup.rawQuery("SELECT * FROM Group_details ORDER BY date_go DESC;", null);
                                    if (c !=null) {
                                        int i = 0;
                                        if (c.moveToFirst()) {
                                            do {
                                                String compare = c.getString(c.getColumnIndex("group_name"));
                                                if (compare.matches(editgroupname.getText().toString().toLowerCase()))
                                                    throw new ArithmeticException("HELLO");

                                            } while (c.moveToNext());
                                        }
                                    }
                                    dbgroup.execSQL("create table if not exists " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,friend TEXT,amount INTEGER NOT NULL DEFAULT 0)");
                                    ContentValues contentValues = new ContentValues();
                                    String COL_2;
                                    COL_2 = "friend";
                                    //exception
                                    try {
                                        String y = f_add.getText().toString();
                                        y=y.toLowerCase();
                                        int flag1=0;
                                        if (y.matches(""))
                                            throw new ArithmeticException("Needs More Details..\n Please Enter Again");

                                        //check if friend name is already included in the list
                                        for(int i=0;i<fname.size();i++){
                                            if(y.matches(fname.get(i))){
                                                Toast.makeText(getApplicationContext(),y+" already included", Toast.LENGTH_SHORT).show();
                                                flag1=1;
                                                break;
                                            }
                                        }
                                        if(flag1==0) {
                                            contentValues.put(COL_2, y);
                                            fname.add(y);
                                            size++;
                                            long result = dbgroup.insert(TABLE_NAME, null, contentValues);
                                            check++;
                                        }
                                        f_add.setText("");
                                        //Exception to catch an errors users may have caused
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Please Enter Friend Name", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {

                                    System.out.print(e.toString());
                                    Toast.makeText(getApplicationContext(), "Group Has Already Been Added", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Your Friend Limit Exceeded", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(), "Needs More Details..\n Please Enter Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    //for back press to bring user back to home screen
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
