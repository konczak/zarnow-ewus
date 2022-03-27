package pl.konczak.nzoz.ewus.db;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.konczak.nzoz.ewus.config.EwusCheckListedConfiguration;
import pl.konczak.nzoz.ewus.db.csv.ListedPesel;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ListedPeselRepository {

    private final EwusCheckListedConfiguration ewusCheckListedConfiguration;

    public List<String> getPeselsToCheck() {
        List<ListedPesel> listedPesels;
        final String filePath = ewusCheckListedConfiguration.getFilePath();
        try {
            listedPesels = getCsvLines(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Read CSV file <" + filePath + "> failed", e);
        }
        return listedPesels.stream()
                .map(ListedPesel::getPesel)
                .collect(Collectors.toList());
    }

    private List<ListedPesel> getCsvLines(String filePath) throws Exception {
        return new CsvToBeanBuilder<ListedPesel>(new FileReader(filePath))
                .withType(ListedPesel.class)
                .build()
                .parse();
    }
}
