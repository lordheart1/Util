package com.dc.hibernate.cache.region;


import java.util.concurrent.atomic.AtomicLong;

public final class Timestamper {
    private static final int BIN_DIGITS = 12;
    public static final short ONE_MS = 4096;
    private static final AtomicLong VALUE = new AtomicLong();

    public static long next() {
        while(true) {
            long base = System.currentTimeMillis() << 12;
            long maxValue = base + 4096L - 1L;
            long current = VALUE.get();

            for(long update = Math.max(base, current + 1L); update < maxValue; update = Math.max(base, current + 1L)) {
                if (VALUE.compareAndSet(current, update)) {
                    return update;
                }

                current = VALUE.get();
            }
        }
    }

    public Timestamper() {
    }
}