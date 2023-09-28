package b;

import java.util.LinkedList;

public class CustomerQueue {
    private LinkedList<Customer> customers = new LinkedList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer getCustomer() {
        return customers.poll();
    }
}
