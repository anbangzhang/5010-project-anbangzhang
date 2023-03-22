package flowengine.action.impl.attack;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.context.Context;

/**
 * AttackValidateAction.
 * 
 * @author anbang
 * @date 2023-03-18 10:15
 */
@Component(value = "attackValidateAction")
public class AttackValidateAction implements Action {

  @Override
  public void execute(FlowContext context) throws IllegalStateException {
    Context ctx = context.getContext();
    if (!Objects.equals(context.getRequest().getPlayer().getSpaceIndex(),
        ctx.getTarget().getPosition())) {
      throw new IllegalStateException("The target and the player are not in the same space.");
    }
  }

}
