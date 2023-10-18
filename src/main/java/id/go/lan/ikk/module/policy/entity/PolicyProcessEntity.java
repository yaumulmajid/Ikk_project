package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import id.go.lan.ikk.entity.PolicyProcessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "policy_process")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicyProcessEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private PolicyProcessEnum name;
}
