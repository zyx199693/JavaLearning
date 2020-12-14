package Concurrent;

public class MySignal {
    protected boolean hasDataToProcess = false;

    public synchronized boolean hasDataToprocess() {
        return this.hasDataToProcess;
    }

    public synchronized void setHasDataToProcess(boolean hasData) {
        this.hasDataToProcess = hasData;
    }
}