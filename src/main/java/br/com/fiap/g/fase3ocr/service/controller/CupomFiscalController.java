package br.com.fiap.g.fase3ocr.service.controller;

import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscal;
import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscalDocument;
import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscalService;
import br.com.fiap.g.fase3ocr.domain.cv.CvImage;
import br.com.fiap.g.fase3ocr.domain.cv.CvService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cupom")
public class CupomFiscalController {

    private CupomFiscalService cupomFiscalService;
    private CvService cvService;

    @Autowired
    public CupomFiscalController(
            CupomFiscalService cupomFiscalService, CvService cvService) {
        this.cupomFiscalService = cupomFiscalService;
        this.cvService = cvService;
    }

    @CrossOrigin
    @PostMapping(value = "/read")
    public CupomFiscal read(@RequestBody CupomFiscalDocument cupomFiscalDocument) {
        return cupomFiscalService.create(cupomFiscalDocument);
    }

    @PostMapping(value = "/read-image")
    public void read(@RequestBody CupomFiscalDocument cupomFiscalDocument, HttpServletResponse response)
            throws IOException {

        BufferedImage image = base64ToImage(cupomFiscalDocument.getBase64File());
        CvImage cvImage = cvService.createCvImage(image);

        BufferedImage result = cvImage.getProcessedImage();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(result, "PNG", outputStream);
        outputStream.writeTo(response.getOutputStream());

    }

    public static BufferedImage base64ToImage(String base64File) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64File);
        ByteArrayInputStream is = new ByteArrayInputStream(imageBytes);

        BufferedImage result = ImageIO.read(is);
        is.close();

        return result;
    }
}
