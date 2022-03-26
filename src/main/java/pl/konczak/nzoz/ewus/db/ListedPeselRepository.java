package pl.konczak.nzoz.ewus.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ListedPeselRepository {

    public List<String> getPeselsToCheck() {
        List<String> pesels = new ArrayList<>();
        pesels.add("03070665355");
        pesels.add("03111357236");
        pesels.add("00000000000");
        pesels.add("87080200000");
        pesels.add("");
        pesels.add(" ");
        pesels.add("1234567890");
        pesels.add("123456789012");
        return pesels;
    }
}
