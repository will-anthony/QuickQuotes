package uk.co.willanthony.quotationapp.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import uk.co.willanthony.quotationapp.BuildConfig;
import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.Quote;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.database.JobDatabase;
import uk.co.willanthony.quotationapp.database.QuoteDatabaseHelper;
import uk.co.willanthony.quotationapp.recyclerview.JobPDFAdapter;
import uk.co.willanthony.quotationapp.util.DisplayCost;

public class QuotePDFActivity extends AppCompatActivity {

    private static final String QUOTE_PATH_FORMAT = "quotes/quote-%d.pdf";

    private View linearLayout;

    private Quote quote;
    private List<Job> jobs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_pdf);
        retrieveQuote(getIntent().getLongExtra("quoteID", -1));

        if (this.quote != null) {
            setAddress();
            retrieveJobs(quote.getID());
            setDate();
            setQuoteNumber();
            setQuoteTitle();
            setJobRV();
            setTotalCost();
            setSignatureView();
            setFooterView();
            setPDFGeneratorButton();
        }
    }

    private void setAddress() {
        TextView addressView = findViewById(R.id.addressView);
        addressView.setText(R.string.work_address);
    }

    private void retrieveQuote(long quoteID) {
        if (quoteID == -1) {
            Toast.makeText(this, "Quote not passed to quotePDF", Toast.LENGTH_LONG).show();
        }
        QuoteDatabaseHelper quoteDatabaseHelper = new QuoteDatabaseHelper(this);
        this.quote = quoteDatabaseHelper.getQuote(quoteID);
        quoteDatabaseHelper.close();
    }

    private void setDate() {
        TextView dateView = findViewById(R.id.dateView);
        dateView.setText(this.quote.getDate());
    }

    private void setQuoteTitle() {
        TextView quoteTitleView = findViewById(R.id.quoteTitleView);
        quoteTitleView.setText(quote.getTitle());
    }

    private void setQuoteNumber() {
        TextView quoteNumberView = findViewById(R.id.quoteNumberView);
        TextView numberText = findViewById(R.id.numberText);
        quoteNumberView.setText(String.valueOf(quote.getID()));
        numberText.setText(R.string.quote_no);
    }

    private void setJobRV() {
        RecyclerView jobRV = findViewById(R.id.jobPDFRecycler);
        jobRV.setAdapter(new JobPDFAdapter(jobs));
        jobRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void retrieveJobs(long quoteID) {
        JobDatabase jobDatabase = new JobDatabase(this);
        this.jobs = jobDatabase.getQuoteJobList(quoteID);
        jobDatabase.close();
    }

    private void setTotalCost() {
        TextView totalCostView = findViewById(R.id.totalCostVIew);

        DisplayCost displayCost = new DisplayCost();
        String totalCost = displayCost.setJobTotalString(jobs);

        totalCostView.setText("Total: " + totalCost);
    }

    private void setSignatureView() {
        TextView signatureView = findViewById(R.id.signatureView);
        signatureView.setText(R.string.yours_faithfully);
    }

    private void setFooterView() {
        TextView footerView = findViewById(R.id.footerView);
        footerView.setText(R.string.quote_footer);
    }

    private void setPDFGeneratorButton() {
        this.linearLayout = findViewById(R.id.quoteLayout);
        Button generatePDFButton = findViewById(R.id.pdfButton);
        generatePDFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1024);
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1025);
                createPdf();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission(String permission, int requestCode) {
        int check = ActivityCompat.checkSelfPermission(this, permission);
        if (check != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    private void createPdf() {

        int height = linearLayout.getHeight();
        int width = linearLayout.getWidth();
        Bitmap bitmap = convertViewToBitmap(linearLayout, width, height);

        PdfDocument pdf = drawPDFDocument(bitmap);
        writePDFToStorage(pdf);
        pdf.close();

        openGeneratedPDF();
    }

    public static Bitmap convertViewToBitmap(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private PdfDocument drawPDFDocument(Bitmap bitmap) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        return document;
    }

    private void writePDFToStorage(PdfDocument pdf) {

        File filePath = new File(getExternalFilesDir(null), String.format(QUOTE_PATH_FORMAT, quote.getID()));
        checkPathIsCreated(filePath);

        try {
            int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (check == PackageManager.PERMISSION_GRANTED) {
                final boolean dirsCreated = Objects.requireNonNull(filePath.getParentFile()).mkdirs();
                Toast.makeText(this, "Dirs created: " + dirsCreated, Toast.LENGTH_LONG).show();

                pdf.writeTo(new FileOutputStream(filePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void checkPathIsCreated(File filePath) {
        String created = filePath.exists() ? "file created" : "not created";
        Toast.makeText(this, created, Toast.LENGTH_LONG).show();
    }

    private void openGeneratedPDF() {
        File file = new File(getExternalFilesDir(null), String.format(QUOTE_PATH_FORMAT, quote.getID()));
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(QuotePDFActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
}
