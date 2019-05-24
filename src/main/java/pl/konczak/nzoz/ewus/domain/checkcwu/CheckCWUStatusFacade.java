package pl.konczak.nzoz.ewus.domain.checkcwu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.konczak.nzoz.ewus.db.PacjentPagableRepository;
import pl.konczak.nzoz.ewus.db.Patient;
import pl.konczak.nzoz.ewus.domain.authentication.Credentials;
import pl.konczak.nzoz.ewus.domain.authentication.LoginService;
import pl.konczak.nzoz.ewus.domain.authentication.LogoutService;
import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

@Slf4j
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CheckCWUStatusFacade {

    private static final String PESEL_NEW_CHILD = "00000000000";

    private static final String PESEL_INCORRECT_PREFIX = "00000";

    private final LoginService loginService;

    private final CheckCWUStatusService checkCWUStatusService;

    private final LogoutService logoutService;

    private final PacjentPagableRepository pacjentPagableRepository;

    private final CheckCWUForAllReportPersistence checkCWUForAllReportPersistence;

    public CheckCWUResponse checkCWU(String pesel) throws Exception {
        log.debug("start checkCWU proces");

        Credentials credentials = loginService.login();

        CheckCWUResponse checkCWUResponse = checkCWUStatusService.checkCWU(credentials, pesel);

        logoutService.logout(credentials);

        log.debug("checkCWU proces completed");

        return checkCWUResponse;
    }

    public void checkCWUForAll() throws Exception {
        log.debug("start checkCWU all proces");

        CheckCWUForAllReport.CheckCWUForAllReportBuilder builder = CheckCWUForAllReport.builder();
        builder.registerStart();

        Credentials credentials = loginService.login();

        final int size = 100;
        int pageNumber = 0;
        Page<Patient> page = pacjentPagableRepository.findPage(pageNumber, size);
        builder.withCountOfAllPesel(page.getTotalElements());

        while (page.hasContent()) {
            page.getContent().stream()
                    .filter(patient -> !patient.getPesel().isEmpty())
                    .filter(patient -> patient.getPesel().length() == 11)
                    .filter(patient -> !PESEL_NEW_CHILD.equals(patient.getPesel()))
                    .filter(patient -> !patient.getPesel().endsWith(PESEL_INCORRECT_PREFIX))
                    .filter(patient -> !patient.isDead())
                    .forEach(patient -> {
                        builder.incrementCountOfCheckedPesel();
                        String pesel = patient.getPesel();
                        try {
                            CheckCWUResponse checkCWUResponse = checkCWUStatusService.checkCWU(credentials, pesel);
                            if (!CheckCWUResponseAnalyzeUtil.isUbezpieczony(checkCWUResponse)) {
                                builder.addPeselBezUbezpieczenia(pesel);
                            }
                        } catch (Exception ex) {
                            builder.addFailedPesel(pesel);
                            log.error("Failed to checkCWU for <{}> because <{}>", patient.getPesel(), ex.getMessage());
                        }
                    });

            page = pacjentPagableRepository.findPage(++pageNumber, size);
            log.debug("page <{}> processed", pageNumber);
        }

        pacjentPagableRepository.forceDatabaseStateRefresh();

        logoutService.logout(credentials);

        builder.registerEnd();

        CheckCWUForAllReport checkCWUForAllReport = builder.build();

        checkCWUForAllReportPersistence.persist(checkCWUForAllReport);

        log.info("finished checkCWU for <{} of {}> found pesel numbers and <{}> has failed, process took <{}>s",
                checkCWUForAllReport.getCountOfCheckedPesel(),
                checkCWUForAllReport.getCountOfAllPesel(),
                checkCWUForAllReport.getCountOfFailedPesels(),
                checkCWUForAllReport.getProcessTimeInSeconds());

        log.debug("checkCWU all proces completed");
    }
}
