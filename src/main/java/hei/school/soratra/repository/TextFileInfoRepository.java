package hei.school.soratra.repository;

import hei.school.soratra.repository.model.TextFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextFileInfoRepository extends JpaRepository<TextFileInfo, String> {}
