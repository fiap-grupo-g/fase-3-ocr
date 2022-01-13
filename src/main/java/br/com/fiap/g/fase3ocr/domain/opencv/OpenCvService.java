package br.com.fiap.g.fase3ocr.domain.opencv;

import org.opencv.core.Mat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.fiap.g.fase3ocr.domain.opencv.OpenCvUtils.*;

@Service
public class OpenCvService {

    public OpenCvService() {
        nu.pattern.OpenCV.loadLocally();
    }

    public List<BufferedImage> processImage(BufferedImage bufferedImage) {
        Mat image = OpenCvUtils.bufferedImage2Mat(bufferedImage);
        Mat greyImage = toGrey(image);

        CompletableFuture<Mat> bilateral = withBilateralFilter(greyImage);
        CompletableFuture<Mat> skewFilter = withSkewFilter(greyImage);
        CompletableFuture<Mat> GaussianAndSkewFilter = withGaussianAndSkewFilter(greyImage);

        return Stream.of(bilateral, skewFilter, GaussianAndSkewFilter)
                .map(CompletableFuture::join)
                .map(OpenCvUtils::mat2BufferedImage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Async
    private CompletableFuture<Mat> withBilateralFilter(final Mat greyImage) {
        return CompletableFuture.supplyAsync(() -> bilateralFilter(greyImage));
    }

    @Async
    private CompletableFuture<Mat> withSkewFilter(final Mat greyImage) {
        return CompletableFuture.supplyAsync(() -> {
            Mat processedSkewImage = reSize(greyImage);
            deSkew(processedSkewImage);
            return bilateralFilter(processedSkewImage);
        });
    }

    @Async
    private CompletableFuture<Mat> withGaussianAndSkewFilter(final Mat greyImage) {
        return CompletableFuture.supplyAsync(() -> {
            Mat processedGaussianSkewImage = reSize(greyImage);
            deSkew(processedGaussianSkewImage);
            processedGaussianSkewImage = gaussianAdaptiveThresholding(processedGaussianSkewImage);
            return morphologicalOpening(processedGaussianSkewImage);
        });
    }
}