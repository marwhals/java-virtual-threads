package virtualthreads.section7executorservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.section7executorservice.externalservice.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrencyLimit {
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class);

    public static void main(String[] args) {
        var factory = Thread.ofVirtual().name("virtual-thread", 1).factory();
        // This creates a virtual thread pool * this not how they should be used*
        // Thread pools are used to avoid creating new threads, by reusing existing threads
        execute(Executors.newFixedThreadPool(3, factory), 20);
    }

    private static void execute(ExecutorService executorService, int taskCount) {
        try (executorService) {
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                executorService.submit(() -> printProductInfo(j));
            }
            log.info("submitted");
        }
    }

    private static void printProductInfo(int id) {
        log.info("{} => {}", id, Client.getProduct(id));
    }

}
