package id.go.lan.ikk.module.user.entity;

import id.go.lan.ikk.entity.AgencyCategoryEnum;
import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "agencies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgencyEntity extends BaseEntity {
    private String name;
    @Enumerated(EnumType.STRING)
    private AgencyCategoryEnum category;

    public AgencyEntity(Long id, Long createdBy, Long modifiedBy, Date createdAt, Date updatedAt, String name, AgencyCategoryEnum category) {
        super(id, createdBy, modifiedBy, createdAt, updatedAt);
        this.name = name;
        this.category = category;
    }
}
