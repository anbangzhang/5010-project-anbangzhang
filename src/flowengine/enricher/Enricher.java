package flowengine.enricher;

import flowengine.context.FlowContext;
import java.io.IOException;

/**
 * Enricher.
 * 
 * @author anbang
 * @date 2023-03-25 00:24
 */
public interface Enricher {

  /**
   * Enrich the context.
   * 
   * @param context context
   */
  void enrich(FlowContext context) throws IOException;
}
