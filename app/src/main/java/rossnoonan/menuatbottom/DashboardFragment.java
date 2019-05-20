package rossnoonan.menuatbottom;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import rossnoonan.menuatbottom.graphs.Main_graph_activity;
import rossnoonan.menuatbottom.groupandbills.Add_Group;
import rossnoonan.menuatbottom.main.SendBirdLoginActivity;



public class DashboardFragment extends Fragment {




    public DashboardFragment() {


        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Button btnOpenGroup = (Button) view.findViewById(R.id.btnCreateChat);
        btnOpenGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), SendBirdLoginActivity.class);
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
                Intent in = new Intent(getActivity(), Add_Group.class);
                startActivity(in);
            }
        });
        Button btnOpenGraphss = (Button) view.findViewById(R.id.btnGraphsNav);
        btnOpenGraphss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent in = new Intent(getActivity(), Main_graph_activity.class);
                startActivity(in);
            }
       });


        return view;
    }



    }


