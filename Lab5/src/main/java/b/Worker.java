package main.java.b;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread {
    CyclicBarrier barrier;
    StringBuilder string;
    ExecutionState state;
    Random random;

    public Worker(CyclicBarrier barrier, StringBuilder string, ExecutionState state, Random random) {
        this.barrier = barrier;
        this.string = string;
        this.state = state;
        this.random = random;
    }

    @Override
    public void run() {
        while (state.getState()) {
            for (int i = 0; i < string.length(); ++i) {
                boolean s = random.nextBoolean();
                if (s) {
                    char ch = string.charAt(i);

                    char newCh = switch (ch) {
                        case 'A' -> 'C';
                        case 'B' -> 'D';
                        case 'C' -> 'A';
                        case 'D' -> 'B';
                        default -> ';';
                    };

                    string.setCharAt(i, newCh);
                }
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
