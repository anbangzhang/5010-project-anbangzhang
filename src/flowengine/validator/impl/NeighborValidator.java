package flowengine.validator.impl;

import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.validator.Validator;
import java.util.List;
import java.util.Objects;
import model.World;
import model.constant.Constants;
import model.context.Context;
import model.model.Space;
import org.springframework.stereotype.Component;

/**
 * NeighborValidator.
 * 
 * @author anbang
 * @date 2023-03-18 11:19
 */
@Component(value = "neighborValidator")
public class NeighborValidator implements Validator {

  @Override
  public void validate(FlowContext context) {
    BaseRequest request = context.getRequest();
    Context ctx = context.getContext();

    if (!Objects.equals(Constants.COMPUTER_DEFAULT, request.getInput())) {
      if (!World.getNeighbors(ctx, request.getPlayer().getSpaceIndex())
          .contains(request.getInput())) {
        throw new IllegalArgumentException(String
            .format("Space: [%s] is not the neighbor of the current space.", request.getInput()));
      }
    } else {
      List<Space> neighbors = World.getSpace(ctx, request.getPlayer().getSpaceIndex())
          .getNeighbors();
      if (neighbors.isEmpty()) {
        throw new IllegalStateException("The current space has no neighbor spaces.");
      }
      request.setInput(neighbors.get(0).getName());
    }
  }

}
