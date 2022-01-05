package br.com.fiap.g.fase3ocr.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

public class ImageUtils {

    public static BufferedImage base64ToImage(String base64File) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64File);
        ByteArrayInputStream is = new ByteArrayInputStream(imageBytes);

        BufferedImage result = ImageIO.read(is);
        is.close();

        return result;
    }

}
