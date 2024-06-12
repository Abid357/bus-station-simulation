import java.util.LinkedList;
import java.util.Queue;

public class Booth {

    private Queue<Customer> queue;
    private boolean isWorking;

    public Booth() {
        this.queue = new LinkedList<>();
        this.isWorking = true;
    }

    public void addCustomer(Customer customer){
        queue.add(customer);
    }

    public Customer getFirstCustomer(){
        return queue.poll();
    }

    public Queue<Customer> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Customer> queue) {
        this.queue = queue;
    }

    public synchronized boolean isWorking() {
        return isWorking;
    }

    public synchronized void setWorking(boolean working) {
        isWorking = working;
    }
}
