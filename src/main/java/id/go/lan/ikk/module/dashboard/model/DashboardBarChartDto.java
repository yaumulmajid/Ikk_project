package id.go.lan.ikk.module.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardBarChartDto {
    private DashboardTopTenAgencyNameDto yAxis;
    private DashboardTopTenAgencyScoreDto series;
}
