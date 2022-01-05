package br.com.fiap.g.fase3ocr.domain.cv.provider;

import br.com.fiap.g.fase3ocr.domain.cv.CvImage;
import br.com.fiap.g.fase3ocr.domain.cv.CvUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;
import org.opencv.core.Mat;

public class OpenCvImage implements CvImage {

    private final OpenCvService openCvService;
    private final Mat image;
    private Mat greyImage;
    private Mat processedImageA;
    private Mat processedImageB;
    private Mat processedImageC;

    public OpenCvImage(OpenCvService openCvService, BufferedImage image) {
        this.openCvService = openCvService;
        this.image = bufferedImageFile2Mat(image);
    }

    public Boolean hasNext() {
        return Stream.of(processedImageA, processedImageB, processedImageC).anyMatch(Objects::isNull);
    }

    public BufferedImage getProcessedImage() {
        if (greyImage == null) greyImage = openCvService.toGrey(image);

        if (processedImageA == null) processImageA();
        else if (processedImageB == null) processImageB();
        else if (processedImageC == null) processImageC();

        Mat result = processedImageC == null ? processedImageB == null ?
                processedImageA : processedImageB : processedImageC;

        return mat2BufferedImageFile(result);
    }

    public void processImageA() {
        processedImageA = openCvService.bilateralFilter(greyImage);
    }

    public void processImageB() {
        processedImageB = openCvService.reSize(greyImage);
        processedImageB = openCvService.deSkew(processedImageB);
        processedImageB = openCvService.bilateralFilter(processedImageB);
    }

    public void processImageC() {
        processedImageC = openCvService.reSize(greyImage);
        processedImageC = openCvService.deSkew(processedImageC);
        processedImageC = openCvService.gaussianAdaptiveThresholding(processedImageC);
        processedImageC = openCvService.morphologicalOpening(processedImageC);
    }

    private BufferedImage mat2BufferedImageFile(Mat mat) {
        try {
            return CvUtil.mat2BufferedImage(mat);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // add exception
        }
    }

    private Mat bufferedImageFile2Mat(BufferedImage bufferedImage) {
        try {
            return CvUtil.bufferedImage2Mat(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // add exception
        }
    }
}
