package com.dolfdijkstra.beacon;

import com.dolfdijkstra.beacon.domain.Hit;

public interface HitDao {

    void makePersistent(Hit hit);

    void close();

}
