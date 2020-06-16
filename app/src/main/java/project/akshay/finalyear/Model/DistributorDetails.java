package project.akshay.finalyear.Model;

public class DistributorDetails {

    private String id;
    private String name;
    private String receivedDate;
    private String deliveredTo;

    public DistributorDetails(String id, String name, String receivedDate, String deliveredTo) {
        this.id = id;
        this.name = name;
        this.receivedDate = receivedDate;
        this.deliveredTo = deliveredTo;
    }

    public DistributorDetails() {
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

    public String getDeliveredTo() {
        return deliveredTo;
    }
}
