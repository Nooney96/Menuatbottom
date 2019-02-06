package rossnoonan.menuatbottom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainGraphActivityBills extends AppCompatActivity {
    private List<GraphBills> graphBillsList = new ArrayList<>();
    private RecyclerView gbrecyclerView;
    private GraphsBillAdapter gbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphbills_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        gbrecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        gbAdapter = new GraphsBillAdapter(graphBillsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        gbrecyclerView.setLayoutManager(mLayoutManager);
        gbrecyclerView.setItemAnimator(new DefaultItemAnimator());
        gbrecyclerView.setAdapter(gbAdapter);

        prepareGraphBillData();
    }

    private void prepareGraphBillData() {
        GraphBills graphBills = new GraphBills("Food", "£1.00", "20/09/2018");
        graphBillsList.add(graphBills);

        graphBills = new GraphBills("Electric Bill", "£10.00", "20/09/2018");
        graphBillsList.add(graphBills);

        graphBills = new GraphBills("Internet Bill", "£12.00", "20/09/2018");
        graphBillsList.add(graphBills);

        graphBills = new GraphBills("Makro", "£90.00", "20/09/2018");
        graphBillsList.add(graphBills);


        gbAdapter.notifyDataSetChanged();
    }
}