package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "implementasi_kebijakan_ku")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImplementasiKebijakanKUEntity extends BaseEntity {
    private String c1A;
    private String c1B;
    private String c1C;
    private String c1D;
    private String c2A;
    private String c2B;
    private String c2C;
    private String c3A;
    private String c3B;
    private String c3C;
}
