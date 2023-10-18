package id.go.lan.ikk.module.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardAdminInstansiCardDto {
    private Integer kebijakanDiajukan;
    private String kebijakanDiajukanLastUpdate;
    private Integer kebijakanDisetujui;
    private String kebijakanDisetujuiUpdate;
    private Integer kebijakanDitolak;
    private String kebijakanDitolakLastUpdate;
}
