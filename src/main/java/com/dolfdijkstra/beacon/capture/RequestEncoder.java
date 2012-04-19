package com.dolfdijkstra.beacon.capture;

public interface RequestEncoder<T, E> {

    E encode(T message) throws Exception;

}
