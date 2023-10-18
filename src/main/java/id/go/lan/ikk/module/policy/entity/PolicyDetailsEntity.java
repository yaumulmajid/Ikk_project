package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "policy_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicyDetailsEntity extends BaseEntity {
    private String sector;
    private Date effectiveDate;
    private Double progress;
    private Double baseScore;
    private Double validationKIScore;

    @Column(columnDefinition = "TEXT")
    private String validationKINote;

    private Double validationKUScore;
    private String filePath;
    private String fileSize;
    private String fileType;
    private String fileOriginalName;
}
