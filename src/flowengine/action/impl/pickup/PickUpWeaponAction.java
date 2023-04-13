package flowengine.action.impl.pickup;

import flowengine.action.Action;
import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import java.util.Objects;
import org.springframework.stereotype.Component;
import model.World;
import model.base.BaseWeapon;
import model.context.Context;
import model.model.Player;

/**
 * PickUpWeaponAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:22
 */
@Component(value = "pickUpWeaponAction")
public class PickUpWeaponAction implements Action {

  @Override
  public void execute(FlowContext context) {
    Context ctx = context.getContext();
    BaseRequest request = context.getRequest();
    Player player = request.getPlayer();

    BaseWeapon weapon = Objects
        .requireNonNull(World.getSpace(ctx, request.getPlayer().getSpaceIndex())).getWeapons()
        .stream().filter(item -> Objects.equals(item.getName(), request.getInput())).findFirst()
        .orElse(null);

    World.pickUp(player, weapon);
    context
        .setResult(BaseResult.newSuccessResult().result("Player pick up weapon succeed.").build());

  }

}
