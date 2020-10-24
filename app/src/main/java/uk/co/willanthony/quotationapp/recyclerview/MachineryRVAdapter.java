package uk.co.willanthony.quotationapp.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.willanthony.quotationapp.R;

public class MachineryRVAdapter extends RecyclerView.Adapter<MachineryRVAdapter.MyViewHolder> {
    private List<ExtrasItemData> itemDataList;
    private Context context;
    private MachineryAddedRVAdapter machineryAddedRVAdapter;

    public MachineryRVAdapter(List<ExtrasItemData> itemDataList, Context context, MachineryAddedRVAdapter machineryAddedRVAdapter) {
        this.itemDataList = itemDataList;
        this.context = context;
        this.machineryAddedRVAdapter = machineryAddedRVAdapter;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View arrayItem = layoutInflater.inflate(R.layout.extras_item_view, parent, false);
        return new MyViewHolder(arrayItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ExtrasItemData extrasItemData = itemDataList.get(position);
        holder.getTextView().setText(extrasItemData.getName());
        int color = R.color.linkWater;

        holder.getTextView().setTextColor(Color.parseColor("#F0F5FB"));
//        holder.getButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                int tag = itemDataArray[position].getTag();
//                v.setBackgroundResource(R.drawable.edit_button_style);
//                rvButtonListener.rVButtonClick(position, tag);
//                System.out.println("Button " + position + ", Tag " + tag);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }

    public void changeBackground() {

    }

    public

    // inner class
    class MyViewHolder extends RecyclerView.ViewHolder implements NumericRVAdapter.RVButtonListener {

        private static final String TAG = "MyViewHolder";
        private TextView extrasTitleView;
        private RecyclerView numberRecyclerView;
        private ButtonItemData[] numberButtons;
        private String textColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textColor = "#F0F5FB";
            this.numberButtons = initNumberButtons();
            this.extrasTitleView = itemView.findViewById(R.id.extrasTitleView);
            this.numberRecyclerView = itemView.findViewById(R.id.extrasNumberRV);
            this.numberRecyclerView.setAdapter(new NumericRVAdapter(numberButtons, this));
            this.numberRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
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

        public TextView getTextView() {
            return extrasTitleView;
        }

        public RecyclerView getNumberRecyclerView() {
            return numberRecyclerView;
        }

        @Override
        public void rVButtonClick(int position, int tag) {

            String machineryName = itemDataList.get(getAdapterPosition()).getName();
            float price = itemDataList.get(getAdapterPosition()).getPrice();
            float amount = numberButtons[position].getValue();
            ExtrasAddedItemData machineryToAdd = new ExtrasAddedItemData(machineryName, price, (int)amount);
            MachineryRVAdapter.this.machineryAddedRVAdapter.addMachinery(machineryToAdd);

        }
    }
}
