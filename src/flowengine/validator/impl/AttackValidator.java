package flowengine.validator.impl;

import flowengine.context.FlowContext;
import flowengine.validator.Validator;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.context.Context;

/**
 * AttackValidator.
 * 
 * @author anbang
 * @date 2023-03-18 10:15
 */
@Component(value = "attackValidator")
public class AttackValidator implements Validator {

  @Override
  public void validate(FlowContext context) throws IllegalStateException {
    Context ctx = context.getContext();
    if (!Objects.equals(context.getRequest().getPlayer().getSpaceIndex(),
        ctx.getTarget().getPosition())) {
      throw new IllegalStateException("The target and the player are not in the same space.");
    }
  }

}
