package uk.co.willanthony.quotationapp.dialogs;

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

import uk.co.willanthony.quotationapp.R;
import uk.co.willanthony.quotationapp.util.SwipeToDeleteCallback;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.ExtrasAddedRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.ExtrasToAddRVAdapter;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasAddedItemData;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasItemData;

public class ExtrasDialog {
    private Dialog dialog;
    private Context context;
    private TextView extrasTitleView;
    private RecyclerView extrasToAddRV, extrasAddedRV;
    private ExtrasAddedRVAdapter extrasAddedRVAdapter;
    private ExtrasToAddRVAdapter extrasToAddRVAdapter;
    private String title;
    private List<ExtrasItemData> extrasList;
    private List<ExtrasAddedItemData> extrasAdded;
    private Button addButton;
    private DialogResult dialogResult;

    public ExtrasDialog(Context context, String title, List<ExtrasItemData> extrasList ) {
        this.context = context;
        this.dialog = new Dialog(context);
        this.dialog.setContentView(R.layout.extras_selector_dialog);
        this.title = title;
        this.extrasList = extrasList;
        this.extrasAdded = new ArrayList<>();
        initialiseViews();
    }

    private void initialiseViews() {

        setUpExtrasAddedRV();
        setUpExtrasToAddRV();
        setUpAddButton();
    }

    private void setUpExtrasToAddRV() {
        this.extrasTitleView = dialog.findViewById(R.id.extrasTitleView);
        this.extrasToAddRVAdapter = new ExtrasToAddRVAdapter(extrasList, context, (ExtrasAddedRVAdapter) extrasAddedRV.getAdapter());
        this.extrasToAddRV = dialog.findViewById(R.id.extrasRecyclerView);
        this.extrasToAddRV.setAdapter(extrasToAddRVAdapter);
        extrasToAddRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    private void setUpExtrasAddedRV() {
        this.extrasAddedRV = dialog.findViewById(R.id.extrasAddedRecyclerView);
        this.extrasAddedRVAdapter = new ExtrasAddedRVAdapter(extrasAdded);

        this.extrasAddedRV.setAdapter(extrasAddedRVAdapter);
        this.extrasAddedRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(extrasAddedRVAdapter, context));
        itemTouchHelper.attachToRecyclerView(extrasAddedRV);

    }

    private void setUpAddButton() {
        this.addButton = dialog.findViewById(R.id.addButton);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( dialogResult != null ){
                    dialogResult.finish(extrasAdded);
                }
                extrasToAddRVAdapter.deselectAllNumberButtons();
                dialog.dismiss();
                extrasAddedRVAdapter.resetRVEntries();
            }
        });
    }

    public void setDialogResult(DialogResult dialogResult){
        this.dialogResult = dialogResult;
    }

    public interface DialogResult{
        void finish(List<ExtrasAddedItemData> result);
    }

    public void showPopUp() {
        this.extrasTitleView.setText(this.title);
        this.dialog.show();
    }
}
