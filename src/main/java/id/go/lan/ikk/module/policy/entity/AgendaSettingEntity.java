package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "agenda_setting")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendaSettingEntity extends BaseEntity {
    private String a1A;
    private String a1B;
    private String a1C;
    private String a1D;
    private String a2A;
    private String a2B;
    private String a2C;
    @Column(columnDefinition = "TEXT")
    private String informasiA3;
}
