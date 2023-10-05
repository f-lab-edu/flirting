package site.ymango.util;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateUtil {
  public static String generateRandomNumber(int length) {
    return IntStream.range(0, length)
        .mapToObj(i -> String.valueOf((int) (Math.random() * 10)))
        .collect(Collectors.joining());
  }
}
