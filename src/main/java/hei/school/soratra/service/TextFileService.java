package hei.school.soratra.service;

import hei.school.soratra.file.BucketComponent;
import hei.school.soratra.model.RestText;
import hei.school.soratra.repository.model.TextFileInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TextFileService {

  BucketComponent bucketComponent;
  TextFileInfoService textFileInfoService;

  public File uploadLowercaseTextFile(String id, byte[] file) {
    try {
      if (file == null) {
        throw new RuntimeException("Text file is mandatory");
      }
      String fileSuffix = ".txt";
      String inputFilePrefix = id + fileSuffix;
      String outputFilePrefix = id + "-transformed" + fileSuffix;
      File originalTmpFile;
      File outputTmpFile;
      try {
        originalTmpFile = File.createTempFile(inputFilePrefix, fileSuffix);
        outputTmpFile = File.createTempFile(outputFilePrefix, fileSuffix);
      } catch (IOException e) {
        throw new RuntimeException("Creation of temp file failed");
      }
      writeFileFromByteArray(file, originalTmpFile);

      File transformedTextFile = convertToUppercaseTextFile(originalTmpFile, outputTmpFile);
      String originalBucketKey = getTextBucketName(inputFilePrefix);
      String transformedBucketKey = getTextBucketName(outputFilePrefix);
      uploadTextFile(originalTmpFile, originalBucketKey);
      uploadTextFile(transformedTextFile, transformedBucketKey);
      TextFileInfo toSave =
          new TextFileInfo(id, originalBucketKey, transformedBucketKey, null, null);
      textFileInfoService.save(toSave);
      return bucketComponent.download(transformedBucketKey);
    } catch (Exception e) {
      return null;
    }
  }

  private File convertToUppercaseTextFile(File originalFile, File outputFile) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(originalFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    String currentLine;
    while ((currentLine = reader.readLine()) != null) {
      if (!currentLine.equals(currentLine.toLowerCase())) {
        throw new RuntimeException("text provide not in lowercase");
      }
      writer.write(currentLine.toUpperCase());
      writer.newLine();
    }
    reader.close();
    writer.close();
    return outputFile;
  }

  private void uploadTextFile(File file, String bucketKey) {
    bucketComponent.upload(file, bucketKey);
    file.delete();
  }

  private String getTextBucketName(String filename) {
    return "text/" + filename;
  }

  public RestText getRestTextFile(TextFileInfo picture) {
    try {
      RestText restPicture = new RestText();
      restPicture.setOriginal_url(
          bucketComponent.presign(picture.getOriginalBucketKey(), Duration.ofHours(12)).toString());
      restPicture.setTransformed_url(
          bucketComponent
              .presign(picture.getTransformedBucketKey(), Duration.ofHours(12))
              .toString());
      return restPicture;
    } catch (Exception e) {
      return null;
    }
  }

  private File writeFileFromByteArray(byte[] bytes, File file) {
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(bytes);
      return file;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
