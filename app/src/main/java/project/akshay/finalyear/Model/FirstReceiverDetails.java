package project.akshay.finalyear.Model;

public class FirstReceiverDetails {

    private String id;
    private String name;
    private String receivedDate;
    private String deliveredDate;
    private String location;

    public FirstReceiverDetails(String id, String name, String receivedDate, String deliveredDate, String location) {
        this.id = id;
        this.name = name;
        this.receivedDate = receivedDate;
        this.deliveredDate = deliveredDate;
        this.location = location;
    }

    public FirstReceiverDetails() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public String getLocation() {
        return location;
    }
}
