import gui.Canvas;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class Driver {

    public static Random random = new Random();
    public static Terminal terminal = new Terminal();
    public static Inspector inspector = new Inspector();
    public static List<Customer> world = new ArrayList<>();

    public static void populateWith150Customers() {
        for (int i = 0; i < 150; i++) {
            world.add(new Customer());
        }
    }

    public static void main(String[] arg) {

        Canvas panel = new Canvas();

        // call this function to populate with 150 people at the beginning of simulation
//        populateWith150Customers();

        // change the constructor to whichever implementation you want
        BusService busService = new BusServiceInterval(panel);
        Bus bus1 = new Bus(Bus.NUMBER_1);
        Bus bus2 = new Bus(Bus.NUMBER_2);
        Bus bus3 = new Bus(Bus.NUMBER_3);
        busService.addBuses(Arrays.asList(bus1, bus2, bus3));
        busService.addTerminal(terminal);
        busService.schedule();

        // TimerTask is actually a Thread that will be run by Timer class
        TimerTask customerGenerator = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                synchronized (world) {
                    world.add(new Customer());
                }
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = random.nextInt(3) + 1;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(customerGenerator, delay,
                intervalPeriod * 500);

        TimerTask terminalEntrance = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                synchronized (world) {
                    Iterator<Customer> iterator = world.iterator();
                    while (iterator.hasNext()) {
                        Customer customer = iterator.next();
                        if (terminal.addCustomerToQueue(customer)) {
                            iterator.remove();
                        } else {
                            break;
                        }
                    }

                    int booth1 = terminal.getBooth1().getQueue().size();
                    int booth2 = terminal.getBooth2().getQueue().size();
                    int booth3 = terminal.getBooth3().getQueue().size();
                    int foyer = terminal.getFoyer().size();
                    int area1 = terminal.getArea1().getCustomers().size();
                    ;
                    int area2 = terminal.getArea2().getCustomers().size();
                    ;
                    int area3 = terminal.getArea3().getCustomers().size();
                    int terminalCount = terminal.getCustomerCount();
                    int outside = world.size();

                    System.out.println("Booth1=" + booth1 +
                            " Booth2=" + booth2 +
                            " Booth3=" + booth3 +
                            " Foyer=" + foyer +
                            " Area1=" + area1 +
                            " Area2=" + area2 +
                            " Area3=" + area3 +
                            " Terminal=" + terminalCount +
                            " Outside=" + outside);
                    panel.updateCustomerCounts(booth1, booth2, booth3, foyer, area1, area2, area3, terminalCount,
                            outside);
                    panel.updateTerminalGates(terminal.shouldTerminalGatesClose());
                }
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = 1;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(terminalEntrance, delay,
                intervalPeriod * 1000);

        // TimerTask is actually a Thread that will be run by Timer class
        TimerTask boothBreaker = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here

                if (random.nextInt(4) == 0 && terminal.getBooth1().isWorking()) {
                    terminal.getBooth1().setWorking(false);
                    terminal.distributeCustomersFromBooth1(terminal.getBooth1().getQueue().size());
                    System.out.println("Booth 1 broke! Repairing in process...");
                    panel.updateBooth1(false);

                    TimerTask boothRepairer = new TimerTask() {
                        @Override
                        public void run() {
                            // task to run goes here
                            terminal.getBooth1().setWorking(true);
                            System.out.println("Booth 1 has been repaired!");
                            panel.updateBooth1(true);
                        }
                    };

                    Timer timer = new Timer();
                    long delay = random.nextInt(10) + 10;
                    ;
                    // schedules the task to be run in an interval
                    timer.schedule(boothRepairer, delay * 1000);
                }
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = 10;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(boothBreaker, delay,
                intervalPeriod * 1000);

        // TimerTask is actually a Thread that will be run by Timer class
        TimerTask ticketGeneratorBooth1 = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                if (!terminal.getBooth1().isWorking())
                    return;

                int ticketNo = random.nextInt(3) + 1;
                terminal.serveCustomer(1, ticketNo);
                panel.updateTicketForBooth1(ticketNo);
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = random.nextInt(5) + 1;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(ticketGeneratorBooth1, delay,
                intervalPeriod * 1000);

        TimerTask ticketGeneratorBooth2 = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                if (!terminal.getBooth2().isWorking())
                    return;

                int ticketNo = random.nextInt(3) + 1;
                terminal.serveCustomer(2, ticketNo);
                panel.updateTicketForBooth2(ticketNo);
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = random.nextInt(3) + 2;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(ticketGeneratorBooth2, delay,
                intervalPeriod * 1000);

        TimerTask ticketGeneratorBooth3 = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                if (!terminal.getBooth3().isWorking())
                    return;

                int ticketNo = random.nextInt(3) + 1;
                terminal.serveCustomer(3, ticketNo);
                panel.updateTicketForBooth3(ticketNo);
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = random.nextInt(3) + 2;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(ticketGeneratorBooth3, delay,
                intervalPeriod * 1000);

        TimerTask bathroomBreakerBooth2 = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here

                if (random.nextInt(5) == 0) { // 20% probability
                    terminal.getBooth2().setWorking(false);
                    System.out.println("Booth 2 staff is on a bathroom break!");
                    panel.updateBooth2Staff(false);

                    TimerTask bathroomBreakOver = new TimerTask() {
                        @Override
                        public void run() {
                            // task to run goes here
                            terminal.getBooth2().setWorking(true);
                            System.out.println("Booth 2 staff is back from bathroom break!");
                            panel.updateBooth2Staff(true);
                        }
                    };

                    Timer timer = new Timer();
                    long delay = random.nextInt(10) + 20;
                    // schedules the task to be run in an interval
                    timer.schedule(bathroomBreakOver, delay * 1000);
                }
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = 10;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(bathroomBreakerBooth2, delay,
                intervalPeriod * 1000);

        TimerTask bathroomBreakerBooth3 = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here

                if (random.nextInt(5) == 0) { // 20% probability
                    terminal.getBooth3().setWorking(false);
                    System.out.println("Booth 3 staff is on a bathroom break!");
                    panel.updateBooth3Staff(false);

                    TimerTask bathroomBreakOver = new TimerTask() {
                        @Override
                        public void run() {
                            // task to run goes here
                            terminal.getBooth3().setWorking(true);
                            System.out.println("Booth 3 staff is back from bathroom break!");
                            panel.updateBooth3Staff(true);
                        }
                    };

                    Timer timer = new Timer();
                    long delay = random.nextInt(10) + 20;
                    // schedules the task to be run in an interval
                    timer.schedule(bathroomBreakOver, delay * 1000);
                }
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = 10;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(bathroomBreakerBooth3, delay,
                intervalPeriod * 1000);

        TimerTask foyerProcessor = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here

                terminal.moveCustomersToWaitingAreas();
            }
        };
        timer = new Timer();
        delay = 0;
        intervalPeriod = 1;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(foyerProcessor, delay,
                intervalPeriod * 1000);

        inspector.setCurrentArea(terminal.getArea1());

        TimerTask inspectorArea = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here

                if (inspector.getCurrentArea().getNumber() == Bus.NUMBER_1 || inspector.getCurrentArea().getNumber() == Bus.NUMBER_3) {
                    inspector.setPreviousArea(inspector.getCurrentArea());
                    inspector.setCurrentArea(terminal.getArea2());
                } else if (inspector.getCurrentArea().getNumber() == Bus.NUMBER_2) {
                    if (inspector.getPreviousArea().getNumber() == Bus.NUMBER_1)
                        inspector.setCurrentArea(terminal.getArea3());
                    else
                        inspector.setCurrentArea(terminal.getArea1());
                    inspector.setPreviousArea(terminal.getArea2());
                }

                System.out.println("Inspector moved to waiting area " + inspector.getCurrentArea().getNumber() + "!");
                panel.updateInspector(inspector.getCurrentArea().getNumber());
            }
        };

        timer = new Timer();
        delay = 0;
        intervalPeriod = 10;
        timer.scheduleAtFixedRate(inspectorArea, delay, intervalPeriod * 1000);

        TimerTask inspection = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                WaitingArea area = inspector.getCurrentArea();
                synchronized (area.getCustomers()) {
                    int caught = 0;
                    int scanned = 0;
                    synchronized (area.getCustomers()) {
                        for (int i = 0; i < area.getCustomers().size(); i++) {
                            Customer customer = area.getCustomers().get(i);
                            if (!customer.getTicket().isScanned()) {
                                caught++;
                                customer.getTicket().setScanned(true);
                            }
                            if (!customer.getTicket().isChecked()) {
                                customer.getTicket().setChecked(true);
                                scanned++;
                            }
                        }
                    }

                    panel.updateInspection(area.getNumber(), scanned, caught);

                    if (caught != 0)
                        System.out.println(caught + " passengers were caught not scanning their ticket in waiting " +
                                "area " + area.getNumber() + "!");

                }
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(inspection, 1 * 1000, 1 * 1000);

        // Create GUI using main Thread
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(1000, 800));
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
