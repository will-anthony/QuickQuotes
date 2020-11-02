package uk.co.willanthony.quotationapp.util;

import java.text.DecimalFormat;
import java.util.List;

import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasAddedItemData;

public class DisplayCost {

    private DecimalFormat decimalFormat;

    public DisplayCost() {
        this.decimalFormat = new DecimalFormat("0.00");
    }

    public String setExtrasString(ExtrasAddedItemData extrasAddedItemData) {
        return "£" + decimalFormat.format(extrasAddedItemData.getPrice() * extrasAddedItemData.getNumber());
    }

    // receives single job and returns formatted total cost as a String
    public String setJobTotalString(Job job) {
        return "£" + decimalFormat.format(Float.parseFloat(job.getCostMinusVAT())) +
                " + VAT = £" + decimalFormat.format(Float.parseFloat(job.getCostPlusVAT()));
    }

    // receives list of jobs and adds all prices, then returns final cost as a String
    public String setJobTotalString(List<Job> jobs) {
        return "£" + decimalFormat.format(getQuoteCostMinusVAT(jobs)) + "  +  VAT  =  £" + decimalFormat.format(getQuoteCostPlusVAT(jobs));
    }

    private float getQuoteCostMinusVAT(List<Job> jobs) {
        float costMinusVAT = 0f;
        for(Job job : jobs) {
            costMinusVAT += Float.parseFloat(job.getCostMinusVAT());
        }
        return costMinusVAT;
    }

    private float getQuoteCostPlusVAT(List<Job> jobs) {
        float costPlusVAT = 0f;
        for(Job job : jobs) {
            costPlusVAT += Float.parseFloat(job.getCostPlusVAT());
        }
        return costPlusVAT;
    }
}
