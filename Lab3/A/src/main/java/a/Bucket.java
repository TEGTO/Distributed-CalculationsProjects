package a;

public class Bucket {
    private final int SEMAPHORE_IS_AVAILABLE = 0;
    private final int SEMAPHORE_IS_NOT_AVAILABLE = 1;

    volatile int semaphore = SEMAPHORE_IS_AVAILABLE;

    private final int capacity;
    private int amount;

    private boolean notify = false;

    public Bucket(int capacity) {
        this.capacity = capacity;
        amount = 0;
    }

    public boolean addHoney(int honey) {
        while (semaphore == SEMAPHORE_IS_NOT_AVAILABLE) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        semaphore = SEMAPHORE_IS_NOT_AVAILABLE;

        boolean wasJustFilled = false;

        if (amount <= capacity) {
            amount = Math.min(amount + honey, capacity);

            System.out.println("Honey was added, new amount = " + amount);

            if (amount >= capacity) {
                wasJustFilled = true;
            }
        }

        semaphore = SEMAPHORE_IS_AVAILABLE;

        return wasJustFilled;
    }

    public void eatAllHoney() {
        while (semaphore == SEMAPHORE_IS_NOT_AVAILABLE) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        semaphore = SEMAPHORE_IS_NOT_AVAILABLE;

        amount = 0;

        System.out.println("All honey was eaten");

        semaphore = SEMAPHORE_IS_AVAILABLE;
    }

    public void notifyBear() {
        while (semaphore == SEMAPHORE_IS_NOT_AVAILABLE) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        semaphore = SEMAPHORE_IS_NOT_AVAILABLE;

        notify = true;

        semaphore = SEMAPHORE_IS_AVAILABLE;
    }

    public boolean takeNotification() {
        while (semaphore == SEMAPHORE_IS_NOT_AVAILABLE) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        semaphore = SEMAPHORE_IS_NOT_AVAILABLE;

        boolean wasNotified = notify;
        notify = false;

        semaphore = SEMAPHORE_IS_AVAILABLE;

        return wasNotified;
    }
}