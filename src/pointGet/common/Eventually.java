package pointGet.common;

import java.time.Duration;
import java.util.function.Supplier;

import lombok.val;

/**
 * @author saitou
 *
 */
public class Eventually {
  private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
  private static final Duration DEFAULT_INTERVAL = Duration.ofSeconds(3);

  private static long now() {
    return System.currentTimeMillis();
  }

  /**
   * @param f
   */
  public static void eventually(Runnable f) {
    eventually(f, DEFAULT_TIMEOUT, DEFAULT_INTERVAL);
  }

  /**
   * @param f
   * @param timeout
   */
  public static void eventually(Runnable f, Duration timeout) {
    eventually(f, timeout, DEFAULT_INTERVAL);
  }

  /**
   * @param f
   * @param timeout
   * @param interval
   */
  public static void eventually(Runnable f, Duration timeout, Duration interval) {
    eventually(() -> {
      f.run();
      return null;
    }, timeout, interval);
  }

  /**
   * @param f
   * @return
   */
  public static <R> R eventually(Supplier<R> f) {
    return eventually(f, DEFAULT_TIMEOUT, DEFAULT_INTERVAL);
  }

  /**
   * @param f
   * @param timeout
   * @return
   */
  public static <R> R eventually(Supplier<R> f, Duration timeout) {
    return eventually(f, timeout, DEFAULT_INTERVAL);
  }

  /**
   * @param f
   * @param timeout
   * @param interval
   * @return
   */
  public static <R> R eventually(Supplier<R> f, Duration timeout, Duration interval) {
    long start = now();
    long end = start + timeout.toMillis();
    Throwable lastError = null;
    while (now() < end) {
      try {
        return f.get();
      } catch (Throwable t) {
        lastError = t;
      }
      try {
        Thread.sleep(interval.toMillis());
      } catch (InterruptedException e) {
      }
    }
    val timeStr = Utille.prettyFormat(now() - start);
    val message = MESSAGE_TEMPLATE.replace("$time", timeStr).replace("$message", lastError.getMessage());
    throw new RuntimeException(message, lastError);
  }

  private static final String MESSAGE_TEMPLATE = "Eventually failed over $time. Last message:\n$message";
}
