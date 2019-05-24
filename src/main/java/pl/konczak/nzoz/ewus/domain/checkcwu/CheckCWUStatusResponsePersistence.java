package pl.konczak.nzoz.ewus.domain.checkcwu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.config.EwusPersistenceConfiguration;

import javax.xml.soap.SOAPMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CheckCWUStatusResponsePersistence {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    private final EwusPersistenceConfiguration ewusPersistenceConfiguration;

    private final CheckCWUStatusResponsePersistenceSuffixFactory checkCWUStatusResponsePersistenceSuffixFactory;

    void persist(String pesel, SOAPMessage message) throws Exception {
        if (!ewusPersistenceConfiguration.isExecute()) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        String pathToFolder = createDirectory(now);
        String status = checkCWUStatusResponsePersistenceSuffixFactory.create(message);
        String pathToFile = createPathToFile(pathToFolder, pesel, now, status);

        File file = new File(pathToFile);
        file.createNewFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            message.writeTo(fileOutputStream);
            fileOutputStream.flush();
        }

        log.info("CheckCWUStatusResponse save to file <{}>", pathToFile);
    }

    private String createDirectory(LocalDateTime now) {
        String folderdate = now.format(DateTimeFormatter.ISO_DATE);
        String folderPath = ewusPersistenceConfiguration.getFolder() + folderdate;

        File directory = new File(folderPath);
        directory.mkdirs();

        return folderPath;
    }

    private String createPathToFile(String pathToFolder, String pesel, LocalDateTime now, String status) {
        String fulldate = now.format(DATE_TIME_FORMATTER);

        return pathToFolder + "/" + fulldate + "_" + pesel + "_" + status + ".xml";
    }
}
