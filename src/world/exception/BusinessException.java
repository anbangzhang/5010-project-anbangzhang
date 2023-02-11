package world.exception;

/**
 * BusinessException class.
 * 
 * @author anbang
 * @date 2023-02-11 01:01
 */
public class BusinessException extends Exception {

  public BusinessException() {
    super();
  }

  public BusinessException(String message) {
    super(message);
  }
}
