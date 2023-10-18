package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "evaluasi_kebijakan_ku")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluasiKebijakanKUEntity extends BaseEntity {
    private String d1A;
    private String d1B;
    private String d2A;
    private String d2B;
    private String d3A;
    private String d3B;
    private String d3C;
    private String d3D;
    private String d3E;
}
