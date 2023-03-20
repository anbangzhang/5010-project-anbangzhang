package flowengine.action.impl.pickup;

import flowengine.action.Action;
import flowengine.request.BaseRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import world.World;
import world.base.BaseWeapon;
import world.constant.Constants;
import world.context.Context;
import world.model.Space;

/**
 * WeaponValidateAction.
 * 
 * @author anbang
 * @date 2023-03-18 12:48
 */
@Component(value = "weaponValidateAction")
public class WeaponValidateAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();

    if (!Objects.equals(Constants.COMPUTER_DEFAULT, request.getInput())) {
      Space space = World.getSpace(context, request.getPlayer().getSpaceIndex());
      if (!Objects.requireNonNull(space).getWeapons().stream().map(BaseWeapon::getName)
          .collect(Collectors.toSet()).contains(request.getInput())) {
        throw new IllegalArgumentException(
            String.format("Weapon: [%s] is not in the current space.", request.getInput()));
      }
    } else {
      List<BaseWeapon> weapons = World.getSpace(context, request.getPlayer().getSpaceIndex())
          .getWeapons();
      if (weapons.isEmpty()) {
        throw new IllegalStateException("There is no weapon in the space.");
      }
      // Get the first weapon
      request.setInput(weapons.get(0).getName());
    }
  }

}
