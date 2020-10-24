package uk.co.willanthony.quotationapp.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.willanthony.quotationapp.util.DisplayCost;
import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.JobPopUpController;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.database.QuoteDatabase;

public class QuoteRVAdapter extends RecyclerView.Adapter<QuoteRVAdapter.JobItemViewHolder> {

    private LayoutInflater inflater;
    private List<Job> jobs;
    private Context context;
    private Job deletedQuote;
    private int deletedPosition;
    private DisplayCost displayCost;

    public QuoteRVAdapter(Context context, List<Job> jobs){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jobs = jobs;
        this.displayCost = new DisplayCost();
    }

    @NonNull
    @Override
    public QuoteRVAdapter.JobItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.job_item,viewGroup,false);
        return new QuoteRVAdapter.JobItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteRVAdapter.JobItemViewHolder viewHolder, int i) {

        Job currentJob = jobs.get(i);
        if (currentJob != null) {

            String title = currentJob.getTitle();
            viewHolder.jobTitle.setText(title);
            viewHolder.jobPrice.setText(displayCost.setJobTotalString(currentJob));
        }
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public Context getContext() {
        return context;
    }

    public void deleteItem(int position) {
        this.deletedQuote = jobs.get(position);
        deletedPosition = position;
        jobs.remove(position);
        notifyItemRemoved(position);
        QuoteDatabase quoteDatabase = new QuoteDatabase(context);
        quoteDatabase.deleteQuote(deletedQuote.getID());
    }

    public class JobItemViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, jobPrice;
        JobPopUpController jobPopUpController;

        public JobItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.jobTitle = itemView.findViewById(R.id.jobTitle);
            this.jobPrice = itemView.findViewById(R.id.jobPrice);
            this.jobPopUpController = new JobPopUpController(context);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    long jobID = jobs.get(getAdapterPosition()).getID();
                    jobPopUpController.showPopUp(jobID);

                }
            });
        }
    }
}
