package flowengine.action.impl.moveplayer;

import org.springframework.stereotype.Component;
import flowengine.action.Action;
import flowengine.request.BaseRequest;
import world.World;
import world.context.Context;

/**
 * NeighborValidateAction.
 * 
 * @author anbang
 * @date 2023-03-18 11:19
 */
@Component(value = "neighborValidateAction")
public class NeighborValidateAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();

    if (!World.getNeighbors(context, request.getPlayer().getSpaceIndex())
        .contains(request.getInput())) {
      throw new IllegalArgumentException(String
          .format("Space: [%s] is not the neighbor of the current space.", request.getInput()));
    }
  }

}
