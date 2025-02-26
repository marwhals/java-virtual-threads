package virtualthreads.section8executorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.section8executorservice.aggregator.AggregatorService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class AllOfDemo {

    private static final Logger log = LoggerFactory.getLogger(AllOfDemo.class);

    public static void main(String[] args) {

        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);


        var futures = IntStream.rangeClosed(52, 100)
                .mapToObj(
                        id -> CompletableFuture.supplyAsync(() -> aggregator.getProductDto(id), executor)
                )
                .toList();

        /*
        - See allof method doc
         */

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        var list = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        log.info("list: {}", list);
    }


}