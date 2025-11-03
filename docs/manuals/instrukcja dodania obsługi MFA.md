# Żarnów eWUŚ v1.5.0

### 1. Wymaganie - możliwość logowania do eWUŚ z obsługą MFA

System eWUŚ od 17 listopada 2025 będzie wymagał rozszerzonej autentykacji o MFA.
Na poziomie aplikacji konieczne były zmiany które potrafią wygenerować TOTP i dodać do żądania zalogowania.
Aby rozwiązanie działało w wersji produkcyjnej konieczne są podmiana pliku JAR oraz dodanie kilku properties do konfiguracji.

### 2. Aktualizacj konfiguracji

Podczas instalacji wersji **zarnow-ewus v1.5.0** konieczne jest dodanie wpisów do pliku konfiguracyjnego _application.properties_. Nowe klucze to następująco:

```properties
    ewus.credentials.totp.secret=
ewus.credentials.totp.issuer=
# SHA1 in Java is called HmacSHA1
ewus.credentials.totp.algorithm=HmacSHA1
ewus.credentials.totp.digits=6
ewus.credentials.totp.period=30
```

klucze `ewus.credentials.totp.secret` oraz `ewus.credentials.totp.issuer` należy uzupełnić odpowiednimi wartościami, podczas gdy pozostałe zachować tak jak w
przykładzie.

Dla porządku sugeruję dodać je po linijce z kluczem `ewus.credentials.password`.

Wartości można odczytać z kodu QR i musi to być ten sam kod QR który został w panelu administratora skonfigurowany dla użytkownika podanego
`ewus.credentials.login`.
W przeciwnym wypadku logowanie do eWUŚ będzie zwracało błąd o niepoprawnych danych logowania.

UWAGA: zgodnie z komunikatem ostrzegawczym eWUŚ, istotne jest aby czas na serwerze na którym działa aplikacja był zgodny z czasem serwera.
Przestawienie czasu zimowy/letni czy rozsynchronizowanie się może prowadzić do błędów logowania.
Dlaczego? Ponieważ czas jest składową generowanego TOTP, oraz ma on krótką trwałość od wygenerowania po czym jest nieważny.
