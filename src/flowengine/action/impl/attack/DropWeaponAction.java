package flowengine.action.impl.attack;

import flowengine.action.Action;
import flowengine.request.AttackRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.base.BaseWeapon;
import world.context.Context;

/**
 * DropWeaponAction.
 * 
 * @author anbang
 * @date 2023-03-18 09:01
 */
@Component(value = "dropWeaponAction")
public class DropWeaponAction implements Action {

  @Override
  public void execute(Context context) {
    AttackRequest request = (AttackRequest) context.getRequest();
    BaseWeapon weapon = request.getWeapon();
    if (Objects.nonNull(weapon)) {
      context.getWeapons().remove(weapon);
      context.getEvidences().add(weapon);
    }
  }

}
