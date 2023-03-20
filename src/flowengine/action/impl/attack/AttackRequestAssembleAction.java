package flowengine.action.impl.attack;

import java.util.List;
import org.springframework.stereotype.Component;
import flowengine.action.Action;
import flowengine.request.AttackRequest;
import flowengine.request.BaseRequest;
import world.base.BaseWeapon;
import world.context.Context;
import world.model.Player;

/**
 * AttackRequestAssembleAction.
 * 
 * @author anbang
 * @date 2023-03-18 08:43
 */
@Component(value = "attackRequestAssembleAction")
public class AttackRequestAssembleAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();
    Player player = request.getPlayer();

    AttackRequest attackRequest = new AttackRequest(player);
    attackRequest.setInput(request.getInput());
    List<BaseWeapon> weapons = player.getWeapons();
    attackRequest.setWeapon(weapons.isEmpty() ? null : weapons.get(0));

    context.setRequest(attackRequest);
  }

}
