package pl.konczak.nzoz.ewus.domain.checkcwu;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.config.EwusPersistenceConfiguration;

import javax.xml.soap.SOAPMessage;

@Component
public class CheckCWUStatusResponsePersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCWUStatusResponsePersistence.class);

    private final EwusPersistenceConfiguration ewusPersistenceConfiguration;

    private final DateTimeFormatter dateTimeFormatter;

    private final CheckCWUStatusResponsePersistenceSuffixFactory checkCWUStatusResponsePersistenceSuffixFactory;

    public CheckCWUStatusResponsePersistence(EwusPersistenceConfiguration ewusPersistenceConfiguration,
            CheckCWUStatusResponsePersistenceSuffixFactory checkCWUStatusResponsePersistenceSuffixFactory) {
        this.ewusPersistenceConfiguration = ewusPersistenceConfiguration;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        this.checkCWUStatusResponsePersistenceSuffixFactory = checkCWUStatusResponsePersistenceSuffixFactory;
    }

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

        LOGGER.info("CheckCWUStatusResponse save to file <{}>", pathToFile);
    }

    private String createDirectory(LocalDateTime now) {
        String folderdate = now.format(DateTimeFormatter.ISO_DATE);
        String folderPath = ewusPersistenceConfiguration.getFolder() + folderdate;

        File directory = new File(folderPath);
        directory.mkdirs();

        return folderPath;
    }

    private String createPathToFile(String pathToFolder, String pesel, LocalDateTime now, String status) {
        String fulldate = now.format(dateTimeFormatter);

        return pathToFolder + "/" + fulldate + "_" + pesel + "_" + status + ".xml";
    }
}
