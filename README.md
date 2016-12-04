# Szymon_Durak_PSI_2016

### Projekt: Koncepcja sterowania lub wspomagania sterowania pojazdem wyposa¿onym w czujniki odleg³oœci od przeszkód terenowych

#### Cel projektu

Wykorzystanie SSN do realizowania ró¿nych aspektów kierowania pojazdem, w tym interpretowania wyników otrzymywanych od urz¹dzeñ pomiarowych. Zak³adamy, ¿e nasz pojazd posiada 
czujniki odleg³oœci zdolne wykrywaæ przeszkody terenowe w pewnym promieniu i rozmieszczone s¹ na rogach wielok¹ta foremnego, zwrócone s¹ na zewn¹trz.


#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a z funkcjami aktywacji unipolarn¹ i liniow¹, jednowarstwowej sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz¹cego - reprezentuj¹cych odczyty z czujników
Zadaniem perceptronu bêdzie okreœlenie czy zasz³a kolizja z przeszkod¹ z dowolnej strony - kolizja zachodzi, gdy którykolwiek czujnik wskazuje bardzo ma³¹ odleg³oœæ (wysoka wartoœæ sygna³u) oraz czy istnieje droga wolna w jakimkolwiek kierunku (niska wartoœæ sygna³u na dowolnym wyjœciu).
Zadaniem sieci jednowarstwowej z liniow¹ funkcj¹ aktywacji bêdzie okreœlenie bezpiecznej strefy wokó³ pojazdu - czyli najmniejszej odleg³oœci do przeszkody

2. Implementacja regu³y delta dla funkcji nieliniowej i algorytmu wstecznej propagacji pozwalaj¹cej wykorzystaæ sieæ wielowarstwow¹. wygenerowanie nowego zbioru ucz¹cego reprezentuj¹ce odczyty z pojazdu znajduj¹cego siê w "tunelu". Sieæ bêdzie mia³a za zadanie okreœliæ k¹t, o jaki powinien siê obróciæ
pojazd (<-1,1>, gdzie +/-1 oznacza +/- 90 stopni) aby byæ ustawionym wzd³u¿ tunelu i móc jechaæ. Tunel zawsze jest prosty i posiada dwa wyloty, jednak mo¿e mieæ nieznacznie ró¿n¹ szerokoœæ.

3. Implementacja sieci ucz¹cych siê wed³ug regu³ Hebba z zapominaniem i bez, z nadzorem i bez, jednowarstwowych i z warstw¹ ukryt¹, a tak¿e sieci dzia³aj¹cych wg. regu³y Oji. Próba sklasyfikowania danych ucz¹cych zgodnie z po¿¹danymi wzorcami - do rozró¿niania tunelu ze œcianami po bokach od œciany tylko z lewej lub z prawej

4. Implementacja sieci samoorganizuj¹cej siê wg regu³y WTA, próba realizacji zadania z p.3