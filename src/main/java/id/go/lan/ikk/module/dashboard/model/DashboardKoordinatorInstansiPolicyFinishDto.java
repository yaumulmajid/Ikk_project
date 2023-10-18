package id.go.lan.ikk.module.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardKoordinatorInstansiPolicyFinishDto {
    private Long idInstansi;
    private String namaInstansi;
    private Integer totalKebijakan = 0;
}
