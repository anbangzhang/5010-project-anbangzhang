package flowengine.action.impl.movepet;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import model.World;
import model.context.Context;
import model.model.Space;
import org.springframework.stereotype.Component;

/**
 * MovePetAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:31
 */
@Component(value = "movePetAction")
public class MovePetAction implements Action {

  @Override
  public void execute(FlowContext context) {
    Context ctx = context.getContext();
    BaseRequest request = context.getRequest();
    Space space = World.getSpace(ctx, request.getInput());

    ctx.getPet().setSpaceIndex(space.getOrder());

    context.setResult(BaseResult.newSuccessResult().result("Player move the pet succeed.").build());
  }

}
