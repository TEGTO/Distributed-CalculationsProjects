package b;

public class HairStyler extends Thread {

    private final CustomerQueue queue;

    public HairStyler(CustomerQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (isAlive()) {
            Customer customer = queue.getCustomer();
            if (customer == null) {
                synchronized (this) {
                    try {
                        System.out.println("[HairStyler] Sleeping");
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else {
                synchronized (customer) {
                    try {
                        System.out.println("[HairStyler] Calling next customer");

                        customer.notify();
                        customer.wait();

                        System.out.println("[HairStyler] Doing haircut");
                        Thread.sleep(100);
                        System.out.println("[HairStyler] Finished haircut");

                        customer.notify();
                        customer.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
