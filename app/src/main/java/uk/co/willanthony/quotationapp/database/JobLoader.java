package uk.co.willanthony.quotationapp.database;

import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.dialogs.ExtrasDialog;
import uk.co.willanthony.quotationapp.recyclerview.JobActExtrasRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasAddedItemData;
import uk.co.willanthony.quotationapp.util.RVButtonData;

public class JobLoader {
    private EditText jobTitle;
    private EditText jobDescription;
    private Button saveButton;
    private Button machineryButton, materialsButton;
    private List<ExtrasAddedItemData> machineryDataList;
    private List<ExtrasAddedItemData> materialsDataList;
    private ExtrasDialog machineryDialog, materialsDialog;
    private JobActExtrasRVAdapter machineryRVAdapter, materialsRVAdapter;
    private RVButtonData buttonData;
    private long jobID;
    private Job job;
    private long quoteID;

    private RecyclerView workersRV, hoursRV, frequencyRV, percentageRV;
    private RecyclerView machineryRV, materialsRV;

    private float noOfWorkers, noOfHours, machineryCost, materialsCost, frequency, percentage;
    private float totalCost, costPlusVAT;
    DecimalFormat df;
}
