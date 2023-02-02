package world.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import world.Space;
import world.Target;
import world.Weapon;
import world.World;
import world.base.BaseSpace;
import world.base.BaseWeapon;

/**
 * WorldImpl class. Implementation of world interface.
 * 
 * @author anbang
 * @date 2023-01-28 22:51
 */
public class WorldImpl implements World {

  /**
   * Scale factor.
   */
  private static final int SCALE_FACTOR = 20;

  /**
   * The world represent in int.
   */
  private int[][] matrix;

  /**
   * World name.
   */
  private String name;

  /**
   * Target character.
   */
  private Target target;

  /**
   * Spaces in the world.
   */
  private List<Space> spaces;

  /**
   * Input stream.
   */
  private Readable in;

  /**
   * Construct world with readable source.
   * 
   * @param in input source
   */
  public WorldImpl(Readable in) {
    if (Objects.isNull(in)) {
      throw new IllegalArgumentException("Invalid input source.");
    }
    this.in = in;
    Scanner scan = new Scanner(this.in);
    /* World info */
    int m = scan.nextInt();
    int n = scan.nextInt();
    this.name = scan.nextLine().trim();

    /* Target info */
    int health = scan.nextInt();
    String targetName = scan.nextLine().trim();
    this.target = new Target(health, targetName);

    /* Spaces info */
    int sizeOfSpace = scan.nextInt();
    List<BaseSpace> baseSpaces = new ArrayList<>(sizeOfSpace);
    for (int i = 0; i < sizeOfSpace; i++) {
      int x1 = scan.nextInt();
      int y1 = scan.nextInt();
      int x2 = scan.nextInt();
      int y2 = scan.nextInt();
      String spaceName = scan.nextLine().trim();
      baseSpaces.add(new BaseSpace(x1, y1, x2, y2, i, spaceName));
    }

    /* Weapons info */
    int sizeOfWeapon = scan.nextInt();
    List<BaseWeapon> baseWeapons = new ArrayList<>(sizeOfWeapon);
    for (int i = 0; i < sizeOfWeapon; i++) {
      int index = scan.nextInt();
      int damage = scan.nextInt();
      String weaponName = scan.nextLine().trim();
      baseWeapons.add(new BaseWeapon(index, damage, weaponName));
    }

    initMatrixAndValidateSpaces(m, n, baseSpaces);
    initNeighborMapAndSpaces(m, n, baseSpaces, baseWeapons);
  }

  /**
   * Constructor of World.
   * 
   * @param m           row
   * @param n           col
   * @param name        name
   * @param target      target
   * @param baseSpaces  base spaces
   * @param baseWeapons base weapons
   */
  public WorldImpl(int m, int n, String name, Target target, List<BaseSpace> baseSpaces,
      List<BaseWeapon> baseWeapons) {
    baseSpaces.sort(Comparator.comparingInt(BaseSpace::getOrder));
    initMatrixAndValidateSpaces(m, n, baseSpaces);
    this.name = name;
    this.target = target;
    initNeighborMapAndSpaces(m, n, baseSpaces, baseWeapons);
  }

  /**
   * Init the matrix and validate the input base spaces.
   * 
   * @param m          row
   * @param n          col
   * @param baseSpaces base spaces
   * @throws IllegalArgumentException any param is invalid
   */
  private void initMatrixAndValidateSpaces(int m, int n, List<BaseSpace> baseSpaces)
      throws IllegalArgumentException {
    if (m <= 0 || n <= 0) {
      throw new IllegalArgumentException("World's width and length is smaller than 0.");
    }

    this.matrix = new int[m][n];
    for (int i = 0; i < m; i++) {
      Arrays.fill(this.matrix[i], -1);
    }

    for (BaseSpace base : baseSpaces) {
      int[] start = base.getStart();
      int[] end = base.getEnd();

      if (start[0] < 0 || start[0] >= m || start[1] < 0 || start[1] >= n) {
        throw new IllegalArgumentException(
            "There is a space whose upper left corner coordinates are invalid.");
      }
      if (end[0] < 0 || end[0] >= m || end[1] < 0 || end[1] >= n) {
        throw new IllegalArgumentException(
            "There is a space whose lower right corner coordinates are invalid.");
      }

      for (int i = start[0]; i <= end[0]; i++) {
        for (int j = start[1]; j <= end[1]; j++) {
          if (matrix[i][j] != -1) {
            throw new IllegalArgumentException("There is a overlap in the spaces.");
          } else {
            matrix[i][j] = base.getOrder();
          }
        }
      }
    }
  }

  /**
   * Calculate neighbor map based on the matrix.
   * 
   * @param m           row
   * @param n           col
   * @param baseSpaces  base spaces
   * @param baseWeapons base weapons
   */
  private void initNeighborMapAndSpaces(int m, int n, List<BaseSpace> baseSpaces,
      List<BaseWeapon> baseWeapons) {
    Map<BaseSpace, List<BaseSpace>> map = new HashMap<>();

    baseSpaces.forEach(baseSpace -> {
      List<BaseSpace> neighbors = getNeighborsFormMatrix(baseSpace, baseSpaces, m, n);
      map.put(baseSpace, neighbors);
    });

    List<Space> spaces = baseSpaces.stream()
        .map(base -> new Space(base, map.getOrDefault(base, new ArrayList<>()), new ArrayList<>()))
        .sorted(Comparator.comparingInt(Space::getOrder)).collect(Collectors.toList());

    baseWeapons.forEach(baseWeapon -> {
      int index = baseWeapon.getSpaceIndex();
      Weapon weapon = new Weapon(baseWeapon, baseSpaces.get(index));
      spaces.get(index).getWeapons().add(weapon);
    });

    spaces.forEach(space -> space.getWeapons().sort(Comparator.comparingInt(Weapon::getDamage)));

    this.spaces = spaces;
  }

