package android.iris.graph;

/**
 * Created by rohit on 16/2/16.
 */
public class BarData {

    private float credit;
    private float debit;
    private int index;
    private String label;


    public BarData(float credit, float debit, int index, String label) {
        this.credit = credit;
        this.debit = debit;
        this.index = index;
        this.label = label;
    }


    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public float getDebit() {
        return debit;
    }

    public void setDebit(float debit) {
        this.debit = debit;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
