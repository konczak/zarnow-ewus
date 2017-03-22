package pl.konczak.nzoz.ewus.domain.checkcwu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.config.EwusPersistenceConfiguration;

@Component
public class CheckCWUForAllReportPersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUForAllReportPersistence.class);
    
    private final EwusPersistenceConfiguration ewusPersistenceConfiguration;

    private final DateTimeFormatter dateTimeFormatter;

    public CheckCWUForAllReportPersistence(EwusPersistenceConfiguration ewusPersistenceConfiguration) {
        this.ewusPersistenceConfiguration = ewusPersistenceConfiguration;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    }

    void persist(CheckCWUForAllReport checkCWUForAllReport) throws IOException {
        final String pathToFile = createPathToFile();
        List<String> content = new ArrayList<>();

        content.add("CheckCWUForAllReport");
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
        
        
        LOGGER.info("CheckCWUForAllReport save to file <{}>", pathToFile);
    }

    private String createPathToFile() {
        LocalDateTime now = LocalDateTime.now();
        String fulldate = now.format(dateTimeFormatter);

        return ewusPersistenceConfiguration.getFolder() + fulldate + "_CheckCWUForAllReport.txt";
    }
}
