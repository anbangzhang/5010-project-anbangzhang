package flowengine;

import java.util.Objects;

/**
 * Flow.
 * 
 * @author anbang
 * @date 2023-03-20 06:40
 */
public enum Flow {
  MOVE_PLAYER(1, "MOVE_PLAYER"), PICK_UP_WEAPON(2, "PICK_UP_WEAPON"), LOOK_AROUND(3, "LOOK_AROUND"),
  MOVE_PET(4, "MOVE_PET"), ATTACK_TARGET(5, "ATTACK_TARGET"), PET_DFS(999, "PET_DFS");

  private int code;
  private String desc;

  /**
   * Constructor.
   * 
   * @param code code
   * @param desc desc
   */
  Flow(int code, String desc) {
    this.code = code;
    this.desc = desc;
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
