import gui.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BusServiceInterval implements BusService {

    public static final int ARRIVAL_DELAY = 5;
    public static final int DEPARTURE_DELAY = 10;

    private List<Bus> busList;
    private Terminal terminal;
    private Canvas panel;

    public BusServiceInterval(Canvas panel) {
        this.panel = panel;
    }

    @Override
    public void schedule() {
        scheduleEachBus(1, busList.get(0), terminal.getArea1(), 0);
        scheduleEachBus(2, busList.get(1), terminal.getArea2(), 5);
        scheduleEachBus(3, busList.get(2), terminal.getArea3(), 7);
    }

    public void scheduleEachBus(int busNo, Bus bus, WaitingArea area, int delay) {
        TimerTask arrivalBus = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                bus.setStationed(true);
                bus.getPassengers().removeAllElements();
                System.out.println("Bus " + busNo + " has arrived!");
                panel.updateBuses(busNo, bus.isStationed());
            }
        };
        Timer timer1 = new Timer();
        timer1.scheduleAtFixedRate(arrivalBus, delay * 1000, (DEPARTURE_DELAY + ARRIVAL_DELAY) * 1000);

        TimerTask departureBus = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here

                bus.setStationed(false);
                System.out.println("Bus " + busNo + " has departed with " + bus.getPassengers().size() + " passengers" +
                        "!");
                panel.updateBuses(busNo, bus.isStationed());
            }
        };
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(departureBus, DEPARTURE_DELAY * 1000, (DEPARTURE_DELAY + ARRIVAL_DELAY) * 1000);

        TimerTask populateBus = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                if (bus.isStationed()) {
                    List<Customer> customersToRemove = new ArrayList<>();
                    for (int i = 0; i < area.getCustomers().size(); i++) {
                        Customer customer = area.getCustomers().get(i);
                        if (customer.getTicket().isChecked() && customer.getTicket().isScanned()) {
                            if (bus.addPassenger(customer)) {
                                customersToRemove.add(customer);
                                terminal.setCustomerCount(terminal.getCustomerCount() - 1);
                            }
                        }
                    }
                    area.getCustomers().removeAll(customersToRemove);
                    panel.updatePassengerCounts(busNo, bus.getPassengers().size());
                }
            }
        };
        Timer timer3 = new Timer();
        timer3.scheduleAtFixedRate(populateBus, 1 * 1000, 1 * 1000);
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
