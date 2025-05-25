# Integracja Allegro i SubiektGT - zamówienia, faktury sprzedaży, paragony i produkty

Repozytorium dotyczy aplikacji desktopowej umożliwiającej prostą integrację Allegro z Subiekt GT. Integracja dotyczy zamówień po stronie Allegro oraz paragonów i faktur sprzedaży po stronie Subiekt GT oraz analogicznie ofert użytkownika - towarów. Ze względów bezpieczeństwa aplikacja ta operuje na środowisku testowym `Allegro Sandbox`, ale również mam wersję aplikacji przeznaczoną dla standardowego środowiska Allegro (produkcyjnego), jednak jest ona niepubliczna.

## Krótki opis działania aplikacji

Aplikacja łączy się z Allegro w imieniu konta Allegro posiadanego przez użytkownika aplikacji integrator. Jednak aplikacja będzie połączona z kontem z Allegro jedynie wtedy, gdy użytkownik na to zezwoli. Dodatkowo użytkownik będzie mógł się zapoznać z uprawnieniami, jakie uzyska aplikacja integrator.

Użytkownik może w aplikacji wybrać zakresy danych, które chce załadować do Subiekt GT. Następnie dane te zostaną zapisane w plikach o odpowiednich formatach akceptowanych przez Subiekt GT (.epp dla paragonów, towarów i usług oraz xml dla faktur sprzedaży). W Subiekt GT można następnie importować dane poprzez przykładowo skorzystanie z opcji `Operacje -> Dodaj na podstawie` po wcześniejszym przejściu do odpowiedniej sekcji np. z towarami.

Dane aplikacji potrzebne do połączenia z Allegro (`secret`) znajdują się w osobnym pliku i są zaszyfrowane dwukrotnie (w tym raz hasłem podawanym w aplikacji za pierwszym logowaniem) i po pierwszym uruchomieniu aplikacji są usuwane. Dane te bedą później przechowywane w menedżerze poświadczeń. Podobnie bedą przechowywane dane otrzymane po połączeniu z Allegro (`access token` i `refresh token`). Zaimplementowano odświeżanie tokenów dostępu po ich wygaśnięciu.

Aplikacja została w znacznej części przetestowana. Jednak najmniej przetestowane zostały sytuacje wyjątkowe np. gdy klientem jest ktoś z innego Państwa.

## Technologie

* Java,
* Swing,
* Maven,
* JSON,
* XML,
* REST,
* OAuth 2.0,
* Git.

## Krótki poradnik

Aplikacja powinna być pierwszy raz uruchomiona razem z plikiem `auth-data`. Po pierwszym zalogowaniu plik ten zostanie usunięty. Aplikacja jest napisana w Javie, zatem aby ją uruchomić potrzebne będzie zainstalowanie `Javy (JRE)`. Aplikację można uruchomić jak standardowy program na komputerze poprzez dwukrotne kliknięcie.

Po uruchomieniu aplikacji powinno się pojawić takie okno:
<p align="center">
    <img src="screenshoty/logowanie-1.png">
<p>


### Logowanie do aplikacji

Pierwszym krokiem jest logowanie do aplikacji:
<p align="center">
    <img src="screenshoty/logowanie-2.png">
<p>

Dla Allegro Sandbox hasłem jest `integracja-12234`.

Po zalogowaniu:
<p align="center">
    <img src="screenshoty/logowanie-3.png">
<p>


### Łączenie aplikacji z Allegro

Następnym krokiem jest połączenie aplikacji z Allegro:
<p align="center">
    <img src="screenshoty/logowanie-4.png">
<p>

Po naciśnięciu przycisku `ok` aplikacja przekieruje do strony Allegro. Konieczne jest posiadanie zalogowanego konta Allegro. 

Pierwszym krokiem jest potwierdzenie kodu z aplikacji. Jednak z powodu, że aplikacja automatycznie uzupełniła kod, wystarczy przejście dalej.
<p align="center">
    <img src="screenshoty/logowanie-5.png">
<p>

Następnie użytkownik może zapoznać się z uprawnieniami żądanymi przez aplikację integrator i zdecydować, czy warto zaufać tej aplikacji.
<p align="center">
    <img src="screenshoty/logowanie-6.png">
<p>

Po udanym procesie powinno wyświetlić się takie okno:
<p align="center">
    <img src="screenshoty/logowanie-7.png">
<p>

Ostatnim krokiem jest potwierdzenie zezwolenia uprawnień:
<p align="center">
    <img src="screenshoty/logowanie-8.png">
<p>


## Po zalogowaniu do aplikacji i połączeniu aplikacji z Allegro

Użytkownik może przeglądać swoje zamówienia lub oferty z Allegro oraz zdecydować, którą stronę z danymi chciałby załadować do Subiekt GT. Istotne jest, że aby poprawnie zaimportować paragony albo faktury sprzedaży w Subiekt GT, konieczne jest wcześniejsze istnienie w Subiekt GT towarów i usług, które zostały podane w tych dokumentach. Dodatkowo konieczne jest utworzenie usługi o nazwie dostawa, którą dostarcza aplikacja integrator, standardowo w postaci pliku.

Zatem aby dodać faktury sprzedaży albo paragonów do Subiekt GT, pierwszym krokiem jest dodanie towarów:
<p align="center">
    <img src="screenshoty/produkty.png">
<p>

Po zapisaniu produktów z danej strony w wybranej lokalizacji na dysku, następnym krokiem jest zaimportowanie ich do Subiekt GT. Proponuję zrobienie tego poprzez opcję `Dodaj na podstawie`:
<p align="center">
    <img src="screenshoty/subiekt-towary-1.png">
<p>

Powinno pojawić się podobne okno:
<p align="center">
    <img src="screenshoty/subiekt-towary-2.png">
<p>

Należy tak jak na powyższym screenie wybrać wplik, nacisnąć `Wczytaj`, następnie zaznaczyć, które produkty mają być zapisane i potwierdzić poprzez `Wykonaj`.

Podobnie proponuję postąpić z dostawą.

Przykładowy widok zamówień:
<p align="center">
    <img src="screenshoty/zamowienia.png">
<p>

W tym przypadku również moim zdaniem dobrym pomysłem będzie skorzystanie z opcji `Dodaj na podstawie`, analogicznie dla produktów. Jednak w tym przypadku Subiekt GT umożliwia wcześniejsze sprawdzenie wczytanych danych i dokonanie ewentualnych korekt. Nie udało mi się zaimplementować tworzenia sumy kontrolnej, dlatego prawdopodobnie Subiekt GT będzie wysyłał ostrzeżenie odnośnie niezgodnej sumy kontrolnej. Dodatkowo przy tworzeniu faktur sprzedaży konieczne jest podanie Daty zakończenia dostawy w Subiekcie ręcznie. Zauważyłem też, że po utworzeniu faktury sprzedaży zaimportowanej przez aplikację, trzeba jeszcze raz wybrać klienta, gdyż w przeciwnym razie zostanie wygenerowana faktura z pustym polem w miejscu klienta.



