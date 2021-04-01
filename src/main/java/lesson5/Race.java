package lesson5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Race {
    private final CyclicBarrier waitBeforeStart;
    private ArrayList<Stage> stages;
    private volatile boolean winnerFound;
    private final CountDownLatch startRace;
    private final CountDownLatch endRace;

    public Race(int countPlayers, Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.waitBeforeStart = new CyclicBarrier(countPlayers);
        winnerFound = false;
        startRace = new CountDownLatch(countPlayers);
        endRace = new CountDownLatch(countPlayers);
    }

    public boolean isWinnerFound() {
        return winnerFound;
    }

    public synchronized void setWinnerFound(boolean winnerFound) {
        this.winnerFound = winnerFound;
    }

    public CountDownLatch getStartRace() {
        return startRace;
    }

    public CountDownLatch getEndRace() {
        return endRace;
    }

    public ArrayList<Stage> getStages() { return stages; }

    public CyclicBarrier getWaitBeforeStart() {
        return waitBeforeStart;
    }
}