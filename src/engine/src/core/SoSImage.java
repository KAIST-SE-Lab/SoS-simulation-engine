package core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Project: NewSimulator
 * Created by IntelliJ IDEA
 * Author: Sumin Park <smpark@se.kaist.ac.kr>
 * Github: https://github.com/sumin0407/NewSimulator.git
 */

public class SoSImage {

    private SoSImage() {

    }

    private static HashMap<String, SoSImage> strToImage = new HashMap<>();
    public static SoSImage create(String filePath) {
        SoSImage image = null;

        if(strToImage.containsKey(filePath)) {
            image = strToImage.get(filePath);
        } else {
            image = new SoSImage();
            image.setFilePath(filePath);
            try {
                image.setImage(ImageIO.read(new File(filePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            strToImage.put(filePath, image);
        }

        if(image != null) {
            image.refCount++;
        }

        return image;
    }

    public void clear() {
        refCount--;
        if(refCount <= 0) {
            strToImage.remove(this);
        }
    }

    // << Field: image >>
    private BufferedImage image;
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public BufferedImage getImage() {
        return image;
    }
    // << Field: image >>


    // << Field: filePath >>
    // 이미지 파일 경로
    private String filePath;
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
    // << Field: filePath >>


    // << Field: refCount >>
    // 이 이미지를 참조하고 있는 객체 수
    private int refCount = 0;
    public int getRefCount() {
        return refCount;
    }
    // << Field: refCount >>


    // << Method: clear >>
    // 정리 코드 작성
    //public void clear() { }
    // << Method: clear >>
}
