# Szymon_Durak_PSI_2016

### Projekt: Koncepcja sterowania lub wspomagania sterowania pojazdem wyposa¿onym w czujniki odleg³oœci od przeszkód terenowych

#### Cel projektu

Wykorzystanie SSN do realizowania ró¿nych aspektów kierowania pojazdem, w tym interpretowania wyników otrzymywanych od urz¹dzeñ pomiarowych. Zak³adamy, ¿e nasz pojazd posiada 
czujniki odleg³oœci zdolne wykrywaæ przeszkody terenowe w pewnym promieniu i rozmieszczone s¹ na rogach wielok¹ta foremnego, zwrócone s¹ na zewn¹trz.


#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a z funkcjami aktywacji unipolarn¹ i liniow¹, jednowarstwowej sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz¹cego - reprezentuj¹cych odczyty z czujników
Zadaniem perceptronu bêdzie okreœlenie czy zasz³a kolizja z przeszkod¹ z dowolnej strony - kolizja zachodzi, gdy którykolwiek czujnik wskazuje bardzo ma³¹ odleg³oœæ (wysoka wartoœæ sygna³u) oraz czy istnieje droga wolna w jakimkolwiek kierunku (niska wartoœæ sygna³u na dowolnym wyjœciu).