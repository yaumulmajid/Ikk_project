package id.go.lan.ikk.module.user.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "coordinator_types")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoordinatorTypeEntity extends BaseEntity {
    private String name;

    public CoordinatorTypeEntity(Long id, Long createdBy, Long modifiedBy, Date createdAt, Date updatedAt, String name) {
        super(id, createdBy, modifiedBy, createdAt, updatedAt);
        this.name = name;
    }
}
