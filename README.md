# Szymon_Durak_PSI_2016

### Projekt: Przewidywanie przysz³ego po³o¿enia poruszaj¹cego siê obiektu na podstawie znanych wczeœniejszych po³o¿eñ z wykorzystaniem sieci neuronowych

#### Cel projektu

Wykorzystanie ró¿nego rodzaju sieci neuronowych do wyznaczania wyników dwóch funkcji logicznych dzia³aj¹cych w logice rozmytej, symbolizuj¹cych pewn¹ strategiê sterowania pojazdem. Na wejœciu sieæ bêdzie otrzymywa³a 8 parametrów o wartoœciach z przedzia³u <0,1>, stanowi¹cych parametry funkcji. Dwa wyjœcia bêd¹ odpowiada³y wynikom dwóch funkcji.
Przyjmujemy nastêpuj¹ce znaczenie operacji:
AND(a,b) -> a*b
OR(a,b) -> a+b>1?1:a+b
XOR(a,b) -> abs(a-b)
EQ(a,b) -> (1-XOR(a,b))
NOT(a) -> 1-a
zmienne wejœciowe oznaczamy:
a,b,c,d,e,f,g,h
wyjœcia oznaczamy:
out1,out2
funkcje testowe to:
out1=AND(OR(OR(EQ(AND(a,0.7),b),OR(c,NOT(d)))),OR(NOT(OR(f,AND(e,0.5),OR(g,AND(h,0.5))
out2=AND(OR(OR(EQ(AND(b,0.7),a),OR(d,NOT(c)))),OR(NOT(OR(e,AND(f,0.5),OR(h,AND(g,0.5))


#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a, jednowarstwowej sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz¹cego. Naszym podproblemem bêdzie sytuacja, gdy zmienne cfr,cfl,cbr,cbl (reprezentuj¹ce czujniki koloru pod³o¿a) przyjm¹ wartoœæ "0", zaœ ofr,ofl,or,ol (reprezentuj¹ce czujniki odleg³oœci od przeciwnika - dwa z przodu i po jednym po bokach) bêd¹ dowolne.
Do uczenia wykorzystamy regu³ê Widrowa-Hoffa.
2. Implementacja neuronu z sigmoidaln¹ funkcj¹ aktywacji i uczenie regu³¹ delta, wykorzystanie w uczeniu sieci z pojedyncz¹ warstw¹ ukryt¹.
3. Implementacja dodatkowych funkcji aktywacji, konfigurowalna sieæ neuronowa
4. Algorytm propagacji wstecznej - implementacja
5. Uczenie sieci neuronowej rekurencyjnej - implementacja sieci rekurencyjnej
6. Uczenie sieci neuronowej za pomoc¹ algorytmu genetycznego - implementacja algorytmu genetycznego
7. Uczenie sieci neuronowej za pomoc¹ algorytmu genetycznego - implementacja sieci wykorzystuj¹cej uczenie algorytmem genetycznym
8. Sieæ Kohonena - implementacja prostej sieci samoorganizuj¹cej
9. Uczenie sieci bez nadzoru - implementacja prostego symulatora dostarczaj¹cego dane wejœciowe
10. Uczenie sieci bez nadzoru - implementacja algorytmu uczenia sieci wykorzystuj¹cej symulator 