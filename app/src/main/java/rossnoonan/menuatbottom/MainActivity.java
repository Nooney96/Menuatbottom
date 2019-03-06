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
    private NotifcationFragment notifcationFragment;
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
        notifcationFragment = new NotifcationFragment();
        whatsappFragment = new WhatsappFragment();

       // bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        //bottomNavigation.setAccentColor(fetchColor(R.color.AccentColor));
        //bottomNavigation.setInactiveColor(fetchColor(R.color.yourInactiveColor));

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // mTextMessage.setText(R.string.title_home);
                        setFragment(graphFragment);
                        return true;
                    case R.id.navigation_dashboard:
                        // mTextMessage.setText(R.string.title_dashboard);
                        setFragment(dashboardFragment);

                        return true;
                    case R.id.navigation_notifications:
                        // mTextMessage.setText(R.string.title_notifications);
                        setFragment(notifcationFragment);

                        return true;
                    case R.id.navigation_whatsapp:
                        // mTextMessage.setText(R.string.title_whatsapp);
                        setFragment(whatsappFragment);
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







