package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "formulasi_kebijakan_ki")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormulasiKebijakanKIEntity extends BaseEntity {
    private String b1A;
    private String b1B;
    private String b2A;
    private String b2B;
    private String b3A;
    private String b3B;
    private String b3C;
    private String b4A;
    private String b4B;
    private String b4C;
    private String b5A;
    private String b5B;
    private String b5C;
}
