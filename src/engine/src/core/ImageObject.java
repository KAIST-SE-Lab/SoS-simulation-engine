package core;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Project: NewSimulator
 * Created by IntelliJ IDEA
 * Author: Sumin Park <smpark@se.kaist.ac.kr>
 * Github: https://github.com/sumin0407/NewSimulator.git
 */

public class ImageObject extends SoSObject {

    SoSImage image;
    float scale = 1;
    public ImageObject(String filePath) {
        setImage(filePath);
    }

    public ImageObject(String filePath, float scale) {
        setImage(filePath);
        this.scale = scale;
    }

    public void setImage(String filePath) {
        clear();
        image = SoSImage.create(filePath);
    }

    @Override
    // Change the scale of the image
    protected void onRender(Graphics2D g) {
        BufferedImage bufImage = image.getImage();
        g.translate((int)(-scale / 2) * Map.tileSize.width, (int)(-scale / 2) * Map.tileSize.height);
        g.scale(scale, scale);
        g.drawImage(bufImage, 0, 0, Map.tileSize.width, Map.tileSize.height, null);
    }

    @Override
    public void clear() {
        if(image != null) {
            image.clear();
            image = null;
        }
        scale = 1;
    }

}
