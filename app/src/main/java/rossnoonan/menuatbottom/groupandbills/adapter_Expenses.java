package rossnoonan.menuatbottom.groupandbills;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rossnoonan.menuatbottom.R;

//Adapter class for Fragment_Exspenses and exspenses fragment
public class adapter_Expenses extends ArrayAdapter<item> {
    private SparseBooleanArray mSelectedItemsIds;
    private LayoutInflater inflater;
    private Context mContext;
    private List<item> list;

    //Starting inflater for activity
    public adapter_Expenses(Context context, int resourceId, List<item> list) {
        super(context, resourceId, list);
        mSelectedItemsIds = new SparseBooleanArray();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    private static class ViewHolder {
        TextView name;
        TextView note;
        TextView amount;
    }
//Made public so it could be called to the ViewBillDetails
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.textfile_bill, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.note = (TextView) view.findViewById(R.id.note);
            holder.amount = (TextView) view.findViewById(R.id.amount);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.note.setText(list.get(position).getNote());
        holder.amount.setText(list.get(position).getAmount());
        holder.name.setText(list.get(position).getName());
        return view;
    }
//function that is used with the delete menu
    @Override
    public void remove(item remitm) {
        list.remove(remitm);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