  /**
   * Get neighbors from Matrix for base space.
   * 
   * @param baseSpace  base space
   * @param baseSpaces base spaces
   * @param m          row
   * @param n          col
   * @return the neighbors
   */
  private List<BaseSpace> getNeighborsFormMatrix(BaseSpace baseSpace, List<BaseSpace> baseSpaces,
      int m, int n) {
    List<BaseSpace> neighbors = new ArrayList<>();
    Set<Integer> set = new HashSet<>();
    int[] start = baseSpace.getStart();
    int[] end = baseSpace.getEnd();

    if (start[1] > 0) {
      for (int x = start[0]; x <= end[0]; x++) {
        if (this.matrix[x][start[1] - 1] != -1) {
          if (set.add(this.matrix[x][start[1] - 1])) {
            neighbors.add(baseSpaces.get(this.matrix[x][start[1] - 1]));
          }
        }
      }
    }

    if (end[1] < n - 1) {
      for (int x = start[0]; x <= end[0]; x++) {
        if (this.matrix[x][end[1] + 1] != -1) {
          if (set.add(this.matrix[x][end[1] + 1])) {
            neighbors.add(baseSpaces.get(this.matrix[x][end[1] + 1]));
          }
        }
      }
    }

    if (start[0] > 0) {
      for (int y = start[1]; y <= end[1]; y++) {
        if (this.matrix[start[0] - 1][y] != -1) {
          if (set.add(this.matrix[start[0] - 1][y])) {
            neighbors.add(baseSpaces.get(this.matrix[start[0] - 1][y]));
          }
        }
      }
    }

    if (end[0] < m - 1) {
      for (int y = start[1]; y <= end[1]; y++) {
        if (this.matrix[end[0] + 1][y] != -1) {
          if (set.add(this.matrix[end[0] + 1][y])) {
            neighbors.add(baseSpaces.get(this.matrix[end[0] + 1][y]));
          }
        }
      }
    }
    neighbors.sort(Comparator.comparingInt(BaseSpace::getOrder));

    return neighbors;
  }

  /**
   * Get the neighbors of the given space name. Spaces that share a "wall" are
   * neighbors.
   *
   * @param name the space name
   * @return the list of neighbors
   */
  @Override
  public List<String> getNeighbors(String name) {
    return getNeighborNamesFromSpace(getSpace(name));
  }

  /**
   * Get the neighbors of the given space index. Spaces that share a "wall" are
   * neighbors.
   *
   * @param index the space index
   * @return the list of neighbors
   */
  @Override
  public List<String> getNeighbors(Integer index) {
    return getNeighborNamesFromSpace(getSpace(index));
  }

  /**
   * Get neighbor's name list from space.
   * 
   * @param space space
   * @return neighbor's name list
   */
  private List<String> getNeighborNamesFromSpace(Space space) {
    List<String> result = new ArrayList<>();
    Optional.ofNullable(space).ifPresent(space1 -> result.addAll(
        space.getNeighbors().stream().map(BaseSpace::getName).collect(Collectors.toList())));
    return result;
  }

  /**
   * Get the space with neighbors and weapons.
   *
   * @param name space name
   * @return space
   */
  @Override
  public Space getSpace(String name) {
    return spaces.stream().filter(item -> Objects.equals(item.getName(), name)).findFirst()
        .orElse(null);
  }

  /**
   * Get the space with neighbors and weapons.
   *
   * @param index space index
   * @return space
   */
  @Override
  public Space getSpace(Integer index) {
    if (index < 0 || index >= spaces.size()) {
      return null;
    }
    return spaces.get(index);
  }

  /**
   * Get target current position.
   *
   * @return space of current position
   */
  @Override
  public Space getTargetPosition() {
    int pos = target.getPosition();
    return spaces.get(pos);
  }

  /**
   * Move the target character.
   *
   * @return the space that target is in
   */
  @Override
  public Space moveTarget() {
    int pos = target.getPosition();
    int newPos = (pos + 1) % spaces.size();
    target.setPosition(newPos);
    return spaces.get(newPos);
  }

  /**
   * Show the world in image.
   * 
   * @param directory directory of output
   * @throws IOException if write file fail
   */
  @Override
  public void showGraphicalImage(String directory) throws IOException {
    int m = matrix.length;
    int n = matrix[0].length;
    BufferedImage image = new BufferedImage(n * SCALE_FACTOR, m * SCALE_FACTOR,
        BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D graphics = image.createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, n * SCALE_FACTOR, m * SCALE_FACTOR);
    graphics.setColor(Color.BLACK);

    Font font = new Font("New Times Rome", Font.PLAIN, 10);
    spaces.forEach(space -> {
      int[] start = space.getStart();
      int[] end = space.getEnd();
      graphics.drawRect(start[1] * SCALE_FACTOR, start[0] * SCALE_FACTOR,
          (end[1] - start[1] + 1) * SCALE_FACTOR, (end[0] - start[0] + 1) * SCALE_FACTOR);
      graphics.setFont(font);
      graphics.drawString(space.getName(), start[1] * SCALE_FACTOR + 5,
          start[0] * SCALE_FACTOR + 10);
    });
    String fileName = String.format("%s%s.png", directory, this.name);
    File outputFile = new File(fileName);
    graphics.dispose();
    ImageIO.write(image, "png", outputFile);
  }
}
