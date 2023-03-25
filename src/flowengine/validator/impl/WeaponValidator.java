package flowengine.validator.impl;

import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.validator.Validator;
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
 * WeaponValidator.
 * 
 * @author anbang
 * @date 2023-03-18 12:48
 */
@Component(value = "weaponValidator")
public class WeaponValidator implements Validator {

  @Override
  public void validate(FlowContext context) {
    Context ctx = context.getContext();
    BaseRequest request = context.getRequest();

    if (!Objects.equals(Constants.COMPUTER_DEFAULT, request.getInput())) {
      Space space = World.getSpace(ctx, request.getPlayer().getSpaceIndex());
      if (!Objects.requireNonNull(space).getWeapons().stream().map(BaseWeapon::getName)
          .collect(Collectors.toSet()).contains(request.getInput())) {
        throw new IllegalArgumentException(
            String.format("Weapon: [%s] is not in the current space.", request.getInput()));
      }
    } else {
      List<BaseWeapon> weapons = World.getSpace(ctx, request.getPlayer().getSpaceIndex())
          .getWeapons();
      if (weapons.isEmpty()) {
        throw new IllegalStateException("There is no weapon in the space.");
      }
      // Get the first weapon
      request.setInput(weapons.get(0).getName());
    }
  }

}
