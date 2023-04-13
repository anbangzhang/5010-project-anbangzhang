package flowengine.action.impl.moveplayer;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import model.World;
import model.context.Context;
import model.model.Player;
import model.model.Space;
import org.springframework.stereotype.Component;

/**
 * MovePlayerAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:01
 */
@Component(value = "movePlayerAction")
public class MovePlayerAction implements Action {

  @Override
  public void execute(FlowContext context) {
    Context ctx = context.getContext();
    BaseRequest request = context.getRequest();
    Player player = request.getPlayer();
    Space space = World.getSpace(ctx, request.getInput());

    World.movePlayer(player, space);
    context.setResult(
        BaseResult.newSuccessResult().result("Player move to the neighbor space succeed.").build());

  }

}
