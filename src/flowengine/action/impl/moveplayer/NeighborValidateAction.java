package flowengine.action.impl.moveplayer;

import flowengine.action.Action;
import flowengine.request.BaseRequest;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.World;
import world.constant.Constants;
import world.context.Context;
import world.model.Space;

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

    if (!Objects.equals(Constants.COMPUTER_DEFAULT, request.getInput())) {
      if (!World.getNeighbors(context, request.getPlayer().getSpaceIndex())
          .contains(request.getInput())) {
        throw new IllegalArgumentException(String
            .format("Space: [%s] is not the neighbor of the current space.", request.getInput()));
      }
    } else {
      List<Space> neighbors = World.getSpace(context, request.getPlayer().getSpaceIndex())
          .getNeighbors();
      if (neighbors.isEmpty()) {
        throw new IllegalStateException("The current space has no neighbor spaces.");
      }
      request.setInput(neighbors.get(0).getName());
    }
  }

}
