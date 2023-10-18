package id.go.lan.ikk.module.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardEnumeratorCardDto {
    private Integer kebijakanMasuk;
    private String kebijakanMasukLastUpdate;
    private Integer kebijakanDiproses;
    private String kebijakanDiprosesLastUpdate;
    private Integer kebijakanSelesai;
    private String kebijakanSelesaiLastUpdate;
}
