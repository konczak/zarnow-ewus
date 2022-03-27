package pl.konczak.nzoz.ewus.db.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListedPesel {
    @CsvBindByPosition(position = 0)
    private String pesel;
}
