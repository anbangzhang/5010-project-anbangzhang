package view.component;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Icon.
 * 
 * @author anbang
 * @date 2023-04-06 14:37
 */
public class Icon extends ImageIcon {

  private URL url;

  /**
   * Constructor.
   *
   * @param url url.
   */
  public Icon(URL url) {
    this.url = url;
  }

  /**
   * Toggle color for players' icons.
   *
   * @param newColor the color for player.
   * @return ImageIcon a new ImageIcon in the given color.
   */
  public ImageIcon setIconColor(Color newColor) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(this.url);
    } catch (IOException e) {
      e.printStackTrace();
    }

    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixelColor = image.getRGB(x, y);

        if (pixelColor < 0) {
          int color = newColor.getRGB();
          newImage.setRGB(x, y, color);
        }
      }
    }
    return new ImageIcon(newImage);
  }

}
