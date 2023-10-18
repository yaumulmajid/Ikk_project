package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.PolicySampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicySampleRepository extends JpaRepository<PolicySampleEntity, Long> {

    @Query("SELECT new id.go.lan.ikk.module.policy.entity.PolicySampleEntity(" +
            "s.id, s.createdBy, s.modifiedBy, s.createdAt, s.updatedAt, " +
            "s.idUserAdmin, s.policyId" +
            ") " +
            "FROM PolicySampleEntity s " +
            "INNER JOIN PolicyEntity p " +
            "ON s.policyId = p.id " +
            "LEFT JOIN p.policyDetailsEntity d " +
            "WHERE d.effectiveDate BETWEEN " +
            "(SELECT y.startYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "AND " +
            "(SELECT y.endYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "")
    List<PolicySampleEntity> findAll();

    @Query("SELECT new id.go.lan.ikk.module.policy.entity.PolicySampleEntity(" +
            "s.id, s.createdBy, s.modifiedBy, s.createdAt, s.updatedAt, " +
            "s.idUserAdmin, s.policyId" +
            ") " +
            "FROM PolicySampleEntity s " +
            "INNER JOIN PolicyEntity p " +
            "ON s.policyId = p.id " +
            "LEFT JOIN p.policyDetailsEntity d " +
            "WHERE s.idUserAdmin = :id " +
            "AND d.effectiveDate BETWEEN " +
            "(SELECT y.startYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "AND " +
            "(SELECT y.endYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "")
    List<PolicySampleEntity> findByIdUserAdmin(Long id);

    PolicySampleEntity findByPolicyId(Long policyId);
}
