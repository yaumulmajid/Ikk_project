package id.go.lan.ikk.module.user.presenter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminNasionalAddUpdateUserRequest {
    private Long parentId;
    private String nip;
    private String nik;
    private String nama;
    private String jabatan;
    private Long idInstansi;
    private Long jenisKoordinator;
//    private String jenisKoordinator;
    private String unitKerja;
    private String email;
    private String telpon;
    private String role;
    private String status;
    private String password;
}
