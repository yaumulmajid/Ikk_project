package id.go.lan.ikk.module.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardTopTenAgencyScoreDto {
    List<DashboardAgencyScoreDto> data;
}
