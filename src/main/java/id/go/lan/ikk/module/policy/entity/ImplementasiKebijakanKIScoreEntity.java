package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "implementasi_kebijakan_ki_score")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImplementasiKebijakanKIScoreEntity extends BaseEntity {
    private Double c1A = 0.0;
    private Double c1B = 0.0;
    private Double c1C = 0.0;
    private Double c1D = 0.0;
    private Double c2A = 0.0;
    private Double c2B = 0.0;
    private Double c2C = 0.0;
    private Double c3A = 0.0;
    private Double c3B = 0.0;
    private Double c3C = 0.0;
    private Double totalScore = 0.0;
}
