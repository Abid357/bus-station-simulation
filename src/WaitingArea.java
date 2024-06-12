import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WaitingArea {

    public static final int MAX_LIMIT = 10;

    private Random random = new Random();
    private int number;
    private List<Customer> customers;

    public WaitingArea(int number) {
        this.number = number;
        this.customers = Collections.synchronizedList(new ArrayList<>());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean addCustomer(Customer customer){
        if (customers.size() == MAX_LIMIT)
            return false;
        if (random.nextInt(4) == 0) // 25% chance
            customer.getTicket().setScanned(false);
        else
            customer.getTicket().setScanned(true);
        customers.add(customer);
        return true;
    }

    public void removeCustomer(Customer customer){
        if (customers.contains(customer))
            customers.remove(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
