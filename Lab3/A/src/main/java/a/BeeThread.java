package a;

public class BeeThread extends Thread {

    private final Bucket bucket;

    BeeThread(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        while (isAlive()) {
            if (bucket.addHoney(1)) {
                bucket.notifyBear();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
