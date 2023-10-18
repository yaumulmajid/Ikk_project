package id.go.lan.ikk.module.user.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "koordinator_instansi_admin_instansi")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KoordinatorInstansiAdminInstansiEntity extends BaseEntity {
    private Long koordinatorInstansiId;
    private Long adminInstansiId;
}
