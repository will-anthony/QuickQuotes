package uk.co.willanthony.quotationapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.co.willanthony.quotationapp.activities.AddJobActivity;
import uk.co.willanthony.quotationapp.recyclerview.ExtrasAddedItemData;
import uk.co.willanthony.quotationapp.recyclerview.ExtrasItemData;
import uk.co.willanthony.quotationapp.recyclerview.MachineryAddedRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.MachineryRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.SwipeToDeleteCallback;

public class MachineryDialog {
    private Dialog dialog;
    private Context context;
    private TextView extrasTitleView;
    private RecyclerView extrasRV, extrasAddedRV;
    private String title;
    private List<ExtrasItemData> machineryList;
    private List<ExtrasAddedItemData> machineryAdded;
    private Button addButton;
    OnMyDialogResult mDialogResult;

    public MachineryDialog(Context context, String title) {
        this.context = context;
        this.dialog = new Dialog(context);
        this.dialog.setContentView(R.layout.extras_selector_dialog);
        this.title = title;
        this.machineryList = setExtrasList();
        this.machineryAdded = new ArrayList<>();
        initialiseViews();
    }

    private List<ExtrasItemData> setExtrasList() {
        List<ExtrasItemData> extrasList = new ArrayList<>();
        extrasList.add(new ExtrasItemData("tractor", 25f));
        extrasList.add(new ExtrasItemData("ride-on", 15f));
        extrasList.add(new ExtrasItemData("digger", 25f));
        extrasList.add(new ExtrasItemData("mini-digger", 15f));
        extrasList.add(new ExtrasItemData("strimmer", 5f));
        extrasList.add(new ExtrasItemData("line-marker", 5f));
        extrasList.add(new ExtrasItemData("hedge-cutter", 5f));
        extrasList.add(new ExtrasItemData("push-mower", 5f));
        extrasList.add(new ExtrasItemData("blower", 5f));

        return extrasList;
    }

    private void initialiseViews() {

        setUpMachineryAddedRV();
        setUpMachineryListRV();
        setUpAddButton();
    }

    private void setUpMachineryAddedRV() {
        this.extrasAddedRV = dialog.findViewById(R.id.extrasAddedRecyclerView);
        MachineryAddedRVAdapter adapter = new MachineryAddedRVAdapter(machineryAdded,context);

        this.extrasAddedRV.setAdapter(adapter);
        this.extrasAddedRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter, context));
        itemTouchHelper.attachToRecyclerView(extrasAddedRV);

    }

    private void setUpMachineryListRV() {
        this.extrasTitleView = dialog.findViewById(R.id.extrasTitleView);
        this.extrasRV = dialog.findViewById(R.id.extrasRecyclerView);
        this.extrasRV.setAdapter(new MachineryRVAdapter(machineryList, context, (MachineryAddedRVAdapter) extrasAddedRV.getAdapter()));
        extrasRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    private void setUpAddButton() {
        this.addButton = dialog.findViewById(R.id.addButton);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(List<ExtrasAddedItemData> result);
    }

    public void showPopUp() {
        this.extrasTitleView.setText(this.title);
        this.dialog.show();
    }
}
