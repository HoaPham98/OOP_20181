package n06.oop.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static String getRandomFromList(List<String> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0,list.size()));
    }

    public static String getRandomFromList(String[] list) {
        return list[ThreadLocalRandom.current().nextInt(0,list.length)];
    }

    public static String nameToIRIString(String name) {
        return name.replaceAll(" ","_");
    }

    public static List<Integer> splitIntoParts(int whole, int parts) {
        List<Integer> arr = new ArrayList<>(parts);
        int remain = whole;
        int partsLeft = parts;
        for (int i = 0; partsLeft > 0; i++) {
            int size = (remain + partsLeft - 1) / partsLeft; // rounded up, aka ceiling
            arr.add(size);
            remain -= size;
            partsLeft--;
        }
        Collections.shuffle(arr);
        return arr;
    }
}
