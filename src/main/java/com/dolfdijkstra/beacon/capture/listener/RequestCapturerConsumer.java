package com.dolfdijkstra.beacon.capture.listener;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.dolfdijkstra.beacon.capture.Manageable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestCapturerConsumer<T> implements CapturedRequestListener<T>, Manageable {
    public static final int POLL_WAIT = 50;

    final Log log = LogFactory.getLog(this.getClass());

    final private CapturedRequestListener<T> delegate;

    private ScheduledExecutorService service;

    private BlockingQueue<T> queue = new LinkedBlockingQueue<T>();
    private AtomicLong processedCounter = new AtomicLong();
    private AtomicLong failedCounter = new AtomicLong();
    private AtomicLong emptyCounter = new AtomicLong();

    /**
     * @param delegate
     */
    public RequestCapturerConsumer(CapturedRequestListener<T> delegate) {
        super();
        this.delegate = delegate;
    }

    public void capturedRequestReceived(T capturedRequest) {
        boolean b = queue.offer(capturedRequest);
        if (b == false) {
            failedCounter.incrementAndGet();
        }

    }

    public void start() {

        service = new ScheduledThreadPoolExecutor(1);
        Runnable command = new Runnable() {

            @Override
            public void run() {
                consume();

            }

        };
        service.scheduleAtFixedRate(command, 2 * POLL_WAIT, 2 * POLL_WAIT, TimeUnit.MILLISECONDS);

    }

    protected void consume() {
        if (queue.isEmpty()) {
            emptyCounter.incrementAndGet();
            return;
        }
        try {
            T tp = null;
            while ((tp = queue.poll(POLL_WAIT, TimeUnit.MILLISECONDS)) != null) {
                processedCounter.incrementAndGet();
                delegate.capturedRequestReceived(tp);
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);

        }
    }

    public void stop() {
        service.shutdown();
        service = null;
    }

    /**
     * @return the count of failure to add to the queue
     */
    public long getFailureCount() {
        return failedCounter.get();
    }

    public long getEmptyCount() {
        return emptyCounter.get();
    }

    public long getProcessedCount() {
        return processedCounter.get();
    }
}
