package site.ymango.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GenerateUtilTest {
  @Test
  public void generateRandomNumber() {
    String randomNumber = GenerateUtil.generateRandomNumber(6);
    assertEquals(6, randomNumber.length());
  }

  @Test
  public void generateRandomNumber2() {
    String randomNumber = GenerateUtil.generateRandomNumber(4);
    assertEquals(4, randomNumber.length());
  }
}