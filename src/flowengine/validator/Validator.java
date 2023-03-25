package flowengine.validator;

import flowengine.context.FlowContext;

/**
 * Validator.
 * 
 * @author anbang
 * @date 2023-03-25 00:30
 */
public interface Validator {

  /**
   * validate.
   * 
   * @param context context
   */
  void validate(FlowContext context);

}
