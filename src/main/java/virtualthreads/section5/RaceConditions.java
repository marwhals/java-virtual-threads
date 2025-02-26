package virtualthreads.section5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Synchronisation
 * -- A mechanism to provide controlled access to shared resources / critical sections of code in a multithreaded environment
 * -- Prevents race conditions and data corruption
 * -- All concurrency problems like race conditions, deadlocks etc are still applicable for Virtual Threads
 */

public class RaceConditions {


    private static final Logger log = LoggerFactory.getLogger(RaceConditions.class);
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofVirtual());

        CommonUtils.sleep(Duration.ofSeconds(2));

        log.info("list size: {}", Optional.of(list.size()));
    }

    private static void demo(Thread.Builder builder) {
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started. {}", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
//                    inMemoryTask();
                    ioTask();
                }
                log.info("Task ended. {}", Thread.currentThread());
            });
        }
    }

    private static void inMemoryTask() {
        list.add(1);
    }

    private static synchronized void ioTask() {
        list.add(1);
        CommonUtils.sleep(Duration.ofSeconds(10)); //Simulate some kind of IO side effect
    }

}