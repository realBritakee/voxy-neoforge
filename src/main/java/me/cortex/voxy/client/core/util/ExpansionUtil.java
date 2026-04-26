package me.cortex.voxy.client.core.util;

public class ExpansionUtil {

    public static int compress(int i, int mask) {
        int result = 0, bit = 0;
        while (mask != 0) {
            int lowest = mask & -mask;
            if ((i & lowest) != 0) result |= (1 << bit);
            bit++;
            mask &= mask - 1;
        }
        return result;
    }

    public static int expand(int i, int mask) {
        int result = 0, bit = 0;
        while (mask != 0) {
            int lowest = mask & -mask;
            if ((i & (1 << bit)) != 0) result |= lowest;
            bit++;
            mask &= mask - 1;
        }
        return result;
    }

    public static long compress(long i, long mask) {
        long result = 0;
        int bit = 0;
        while (mask != 0) {
            long lowest = mask & -mask;
            if ((i & lowest) != 0) result |= (1L << bit);
            bit++;
            mask &= mask - 1;
        }
        return result;
    }

    public static long expand(long i, long mask) {
        long result = 0;
        int bit = 0;
        while (mask != 0) {
            long lowest = mask & -mask;
            if ((i & (1L << bit)) != 0) result |= lowest;
            bit++;
            mask &= mask - 1;
        }
        return result;
    }

    public static boolean isJava21() {
        return false;
    }
}
