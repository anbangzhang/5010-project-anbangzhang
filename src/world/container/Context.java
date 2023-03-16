package world.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import world.base.BaseSpace;
import world.base.BaseWeapon;
import world.constant.Constants;
import world.model.Pet;
import world.model.Player;
import world.model.Space;
import world.model.Target;

/**
 * Context.
 * 
 * @author anbang
 * @date 2023-03-15 16:48
 */
public class Context {

  /**
   * map.
   */
  private final Map<String, Object> map = new HashMap<>();

  /**
   * Set m.
   *
   * @param m m
   */
  public void setM(Integer m) {
    if (null == m) {
      return;
    }
    map.put(Constants.M, m);
  }

  /**
   * Get m.
   *
   * @return m
   */
  public Integer getM() {
    return (Integer) map.get(Constants.M);
  }

  /**
   * Set n.
   *
   * @param n n
   */
  public void setN(Integer n) {
    if (null == n) {
      return;
    }
    map.put(Constants.N, n);
  }

  /**
   * Get n.
   *
   * @return n
   */
  public Integer getN() {
    return (Integer) map.get(Constants.N);
  }

  /**
   * Set world name.
   * 
   * @param name name
   */
  public void setWorldName(String name) {
    if (null == name) {
      return;
    }
    map.put(Constants.WORLD_NAME, name);
  }

  /**
   * Get world name.
   * 
   * @return world name
   */
  public String getWorldName() {
    return (String) map.get(Constants.WORLD_NAME);
  }

  /**
   * Set target.
   * 
   * @param target target
   */
  public void setTarget(Target target) {
    if (null == target) {
      return;
    }
    map.put(Constants.TARGET, target);
  }

  /**
   * Get target.
   * 
   * @return target
   */
  public Target getTarget() {
    return (Target) map.get(Constants.TARGET);
  }

  /**
   * Set pet.
   *
   * @param pet pet
   */
  public void setPet(Pet pet) {
    if (null == pet) {
      return;
    }
    map.put(Constants.PET, pet);
  }

  /**
   * Get pet.
   *
   * @return pet
   */
  public Pet getPet() {
    return (Pet) map.get(Constants.PET);
  }

  /**
   * Set spaces.
   *
   * @param spaces spaces
   */
  public void setSpaces(List<BaseSpace> spaces) {
    if (null == spaces) {
      return;
    }
    map.put(Constants.SPACES, spaces);
  }

  /**
   * Get spaces.
   *
   * @return spaces
   */
  public List<BaseSpace> getSpaces() {
    return (List<BaseSpace>) map.getOrDefault(Constants.SPACES, new ArrayList<>());
  }

  /**
   * Set neighborMap.
   *
   * @param neighborMap neighborMap
   */
  public void setNeighborMap(Map<BaseSpace, List<BaseSpace>> neighborMap) {
    if (null == neighborMap) {
      return;
    }
    map.put(Constants.NEIGHBOR_MAP, neighborMap);
  }

  /**
   * Get neighborMap.
   *
   * @return neighborMap
   */
  public Map<BaseSpace, List<BaseSpace>> getNeighborMap() {
    return (Map<BaseSpace, List<BaseSpace>>) map.getOrDefault(Constants.NEIGHBOR_MAP,
        new HashMap<>());
  }

  /**
   * Set exposed spaces.
   *
   * @param spaces exposed spaces
   */
  public void setExposedSpaces(Set<Space> spaces) {
    if (null == spaces) {
      return;
    }
    map.put(Constants.EXPOSED_SPACES, spaces);
  }

  /**
   * Get exposed spaces.
   *
   * @return exposed spaces
   */
  public Set<Space> getExposedSpaces() {
    return (Set<Space>) map.getOrDefault(Constants.EXPOSED_SPACES, new HashSet<>());
  }

  /**
   * Set weapons.
   *
   * @param weapons weapons
   */
  public void setWeapons(List<BaseWeapon> weapons) {
    if (null == weapons) {
      return;
    }
    map.put(Constants.WEAPONS, weapons);
  }

  /**
   * Get weapons.
   *
   * @return weapons
   */
  public List<BaseWeapon> getWeapons() {
    return (List<BaseWeapon>) map.getOrDefault(Constants.WEAPONS, new ArrayList<>());
  }

  /**
   * Set evidences.
   *
   * @param evidences evidences
   */
  public void setEvidences(List<BaseWeapon> evidences) {
    if (null == evidences) {
      return;
    }
    map.put(Constants.EVIDENCES, evidences);
  }

  /**
   * Get weapons.
   *
   * @return evidences
   */
  public List<BaseWeapon> getEvidences() {
    return (List<BaseWeapon>) map.getOrDefault(Constants.EVIDENCES, new ArrayList<>());
  }

  /**
   * Set players.
   *
   * @param players players
   */
  public void setPlayers(List<Player> players) {
    if (null == players) {
      return;
    }
    map.put(Constants.PLAYERS, players);
  }

  /**
   * Get players.
   *
   * @return players
   */
  public List<Player> getPlayers() {
    return (List<Player>) map.getOrDefault(Constants.PLAYERS, new ArrayList<>());
  }
}
