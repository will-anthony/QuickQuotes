package uk.co.willanthony.quotationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.willanthony.quotationapp.Quote;
import uk.co.willanthony.quotationapp.database.QuoteDatabase;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.recyclerview.MainMenuRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.SwipeToDeleteCallback;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Quote> allQuotes;
    MainMenuRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        retrieveDBInfo();
        setUpRecyclerView();
        Toast.makeText(MainActivity.this, "Main Activity", Toast.LENGTH_SHORT).show();

    }

    private void setUpToolbar() {
        this.toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle("QUICKQUOTE");
    }

    private void retrieveDBInfo() {
        QuoteDatabase database = new QuoteDatabase(this);
        this.allQuotes = database.getAllQuotes();
    }

    private void setUpRecyclerView() {
        this.recyclerView = findViewById(R.id.listOfQuotes);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new MainMenuRVAdapter(this, allQuotes);
        this.recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void openNewQuoteActivity() {
        Intent intent = new Intent(this, QuoteActivity.class);
        startActivity(intent);
    }

    private void openAddJobActivity() {
        Intent intent = new Intent(this, AddJobActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Add) {
            Intent intent = new Intent(this, QuoteActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Add button clicked", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.drop_menu) {
            Toast.makeText(this, "Menu button clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
