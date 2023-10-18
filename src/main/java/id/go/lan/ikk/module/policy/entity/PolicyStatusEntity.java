package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import id.go.lan.ikk.entity.PolicyStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "policy_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicyStatusEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private PolicyStatusEnum name;
}
