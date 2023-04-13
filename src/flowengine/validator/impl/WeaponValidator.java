package flowengine.validator.impl;

import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.validator.Validator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import model.World;
import model.base.BaseWeapon;
import model.constant.Constants;
import model.context.Context;
import model.model.Space;
import org.springframework.stereotype.Component;

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
