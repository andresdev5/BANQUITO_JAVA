package ec.edu.monster.phoneshop.controller;

import jakarta.annotation.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ResourcesController {
    @GetMapping("/public/download/invoice")
    public ResponseEntity<ByteArrayResource> downloadInvoice(@RequestParam String invoiceUrl) throws Exception {
        String fileName = invoiceUrl.substring(invoiceUrl.lastIndexOf("/") + 1);
        File file = new File("uploads/invoices/" + fileName);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(org.springframework.http.MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
