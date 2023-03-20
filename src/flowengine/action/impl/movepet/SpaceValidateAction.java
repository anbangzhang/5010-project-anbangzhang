package flowengine.action.impl.movepet;

import flowengine.action.Action;
import flowengine.request.BaseRequest;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import world.base.BaseSpace;
import world.context.Context;

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

    if (!context.getSpaces().stream().map(BaseSpace::getName).collect(Collectors.toSet())
        .contains(request.getInput())) {
      throw new IllegalArgumentException(
          String.format("Space: [%s] is not in the world.", request.getInput()));
    }
  }

}
