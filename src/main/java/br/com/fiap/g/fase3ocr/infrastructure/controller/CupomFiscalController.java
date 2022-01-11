package br.com.fiap.g.fase3ocr.infrastructure.controller;

import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscal;
import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscalDocument;
import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscalService;
import io.undertow.util.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("cupom")
public class CupomFiscalController {

    private final CupomFiscalService cupomFiscalService;

    @Autowired
    public CupomFiscalController(CupomFiscalService cupomFiscalService) {
        this.cupomFiscalService = cupomFiscalService;
    }

    public static BufferedImage base64ToImage(String base64File) throws IOException {
        var imageBytes = Base64.getDecoder().decode(base64File);
        var is = new ByteArrayInputStream(imageBytes);
        var result = ImageIO.read(is);

        is.close();

        return result;
    }

//    @PostMapping(value = "/read-image")
//    public void read(@RequestBody CupomFiscalDocument cupomFiscalDocument, HttpServletResponse response)
//            throws IOException {
//
//        var image = base64ToImage(cupomFiscalDocument.getBase64File());
//        var cvImage = cvService.createCvImage(image);
//
//        var result = cvImage.getProcessedImage();
//
//        var outputStream = new ByteArrayOutputStream();
//        ImageIO.write(result, "PNG", outputStream);
//        outputStream.writeTo(response.getOutputStream());
//    }

    @CrossOrigin
    @PostMapping(value = "/read")
    public CupomFiscal read(@RequestBody CupomFiscalDocument cupomFiscalDocument) throws BadRequestException {
        return cupomFiscalService.create(cupomFiscalDocument);
    }
}
