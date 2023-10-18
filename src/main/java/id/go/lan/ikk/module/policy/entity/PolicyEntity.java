package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "policies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicyEntity extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agency_id", referencedColumnName = "id")
    private AgencyEntity agency;

    private Long enumeratorId;

    private Date sentByAdminAt;
    private Long sentByAdminId;
    private Date verifiedByKoordinatorAt;
    private Long verifiedByKoordinatorId;
    private Date assignedByAdminAt;
    private Long assignedByAdminId;
    private Date processedByEnumeratorAt;
    private Long processedByEnumeratorId;
    private Date validatedByKoordinatorAt;
    private Long validatedByKoordinatorId;
    private Date validatedByKoordinatorUtamaAt;
    private Long validatedByKoordinatorUtamaId;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_details_id", referencedColumnName = "id")
    private PolicyDetailsEntity policyDetailsEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_process_id", referencedColumnName = "id")
    private PolicyProcessEntity policyProcessEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_status_id", referencedColumnName = "id")
    private PolicyStatusEntity policyStatusEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_setting_id", referencedColumnName = "id")
    private AgendaSettingEntity agendaSettingEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_setting_ki_id", referencedColumnName = "id")
    private AgendaSettingKIEntity agendaSettingKIEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_setting_ku_id", referencedColumnName = "id")
    private AgendaSettingKUEntity agendaSettingKUEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_setting_file_id", referencedColumnName = "id")
    private AgendaSettingFileEntity agendaSettingFileEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_setting_base_score_id", referencedColumnName = "id")
    private AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_setting_ki_score_id", referencedColumnName = "id")
    private AgendaSettingKIScoreEntity agendaSettingKIScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_setting_ku_score_id", referencedColumnName = "id")
    private AgendaSettingKUScoreEntity agendaSettingKUScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulasi_kebijakan_id", referencedColumnName = "id")
    private FormulasiKebijakanEntity formulasiKebijakanEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulasi_kebijakan_ki_id", referencedColumnName = "id")
    private FormulasiKebijakanKIEntity formulasiKebijakanKIEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulasi_kebijakan_ku_id", referencedColumnName = "id")
    private FormulasiKebijakanKUEntity formulasiKebijakanKUEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulasi_kebijakan_file_id", referencedColumnName = "id")
    private FormulasiKebijakanFileEntity formulasiKebijakanFileEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulasi_kebijakan_base_score_id", referencedColumnName = "id")
    private FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulasi_kebijakan_ki_score_id", referencedColumnName = "id")
    private FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "formulasi_kebijakan_ku_score_id", referencedColumnName = "id")
    private FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "implementasi_kebijakan_id", referencedColumnName = "id")
    private ImplementasiKebijakanEntity implementasiKebijakanEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "implementasi_kebijakan_ki_id", referencedColumnName = "id")
    private ImplementasiKebijakanKIEntity implementasiKebijakanKIEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "implementasi_kebijakan_ku_id", referencedColumnName = "id")
    private ImplementasiKebijakanKUEntity implementasiKebijakanKUEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "implementasi_kebijakan_file_id", referencedColumnName = "id")
    private ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "implementasi_kebijakan_base_score_id", referencedColumnName = "id")
    private ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "implementasi_kebijakan_ki_score_id", referencedColumnName = "id")
    private ImplementasiKebijakanKIScoreEntity implementasiKebijakanKIScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "implementasi_kebijakan_ku_score_id", referencedColumnName = "id")
    private ImplementasiKebijakanKUScoreEntity implementasiKebijakanKUScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluasi_kebijakan_id", referencedColumnName = "id")
    private EvaluasiKebijakanEntity evaluasiKebijakanEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluasi_kebijakan_ki_id", referencedColumnName = "id")
    private EvaluasiKebijakanKIEntity evaluasiKebijakanKIEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluasi_kebijakan_ku_id", referencedColumnName = "id")
    private EvaluasiKebijakanKUEntity evaluasiKebijakanKUEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluasi_kebijakan_file_id", referencedColumnName = "id")
    private EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluasi_kebijakan_base_score_id", referencedColumnName = "id")
    private EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluasi_kebijakan_ki_score_id", referencedColumnName = "id")
    private EvaluasiKebijakanKIScoreEntity evaluasiKebijakanKIScoreEntity;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluasi_kebijakan_ku_score_id", referencedColumnName = "id")
    private EvaluasiKebijakanKUScoreEntity evaluasiKebijakanKUScoreEntity;

    public PolicyEntity(Long id, Long createdBy, Long modifiedBy, Date createdAt, Date updatedAt, String name, AgencyEntity agency, Long enumeratorId, Date sentByAdminAt, Long sentByAdminId, Date verifiedByKoordinatorAt, Long verifiedByKoordinatorId, Date assignedByAdminAt, Long assignedByAdminId, Date processedByEnumeratorAt, Long processedByEnumeratorId, Date validatedByKoordinatorAt, Long validatedByKoordinatorId, Date validatedByKoordinatorUtamaAt, Long validatedByKoordinatorUtamaId, PolicyDetailsEntity policyDetailsEntity, PolicyProcessEntity policyProcessEntity, PolicyStatusEntity policyStatusEntity, AgendaSettingEntity agendaSettingEntity, AgendaSettingKIEntity agendaSettingKIEntity, AgendaSettingKUEntity agendaSettingKUEntity, AgendaSettingFileEntity agendaSettingFileEntity, AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity, AgendaSettingKIScoreEntity agendaSettingKIScoreEntity, AgendaSettingKUScoreEntity agendaSettingKUScoreEntity, FormulasiKebijakanEntity formulasiKebijakanEntity, FormulasiKebijakanKIEntity formulasiKebijakanKIEntity, FormulasiKebijakanKUEntity formulasiKebijakanKUEntity, FormulasiKebijakanFileEntity formulasiKebijakanFileEntity, FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity, FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity, FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity, ImplementasiKebijakanEntity implementasiKebijakanEntity, ImplementasiKebijakanKIEntity implementasiKebijakanKIEntity, ImplementasiKebijakanKUEntity implementasiKebijakanKUEntity, ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity, ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity, ImplementasiKebijakanKIScoreEntity implementasiKebijakanKIScoreEntity, ImplementasiKebijakanKUScoreEntity implementasiKebijakanKUScoreEntity, EvaluasiKebijakanEntity evaluasiKebijakanEntity, EvaluasiKebijakanKIEntity evaluasiKebijakanKIEntity, EvaluasiKebijakanKUEntity evaluasiKebijakanKUEntity, EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity, EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity, EvaluasiKebijakanKIScoreEntity evaluasiKebijakanKIScoreEntity, EvaluasiKebijakanKUScoreEntity evaluasiKebijakanKUScoreEntity) {
        super(id, createdBy, modifiedBy, createdAt, updatedAt);
        this.name = name;
        this.agency = agency;
        this.enumeratorId = enumeratorId;
        this.sentByAdminAt = sentByAdminAt;
        this.sentByAdminId = sentByAdminId;
        this.verifiedByKoordinatorAt = verifiedByKoordinatorAt;
        this.verifiedByKoordinatorId = verifiedByKoordinatorId;
        this.assignedByAdminAt = assignedByAdminAt;
        this.assignedByAdminId = assignedByAdminId;
        this.processedByEnumeratorAt = processedByEnumeratorAt;
        this.processedByEnumeratorId = processedByEnumeratorId;
        this.validatedByKoordinatorAt = validatedByKoordinatorAt;
        this.validatedByKoordinatorId = validatedByKoordinatorId;
        this.validatedByKoordinatorUtamaAt = validatedByKoordinatorUtamaAt;
        this.validatedByKoordinatorUtamaId = validatedByKoordinatorUtamaId;
        this.policyDetailsEntity = policyDetailsEntity;
        this.policyProcessEntity = policyProcessEntity;
        this.policyStatusEntity = policyStatusEntity;
        this.agendaSettingEntity = agendaSettingEntity;
        this.agendaSettingKIEntity = agendaSettingKIEntity;
        this.agendaSettingKUEntity = agendaSettingKUEntity;
        this.agendaSettingFileEntity = agendaSettingFileEntity;
        this.agendaSettingBaseScoreEntity = agendaSettingBaseScoreEntity;
        this.agendaSettingKIScoreEntity = agendaSettingKIScoreEntity;
        this.agendaSettingKUScoreEntity = agendaSettingKUScoreEntity;
        this.formulasiKebijakanEntity = formulasiKebijakanEntity;
        this.formulasiKebijakanKIEntity = formulasiKebijakanKIEntity;
        this.formulasiKebijakanKUEntity = formulasiKebijakanKUEntity;
        this.formulasiKebijakanFileEntity = formulasiKebijakanFileEntity;
        this.formulasiKebijakanBaseScoreEntity = formulasiKebijakanBaseScoreEntity;
        this.formulasiKebijakanKIScoreEntity = formulasiKebijakanKIScoreEntity;
        this.formulasiKebijakanKUScoreEntity = formulasiKebijakanKUScoreEntity;
        this.implementasiKebijakanEntity = implementasiKebijakanEntity;
        this.implementasiKebijakanKIEntity = implementasiKebijakanKIEntity;
        this.implementasiKebijakanKUEntity = implementasiKebijakanKUEntity;
        this.implementasiKebijakanFileEntity = implementasiKebijakanFileEntity;
        this.implementasiKebijakanBaseScoreEntity = implementasiKebijakanBaseScoreEntity;
        this.implementasiKebijakanKIScoreEntity = implementasiKebijakanKIScoreEntity;
        this.implementasiKebijakanKUScoreEntity = implementasiKebijakanKUScoreEntity;
        this.evaluasiKebijakanEntity = evaluasiKebijakanEntity;
        this.evaluasiKebijakanKIEntity = evaluasiKebijakanKIEntity;
        this.evaluasiKebijakanKUEntity = evaluasiKebijakanKUEntity;
        this.evaluasiKebijakanFileEntity = evaluasiKebijakanFileEntity;
        this.evaluasiKebijakanBaseScoreEntity = evaluasiKebijakanBaseScoreEntity;
        this.evaluasiKebijakanKIScoreEntity = evaluasiKebijakanKIScoreEntity;
        this.evaluasiKebijakanKUScoreEntity = evaluasiKebijakanKUScoreEntity;
    }
}
