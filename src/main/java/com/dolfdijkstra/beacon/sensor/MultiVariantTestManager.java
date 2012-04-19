package com.dolfdijkstra.beacon.sensor;

import java.util.HashMap;
import java.util.Map;

public class MultiVariantTestManager {
    private Map<String, KPI> kpiList = new HashMap<String, KPI>();

    public void hit(String id) {
        if (id == null)
            return;
        String[] parts = id.split("-");
        if (parts.length < 3)
            return;
        KPI kpi;
        synchronized (this) {
            kpi = get(parts[1]);
        }
        if (kpi.hasWinner())
            return;
        kpi.hit(parts[2]);

    }

    public KPI get(String testId) {
        KPI kpi= this.kpiList.get(testId);
        if (kpi == null) {
            kpi = new KPI(this, testId);
            kpiList.put(testId, kpi);
        }
        return kpi;
    }

    protected void championElected(KPI kpi) {
        // TODO Auto-generated method stub
        
    }
    
    

}
