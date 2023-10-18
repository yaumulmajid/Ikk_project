package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.policy.model.ActiveYearDto;

public interface ActiveYearService {
    ActiveYearDto getActiveYear();
    void updateActiveYear(String startYear, String endYear);
}
