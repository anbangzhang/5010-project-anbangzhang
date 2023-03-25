package flowengine.validator.impl;

import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.validator.Validator;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.World;
import world.context.Context;
import world.model.Space;

/**
 * SpaceValidator.
 * 
 * @author anbang
 * @date 2023-03-18 11:12
 */
@Component(value = "spaceValidator")
public class SpaceValidator implements Validator {

  @Override
  public void validate(FlowContext context) {
    BaseRequest request = context.getRequest();
    Context ctx = context.getContext();

    Space space = World.getSpace(ctx, request.getInput());
    if (Objects.isNull(space)) {
      throw new IllegalArgumentException(
          String.format("Space: [%s] is not in the world.", request.getInput()));
    }

    if (Objects.equals(space.getOrder(), ctx.getPet().getSpaceIndex())) {
      throw new IllegalStateException(
          String.format("The pet is already in Space: [%s].", request.getInput()));
    }
  }

}
