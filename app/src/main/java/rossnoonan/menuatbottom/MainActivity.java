package rossnoonan.menuatbottom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private GraphFragment graphFragment;
    private PaypalMoneyPoolFragment paypalMoneyPoolFragment;
    private DashboardFragment dashboardFragment;
    private WhatsappFragment whatsappFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mTextMessage = (TextView) findViewById(R.id.message);


        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        graphFragment = new GraphFragment();
        dashboardFragment = new DashboardFragment();
        paypalMoneyPoolFragment = new PaypalMoneyPoolFragment();
        whatsappFragment = new WhatsappFragment();

       // bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        //bottomNavigation.setAccentColor(fetchColor(R.color.AccentColor));
        //bottomNavigation.setInactiveColor(fetchColor(R.color.yourInactiveColor));

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        // mTextMessage.setText(R.string.title_dashboard);
                        setFragment(dashboardFragment);

                        return true;
                    case R.id.navigation_paypal:
                        // mTextMessage.setText(R.string.title_notifications);
                        setFragment(paypalMoneyPoolFragment);

                        return true;

                    default:
                        return false;

                }
            }
        });
    }
            private void setFragment(Fragment fragment) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.commit();


            }

            }







