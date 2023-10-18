package id.go.lan.ikk.module.policy.model;

import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicyDto {
    private Long id;
    private String nama;
    private String tanggalBerlaku;
    private String tanggalVerifikasi;
    private String tanggalValidasiKi;
    private String tanggalValidasiKu;
    private String tanggalDiajukan;
    private String tanggalAssign;
    private String tanggalProses;
    private String jenis;
    private String filePath;
    private String fileType;
    private String fileSize;
    private String fileOriginalName;
    private String proses;
    private String status;
    private Long createBy;
    private GetUserDetailDto enumerator;
    private String instansi;
    private Double progres;

    public void setTanggalBerlaku(Date tanggalBerlaku) {
        if (tanggalBerlaku != null) {
            this.tanggalBerlaku = new SimpleDateFormat("yyyy-MM-dd").format(tanggalBerlaku);
        } else {
            this.tanggalBerlaku = null;
        }
    }

    public void setTanggalVerifikasi(Date tanggalVerifikasi) {
        if (tanggalVerifikasi != null) {
            this.tanggalVerifikasi = new SimpleDateFormat("yyyy-MM-dd").format(tanggalVerifikasi);
        } else {
            this.tanggalVerifikasi = null;
        }
    }

    public void setTanggalValidasiKi(Date tanggalValidasiKi) {
        if (tanggalValidasiKi != null) {
            this.tanggalValidasiKi = new SimpleDateFormat("yyyy-MM-dd").format(tanggalValidasiKi);
        } else {
            this.tanggalValidasiKi = null;
        }
    }

    public void setTanggalValidasiKu(Date tanggalValidasiKu) {
        if (tanggalValidasiKu != null) {
            this.tanggalValidasiKu = new SimpleDateFormat("yyyy-MM-dd").format(tanggalValidasiKu);
        } else {
            this.tanggalValidasiKu = null;
        }
    }

    public void setTanggalDiajukan(Date tanggalDiajukan) {
        if (tanggalDiajukan != null) {
            this.tanggalDiajukan = new SimpleDateFormat("yyyy-MM-dd").format(tanggalDiajukan);
        } else {
            this.tanggalDiajukan = null;
        }
    }

    public void setTanggalAssign(Date tanggalAssign) {
        if (tanggalAssign != null) {
            this.tanggalAssign = new SimpleDateFormat("yyyy-MM-dd").format(tanggalAssign);
        } else {
            this.tanggalAssign = null;
        }
    }

    public void setTanggalProses(Date tanggalProses) {
        if (tanggalProses != null) {
            this.tanggalProses = new SimpleDateFormat("yyyy-MM-dd").format(tanggalProses);
        } else {
            this.tanggalProses = null;
        }
    }

    public void setProgres(Double progres) {
        this.progres = BigDecimal.valueOf(progres)
                .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                .doubleValue();
    }
}
