package id.go.lan.ikk.module.policy.presenter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddUpdatePolicyRequest {
    private String nama;
    private String tanggal;
    private String jenis;
    private MultipartFile file;
}
