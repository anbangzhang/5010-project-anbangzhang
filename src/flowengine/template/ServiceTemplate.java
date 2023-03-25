package flowengine.template;

import flowengine.process.BaseProcessCallBack;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import java.io.IOException;

/**
 * ServiceTemplate.
 * 
 * @author anbang
 * @date 2023-03-16 21:38
 */
public interface ServiceTemplate<T extends BaseRequest, R extends BaseResult> {

  /**
   * Execute.
   * 
   * @param flowName flow name
   * @param request  request
   * @param callBack callback
   * @return result
   * @throws IOException io exception
   */
  R execute(T request, String flowName, BaseProcessCallBack<T, R> callBack) throws IOException;

}