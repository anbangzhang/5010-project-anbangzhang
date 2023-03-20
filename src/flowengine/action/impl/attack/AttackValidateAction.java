package flowengine.action.impl.attack;

import java.util.Objects;
import org.springframework.stereotype.Component;
import flowengine.action.Action;
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
  public void execute(Context context) throws IllegalStateException {
    if (!Objects.equals(context.getRequest().getPlayer().getSpaceIndex(),
        context.getTarget().getPosition())) {
      throw new IllegalStateException("The target and the player are not in the same space.");
    }
  }

}
