package id.go.lan.ikk.module.policy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PolicySelesaiDto {
    private Long id;
    private String nama;
    private String enumerator;
    private String proses;
    private String status;
    private Double nilai;
}
