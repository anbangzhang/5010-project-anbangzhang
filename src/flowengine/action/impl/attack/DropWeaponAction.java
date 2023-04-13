package flowengine.action.impl.attack;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import flowengine.request.AttackRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;
import model.base.BaseWeapon;
import model.context.Context;

/**
 * DropWeaponAction.
 * 
 * @author anbang
 * @date 2023-03-18 09:01
 */
@Component(value = "dropWeaponAction")
public class DropWeaponAction implements Action {

  @Override
  public void execute(FlowContext context) {
    AttackRequest request = (AttackRequest) context.getRequest();
    Context ctx = context.getContext();
    BaseWeapon weapon = request.getWeapon();
    if (Objects.nonNull(weapon)) {
      ctx.getWeapons().remove(weapon);
      ctx.getEvidences().add(weapon);
    }
  }

}
