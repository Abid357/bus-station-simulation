import java.util.*;

public class Terminal {

    public static final int MAX_LIMIT = 100;

    private int customerCount;
    private List<Customer> foyer;
    private Booth booth1;
    private Booth booth2;
    private Booth booth3;
    private WaitingArea area1;
    private WaitingArea area2;
    private WaitingArea area3;
    private boolean hasReachedMaxLimit;

    public Terminal() {
        this.foyer = new ArrayList<>();
        this.booth1 = new Booth();
        this.booth2 = new Booth();
        this.booth3 = new Booth();
        this.area1 = new WaitingArea(Bus.NUMBER_1);
        this.area2 = new WaitingArea(Bus.NUMBER_2);
        this.area3 = new WaitingArea(Bus.NUMBER_3);
        this.customerCount = 0;
        this.hasReachedMaxLimit = false;
    }

    public synchronized void serveCustomer(int boothNo, int ticketNo) {
        Ticket ticket = new Ticket(ticketNo);
        Customer customer = null;
        switch (boothNo) {
            case 1:
                customer = booth1.getFirstCustomer();
                break;
            case 2:
                customer = booth2.getFirstCustomer();
                break;
            case 3:
                customer = booth3.getFirstCustomer();
                break;
        }

        if (customer != null) {
            customer.setTicket(ticket);
            foyer.add(customer);
            System.out.println("Booth " + boothNo + " just served a ticket for Bus " + ticketNo);
        }
    }

    public synchronized void moveCustomersToWaitingAreas(){
        Iterator<Customer> iterator = foyer.iterator();
        while (iterator.hasNext()){
            Customer customer = iterator.next();
            int areaNo = customer.getTicket().getBus();
            switch (areaNo){
                case Bus.NUMBER_1:
                    if (area1.addCustomer(customer))
                        iterator.remove();
                    break;
                case Bus.NUMBER_2:
                    if (area2.addCustomer(customer))
                        iterator.remove();
                    break;
                case Bus.NUMBER_3:
                    if (area3.addCustomer(customer))
                        iterator.remove();
                    break;
            }
        }
    }

    public void distributeCustomersFromBooth1(int count) {
        if (count != 0) {
            for (int i = 0; i < count / 2; i++) {
                booth2.addCustomer(booth1.getFirstCustomer());
                booth3.addCustomer(booth1.getFirstCustomer());
            }
            if (count % 2 == 1)
                booth2.addCustomer(booth1.getFirstCustomer());
        }
    }

    public boolean shouldTerminalGatesClose(){
        return hasReachedMaxLimit && customerCount >= 0.7 * MAX_LIMIT;

    }

    public boolean addCustomerToQueue(Customer customer) {
        if (shouldTerminalGatesClose())
            return false;

        Booth minBooth = null;
        if (!booth1.isWorking()) { //booth1 not working
            minBooth = booth2;
            if (booth3.getQueue().size() < minBooth.getQueue().size())
                minBooth = booth3;
        } else { //booth1 currently working
            minBooth = booth1;
            if (booth2.getQueue().size() < minBooth.getQueue().size())
                minBooth = booth2;
            if (booth3.getQueue().size() < minBooth.getQueue().size())
                minBooth = booth3;
        }

        minBooth.addCustomer(customer);
        setCustomerCount(++customerCount);

        return true;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        if (customerCount == MAX_LIMIT)
            hasReachedMaxLimit = true;
        else if (customerCount < 0.7 * MAX_LIMIT)
            hasReachedMaxLimit = false;
        this.customerCount = customerCount;
    }

    public List<Customer> getFoyer() {
        return foyer;
    }

    public void setFoyer(List<Customer> foyer) {
        this.foyer = foyer;
    }

    public Booth getBooth1() {
        return booth1;
    }

    public void setBooth1(Booth booth1) {
        this.booth1 = booth1;
    }

    public Booth getBooth2() {
        return booth2;
    }

    public void setBooth2(Booth booth2) {
        this.booth2 = booth2;
    }

    public Booth getBooth3() {
        return booth3;
    }

    public void setBooth3(Booth booth3) {
        this.booth3 = booth3;
    }

    public WaitingArea getArea1() {
        return area1;
    }

    public void setArea1(WaitingArea area1) {
        this.area1 = area1;
    }

    public WaitingArea getArea2() {
        return area2;
    }

    public void setArea2(WaitingArea area2) {
        this.area2 = area2;
    }

    public WaitingArea getArea3() {
        return area3;
    }

    public void setArea3(WaitingArea area3) {
        this.area3 = area3;
    }
}
