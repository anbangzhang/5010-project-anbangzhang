package flowengine.action.impl.lookaround;

import java.util.Objects;
import application.annotation.Component;
import flowengine.action.Action;
import flowengine.result.BaseResult;
import world.World;
import world.context.Context;
import world.model.Pet;
import world.model.Player;
import world.model.Space;

/**
 * LookAroundAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:21
 */
@Component
public class LookAroundAction implements Action {

  @Override
  public void execute(Context context) {
    Player player = context.getRequest().getPlayer();

    Space space = World.getSpace(context, player.getSpaceIndex());
    Objects.requireNonNull(space);

    context.getExposedSpaces().add(space);
    StringBuilder sb = new StringBuilder(space.showDetail());
    sb.append("\n").append("Neighbors: [");

    Pet pet = context.getPet();
    space.getNeighbors().forEach(s -> {

      context.getExposedSpaces().add(s);

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
