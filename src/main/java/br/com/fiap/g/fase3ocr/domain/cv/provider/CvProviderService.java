package br.com.fiap.g.fase3ocr.domain.cv.provider;

import br.com.fiap.g.fase3ocr.domain.cv.CvImage;
import java.awt.image.BufferedImage;

public interface CvProviderService<T extends CvImage> {

    T createImage(BufferedImage image);
}
