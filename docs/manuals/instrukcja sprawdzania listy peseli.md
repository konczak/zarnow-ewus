# Żarnów eWUŚ v1.4.0

### 1. Wymaganie - możliwość sprawdzenia listy peseli na żądanie

Potrzebne jest rozwiązanie które pozwoli sprawdzić hurtem listę wybranych peseli. Najlepiej w sposób podobny do nocnego sprawdzania wszystkich. Przykładem
użycia może być sytuacja gdy w nocy sprawdzenie kilkudziesięciu peseli nie powiodło się. Wtedy w pliku raportu są wymienione błędne czyli mamy listę dla których
wystąpił błąd.

### 2. Aktualizacj konfiguracji

Podczas instalacji wersji **zarnow-ewus v1.4.0** konieczne jest dodanie wpisu do pliku konfiguracyjnego _application.properties_. Nowy klucz wygląda
następujące:

```properties
    ewus.check-listed.file-path=
```

musi on zawierać pełną ścieżkę wraz z nazwą pliku w którym zostanie umieszczona lista peseli do sprawdzenia. Plik najlepiej aby miał rozszerzenie `.csv` aby
jasno wskazywał jakiego format danych przechowuje. Więcej informacji o pliku i formacie w
sekcji [Format pliku do sprawdzania listy peseli](#format-pliku-do-sprawdzania-listy-peseli).

przykładowo u mnie wygląda on następująco:

```properties
  ewus.check-listed.file-path=e:/programowanie/nzoz/ewus-prod/pesele-do-sprawdzenia.csv
```

Po zmianie w pliku _application.properties_ należy wykonać standardowe kroki dla instalacji nowej wersji. To jest:

1. Zatrzymać działającą usługę Windows (odczekać chwilę aż się zakończy),
2. Podmienić plik JAR "zarnow-ewus.jar" plikiem "zarnow-ewus-1.4.0.jar" (nazwa pliku powinna brzmieć "zarnow-ewus.jar" aby być zgodna z plikiem run.bat),
3. Uruchomić usługę ponownie.

### 3. Format pliku do sprawdzania listy peseli

Potrzebujemy podać listę peseli. Do tego raport dla błędów generuje kolejne pesele jeden pod drugim. Najlepiej skorzystać z ogólnie
znanego [formatu pliku jakim jest CSV](https://pl.wikipedia.org/wiki/CSV_%28format_pliku%29).

Jako że nasz plik będzie zawierał na ten moment tylko jedną kolumnę to pominiemy nazwy/nagłówki kolumn w pliku CSV. Dzięki temu wystarczy z pliku z raportem
zaznaczyć listę peseli i przekopiować do pliku CSV bez dodatkowych kroków.

Przykładowy plik CSV nadający się do sprawdzenia:

```csv
12345678901
12345678902
12345678903
12345678904
12345678905
12345678906
12345678907
```

**UWAGA** jest to poprawny przykład pliku, ale nr PESEL nie są poprawne więc wywołanie dlań sprawdzenia serwer eWUŚ odrzuci.

Plik CSV możemy dowolnie modyfikować dla kolejnych sprawdzeń bez zatrzymywania usługi. Jedynie lepiej nie robić tego gdy trwa już sprawdzanie wybranych peseli
aby uniknąć niespodziewanych błędów które mogłyby przerwać proces.

### 4. Uruchamianie mechanizmu sprawdzenia listy wybranych peseli

Mając przygotowany i zapisany plik CSV z numerami pesel możemy wywołać operację sprawdzania. Jeżeli pliku nie będzie w lokalizacji wskazanej przez klucz _
ewus.check-listed.file-path_ to dostaniemy błąd przy wywołaniu. Do tego celu aplikacja wystawia metodę HTTP GET _"/ewus/check/listed"_. Otwierając w
przeglądarce URL:

```txt
    http://localhost:13000/ewus/check/listed
```

spowodujemy wywołanie sprawdzenia dla nr PESEL wymienionych w pliku CSV. Aplikacja odczyta wszystkie numery z pliku, a następnie dla każdego skontaktuje się z
serwerem eWUŚ pytając o status ubezpieczenia. Dokładne wyniki opisuje
sekcja [Wyniki sprawdzenia listy wybranych peseli](#wyniki-sprawdzenia-listy-wybranych-peseli).

**UWAGA** po otwarciu w/w adresu URL w przeglądarce będzie ona czekać na wynik - a ten aplikacja zwróci dopiero gdy skończy sprawdzanie wszystkich. Dla 50
peseli zwykle wystarczy kilkanaście sekund. Dla większej liczby oczywiście ten czas będzie większy.

### 5. Wyniki sprawdzenia listy wybranych peseli

Po zakończeniu sprawdzania na poziomie przeglądarki zobaczymy jedynie białą pustą stronę. Interesujące wyniki są gdzie indziej.

Generalnie wyniki są mocno analogiczne do rozwiązania sprawdzającego wszystkie pesele z bazy.

1. Każdy poprawnie sprawdzony PESEL dostanie swój własny plik XML. Plik XML zostanie zapisany w tym samym folderze jak dla sprawdzenia wszystkich. (czyli tam
   gdzie wskazuje konfiguracja _ewus.persistence.folder_ oraz w folderze nazwanego datą dnia wykonania sprawdzenia). Nazwa pliku również będzie analogiczna
   czyli _czas-sprawdzenia_PESEL_STATUS_UBEZPIECZENIA.xml_.
2. Raport z podsumowaniem wykonania sprawdzenia w formacie TXT analogicznym do sprawdzania wszystkich. Plik zostanie umieszczony w folderze wskazanym przez
   konfigurację _ewus.persistence.folder_. Opróćz czasu wykonania w nazwie pliku znajdzie się tekst **CheckCWUForListedPeselsReport**. Dzięki temu będzie można
   go łatwo odróżnić od raportu dla wszystkich który w nazwie ma **CheckCWUForAllReport**. Wewnątrz pliku również jako pierwsze linia pojawi się nazwa **
   CheckCWUForListedPeselsReport**. Pozostałe dane w pliku będą analogiczne jak w raporcie wszystkich czyli:
   - _proces trwał_ - czas sprawdzenia od początku do końca,
   - _liczba odnalezionych peseli_ - ile peseli odczytano z pliku CSV,
   - _liczba sprawdzonych peseli_ - dla ilu peseli wykonano sprawdzenie,
   - _liczba błędów podczas sprawdzania peseli_ - dla ilu peseli trafiono na błąd,
   - _liczba wykrytych peseli bez ubezpieczenia_ - dla ilu peseli stwierdzono brak ubezpieczenia.

   **UWAGA** wartości w _liczba odnalezionych peseli_ oraz _liczba sprawdzonych peseli_ będą się różnić jeżeli w pliku CSV znajdą się wpisy takie jak pesel z
   samych "0", czy zawierajacy tylko datę urodzenia, itp.

### 6. Czy można wywołać sprawdzenia listy wybranych peseli gdy działa sprawdzanie wszystkich?

Generalnie jest to możliwe, ale nie zalecane. Operacje w trakcie sprawdzania nie przeszkadzały by sobie, ale liczba równoczensych zapytań do serwera eWUŚ
zwiększyła by się dwukrotnie na sekundę. Mogłoby to doprowadzić do sytuacji że serwer zaczał by celowo odrzucać nasze sprawdzenia ze względu na zbyt dużą ilość
zapytań.

Każde zapytanie do eWUŚ wymaga dołączenia tokenu uwierzytelniającego. Podczas sprawdzania wielu peseli (w obu przypadkach) najpierw jest logowanie, sprawdzanie
kolejnych i wylogowanie. Operacja wylogowania w procesie zakończonym jako pierwszym spowodowała by że kolejne zapytania do eWUŚ z drugiego procesu kończyły by
się błędem uwierzytelnienia.

### 7. Ekstra - informacje dodatkowe

Od wersji **eWUŚ-v5** oraz **zarnow-ewus-1.3.0** do plików XML zapisywane są _"informacje_dodatkowe"_. Można w nich znaleźć kody wskazujące na kwarantannę,
izolację, szczepienie, czy uprawnienie specjalne dla uchodźców z Ukrainy. Wraz z rozbudową w eWUŚ w tym polu mogą pojawiać się nowe kody które aplikacja bez
problemu odczyta.

Wersja **zarnow-ewus-1.4.0** udostępnia te informacje w metodzie do sprawdzania pojedynczych PESEL. Wchodząc w przeglądarce na URL (z podanym prawidłowo numer
PESEL):

```txt
   http://localhost:13000/ewus/check?pesel=WSTAW_SWOJ_NR_PESEL
```

wyświetli się w przeglądarce wynik podobny do:

```json
{
   "status": "PERSON_WITH_PESEL_EXISTS",
   "pesel": "02082642235",
   "imie": "ImięUKR",
   "nazwisko": "NazwiskoUKR",
   "ubezpieczony": true,
   "oznaczenieRecept": null,
   "informacjeDodatkowe": [
      {
         "kod": "UKR",
         "poziom": "I",
         "wartosc": "Pacjent posiada uprawnienie do świadczeń opieki zdrowotnej na mocy Ustawy z dnia 12 marca 2022 r. o pomocy obywatelom Ukrainy w związku z konfliktem zbrojnym na terytorium tego państwa"
      },
      {
         "kod": "IZOLACJA DOMOWA",
         "poziom": "O",
         "wartosc": "Pacjent podlega izolacji domowej do dnia 10-04-2022"
      }
   ]
}

```

gdzie możemy zobaczyć że:

- pacjentowi należą się świadczenia w ramach pomocy dla Ukraińców
- pacjent podlega izolacji do 10 kwietnia 2022.
