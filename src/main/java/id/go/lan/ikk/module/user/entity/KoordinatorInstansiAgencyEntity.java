package id.go.lan.ikk.module.user.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "koordinator_instansi_agency")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KoordinatorInstansiAgencyEntity extends BaseEntity {
    private Long koordinatorInstansiId;
    private Long agencyId;
}
