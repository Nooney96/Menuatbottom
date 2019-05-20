package rossnoonan.menuatbottom.groupandbills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import rossnoonan.menuatbottom.R;

/*
 * main class for fragments expenses_fragment and balance_fragment
 * */

public class ViewBillDetails extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    String groupname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent in=getIntent();
        Bundle bundle=in.getExtras();
        groupname=bundle.getString("id");
        setTitle(groupname);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new ViewBillDetails.MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);


    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    Fragment fragmentexpenses =new Fragment_Expenses();
                    return Fragment_Expenses.newInstance(0, null);
                case 1:
                    Fragment fragmentbalance=new Fragment_Balance();
                    return Fragment_Balance.newInstance(1,null);


                default:
                    return null;
            }
        }

        // Returns the indicated page title for the top indicator in selection
        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "EXPENSES";
            else if(position==1) return "BALANCES";
            return null;
        }

    }
    //on a back press method for user to get back to home screen with  a backpress
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent in=new Intent(getApplicationContext(), Add_Group.class);
        in.putExtra("id",groupname);
        startActivity(in);
        finish();

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
