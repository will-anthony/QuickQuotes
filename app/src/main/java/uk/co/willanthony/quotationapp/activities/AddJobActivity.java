package uk.co.willanthony.quotationapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import uk.co.willanthony.quotationapp.MachineryDialog;
import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.database.JobDatabase;
import uk.co.willanthony.quotationapp.recyclerview.ButtonItemData;
import uk.co.willanthony.quotationapp.recyclerview.ExtrasAddedItemData;
import uk.co.willanthony.quotationapp.recyclerview.MachineryAddedRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.NumericRVAdapter;

public class AddJobActivity extends AppCompatActivity implements NumericRVAdapter.RVButtonListener {

    private static final String TAG = "AddJobActivity";
    //    private Toolbar toolbar;
    private EditText jobTitle;
    private EditText jobDescription;
    private Button saveButton;
    private String textColor;
    private Button machineryButton, materialsButton;
    private List<ExtrasAddedItemData> itemDataList;


    private ButtonItemData[] workersItemData, hoursItemData, frequencyItemData, percentageItemData;
    private RecyclerView workersRecyclerView, hoursRecyclerView, frequencyRecyclerView, percentageRecyclerView;
    private RecyclerView extrasRV;

    private float noOfWorkers, noOfHours, machineryCost, materialsCost, frequency, percentage;
    private float totalCost, costPlusVAT;
    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_test);

        this.textColor = "#F0F5FB";
        jobDescription = findViewById(R.id.jobDescriptionText);
        jobTitle = findViewById(R.id.jobTitle);
        extrasRV = findViewById(R.id.extrasRV);
        itemDataList = new ArrayList<>();
        extrasRV.setAdapter(new MachineryAddedRVAdapter(itemDataList, this));

        df = new DecimalFormat("0.00");

        initSumValues();

        this.workersRecyclerView = findViewById(R.id.worker_recyclerview);
        this.workersItemData = setWorkersItemData();
        setUpHorizontalRV(this.workersRecyclerView, this.workersItemData);

        this.hoursRecyclerView = findViewById(R.id.hours_recyclerview);
        this.hoursItemData = setHoursItemData();
        setUpHorizontalRV(this.hoursRecyclerView, this.hoursItemData);

        this.frequencyRecyclerView = findViewById(R.id.frequency_recyclerview);
        this.frequencyItemData = setFrequencyItemData();
        setUpHorizontalRV(this.frequencyRecyclerView, this.frequencyItemData);

        this.percentageRecyclerView = findViewById(R.id.percentage_recyclerview);
        this.percentageItemData = setPercentageItemData();
        setUpHorizontalRV(this.percentageRecyclerView, this.percentageItemData);

        this.saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job job = new Job(getIntent().getLongExtra("quoteID", 0), jobTitle.getText().toString(), jobDescription.getText().toString(),
                        String.valueOf(AddJobActivity.this.totalCost), String.valueOf(AddJobActivity.this.costPlusVAT));

                JobDatabase jobDatabase = new JobDatabase(AddJobActivity.this);
                jobDatabase.addJob(job);
                Toast.makeText(AddJobActivity.this, "Save button clicked", Toast.LENGTH_SHORT).show();
                goToQuote();
            }
        });

        this.machineryButton = findViewById(R.id.addMachineryButton);
        this.machineryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MachineryDialog(AddJobActivity.this, "Extra Machinery", this).showPopUp();
            }
        });
        this.materialsButton = findViewById(R.id.addMaterialsButton);

    }

    private void goToQuote() {
        // Gets quoteID, passed to this class inside the intent and returns it. QuoteActivity class
        // then checks for -1. If true creates new DB entry, else edits existing entry
        long quoteID = getIntent().getLongExtra("quoteID", -1);

        Intent intent = new Intent(this, QuoteActivity.class);
        Log.d(TAG, "goToQuote: " + quoteID);
        intent.putExtra("quoteID", quoteID);
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

    private void setUpHorizontalRV(RecyclerView recyclerView, ButtonItemData[] itemDataArray) {

        NumericRVAdapter numericRVAdapter = new NumericRVAdapter(itemDataArray, this);
        recyclerView.setAdapter(numericRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    private ButtonItemData[] setWorkersItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("1", textColor, 15, 1),
                new ButtonItemData("2", textColor, 30, 1),
                new ButtonItemData("3", textColor, 45, 1),
                new ButtonItemData("4", textColor, 60, 1),
                new ButtonItemData("5", textColor, 75, 1),
                new ButtonItemData("6", textColor, 90, 1),
                new ButtonItemData("7", textColor, 105, 1),
                new ButtonItemData("8", textColor, 120, 1),
                new ButtonItemData("9", textColor, 135, 1),
                new ButtonItemData("10", textColor, 150, 1),
        };
    }

    private ButtonItemData[] setHoursItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("0.5", textColor, 0.5f, 2),
                new ButtonItemData("1", textColor, 1f, 2),
                new ButtonItemData("1.5", textColor, 1.5f, 2),
                new ButtonItemData("2", textColor, 2f, 2),
                new ButtonItemData("2.5", textColor, 2.5f, 2),
                new ButtonItemData("3", textColor, 3f, 2),
                new ButtonItemData("4", textColor, 4f, 2),
                new ButtonItemData("5", textColor, 5f, 2),
                new ButtonItemData("6", textColor, 6f, 2),
                new ButtonItemData("8", textColor, 8f, 2),
                new ButtonItemData("10", textColor, 10f, 2),
                new ButtonItemData("12", textColor, 12f, 2),
                new ButtonItemData("16", textColor, 16f, 2),
                new ButtonItemData("24", textColor, 24f, 2),
                new ButtonItemData("32", textColor, 32f, 2),
                new ButtonItemData("40", textColor, 40f, 2),
        };
    }

