# Szymon_Durak_PSI_2016

### Projekt: Koncepcja sterowania lub wspomagania sterowania pojazdem wyposa�onym w czujniki odleg�o�ci od przeszk�d terenowych

#### Cel projektu

Wykorzystanie SSN do realizowania r�nych aspekt�w kierowania pojazdem, w tym interpretowania wynik�w otrzymywanych od urz�dze� pomiarowych. Zak�adamy, �e nasz pojazd posiada 
czujniki odleg�o�ci zdolne wykrywa� przeszkody terenowe w pewnym promieniu i rozmieszczone s� na rogach wielok�ta foremnego, zwr�cone s� na zewn�trz.


#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a z funkcjami aktywacji unipolarn� i liniow�, jednowarstwowej sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz�cego - reprezentuj�cych odczyty z czujnik�w
Zadaniem perceptronu b�dzie okre�lenie czy zasz�a kolizja z przeszkod� z dowolnej strony - kolizja zachodzi, gdy kt�rykolwiek czujnik wskazuje bardzo ma�� odleg�o�� (wysoka warto�� sygna�u) oraz czy istnieje droga wolna w jakimkolwiek kierunku (niska warto�� sygna�u na dowolnym wyj�ciu).
Zadaniem sieci jednowarstwowej z liniow� funkcj� aktywacji b�dzie okre�lenie bezpiecznej strefy wok� pojazdu - czyli najmniejszej odleg�o�ci do przeszkody

2. Implementacja regu�y delta dla funkcji nieliniowej i algorytmu wstecznej propagacji pozwalaj�cej wykorzysta� sie� wielowarstwow�. wygenerowanie nowego zbioru ucz�cego reprezentuj�ce odczyty z pojazdu znajduj�cego si� w "tunelu". Sie� b�dzie mia�a za zadanie okre�li� k�t, o jaki powinien si� obr�ci�
pojazd (<-1,1>, gdzie +/-1 oznacza +/- 90 stopni) aby by� ustawionym wzd�u� tunelu i m�c jecha�. Tunel zawsze jest prosty i posiada dwa wyloty, jednak mo�e mie� nieznacznie r�n� szeroko��.