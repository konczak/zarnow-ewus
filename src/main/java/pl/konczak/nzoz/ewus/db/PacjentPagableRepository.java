package pl.konczak.nzoz.ewus.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PacjentPagableRepository {

    private final PacjentRepository pacjentRepository;

    public Page<Patient> findPage(int page, int size) {
        List<Patient> list = pacjentRepository.findAll();
        Pageable pageable = new PageRequest(page, size);

        int skip = page * size;

        List<Patient> result = list.stream()
                .skip(skip)
                .limit(size)
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, list.size());

    }

    public void forceDatabaseStateRefresh() {
        pacjentRepository.clearCache();
    }
}
