package id.go.lan.ikk.module.user.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin_instansi_enumerator")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminInstansiEnumeratorEntity extends BaseEntity {
    private Long adminInstansiId;
    private Long enumeratorId;
}
