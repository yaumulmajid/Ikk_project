package id.go.lan.ikk.module.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardAgencyScoreDto {
    private Long idInstansi;
    private Double value;

    public void setValue(Double value) {
        this.value = BigDecimal.valueOf(value)
                .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                .doubleValue();
    }
}
