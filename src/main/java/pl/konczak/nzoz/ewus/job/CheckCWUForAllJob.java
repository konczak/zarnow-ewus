package pl.konczak.nzoz.ewus.job;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.domain.checkcwu.CheckCWUStatusFacade;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CheckCWUForAllJob {

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    @Scheduled(cron = "${job.checkCWUForAll.cron}")
    public void run() {
        log.info("CheckCWUForAllJob starts");

        try {
            checkCWUStatusFacade.checkCWUForAll();
        } catch (Exception ex) {
            log.error("checkCWUForAll failed", ex);
        }

        log.info("CheckCWUForAllJob ends");
    }

}
