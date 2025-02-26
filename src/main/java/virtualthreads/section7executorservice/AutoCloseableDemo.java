package virtualthreads.section7executorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.concurrent.Executors;

public class AutoCloseableDemo {

    private static final Logger log = LoggerFactory.getLogger(AutoCloseableDemo.class);

    public static void main(String[] args) {
        withAutoCloseable();
    }

    private static void withoutAutoCloseable() {
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(AutoCloseableDemo::task);
        log.info("submitted");
        executorService.shutdown();
    }

    private static void withAutoCloseable() {
        try (var executorService = Executors.newSingleThreadExecutor()) {
            executorService.submit(AutoCloseableDemo::task);
            executorService.submit(AutoCloseableDemo::task);
            executorService.submit(AutoCloseableDemo::task);
            executorService.submit(AutoCloseableDemo::task);
            log.info("submitted");
        }
    }

    private static void task() {
        CommonUtils.sleep(Duration.ofSeconds(1));
        log.info("task executed");
    }
}
