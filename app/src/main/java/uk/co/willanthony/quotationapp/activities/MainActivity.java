package uk.co.willanthony.quotationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.List;

import uk.co.willanthony.quotationapp.Quote;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.database.QuoteSQLiteCypherHelper;
import uk.co.willanthony.quotationapp.recyclerview.MainMenuRVAdapter;
import uk.co.willanthony.quotationapp.util.SwipeToDeleteCallback;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Quote> allQuotes;
    private MainMenuRVAdapter adapter;
    private FloatingActionButton fab;
    private ImageView paperImage;
    private TextView addQuoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase.loadLibs(this);
        setUpToolbar();
        retrieveDBInfo();
        setUpRecyclerView();
        setUpFab();
        setUpBackgroundImages();
    }

    private void setUpToolbar() {
        this.toolbar = findViewById(R.id.main_pdf_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle("QUICKQUOTE");
    }

    private void retrieveDBInfo() {
        QuoteSQLiteCypherHelper db = new QuoteSQLiteCypherHelper(this);
        this.allQuotes = db.getAllQuotes();

//        QuoteDatabaseHelper database = new QuoteDatabaseHelper(this);
//        this.allQuotes = database.getAllQuotes();
    }

    private void setUpRecyclerView() {
        this.recyclerView = findViewById(R.id.listOfQuotes);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new MainMenuRVAdapter(allQuotes,this, this);
        this.recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setUpFab() {
        this.fab = findViewById(R.id.addFabButton);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpBackgroundImages() {
        this.addQuoteText = findViewById(R.id.add);
        this.paperImage = findViewById(R.id.paperStackImage);
        shouldImageBeVisible();
    }

    public boolean shouldImageBeVisible() {
        if(recyclerView.getAdapter().getItemCount() <= 2) {
            this.addQuoteText.setVisibility(View.VISIBLE);
            this.paperImage.setVisibility(View.VISIBLE);
            return false;
        } else {
            this.addQuoteText.setVisibility(View.INVISIBLE);
            this.paperImage.setVisibility(View.INVISIBLE);
            return true;
        }
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
        }

        if(item.getItemId() == R.id.drop_menu) {
        }
        return super.onOptionsItemSelected(item);
    }
}
