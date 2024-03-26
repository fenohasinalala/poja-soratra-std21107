package hei.school.soratra.service;

import hei.school.soratra.repository.TextFileInfoRepository;
import hei.school.soratra.repository.model.TextFileInfo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TextFileInfoService {
  private final TextFileInfoRepository repository;

  @Transactional
  public TextFileInfo save(TextFileInfo textFileInfo) {
    return repository.save(textFileInfo);
  }

  public TextFileInfo getTextFileInfoById(String id) {
    return repository.findById(id).orElse(null);
  }
}
