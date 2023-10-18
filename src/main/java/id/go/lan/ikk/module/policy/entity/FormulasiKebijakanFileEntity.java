package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "formulasi_kebijakan_file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormulasiKebijakanFileEntity extends BaseEntity {
    private String filePathB1A;
    private String fileSizeB1A;
    private String fileTypeB1A;
    private String fileOriginalNameB1A;

    private String filePathB1B;
    private String fileSizeB1B;
    private String fileTypeB1B;
    private String fileOriginalNameB1B;

    private String filePathB2A;
    private String fileSizeB2A;
    private String fileTypeB2A;
    private String fileOriginalNameB2A;

    private String filePathB2B;
    private String fileSizeB2B;
    private String fileTypeB2B;
    private String fileOriginalNameB2B;

    private String filePathB3A;
    private String fileSizeB3A;
    private String fileTypeB3A;
    private String fileOriginalNameB3A;

    private String filePathB3B;
    private String fileSizeB3B;
    private String fileTypeB3B;
    private String fileOriginalNameB3B;

    private String filePathB3C;
    private String fileSizeB3C;
    private String fileTypeB3C;
    private String fileOriginalNameB3C;

    private String filePathB4A;
    private String fileSizeB4A;
    private String fileTypeB4A;
    private String fileOriginalNameB4A;

    private String filePathB4B;
    private String fileSizeB4B;
    private String fileTypeB4B;
    private String fileOriginalNameB4B;

    private String filePathB4C;
    private String fileSizeB4C;
    private String fileTypeB4C;
    private String fileOriginalNameB4C;

    private String filePathB5A;
    private String fileSizeB5A;
    private String fileTypeB5A;
    private String fileOriginalNameB5A;

    private String filePathB5B;
    private String fileSizeB5B;
    private String fileTypeB5B;
    private String fileOriginalNameB5B;

    private String filePathB5C;
    private String fileSizeB5C;
    private String fileTypeB5C;
    private String fileOriginalNameB5C;
}
