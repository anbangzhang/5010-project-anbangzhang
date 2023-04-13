package controller.impl;

import controller.WorldController;
import flowengine.template.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;

/**
 * AbstractWorldController.
 * 
 * @author anbang
 * @date 2023-04-05 01:25
 */
public abstract class AbstractWorldController implements WorldController {

  protected Integer turn;

  protected Integer maxPlayerAmount;

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

}
