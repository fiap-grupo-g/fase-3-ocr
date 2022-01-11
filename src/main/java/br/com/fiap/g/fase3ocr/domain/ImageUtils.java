package br.com.fiap.g.fase3ocr.domain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {

    public static BufferedImage base64ToImage(String base64File) {
        try {
            var imageBytes = Base64.getDecoder().decode(base64File);
            var is = new ByteArrayInputStream(imageBytes);
            var result = ImageIO.read(is);
            is.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
