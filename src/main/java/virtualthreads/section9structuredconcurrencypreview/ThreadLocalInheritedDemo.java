package virtualthreads.section9structuredconcurrencypreview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtualthreads.util.CommonUtils;

import java.time.Duration;
import java.util.UUID;

public class ThreadLocalInheritedDemo {

    private static final Logger log = LoggerFactory.getLogger(ThreadLocalInheritedDemo.class);
    private static final ThreadLocal<String> SESSION_TOKEN = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        Thread.ofVirtual().name("virtual-1").start(() -> processIncomingRequest());
        Thread.ofVirtual().name("virtual-2").start(() -> processIncomingRequest());

        CommonUtils.sleep(Duration.ofSeconds(1));

    }

    private static void processIncomingRequest() {
        authenticate();
        controller();
    }

    private static void authenticate() {
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        SESSION_TOKEN.set(token);
    }

    private static void controller() {
        log.info("controller: {}", SESSION_TOKEN.get());
        service();
    }

    private static void service() {
        log.info("service: {}", SESSION_TOKEN.get());
        var threadName = "child-of-" + Thread.currentThread().getName();
        Thread.ofVirtual().name(threadName).start(() -> callExternalService());
    }

    private static void callExternalService() {
        log.info("preparing HTTP request with token: {}", SESSION_TOKEN.get());
    }
}