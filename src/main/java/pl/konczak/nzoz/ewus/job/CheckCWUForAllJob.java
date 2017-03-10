package pl.konczak.nzoz.ewus.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.domain.checkcwu.CheckCWUStatusFacade;

@Component
public class CheckCWUForAllJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUForAllJob.class);

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    public CheckCWUForAllJob(CheckCWUStatusFacade checkCWUStatusFacade) {
        this.checkCWUStatusFacade = checkCWUStatusFacade;
    }

    @Scheduled(cron = "${job.checkCWUForAll.cron}")
    public void run() {
        LOGGER.info("CheckCWUForAllJob starts");

        try {
            checkCWUStatusFacade.checkCWUForAll();
        } catch (Exception ex) {
            LOGGER.error("checkCWUForAll failed", ex);
        }

        LOGGER.info("CheckCWUForAllJob ends");
    }

}
