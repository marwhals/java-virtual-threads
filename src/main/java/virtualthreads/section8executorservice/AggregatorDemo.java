package virtualthreads.section8executorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.section8executorservice.aggregator.AggregatorService;

import java.util.concurrent.Executors;

public class AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(AggregatorDemo.class);

    public static void main(String[] args) throws Exception {

        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        log.info("product={}", aggregator.getProductDto(51));
    }

}