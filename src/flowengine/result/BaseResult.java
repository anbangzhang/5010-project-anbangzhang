package flowengine.result;

import java.io.Serializable;

/**
 * BaseResult
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

  public BaseResult() {
  }

  private BaseResult(Builder<T> builder) {
    this.success = builder.success;
    this.errorMsg = builder.errorMsg;
    this.result = builder.result;
  }

  public static Builder newSuccessResult() {
    return new Builder().success(true);
  }

  public static Builder newFailResult() {
    return new Builder().success(false);
  }

  public static final class Builder<T> {
    private boolean success = false;
    private String errorMsg;
    private T result;

    private Builder() {
    }

    public BaseResult build() {
      return new BaseResult((this));
    }

    public Builder success(boolean success) {
      this.success = success;
      return this;
    }

    public Builder errorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
      return this;
    }

    public Builder result(T result) {
      this.result = result;
      return this;
    }
  }

  public boolean isSuccess() {
    return success;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public T getResult() {
    return result;
  }

}
