package virtualthreads.section6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

/*
- Thread builder is not safe
- Use thread factory
 */

public class VirtualThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadFactory.class);

    public static void main(String[] args) {

        demo(Thread.ofVirtual().name("virtual-thread", 1).factory());

        CommonUtils.sleep(Duration.ofSeconds(3));

    }

    private static void demo(ThreadFactory factory){
        for (int i = 0; i < 30; i++) {
            var t = factory.newThread(() -> {
                log.info("Task started. {}", Thread.currentThread());
                var ct = factory.newThread(() -> {
                    log.info("Child task started. {}", Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    log.info("Child task ended. {}", Thread.currentThread());
                });
                ct.start();
                log.info("Task ended. {}", Thread.currentThread());
            });
            t.start();
        }
    }

}
