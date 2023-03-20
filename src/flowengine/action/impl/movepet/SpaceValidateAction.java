package flowengine.action.impl.movepet;

import flowengine.action.Action;
import flowengine.request.BaseRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.World;
import world.context.Context;
import world.model.Space;

/**
 * SpaceValidateAction.
 * 
 * @author anbang
 * @date 2023-03-18 11:12
 */
@Component(value = "spaceValidateAction")
public class SpaceValidateAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();

    Space space = World.getSpace(context, request.getInput());
    if (Objects.isNull(space)) {
      throw new IllegalArgumentException(
          String.format("Space: [%s] is not in the world.", request.getInput()));
    }

    if (Objects.equals(space.getOrder(), context.getPet().getSpaceIndex())) {
      throw new IllegalStateException(
          String.format("The pet is already in Space: [%s].", request.getInput()));
    }
  }

}
