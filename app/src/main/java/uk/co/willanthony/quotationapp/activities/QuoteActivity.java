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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.co.willanthony.quotationapp.util.DateTimeSetter;
import uk.co.willanthony.quotationapp.util.DisplayCost;
import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.Quote;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.database.JobDatabase;
import uk.co.willanthony.quotationapp.database.QuoteDatabase;
import uk.co.willanthony.quotationapp.recyclerview.QuoteRVAdapter;

public class QuoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText quoteTitleTextView;
    private TextView quoteIDTextView;
    private TextView totalTextView;
    private TextView addJobTextView;
    private ImageView addJobBackground;
    private FloatingActionButton fab;

    private QuoteRVAdapter adapter;

    private long quoteID;
    private Quote quote;
    private List<Job> jobs;

    private String todaysDate, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);
        initialiseViews();
        setUpDataBaseEntry();
        setUpTitleText();
        setDateAndTime();
        setUpToolbar();
        setUpFAB();
        retrieveJobs();
        setUpIDVIew();
        setUpRecyclerView();
        setCostTextView();
        setBackgroundImage();
        Toast.makeText(QuoteActivity.this, "Quote Activity", Toast.LENGTH_SHORT).show();
    }

    private void initialiseViews() {
        this.quoteIDTextView = findViewById(R.id.quoteNumberTextView);
        this.quoteTitleTextView = findViewById(R.id.quoteTitle);
        this.toolbar = findViewById(R.id.main_pdf_toolbar);
        this.recyclerView = findViewById(R.id.listOfJobs);
        this.fab = findViewById(R.id.addJobButton);
        this.totalTextView = findViewById(R.id.totalTextView);
    }

    private void setUpDataBaseEntry() {

        Intent intent = getIntent();
        long checkQuoteID = intent.getLongExtra("quoteID", -1);
        if (checkQuoteID == -1) {
            this.quoteID = saveQuoteToDataBase();
            retrieveQuote(quoteID);
        } else {
            this.quoteID = checkQuoteID;
            retrieveQuote(quoteID);
        }
    }

    private long saveQuoteToDataBase() {
        Quote quote = new Quote(quoteTitleTextView.getText().toString(), todaysDate, currentTime);
        QuoteDatabase quoteDatabase = new QuoteDatabase(this);
        return quoteDatabase.addQuote(quote);
    }

    private void retrieveQuote(long quoteID) {
        QuoteDatabase quoteDatabase = new QuoteDatabase(this);
        this.quote = quoteDatabase.getQuote(quoteID);
        quoteDatabase.close();
    }

    private void setUpTitleText() {
        this.quoteTitleTextView.setText(quote.getTitle());
        this.quoteTitleTextView.addTextChangedListener(new TextWatcher() {
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

    public void setDateAndTime() {
        DateTimeSetter dateTimeSetter = new DateTimeSetter();
        this.todaysDate = dateTimeSetter.getDate();
        this.currentTime = dateTimeSetter.getTime();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        this.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpFAB() {
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quote quote = new Quote(quoteID, quoteTitleTextView.getText().toString(), todaysDate, currentTime);

                QuoteDatabase quoteDatabase = new QuoteDatabase(QuoteActivity.this);
                quoteDatabase.editQuote(quote);

                Intent intent = new Intent(QuoteActivity.this, AddJobActivity.class);
                intent.putExtra("quoteID", quoteID);
                startActivity(intent);
            }
        });
    }

    private void retrieveJobs() {
        JobDatabase jobDatabase = new JobDatabase(this);
        this.jobs = jobDatabase.getQuoteJobList(quoteID);
        for (Job job : jobs) {
            Toast.makeText(this, job.getCostMinusVAT() + " + " + job.getCostPlusVAT(), Toast.LENGTH_SHORT).show();
        }
        jobDatabase.close();
    }

    private void setUpIDVIew() {
        String quoteIDString = Long.toString(quoteID);
        quoteIDTextView.setText(quoteIDString);
    }

    private void setUpRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new QuoteRVAdapter(this, jobs);
        this.recyclerView.setAdapter(adapter);
    }

    private void setCostTextView() {
        DisplayCost displayCost = new DisplayCost();
        this.totalTextView.setText(displayCost.setJobTotalString(jobs));
    }

    private void setBackgroundImage() {
        this.addJobTextView = findViewById(R.id.addJobTextView);
        this.addJobBackground = findViewById(R.id.pageSheetImage);

        checkBackgroundVisibility();
    }

    public void checkBackgroundVisibility() {
        if (recyclerView.getAdapter().getItemCount() >= 3) {
            addJobTextView.setVisibility(View.INVISIBLE);
            addJobBackground.setVisibility(View.INVISIBLE);
        } else {
            addJobTextView.setVisibility(View.VISIBLE);
            addJobBackground.setVisibility(View.VISIBLE);
        }
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

            Quote quote = new Quote(quoteID, quoteTitleTextView.getText().toString(), todaysDate, currentTime);

            QuoteDatabase quoteDatabase = new QuoteDatabase(this);
            quoteDatabase.editQuote(quote);
            goToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
