# Szymon_Durak_PSI_2016

### Projekt: Przewidywanie przysz�ego po�o�enia poruszaj�cego si� obiektu na podstawie znanych wcze�niejszych po�o�e� z wykorzystaniem sieci neuronowych

#### Cel projektu

Wykorzystanie r�nego rodzaju sieci neuronowych do wyznaczania wynik�w dw�ch funkcji logicznych dzia�aj�cych w logice rozmytej, symbolizuj�cych pewn� strategi� sterowania pojazdem. Na wej�ciu sie� b�dzie otrzymywa�a 8 parametr�w o warto�ciach z przedzia�u <0,1>, stanowi�cych parametry funkcji. Dwa wyj�cia b�d� odpowiada�y wynikom dw�ch funkcji.
Przyjmujemy nast�puj�ce znaczenie operacji:
AND(a,b) -> a*b
OR(a,b) -> a+b>1?1:a+b
XOR(a,b) -> abs(a-b)
EQ(a,b) -> (1-XOR(a,b))
NOT(a) -> 1-a
zmienne wej�ciowe oznaczamy:
a,b,c,d,e,f,g,h
wyj�cia oznaczamy:
out1,out2
funkcje testowe to:
out1=AND(OR(OR(EQ(AND(a,0.7),b),OR(c,NOT(d)))),OR(NOT(OR(f,AND(e,0.5),OR(g,AND(h,0.5))
out2=AND(OR(OR(EQ(AND(b,0.7),a),OR(d,NOT(c)))),OR(NOT(OR(e,AND(f,0.5),OR(h,AND(g,0.5))


#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a, jednowarstwowej sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz�cego. Naszym podproblemem b�dzie sytuacja, gdy zmienne cfr,cfl,cbr,cbl (reprezentuj�ce czujniki koloru pod�o�a) przyjm� warto�� "0", za� ofr,ofl,or,ol (reprezentuj�ce czujniki odleg�o�ci od przeciwnika - dwa z przodu i po jednym po bokach) b�d� dowolne.
Do uczenia wykorzystamy regu�� Widrowa-Hoffa.
2. Implementacja neuronu z sigmoidaln� funkcj� aktywacji i algorytmu propagacji wstecznej, wykorzystanie w uczeniu sieci z pojedyncz� warstw� ukryt�.