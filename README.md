# Szymon_Durak_PSI_2016

### Projekt: Koncepcja sterowania lub wspomagania sterowania pojazdem wyposa�onym w czujniki odleg�o�ci od przeszk�d terenowych

#### Cel projektu

Wykorzystanie SSN do realizowania r�nych aspekt�w kierowania pojazdem, w tym interpretowania wynik�w otrzymywanych od urz�dze� pomiarowych. Zak�adamy, �e nasz pojazd posiada 
czujniki odleg�o�ci zdolne wykrywa� przeszkody terenowe w pewnym promieniu i rozmieszczone s� na rogach wielok�ta foremnego, zwr�cone s� na zewn�trz.


#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a z funkcjami aktywacji unipolarn� i liniow�, jednowarstwowej sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz�cego - reprezentuj�cych odczyty z czujnik�w
Zadaniem perceptronu b�dzie okre�lenie czy zasz�a kolizja z przeszkod� z dowolnej strony - kolizja zachodzi, gdy kt�rykolwiek czujnik wskazuje bardzo ma�� odleg�o�� (wysoka warto�� sygna�u) oraz czy istnieje droga wolna w jakimkolwiek kierunku (niska warto�� sygna�u na dowolnym wyj�ciu).