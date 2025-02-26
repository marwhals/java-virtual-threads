package virtualthreads.section7executorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.section7executorservice.externalservice.Client;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorWithVirtualThreads {
    private static final Logger log = LoggerFactory.getLogger(ScheduledExecutorWithVirtualThreads.class);

    public static void main(String[] args) {
        scheduled();
    }

    private static void scheduled() {
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try (scheduler; executor) {
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> printProductInfo(1));
            }, 0, 3, TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(15));
        }
    }

    private static void printProductInfo(int id) {
        log.info("{} => {}", id, Client.getProduct(id));
    }

}
