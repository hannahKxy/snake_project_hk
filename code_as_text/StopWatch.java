package hdm.lab.snake;

public class StopWatch {
    long startTime;

    /**
     * Creates a new stop watch and starts measuring elapsed time.
     */
    public StopWatch() {
        startTime = System.nanoTime();
    }

    /**
     * Returns time elapsed since creation of StopWatch or last reset, in
     * nanoseconds.
     * 
     * @return Elapsed time in nanoseconds.
     */
    public long getElapsedTime() {
        long now = System.nanoTime();
        return (now - startTime);
    }

    /**
     * Resets elapsed time of stop watch to 0.
     */
    public void reset() {
        startTime = System.nanoTime();
    }

    @Override
    public String toString() {
        return String.format("Elapsed time: %,d ms", getElapsedTime() / 1_000_000);
    }
}
