package id.go.lan.ikk.module.policy.repository;

import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {

    @Query("SELECT new id.go.lan.ikk.module.policy.entity.PolicyEntity(" +
            "p.id, p.createdBy, p.modifiedBy, p.createdAt, p.updatedAt, " +
            "p.name, p.agency, p.enumeratorId, " +
            "p.sentByAdminAt, p.sentByAdminId, p.verifiedByKoordinatorAt, p.verifiedByKoordinatorId, " +
            "p.assignedByAdminAt, p.assignedByAdminId, p.processedByEnumeratorAt, p.processedByEnumeratorId, " +
            "p.validatedByKoordinatorAt, p.validatedByKoordinatorId, p.validatedByKoordinatorUtamaAt, p.validatedByKoordinatorUtamaId, " +
            "p.policyDetailsEntity, p.policyProcessEntity, p.policyStatusEntity, " +
            "p.agendaSettingEntity, p.agendaSettingKIEntity, p.agendaSettingKUEntity, p.agendaSettingFileEntity, p.agendaSettingBaseScoreEntity, p.agendaSettingKIScoreEntity, p.agendaSettingKUScoreEntity, " +
            "p.formulasiKebijakanEntity, p.formulasiKebijakanKIEntity, p.formulasiKebijakanKUEntity, p.formulasiKebijakanFileEntity, p.formulasiKebijakanBaseScoreEntity, p.formulasiKebijakanKIScoreEntity, p.formulasiKebijakanKUScoreEntity, " +
            "p.implementasiKebijakanEntity, p.implementasiKebijakanKIEntity, p.implementasiKebijakanKUEntity, p.implementasiKebijakanFileEntity, p.implementasiKebijakanBaseScoreEntity, p.implementasiKebijakanKIScoreEntity, p.implementasiKebijakanKUScoreEntity, " +
            "p.evaluasiKebijakanEntity, p.evaluasiKebijakanKIEntity, p.evaluasiKebijakanKUEntity, p.evaluasiKebijakanFileEntity, p.evaluasiKebijakanBaseScoreEntity, p.evaluasiKebijakanKIScoreEntity, p.evaluasiKebijakanKUScoreEntity " +
            ") " +
            "FROM PolicyEntity p " +
            "LEFT JOIN p.policyDetailsEntity d " +
            "WHERE p.agency = :agencyEntity " +
            "AND d.effectiveDate BETWEEN " +
            "(SELECT y.startYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "AND " +
            "(SELECT y.endYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "")
    List<PolicyEntity> findByAgency(AgencyEntity agencyEntity);

    @Query("SELECT COUNT(p.id) " +
            "FROM PolicyEntity p " +
            "LEFT JOIN p.policyDetailsEntity d " +
            "WHERE p.agency = :agency " +
            "AND d.effectiveDate BETWEEN " +
            "(SELECT y.startYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "AND " +
            "(SELECT y.endYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "")
    Integer countByAgency(AgencyEntity agency);

    @Query("SELECT new id.go.lan.ikk.module.policy.entity.PolicyEntity(" +
            "p.id, p.createdBy, p.modifiedBy, p.createdAt, p.updatedAt, " +
            "p.name, p.agency, p.enumeratorId, " +
            "p.sentByAdminAt, p.sentByAdminId, p.verifiedByKoordinatorAt, p.verifiedByKoordinatorId, " +
            "p.assignedByAdminAt, p.assignedByAdminId, p.processedByEnumeratorAt, p.processedByEnumeratorId, " +
            "p.validatedByKoordinatorAt, p.validatedByKoordinatorId, p.validatedByKoordinatorUtamaAt, p.validatedByKoordinatorUtamaId, " +
            "p.policyDetailsEntity, p.policyProcessEntity, p.policyStatusEntity, " +
            "p.agendaSettingEntity, p.agendaSettingKIEntity, p.agendaSettingKUEntity, p.agendaSettingFileEntity, p.agendaSettingBaseScoreEntity, p.agendaSettingKIScoreEntity, p.agendaSettingKUScoreEntity, " +
            "p.formulasiKebijakanEntity, p.formulasiKebijakanKIEntity, p.formulasiKebijakanKUEntity, p.formulasiKebijakanFileEntity, p.formulasiKebijakanBaseScoreEntity, p.formulasiKebijakanKIScoreEntity, p.formulasiKebijakanKUScoreEntity, " +
            "p.implementasiKebijakanEntity, p.implementasiKebijakanKIEntity, p.implementasiKebijakanKUEntity, p.implementasiKebijakanFileEntity, p.implementasiKebijakanBaseScoreEntity, p.implementasiKebijakanKIScoreEntity, p.implementasiKebijakanKUScoreEntity, " +
            "p.evaluasiKebijakanEntity, p.evaluasiKebijakanKIEntity, p.evaluasiKebijakanKUEntity, p.evaluasiKebijakanFileEntity, p.evaluasiKebijakanBaseScoreEntity, p.evaluasiKebijakanKIScoreEntity, p.evaluasiKebijakanKUScoreEntity " +
            ") " +
            "FROM PolicyEntity p " +
            "LEFT JOIN p.policyDetailsEntity d " +
            "WHERE p.enumeratorId = :id " +
            "AND d.effectiveDate BETWEEN " +
            "(SELECT y.startYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "AND " +
            "(SELECT y.endYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "")
    List<PolicyEntity> findByEnumeratorId(Long id);

    @Query("SELECT new id.go.lan.ikk.module.user.entity.AgencyEntity(" +
            "a.id, a.createdBy, a.modifiedBy, a.createdAt, a.updatedAt, " +
            "a.name, a.category " +
            ") " +
            "FROM PolicyEntity p " +
            "INNER JOIN p.agency a " +
            "INNER JOIN p.policyDetailsEntity d " +
            "WHERE d.effectiveDate BETWEEN " +
            "(SELECT y.startYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "AND " +
            "(SELECT y.endYear FROM ActiveYearEntity y WHERE y.id = 1) " +
            "GROUP BY p.agency " +
            "")
    List<AgencyEntity> getAgenciesWithPolicies();
}
