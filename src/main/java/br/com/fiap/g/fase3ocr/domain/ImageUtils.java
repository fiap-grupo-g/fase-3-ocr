package br.com.fiap.g.fase3ocr.domain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {

    public static BufferedImage base64ToImage(String base64File) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64File);
            ByteArrayInputStream is = new ByteArrayInputStream(imageBytes);
            BufferedImage result = ImageIO.read(is);
            is.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String imageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", os);

            byte[] imageBytes = os.toByteArray();
            os.close();

            byte[] result = Base64.getEncoder().encode(imageBytes);
            return new String(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
