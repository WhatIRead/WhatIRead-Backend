package com.mvp1.whatiread.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serial;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
@Data
@JsonIgnoreProperties(
    value = {"createdBY", "updatedBy"},
    allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {

  @Serial
  private static final long serialVersionUID = 1L;

  @CreatedBy
  @Column(updatable = false)
  private Long createdBy;

  @LastModifiedBy
  private Long updatedBy;
}
