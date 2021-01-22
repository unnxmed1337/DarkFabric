package com.github.darkfabric.util;

public class TimeUtils {

    private long lastTime = getCurrentTime();

    public TimeUtils() {
        reset();
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getDifference() {
        return getCurrentTime() - lastTime;
    }

    public void reset() {
        lastTime = getCurrentTime();
    }

    public boolean hasReached(long milliseconds) {
        return getDifference() >= milliseconds;
    }

}