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
public class DashboardKoordinatorUtamaCardPolicyDto {
    private Long idKebijakan;
    private String instansi;
    private String namaKebijakan;
    private String prosesKebijakan;
    private String statusKebijakan;
    private String tanggalPengesahan;
    private Double progres;
    private Double totalNilaiSatu;
    private Double totalNilaiDua;
    private Double totalNilaiTiga;
    private Double nilaiSatuAgendaSetting;
    private Double nilaiSatuFormulasiKebijakan;
    private Double nilaiSatuImplementasiKebijakan;
    private Double nilaiSatuEvaluasiKebijakan;
    private Double nilaiDuaAgendaSetting;
    private Double nilaiDuaFormulasiKebijakan;
    private Double nilaiDuaImplementasiKebijakan;
    private Double nilaiDuaEvaluasiKebijakan;
    private Double nilaiTigaAgendaSetting;
    private Double nilaiTigaFormulasiKebijakan;
    private Double nilaiTigaImplementasiKebijakan;
    private Double nilaiTigaEvaluasiKebijakan;

    public void setTanggalPengesahan(Date tanggalPengesahan) {
        if (tanggalPengesahan == null) {
            return;
        }
        this.tanggalPengesahan = new SimpleDateFormat("yyyy-MM-dd").format(tanggalPengesahan);
    }
}
