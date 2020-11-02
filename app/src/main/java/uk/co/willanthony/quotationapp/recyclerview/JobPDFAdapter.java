package uk.co.willanthony.quotationapp.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.util.DisplayCost;

public class JobPDFAdapter extends RecyclerView.Adapter<JobPDFAdapter.MyViewHolder> {

    private List<Job> jobList;

    public JobPDFAdapter(List<Job> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobPDFAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View arrayItem = layoutInflater.inflate(R.layout.job_pdf_item_view, parent, false);
        return new MyViewHolder(arrayItem);
    }

    @Override
    public void onBindViewHolder(@NonNull JobPDFAdapter.MyViewHolder holder, int position) {
        Job job = jobList.get(position);

        DisplayCost displayCost = new DisplayCost();
        holder.getJobTitle().setText(job.getTitle());
        holder.getJobDescription().setText(job.getDescription());
        holder.getJobPrice().setText(displayCost.setJobTotalString(job));

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView jobTitle, jobDescription, jobPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.jobTitle = itemView.findViewById(R.id.jobTitleView);
            this.jobDescription = itemView.findViewById(R.id.descriptionView);
            this.jobPrice = itemView.findViewById(R.id.costView);
        }

        public TextView getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(TextView jobTitle) {
            this.jobTitle = jobTitle;
        }

        public TextView getJobDescription() {
            return jobDescription;
        }

        public void setJobDescription(TextView jobDescription) {
            this.jobDescription = jobDescription;
        }

        public TextView getJobPrice() {
            return jobPrice;
        }

        public void setJobPrice(TextView jobPrice) {
            this.jobPrice = jobPrice;
        }
    }
}
