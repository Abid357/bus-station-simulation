public class Ticket {

    private int bus;
    private boolean isScanned;
    private boolean isChecked;

    public Ticket(int bus) {
        this.bus = bus;
        this.isChecked = false;
        this.isScanned = false;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
