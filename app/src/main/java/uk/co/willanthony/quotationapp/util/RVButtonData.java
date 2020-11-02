package uk.co.willanthony.quotationapp.util;

import java.util.ArrayList;
import java.util.List;

import uk.co.willanthony.quotationapp.recyclerview.ButtonItemData;
import uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data.ExtrasItemData;

public class RVButtonData {

    private String textColor;
    private ButtonItemData[] workersItemData, hoursItemData, frequencyItemData, percentageItemData;
    private List<ExtrasItemData> machineryItemData, materialsItemData;


    public RVButtonData(String textColor) {
        this.textColor = textColor;
        this.workersItemData = setWorkersItemData();
        this.hoursItemData = setHoursItemData();
        this.frequencyItemData = setFrequencyItemData();
        this.percentageItemData = setPercentageItemData();
        this.machineryItemData = setMachineryItemData();
        this.materialsItemData = setMaterialsItemData();
        this.machineryItemData = setMachineryItemData();
        this.materialsItemData = setMaterialsItemData();
    }

    private ButtonItemData[] setWorkersItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("1", textColor, 15, 1),
                new ButtonItemData("2", textColor, 30, 1),
                new ButtonItemData("3", textColor, 45, 1),
                new ButtonItemData("4", textColor, 60, 1),
                new ButtonItemData("5", textColor, 75, 1),
                new ButtonItemData("6", textColor, 90, 1),
                new ButtonItemData("7", textColor, 105, 1),
                new ButtonItemData("8", textColor, 120, 1),
                new ButtonItemData("9", textColor, 135, 1),
                new ButtonItemData("10", textColor, 150, 1),
        };
    }

    private ButtonItemData[] setHoursItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("0.5", textColor, 0.5f, 2),
                new ButtonItemData("1", textColor, 1f, 2),
                new ButtonItemData("1.5", textColor, 1.5f, 2),
                new ButtonItemData("2", textColor, 2f, 2),
                new ButtonItemData("2.5", textColor, 2.5f, 2),
                new ButtonItemData("3", textColor, 3f, 2),
                new ButtonItemData("4", textColor, 4f, 2),
                new ButtonItemData("5", textColor, 5f, 2),
                new ButtonItemData("6", textColor, 6f, 2),
                new ButtonItemData("8", textColor, 8f, 2),
                new ButtonItemData("10", textColor, 10f, 2),
                new ButtonItemData("12", textColor, 12f, 2),
                new ButtonItemData("16", textColor, 16f, 2),
                new ButtonItemData("24", textColor, 24f, 2),
                new ButtonItemData("32", textColor, 32f, 2),
                new ButtonItemData("40", textColor, 40f, 2),
        };
    }

    private ButtonItemData[] setFrequencyItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("1", textColor, 1f, 5),
                new ButtonItemData("2", textColor, 2f, 5),
                new ButtonItemData("3", textColor, 3f, 5),
                new ButtonItemData("4", textColor, 4f, 5),
                new ButtonItemData("5", textColor, 5f, 5),
                new ButtonItemData("6", textColor, 6f, 5),
                new ButtonItemData("8", textColor, 8f, 5),
                new ButtonItemData("10", textColor, 10f, 5),
                new ButtonItemData("12", textColor, 12f, 5),
                new ButtonItemData("14", textColor, 14f, 5),
                new ButtonItemData("16", textColor, 16f, 5),
                new ButtonItemData("18", textColor, 18f, 5),
                new ButtonItemData("20", textColor, 20f, 5),
                new ButtonItemData("25", textColor, 25f, 5),
        };
    }

    private ButtonItemData[] setPercentageItemData() {
        return new ButtonItemData[]{
                new ButtonItemData("10%", textColor, 10f, 6),
                new ButtonItemData("15%", textColor, 15f, 6),
                new ButtonItemData("20%", textColor, 20f, 6),
                new ButtonItemData("25%", textColor, 25f, 6),
                new ButtonItemData("30%", textColor, 30f, 6),
                new ButtonItemData("35%", textColor, 35f, 6),
                new ButtonItemData("40%", textColor, 40f, 6),
                new ButtonItemData("45%", textColor, 45f, 6),
                new ButtonItemData("50%", textColor, 50f, 6),
                new ButtonItemData("60%", textColor, 60f, 6),
                new ButtonItemData("80%", textColor, 80f, 6),
                new ButtonItemData("100%", textColor, 100f, 6),
        };
    }

    private List<ExtrasItemData> setMachineryItemData() {
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

    private List<ExtrasItemData> setMaterialsItemData() {
        List<ExtrasItemData> extrasList = new ArrayList<>();

        extrasList.add(new ExtrasItemData("white marker fluid", 30f));
        extrasList.add(new ExtrasItemData("blue marker fluid", 70f));
        extrasList.add(new ExtrasItemData("postcrete", 5f));
        extrasList.add(new ExtrasItemData("3\" post", 13f));
        extrasList.add(new ExtrasItemData("4\" post", 20f));
        extrasList.add(new ExtrasItemData("4' panel", 25f));
        extrasList.add(new ExtrasItemData("5' panel", 26f));
        extrasList.add(new ExtrasItemData("6' panel", 27f));
        extrasList.add(new ExtrasItemData("4\" rail", 11f));

        return extrasList;
    }

    public ButtonItemData[] getWorkersItemData() {
        return workersItemData;
    }

    public ButtonItemData[] getHoursItemData() {
        return hoursItemData;
    }

    public ButtonItemData[] getFrequencyItemData() {
        return frequencyItemData;
    }

    public ButtonItemData[] getPercentageItemData() {
        return percentageItemData;
    }

    public List<ExtrasItemData> getMachineryItemData() {
        return machineryItemData;
    }

    public List<ExtrasItemData> getMaterialsItemData() {
        return materialsItemData;
    }
}
