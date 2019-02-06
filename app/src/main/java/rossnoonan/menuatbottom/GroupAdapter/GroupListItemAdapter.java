package rossnoonan.menuatbottom.GroupAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rossnoonan.menuatbottom.GroupModel.GroupToDo;
import rossnoonan.menuatbottom.MainGroupActivity;
import rossnoonan.menuatbottom.R;

class GroupListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
{
GroupItemClickListener groupItemClickListener;
TextView Groupitem_title,Groupitem_description;

    public GroupListItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        Groupitem_title = (TextView) itemView.findViewById(R.id.Groupitem_title);
        Groupitem_description = (TextView) itemView.findViewById(R.id.Groupitem_description);
    }

    public void setGroupItemClickListener(GroupItemClickListener groupItemClickListener){
        this.groupItemClickListener = groupItemClickListener;

    }

    @Override
    public void onClick(View view) {
        groupItemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select the action");
        contextMenu.add(0,0,getAdapterPosition(),"DELETE");

    }
}

public class GroupListItemAdapter extends RecyclerView.Adapter<GroupListItemViewHolder>  {

    MainGroupActivity mainGroupActivity;
    List<GroupToDo> todoList;

    public GroupListItemAdapter(MainGroupActivity mainGroupActivity, List<GroupToDo> groupToDoList){

        this.mainGroupActivity = mainGroupActivity;
        this.todoList = groupToDoList;
    }

    @Override
    public GroupListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((mainGroupActivity.getBaseContext()));
        View view = inflater.inflate(R.layout.group_list_item,parent,false);
        return new GroupListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListItemViewHolder holder, int position) {

//set data for item
        holder.Groupitem_title.setText(todoList.get(position).getTitlegroup());
        holder.Groupitem_description.setText(todoList.get(position).getDescriptiongroup());


        holder.setGroupItemClickListener(new GroupItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //When user select item , data will auto set for Edit Text View
                mainGroupActivity.titlegroup.setText(todoList.get(position).getTitlegroup());
                mainGroupActivity.descriptiongroup.setText(todoList.get(position).getDescriptiongroup());


                mainGroupActivity.isUpdate=true; //set flag is update = true
                mainGroupActivity.idUpdate = todoList.get(position).getIdgroup();


            }
        });

    }

    @Override
    public int getItemCount() {

        return todoList.size();
    }
}
