package world.base;

/**
 * Base class of space.
 * 
 * @author anbang
 * @date 2023-01-28 22:30
 */
public class BaseSpace {

  /**
   * Start of row.
   */
  private int startX;

  /**
   * Start of col.
   */
  private int startY;

  /**
   * End of row.
   */
  private int endX;

  /**
   * End of col.
   */
  private int endY;

  /**
   * Index order.
   */
  private int order;

  /**
   * Space name.
   */
  private String name;

  /**
   * Constructor of base space.
   * 
   * @param baseSpace base space.
   */
  public BaseSpace(BaseSpace baseSpace) {
    this.startX = baseSpace.startX;
    this.startY = baseSpace.startY;
    this.endX = baseSpace.endX;
    this.endY = baseSpace.endY;
    this.order = baseSpace.order;
    this.name = baseSpace.name;
  }

  /**
   * Constructor of base space.
   * 
   * @param startX startX
   * @param startY startY
   * @param endX   endX
   * @param endY   endY
   * @param order  ORDER
   * @param name   name
   * @throws IllegalArgumentException if param is invalid
   */
  public BaseSpace(int startX, int startY, int endX, int endY, int order, String name)
      throws IllegalArgumentException {
    if (startX < 0 || startY < 0 || endX < 0 || endY < 0 || endX <= startX || endY <= startY) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
    if (order < 0) {
      throw new IllegalArgumentException("Invalid order.");
    }
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.order = order;
    this.name = name;
  }

  /**
   * Getter of start position.
   * 
   * @return start position
   */
  public int[] getStart() {
    return new int[] { this.startX, this.startY };
  }

  /**
   * Getter of end position.
   *
   * @return end position
   */
  public int[] getEnd() {
    return new int[] { this.endX, this.endY };
  }

  /**
   * Getter of order.
   * 
   * @return order
   */
  public int getOrder() {
    return this.order;
  }

  /**
   * Getter of space name.
   * 
   * @return name
   */
  public String getName() {
    return this.name;
  }
}
