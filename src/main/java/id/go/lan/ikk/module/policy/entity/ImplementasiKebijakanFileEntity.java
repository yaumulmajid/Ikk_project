package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "implementasi_kebijakan_file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImplementasiKebijakanFileEntity extends BaseEntity {
    private String filePathC1A;
    private String fileSizeC1A;
    private String fileTypeC1A;
    private String fileOriginalNameC1A;

    private String filePathC1B;
    private String fileSizeC1B;
    private String fileTypeC1B;
    private String fileOriginalNameC1B;

    private String filePathC1C;
    private String fileSizeC1C;
    private String fileTypeC1C;
    private String fileOriginalNameC1C;

    private String filePathC1D;
    private String fileSizeC1D;
    private String fileTypeC1D;
    private String fileOriginalNameC1D;

    private String filePathC2A;
    private String fileSizeC2A;
    private String fileTypeC2A;
    private String fileOriginalNameC2A;

    private String filePathC2B;
    private String fileSizeC2B;
    private String fileTypeC2B;
    private String fileOriginalNameC2B;

    private String filePathC2C;
    private String fileSizeC2C;
    private String fileTypeC2C;
    private String fileOriginalNameC2C;

    private String filePathC3A;
    private String fileSizeC3A;
    private String fileTypeC3A;
    private String fileOriginalNameC3A;

    private String filePathC3B;
    private String fileSizeC3B;
    private String fileTypeC3B;
    private String fileOriginalNameC3B;

    private String filePathC3C;
    private String fileSizeC3C;
    private String fileTypeC3C;
    private String fileOriginalNameC3C;
}
