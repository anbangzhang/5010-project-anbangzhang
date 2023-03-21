package flowengine.action.impl.attack;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import flowengine.request.AttackRequest;
import flowengine.result.BaseResult;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.World;
import world.constant.Constants;
import world.context.Context;
import world.model.Player;
import world.model.Space;

/**
 * AttackAction.
 * 
 * @author anbang
 * @date 2023-03-15 19:59
 */
@Component(value = "attackAction")
public class AttackAction implements Action {

  @Override
  public void execute(FlowContext context) {
    AttackRequest request = (AttackRequest) context.getRequest();
    Context ctx = context.getContext();
    Player player = request.getPlayer();
    Space space = World.getSpace(ctx, player.getSpaceIndex());

    int damage = Objects.isNull(request.getWeapon()) ? 1 : request.getWeapon().getDamage();
    List<Player> players = Objects.requireNonNull(space).getOccupiers();

    // there's other players in the space
    if (players.size() > 1) {
      context.setResult(buildAttackSeenResult());
      return;
      // the pet is not in the space, and the space is exposed
    } else if (!Objects.equals(ctx.getPet().getSpaceIndex(), player.getSpaceIndex())
        && ctx.getExposedSpaces().contains(space)) {
      context.setResult(buildAttackSeenResult());
      return;
    }
    int health = ctx.getTarget().decreaseHealth(damage);

    if (health == 0) {
      ctx.set(Constants.WINNER, player);
    }
    context.setResult(buildSuccessResult());
  }

  private BaseResult buildAttackSeenResult() {
    return BaseResult.newSuccessResult().result("Attack failed, the attack is seen.").build();
  }

  private BaseResult buildSuccessResult() {
    return BaseResult.newSuccessResult().result("Attack succeed.").build();
  }

}
