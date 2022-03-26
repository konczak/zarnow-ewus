package pl.konczak.nzoz.ewus.domain.checkcwu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.config.EwusPersistenceConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CheckCWUForAllReportPersistence {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    private final EwusPersistenceConfiguration ewusPersistenceConfiguration;

    void persist(CheckCWUForManyPeselsReport checkCWUForAllReport) throws IOException {
        final String pathToFile = createPathToFile(checkCWUForAllReport);
        List<String> content = new ArrayList<>();

        content.add(checkCWUForAllReport.getName());
        content.add("proces trwał: " + checkCWUForAllReport.getProcessTimeInSeconds() + "s");
        content.add("liczba odnalezionych peseli: " + checkCWUForAllReport.getCountOfAllPesel());
        content.add("liczba sprawdzonych peseli: " + checkCWUForAllReport.getCountOfCheckedPesel());
        content.add("liczba błędów podczas sprawdzania peseli: " + checkCWUForAllReport.getCountOfFailedPesels());
        content.add("liczba wykrytych peseli bez ubezpieczenia: " + checkCWUForAllReport.getCountOfPeseleBezUbezpieczenia());
        if (checkCWUForAllReport.getCountOfFailedPesels() > 0) {
            content.add("===== ***** ===== ***** =====");
            content.add("lista peseli dla których wystąpił błąd:");
            checkCWUForAllReport.getFailedPesels().stream()
                    .sorted()
                    .forEach(content::add);
        }

        if (checkCWUForAllReport.getCountOfPeseleBezUbezpieczenia() > 0) {
            content.add("===== ***** ===== ***** =====");
            content.add("lista peseli bez ubezpieczenia:");
            checkCWUForAllReport.getPeseleBezUbezpieczenia().stream()
                    .sorted()
                    .forEach(content::add);
        }

        Files.write(Paths.get(pathToFile), content);


        log.info("CheckCWUForAllReport save to file <{}>", pathToFile);
    }

    private String createPathToFile(CheckCWUForManyPeselsReport checkCWUForAllReport) {
        LocalDateTime now = LocalDateTime.now();
        String fulldate = now.format(DATE_TIME_FORMATTER);

        return ewusPersistenceConfiguration.getFolder() + fulldate + "_" + checkCWUForAllReport.getName() + ".txt";
    }
}
