package virtualthreads.section8futures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureDemo.class);

    public static void main(String[] args) {
        log.info("main starts");
        var cf = slowTask();
//        var cf = fastTask();
        cf.thenAccept(v -> log.info("value={}", v));

//        log.info("value={}", cf.join());
        log.info("main ends");

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> fastTask() {
        log.info("method starts");
        var cf = new CompletableFuture<String>();
        cf.complete("hi");
        log.info("method ends");
        return cf;
    }

    private static CompletableFuture<String> slowTask() {
        log.info("method starts");
        var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            cf.complete("hi");
        });
        log.info("method ends");
        return cf;
    }

}
