package model.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.base.BaseSpace;
import model.base.BaseWeapon;
import model.constant.Constants;
import model.model.Pet;
import model.model.Player;
import model.model.Space;
import model.model.Target;

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
  protected final Map<String, Object> map = new HashMap<>();

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
   * Set model name.
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
   * Get model name.
   * 
   * @return model name
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
    map.computeIfAbsent(Constants.SPACES, k -> new ArrayList<>());
    return (List<BaseSpace>) map.get(Constants.SPACES);
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
    map.computeIfAbsent(Constants.NEIGHBOR_MAP, k -> new HashMap<>());
    return (Map<BaseSpace, List<BaseSpace>>) map.get(Constants.NEIGHBOR_MAP);
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
    map.computeIfAbsent(Constants.EXPOSED_SPACES, k -> new HashSet<>());
    return (Set<Space>) map.get(Constants.EXPOSED_SPACES);
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
    map.computeIfAbsent(Constants.WEAPONS, k -> new ArrayList<>());
    return (List<BaseWeapon>) map.get(Constants.WEAPONS);
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
    map.computeIfAbsent(Constants.EVIDENCES, k -> new ArrayList<>());
    return (List<BaseWeapon>) map.get(Constants.EVIDENCES);
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
    map.computeIfAbsent(Constants.PLAYERS, k -> new ArrayList<>());
    return (List<Player>) map.get(Constants.PLAYERS);
  }

  /**
   * Set key and value.
   * 
   * @param key   key
   * @param value value
   */
  public void setStr(String key, String value) {
    map.put(key, value);
  }

  /**
   * Get value from key.
   * 
   * @param key key
   * @return value
   */
  public String getStr(String key) {
    return (String) map.get(key);
  }

  /**
   * Set object.
   * 
   * @param key   key
   * @param value value
   */
  public void set(String key, Object value) {
    map.put(key, value);
  }

  /**
   * Get object.
   * 
   * @param key key
   * @return value
   */
  public Object get(String key) {
    return map.get(key);
  }

}
