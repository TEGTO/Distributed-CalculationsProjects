package a;

public class BeeApp {
    public BeeApp(int beesNumber, int bucketCapacity) {
        Bucket bucket = new Bucket(bucketCapacity);

        BearThread bear = new BearThread(bucket);
        bear.start();

        for (int i = 0; i < beesNumber; ++i) {
            Thread thread = new BeeThread(bucket);
            thread.start();
        }
    }
}
