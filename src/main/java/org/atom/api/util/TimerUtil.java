package org.atom.api.util;

public final class TimerUtil {
    private long time = -1L;
    private long lastMS;

    public boolean hasTimePassed(final long MS) {
        return System.currentTimeMillis() >= time + MS;
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double milliseconds) {
        return (double)(this.getCurrentMS() - this.lastMS) >= milliseconds;
    }


    public long hasTimeLeft(final long MS) {
        return (MS + time) - System.currentTimeMillis();
    }

    public void resetTwo() {
        this.lastMS = this.getCurrentMS();
    }

    public void reset() {
        time = System.currentTimeMillis();
    }
}