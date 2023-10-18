package id.go.lan.ikk.module.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardPoliciesPerAgencyDto {
    private Long idInstansi;
    private String namaInstansi;
    private Integer totalKebijakan;
    private String tanggal;
    private Boolean isBeingVerified;

    public void setTanggal(Date tanggal) {
        if (tanggal == null) {
            return;
        }
        this.tanggal = new SimpleDateFormat("yyyy-MM-dd").format(tanggal);
    }
}
