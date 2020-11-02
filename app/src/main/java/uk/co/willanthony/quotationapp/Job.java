package uk.co.willanthony.quotationapp;

import java.io.Serializable;

public class Job implements Serializable {

    private long ID;
    private long quoteNumber;
    private String title;
    private String description;
    private String workersSelectedString;
    private String hoursSelectedString;
    private String frequencySelectedString;
    private String percentageSelectedString;
    private String machinerySelectedString;
    private String materialsSelectedString;

    private String costMinusVAT;
    private String costPlusVAT;


    public Job() {

    }

    public Job(long quoteNumber, String title, String description, String costMinusVAT, String costPlusVAT, String workersSelectedString,
               String hoursSelectedString, String frequencySelectedString, String percentageSelectedString, String machinerySelectedString,
               String materialsSelectedString) {
        this.quoteNumber = quoteNumber;
        this.title = title;
        this.description = description;
        this.costMinusVAT = costMinusVAT;
        this.costPlusVAT = costPlusVAT;
        this.workersSelectedString = workersSelectedString;
        this.hoursSelectedString = hoursSelectedString;
        this.frequencySelectedString = frequencySelectedString;
        this.percentageSelectedString = percentageSelectedString;
        this.machinerySelectedString = machinerySelectedString;
        this.materialsSelectedString = materialsSelectedString;

    }

    public Job(long ID, long quoteNumber, String title, String description, String costMinusVAT, String costPlusVAT, String workersSelectedString,
               String hoursSelectedString, String frequencySelectedString, String percentageSelectedString, String machinerySelectedString,
               String materialsSelectedString) {
        this.ID = ID;
        this.quoteNumber = quoteNumber;
        this.title = title;
        this.description = description;
        this.costMinusVAT = costMinusVAT;
        this.costPlusVAT = costPlusVAT;
        this.workersSelectedString = workersSelectedString;
        this.hoursSelectedString = hoursSelectedString;
        this.frequencySelectedString = frequencySelectedString;
        this.percentageSelectedString = percentageSelectedString;
        this.machinerySelectedString = machinerySelectedString;
        this.materialsSelectedString = materialsSelectedString;
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

    public String getWorkersSelectedString() {
        return workersSelectedString;
    }

    public void setWorkersSelectedString(String workersSelectedString) {
        this.workersSelectedString = workersSelectedString;
    }

    public String getHoursSelectedString() {
        return hoursSelectedString;
    }

    public void setHoursSelectedString(String hoursSelectedString) {
        this.hoursSelectedString = hoursSelectedString;
    }

    public String getFrequencySelectedString() {
        return frequencySelectedString;
    }

    public void setFrequencySelectedString(String frequencySelectedString) {
        this.frequencySelectedString = frequencySelectedString;
    }

    public String getPercentageSelectedString() {
        return percentageSelectedString;
    }

    public void setPercentageSelectedString(String percentageSelectedString) {
        this.percentageSelectedString = percentageSelectedString;
    }

    public String getMachinerySelectedString() {
        return machinerySelectedString;
    }

    public void setMachinerySelectedString(String machinerySelectedString) {
        this.machinerySelectedString = machinerySelectedString;
    }

    public String getMaterialsSelectedString() {
        return materialsSelectedString;
    }

    public void setMaterialsSelectedString(String materialsSelectedString) {
        this.materialsSelectedString = materialsSelectedString;
    }
}
