package com.example.tesi.AppTravisani;

public class CodeTimer {

    private long executionTime;

    public CodeTimer() {
        this.executionTime = 0;
    }

    public void startTimer()
    {
        this.executionTime = System.currentTimeMillis();
    }

    public void stopTimer()
    {
        this.executionTime = System.nanoTime() - executionTime;
    }

    public void resetTimer()
    {
        this.executionTime = 0;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String toString()
    {
        String timerDescription = "Current execution time is: " + this.executionTime + " in millisecond.";

        return timerDescription;
    }
}
