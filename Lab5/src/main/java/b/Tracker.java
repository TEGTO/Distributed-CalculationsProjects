package main.java.b;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tracker implements Runnable {
    private final List<StringBuilder> builders;
    ExecutionState state;

    public Tracker(List<StringBuilder> builders, ExecutionState state) {
        this.builders = builders;
        this.state = state;
    }

    private int count(StringBuilder builder) {
        int count = 0;
        for (int i = 0; i < builder.length(); ++i) {
            char ch = builder.charAt(i);
            if (ch == 'A' || ch == 'B') ++count;
        }
        return count;
    }

    @Override
    public void run() {
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < 4; ++i) {
            int count = count(builders.get(i));
            int c = m.getOrDefault(count, 0);
            m.put(count, c + 1);
            System.out.print(builders.get(0).toString() + " ");
        }

        System.out.println();

        if (m.containsValue(3)) {
            System.out.println(m);
            state.setState(false);
        }
    }
}
