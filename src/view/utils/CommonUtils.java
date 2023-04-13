package view.utils;

import java.util.Objects;
import model.constant.Constants;

/**
 * CommonUtils.
 * 
 * @author anbang
 * @date 2023-04-05 00:21
 */
public class CommonUtils {
  /**
   * Checks if the given string is NULL, blank or contains text NULL.
   * 
   * @param string           string to be validated.
   * @param exceptionMessage message to be displayed if exception is thrown.
   * @throws NullPointerException     string is NULL.
   * @throws IllegalArgumentException string is blank or contains text NULL.
   */
  public static void stringIsEmpty(String string, String exceptionMessage)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(string);
    if (string.isBlank() || Constants.NULL.equalsIgnoreCase(string)) {
      throw new IllegalArgumentException(exceptionMessage);
    }
  }
}
