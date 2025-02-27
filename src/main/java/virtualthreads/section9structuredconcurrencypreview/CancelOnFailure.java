package virtualthreads.section9structuredconcurrencypreview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

public class CancelOnFailure {

    private static final Logger log = LoggerFactory.getLogger(CancelOnFailure.class);

    public static void main(String[] args) {

        try(var taskScope = new StructuredTaskScope.ShutdownOnFailure()){
            var subtask1 = taskScope.fork(CancelOnFailure::getDeltaAirfare);
            var subtask2 = taskScope.fork(CancelOnFailure::failingTask);

            taskScope.join();
            taskScope.throwIfFailed(ex -> new RuntimeException("something went wrong"));

            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

//            log.info("subtask1 result: {}", subtask1.get());
//            log.info("subtask2 result: {}", subtask2.get());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private static String getDeltaAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("delta: {}", random);
        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta-$" + random;
    }

    private static String getFrontierAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("frontier: {}", random);
        CommonUtils.sleep("frontier", Duration.ofSeconds(2));
        return "Frontier-$" + random;
    }

    private static String failingTask(){
        throw new RuntimeException("oops");
    }


}
