package engine.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteLoader {
    public static BufferedImage loadSprite(String path) {
        try {
            return ImageIO.read(new File("assets/" + path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage[] loadSpriteSheet(String path, int frameWidth, int frameHeight) {
        BufferedImage sheet = loadSprite(path);
        if(sheet == null) return null;

        int rows = sheet.getHeight() / frameHeight;
        int cols = sheet.getWidth() / frameWidth;
        BufferedImage[] frames = new BufferedImage[rows * cols];

        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < cols; x++) {
                frames[y * cols + x] = sheet.getSubimage(
                        x * frameWidth,
                        y * frameHeight,
                        frameWidth,
                        frameHeight
                );
            }
        }
        return frames;
    }
}
