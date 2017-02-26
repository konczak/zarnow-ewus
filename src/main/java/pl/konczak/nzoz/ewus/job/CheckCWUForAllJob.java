package pl.konczak.nzoz.ewus.job;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.domain.checkcwu.CheckCWUStatusFacade;

@Component
public class CheckCWUForAllJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUForAllJob.class);

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    private final boolean onlyStartAndEndOfTheMonth;

    public CheckCWUForAllJob(CheckCWUStatusFacade checkCWUStatusFacade,
            @Value("${job.checkCWUForAll.only-start-and-end-of-the-month}") boolean onlyStartAndEndOfTheMonth) {
        this.checkCWUStatusFacade = checkCWUStatusFacade;
        this.onlyStartAndEndOfTheMonth = onlyStartAndEndOfTheMonth;
    }

    @Scheduled(cron = "${job.checkCWUForAll.cron}")
    public void run() {
        LocalDate now = LocalDate.now();
        if (onlyStartAndEndOfTheMonth
                && !isOneOfTwoFirstDaysOfTheMonth(now)
                && !isOneOfTwoEndDaysOfTheMonth(now)) {
            return;
        }
        LOGGER.info("CheckCWUForAllJob starts");

        try {
            checkCWUStatusFacade.checkCWUForAll();
        } catch (Exception ex) {
            LOGGER.error("checkCWUForAll failed", ex);
        }

        LOGGER.info("CheckCWUForAllJob ends");
    }

    private boolean isOneOfTwoEndDaysOfTheMonth(LocalDate now) {
        int monthLength = now.getMonth().length(now.isLeapYear());
        return now.getDayOfMonth() == monthLength
                || now.getDayOfMonth() == (monthLength - 1);
    }

    private boolean isOneOfTwoFirstDaysOfTheMonth(LocalDate now) {
        return now.getDayOfMonth() == 1
                || now.getDayOfMonth() == 2;
    }
}
