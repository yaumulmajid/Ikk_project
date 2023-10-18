package id.go.lan.ikk.module.user.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "koordinator_utama_koordinator_instansi")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KoordinatorUtamaKoordinatorInstansiEntity extends BaseEntity {
    private Long koordinatorUtamaId;
    private Long koordinatorInstansiId;
}
