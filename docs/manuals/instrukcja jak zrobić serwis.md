# Żarnów eWUŚ #

### 1. nssm ###
Na początek trzeba pobrać [https://nssm.cc/](nssm) ja wybrałem wersję 2.24.
**UWAGA** Ponieważ nssm nie ma pliku instalatora a chcemy aby był dostępny z konsoli musimy wszystko przygotować, a najlepiej będzie zrobić tak:

- w "Program files" (odpowiedniego dla typu systemu x86/x64) tworzymy folder _"nssm"_ i kopiujemy do niego _"nssm.exe"_ z pobranej paczki - oczywiście wybierając ten pasujący do systemu,
- następnie musimy dodać go do systemowej zmiennej _PATH_. Klikamy prawym na ikonie _"Komputer"_ i wybieramy właściwości. Dla Windows 7 i nowszych wybieramy _"Zaawansowane ustawienia systemu"_. Wybieramy zakładkę _"Zaawansowane"_ i klikamy przycisk _"Zmienne środowiskowe"_. Przechodzimy do dolnego okienka _"Zmienne systemowe"_ i odszukujemy zmienną o nazwie _PATH_, i klikamy przycisk _"Edytuj"_. Edytujemy pole _"Wartość zmiennej"_ na samym końcu dodając średnik _";"_ (służy do oddzielania kolejnych elementów listy) i teraz dodajemy ścieżkę do folderu gdzie znajduje się _"nssm.exe"_ z "Program files" u mnie wygląda ona następująco:

```
c:\Program Files\nssm\
```

zatwierdzamy wszystko ok, tym samym zamykając okna _"Zaawansowanych ustawień systemu"_.

- aby zweryfikować że poprawnie skonfigurowaliśmy _"nssm"_ otwieramy _"cmd"_ (uwaga jeżeli była otwarta w trakcie wprowadzania zmian to trzeba ją zrestartować) i wprowadzamy polecenie _"nssm"_ i Enter. Jeżeli dostaliśmy komunikat że polecenia nie rozpoznano to znaczy że coś jest źle skonfigurowane. Jeżeli wszystko jest ok wyskoczy nam numer wersji oraz lista dostępnych poleceń.

### 2. aplikacja jako serwis ###
- upewniamy się że aplikacja jest wyłączona,
- następnie zmieniamy nazwę pliku aplikacji z _"zarnow-ewus-1.0.0.jar"_ na _"zarnow-ewus.jar"_ . Robimy to po to aby uniknąć w przyszłości przy wgrywaniu nowej wersji aplikacji konieczności usuwania serwisu i konfigurowania na nowo. Ja też będę od tej pory dostarczał aplikację bez numeru wersji w nazwie,
- w linii komend zmieniamy folder na ten w którym znajduje się aplikacja (plik _"zarnow-ewus.jar"_),
- w folderze z aplikacją tworzymy skrypt _"run.bat"_ który zawiera następujące polecenie:

```
java -jar zarnow-ewus.jar
```

- z pomocą _"nssm"_ rejestrujemy skrypt jako usługę (serwis) Windows-owy następującą komendą:

```
nssm install NAZWA_NASZEGO_SERWISU "ŚCIEŻKA_DO_SKRYPTU\run.bat"
```
przykładowo u mnie wyglądało ono następująco:

```
nssm install zarnow-ewus "e:\programowanie\nzoz\ewus-prod\run.bat"
```

jeżeli instalacja przebiegła pomyślnie to pojawił się komunikat _"Service "zarnow-ewus" installed successfully!"_. Natomiast przeglądając listę usług np. przez _"services.msc"_ możemy znaleźć nasz serwis na liście i zobaczyć że ma on status uruchomienia Automatyczny oraz że jest aktualnie wyłączony. Po restarcie systemu aplikacja zostanie uruchomiona automatycznie.

**UWAGA** naturalnym wydaje się że moglibyśmy pozbyć się skryptu _"run.bat"_ i bezpośrednio zarejestrować jako usługę _"java -jar zarnow-ewus.jar"_ jednakże w tej sytuacji jest problem. Mianowicie folderem aplikacji staje się folder z Javą i aplikacja nie znajduje wtedy pliku konfiguracyjnego _"application.properties"_. 

### 3. ręczne startowanie i zatrzymywanie serwisu ###
- aby uruchomić usługę z linii poleceń wywołujemy komendę:

```
nssm start NAZWA_NASZEGO_SERWISU
```
przykładowo u mnie:

```
nssm start zarnow-ewus
```

- aby zatrzymać usługę z linii poleceń wywołujemy komendę:
```
nssm stop NAZWA_NASZEGO_SERWISU
```
przykładowo u mnie:

```
nssm stop zarnow-ewus
```

- aby nie musieć pamiętać tych komend i wprowadzać ich każdorazowo sugeruję utworzyć 2 skrypty _".bat"_: _"start.bat"_ i _"stop.bat"_ - analogicznie w każdym z nich wystarczy podać to samo polecenie co w/w. Skrypty można umieścić w folderze z aplikacją lub gdziekolwiek indziej - tylko wtedy najlepiej nadać im bardziej adekwatne nazwy.

### 4. weryfikacja ###
Aby zweryfikować że aplikacja działa powinniśmy wykonać 2 rzeczy:

- wykonać zapytanie przez przeglądarkę:

```
http://localhost:13000/ewus/check?pesel=WSTAW_SWOJ_NR_PESEL
```

- oraz zajrzeć do pliku _"ewus.log"_ i upewnić się że znalazły się tam bieżące wpisy.

### 5. opcjonalne usprawnienie ###
Ponieważ gdy aplikacja działa jako serwis to nie widać konsoli. Jednakże logi są wpisywane jednocześnie do pliku jak i konsoli. Gdy mówimy o serwisie logi konsoli są przekierowywane do logów systemowych. Aplikacja może generować duże ilości logów systemowych co jest zupełnie zbędne. Aby tego uniknąć możemy zmienić konfigurację w pliku _"logback.xml"_. Interesuje nas linia 29:

```
        <appender-ref ref="STDOUT" />
```

Możemy ją całkowicie usunąć lub zamienić "za-komentować":

```
	<!--<appender-ref ref="STDOUT" />-->
```

po zmianie restart aplikacji/serwisu jest potrzebny aby zmiana zaczęła obowiązywać.