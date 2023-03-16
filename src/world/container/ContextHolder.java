package world.container;

/**
 * ContextHolder.
 * 
 * @author anbang
 * @date 2023-03-15 23:28
 */
public class ContextHolder {

  /**
   * Holder.
   */
  private static final ThreadLocal<Context> HOLDER = new InheritableThreadLocal<>();

  /**
   * Set method.
   * 
   * @param ctx context
   */
  public static void set(Context ctx) {
    HOLDER.set(ctx);
  }

  /**
   * Get method.
   * 
   * @return context
   */
  public static Context get() {
    return HOLDER.get();
  }

  /**
   * Clear.
   */
  public static void remove() {
    HOLDER.remove();
  }

}
