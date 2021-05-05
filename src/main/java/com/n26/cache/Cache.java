package com.n26.cache;

import com.google.common.collect.EvictingQueue;

import java.util.concurrent.locks.ReentrantLock;

import static com.n26.util.Constants.CASH_SIZE;

/**
 * Cache :
 * items are added when at teh end of the queue.
 * When the cache has reached its maximum size, the first item on the queue is evicted.
 *
 * @author Mahmoud Kraiem
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

    public EvictingQueue<U> getQueue() {
        return queue;
    }

    public void clearQueue() {
        queue.clear();
    }
}
