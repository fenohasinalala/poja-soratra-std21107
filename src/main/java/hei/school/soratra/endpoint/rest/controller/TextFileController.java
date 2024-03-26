package hei.school.soratra.endpoint.rest.controller;

import hei.school.soratra.model.RestText;
import hei.school.soratra.repository.model.TextFileInfo;
import hei.school.soratra.service.TextFileInfoService;
import hei.school.soratra.service.TextFileService;
import java.io.File;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TextFileController {

  TextFileService service;
  TextFileInfoService textService;

  @PutMapping(value = "/soratra/{id}")
  public ResponseEntity<String> uploadLowercaseTextFile(
      @PathVariable(name = "id") String id, @RequestBody(required = false) byte[] file) {

    File output = service.uploadLowercaseTextFile(id, file);
    if (output == null) {
      return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("");
    }
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("OK");
  }

  @GetMapping(value = "/soratra/{id}")
  public RestText getPictureById(@PathVariable(name = "id") String id) {
    TextFileInfo picture = textService.getTextFileInfoById(id);
    RestText output = service.getRestTextFile(picture);
    if (output == null) {
      RestText restText = new RestText();
      restText.setOriginal_url("https://original.url");
      restText.setTransformed_url("https://transformed.url");
      return restText;
    }
    return output;
  }
}
