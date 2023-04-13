package controller.impl;

import controller.WorldController;
import controller.gameengine.GameEngine;
import flowengine.enums.Flow;
import flowengine.template.ServiceTemplate;
import java.util.Objects;

import model.World;
import model.context.Context;
import model.model.Player;
import model.model.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * AbstractWorldController.
 * 
 * @author anbang
 * @date 2023-04-05 01:25
 */
public abstract class AbstractWorldController implements WorldController {

  protected Integer turn;

  protected Integer maxPlayerAmount;

  protected GameEngine gameEngine;

  @Autowired
  @Qualifier(value = "serviceTemplate")
  protected ServiceTemplate serviceTemplate;

  @Override
  public void setTurn(Integer turn) {
    if (Objects.isNull(turn)) {
      throw new IllegalArgumentException("Invalid turn.");
    }
    this.turn = turn;
  }

  @Override
  public void setMaxPlayerAmount(Integer amount) {
    if (Objects.isNull(turn)) {
      throw new IllegalArgumentException("Invalid max player amount.");
    }
    this.maxPlayerAmount = amount;
  }

  protected Flow determineFlowForComputer(Context ctx, Player player) {
    Space space = World.getSpace(ctx, player.getSpaceIndex());

    if (Objects.equals(ctx.getTarget().getPosition(), player.getSpaceIndex())) {
      return Flow.ATTACK_TARGET;
    } else if (space.getWeapons().size() > 0
        && player.getWeapons().size() < player.getWeaponLimit()) {
      return Flow.PICK_UP_WEAPON;
    }
    return Flow.LOOK_AROUND;
  }

}
