package uk.co.willanthony.quotationapp.recyclerview.dialog_rv;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.recyclerview.ButtonItemData;
import uk.co.willanthony.quotationapp.recyclerview.NumericRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasAddedItemData;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasItemData;

public class ExtrasToAddRVAdapter extends RecyclerView.Adapter<ExtrasToAddRVAdapter.MyViewHolder> {
    private List<ExtrasItemData> itemDataList;
    private Context context;
    private String textColor;
    private ExtrasAddedRVAdapter extrasAddedRVAdapter;
    private LinearLayoutManager layoutManager;
    private ButtonItemData[] numberButtons;

    public ExtrasToAddRVAdapter(List<ExtrasItemData> itemDataList, Context context, ExtrasAddedRVAdapter extrasAddedRVAdapter) {
        this.itemDataList = itemDataList;
        this.context = context;
        this.textColor = "#F0F5FB";
        numberButtons = initNumberButtons();
        this.extrasAddedRVAdapter = extrasAddedRVAdapter;
    }

    private ButtonItemData[] initNumberButtons() {
        return new ButtonItemData[]{
                new ButtonItemData("1", textColor, 1, 0),
                new ButtonItemData("2", textColor, 2, 0),
                new ButtonItemData("3", textColor, 3, 0),
                new ButtonItemData("4", textColor, 4, 0),
                new ButtonItemData("5", textColor, 5, 0),
                new ButtonItemData("6", textColor, 6, 0),
                new ButtonItemData("7", textColor, 7, 0),
                new ButtonItemData("8", textColor, 8, 0),
                new ButtonItemData("9", textColor, 9, 0),
        };
    }

    public void deselectAllNumberButtons() {
        for(int index = 0; index < getItemCount(); index ++) {
            numberButtons[index].setSelected(false);
            notifyItemChanged(index);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View arrayItem = layoutInflater.inflate(R.layout.extras_item_view, parent, false);
        return new MyViewHolder(arrayItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ExtrasItemData extrasItemData = itemDataList.get(position);
        holder.getTextView().setText(extrasItemData.getName());
        int color = R.color.linkWater;

        holder.getTextView().setTextColor(Color.parseColor("#F0F5FB"));
    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }

    // inner class
    public class MyViewHolder extends RecyclerView.ViewHolder implements NumericRVAdapter.RVButtonListener {

        private static final String TAG = "MyViewHolder";
        private TextView extrasTitleView;
        private RecyclerView numberRecyclerView;

        private NumericRVAdapter numericRVAdapter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.extrasTitleView = itemView.findViewById(R.id.extrasTitleView);
            this.numberRecyclerView = itemView.findViewById(R.id.extrasNumberRV);
            this.numericRVAdapter = new NumericRVAdapter(numberButtons, this);
            this.numberRecyclerView.setAdapter(numericRVAdapter);
            layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            this.numberRecyclerView.setLayoutManager(layoutManager);
        }

        public TextView getTextView() {
            return extrasTitleView;
        }

        @Override
        public void rVButtonClick(int position, int tag) {

            String extraName = itemDataList.get(getAdapterPosition()).getName();
            float price = itemDataList.get(getAdapterPosition()).getPrice();
            float amount = numberButtons[position].getValue();
            ExtrasAddedItemData extrasToAdd = new ExtrasAddedItemData(extraName, price, (int) amount);
            ExtrasToAddRVAdapter.this.extrasAddedRVAdapter.addExtras(extrasToAdd);

        }
    }
}
