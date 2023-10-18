package id.go.lan.ikk.module.policy.presenter.controller;

import id.go.lan.ikk.module.policy.model.EvaluasiKebijakanDto;
import id.go.lan.ikk.module.policy.service.EvaluasiKebijakanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("kebijakan/enumerator")
public class EvaluasiKebijakanController {

    @Autowired
    private EvaluasiKebijakanService evaluasiKebijakanService;

    @GetMapping("/evaluasikebijakan/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getEvaluasiKebijakanByPolicyId(@PathVariable Long idKebijakan) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.getEvaluasiKebijakanByPolicyId(idKebijakan);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d1a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD1A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD1A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d1b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD1B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD1B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d2a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD2A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD2A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d2b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD2B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD2B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d3a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD3A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD3A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d3b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD3B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD3B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d3c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD3C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD3C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d3d")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD3D(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD3D(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/d3e")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitD3E(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitD3E(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/evaluasikebijakan/{idKebijakan}/informasid4")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitInformasiD4(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer) {
        EvaluasiKebijakanDto responseBody = evaluasiKebijakanService.submitInformasiD4(idKebijakan, answer);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
