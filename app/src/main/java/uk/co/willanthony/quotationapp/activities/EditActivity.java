package uk.co.willanthony.quotationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.Quote;
import uk.co.willanthony.quotationapp.database.JobDatabase;
import uk.co.willanthony.quotationapp.database.QuoteDatabaseHelper;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.recyclerview.QuoteRVAdapter;

// fix edit so when data is saved it overrides previous iteration of quote


public class EditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private long quoteID;
    private QuoteRVAdapter adapter;
    private TextView idView, totalView;
    private EditText quoteTitle;
    private FloatingActionButton fab;
    private Intent intent;
    private Quote quoteFromDB;

    private Calendar calendar;
    private String todaysDate, currentTime;
    private List<Job> jobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);
        retrieveDBInfo();
        setUpToolbar();
        setUpIDVIew();
        setUpRecyclerView();
        setCostTextView();

        setUpTitleText();
        setUpFAB();
        setDateAndTime();
        Toast.makeText(EditActivity.this, "Edit Activity", Toast.LENGTH_SHORT).show();

    }

    private void retrieveDBInfo() {
        this.intent = getIntent();
        this.quoteID = intent.getLongExtra("ID", 0);
        QuoteDatabaseHelper quoteDatabaseHelper = new QuoteDatabaseHelper(this);
        this.quoteFromDB = quoteDatabaseHelper.getQuote(quoteID);
        quoteDatabaseHelper.close();

        JobDatabase database = new JobDatabase(this);
        this.jobs = database.getQuoteJobList(quoteID);
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.main_pdf_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle(quoteFromDB.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpIDVIew() {
        this.idView = findViewById(R.id.quoteNumberTextView);
        String quoteIDString = Long.toString(quoteID);
        idView.setText(quoteIDString);
    }

    private void setUpRecyclerView() {
        this.recyclerView = findViewById(R.id.listOfJobs);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new QuoteRVAdapter(this, jobs);
        this.recyclerView.setAdapter(adapter);
    }

    private void setCostTextView() {
        this.totalView = findViewById(R.id.totalTextView);
        this.totalView.setText(setTotalViewString());
    }

    private String setTotalViewString() {
        return "£" + getQuoteCostMinusVAT() + " + VAT = " + getQuoteCostPlusVAT();
    }

    private float getQuoteCostMinusVAT() {
        float costMinusVAT = 0f;
        for(Job job : jobs) {
            costMinusVAT += Float.parseFloat(job.getCostMinusVAT());
        }
        return costMinusVAT;
    }

    private float getQuoteCostPlusVAT() {
        float costPlusVAT = 0f;
        for(Job job : jobs) {
            costPlusVAT += Float.parseFloat(job.getCostPlusVAT());
        }
        return costPlusVAT;
    }

    private void setUpTitleText() {
        this.quoteTitle = findViewById(R.id.quoteTitle);
        quoteTitle.setText(quoteFromDB.getTitle());
        this.quoteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpFAB() {
        this.fab = findViewById(R.id.addJobButton);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, AddJobActivity.class);
                startActivity(intent);
                Toast.makeText(EditActivity.this, "Add button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDateAndTime() {
        this.calendar = Calendar.getInstance();
        this.todaysDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        this.currentTime = formatTime(calendar.get(Calendar.HOUR)) + ":" + formatTime(calendar.get(Calendar.MINUTE));
    }

    private String formatTime(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_quote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Quote quote = new Quote(quoteID, quoteTitle.getText().toString(), todaysDate, currentTime);

            System.out.println(quoteTitle.getText().toString() + " " + todaysDate + " " + currentTime);

            QuoteDatabaseHelper quoteDatabaseHelper = new QuoteDatabaseHelper(this);
            long id = quoteDatabaseHelper.editQuote(quote);
            Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show();
            goToMain();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //    Toolbar toolbar;
//    EditText nTitle,nContent;
//    Calendar c;
//    String todaysDate;
//    String currentTime;
//    long nId;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        Intent i = getIntent();
//        nId = i.getLongExtra("ID",0);
//        NoteDatabase db = new NoteDatabase(this);
//        Note note = db.getNote(nId);
//
//        final String title = note.getTitle();
//        String content = note.getContent();
//        nTitle = findViewById(R.id.noteTitle);
//        nContent = findViewById(R.id.noteBody);
//        nTitle.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                getSupportActionBar().setTitle(title);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.length() != 0){
//                    getSupportActionBar().setTitle(s);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//
//        nTitle.setText(title);
//        nContent.setText(content);
//
//        // set current date and time
//        c = Calendar.getInstance();
//        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
//        Log.d("DATE", "Date: "+todaysDate);
//        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
//        Log.d("TIME", "Time: "+currentTime);
//    }
//
//
//    private String pad(int time) {
//        if(time < 10)
//            return "0"+time;
//        return String.valueOf(time);
//
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.save_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.save){
//            Note note = new Note(nId,nTitle.getText().toString(),nContent.getText().toString(),todaysDate,currentTime);
//            Log.d("EDITED", "edited: before saving id -> " + note.getID());
//            NoteDatabase sDB = new NoteDatabase(getApplicationContext());
//            long id = sDB.editNote(note);
//            Log.d("EDITED", "EDIT: id " + id);
//            goToMain();
//            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
//        }else if(item.getItemId() == R.id.delete){
//            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void goToMain() {
//        Intent i = new Intent(this,MainActivity.class);
//        startActivity(i);
//    }
}
