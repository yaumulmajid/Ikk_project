package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "active_year")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActiveYearEntity extends BaseEntity {
    private Date startYear;
    private Date endYear;
}
