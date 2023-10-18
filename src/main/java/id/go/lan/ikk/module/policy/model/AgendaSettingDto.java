package id.go.lan.ikk.module.policy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendaSettingDto {
    private Long idKebijakan;
    private Long idAgendaSetting;

    private String a1A;
    private String filePathA1A;
    private String fileTypeA1A;
    private String fileSizeA1A;
    private String fileOriginalNameA1A;

    private String a1B;
    private String filePathA1B;
    private String fileTypeA1B;
    private String fileSizeA1B;
    private String fileOriginalNameA1B;

    private String a1C;
    private String filePathA1C;
    private String fileTypeA1C;
    private String fileSizeA1C;
    private String fileOriginalNameA1C;

    private String a1D;
    private String filePathA1D;
    private String fileTypeA1D;
    private String fileSizeA1D;
    private String fileOriginalNameA1D;

    private String a2A;
    private String filePathA2A;
    private String fileTypeA2A;
    private String fileSizeA2A;
    private String fileOriginalNameA2A;

    private String a2B;
    private String filePathA2B;
    private String fileTypeA2B;
    private String fileSizeA2B;
    private String fileOriginalNameA2B;

    private List<String> a2C;
    private String filePathA2C;
    private String fileTypeA2C;
    private String fileSizeA2C;
    private String fileOriginalNameA2C;

    private String informasiA3;

    public void setA2C(String a2c) {
        if (a2c == null) {
            this.a2C = new ArrayList<>();
        } else {
            this.a2C = Stream.of(a2c.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
    }

    public void setA2C(List<String> a2c) {
        this.a2C = a2c;
    }
}
