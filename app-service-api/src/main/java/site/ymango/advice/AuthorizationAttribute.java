package site.ymango.advice;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationAttribute {
  private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

  public static void setAttribute(String key, Object value) {
    if (threadLocal.get() == null) {
      threadLocal.set(new HashMap<>());
    }
    threadLocal.get().put(key, value);
  }

  public static Long getUserId() {
    return (Long) threadLocal.get().get("userId");
  }

  public static void clear() {
    threadLocal.remove();
  }
}
