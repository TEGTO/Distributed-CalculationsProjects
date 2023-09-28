package b;

public class HairSalonApp {
    public HairSalonApp(int customersCount) {
        CustomerQueue queue = new CustomerQueue();

        HairStyler hairStyler = new HairStyler(queue);

        for (int i = 0; i < customersCount; ++i) {
            Customer customer = new Customer();
            customer.setHairStyler(hairStyler);
            customer.start();
            queue.addCustomer(customer);
        }

        hairStyler.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Customer customer = new Customer();
        customer.setHairStyler(hairStyler);
        customer.start();
        queue.addCustomer(customer);
    }
}
