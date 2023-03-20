package flowengine.result;

import java.io.Serializable;

/**
 * BaseResult.
 * 
 * @author anbang
 * @date 2023-03-15 16:48
 */
public class BaseResult<T> implements Serializable {

  /**
   * success.
   */
  private boolean success;
  /**
   * error msg.
   */
  private String errorMsg;
  /**
   * result.
   */
  private T result;

  /**
   * Constructor.
   */
  public BaseResult() {
  }

  /**
   * Constructor.
   * 
   * @param builder builder
   */
  private BaseResult(Builder<T> builder) {
    this.success = builder.success;
    this.errorMsg = builder.errorMsg;
    this.result = builder.result;
  }

  /**
   * Construct success result.
   * 
   * @return success result
   */
  public static Builder newSuccessResult() {
    return new Builder().success(true);
  }

  /**
   * Construct fail result.
   * 
   * @return fail result
   */
  public static Builder newFailResult() {
    return new Builder().success(false);
  }

  /**
   * Builder.
   * 
   * @param <T> data
   */
  public static final class Builder<T> {
    private boolean success = false;
    private String errorMsg;
    private T result;

    /**
     * Builder.
     */
    private Builder() {
    }

    /**
     * Build.
     * 
     * @return result
     */
    public BaseResult build() {
      return new BaseResult((this));
    }

    /**
     * Build success.
     * 
     * @param success success
     * @return result
     */
    public Builder success(boolean success) {
      this.success = success;
      return this;
    }

    /**
     * Build errorMsg.
     *
     * @param errorMsg errorMsg
     * @return result
     */
    public Builder errorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
      return this;
    }

    /**
     * Build result.
     * 
     * @param result result
     * @return result
     */
    public Builder result(T result) {
      this.result = result;
      return this;
    }
  }

  /**
   * Is Success.
   * 
   * @return isSuccess
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * Error message.
   * 
   * @return msg
   */
  public String getErrorMsg() {
    return errorMsg;
  }

  /**
   * Get result.
   * 
   * @return result
   */
  public T getResult() {
    return result;
  }

}
