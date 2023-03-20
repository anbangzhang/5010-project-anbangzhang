package flowengine.template;

import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;

/**
 * ServiceTemplate.
 * 
 * @author anbang
 * @date 2023-03-16 21:38
 */
public interface ServiceTemplate<T extends BaseRequest> {

  /**
   * Execute.
   * 
   * @param flowName flow name
   * @param request  request
   * @return result
   */
  BaseResult execute(String flowName, T request);

}