package rossnoonan.menuatbottom;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {




    public DashboardFragment() {


        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Button btnOpenGroup = (Button) view.findViewById(R.id.btnCreateGroup);
        btnOpenGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainGroupActivity.class);
                startActivity(in);
            }
        });
        Button btnOpenAbout = (Button) view.findViewById(R.id.btnAboutUs);
        btnOpenAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(in);
            }
        });

        Button btnOpenBills = (Button) view.findViewById(R.id.btnCreateBill);
        btnOpenBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainBillActivity.class);
                startActivity(in);
            }
        });
        Button btnOpenGraphss = (Button) view.findViewById(R.id.btnGraphsNav);
        btnOpenGraphss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainGraphActivityBills.class);
                startActivity(in);
            }
        });


        return view;



    }




    }


