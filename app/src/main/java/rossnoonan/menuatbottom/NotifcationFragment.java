package rossnoonan.menuatbottom;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifcationFragment extends Fragment  {


    public NotifcationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifcation, container, false);


    }

    //to open Paypal Money pool link
    public void philip (View view){
        Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/signin?returnUri=https%3A%2F%2Fwww.paypal.com%2Fpools&state=%2F"));
        startActivity(browserIntent);

    }

}
