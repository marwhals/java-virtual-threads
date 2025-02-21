package virtualthreads.section1;

import java.util.concurrent.CountDownLatch;

public class InBoundOutboundTaskDemo {

    private static final int MAX_PLATFORM = 5; //!!!
    private static final int MAX_VIRTUAL = 10;

    public static void main(String[] args) throws InterruptedException {
//        platformThreadDemo();
//        platformThreadFactoryDemo();
//        platformThreadDaemonDemo();
        virtualThreadDemo();
    }

    private static void platformThreadDemo() {
        for (int i=0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = new Thread(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

    private static void platformThreadFactoryDemo() {
        /*
        - Using factory method to create threads
         */
        var builder = Thread.ofPlatform().name("builder-thread-", 1);
        for (int i=0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

    public static void platformThreadDaemonDemo() throws InterruptedException {
        /*
            - Using factory method to create background/daemon threads
            - Latch to ensure threads complete before exiting the method
        */
        var latch = new CountDownLatch(MAX_PLATFORM);

        var builder = Thread.ofPlatform().daemon().name("daemon-", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }

    private static void virtualThreadDemo() throws InterruptedException {
        /*
        - Virtual threads are daemon by default
        - OS does not schedule them
        - can park virtual threads - OS takes virtual threads to run/mount on carrier thread
        - Virtual threads create objects in the heap
        - When tasks are blocked the tasks are unmounted and another can be executed
         */
        var latch = new CountDownLatch(MAX_VIRTUAL);
        var builder = Thread.ofVirtual().name("virtual-", 1);
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }

}
