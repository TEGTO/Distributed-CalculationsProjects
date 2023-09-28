package a;

public class BearThread extends Thread {

    private final Bucket bucket;

    public BearThread(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        while (isAlive()) {
            if (bucket.takeNotification()) {
                bucket.eatAllHoney();
            }
        }
    }
}
