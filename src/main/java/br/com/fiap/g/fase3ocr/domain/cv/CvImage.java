package br.com.fiap.g.fase3ocr.domain.cv;

import java.awt.image.BufferedImage;

public interface CvImage {

    Boolean hasNext();

    BufferedImage getProcessedImage();
}
