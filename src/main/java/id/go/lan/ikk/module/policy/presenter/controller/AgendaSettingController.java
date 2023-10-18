package id.go.lan.ikk.module.policy.presenter.controller;

import id.go.lan.ikk.module.policy.model.AgendaSettingDto;
import id.go.lan.ikk.module.policy.service.AgendaSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/kebijakan/enumerator")
public class AgendaSettingController {

    @Autowired
    private AgendaSettingService agendaSettingService;

    @GetMapping("/agendasetting/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getAgendaSettingByPolicyId(@PathVariable Long idKebijakan) {
        AgendaSettingDto responseBody = agendaSettingService.getAgendaSettingByPolicyId(idKebijakan);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/a1a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitA1A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AgendaSettingDto responseBody = agendaSettingService.submitA1A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/a1b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitA1B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AgendaSettingDto responseBody = agendaSettingService.submitA1B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/a1c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitA1C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AgendaSettingDto responseBody = agendaSettingService.submitA1C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/a1d")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitA1D(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AgendaSettingDto responseBody = agendaSettingService.submitA1D(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/a2a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitA2A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AgendaSettingDto responseBody = agendaSettingService.submitA2A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/a2b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitA2B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AgendaSettingDto responseBody = agendaSettingService.submitA2B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/a2c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitA2C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AgendaSettingDto responseBody = agendaSettingService.submitA2C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/agendasetting/{idKebijakan}/informasia3")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitInformasiA3(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer) {
        AgendaSettingDto responseBody = agendaSettingService.submitInformasiA3(idKebijakan, answer);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
