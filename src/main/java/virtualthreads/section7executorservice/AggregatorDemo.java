package virtualthreads.section7executorservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.section7executorservice.aggregator.AggregatorService;
import virtualthreads.section7executorservice.aggregator.ProductDto;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemo {
    private static final Logger log = LoggerFactory.getLogger(AggregatorDemo.class);

    public static void main(String[] args) throws Exception {

        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        var futures = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> executor.submit(() -> aggregator.getProductDto(id)))
                .toList();

        var list = futures.stream()
                .map(AggregatorDemo::toProductDto)
                .toList();

        log.info("list: {}", list);

    }

    private static ProductDto toProductDto(Future<ProductDto> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
