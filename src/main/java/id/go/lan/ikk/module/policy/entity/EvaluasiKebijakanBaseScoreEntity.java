package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "evaluasi_kebijakan_base_score")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluasiKebijakanBaseScoreEntity extends BaseEntity {
    private Double d1A = 0.0;
    private Double d1B = 0.0;
    private Double d2A = 0.0;
    private Double d2B = 0.0;
    private Double d3A = 0.0;
    private Double d3B = 0.0;
    private Double d3C = 0.0;
    private Double d3D = 0.0;
    private Double d3E = 0.0;
    private Double totalScore = 0.0;
}
