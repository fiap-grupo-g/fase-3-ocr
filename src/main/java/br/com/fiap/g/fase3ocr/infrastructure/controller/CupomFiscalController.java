package br.com.fiap.g.fase3ocr.infrastructure.controller;

import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscal;
import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscalDocument;
import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscalService;
import br.com.fiap.g.fase3ocr.domain.opencv.OpenCvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import static br.com.fiap.g.fase3ocr.domain.ImageUtils.base64ToImage;

@RestController
@RequestMapping("cupom")
public class CupomFiscalController {

    private final CupomFiscalService cupomFiscalService;
    private final OpenCvService openCvService;

    @Autowired
    public CupomFiscalController(
            CupomFiscalService cupomFiscalService, OpenCvService openCvService) {
        this.cupomFiscalService = cupomFiscalService;
        this.openCvService = openCvService;
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
        BufferedImage result = openCvService.processImage(image).stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(result, "PNG", outputStream);
        outputStream.writeTo(response.getOutputStream());
    }
}