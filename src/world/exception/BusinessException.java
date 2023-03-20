package world.exception;

/**
 * BusinessException class.
 * 
 * @author anbang
 * @date 2023-02-11 01:01
 */
public class BusinessException extends Exception {

  /**
   * Constructor.
   */
  public BusinessException() {
    super();
  }

  /**
   * Constructor.
   * 
   * @param message msg
   */
  public BusinessException(String message) {
    super(message);
  }
}
