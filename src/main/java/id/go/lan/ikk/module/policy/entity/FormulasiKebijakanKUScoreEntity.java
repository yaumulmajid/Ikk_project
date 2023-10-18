package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "formulasi_kebijakan_ku_score")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormulasiKebijakanKUScoreEntity extends BaseEntity {
    private Double b1A = 0.0;
    private Double b1B = 0.0;
    private Double b2A = 0.0;
    private Double b2B = 0.0;
    private Double b3A = 0.0;
    private Double b3B = 0.0;
    private Double b3C = 0.0;
    private Double b4A = 0.0;
    private Double b4B = 0.0;
    private Double b4C = 0.0;
    private Double b5A = 0.0;
    private Double b5B = 0.0;
    private Double b5C = 0.0;
    private Double totalScore = 0.0;
}
