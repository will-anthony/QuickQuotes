package uk.co.willanthony.quotationapp.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.willanthony.quotationapp.Quote;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.activities.MainActivity;
import uk.co.willanthony.quotationapp.activities.QuoteActivity;
import uk.co.willanthony.quotationapp.activities.QuotePDFActivity;
import uk.co.willanthony.quotationapp.database.QuoteDatabase;

public class MainMenuRVAdapter extends RecyclerView.Adapter<MainMenuRVAdapter.QuoteItemViewHolder> implements DeletableAdapter {

    private LayoutInflater inflater;
    private List<Quote> quotes;
    private Context context;
    private Quote deletedQuote;
    private int deletedPosition;
    private MainActivity mainActivity;

    public MainMenuRVAdapter(List<Quote> quotes, MainActivity mainActivity, Context context){
        this.context = context;
        this.mainActivity = mainActivity;
        this.inflater = LayoutInflater.from(context);
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public QuoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.quote_item,viewGroup,false);
        return new QuoteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteItemViewHolder viewHolder, int i) {
        long id = quotes.get(i).getID();
        String title = quotes.get(i).getTitle();
        String date = quotes.get(i).getDate();
        String time = quotes.get(i).getTime();
        Log.d("date on ", "Date on: "+date);

        viewHolder.quoteID.setText(String.valueOf(quotes.get(i).getID()));
        viewHolder.quoteTitle.setText(title);
        viewHolder.quoteDate.setText(date);
        viewHolder.quoteTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void delete(int position) {
        this.deletedQuote = quotes.get(position);
        deletedPosition = position;
        quotes.remove(position);
        mainActivity.shouldImageBeVisible();
        notifyItemRemoved(position);
        QuoteDatabase quoteDatabase = new QuoteDatabase(context);
        quoteDatabase.deleteQuote(deletedQuote.getID());
    }

    public class QuoteItemViewHolder extends RecyclerView.ViewHolder{
        TextView quoteID, quoteTitle, quoteDate, quoteTime;
        Button documentButton, editButton;

        public QuoteItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.quoteID = itemView.findViewById(R.id.quoteID);
            this.quoteTitle = itemView.findViewById(R.id.quoteTitle);
            this.quoteDate = itemView.findViewById(R.id.quoteDate);
            this.quoteTime = itemView.findViewById(R.id.quoteTime);
            this.documentButton = itemView.findViewById(R.id.documentButton);

            this.documentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), QuotePDFActivity.class);
                    i.putExtra("quoteID",quotes.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(i);
                }
            });

            this.editButton = itemView.findViewById(R.id.penButton);
            this.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), QuoteActivity.class);
                    i.putExtra("quoteID",quotes.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
