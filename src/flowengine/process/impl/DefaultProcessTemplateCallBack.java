package flowengine.process.impl;

import java.util.ArrayList;
import java.util.List;
import flowengine.action.Action;
import flowengine.process.ProcessTemplateCallBack;
import flowengine.result.BaseResult;
import world.context.Context;

/**
 * DefaultProcessTemplateCallBack.
 * 
 * @author anbang
 * @date 2023-03-16 21:30
 */
public class DefaultProcessTemplateCallBack<T extends Context>
    implements ProcessTemplateCallBack<T> {

  /**
   * Actions.
   */
  private final List<Action> actions = new ArrayList<>();

  /**
   * Constructor.
   * 
   * @param actions
   */
  public DefaultProcessTemplateCallBack(List<Action> actions) {
    this.actions.addAll(actions);
  }

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
