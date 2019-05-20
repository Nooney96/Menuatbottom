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


//adapter class for Add_Group


public class add_adapter extends ArrayAdapter<additem_adapter_fragExspense> {
    private SparseBooleanArray mSelectedItemsIds;
    private LayoutInflater inflater;
    private Context mContext;
    private List<additem_adapter_fragExspense> list;

    //Starting inflater for activity
    public add_adapter(Context context, int resourceId, List<additem_adapter_fragExspense> list) {
        super(context, resourceId, list);
        mSelectedItemsIds = new SparseBooleanArray();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    private static class ViewHolder {
        TextView date;
        TextView name;
    }
    //Made public so it could be called to the Add_Group
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.add_text_groupinfo, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.date = (TextView) view.findViewById(R.id.date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.date.setText(list.get(position).getDate());
        holder.name.setText(list.get(position).getName());
        return view;
    }

    @Override
    public void remove(additem_adapter_fragExspense remitm) {
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
