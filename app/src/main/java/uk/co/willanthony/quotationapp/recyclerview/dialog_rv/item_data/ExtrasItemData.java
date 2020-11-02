package uk.co.willanthony.quotationapp.recyclerview.dialog_rv.item_data;

public class ExtrasItemData {

    String name;
    float price;

    public ExtrasItemData(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
