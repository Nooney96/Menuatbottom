package rossnoonan.menuatbottom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;
import rossnoonan.menuatbottom.GroupAdapter.GroupListItemAdapter;
import rossnoonan.menuatbottom.GroupModel.GroupToDo;

public class MainGroupActivity extends AppCompatActivity {

    List<GroupToDo> groupToDoList = new ArrayList<>();
    FirebaseFirestore db;

    RecyclerView listItem;
    RecyclerView.LayoutManager GrouplayoutManager;


    FloatingActionButton fab;

    public MaterialEditText titlegroup,descriptiongroup; //this is public as it will have access to the ListAdapter
    public boolean isUpdate = false ; // flag to check is update or is add new
    public String idUpdate = "" ; // Id of item need to update

    GroupListItemAdapter groupadapter;
// may need to be changed to SpotsDialog dialog; instead of AlertDialog dialog;
    SpotsDialog dialogGroup;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // View v = inflater.inflate(R.layout.)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingroup);

        //Init Firetore
        db = FirebaseFirestore.getInstance();

        //View
        dialogGroup = new SpotsDialog (this);
        titlegroup = (MaterialEditText) findViewById(R.id.titlegroup);
        descriptiongroup = (MaterialEditText) findViewById(R.id.descriptiongroup);
        fab = (FloatingActionButton) findViewById(R.id.fabgroup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add New
                if(!isUpdate)
                {
                    setData (titlegroup.getText().toString(),descriptiongroup.getText().toString());
                }
                else{

                    updateData(titlegroup.getText().toString(),descriptiongroup.getText().toString());
                    isUpdate = !isUpdate; //reset flag
                }


            }
        });



        listItem = (RecyclerView) findViewById(R.id.listTodo);
        listItem.setHasFixedSize(true);
        GrouplayoutManager = new LinearLayoutManager(this);
        listItem.setLayoutManager (GrouplayoutManager);



        loadData(); //Load data from FireStore
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if (item.getTitle().equals("DELETE"))
            deleteItem(item.getOrder());
        return  super.onContextItemSelected(item);
    }

    private void deleteItem(int index) {
        db.collection("GroupToDoList")
                .document(groupToDoList.get(index).getIdgroup())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loadData();

                    }
                });

    }
//here is an error
    private void updateData(String TitleGroup, String DescriptionGroup) {
        db.collection("GroupToDoList").document(idUpdate)
                .update("Grouptitle",TitleGroup,"Groupdescription",DescriptionGroup)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainGroupActivity.this, "Updated !", Toast.LENGTH_SHORT).show();

                        loadData();
                    }
                });

        //Realtime update refresh data
        db.collection("GroupToDoList").document(idUpdate)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        //added in to debug

                        loadData();
                    }
                });
    }

    private void setData(String title, String description) {
        //Random id
        String id = UUID.randomUUID().toString();
        Map<String,Object> todo = new HashMap<>();
        todo.put("Groupid",id);
        todo.put("Grouptitle",title);
        todo.put("Groupdescription",description);

        db.collection("GroupToDoList").document(id)
                .set(todo) . addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Refresh data
                loadData();
            }
        });
    }

    private void loadData() {
        dialogGroup.show();
        if(groupToDoList.size() > 0)
            groupToDoList.clear(); //remove old value
        db.collection("GroupToDoList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc:task.getResult())
                        {
                            GroupToDo grouptodo = new GroupToDo(doc.getString("Groupid"),
                                    doc.getString("Grouptitle"),
                                    doc.getString("Groupdescription"));
                            groupToDoList.add(grouptodo);
                        }
                        groupadapter = new GroupListItemAdapter(MainGroupActivity.this, groupToDoList);
                        listItem.setAdapter(groupadapter);

                        dialogGroup.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainGroupActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
