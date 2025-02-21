package virtualthreads.section3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

/*
    Simlulate a CPU intensive task
 */
public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensiveTask(int i) {
        log.info("starting CPU task. Thread Info: {}", Thread.currentThread());
        var timeTaken = CommonUtils.timer(() -> findFib(i));
        log.info("ending CPU task. time taken: {} ms", timeTaken);
    }

    public static long findFib(long input) {
        if (input < 2)
            return input;
        return findFib(input - 1) + findFib(input - 2);
    }
}