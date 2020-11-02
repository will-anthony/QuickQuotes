package uk.co.willanthony.quotationapp.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.Quote;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.database.JobDatabase;
import uk.co.willanthony.quotationapp.database.QuoteDatabase;
import uk.co.willanthony.quotationapp.recyclerview.JobPDFAdapter;
import uk.co.willanthony.quotationapp.util.DisplayCost;

public class QuotePDFActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button generatePDFButton;
    private TextView addressView, dateView, quoteNumberView, quoteTitleView, numberText, totalCostView, signatureView, footerView;
    private RecyclerView jobRV;
    private Quote quote;
    private List<Job> jobs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_pdf);
        retrieveQuote(getIntent().getLongExtra("quoteID", -1));
        this.toolbar = findViewById(R.id.main_pdf_toolbar);
        this.dateView = findViewById(R.id.dateView);
        setAddress();
        if(this.quote != null) {
            retrieveJobs(quote.getID());
            setDate();
            setQuoteNumber();
            setQuoteTitle();
            setJobRV();
            setTotalCost();
            setSignatureView();
            setFooterView();
        }
    }

    private void setAddress() {
        this.addressView = findViewById(R.id.addressView);
        this.addressView.setText(
                "J H Jones and Son's \n " +
                        "Park Farm \n " +
                        "Bath Road \n " +
                        "Atworth \n " +
                        "Wiltshire \n " +
                        "SN12 8HT"
        );
    }

    private void retrieveQuote(long quoteID) {
        if(quoteID == -1) {
            Toast.makeText(this, "Quote not passed to quotePDF", Toast.LENGTH_LONG).show();
        }
        QuoteDatabase quoteDatabase = new QuoteDatabase(this);
        this.quote = quoteDatabase.getQuote(quoteID);
        quoteDatabase.close();
    }

    private void setDate() {
        this.dateView.setText(this.quote.getDate());
    }

    private void setQuoteTitle() {
        this.quoteTitleView = findViewById(R.id.quoteTitleView);
        this.quoteTitleView.setText(quote.getTitle());
    }

    private void setQuoteNumber() {
        this.quoteNumberView = findViewById(R.id.quoteNumberView);
        this.numberText = findViewById(R.id.numberText);
        this.quoteNumberView.setText(String.valueOf(quote.getID()));
        this.numberText.setText("quote no");
    }

    private void setJobRV() {
        this.jobRV = findViewById(R.id.jobPDFRecycler);
        this.jobRV.setAdapter(new JobPDFAdapter(jobs));
        this.jobRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void retrieveJobs(long quoteID) {
        JobDatabase jobDatabase = new JobDatabase(this);
        this.jobs = jobDatabase.getQuoteJobList(quoteID);
        jobDatabase.close();
    }

    private void setTotalCost() {
        this.totalCostView = findViewById(R.id.totalCostVIew);

        DisplayCost displayCost = new DisplayCost();
        String totalCost = displayCost.setJobTotalString(jobs);

        this.totalCostView.setText("Total: " + totalCost);
    }

    private void setSignatureView() {
        this.signatureView = findViewById(R.id.signatureView);
        this.signatureView.setText("yours faithfully, \n " +
                "\n" +
                "\n" +
                "Will Jones \n" +
                "J H Jones & Sons \n " +
                "If tender is accepted please sign below and return one copy \n" +
                "\n" +
                "signature....................      date....................");
    }

    private void setFooterView() {
        this.footerView = findViewById(R.id.footerView);
        this.footerView.setText("telephone: 01225 703295   email will-anthony@hotmail.com \n "+
                "VAT no 137 9615 41");
    }

//    public class ScrollActivity extends AppCompatActivity {
//
//        private Button btn;
//        private LinearLayout llScroll;
//        private Bitmap bitmap;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_scroll);
//
//            btn = findViewById(R.id.btn);
//            llScroll = findViewById(R.id.llScroll);
//
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
//                    bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
//                    createPdf();
//                }
//            });
//
//        }
//
//        public static Bitmap loadBitmapFromView(View v, int width, int height) {
//            Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            Canvas c = new Canvas(b);
//            v.draw(c);
//
//            return b;
//        }
//
//        private void createPdf(){
//            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//            //  Display display = wm.getDefaultDisplay();
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            float hight = displaymetrics.heightPixels ;
//            float width = displaymetrics.widthPixels ;
//
//            int convertHighet = (int) hight, convertWidth = (int) width;
//
////        Resources mResources = getResources();
////        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
//
//            PdfDocument document = new PdfDocument();
//            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
//            PdfDocument.Page page = document.startPage(pageInfo);
//
//            Canvas canvas = page.getCanvas();
//
//            Paint paint = new Paint();
//            canvas.drawPaint(paint);
//
//            bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//
//            paint.setColor(Color.BLUE);
//            canvas.drawBitmap(bitmap, 0, 0 , null);
//            document.finishPage(page);
//
//            // write the document content
//            String targetPdf = "/sdcard/pdffromScroll.pdf";
//            File filePath;
//            filePath = new File(targetPdf);
//            try {
//                document.writeTo(new FileOutputStream(filePath));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//            }
//
//            // close the document
//            document.close();
//            Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();
//
//            openGeneratedPDF();
//
//        }
//
//        private void openGeneratedPDF(){
//            File file = new File("/sdcard/pdffromScroll.pdf");
//            if (file.exists())
//            {
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                Uri uri = Uri.fromFile(file);
//                intent.setDataAndType(uri, "application/pdf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                try
//                {
//                    startActivity(intent);
//                }
//                catch(ActivityNotFoundException e)
//                {
//                    Toast.makeText(ScrollActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//
//    }
}
