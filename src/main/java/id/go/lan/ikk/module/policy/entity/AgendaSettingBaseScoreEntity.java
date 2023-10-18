package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "agenda_setting_base_score")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendaSettingBaseScoreEntity extends BaseEntity {
    private Double a1A = 0.0;
    private Double a1B = 0.0;
    private Double a1C = 0.0;
    private Double a1D = 0.0;
    private Double a2A = 0.0;
    private Double a2B = 0.0;
    private Double a2C = 0.0;
    private Double totalScore = 0.0;
}
