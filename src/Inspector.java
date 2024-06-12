public class Inspector {
    private WaitingArea currentArea;
    private WaitingArea previousArea;

    public WaitingArea getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(WaitingArea currentArea) {
        this.currentArea = currentArea;
    }

    public WaitingArea getPreviousArea() {
        return previousArea;
    }

    public void setPreviousArea(WaitingArea previousArea) {
        this.previousArea = previousArea;
    }
}
