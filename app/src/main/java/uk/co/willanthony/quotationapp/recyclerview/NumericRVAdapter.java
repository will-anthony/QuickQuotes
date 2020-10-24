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
    private View lastClickedView = null;


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

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ButtonItemData buttonItemData = itemDataArray[position];
        holder.getButton().setText(buttonItemData.getButtonText());
        holder.getButton().setTextColor(Color.parseColor(buttonItemData.getTextColor()));
        holder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                int tag = itemDataArray[position].getTag();
                swapCheckedViewBackGround(view);
                rvButtonListener.rVButtonClick(position, tag);
            }
        });
    }

    public void removeCheckedBackground() {
        lastClickedView.setBackgroundResource(R.drawable.blank_background);
        lastClickedView = null;
    }

    private void swapCheckedViewBackGround(View view) {
        if(lastClickedView != null){
            lastClickedView.setBackgroundResource(R.drawable.blank_background);
        }
        view.setBackgroundResource(R.drawable.edit_button_style);
        this.lastClickedView = view;
    }

    @Override
    public int getItemCount() {
        return itemDataArray.length;
    }

    // inner class
    class MyViewHolder extends RecyclerView.ViewHolder {

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

    // interface
    public interface RVButtonListener {
        void rVButtonClick(int position, int tag);
    }
}
