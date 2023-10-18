package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.module.policy.entity.ActiveYearEntity;
import id.go.lan.ikk.module.policy.model.ActiveYearDto;
import id.go.lan.ikk.module.policy.repository.ActiveYearRepository;
import id.go.lan.ikk.module.policy.service.ActiveYearService;
import id.go.lan.ikk.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

@Service
public class ActiveYearServiceImpl implements ActiveYearService {

    @Autowired
    private ActiveYearRepository activeYearRepository;

    @Autowired
    private UserService userService;

    @Override
    public ActiveYearDto getActiveYear() {
        ActiveYearEntity activeYearEntity = activeYearRepository.findFirstByOrderByIdAsc();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(activeYearEntity.getStartYear());

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(activeYearEntity.getEndYear());

        return new ActiveYearDto(Integer.toString(startCalendar.get(Calendar.YEAR)), Integer.toString(endCalendar.get(Calendar.YEAR)));
    }

    @Override
    public void updateActiveYear(String startYear, String endYear) {
        Date startDate = new Date(parseInt(startYear) - 1900, Calendar.JANUARY, 1);
        Date endDate = new Date(parseInt(endYear) - 1900, Calendar.DECEMBER, 31);

        ActiveYearEntity activeYearEntity = activeYearRepository.findFirstByOrderByIdAsc();
        activeYearEntity.setStartYear(startDate);
        activeYearEntity.setEndYear(endDate);
        activeYearEntity.setModifiedBy(userService.getSignedInUserId());

        activeYearRepository.save(activeYearEntity);
    }
}
