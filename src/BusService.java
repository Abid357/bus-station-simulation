import java.util.List;

public interface BusService {
    void schedule();

    void addBuses(List<Bus> busList);

    void addTerminal(Terminal terminal);
}
