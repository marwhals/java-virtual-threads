package virtualthreads.section8futures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class AsyncDemo {

    private static final Logger log = LoggerFactory.getLogger(AsyncDemo.class);

    public static void main(String[] args) {
        log.info("main starts");

        runAsync()
                .thenRun(() -> log.info("it is done"))
                .exceptionally(ex -> {
                    log.error("error - {}", ex.getMessage());
                    return null;
                });

        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> runAsync(){
        log.info("method starts");

        var cf = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
             log.info("task completed");
//            throw new RuntimeException("exception in completable function");
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("method ends");
        return cf;
    }

}
