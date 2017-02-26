package pl.konczak.nzoz.ewus.domain.checkcwu;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CheckCWUForAllReport {

    private final long countOfCheckedPesel;

    private final long countOfAllPesel;

    private final long processTimeInSeconds;

    private final Set<String> failedPesels;

    private final Set<String> peseleBezUbezpieczenia;

    private CheckCWUForAllReport(long countOfCheckedPesel,
            long countOfAllPesel,
            long processTimeInSeconds,
            Set<String> failedPesels,
            Set<String> peseleBezUbezpieczenia) {
        this.countOfCheckedPesel = countOfCheckedPesel;
        this.countOfAllPesel = countOfAllPesel;
        this.processTimeInSeconds = processTimeInSeconds;
        this.failedPesels = failedPesels;
        this.peseleBezUbezpieczenia = peseleBezUbezpieczenia;
    }

    public long getCountOfCheckedPesel() {
        return countOfCheckedPesel;
    }

    public long getCountOfAllPesel() {
        return countOfAllPesel;
    }

    public long getProcessTimeInSeconds() {
        return processTimeInSeconds;
    }

    public Set<String> getFailedPesels() {
        return Collections.unmodifiableSet(failedPesels);
    }

    public int getCountOfFailedPesels() {
        return this.failedPesels.size();
    }

    public Set<String> getPeseleBezUbezpieczenia() {
        return Collections.unmodifiableSet(peseleBezUbezpieczenia);
    }

    public int getCountOfPeseleBezUbezpieczenia() {
        return this.peseleBezUbezpieczenia.size();
    }

    public static CheckCWUForAllReportBuilder builder() {
        return new CheckCWUForAllReportBuilder();
    }

    public static class CheckCWUForAllReportBuilder {

        private long countOfAllPesel;

        private long countOfCheckedPesel;

        private long startProcess;

        private long endProcess;

        private final Set<String> failedPesels;

        private final Set<String> peseleBezUbezpieczenia;

        public CheckCWUForAllReportBuilder() {
            this.countOfAllPesel = 0;
            this.countOfCheckedPesel = 0;
            this.startProcess = 0;
            this.endProcess = 0;
            this.failedPesels = new HashSet<>();
            this.peseleBezUbezpieczenia = new HashSet<>();
        }

        public void withCountOfAllPesel(long countOfAllPesel) {
            this.countOfAllPesel = countOfAllPesel;
        }

        public void incrementCountOfCheckedPesel() {
            this.countOfCheckedPesel++;
        }

        public void registerStart() {
            this.startProcess = System.currentTimeMillis();
        }

        public void registerEnd() {
            this.endProcess = System.currentTimeMillis();
        }

        public void addFailedPesel(String pesel) {
            this.failedPesels.add(pesel);
        }

        public void addPeselBezUbezpieczenia(String pesel) {
            this.peseleBezUbezpieczenia.add(pesel);
        }

        public CheckCWUForAllReport build() {
            if (startProcess == 0
                    || endProcess == 0) {
                throw new IllegalStateException("start and end of process has to be registered");
            }
            if (countOfAllPesel == 0
                    && (countOfCheckedPesel != 0 || !failedPesels.isEmpty())) {
                throw new IllegalStateException("total count of all pesel is not set");
            }

            return new CheckCWUForAllReport(countOfCheckedPesel,
                    countOfAllPesel,
                    (endProcess - startProcess) / 1000,
                    new HashSet<>(failedPesels),
                    new HashSet<>(peseleBezUbezpieczenia));
        }

    }

}
