package rossnoonan.menuatbottom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GraphsBillAdapter extends RecyclerView.Adapter<GraphsBillAdapter.MyViewHolder> {

        private List<GraphBills> graphbillList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
                public TextView titlebillg, date, price;

                public MyViewHolder(View view) {
                        super(view);
                        titlebillg = (TextView) view.findViewById(R.id.titlebillg);
                        price = (TextView) view.findViewById(R.id.price);
                        date = (TextView) view.findViewById(R.id.date);
                }
        }


        public GraphsBillAdapter(List<GraphBills> graphbillList) {
                this.graphbillList = graphbillList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.graphbills_list_row, parent, false);

                return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
                GraphBills graphBills = graphbillList.get(position);
                holder.titlebillg.setText(graphBills.getTitlebillg());
                holder.price.setText(graphBills.getPrice());
                holder.date.setText(graphBills.getDate());
        }

        @Override
        public int getItemCount() {
                return graphbillList.size();
        }
}