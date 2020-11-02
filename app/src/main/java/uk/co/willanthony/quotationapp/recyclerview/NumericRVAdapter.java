package uk.co.willanthony.quotationapp.recyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uk.co.willanthony.quotationapp.R;

public class NumericRVAdapter extends RecyclerView.Adapter<NumericRVAdapter.MyViewHolder> {

    private ButtonItemData[] itemDataArray;
    private RVButtonListener rvButtonListener;
    private ButtonItemData lastButtonSelected = null;


    public NumericRVAdapter(ButtonItemData[] itemDataArray, RVButtonListener rvButtonListener) {
        this.itemDataArray = itemDataArray;
        this.rvButtonListener = rvButtonListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View arrayItem = layoutInflater.inflate(R.layout.recyclerview_button_layout, parent, false);
        return new MyViewHolder(arrayItem, this.rvButtonListener);
    }

    public ButtonItemData[] getItemDataArray() {
        return itemDataArray;
    }
    public String getBooleanString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < itemDataArray.length; i++) {
            if(itemDataArray[i].isSelected()) {
                stringBuilder.append("t");
            } else {
                stringBuilder.append("f");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ButtonItemData buttonItemData = itemDataArray[position];
        if (!buttonItemData.isSelected()) {
            holder.getButton().setBackgroundResource(R.drawable.blank_background);
        } else {
            holder.getButton().setBackgroundResource(R.drawable.edit_button_style);
        }
        holder.getButton().setText(buttonItemData.getButtonText());
        holder.getButton().setTextColor(Color.parseColor(buttonItemData.getTextColor()));
        holder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                ButtonItemData itemData = itemDataArray[position];
                int tag = itemData.getTag();
                changeSelectedBackground(view, buttonItemData);

                rvButtonListener.rVButtonClick(position, tag);
            }
        });
    }

    private void changeSelectedBackground(View view, ButtonItemData buttonItemData) {
        view.setBackgroundResource(R.drawable.edit_button_style);
        buttonItemData.setSelected(true);
        if(lastButtonSelected != null) {
            lastButtonSelected.setSelected(false);
            notifyDataSetChanged();
        }
        lastButtonSelected = buttonItemData;
    }

    @Override
    public int getItemCount() {
        return itemDataArray.length;
    }

    // inner class
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "MyViewHolder";
        private Button button;
        RVButtonListener rvButtonListener;

        public MyViewHolder(@NonNull View itemView, RVButtonListener rvButtonListener) {
            super(itemView);

            this.button = itemView.findViewById(R.id.recycleView_button);
            this.rvButtonListener = rvButtonListener;
        }

        public Button getButton() {
            return button;
        }
    }

    public void setLastButtonSelected(ButtonItemData lastButtonSelected) {
        this.lastButtonSelected = lastButtonSelected;
    }

    // interface
    public interface RVButtonListener {
        void rVButtonClick(int position, int tag);
    }
}
