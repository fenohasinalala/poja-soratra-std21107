package hei.school.soratra.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextFileInfo {
  @Id private String id;
  private String originalBucketKey;
  private String transformedBucketKey;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant creationDatetime;

  @UpdateTimestamp private Instant updateDatetime;
}
