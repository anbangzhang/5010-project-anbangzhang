package flowengine.action.impl.movepet;

import java.util.stream.Collectors;
import application.annotation.Component;
import flowengine.action.Action;
import flowengine.request.BaseRequest;
import world.base.BaseSpace;
import world.context.Context;

/**
 * SpaceValidateAction.
 * 
 * @author anbang
 * @date 2023-03-18 11:12
 */
@Component
public class SpaceValidateAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();

    if (!context.getSpaces().stream().map(BaseSpace::getName).collect(Collectors.toSet())
        .contains(request.getInput())) {
      throw new IllegalArgumentException(
          String.format("Space: [%s] is not in the world.", request.getInput()));
    }
  }

}
