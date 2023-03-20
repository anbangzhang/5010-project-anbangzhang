package flowengine.action.impl.movepet;

import org.springframework.stereotype.Component;
import flowengine.action.Action;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import world.World;
import world.context.Context;
import world.model.Space;

/**
 * MovePetAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:31
 */
@Component(value = "movePetAction")
public class MovePetAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();
    Space space = World.getSpace(context, request.getInput());

    context.getPet().setSpaceIndex(space.getOrder());

    context.setResult(BaseResult.newSuccessResult().result("Player move the pet succeed.").build());
  }

}
