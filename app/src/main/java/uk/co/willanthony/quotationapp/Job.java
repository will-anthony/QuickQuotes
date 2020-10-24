package uk.co.willanthony.quotationapp;

import java.io.Serializable;

public class Job implements Serializable {

    private long ID;
    private long quoteNumber;
    private String title;
    private String description;
    private String costMinusVAT;
    private String costPlusVAT;


    public Job() {

    }

    public Job(long quoteNumber, String title, String description, String costMinusVAT, String costPlusVAT) {
        this.quoteNumber = quoteNumber;
        this.title = title;
        this.description = description;
        this.costMinusVAT = costMinusVAT;
        this.costPlusVAT = costPlusVAT;
    }

    public Job(long ID, long quoteNumber, String title, String description, String costMinusVAT, String costPlusVAT) {
        this.ID = ID;
        this.quoteNumber = quoteNumber;
        this.title = title;
        this.description = description;
        this.costMinusVAT = costMinusVAT;
        this.costPlusVAT = costPlusVAT;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getQuoteNumber() {
        return quoteNumber;
    }

    public void setQuoteNumber(long quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCostMinusVAT() {
        return costMinusVAT;
    }

    public void setCostMinusVAT(String costMinusVAT) {
        this.costMinusVAT = costMinusVAT;
    }

    public String getCostPlusVAT() {
        return costPlusVAT;
    }

    public void setCostPlusVAT(String costPlusVAT) {
        this.costPlusVAT = costPlusVAT;
    }
}
