package virtualthreads.section7executorservice.concurrencyLimiter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class ConcurrencyLimiter implements AutoCloseable {
/*
- See docs on AutoCloseable
- Lock vs Semaphore ---
-----  A semaphore - any thread inside the critical code can unlock the thread
-----  A lock - the same thread must lock and unlock
 */
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiter.class);

    private final ExecutorService executor;
    private final Semaphore semaphore;
    private final Queue<Callable<?>> queue; // Manage the order of threads ourselves. store the tasks in some kind of data structure

    public ConcurrencyLimiter(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaphore = new Semaphore(limit); // See documentation -- fairness can be set -- for acquiring permits
        this.queue = new ConcurrentLinkedQueue<>(); // Thread safe data queue
    }

    public <T> Future<T> submit(Callable<T> callable){
        this.queue.add(callable);
        return executor.submit(() -> executeTask() );
    }

    private <T> T executeTask(){
        try{
            semaphore.acquire();
            return (T) this.queue.poll().call();
        }catch (Exception e){
            log.error("error", e);
        }finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.executor.close();
    }
}
