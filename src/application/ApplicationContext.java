package application;

/**
 * ApplicationContext.
 * 
 * @author anbang
 * @date 2023-03-19 18:06
 */
public interface ApplicationContext {

  /**
   * Get bean based on the annotation.
   * 
   * @param clazz class
   * @return bean
   */
  Object getBean(Class clazz);

}
