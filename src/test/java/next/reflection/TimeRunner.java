package next.reflection;

public class TimeRunner {
    @ElapsedTime
    public void wait(final int mills) throws InterruptedException {
        for (int i = 0; i < mills; i++) {
            Thread.sleep(1L);
        }
    }
}
