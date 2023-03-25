package flowengine.process.impl;

import com.google.common.collect.ImmutableMap;
import flowengine.action.Action;
import flowengine.context.FlowContext;
import flowengine.enricher.Enricher;
import flowengine.enums.Flow;
import flowengine.process.BizFlowExecutor;
import flowengine.result.BaseResult;
import flowengine.validator.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import world.exception.BusinessException;

/**
 * BizFlowExecutorImpl.
 * 
 * @author anbang
 * @date 2023-03-16 21:30
 */
@Component(value = "bizFlowExecutor")
public class BizFlowExecutorImpl implements BizFlowExecutor {
  /**
   * Flow enricher names map.
   */
  private final Map<String, List<String>> flowEnrichersMap = ImmutableMap
      .<String, List<String>>builder()
      .put(Flow.MOVE_PLAYER.getDesc(), Collections.singletonList("inputEnricher"))
      .put(Flow.PICK_UP_WEAPON.getDesc(), Collections.singletonList("inputEnricher"))
      .put(Flow.MOVE_PET.getDesc(), Collections.singletonList("inputEnricher")).build();
  /**
   * Flow validator names map.
   */
  private final Map<String, List<String>> flowValidatorsMap = ImmutableMap
      .<String, List<String>>builder()
      .put(Flow.MOVE_PLAYER.getDesc(), Collections.singletonList("neighborValidator"))
      .put(Flow.PICK_UP_WEAPON.getDesc(), Collections.singletonList("weaponValidator"))
      .put(Flow.MOVE_PET.getDesc(), Collections.singletonList("spaceValidator"))
      .put(Flow.ATTACK_TARGET.getDesc(), Collections.singletonList("attackValidator")).build();
  /**
   * Flow action names map.
   */
  private final Map<String, List<String>> flowActionsMap = ImmutableMap
      .<String, List<String>>builder()
      .put(Flow.MOVE_PLAYER.getDesc(), Collections.singletonList("movePlayerAction"))
      .put(Flow.PICK_UP_WEAPON.getDesc(), Collections.singletonList("pickUpWeaponAction"))
      .put(Flow.LOOK_AROUND.getDesc(), Collections.singletonList("lookAroundAction"))
      .put(Flow.MOVE_PET.getDesc(), Collections.singletonList("movePetAction"))
      .put(Flow.ATTACK_TARGET.getDesc(),
          Arrays.asList("attackRequestAssembleAction", "dropWeaponAction", "attackAction"))
      .put(Flow.PET_DFS.getDesc(), Collections.singletonList("petTraverseAction")).build();
  /**
   * name - enricher map.
   */
  @Autowired
  private Map<String, Enricher> enricherMap;
  /**
   * name - validator map.
   */
  @Autowired
  private Map<String, Validator> validatorMap;
  /**
   * name - action map.
   */
  @Autowired
  private Map<String, Action> actionMap;

  @Override
  public void startProcess(FlowContext ctx) throws IOException {
    String flow = ctx.getFlow();

    List<Enricher> enrichers = getEnrichers(flow);

    executeEnricher(enrichers, ctx);

    List<Validator> validators = getValidators(flow);

    executeValidator(validators, ctx);

    List<Action> actions = getActions(flow);

    executeAction(actions, ctx);
  }

  private List<Enricher> getEnrichers(String flow) {
    List<String> enricherNames = flowEnrichersMap.getOrDefault(flow, new ArrayList<>());
    return enricherNames.stream().map(enricherMap::get).collect(Collectors.toList());
  }

  private void executeEnricher(List<Enricher> enrichers, FlowContext ctx) throws IOException {
    for (Enricher enricher : enrichers) {
      enricher.enrich(ctx);
    }
  }

  private List<Validator> getValidators(String flow) {
    List<String> validatorNames = flowValidatorsMap.getOrDefault(flow, new ArrayList<>());
    return validatorNames.stream().map(validatorMap::get).collect(Collectors.toList());
  }

  private void executeValidator(List<Validator> validators, FlowContext ctx) {
    for (Validator validator : validators) {
      validator.validate(ctx);
    }
  }

  private List<Action> getActions(String flow) {
    List<String> actionNames = flowActionsMap.getOrDefault(flow, new ArrayList<>());
    return actionNames.stream().map(actionMap::get).collect(Collectors.toList());
  }

  private void executeAction(List<Action> actions, FlowContext ctx) throws IOException {
    try {
      for (Action action : actions) {
        action.execute(ctx);
      }
    } catch (BusinessException e) {
      ctx.setResult(BaseResult.newFailResult().errorMsg(e.getMessage()).build());
    }
  }

}
