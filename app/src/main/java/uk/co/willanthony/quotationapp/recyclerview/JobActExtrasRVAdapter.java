package uk.co.willanthony.quotationapp.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.activities.AddJobActivity;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasAddedItemData;
import uk.co.willanthony.quotationapp.util.DisplayCost;

public class JobActExtrasRVAdapter extends RecyclerView.Adapter<JobActExtrasRVAdapter.MyViewHolder> implements DeletableAdapter {
    private List<ExtrasAddedItemData> itemDataList;
    private AddJobActivity addJobActivity;


    public JobActExtrasRVAdapter(List<ExtrasAddedItemData> itemDataList, AddJobActivity addJobActivity) {
        this.itemDataList = itemDataList;
        this.addJobActivity = addJobActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View arrayItem = layoutInflater.inflate(R.layout.extra_in_job_activity, parent, false);
        return new MyViewHolder(arrayItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ExtrasAddedItemData extrasItemData = itemDataList.get(position);

        DisplayCost displayCost = new DisplayCost();
        holder.getExtrasAddedView().setText(extrasItemData.getNumber() + " x " + extrasItemData.getName());
        holder.getExtrasTotalView().setText(displayCost.setExtrasString(extrasItemData));

        int color = R.color.linkWater;
        holder.getExtrasAddedView().setTextColor(Color.parseColor("#F0F5FB"));

    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }

    public void addExtras(ExtrasAddedItemData extrasAddedItemData) {

        for(int index = 0; index < itemDataList.size(); index++) {
            if(extrasAddedItemData.getName().equals(itemDataList.get(index).getName())) {
                delete(index);
            }
        }

        itemDataList.add(0, extrasAddedItemData);
        notifyItemInserted(0);

    }

    public String getExtrasString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int index = 0; index < getItemCount(); index++) {
            stringBuilder.append(itemDataList.get(index).getName());
            stringBuilder.append("~");
            stringBuilder.append(itemDataList.get(index).getNumber());
            stringBuilder.append("/");
        }
        Toast.makeText(this.addJobActivity,stringBuilder, Toast.LENGTH_LONG).show();
        return stringBuilder.toString();
    }

    public List<ExtrasAddedItemData> getItemDataList() {
        return itemDataList;
    }

    @Override
    public void delete(int position) {
        ExtrasAddedItemData item = itemDataList.get(position);

        itemDataList.remove(position);
        notifyItemRemoved(position);
        this.addJobActivity.deleteMachineryItem();
        this.addJobActivity.deleteMaterialsItem();
    }

    // inner class
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView extrasAddedView;
        private TextView extrasTotalView;
        private String textColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textColor = "#F0F5FB";
            this.extrasAddedView = itemView.findViewById(R.id.extrasAddedView);
            this.extrasTotalView = itemView.findViewById(R.id.extrasTotalView);
        }

        public TextView getExtrasAddedView() {
            return extrasAddedView;
        }

        public TextView getExtrasTotalView() {
            return extrasTotalView;
        }
    }
}
