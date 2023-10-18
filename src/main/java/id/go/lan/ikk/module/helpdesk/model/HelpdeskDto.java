package id.go.lan.ikk.module.helpdesk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HelpdeskDto {
    private String nama;
    private String number;
    private String role;
}
