package uk.co.willanthony.quotationapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.dialogs.ExtrasDialog;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.database.JobDatabase;
import uk.co.willanthony.quotationapp.recyclerview.ButtonItemData;
import uk.co.willanthony.quotationapp.recyclerview.DeletableAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.ExtrasAddedRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.ExtrasToAddRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasAddedItemData;
import uk.co.willanthony.quotationapp.recyclerview.JobActExtrasRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.NumericRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasItemData;
import uk.co.willanthony.quotationapp.util.RVButtonData;
import uk.co.willanthony.quotationapp.util.SwipeToDeleteCallback;

public class AddJobActivity extends AppCompatActivity implements NumericRVAdapter.RVButtonListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_test);
        this.machineryDataList = new ArrayList<>();
        this.materialsDataList = new ArrayList<>();
        this.df = new DecimalFormat("0.00");
        this.buttonData = new RVButtonData("#F0F5FB");

        initViews();
        setUpJobDataBase();
        setUpMachineryDialog();
        setUpMaterialsDialog();
        setUpSaveButton();
        setUpMachineryButton();
        setUpMaterialsButton();
    }

    private void setUpJobDataBase() {
        Intent intent = getIntent();
        long checkJobID = intent.getLongExtra("jobID", -1);
        if (checkJobID == -1) {
            this.jobID = saveJobToDataBase();
            retrieveJob(jobID);
            initSumValues();
        } else {
            this.jobID = checkJobID;
            retrieveJob(jobID);
            setJobState();
        }
    }

    private long saveJobToDataBase() {
        this.quoteID = getIntent().getLongExtra("quoteID", 0);
        Job job = new Job(quoteID, jobTitle.getText().toString(), jobDescription.getText().toString(),
                String.valueOf(AddJobActivity.this.totalCost), String.valueOf(AddJobActivity.this.costPlusVAT), getNumericRVBooleans(workersRV),
                getNumericRVBooleans(hoursRV), getNumericRVBooleans(frequencyRV), getNumericRVBooleans(percentageRV),
                getExtrasString(machineryRV), getExtrasString(materialsRV));

        JobDatabase jobDatabase = new JobDatabase(AddJobActivity.this);
        return jobDatabase.addJob(job);
    }

    private String getNumericRVBooleans(RecyclerView recyclerView) {
        NumericRVAdapter numericRVAdapter = (NumericRVAdapter) recyclerView.getAdapter();
        return numericRVAdapter.getBooleanString();
    }

    private String getExtrasString(RecyclerView recyclerView) {
        JobActExtrasRVAdapter jobActExtrasRVAdapter = (JobActExtrasRVAdapter) recyclerView.getAdapter();
        return jobActExtrasRVAdapter.getExtrasString();
    }

    private void retrieveJob(long jobID) {
        JobDatabase jobDatabase = new JobDatabase(this);
        this.job = jobDatabase.getJob(jobID);
        this.quoteID = this.job.getQuoteNumber();

        jobDatabase.close();
    }

    private void setJobState() {
        this.jobTitle.setText(job.getTitle());
        this.jobDescription.setText(job.getDescription());

        loadSelectedButtons(job.getWorkersSelectedString(), workersRV);
        loadSelectedButtons(job.getHoursSelectedString(), hoursRV);
        loadSelectedButtons(job.getFrequencySelectedString(), frequencyRV);
        loadSelectedButtons(job.getPercentageSelectedString(), percentageRV);

        loadJobExtras(job.getMachinerySelectedString(), machineryRVAdapter);
        loadJobExtras(job.getMaterialsSelectedString(), materialsRVAdapter);
    }

    private void loadSelectedButtons(String booleanString, RecyclerView recyclerView) {
        if (booleanString != null) {
            char[] chars = booleanString.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == 't') {
                    NumericRVAdapter adapter = (NumericRVAdapter) recyclerView.getAdapter();
                    ButtonItemData buttonItemData = adapter.getItemDataArray()[i];

                    buttonItemData.setSelected(true);
                    adapter.setLastButtonSelected(buttonItemData);
                    rVButtonClick(i, buttonItemData.getTag());
                }
            }
        }
    }

    private void loadJobExtras(String extrasString, JobActExtrasRVAdapter adapter) {
        if (extrasString != null) {
            String[] splitStrings = extrasString.split("/");
            for (String splitString : splitStrings) {
                String[] nameAndNumber = splitString.split("~");
                checkExtrasData(nameAndNumber, buttonData.getMachineryItemData(), adapter);
            }
        }
    }

    private void checkExtrasData(String[] nameAndNumber, List<ExtrasItemData> extrasDataList, JobActExtrasRVAdapter adapter) {
        String name = nameAndNumber[0];
        String number = nameAndNumber[1];
        for (int index = 0; index < extrasDataList.size(); index++) {

            ExtrasItemData extra = extrasDataList.get(index);
            if (name.equals(extra.getName())) {
                ExtrasAddedItemData extrasToAdd = new ExtrasAddedItemData(extra.getName(), extra.getPrice(), Integer.parseInt(number));
                adapter.addExtras(extrasToAdd);
                addMachineryCost();
                addMaterialsCost();
                displayTotal();
            }
        }
    }

    private void initViews() {
        this.jobDescription = findViewById(R.id.jobDescriptionText);
        this.jobTitle = findViewById(R.id.jobTitle);
        this.machineryRV = findViewById(R.id.extrasAddedRV);
        this.materialsRV = findViewById(R.id.materialsAddedRV);
        setUpRecyclerViews();
    }

    private void setUpRecyclerViews() {
        setUpMachineryRV();
        setUpMaterialsRV();
        setUpWorkersRV();
        setUpHoursRV();
        setUpFrequencyRV();
        setUpPercentageRV();
    }

    private void setUpMachineryRV() {
        this.machineryRVAdapter = new JobActExtrasRVAdapter(machineryDataList, this);
        this.machineryRV.setAdapter(machineryRVAdapter);
        this.machineryRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        attachSwipeToDel(machineryRVAdapter, machineryRV);
    }

    private void setUpMaterialsRV() {
        this.materialsRVAdapter = new JobActExtrasRVAdapter(materialsDataList, this);
        this.materialsRV.setAdapter(materialsRVAdapter);
        this.materialsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        attachSwipeToDel(materialsRVAdapter, materialsRV);
    }

    private void attachSwipeToDel(DeletableAdapter adapter, RecyclerView recyclerView) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void setUpMachineryDialog() {
        this.machineryDialog = new ExtrasDialog(this, "extra machinery", buttonData.getMachineryItemData());
        this.machineryDialog.setDialogResult(new ExtrasDialog.DialogResult() {
            @Override
            public void finish(List<ExtrasAddedItemData> result) {
                for (int index = 0; index < result.size(); index++) {
                    machineryRVAdapter.addExtras(result.get(index));
                }
                addMachineryCost();
                displayTotal();
            }
        });
    }

    private void setUpMaterialsDialog() {
        this.materialsDialog = new ExtrasDialog(this, "extra materials", buttonData.getMaterialsItemData());
        this.materialsDialog.setDialogResult(new ExtrasDialog.DialogResult() {
            @Override
            public void finish(List<ExtrasAddedItemData> result) {
                for (int index = 0; index < result.size(); index++) {
                    materialsRVAdapter.addExtras(result.get(index));
                }
                addMaterialsCost();
                displayTotal();
            }
        });
    }

    private void setUpWorkersRV() {
        this.workersRV = findViewById(R.id.worker_recyclerview);
        setUpHorizontalRV(this.workersRV, this.buttonData.getWorkersItemData());
    }

    private void setUpHoursRV() {
        this.hoursRV = findViewById(R.id.hours_recyclerview);
        setUpHorizontalRV(this.hoursRV, this.buttonData.getHoursItemData());
    }

    private void setUpFrequencyRV() {
        this.frequencyRV = findViewById(R.id.frequency_recyclerview);
        setUpHorizontalRV(this.frequencyRV, this.buttonData.getFrequencyItemData());
    }

    private void setUpPercentageRV() {
        this.percentageRV = findViewById(R.id.percentage_recyclerview);
        setUpHorizontalRV(this.percentageRV, this.buttonData.getPercentageItemData());
    }

    private void goToQuote() {
        Intent intent = new Intent(this, QuoteActivity.class);
        intent.putExtra("quoteID", this.quoteID);
        startActivity(intent);
    }

    private void initSumValues() {
        this.noOfWorkers = 0f;
        this.noOfHours = 0f;
        this.machineryCost = 0f;
        this.materialsCost = 0f;
        this.frequency = 0f;
        this.percentage = 0f;
    }

    private void setUpSaveButton() {
        this.saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job = new Job(job.getID(), quoteID, jobTitle.getText().toString(), jobDescription.getText().toString(),
                        String.valueOf(AddJobActivity.this.totalCost), String.valueOf(AddJobActivity.this.costPlusVAT),
                        getNumericRVBooleans(workersRV), getNumericRVBooleans(hoursRV),
                        getNumericRVBooleans(frequencyRV), getNumericRVBooleans(percentageRV),
                        getExtrasString(machineryRV), getExtrasString(materialsRV));

                JobDatabase jobDatabase = new JobDatabase(AddJobActivity.this);
                jobDatabase.editJob(job);
                goToQuote();
            }
        });
    }

    private void setUpMachineryButton() {
        this.machineryButton = findViewById(R.id.addMachineryButton);
        this.machineryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                machineryDialog.showPopUp();
            }
        });
        displayTotal();
    }

    private void setUpMaterialsButton() {
        this.materialsButton = findViewById(R.id.addMaterialsButton);
        this.materialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialsDialog.showPopUp();
            }
        });
        displayTotal();
    }

    private void setUpHorizontalRV(RecyclerView recyclerView, ButtonItemData[] itemDataArray) {

        NumericRVAdapter numericRVAdapter = new NumericRVAdapter(itemDataArray, this);
        recyclerView.setAdapter(numericRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void rVButtonClick(int position, int tag) {
        ButtonItemData buttonItemData;
        switch (tag) {
            //workers
            case 1:
                buttonItemData = buttonData.getWorkersItemData()[position];
                this.noOfWorkers = buttonItemData.getValue();
                break;

            // hours
            case 2:
                buttonItemData = buttonData.getHoursItemData()[position];
                this.noOfHours = buttonItemData.getValue();
                break;

            // frequency
            case 5:
                buttonItemData = buttonData.getFrequencyItemData()[position];
                this.frequency = buttonItemData.getValue();
                break;

            // percentage
            case 6:
                buttonItemData = buttonData.getPercentageItemData()[position];
                this.percentage = buttonItemData.getValue();
                break;
        }
        displayTotal();
    }

    public void displayTotal() {
        totalCost = sumTotalCosts();
        costPlusVAT = addVAT(totalCost);

        TextView textView = findViewById(R.id.totalTextView);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("£" + df.format(totalCost) + " + VAT = £" + df.format(costPlusVAT));
    }

    private void addMachineryCost() {
        this.machineryCost = 0;
        for (ExtrasAddedItemData itemData : machineryDataList) {
            this.machineryCost += itemData.getPrice() * itemData.getNumber();
        }
    }

    private void addMaterialsCost() {
        this.materialsCost = 0;
        for (ExtrasAddedItemData itemData : materialsDataList) {
            this.materialsCost += itemData.getPrice() * itemData.getNumber();
        }
    }

    public void deleteMaterialsItem() {
        addMaterialsCost();
        displayTotal();
    }

    public void deleteMachineryItem() {
        addMachineryCost();
        displayTotal();
    }

    private float sumTotalCosts() {
        float costWithoutPercentage = manHoursSum() + machinerySum() + materialSum();
        return costWithoutPercentage + percentageToAdd(costWithoutPercentage);
    }

    private float manHoursSum() {
        return noOfWorkers * noOfHours * frequency;
    }

    private float machinerySum() {
        return machineryCost * noOfHours * frequency;
    }

    private float materialSum() {
        return materialsCost * frequency;
    }

    private float percentageToAdd(float amount) {
        return percentage / 100 * amount;
    }

    private float addVAT(float preVATTotal) {
        float vatToAdd = preVATTotal * 0.2f;
        return vatToAdd + preVATTotal;
    }
}
