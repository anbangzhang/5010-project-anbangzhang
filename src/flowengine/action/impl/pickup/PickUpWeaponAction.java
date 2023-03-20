package flowengine.action.impl.pickup;

import flowengine.action.Action;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import java.util.Objects;
import org.springframework.stereotype.Component;
import world.World;
import world.base.BaseWeapon;
import world.context.Context;
import world.model.Player;

/**
 * PickUpWeaponAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:22
 */
@Component(value = "pickUpWeaponAction")
public class PickUpWeaponAction implements Action {

  @Override
  public void execute(Context context) {
    BaseRequest request = context.getRequest();
    Player player = request.getPlayer();

    BaseWeapon weapon = Objects
        .requireNonNull(World.getSpace(context, request.getPlayer().getSpaceIndex())).getWeapons()
        .stream().filter(item -> Objects.equals(item.getName(), request.getInput())).findFirst()
        .orElse(null);

    Objects.requireNonNull(weapon);
    if (!player.addWeapon(weapon)) {
      throw new IllegalStateException("Player's weapon amount reaches limit.");
    }
    weapon.setHolder(String.format("player: %s", player.getName()));
    context
        .setResult(BaseResult.newSuccessResult().result("Player pick up weapon succeed.").build());
  }

}
