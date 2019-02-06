package rossnoonan.menuatbottom.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rossnoonan.menuatbottom.MainBillActivity;
import rossnoonan.menuatbottom.Model.ToDo;
import rossnoonan.menuatbottom.R;

class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
{
ItemClickListener itemClickListener;
TextView item_title,item_description;

    public ListItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        item_title = (TextView) itemView.findViewById(R.id.item_title);
        item_description = (TextView) itemView.findViewById(R.id.item_description);
    }

    public void  setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select the action");
        contextMenu.add(0,0,getAdapterPosition(),"DELETE");

    }
}

public class ListItemAdapter extends RecyclerView.Adapter<ListItemViewHolder>  {

    MainBillActivity mainBillActivity;
    List<ToDo> todoList;

    public ListItemAdapter(MainBillActivity mainBillActivity, List<ToDo> toDoList){

        this.mainBillActivity = mainBillActivity;
        this.todoList = toDoList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((mainBillActivity.getBaseContext()));
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {

//set data for item
        holder.item_title.setText(todoList.get(position).getTitle());
        holder.item_description.setText(todoList.get(position).getDescription());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //When user select item , data will auto set for Edit Text View
                mainBillActivity.title.setText(todoList.get(position).getTitle());
                mainBillActivity.description.setText(todoList.get(position).getDescription());


                mainBillActivity.isUpdate=true; //set flag is update = true
                mainBillActivity.idUpdate = todoList.get(position).getId();


            }
        });

    }

    @Override
    public int getItemCount() {

        return todoList.size();
    }
}
