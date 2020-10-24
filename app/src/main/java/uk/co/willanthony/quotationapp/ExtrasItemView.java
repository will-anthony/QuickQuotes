package uk.co.willanthony.quotationapp;

//public class ExtrasItemView {
//    private Context context;
//    private TextView extrasTitleView, extrasListView;
//    private RecyclerView extrasRV;
//    private String title;
//    private List<ExtrasItemData> extrasList;
//
//    public ExtrasDialog(Context context, String title) {
//        this.context = context;
//        this.dialog = new Dialog(context);
//        this.dialog.setContentView(R.layout.extras_selector_dialog);
//        this.title = title;
//        this.extrasList = setExtrasList();
//        initialiseViews();
//    }
//
//    private List<ExtrasItemData> setExtrasList() {
//        List<ExtrasItemData> extrasList = new ArrayList<>();
//        extrasList.add(new ExtrasItemData("tractor", 25f));
//        extrasList.add(new ExtrasItemData("ride-on", 15f));
//        extrasList.add(new ExtrasItemData("digger", 25f));
//        extrasList.add(new ExtrasItemData("mini-digger", 15f));
//        extrasList.add(new ExtrasItemData("strimmer", 5f));
//        extrasList.add(new ExtrasItemData("line-marker", 5f));
//        extrasList.add(new ExtrasItemData("hedge-cutter", 5f));
//        extrasList.add(new ExtrasItemData("push-mower", 5f));
//        extrasList.add(new ExtrasItemData("blower", 5f));
//
//        return extrasList;
//    }
//
//    private void initialiseViews() {
//        this.extrasTitleView = dialog.findViewById(R.id.extrasTitleView);
//        this.extrasRV = dialog.findViewById(R.id.extrasRecyclerView);
//        this.extrasRV.setAdapter(new ExtrasRVAdapter(extrasList));
//        extrasRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//
//        this.extrasListView = dialog.findViewById(R.id.extrasListView);
////        this.addButton = dialog.findViewById(R.id.addButton);
////        this.cancelButton = dialog.findViewById(R.id.cancelButton);
//    }
//
//    private void setUpRV() {
//
//    }
//
//    public void showPopUp() {
//        this.extrasTitleView.setText(this.title);
//        this.dialog.show();
//    }
//
//}
