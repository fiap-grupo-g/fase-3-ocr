package br.com.fiap.g.fase3ocr.domain.cv;

import br.com.fiap.g.fase3ocr.domain.cv.provider.CvProviderService;
import java.awt.image.BufferedImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CvService {

    private CvProviderService cvProviderService;

    @Autowired
    public CvService(CvProviderService cvProviderService) {
        this.cvProviderService = cvProviderService;
    }

    public CvImage createCvImage(BufferedImage image) {
        return cvProviderService.createImage(image);
    }
}
