package flowengine.enums;

import java.util.Objects;

/**
 * Flow.
 * 
 * @author anbang
 * @date 2023-03-20 06:40
 */
public enum Flow {
  MOVE_PLAYER(1, "MOVE_PLAYER", "move to"), PICK_UP_WEAPON(2, "PICK_UP_WEAPON", "pick up"),
  LOOK_AROUND(3, "LOOK_AROUND", "look around"), MOVE_PET(4, "MOVE_PET", "move the pet to"),
  ATTACK_TARGET(5, "ATTACK_TARGET", "attack the target with"), PET_DFS(999, "PET_DFS", "move to");

  private int code;
  private String desc;
  private String action;

  /**
   * Constructor.
   * 
   * @param code   code
   * @param desc   desc
   * @param action action
   */
  Flow(int code, String desc, String action) {
    this.code = code;
    this.desc = desc;
    this.action = action;
  }

  /**
   * Get code.
   * 
   * @return code
   */
  public int getCode() {
    return this.code;
  }

  /**
   * Get desc.
   * 
   * @return desc
   */
  public String getDesc() {
    return this.desc;
  }

  /**
   * Get action.
   * 
   * @return action
   */
  public String getAction() {
    return this.action;
  }

  /**
   * Get by code.
   * 
   * @param code code
   * @return flow
   */
  public static Flow getByCode(int code) {
    for (Flow flow : values()) {
      if (code == flow.getCode()) {
        return flow;
      }
    }
    throw new IllegalArgumentException("Code not Exist");
  }

  /**
   * Get by desc.
   *
   * @param desc desc
   * @return flow
   */
  public static Flow getByDesc(String desc) {
    for (Flow flow : values()) {
      if (Objects.equals(desc, flow.getDesc())) {
        return flow;
      }
    }
    throw new IllegalArgumentException("Desc not Exist");
  }

}
