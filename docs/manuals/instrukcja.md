# Żarnów eWUŚ #

### 1. Java 8 ### {#requirements-java}

- **UWAGA** Java ma brzydki zwyczaj że wersja 8 = 1.8 (analogicznie dla wersji 6 oraz 7)
- sprawdź czy Java 8 jest zainstalowana.
- otwórz menu Start,
- wybierz pozycję uruchom i wpisz **_cmd_** lub wpisz **_cmd_** w okno wyszukiwania,
- następnie w linii komend wprowadź:

```
java -version
```

- jeżeli pojawił się komunikat:

```
java version "1.8***"
```

to znaczy że Java w wymaganej wersji jest już zainstalowana i można przejść do [następnego punktu](#requirements-app-placement).

- jeżeli pojawił się komunikat że polecenia nie odnaleziono lub numer wersji jest mniejszy niż 1.8, konieczne będzie pobranie oraz zainstalowanie Java Runtime
  Edition (JRE) w wersji 8. Należy ją pobrać [tutaj](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) - sugeruję wybór
  wersji offline dzięki temu cały plik instalacyjny zostanie pobrany na dysk lokalny. Należy jeszcze dobrać wersję do systemu operacyjnego - Windows x86 dla
  32-bitowego lub Windows x64 dla 64-bitowego.
- podczas instalacji postępuj zgodnie z instrukcjami.
- po zakończeniu instalacji wymagany jest restart linii komend.

### 2. Dostarczone pliki i lokalizacja aplikacji ### {#requirements-app-placement}

Dostarczone pliki i foldery:

- **_logs/_**
- **_results/_**
- **_application.properties_**
- **_instrukcja.html_**
- **_logback.xml_**
- **_zarnow-ewus-1.0.0.jar_**

umieszczamy w wybranym przez siebie folderze np. _"C:/zarnow-ewus/"_.

### 3. Konfiguracja aplikacji ### {#requirements-config}

Konfiguracja aplikacji znajduje się w pliku application.properties i pozwala na łatwą parametrów bez konieczności zmian w kodzie. Dostarczony plik ma
następujące parametry:

```
server.port=13000
```

- aplikacja nasłuchuje na zapytania HTTP na porcie 13000 - gdyby inna aplikacja zajmowała ten port, aplikacja nie wystartuje - wtedy wystarczy zmienić tą
  wartość na dowolny inny numer. **UWAGA** najlepiej wybierać numer z zakresu 3000-65000.

```
logging.config=
```
- ten parametr definiuje ścieżkę oraz nazwę pliku gdzie znajduje się plik konfiguracyjny logów które będzie generować aplikacja. Konfiguracja logów jest opisana w [osobnym punkcie](#requirements-logs). Plik konfiguracyjny logów to dostarczony logback.xml. Przykładowa wartość tego parametru to _"C:/zarnow-ewus/logback.xml"_
```
ewus.auth.url=https://ewus.nfz.gov.pl/ws-broker-server-ewus/services/Auth
ewus.broker.url=https://ewus.nfz.gov.pl/ws-broker-server-ewus/services/ServiceBroker
```
- te 2 parametry definiuję adres URL serwerów NFZ służących do logwania oraz obsługi zapytań o status ubezpieczenia pacjenta. Domyślnie są ustawione na aktualny adres i nie ma potrzeby ich zmieniać.
```
ewus.credentials.ow=05
ewus.credentials.user-type=SWD
ewus.credentials.identyfikator-swiadczeniodawcy=
ewus.credentials.login=
ewus.credentials.password=
```
- powyższe 5 parametrów określa sposób oraz dane logowania do serwisów NFZ. Kolejno _"ow"_ to identyfikator oddziału a wartość _"05"_ oznacza łódzki. Parametr _"user-type"_ określa typ użytkownika - w naszym przypadku poprawną wartością jest _"SWD"_ oznaczające świadczeniodawcę. Następnie _"identyfikator-swiadczeniodawcy"_ tu należy wprowadzić stosowny numer. Na końcu zostają już proste rzeczy czyli _"login"_ oraz _"password"_ czyli hasło. Ponieważ hasło musi być regularnie zmieniane to pole będzie najczęściej aktualizowane. Należy pamiętać że po wprowadzeniu nowego hasła konieczny jest restart aplikacji.
```
ewus.client.name=zarnow-nzoz-ewus
ewus.client.version=1.0.0
```
- nazwa oraz wersja aplikacji-klienta która będzie kontaktowała się z serwerami NFZ. Możemy w przyszłości pomyśleć o tym aby te parametry były odczytywane automatycznie z aplikacji - jednakże podczas testów łatwiej mi było móc te parametry zmieniać.
```
ewus.persistence.execute=true
ewus.persistence.folder=
```
- pierwszy parametr mówi o tym czy wyniki pobrane z systemu eWUŚ mają być zapisywane do plików. Przyjmuje wartości typu boolean czyli _"true"_ lub _"false"_. Ustawienie wartości _"false"_ sprawi że poszczególne wyniki nie będą zapisywane do pliku. Jednakże nie będzie miało to wpływu na zapis raportu podsumowującego czy też wyniki zwraca przez REST API np. w przeglądarce. Drugi parametr _"folder"_ musi określać ścieżkę gdzie będą zapisywane raporty oraz poszczególne wyniki. Sugeruję podanie tutaj ścieżki do dostarczonego folderu **_results/_** jednakżę nie ma takiego obowiązku. Przykładowa poprawna wartość to _"C:/zarnow-ewus/results/"_
```
database.driver=com.googlecode.paradox.Driver
database.url=jdbc:paradox:TUTAJ_FOLDER_GDZIE_SIEDZI_BAZA
```
- pierwszego parametru nie trzeba zmieniać jednakże jest on potrzebny aplikacji do odszukania sterownika który pozwoli na komunikację z bazą danych typu Paradox. Drugi parametr zawiera prefix określający rodzaj silnika Java który będzie wykorzystywany przy łączeniu z bazą oraz rodzaj bazy danych a na końcu musi znaleźć się ścieżka do folderu z bazą _"Bazap"_. Przykładowa poprawna wartość tego parametru to _"jdbc:paradox:C:/!ProgramNZOZ/folderZawierajacyBaze"_
```
job.checkCWUForAll.cron=0 30 0 * * MON-FRI
```
- ostatni parametr definiuje kiedy aplikacja powinna rozpocząć wykonywanie sprawdzenia statusu ubezpieczenia wszystkich pacjentów. Wartość tego parametru musi być zgodna z wyrażeniem "cron expression". "Cron expression" pozwala właśnie na elastyczne określanie czasów wykonania pewnych powtarzalnych operacji. Dokładny opis można znaleźć na [Wikipedi](https://pl.wikipedia.org/wiki/Cron), choć niestety polski opis jest bardzo skrótowy - wersja angielska jest znacznie lepsza. Domyślna wartość która jest ustawiona oznacza dokładnie: każdego dnia od poniedziałku do piątku o godzinie 00:30:00. Dokładny efekt wywołania tej funkcjonalności znajduje się w sekcji [sprawdzenie wszystkich pacjentów](#business-check-all-patients)

### 4. Konfiguracja logów ### {#requirements-logs}
Na wstępie - po co są logi z aplikacji? Aby można było zweryfikować co aplikacja robiła - jakie dostała żądania np. z REST API czy też że rozpoczął się proces automatyczny. Również z logów można wyczytać jak przebiegała komunikacja z serwerami NFZ. Logi będą również miejscem gdzie zostaną zapisane błędy które być może pojawią się w aplikacji np. że nie można było dostać się do bazy danych lub zapis do pliku się nie powiódł.
Dostarczony plik logback.xml ma kilka domyślnych ustawień:

- zdarzenia z bieżącego dnia będą zapisywane do pliku _"zarnow-ewus.log"_,
- zdarzenia z dni poprzedzających będą zapisane w plikach _"zarnow-ewus-YYYY-MM-DD.log"_czyli z sufixem z jakiego dnia pochodzi log,
- aplikacja będzie automatycznie usuwać logi starsze niż 30 dni - taka wartość zapewnia optymalny czas na zabezpieczenie pliku logu w razie wystąpienia błędów, a jednocześnie zabezpiecza system przed zużywaniem nadmiernej ilości przestrzeni dyskowej.

Przykładowy wpis z logu:
```
INFO  2017-02-26 20:54:09,148 http-nio-13000-exec-1          p.k.n.e.d.a.LoginService       logged with session <5F145366E31FE8EDBA1BCFA0797A1AA0> authToken <BSVDud4n_4KGyyUO8BRZWZ> response <[000] U&#380;ytkownik zosta&#322; prawid&#322;owo zalogowany.>
```
mówi o tym że wystąpiło zdarzenie "informacyjne" (inne typy to np. ERROR, WARN(ING)), miało miejsce 26 lutego 2017 roku o godzinie 20:54:09 i 148 milisekund. Wątek w którym rekord został zalogowany to "http-nio-13000-exec-1", klasa z której pochodzi wpis to "p.k.n.e.d.a.LoginService" gdzie pojedyncze litery to skrót identyfikujący pakiet (nic czym musicie się przejmować;) ). Wiadomość tego logu mówi: zalogowano poprawnie, wynik logowanie to ID sesji "5F145366E31FE8EDBA1BCFA0797A1AA0" ID tokenu autentykacyjnego "BSVDud4n_4KGyyUO8BRZWZ" zaś serwer NFZ dodał od siebie informacje o treści: "[000] U&#380;ytkownik zosta&#322; prawid&#322;owo zalogowany."

Konieczna jest tylko 1 zmiana w pliku **_logback.xml_**, dokładnie w linijce nr 4:
```
    <property name="DESTINATION_FOLDER" value="TUTAJ_FOLDER_Z_APLIKACJA/logs" />
```
parametr value trzeba ustawić tak aby wskazywał na folder w którym będą umieszczane pliki z logami. Sugeruję przechowywać logi w załączonym folderze **_logs/_** jednakże nie jest to konieczne. Przykładowa poprawna wartość dla linijki nr 4 to _"`<property name="DESTINATION_FOLDER" value="C:/zarnow-ewus/logs" />`"_ **UWAGA**: na końcu nie dajemy znaku "/" gdyż jest on doklejany automatycznie w konfiguracji.

### 5. Uruchomienie aplikacji ### {#app-run}
Aby uruchomić aplikację odpalamy linię komend **cmd**, zmieniamy folder na ten w którym znajduje się nasza aplikacja czyli plik **_zarnow-ewus-1.0.0.jar_**, następnie wprowadzamy komendę:

```
java -jar zarnow-ewus-1.0.0.jar
```
Uruchomienie aplikacji zajmuje u mnie ok. 10 sekund. Jeżeli ostatni wpis zawiera przybliżony komunikat: _"Started Application in 10.82 seconds (JVM running for 11.906)"_ to znaczy że aplikacja uruchomiła się poprawnie. W przeciwnym wypadku można odczytać przyczynę błędu z konsoli lub pliku z logiem.

**Opcjonalnie** dla wygody można opakować to zapytanie w wykonywalny skrypt ".bat" którego kod mógłby wyglądać następująco:

```
java -jar ewus-0.1.0.jar
pause
```

zakładamy oczywiście że plik ze skryptem znajduje się w tym samym folderze co aplikacja. Instrukcja _pause_ ma na celu zatrzymanie utrzymanie aktywnej linii
komend w sytuacji gdyby start aplikacji się nie powiódł - w przeciwnym wypadku konsola natychmiast się zamyka. Taki skrypt można już łatwo dodać aby wykonywał
się automatycznie przy starcie systemu.

**Alternatywnie** moglibyśmy skonfigurować tak aby działała i była monitorowana przez system Windows jako usługa/proces/serwis. Na ten moment tego nie opisuję
bo do tego celu trzeba by zainstalować prosty manager nssm i zarejestrować aplikację jako serwis z linii komend. Da się to zrobić ponieważ robiliśmy tak w
pracy. Zaletą takiego rozwiązania jest to że Windows może zarządzać takim procesem czyli:

- startować automatycznie na starcie systemu,
- można go łatwo zatrzymać jak i wznowić,
- w razie błędu otrzymamy stosowny komunikat.

### 6. Cel aplikacji ### {#business-purpose}
Celem aplikacji jest sprawdzenie statusu ubezpieczenia pacjenta w systemie eWUŚ. Można to zrobić dla pojedynczego numeru PESEL lub dla wszystkich pacjentów znajdujących się w bazie _"Bazap"_.

### 7. Sprawdzenie statusu ubezpieczenia 1 pacjenta ###
Sprawdzenie statusu ubezpieczenia pojedynczego pacjenta jest możliwe wykonując żądanie HTTP do wystawionego przez aplikację REST API. Najprostszą opcją jest wykorzystanie do tego celu przeglądarki.

Aplikacja wystawia do tego celu endpoint dla metody HTTP GET _"/ewus/check?pesel=TUTAJ_NUMER_PESEL"_ zakładając że aplikacja pracuje na domyślnym porcie 13000 wprowadzenie w adresie przeglądarki:
```
http://localhost:13000/ewus/check?pesel=WSTAW_SWOJ_NR_PESEL
```

spowoduje wyświetlenie przybliżonego wyniku w formacie JSON:
{"status":"PERSON_WITH_PESEL_EXISTS","pesel":"WSTAW_SWOJ_NR_PESEL","imie":"IMIE","nazwisko":"NAZWISKO,"ubezpieczony":true,"oznaczenieRecept":null}

Jeżeli chcemy uzyskać ładnie sformatowany wynik możemy przekopiować treść wyniku [tutaj](https://jsonformatter.curiousconcept.com/), po kliknięciu przycisku "
process" uzyskamy bardziej czytelny wynik:
{
"status":"PERSON_WITH_PESEL_EXISTS",
"pesel":"WSTAW_SWOJ_NR_PESEL",
"imie":"IMIE",
"nazwisko":"NAZWISKO",
"ubezpieczony":true,
"oznaczenieRecept":null }

Jak należy czytać powyższy wynik:

- **status** zawiera informacje czy w systemach NFZ osoba o podanym PESEL istnieje. Możliwe wartości to _"PERSON_WITH_PESEL_DOES_NOT_EXISTS"_, gdy wskazana osoba nie istnieje,  _"PERSON_WITH_PESEL_EXISTS"_ gdy osoba o podanym pesel istnieje, _"PERSON_EXISTS_BUT_PESEL_IS_CANCELED"_ gdy numer PESEL został anulowany przez NFZ.
- **pesel** tu będzie powtórzony numer pesel dla którego zostało wykonane sprawdzenie,
- **imie** tu będzie imię osoby do której należy numer PESEL lub _null_ gdy osoba nie istnieje,
- **nazwisko** tu będzie nazwisko osoby do której należy numer PESEL lub _null_ gdy osoba nie istnieje,
- **ubezpieczony** posiada wartość _boolean_ i będzie ustawiona na _true_ tylko gdy eWUŚ zwróci że osoba jest ubezpieczona. W przeciwnym wypadku będzie _false_ - również gdy osoba nie istnieje wartość będzie równa _false_,
- **oznaczenieRecept** jest to opcjonalna zmienna typu String - jeżeli system eWUŚ zwróci że pacjent ma dodatkowe oznaczenie na recepcie to stosowna wartość będzie tu podana, w przeciwnym wypadku będzie _null_. Na ten moment dokumentacja eWUŚ-a mówi że jedyną dopuszczalną wartścią jest _"DN"_.

**UWAGA** na ten moment gdyby sprawdzenie się nie powiodło bo np. serwer eWUŚ był niedostępny lub logowanie się nie powiodło endpoint będzie zwracał błąd i status HTTP 500 Internal Server Error, jest to brzydkie rozwiązanie i należałoby to poprawić, jednakże na ten moment nie wiedząc jakie będą potrzeby nic konkretnego w związku z tym nie robiłem.

Dodatkowo jeżeli w konfiguracji włączony jest zapis statusu do pliku w folderze z wynikami w folderze reprezentującym bieżący dzień np. _"2017-02-26"_ pojawi się plik XML z wynikiem zwróconym z systemu eWUŚ. Nazwa pliku będzie składać się z daty dokładnej co do sekundy oraz numeru PESEL który był sprawdzany np. _"2017-02-26-20-54-09_87080203679.xml"_
**Plik ten można zaimportować do programu mMedica.**

Na szybko tylko spojrzałem w Google jak z programu NZOZ w C++ można by wykonać stosowne zapytania i następujące strony wyglądają interesująco:
http://stackoverflow.com/questions/1011339/how-do-you-make-a-http-request-with-c
https://www.example-code.com/cpp/http_post_json.asp
https://github.com/mrtazz/restclient-cpp
w razie czego możemy na ten temat pogadać.

### 8. Sprawdzenie statusu ubezpieczenia wszystkich pacjentów ### {#business-check-all-patients}
**UWAGA** wg zaleceń NFZ hurtowe sprawdzanie jest dopuszczalne tylko w godzinach nocnych - można się spodziewać że w razie wykonywania tej operacji w godzinach pracy wielu przychodzi, NFZ zacznie blokować nam dostęp do swoich serwerów.

Wywołanie tej funkcjonalności powoduje następujące efekty:

- jeżeli włączona jest opcja zapisu wyniku sprawdzenia do pliku to podobnie jak w przypadku pojedynczego sprawdzenia dla każdego numeru PESEL w folderze z
  wynikami i dnia sprawdzenia pojawi się stosowny plik.
- po każdym sprawdzeniu zostanie zapisany plik z raportem informującym o tym:
    - ile numerów PESEL znaleziono,
    - ile zapytań o sprawdzenie statusu ubezpieczenia wysłano do eWUŚ-a,
    - ile oraz wyszczególnione numery PESEL dla których sprawdzenie się nie powiodło,
    - ile oraz wyszczególnione numery PESEL które eWUŚ określił jako nie ubezpieczone.

Sprawdzenie statusu ubezpieczenia wszystkich pacjentów można wywołać 2 sposobami.

1. Automatycznie przez aplikację. Jeżeli aplikacja jest włączona, może ona samodzielnie wywoływać tą funkcjonalność zgodnie z harmonogramem ustawionym w konfiguracji.
2. Podobnie jak w przypadku sprawdzenia statusu pojedynczego numeru pesel przez żądanie HTTP.  Wywołanie w przeglądarce następującego adresu URL:
```
http://localhost:13000/ewus/check/all
```
spowoduje sprawdzenie wszystkich numerów PESEL. **UWAGA** podczas testów stwierdziłem że sprawdzenie 10.000 pacjentów zajmuje ok. 20 minut. Tyle też czasu trzeba byłoby czekać w przeglądarce na odpowiedź - istnieje też ryzyko że przeglądarka automatycznie przerwałaby oczekiwanie (timeout) nie dostając zbyt długo odpowiedzi.

**UWAGA** pracując ze starszą kopią bazy zauważyłem kilka wartości PESEL dla których sprawdzenie nie jest możliwe lub nie ma sensu dlatego wprowadziłem kilka filtrów:

- choć to raczej błąd to jeżeli wystąpi kilku pacjentów o tym samym numerze PESEL to i tak PESEL będzie sprawdzany tylko 1 raz,
- jeżeli w bazie w kolumnie PESEL będzie NULL lub pusty String zostanie taka wartość pominięta,
- jeżeli w bazie w kolumnie PESEL długość będzie inna niż 11 znaków to taka wartość zostanie pominięta,
- jeżeli numer PESEL jest dla małego dziecka czyli 00000000000 to taka wartość zostanie pominięta,
- jeżeli numer PESEL kończy się na 00000 czyli gdy znana jest tylko data urodzenia to taka wartość zostanie pominięta.

Powinien pojawić się jeszcze 1 filtr który musimy ustalić jak mogę go zrobić, mianowicie osoby które są oznaczone jako zgon - w sensie jak mogę rozpoznać w bazie że pacjent zmarł?

### 9. Jak pracować z aplikacją ### {#how-to-work-with-app}
Na ten moment sugeruję zainstalowanie aplikacji oraz skonfigurowanie. Następnie uruchomienie któregoś wieczora i wykonanie test porównawczego - aplikacji oraz mMedica. Możliwe że pojawią się jakieś problemy które należałoby najpierw naprawić.
Na ten moment nie widzę więcej problemów, jednakże doświadczenie podpowiada mi że mogą być potrzebne pierwsze próby rozruchu z błędami zanim dojdziemy do stabilnej wersji.

Dalsze użytkowanie widzę na 2 sposoby:

1. Pozwolić aplikacji działać non-stop i uruchamiać się każdego dnia od poniedziałku do piątku zgodnie z domyślnym harmonogramem. Od czasu do czasu usunąć foldery z wynikami sprawdzenia z dni które nie będą interesujące, aby uniknąć zbędnego zużywania przestrzeni dyskowej.
2. Uruchamiać aplikację tylko wtedy kiedy chcemy rzeczywiście wykonać sprawdzenie. Np. chcąc aby sprawdzenie zostało wykonane w piątek 00:30:00 uruchomić aplikację już w poprzedzający czwartek np. o 21:00.
3. System Windows ma wbudowaną obsługę harmonogramów - można by je ręcznie skonfigurować aby uruchamiał plik ze skryptem _.bat_ uruchamiającym aplikację wtedy kiedy chcemy.

Następnego dnia wyciągnąć z raportu listę numerów PESEL zidentyfikowanych jako nie ubezpieczonych pacjentów. Tą listę przekopiować do Excela tak jak do tej pory
czy też inaczej, czy też wprowadzić do mMedica lub ProgramNZOZ.

### 10. Możliwości rozszerzenia programu ### {#future-development}

1. Tak jak pisałem wcześniej należałoby odfiltrowywać z listy PESEL-i do sprawdzenia osoby oznaczone jako zgon - musimy ustalić jak to zrobić.
2. Zapisywanie wynikowej listy osób rozpoznanych jako nie posiadających ubezpieczenia do pliku typu CSV lub Excel - jak będę wiedział w jakim formacie miało by
   to być to można to zrobić. Możliwe jest również zapisywanie pozostałych danych.
3. Automatyczne porównywanie z poprzednim wynikiem sprawdzenia, lub zbieranie tego do pliku Excela. Możliwe byłoby odczytywanie pliku Excela oraz jego
   modyfikacja np. dodanie kolumny zawierającej status ubezpieczenia w kolejnym dniu. Jednakże do tego celu efektywniejsze byłoby użycie bazy danych gdzie
   statusy byłby by składowane zaś sama aplikacja (lub odrębna) czytała by zeń dane i generowała plik Excel.
4. Na ten moment gdy stwierdzasz że pacjent powinien złożyć deklarację bo eWUŚ go wyrzuci na koniec miesiąca (na podstawie zmiany statusu ubezpieczenia 1 dnia
   roboczego) wprowadzasz stosowny marker do bazy ProgramNZOZ. Mogłoby to się dziać automatycznie. Jedynie na ten moment nie jestem pewien czy będę w stanie
   zapisywać do bazy _Bazap_. Mianowicie przy odczytywaniu listy numerów PESEL musiałem wyciągnąć wszystkie wpisy i dopiero w nich wyszukiwać zamiast wysłać
   zapytanie do bazy i dostać jedynie interesujące (np. te które są poprawnymi numerami PESEL jak opisane w
   sekcji [sprawdzania wszystkich numerów](#business-check-all-patients)).
5. Mogę zrobić prostą stronę Internetową (w osobnej aplikacji) która łączyła by się z tą aplikacją przez REST API. Ta strona robiła by praktycznie to samo co ta
   dostarczana przez eWUŚ z tą różnicą że nie trzeba byłoby się logować - jedynie byłby pojedynczy input na PESEL i następnie byłby wyświetlany wynik. Jednakże
   byłoby to samo co np. Kasia teraz robi w rejestracji czyli kopiuje PESEL z ProgramNZOZ do mMedica i tam sprawdza. Jedyna różnica byłaby taka że można by
   udostępnić tą stronę w sieci wewnętrznej w Żarnowie bez ograniczenia liczby równocześnie działających instancji tak jak jest w tej chwili. Po odpowiednich
   modyfikacjach możliwe byłoby sprawdzenie kilka PESELI jednocześnie np. do max 10, choć nie wiem czy miałoby to jakikolwiek sens.
