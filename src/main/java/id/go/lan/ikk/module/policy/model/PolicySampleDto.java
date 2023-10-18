package id.go.lan.ikk.module.policy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicySampleDto {
    private Integer totalKebijakanDiajukan;
    private Integer totalKebijakanDisetujui;
    private Integer totalSampleKebijakan;
    private Boolean isRandomized;
    private List<PolicyDto> sampleKebijakanList;
}
