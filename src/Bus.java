import java.util.Queue;
import java.util.Stack;

public class Bus {

    public static final int MAX_LIMIT = 12;

    public static final int NUMBER_1 = 1;
    public static final int NUMBER_2 = 2;
    public static final int NUMBER_3 = 3;

    private int number;
    private Stack<Customer> passengers;
    private boolean isStationed;

    public Bus(int number) {
        this.number = number;
        passengers = new Stack<>();
        isStationed = true;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean addPassenger(Customer passenger){
        if (passengers.size() == MAX_LIMIT)
            return false;
        passengers.add(passenger);
        return true;
    }


    public Stack<Customer> getPassengers() {
        return passengers;
    }

    public void setPassengers(Stack<Customer> passengers) {
        this.passengers = passengers;
    }

    public synchronized boolean isStationed() {
        return isStationed;
    }

    public synchronized void setStationed(boolean stationed) {
        isStationed = stationed;
    }
}
