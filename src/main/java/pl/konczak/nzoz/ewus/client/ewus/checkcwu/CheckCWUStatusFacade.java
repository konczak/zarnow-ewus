package pl.konczak.nzoz.ewus.client.ewus.checkcwu;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pl.konczak.nzoz.ewus.client.ewus.auth.Credentials;
import pl.konczak.nzoz.ewus.client.ewus.auth.LoginService;
import pl.konczak.nzoz.ewus.client.ewus.auth.LogoutService;
import pl.konczak.nzoz.ewus.client.ewus.checkcwu.response.CheckCWUResponse;
import pl.konczak.nzoz.ewus.db.PacjentPagableRepository;

@Service
public class CheckCWUStatusFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUStatusFacade.class);

    private static final String PESEL_NEW_CHILD = "00000000000";

    private static final String PESEL_INCORRECT_PREFIX = "00000";

    private final LoginService loginService;

    private final CheckCWUStatusService checkCWUStatusService;

    private final LogoutService logoutService;

    private final PacjentPagableRepository pacjentPagableRepository;

    public CheckCWUStatusFacade(LoginService loginService,
            CheckCWUStatusService checkCWUStatusService,
            LogoutService logoutService,
            PacjentPagableRepository pacjentPagableRepository) {
        this.loginService = loginService;
        this.checkCWUStatusService = checkCWUStatusService;
        this.logoutService = logoutService;
        this.pacjentPagableRepository = pacjentPagableRepository;
    }

    public CheckCWUResponse checkCWU(String pesel) throws Exception {
        LOGGER.debug("start checkCWU proces");

        Credentials credentials = loginService.login();

        CheckCWUResponse checkCWUResponse = checkCWUStatusService.checkCWU(credentials, pesel);

        logoutService.logout(credentials);

        LOGGER.debug("checkCWU proces completed");

        return checkCWUResponse;
    }

    public void checkCWUForAll() throws Exception {
        LOGGER.debug("start checkCWU all proces");

        Credentials credentials = loginService.login();

        AtomicLong count = new AtomicLong(0);
        AtomicLong countOfFails = new AtomicLong(0);
        final int size = 100;
        int pageNumber = 0;
        long totalNumberOfPesel, start, end;
        start = System.currentTimeMillis();
        Page<String> page = pacjentPagableRepository.findPage(pageNumber, size);
        totalNumberOfPesel = page.getTotalElements();

        while (page.hasContent()) {
            page.getContent().stream()
                    .filter(pesel -> !pesel.isEmpty())
                    .filter(pesel -> !PESEL_NEW_CHILD.equals(pesel))
                    .filter(pesel -> !pesel.endsWith(PESEL_INCORRECT_PREFIX))
                    .forEach(pesel -> {
                        count.incrementAndGet();
                        try {
                            checkCWUStatusService.checkCWU(credentials, pesel);
                        } catch (Exception ex) {
                            countOfFails.incrementAndGet();
                            LOGGER.error("Failed to checkCWU for <{}> because <{}>", pesel, ex.getMessage());
                        }
                    });

            page = pacjentPagableRepository.findPage(++pageNumber, size);
            LOGGER.debug("page <{}> processed", pageNumber);
        }

        pacjentPagableRepository.forceDatabaseStateRefresh();

        logoutService.logout(credentials);

        end = System.currentTimeMillis();

        LOGGER.info("finished checkCWU for <{}> pesel of <{}> found and <{}> has failed, process took <{}>s",
                count.get(),
                totalNumberOfPesel,
                countOfFails.get(),
                (end - start) / 1000);

        LOGGER.debug("checkCWU all proces completed");
    }
}
