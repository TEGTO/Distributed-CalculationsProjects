package b;

public class Customer extends Thread {
    private HairStyler hairStyler;

    public void setHairStyler(HairStyler hairStyler) {
        this.hairStyler = hairStyler;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                System.out.println("[Customer] Came in, waiting");
                synchronized (hairStyler) {
                    hairStyler.notify();
                }
                wait();
                System.out.println("[Customer] Taking a seat");
                notify();
                System.out.println("[Customer] Falling a sleep");
                wait();
                System.out.println("[Customer] Leaving");
                notify();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
