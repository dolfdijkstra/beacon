package com.dolfdijkstra.beacon.sensor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KPI {
    final Log log = LogFactory.getLog(this.getClass());
    private boolean winner = false;

    private int hits = 0;

    private int winnerLevel = 5;

    private String name;

    private Map<String, AtomicLong> testResults = new HashMap<String, AtomicLong>();

    private MultiVariantTestManager multiVariantTestManager;

    private String champion;

    public KPI(MultiVariantTestManager multiVariantTestManager, String string) {
        this.multiVariantTestManager = multiVariantTestManager;
        this.name = string;
    }

    public boolean hasWinner() {
        return winner;
    }

    public synchronized void hit(String string) {
        log.debug("hit "+ name +" with "+ string);
        hits++;
        
        AtomicLong x = testResults.get(string);
        if (x == null) {
            x = new AtomicLong();
            testResults.put(string, x);
        }
        x.incrementAndGet();
        
        if (hits > winnerLevel) {
            notifyWinnerAwarded();
        }

    }

    private void notifyWinnerAwarded() {
        winner = true;
        Map.Entry<String, AtomicLong> highest = null;
        for (Map.Entry<String, AtomicLong> e : testResults.entrySet()) {
            if (highest == null) {
                highest = e;
            } else if (e.getValue().longValue() > highest.getValue()
                    .longValue()) {
                highest = e;
            }
        }
        champion = highest.getKey(); //make winner
        testResults.clear(); //clear losers
        multiVariantTestManager.championElected(this); //notify manager
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getWinner() {
        if (hasWinner()) {
            return champion;
        } else {
            return null;
        }
    }
}
