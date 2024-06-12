package gui;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private static final int MAX_CUSTOMERS_IN_TERMINAL = 100;
    private static final int MAX_CUSTOMERS_IN_AREA = 10;
    private static final int MAX_PASSENGERS_IN_BUS = 12;

    private static final int CANVAS_X = 50;
    private static final int CANVAS_Y = 150;
    private static final int CANVAS_WIDTH = 870;
    private static final int CANVAS_HEIGHT = 600;

    private static final int BOOTH_WIDTH = 250;
    private static final int BOOTH_HEIGHT = 200;
    private static final int BOOTH1_X = CANVAS_X + 30;
    private static final int BOOTH2_X = BOOTH1_X + BOOTH_WIDTH + 30;
    private static final int BOOTH3_X = BOOTH2_X + BOOTH_WIDTH + 30;
    private static final int BOOTHS_Y = CANVAS_Y;

    private static final int AREA_WIDTH = 250;
    private static final int AREA_HEIGHT = 100;
    private static final int AREA1_X = BOOTH1_X;
    private static final int AREA2_X = BOOTH2_X;
    private static final int AREA3_X = BOOTH3_X;
    private static final int AREAS_Y = BOOTHS_Y + BOOTH_HEIGHT + 100;

    private static final int BUS_WIDTH = 190;
    private static final int BUS_HEIGHT = 80;
    private static final int BUS1_X = AREA1_X + 30;
    private static final int BUS2_X = AREA2_X + 30;
    private static final int BUS3_X = AREA3_X + 30;
    private static final int BUSES_Y = AREAS_Y + AREA_HEIGHT + 40;

    private int generatedCustomers;
    private int customersInBooth1;
    private int customersInBooth2;
    private int customersInBooth3;
    private int customersInFoyer;
    private int customersInArea1;
    private int customersInArea2;
    private int customersInArea3;
    private boolean hasBus1Arrived;
    private boolean hasBus2Arrived;
    private boolean hasBus3Arrived;
    private int passengersInBus1;
    private int passengersInBus2;
    private int passengersInBus3;
    private int inspectorInArea;
    private int scannedPassengersInArea1;
    private int scannedPassengersInArea2;
    private int scannedPassengersInArea3;
    private int caughtPassengersInArea1;
    private int caughtPassengersInArea2;
    private int caughtPassengersInArea3;
    private boolean isBooth1Working = true;
    private boolean isStaffInBooth2 = true;
    private boolean isStaffInBooth3 = true;
    private int customersInTerminal;
    private int customersOutside;
    private boolean shouldTerminalGatesClose;
    private int ticketSoldInBooth1;
    private int ticketSoldInBooth2;
    private int ticketSoldInBooth3;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        canvas.drawRect(CANVAS_X, CANVAS_Y, CANVAS_WIDTH, CANVAS_HEIGHT);

        canvas.setFont(new Font("Arial", Font.PLAIN, 40));
        canvas.drawString("BUS STATION SIMULATOR", BOOTH1_X + (BOOTH_WIDTH / 2) + 5, 40);
        canvas.setFont(new Font("Arial", Font.ITALIC, 20));
        canvas.drawString("by Abid357", BOOTH2_X + 50, 65);

        // BOOTH 1
        canvas.setColor(Color.RED);
        canvas.drawRect(BOOTH1_X, BOOTHS_Y, BOOTH_WIDTH, BOOTH_HEIGHT);
        int adjustedHeight = customersInBooth1 * (BOOTH_HEIGHT / MAX_CUSTOMERS_IN_TERMINAL);
        int startAt = BOOTHS_Y + BOOTH_HEIGHT - adjustedHeight;
        canvas.fillRect(BOOTH1_X, startAt, BOOTH_WIDTH, adjustedHeight);

        // BOOTH 2
        canvas.setColor(Color.BLUE);
        canvas.drawRect(BOOTH2_X, BOOTHS_Y, BOOTH_WIDTH, BOOTH_HEIGHT);
        adjustedHeight = customersInBooth2 * (BOOTH_HEIGHT / MAX_CUSTOMERS_IN_TERMINAL);
        startAt = BOOTHS_Y + BOOTH_HEIGHT - adjustedHeight;
        canvas.fillRect(BOOTH2_X, startAt, BOOTH_WIDTH, adjustedHeight);

        // BOOTH 3
        canvas.setColor(Color.GREEN);
        canvas.drawRect(BOOTH3_X, BOOTHS_Y, BOOTH_WIDTH, BOOTH_HEIGHT);
        adjustedHeight = customersInBooth3 * (BOOTH_HEIGHT / MAX_CUSTOMERS_IN_TERMINAL);
        startAt = BOOTHS_Y + BOOTH_HEIGHT - adjustedHeight;
        canvas.fillRect(BOOTH3_X, startAt, BOOTH_WIDTH, adjustedHeight);

        // BOOTH STRINGS
        canvas.setColor(Color.LIGHT_GRAY);
        canvas.setFont(new Font("Arial", Font.PLAIN, 30));
        canvas.drawString(Integer.toString(customersInBooth1), BOOTH1_X + 110, BOOTHS_Y + BOOTH_HEIGHT - 10);
        canvas.drawString(Integer.toString(customersInBooth2), BOOTH2_X + 110, BOOTHS_Y + BOOTH_HEIGHT - 10);
        canvas.drawString(Integer.toString(customersInBooth3), BOOTH3_X + 110,
                BOOTHS_Y + BOOTH_HEIGHT - 10);

        canvas.drawString("Booth 1", BOOTH1_X + 80, BOOTHS_Y + 30);
        canvas.drawString("Booth 2", BOOTH2_X + 80, BOOTHS_Y + 30);
        canvas.drawString("Booth 3", BOOTH3_X + 80, BOOTHS_Y + 30);

        // FOYER
        canvas.drawString(customersInFoyer + " in Foyer", CANVAS_X + 30 + BOOTH_WIDTH + 30 + 40,
                CANVAS_Y + BOOTH_HEIGHT + 50);

        // TERMINAL
        canvas.drawString(customersInTerminal + " in Terminal", CANVAS_X + (CANVAS_WIDTH / 2) + (CANVAS_WIDTH / 4),
                CANVAS_Y - 30);

        // OUTSIDE
        canvas.drawString(customersOutside + " in Waiting Outside", CANVAS_X + 20,
                CANVAS_Y - 30);

        // WAITING AREA 1
        canvas.setColor(Color.MAGENTA);
        canvas.drawRect(AREA1_X, AREAS_Y, AREA_WIDTH, AREA_HEIGHT);
        adjustedHeight = customersInArea1 * (AREA_HEIGHT / MAX_CUSTOMERS_IN_AREA);
        startAt = AREAS_Y + AREA_HEIGHT - adjustedHeight;
        canvas.fillRect(AREA1_X, startAt, AREA_WIDTH, adjustedHeight);

        // WAITING AREA 2
        canvas.setColor(Color.CYAN);
        canvas.drawRect(AREA2_X, AREAS_Y, AREA_WIDTH, AREA_HEIGHT);
        adjustedHeight = customersInArea2 * (AREA_HEIGHT / MAX_CUSTOMERS_IN_AREA);
        startAt = AREAS_Y + AREA_HEIGHT - adjustedHeight;
        canvas.fillRect(AREA2_X, startAt, AREA_WIDTH, adjustedHeight);

        // WAITING AREA 3
        canvas.setColor(Color.YELLOW);
        canvas.drawRect(AREA3_X, AREAS_Y, AREA_WIDTH, AREA_HEIGHT);
        adjustedHeight = customersInArea3 * (AREA_HEIGHT / MAX_CUSTOMERS_IN_AREA);
        startAt = AREAS_Y + AREA_HEIGHT - adjustedHeight;
        canvas.fillRect(AREA3_X, startAt, AREA_WIDTH, adjustedHeight);

        // WAITING AREA STRINGS
        canvas.setColor(Color.LIGHT_GRAY);
        canvas.drawString(Integer.toString(customersInArea1), AREA1_X + 110, AREAS_Y + AREA_HEIGHT - 10);
        canvas.drawString(Integer.toString(customersInArea2), AREA2_X + 110, AREAS_Y + AREA_HEIGHT - 10);
        canvas.drawString(Integer.toString(customersInArea3), AREA3_X + 110, AREAS_Y + AREA_HEIGHT - 10);

        canvas.setFont(new Font("Arial", Font.PLAIN, 20));
        canvas.drawString("Waiting Area 1", AREA1_X + 60, AREAS_Y + 30);
        canvas.drawString("Waiting Area 2", AREA2_X + 60, AREAS_Y + 30);
        canvas.drawString("Waiting Area 3", AREA3_X + 60, AREAS_Y + 30);

        // BUS 1
        canvas.setColor(Color.ORANGE);
        if (hasBus1Arrived) {
            canvas.drawRect(BUS1_X, BUSES_Y, BUS_WIDTH, BUS_HEIGHT);
            adjustedHeight = (int) (passengersInBus1 * Math.round(BUS_HEIGHT * 1.0 / MAX_PASSENGERS_IN_BUS));
            startAt = BUSES_Y + BUS_HEIGHT - adjustedHeight;
            canvas.fillRect(BUS1_X, startAt, BUS_WIDTH, adjustedHeight);

            // wheels
            canvas.setColor(Color.DARK_GRAY);
            canvas.fillOval(BUS1_X + 20, BUSES_Y - 5, 20, 12);
            canvas.fillOval(BUS1_X + 20, BUSES_Y + BUS_HEIGHT - 5, 20, 12);
            canvas.fillOval(BUS1_X + BUS_WIDTH - 40, BUSES_Y - 5, 20, 12);
            canvas.fillOval(BUS1_X + BUS_WIDTH - 40, BUSES_Y + BUS_HEIGHT - 5, 20, 12);

            canvas.setColor(Color.LIGHT_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 40));
            canvas.drawString("1", BUS1_X + 20, BUSES_Y + BUS_HEIGHT - 25);
            canvas.setColor(Color.DARK_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 16));
            canvas.drawString("Just arrived!", BUS1_X + 50, BUSES_Y + BUS_HEIGHT + 35);
        } else {
            canvas.setColor(Color.DARK_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 16));
            canvas.drawString("Departed with " + passengersInBus1 + " passengers", BUS1_X,
                    BUSES_Y + BUS_HEIGHT + 35);
        }

        // BUS 2
        canvas.setColor(Color.ORANGE);
        if (hasBus2Arrived) {
            canvas.drawRect(BUS2_X, BUSES_Y, BUS_WIDTH, BUS_HEIGHT);
            adjustedHeight = (int) (passengersInBus2 * Math.round(BUS_HEIGHT * 1.0 / MAX_PASSENGERS_IN_BUS));
            startAt = BUSES_Y + BUS_HEIGHT - adjustedHeight;
            canvas.fillRect(BUS2_X, startAt, BUS_WIDTH, adjustedHeight);

            // wheels
            canvas.setColor(Color.DARK_GRAY);
            canvas.fillOval(BUS2_X + 20, BUSES_Y - 5, 20, 12);
            canvas.fillOval(BUS2_X + 20, BUSES_Y + BUS_HEIGHT - 5, 20, 12);
            canvas.fillOval(BUS2_X + BUS_WIDTH - 40, BUSES_Y - 5, 20, 12);
            canvas.fillOval(BUS2_X + BUS_WIDTH - 40, BUSES_Y + BUS_HEIGHT - 5, 20, 12);

            canvas.setColor(Color.LIGHT_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 40));
            canvas.drawString("2", BUS2_X + 20, BUSES_Y + BUS_HEIGHT - 25);
            canvas.setColor(Color.DARK_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 16));
            canvas.drawString("Just arrived!", BUS2_X + 50, BUSES_Y + BUS_HEIGHT + 35);
        } else {
            canvas.setColor(Color.DARK_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 16));
            canvas.drawString("Departed with " + passengersInBus2 + " passengers", BUS2_X,
                    BUSES_Y + BUS_HEIGHT + 35);
        }

        // BUS 3
        canvas.setColor(Color.ORANGE);
        if (hasBus3Arrived) {
            canvas.drawRect(BUS3_X, BUSES_Y, BUS_WIDTH, BUS_HEIGHT);
            adjustedHeight = (int) (passengersInBus3 * Math.round(BUS_HEIGHT * 1.0 / MAX_PASSENGERS_IN_BUS));
            startAt = BUSES_Y + BUS_HEIGHT - adjustedHeight;
            canvas.fillRect(BUS3_X, startAt, BUS_WIDTH, adjustedHeight);

            // wheels
            canvas.setColor(Color.DARK_GRAY);
            canvas.fillOval(BUS3_X + 20, BUSES_Y - 5, 20, 12);
            canvas.fillOval(BUS3_X + 20, BUSES_Y + BUS_HEIGHT - 5, 20, 12);
            canvas.fillOval(BUS3_X + BUS_WIDTH - 40, BUSES_Y - 5, 20, 12);
            canvas.fillOval(BUS3_X + BUS_WIDTH - 40, BUSES_Y + BUS_HEIGHT - 5, 20, 12);

            canvas.setColor(Color.LIGHT_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 40));
            canvas.drawString("3", BUS3_X + 20, BUSES_Y + BUS_HEIGHT - 25);
            canvas.setColor(Color.DARK_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 16));
            canvas.drawString("Just arrived!", BUS3_X + 50, BUSES_Y + BUS_HEIGHT + 35);
        } else {
            canvas.setColor(Color.DARK_GRAY);
            canvas.setFont(new Font("Arial", Font.PLAIN, 16));
            canvas.drawString("Departed with " + passengersInBus3 + " passengers", BUS3_X,
                    BUSES_Y + BUS_HEIGHT + 35);
        }

        // INSPECTOR
        canvas.setColor(Color.BLUE);
        canvas.setStroke(new BasicStroke(3));
        if (inspectorInArea == 1) {
            canvas.fillOval(AREA1_X + AREA_WIDTH - 30, AREAS_Y + AREA_HEIGHT - 10, 20, 20);
            canvas.drawLine(AREA1_X + AREA_WIDTH - 20, AREAS_Y + AREA_HEIGHT - 5, AREA1_X + AREA_WIDTH - 20,
                    AREAS_Y + AREA_HEIGHT + 20);
            canvas.drawLine(AREA1_X + AREA_WIDTH - 30, AREAS_Y + AREA_HEIGHT + 10, AREA1_X + AREA_WIDTH - 10,
                    AREAS_Y + AREA_HEIGHT + 10);
        } else if (inspectorInArea == 2) {
            canvas.fillOval(AREA2_X + AREA_WIDTH - 30, AREAS_Y + AREA_HEIGHT - 10, 20, 20);
            canvas.drawLine(AREA2_X + AREA_WIDTH - 20, AREAS_Y + AREA_HEIGHT - 5, AREA2_X + AREA_WIDTH - 20,
                    AREAS_Y + AREA_HEIGHT + 20);
            canvas.drawLine(AREA2_X + AREA_WIDTH - 30, AREAS_Y + AREA_HEIGHT + 10, AREA2_X + AREA_WIDTH - 10,
                    AREAS_Y + AREA_HEIGHT + 10);
        } else {
            canvas.fillOval(AREA3_X + AREA_WIDTH - 30, AREAS_Y + AREA_HEIGHT - 10, 20, 20);
            canvas.drawLine(AREA3_X + AREA_WIDTH - 20, AREAS_Y + AREA_HEIGHT - 5, AREA3_X + AREA_WIDTH - 20,
                    AREAS_Y + AREA_HEIGHT + 20);
            canvas.drawLine(AREA3_X + AREA_WIDTH - 30, AREAS_Y + AREA_HEIGHT + 10, AREA3_X + AREA_WIDTH - 10,
                    AREAS_Y + AREA_HEIGHT + 10);
        }

        // BOOTH STAFF
        canvas.setColor(Color.BLACK);
        if (isStaffInBooth2) {
            canvas.fillOval(BOOTH2_X + BOOTH_WIDTH - 30, BOOTHS_Y + BOOTH_HEIGHT - 10, 20, 20);
            canvas.drawLine(BOOTH2_X + BOOTH_WIDTH - 20, BOOTHS_Y + BOOTH_HEIGHT - 5, BOOTH2_X + BOOTH_WIDTH - 20,
                    BOOTHS_Y + BOOTH_HEIGHT + 20);
            canvas.drawLine(BOOTH2_X + BOOTH_WIDTH - 30, BOOTHS_Y + BOOTH_HEIGHT + 10, BOOTH2_X + BOOTH_WIDTH - 10,
                    BOOTHS_Y + BOOTH_HEIGHT + 10);
        }
        if (isStaffInBooth3) {
            canvas.fillOval(BOOTH3_X + BOOTH_WIDTH - 30, BOOTHS_Y + BOOTH_HEIGHT - 10, 20, 20);
            canvas.drawLine(BOOTH3_X + BOOTH_WIDTH - 20, BOOTHS_Y + BOOTH_HEIGHT - 5, BOOTH3_X + BOOTH_WIDTH - 20,
                    BOOTHS_Y + BOOTH_HEIGHT + 20);
            canvas.drawLine(BOOTH3_X + BOOTH_WIDTH - 30, BOOTHS_Y + BOOTH_HEIGHT + 10, BOOTH3_X + BOOTH_WIDTH - 10,
                    BOOTHS_Y + BOOTH_HEIGHT + 10);
        }
        if (!isBooth1Working) {
            canvas.setColor(Color.RED);
        }
        canvas.setStroke(new BasicStroke(5));
        canvas.fillOval(BOOTH1_X + BOOTH_WIDTH - 30, BOOTHS_Y + BOOTH_HEIGHT - 10, 20, 20);
        canvas.drawLine(BOOTH1_X + BOOTH_WIDTH - 20, BOOTHS_Y + BOOTH_HEIGHT - 5, BOOTH1_X + BOOTH_WIDTH - 20,
                BOOTHS_Y + BOOTH_HEIGHT + 20);

        // INSPECTOR STRINGS
        canvas.setColor(Color.DARK_GRAY);
        canvas.drawString("Scanned: " + scannedPassengersInArea1 + "   Caught: " + caughtPassengersInArea1, BUS1_X - 5,
                AREAS_Y + AREA_HEIGHT + 15);
        canvas.drawString("Scanned: " + scannedPassengersInArea2 + "   Caught: " + caughtPassengersInArea2, BUS2_X - 5,
                AREAS_Y + AREA_HEIGHT + 15);
        canvas.drawString("Scanned: " + scannedPassengersInArea3 + "   Caught: " + caughtPassengersInArea3, BUS3_X - 5,
                AREAS_Y + AREA_HEIGHT + 15);

        // TICKET STRINGS
        canvas.drawString("Last ticket sold: Bus " + ticketSoldInBooth1, BUS1_X + 10, BOOTHS_Y + BOOTH_HEIGHT + 15);
        canvas.drawString("Last ticket sold: Bus " + ticketSoldInBooth2, BUS2_X + 10, BOOTHS_Y + BOOTH_HEIGHT + 15);
        canvas.drawString("Last ticket sold: Bus " + ticketSoldInBooth3, BUS3_X + 10, BOOTHS_Y + BOOTH_HEIGHT + 15);

        // GATES
        canvas.setColor(Color.BLACK);
        canvas.setStroke(new BasicStroke(6));
        if (shouldTerminalGatesClose) {
            canvas.drawLine(CANVAS_X, BOOTHS_Y + BOOTH_HEIGHT - (BOOTH_HEIGHT / 4), CANVAS_X,
                    AREAS_Y + (AREA_HEIGHT / 2));
            canvas.drawLine(CANVAS_X + CANVAS_WIDTH, BOOTHS_Y + BOOTH_HEIGHT - (BOOTH_HEIGHT / 4),
                    CANVAS_X + CANVAS_WIDTH,
                    AREAS_Y + (AREA_HEIGHT / 2));
        } else {
            canvas.drawLine(CANVAS_X, BOOTHS_Y + (BOOTH_HEIGHT / 2), CANVAS_X, BOOTHS_Y + BOOTH_HEIGHT);
            canvas.drawLine(CANVAS_X, AREAS_Y, CANVAS_X, AREAS_Y + AREA_HEIGHT);
            canvas.drawLine(CANVAS_X + CANVAS_WIDTH, BOOTHS_Y + (BOOTH_HEIGHT / 2), CANVAS_X + CANVAS_WIDTH,
                    BOOTHS_Y + BOOTH_HEIGHT);
            canvas.drawLine(CANVAS_X + CANVAS_WIDTH, AREAS_Y, CANVAS_X + CANVAS_WIDTH, AREAS_Y + AREA_HEIGHT);
        }
    }

    public void updateTerminalGates(boolean value) {
        shouldTerminalGatesClose = value;
        repaint();
    }

    public void updateInspection(int area, int scanned, int caught) {
        switch (area) {
            case 1:
                scannedPassengersInArea1 += scanned;
                caughtPassengersInArea1 += caught;
                break;
            case 2:
                scannedPassengersInArea2 += scanned;
                caughtPassengersInArea2 += caught;
                break;
            case 3:
                scannedPassengersInArea3 += scanned;
                caughtPassengersInArea3 += caught;
                break;
        }
        repaint();
    }

    public void updateInspector(int area) {
        inspectorInArea = area;
        switch (area) {
            case 1:
                scannedPassengersInArea1 = 0;
                caughtPassengersInArea1 = 0;
                break;
            case 2:
                scannedPassengersInArea2 = 0;
                caughtPassengersInArea2 = 0;
                break;
            case 3:
                scannedPassengersInArea3 = 0;
                caughtPassengersInArea3 = 0;
                break;
        }
        repaint();
    }

    public void updateGeneratedCustomers(int generatedCustomers) {
        this.generatedCustomers = generatedCustomers;
        repaint();
    }

    public void updateCustomerCounts(int booth1, int booth2, int booth3, int foyer, int area1, int area2, int area3,
                                     int terminal, int outside) {
        customersInBooth1 = booth1;
        customersInBooth2 = booth2;
        customersInBooth3 = booth3;
        customersInFoyer = foyer;
        customersInArea1 = area1;
        customersInArea2 = area2;
        customersInArea3 = area3;
        customersInTerminal = terminal;
        customersOutside = outside;
        repaint();
    }

    public void updateBuses(int bus, boolean value) {
        switch (bus) {
            case 1:
                hasBus1Arrived = value;
                if (value)
                    passengersInBus1 = 0;
                break;
            case 2:
                hasBus2Arrived = value;
                if (value)
                    passengersInBus2 = 0;
                break;
            case 3:
                hasBus3Arrived = value;
                if (value)
                    passengersInBus3 = 0;
                break;
        }
        repaint();
    }

    public void updatePassengerCounts(int bus, int value) {
        switch (bus) {
            case 1:
                passengersInBus1 = value;
                break;
            case 2:
                passengersInBus2 = value;
                break;
            case 3:
                passengersInBus3 = value;
                break;
        }
        repaint();
    }

    public void updateBooth1(boolean value) {
        isBooth1Working = value;
        repaint();
    }

    public void updateBooth2Staff(boolean value) {
        isStaffInBooth2 = value;
        repaint();
    }

    public void updateBooth3Staff(boolean value) {
        isStaffInBooth3 = value;
        repaint();
    }

    public void updateTicketForBooth1(int ticket) {
        ticketSoldInBooth1 = ticket;
        repaint();
    }

    public void updateTicketForBooth2(int ticket) {
        ticketSoldInBooth2 = ticket;
        repaint();
    }

    public void updateTicketForBooth3(int ticket) {
        ticketSoldInBooth3 = ticket;
        repaint();
    }
}
