package main.java.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class StringApp {
    public StringApp(int size) {
        ExecutionState state = new ExecutionState();

        List<StringBuilder> strings = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 4; ++i) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < size; ++j) {
                builder.append((char)random.nextInt('A', 'E'));
            }
            strings.add(builder);
        }

        CyclicBarrier barrier = new CyclicBarrier(4, new Tracker(strings, state));

        for (int i = 0; i < 4; ++i) {
            Worker worker = new Worker(barrier, strings.get(i), state, random);
            worker.start();;
        }
    }
}
