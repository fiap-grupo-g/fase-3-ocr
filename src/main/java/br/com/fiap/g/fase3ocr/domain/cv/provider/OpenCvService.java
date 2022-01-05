package br.com.fiap.g.fase3ocr.domain.cv.provider;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

@Component
public class OpenCvService implements CvProviderService<OpenCvImage> {

    private static final int MAX_RGB_COLOR_VALUE = 255;

    private static final int BILATERAL_FILTER_PIXEL_RANGE = 15;
    private static final int BILATERAL_FILTER_SIGMA_COLOR = 55;
    private static final int BILATERAL_FILTER_SIGMA_SPACE = 45;

    private static final int GAUSSIAN_ADAPTIVE_THRESHOLDING_PIXEL_RANGE = 11;
    private static final int GAUSSIAN_ADAPTIVE_THRESHOLDING_CONSTANT = 9;

    private static final int MORPHOLOGICAL_KERNEL_SIZE = 2;

    public OpenCvService() {
        nu.pattern.OpenCV.loadLocally();
    }

    @Override
    public OpenCvImage createImage(BufferedImage image) {
        return new OpenCvImage(this, image);
    }

    public Mat toGrey(Mat image) {
        Mat result = getNewMat(image);
        Imgproc.cvtColor(image, result, Imgproc.COLOR_RGB2GRAY, 0);
        return result;
    }

    public Mat bilateralFilter(Mat image) {
        Mat result = getNewMat(image);
        Imgproc.bilateralFilter(image, result,
                BILATERAL_FILTER_PIXEL_RANGE,
                BILATERAL_FILTER_SIGMA_COLOR,
                BILATERAL_FILTER_SIGMA_SPACE);
        return result;
    }

    public Mat gaussianAdaptiveThresholding(Mat image) {
        Mat result = getNewMat(image);
        Imgproc.adaptiveThreshold(image, result,
                MAX_RGB_COLOR_VALUE,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                THRESH_BINARY,
                GAUSSIAN_ADAPTIVE_THRESHOLDING_PIXEL_RANGE,
                GAUSSIAN_ADAPTIVE_THRESHOLDING_CONSTANT);
        return result;
    }

    public Mat morphologicalOpening(Mat image) {
        Mat result = getNewMat(image);
        Mat kernel = Mat.ones(MORPHOLOGICAL_KERNEL_SIZE, MORPHOLOGICAL_KERNEL_SIZE, CvType.CV_32F);
        Imgproc.morphologyEx(image, result, Imgproc.MORPH_OPEN, kernel);
        return result;
    }

    public Mat deSkew(Mat image) {
        Mat img = getNewMat(image);

        //Binarization
        Imgproc.adaptiveThreshold(image, img, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 40);

        //Inverting colors
        Core.bitwise_not( img, img );
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));

        //Erosion
        Imgproc.erode(img, img, element);

        //Finding all white pixels
        Mat wLocMat = Mat.zeros(img.size(),img.type());
        Core.findNonZero(img, wLocMat);

        MatOfPoint matOfPoint = new MatOfPoint( wLocMat );
        MatOfPoint2f mat2f = new MatOfPoint2f();
        matOfPoint.convertTo(mat2f, CvType.CV_32FC2);

        //Getting rotated rect of white pixels
        RotatedRect rotatedRect = Imgproc.minAreaRect( mat2f );

        Point[] vertices = new Point[4];
        rotatedRect.points(vertices);
        List<MatOfPoint> boxContours = new ArrayList<>();
        boxContours.add(new MatOfPoint(vertices));
        Imgproc.drawContours( img, boxContours, 0, new Scalar(128, 128, 128), -1);

        // final rotation angle
        rotatedRect.angle = rotatedRect.angle < -45 ? rotatedRect.angle + 90.f : rotatedRect.angle;

        //de-skewing
        Point center = new Point(image.width()/2, image.height()/2);
        Mat rotImage = Imgproc.getRotationMatrix2D(center, rotatedRect.angle, 1.0);
        Size size = new Size(image.width(), image.height());
        Imgproc.warpAffine(image, image, rotImage, size, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_FILL_OUTLIERS);

        return image;
    }

    public Mat reSize(Mat image) {
        Mat result = getNewMat(image);
        Size size = new Size(result.width() * 1.25, result.height() * 1.25);
        Imgproc.resize(image, result, size, 0, 0, Imgproc.INTER_LANCZOS4);
        return result;
    }

    private Mat getNewMat(Mat image) {
        return new Mat(image.rows(), image.cols(), image.type());
    }
}
