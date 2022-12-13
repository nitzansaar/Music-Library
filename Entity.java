import java.util.Date;

public class Entity {
    protected String name;
    protected static int counter = 0;
    protected int entityID;
    protected Date dateCreated;

    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }
    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
    public String toHTML() {
        return "<b>" + this.name + "</b><i> " + this.entityID + "</i>";
    }
    public String toXML() {
        return "<entity><name>" + this.name + "</name><ID> " + this.entityID + "</ID></entity>";
    }
}