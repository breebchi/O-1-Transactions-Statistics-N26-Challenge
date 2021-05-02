package com.n26.cache;

import com.google.common.collect.EvictingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static com.n26.util.Constants.CASH_SIZE;

/**
 * items are added when @method(add) is called
 * When the cache has reached its maximum size @param(maxSize), the first item on the queue is evicted.
 */
public class Cache<U> {

    private final EvictingQueue<U> queue = EvictingQueue.create(CASH_SIZE);

    public boolean addToQueue(U u) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            return queue.add(u);
        } finally {
            lock.unlock();
        }
    }

    public List<U> getQueue() {
        return new ArrayList<>(queue);
    }

    public void clearQueue() {
        queue.clear();
    }
}
