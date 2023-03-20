package application.exception;

/**
 * CustomException.
 * 
 * @author anbang
 * @date 2023-03-19 18:23
 */
public class CustomException extends Exception {

  public CustomException() {
    super();
  }

  public CustomException(String message) {
    super(message);
  }
}
