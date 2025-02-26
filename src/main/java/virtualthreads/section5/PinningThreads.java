package virtualthreads.section5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
/*
- Unmounting cannot happen with synchronised methods in virtual methods....they become "pinned"
- This occurs with synchronised methods, synchronised blocks and with the JNI
- A synchronised virtual thread cannot be unmounted
 */


public class PinningThreads {

    private static final Logger log = LoggerFactory.getLogger(PinningThreads.class);

    // This checks if threads are being pinned in the application
    // Can monitor the log to see if threads are being pinned or not
    static {
        System.setProperty("jdk.tracePinnedThreads", "short");
    }

    public static void main(String[] args) {

        Runnable runnable = () -> log.info("*** Test Message ***");

//        Pinning is not a problem with platform threads
//        demo(Thread.ofPlatform());
//        Thread.ofPlatform().start(runnable);

        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable);

        CommonUtils.sleep(Duration.ofSeconds(15));

    }

    private static void demo(Thread.Builder builder) {
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started. {}", Thread.currentThread());
                ioTask();
                log.info("Task ended. {}", Thread.currentThread());
            });
        }
    }

    private static synchronized void ioTask() {
        CommonUtils.sleep(Duration.ofSeconds(10));
    }
}
