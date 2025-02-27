package virtualthreads.section9structuredconcurrencypreview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

public class ScopedValuesWithStructuredTaskScope {

    private static final Logger log = LoggerFactory.getLogger(ScopedValuesWithStructuredTaskScope.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {

        ScopedValue.runWhere(SESSION_TOKEN, "token-123", ScopedValuesWithStructuredTaskScope::task);

    }

    private static void task(){
        try(var taskScope = new StructuredTaskScope<>()){

            log.info("token: {}", SESSION_TOKEN.get());

            var subtask1 = taskScope.fork(ScopedValuesWithStructuredTaskScope::getDeltaAirfare);
            var subtask2 = taskScope.fork(ScopedValuesWithStructuredTaskScope::getFrontierAirfare);

            taskScope.join();

            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

            log.info("subtask1 result: {}", subtask1.get());
            log.info("subtask2 result: {}", subtask2.get());

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static String getDeltaAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("delta: {}", random);
        log.info("token: {}", SESSION_TOKEN.get());
        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta-$" + random;
    }

    private static String getFrontierAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("frontier: {}", random);
        log.info("token: {}", SESSION_TOKEN.get());
        CommonUtils.sleep("frontier", Duration.ofSeconds(2));
        return "Frontier-$" + random;
    }

    private static String failingTask(){
        throw new RuntimeException("oops");
    }


}
