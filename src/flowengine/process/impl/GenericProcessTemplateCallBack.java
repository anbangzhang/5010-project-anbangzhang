package flowengine.process.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.ImmutableMap;
import flowengine.action.Action;
import flowengine.process.ProcessTemplateCallBack;
import flowengine.result.BaseResult;
import world.constant.Flow;
import world.context.Context;

/**
 * GenericProcessTemplateCallBack.
 * 
 * @author anbang
 * @date 2023-03-16 21:30
 */
@Component(value = "genericProcessTemplateCallBack")
public class GenericProcessTemplateCallBack<T extends Context>
    implements ProcessTemplateCallBack<T> {

  /**
   * Flow action names map.
   */
  private final Map<String, List<String>> flowMap = ImmutableMap.<String, List<String>>builder()
      .put(Flow.MOVE_PLAYER,
          Arrays.asList("getInputAction", "neighborValidateAction", "movePlayerAction"))
      .put(Flow.PICK_UP_WEAPON,
          Arrays.asList("getInputAction", "weaponValidateAction", "pickUpWeaponAction"))
      .put(Flow.LOOK_AROUND, Collections.singletonList("lookAroundAction"))
      .put(Flow.MOVE_PET, Arrays.asList("getInputAction", "spaceValidateAction", "movePetAction"))
      .put(Flow.ATTACK_TARGET, Arrays.asList("attackValidateAction", "attackRequestAssembleAction",
          "dropWeaponAction", "attackAction"))
      .build();

  /**
   * name - action map.
   */
  @Autowired
  private Map<String, Action> actionMap;

  @Override
  public void paramValidate(T context) {
  }

  @Override
  public void bizValidate(T context) {
  }

  @Override
  public void beforeProcess(T context) {
  }

  @Override
  public void process(T context) {
    String flow = context.getFlow();
    List<String> actionNames = flowMap.getOrDefault(flow, new ArrayList<>());
    List<Action> actions = actionNames.stream().map(actionMap::get).collect(Collectors.toList());

    for (Action action : actions) {
      action.execute(context);
    }
  }

  @Override
  public void afterProcess(T context) {
  }

  @Override
  public BaseResult assemble(T context) {
    return context.getResult();
  }

}
