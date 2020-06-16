package project.akshay.finalyear.Model;

public class ProcessorDetails {

    private String id;
    private String name;
    private String receivedDate;
    private String processingMethod;
    private String deliveredDate;
    private String expiryDate;
    private String deliveredTo;

    public ProcessorDetails(String id, String name, String receivedDate, String processingMethod, String deliveredDate, String expiryDate, String deliveredTo) {
        this.name = name;
        this.id = id;
        this.receivedDate = receivedDate;
        this.processingMethod = processingMethod;
        this.deliveredDate = deliveredDate;
        this.expiryDate = expiryDate;
        this.deliveredTo = deliveredTo;
    }

    public ProcessorDetails() {
    }

    public String getName() {
        return name;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getDeliveredTo() {
        return deliveredTo;
    }
}
