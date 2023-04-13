package model.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import model.base.BasePet;
import model.base.BaseSpace;
import model.base.BaseWeapon;
import model.model.Target;

/**
 * ContextBuilder.
 * 
 * @author anbang
 * @date 2023-03-15 22:18
 */
public class ContextBuilder {

  /**
   * Builder context.
   * 
   * @param in in
   * @return context
   * @throws IllegalArgumentException invalid input
   */
  public static Context build(Readable in) throws IllegalArgumentException {
    if (Objects.isNull(in)) {
      throw new IllegalArgumentException("Invalid input source.");
    }
    Context context = new Context();
    Scanner scan = new Scanner(in);
    /* World info */
    int m = scan.nextInt();
    context.setM(m);
    int n = scan.nextInt();
    context.setN(n);
    context.setWorldName(scan.nextLine().trim());

    /* Target info */
    int health = scan.nextInt();
    String targetName = scan.nextLine().trim();
    context.setTarget(new Target(health, targetName));

    /* Pet info */
    String petName = scan.nextLine().trim();
    context.setPet(new BasePet(petName));

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
    context.setSpaces(baseSpaces);

    /* Weapons info */
    int sizeOfWeapon = scan.nextInt();
    List<BaseWeapon> baseWeapons = new ArrayList<>(sizeOfWeapon);
    for (int i = 0; i < sizeOfWeapon; i++) {
      int index = scan.nextInt();
      int damage = scan.nextInt();
      String weaponName = scan.nextLine().trim();
      BaseWeapon weapon = new BaseWeapon(index, damage, weaponName);
      weapon.setHolder(String.format("space: %s", baseSpaces.get(index).getName()));
      baseWeapons.add(weapon);
    }
    baseWeapons
        .sort((a, b) -> a.getSpaceIndex() == b.getSpaceIndex() ? b.getDamage() - a.getDamage()
            : a.getSpaceIndex() - b.getSpaceIndex());
    context.setWeapons(baseWeapons);

    int[][] matrix = initMatrixAndValidateSpaces(m, n, baseSpaces);
    initNeighborMapAndSpaces(m, n, matrix, baseSpaces, context);

    context.setPlayers(new ArrayList<>());
    context.setExposedSpaces(new HashSet<>());
    context.setEvidences(new ArrayList<>());

    return context;
  }

  /**
   * Init the matrix and validate the input base spaces.
   *
   * @param m          row
   * @param n          col
   * @param baseSpaces base spaces
   * @return matrix
   * @throws IllegalArgumentException any param is invalid
   */
  private static int[][] initMatrixAndValidateSpaces(int m, int n, List<BaseSpace> baseSpaces)
      throws IllegalArgumentException {
    if (m <= 0 || n <= 0) {
      throw new IllegalArgumentException("World's width and length is smaller than 0.");
    }

    int[][] matrix = new int[m][n];
    for (int i = 0; i < m; i++) {
      Arrays.fill(matrix[i], -1);
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
    return matrix;
  }

  /**
   * Calculate neighbor map based on the matrix.
   *
   * @param m          row
   * @param n          col
   * @param matrix     matrix
   * @param baseSpaces base spaces
   * @param context    context
   */
  private static void initNeighborMapAndSpaces(int m, int n, int[][] matrix,
      List<BaseSpace> baseSpaces, Context context) {
    Map<BaseSpace, List<BaseSpace>> map = new HashMap<>();

    baseSpaces.forEach(baseSpace -> {
      List<BaseSpace> neighbors = getNeighborsFormMatrix(baseSpace, baseSpaces, m, n, matrix);
      map.put(baseSpace, neighbors);
    });

    context.setNeighborMap(map);
  }

  /**
   * Get neighbors from Matrix for base space.
   *
   * @param baseSpace  base space
   * @param baseSpaces base spaces
   * @param m          row
   * @param n          col
   * @param matrix     matrix
   * @return the neighbors
   */
  private static List<BaseSpace> getNeighborsFormMatrix(BaseSpace baseSpace,
      List<BaseSpace> baseSpaces, int m, int n, int[][] matrix) {
    List<BaseSpace> neighbors = new ArrayList<>();
    Set<Integer> set = new HashSet<>();
    int[] start = baseSpace.getStart();
    int[] end = baseSpace.getEnd();

    if (start[1] > 0) {
      for (int x = start[0]; x <= end[0]; x++) {
        if (matrix[x][start[1] - 1] != -1) {
          if (set.add(matrix[x][start[1] - 1])) {
            neighbors.add(baseSpaces.get(matrix[x][start[1] - 1]));
          }
        }
      }
    }

    if (end[1] < n - 1) {
      for (int x = start[0]; x <= end[0]; x++) {
        if (matrix[x][end[1] + 1] != -1) {
          if (set.add(matrix[x][end[1] + 1])) {
            neighbors.add(baseSpaces.get(matrix[x][end[1] + 1]));
          }
        }
      }
    }

    if (start[0] > 0) {
      for (int y = start[1]; y <= end[1]; y++) {
        if (matrix[start[0] - 1][y] != -1) {
          if (set.add(matrix[start[0] - 1][y])) {
            neighbors.add(baseSpaces.get(matrix[start[0] - 1][y]));
          }
        }
      }
    }

    if (end[0] < m - 1) {
      for (int y = start[1]; y <= end[1]; y++) {
        if (matrix[end[0] + 1][y] != -1) {
          if (set.add(matrix[end[0] + 1][y])) {
            neighbors.add(baseSpaces.get(matrix[end[0] + 1][y]));
          }
        }
      }
    }
    neighbors.sort(Comparator.comparingInt(BaseSpace::getOrder));

    return neighbors;
  }
}
