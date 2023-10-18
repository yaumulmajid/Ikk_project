package id.go.lan.ikk.module.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetUserDetailDto {
    private Long id;
    private String nip;
    private String nik;
    private String nama;
    private String jabatan;
    private String namaInstansi;
    private CoordinatorTypeDto jenisKoordinator;
    private String unitKerja;
    private String email;
    private String telpon;
    private String role;
    private String status;
}
