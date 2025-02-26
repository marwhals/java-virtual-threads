package virtualthreads.section7executorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.section7executorservice.concurrencyLimiter.ConcurrencyLimiter;
import virtualthreads.section7executorservice.externalservice.Client;

import java.util.concurrent.Executors;

public class ConcurrencyLimitWithSemaphore {
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimitWithSemaphore.class);

    public static void main(String[] args) throws Exception {
        var factory = Thread.ofVirtual().name("virtual-thread", 1).factory();
        var limiter = new ConcurrencyLimiter(Executors.newThreadPerTaskExecutor(factory), 3);
        execute(limiter, 200);
    }

    private static void execute(ConcurrencyLimiter concurrencyLimiter, int taskCount) throws Exception {
        try(concurrencyLimiter){
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                concurrencyLimiter.submit(() -> printProductInfo(j));
            }
            log.info("submitted");
        }
    }

    private static String printProductInfo(int id){
        var product = Client.getProduct(id);
        log.info("{} => {}", id, product);
        return product;
    }

}
