package flowengine.action.impl.lookaround;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import flowengine.result.BaseResult;
import java.util.Objects;
import model.World;
import model.context.Context;
import model.model.Pet;
import model.model.Player;
import model.model.Space;
import org.springframework.stereotype.Component;

/**
 * LookAroundAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:21
 */
@Component(value = "lookAroundAction")
public class LookAroundAction implements Action {

  @Override
  public void execute(FlowContext context) {
    Context ctx = context.getContext();
    Player player = context.getRequest().getPlayer();

    Space space = World.getSpace(ctx, player.getSpaceIndex());
    Objects.requireNonNull(space);

    ctx.getExposedSpaces().add(space);
    StringBuilder sb = new StringBuilder(space.showDetail());
    sb.append("\n").append("Neighbors: [");

    Pet pet = ctx.getPet();
    space.getNeighbors().forEach(s -> {

      ctx.getExposedSpaces().add(s);

      if (Objects.equals(s.getOrder(), pet.getSpaceIndex())) {
        sb.append("\n").append(String.format("Space: [%s], Pet: [%s]", s.getName(), pet.getName()));
      } else {
        sb.append("\n").append(s.showDetail());
      }

    });
    sb.append("]");

    context.setResult(BaseResult.newSuccessResult().result(sb.toString()).build());
  }

}
