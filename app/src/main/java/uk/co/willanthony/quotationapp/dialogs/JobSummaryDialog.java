package uk.co.willanthony.quotationapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.willanthony.quotationapp.Job;
import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.activities.AddJobActivity;
import uk.co.willanthony.quotationapp.database.JobDatabase;
import uk.co.willanthony.quotationapp.util.DisplayCost;

public class JobSummaryDialog {
    private Dialog dialog;
    private Context context;
    private TextView titleView, descriptionView, totalView;
    private Button editButton, cancelButton;
    private Job job;

    public JobSummaryDialog(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);
        this.dialog.setContentView(R.layout.job_pop_up);
        initialiseViews();
    }

    private void initialiseViews() {
        this.titleView = dialog.findViewById(R.id.titleView);
        this.descriptionView = dialog.findViewById(R.id.descriptionView);
        this.totalView = dialog.findViewById(R.id.totalView);
        this.editButton = dialog.findViewById(R.id.addButton);
        this.cancelButton = dialog.findViewById(R.id.cancelButton);
    }

    public void showPopUp(long jobID) {
        this.job = getJobFromDatabase(jobID);
        this.titleView.setText(job.getTitle());
        this.descriptionView.setText(job.getDescription());
        this.totalView.setText(new DisplayCost().setJobTotalString(job));
        setEditClickListener();
        setCancelClickListener();

        this.dialog.show();
    }

    private Job getJobFromDatabase(long jobID) {
        JobDatabase jobDatabase = new JobDatabase(context);
        return jobDatabase.getJob(jobID);
    }

    private void setEditClickListener() {
        this.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddJobActivity.class);
                intent.putExtra("jobID", job.getID());
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    private void setCancelClickListener() {
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
