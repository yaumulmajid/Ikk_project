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
public class DashboardKoordinatorInstansiPolicyProcessDto {
    private String namaInstansi;
    private Long idInstansi;
    private String tanggalDiajukan;
    private Integer totalKebijakan = 0;

    public void setTanggalDiajukan(Date tanggal) {
        if (tanggal == null) {
            return;
        }
        this.tanggalDiajukan = new SimpleDateFormat("yyyy-MM-dd").format(tanggal);
    }
}
