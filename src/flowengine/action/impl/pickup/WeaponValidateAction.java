package flowengine.action.impl.pickup;

import java.util.Objects;
import java.util.stream.Collectors;
import application.annotation.Component;
import flowengine.action.Action;
import flowengine.request.BaseRequest;
import world.World;
import world.base.BaseWeapon;
import world.context.Context;
import world.model.Space;

/**
 * WeaponValidateAction.
 * 
 * @author anbang
 * @date 2023-03-18 12:48
 */
@Component
public class WeaponValidateAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();

    Space space = World.getSpace(context, request.getPlayer().getSpaceIndex());
    if (!Objects.requireNonNull(space).getWeapons().stream().map(BaseWeapon::getName)
        .collect(Collectors.toSet()).contains(request.getInput())) {
      throw new IllegalArgumentException(
          String.format("Weapon: [%s] is not in the current space.", request.getInput()));
    }
  }

}
