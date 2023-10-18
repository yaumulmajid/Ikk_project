package id.go.lan.ikk.module.policy.entity;

import id.go.lan.ikk.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "agenda_setting_file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendaSettingFileEntity extends BaseEntity {
    private String filePathA1A;
    private String fileSizeA1A;
    private String fileTypeA1A;
    private String fileOriginalNameA1A;

    private String filePathA1B;
    private String fileSizeA1B;
    private String fileTypeA1B;
    private String fileOriginalNameA1B;

    private String filePathA1C;
    private String fileSizeA1C;
    private String fileTypeA1C;
    private String fileOriginalNameA1C;

    private String filePathA1D;
    private String fileSizeA1D;
    private String fileTypeA1D;
    private String fileOriginalNameA1D;

    private String filePathA2A;
    private String fileSizeA2A;
    private String fileTypeA2A;
    private String fileOriginalNameA2A;

    private String filePathA2B;
    private String fileSizeA2B;
    private String fileTypeA2B;
    private String fileOriginalNameA2B;

    private String filePathA2C;
    private String fileSizeA2C;
    private String fileTypeA2C;
    private String fileOriginalNameA2C;
}
