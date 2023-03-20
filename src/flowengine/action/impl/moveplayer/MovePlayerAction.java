package flowengine.action.impl.moveplayer;

import application.annotation.Component;
import flowengine.action.Action;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import world.World;
import world.context.Context;
import world.model.Player;
import world.model.Space;

/**
 * MovePlayerAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:01
 */
@Component
public class MovePlayerAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();
    Player player = request.getPlayer();

    Space space = World.getSpace(context, request.getInput());
    player.setSpaceIndex(space.getOrder());

    context.setResult(
        BaseResult.newSuccessResult().result("Player move to the neighbor space succeed.").build());
  }

}
