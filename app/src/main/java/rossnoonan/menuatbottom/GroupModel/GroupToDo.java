package rossnoonan.menuatbottom.GroupModel;

//import java.util.HashMap;

public class GroupToDo {
    private String idgroup,titlegroup,descriptiongroup;

    public GroupToDo(){

    }
    public GroupToDo(String idgroup, String titlegroup, String descriptiongroup) {
        this.idgroup = idgroup;
        this.titlegroup = titlegroup;
        this.descriptiongroup = descriptiongroup;

    }

    public String getIdgroup() {
        return idgroup;
    }

    public void setId(String idgroup) {
        this.idgroup = idgroup;
    }

    public String getTitlegroup() {
        return titlegroup;
    }

    public void setTitle(String titlegroup) {
        this.titlegroup = titlegroup;
    }

    public String getDescriptiongroup() {
        return descriptiongroup;
    }

    public void setDescription(String descriptiongroup) {
        this.descriptiongroup = descriptiongroup;
    }

}
