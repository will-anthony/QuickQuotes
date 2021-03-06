package uk.co.willanthony.quotationapp.recyclerview;

public class ButtonItemData {

    private String buttonText;
    private String textColor;
    private float value;
    private int tag;
    private boolean selected;

    public ButtonItemData(String buttonText, String textColor, float value, int tag) {
        this.buttonText = buttonText;
        this.textColor = textColor;
        this.value = value;
        this.tag = tag;
        this.selected = false;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public float getValue() {
        return value;
    }

    public int getTag() {
        return tag;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
