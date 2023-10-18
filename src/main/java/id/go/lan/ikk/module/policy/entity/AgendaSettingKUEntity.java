package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "agenda_setting_ku")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendaSettingKUEntity extends BaseEntity {
    private String a1A;
    private String a1B;
    private String a1C;
    private String a1D;
    private String a2A;
    private String a2B;
    private String a2C;
}