//    private ButtonItemData[] setMaterialsItemData() {
//        return new ButtonItemData[]{
//                new ButtonItemData("white marker fluid", textColor, 30f, 4),
//                new ButtonItemData("blue marker fluid", textColor, 70f, 4),
//                new ButtonItemData("postcrete", textColor, 5f, 4),
//                new ButtonItemData("3\" post", textColor, 13f, 4),
//                new ButtonItemData("4\" post", textColor, 20f, 4),
//                new ButtonItemData("4' panel", textColor, 25f, 4),
//                new ButtonItemData("5' panel", textColor, 26f, 4),
//                new ButtonItemData("6' panel", textColor, 27f, 4),
//                new ButtonItemData("4\" rail", textColor, 11f, 4),
//        };
//    }

    private ButtonItemData[] setFrequencyItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("1", textColor, 1f, 5),
                new ButtonItemData("2", textColor, 2f, 5),
                new ButtonItemData("3", textColor, 3f, 5),
                new ButtonItemData("4", textColor, 4f, 5),
                new ButtonItemData("5", textColor, 5f, 5),
                new ButtonItemData("6", textColor, 6f, 5),
                new ButtonItemData("8", textColor, 8f, 5),
                new ButtonItemData("10", textColor, 10f, 5),
                new ButtonItemData("12", textColor, 12f, 5),
                new ButtonItemData("14", textColor, 14f, 5),
                new ButtonItemData("16", textColor, 16f, 5),
                new ButtonItemData("18", textColor, 18f, 5),
                new ButtonItemData("20", textColor, 20f, 5),
                new ButtonItemData("25", textColor, 25f, 5),
        };
    }

    private ButtonItemData[] setPercentageItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("10%", textColor, 10f, 6),
                new ButtonItemData("15%", textColor, 15f, 6),
                new ButtonItemData("20%", textColor, 20f, 6),
                new ButtonItemData("25%", textColor, 25f, 6),
                new ButtonItemData("30%", textColor, 30f, 6),
                new ButtonItemData("35%", textColor, 35f, 6),
                new ButtonItemData("40%", textColor, 40f, 6),
                new ButtonItemData("45%", textColor, 45f, 6),
                new ButtonItemData("50%", textColor, 50f, 6),
                new ButtonItemData("60%", textColor, 60f, 6),
                new ButtonItemData("80%", textColor, 80f, 6),
                new ButtonItemData("100%", textColor, 100f, 6),
        };
    }

    @Override
    public void rVButtonClick(int position, int tag) {
        ButtonItemData buttonItemData = null;
        Log.d(TAG, "rVButtonClick: clicked ");
        switch (tag) {

            //workers
            case 1:
                buttonItemData = workersItemData[position];
                this.noOfWorkers = buttonItemData.getValue();
                break;

            // hours
            case 2:
                buttonItemData = hoursItemData[position];
                this.noOfHours = buttonItemData.getValue();
                break;

//            // machinery
//            case 3:
//                buttonItemData = machineryItemData[position];
//                this.machineryCost += buttonItemData.getValue();
//                break;
//
//            // materials
//            case 4:
//                buttonItemData = materialsItemData[position];
//                this.materialsCost += buttonItemData.getValue();
//                break;

            // frequency
            case 5:
                buttonItemData = frequencyItemData[position];
                this.frequency = buttonItemData.getValue();
                break;

            // percentage
            case 6:
                buttonItemData = percentageItemData[position];
                this.percentage = buttonItemData.getValue();
                break;
        }

        displayTotal();
    }

    private void displayTotal() {
        totalCost = sumTotalCosts();
        costPlusVAT = addVAT(totalCost);

        TextView textView = findViewById(R.id.totalTextView);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("£" + df.format(totalCost) + " + VAT = £" + df.format(costPlusVAT));
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


    public void addMachinery(ExtrasAddedItemData extrasAddedItemData) {
        for (int index = 0; index < itemDataList.size(); index++) {
            itemDataList.add(0, extrasAddedItemData);
            extrasRV.getAdapter().notifyItemInserted(0);
        }

    }
}
