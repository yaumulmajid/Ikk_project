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
public class DashboardAdminInstansiCardPolicyDto {
    private Long id;
    private String instansi;
    private String namaKebijakan;
    private String status;
    private String tanggalPengesahan;
    private Double progres;
    private String proses;

    public void setTanggalPengesahan(Date tanggalPengesahan) {
        if (tanggalPengesahan == null) {
            return;
        }
        this.tanggalPengesahan = new SimpleDateFormat("yyyy-MM-dd").format(tanggalPengesahan);
    }
}
