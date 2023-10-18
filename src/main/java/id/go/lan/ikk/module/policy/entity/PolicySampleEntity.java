package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "policy_sample")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicySampleEntity extends BaseEntity {
    private Long idUserAdmin;
    private Long policyId;

    public PolicySampleEntity(Long id, Long createdBy, Long modifiedBy, Date createdAt, Date updatedAt, Long idUserAdmin, Long policyId) {
        super(id, createdBy, modifiedBy, createdAt, updatedAt);
        this.idUserAdmin = idUserAdmin;
        this.policyId = policyId;
    }
}
