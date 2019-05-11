package ru.croc.ecosmartdata.design;

import org.apache.log4j.Logger;
import ru.croc.ecosmartdata.design.out.Builder;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuildManager {

    private static final Logger LOG = Logger.getLogger(BuildManager.class);

    Thread monitoringThread;
    volatile boolean isActive = true;
    volatile boolean isRunning = false;
    int runnerCount;
    DataProvider dataProvider;
    Queue<Runner> runnerPool;
    Queue<Builder> builders = new ConcurrentLinkedQueue<>();

    class Runner {

        BuildManager buildManager;

        Runner(BuildManager buildManager) {
            this.buildManager = buildManager;
        }

        public void start(Builder builder) {
            try {
                builder.build(dataProvider);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buildManager.release(this);
        }
    }

    private void release(Runner runner) {
        boolean result = runnerPool.offer(runner);
        LOG.debug("Runner has just finished calculation. runnerPool.offer status = " + result);
    }

    BuildManager(int runnerCount, DataProvider dataProvider) {
        LOG.debug("BuildManager has been created with parameters: runnerCount = " + runnerCount + ", dataProvider = " + dataProvider + ", BuildManager = " + this);
        this.runnerCount = runnerCount;
        this.dataProvider = dataProvider;
        this.runnerPool = IntStream.range(0, runnerCount).mapToObj(i -> new Runner(this)).collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
        initThread();
        start();
    }

    public Thread getMonitoringThread() {
        return monitoringThread;
    }

    private void initThread() {
        monitoringThread = new Thread(() -> {
            isRunning = true;
            while (!(!isActive && runnerPool.size() == runnerCount && builders.size() == 0)) {
                LOG.debug("runnerPool.size = " + runnerPool.size() + ", builders.size = " + builders.size());
                Runner runner;
                do {
                    runner = runnerPool.poll();
                    if (runner != null) {
                        Builder builder = builders.poll();
                        if (builder != null) {
                            LOG.debug("inner loop => runnerPool.size = " + runnerPool.size() + ", builders.size = " + builders.size());
                            Runner finalRunner = runner;
                            new Thread(() -> {
                                finalRunner.start(builder);
                            }, "runnerThread").start();
                        } else {
                            runnerPool.offer(runner);
                        }
                    }
                } while (builders.peek() != null);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;
            LOG.debug("monitoringThread has just finished");
        }, "monitoringThread");
    }

    public void start() {
        if (isActive && !isRunning) {
            LOG.debug("BuildManager.start has been invoked on state: isActive = " + isActive + ", isRunning = " + isRunning);
            monitoringThread.start();
        }
    }

    public boolean addBuilder(Builder builder) {
        if (isActive) {
            return builders.add(builder);
        }
        return false;
    }

    public void finish() {
        isActive = false;
    }

    public void resume() {
        if (!isActive) {
            isActive = !isActive;
            start();
        }
    }
}
