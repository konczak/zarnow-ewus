package pl.konczak.nzoz.ewus.db;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PacjentPagableRepository {

    private final PacjentRepository pacjentRepository;

    public PacjentPagableRepository(PacjentRepository pacjentRepository) {
        this.pacjentRepository = pacjentRepository;
    }

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
