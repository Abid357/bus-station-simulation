import java.util.List;

public class BusServiceFull implements BusService {

    private List<Bus> busList;
    private Terminal terminal;

    @Override
    public void schedule() {

    }

    @Override
    public void addBuses(List<Bus> busList) {
        this.busList = busList;
    }

    @Override
    public void addTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
}
