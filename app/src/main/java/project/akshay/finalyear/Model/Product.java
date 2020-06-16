package project.akshay.finalyear.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Product {

    private String productID;
    private FishermanDetails fishermanDetails;
    private FirstReceiverDetails firstReceiverDetails;
    private ProcessorDetails processorDetails;
    private DistributorDetails distributorDetails;
    private ShopDetails shopDetails;

    public Product() {
    }

    public Product(String productID) {
        this.productID = productID;
    }

    public void setFishermanDetails(FishermanDetails fishermanDetails) {
        this.fishermanDetails = fishermanDetails;
    }

    public void setFirstReceiverDetails(FirstReceiverDetails firstReceiverDetails) {
        this.firstReceiverDetails = firstReceiverDetails;
    }

    public void setProcessorDetails(ProcessorDetails processorDetails) {
        this.processorDetails = processorDetails;
    }

    public void setDistributorDetails(DistributorDetails distributorDetails) {
        this.distributorDetails = distributorDetails;
    }

    public void setShopDetails(ShopDetails shopDetails) {
        this.shopDetails = shopDetails;
    }

    public String getProductID() {
        return productID;
    }

    public FishermanDetails getFishermanDetails() {
        return fishermanDetails;
    }

    public FirstReceiverDetails getFirstReceiverDetails() {
        return firstReceiverDetails;
    }

    public ProcessorDetails getProcessorDetails() {
        return processorDetails;
    }

    public DistributorDetails getDistributorDetails() {
        return distributorDetails;
    }

    public ShopDetails getShopDetails() {
        return shopDetails;
    }

}
