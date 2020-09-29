package life.yjgbg.jpa.plus.entitySupport;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class StdEntity<Self extends StdEntity<Self>>
    implements ActiveEntity<Self>,Serializable {
  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private boolean deleted; // 逻辑删除字段

  public void removeLogically() {
    this.deleted = true;
    save();
  }

  public Self undoRemoveLogically() {
    this.deleted = false;
    return save();
  }
}
