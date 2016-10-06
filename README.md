# Szymon_Durak_PSI_2016

### Projekt: Przewidywanie przysz�ego po�o�enia poruszaj�cego si� obiektu na podstawie znanych wcze�niejszych po�o�e� z wykorzystaniem sieci neuronowych

#### Cel projektu

Wykorzystanie r�nego typu sieci neuronowych do predykcji po�o�enia obiektu poruszaj�cego si� w przestrzeni 3D (np rzuconej pi�ki, opadaj�cego pocisku artyleryjskiego, lec�cego samolotu) na podstawie zebranych danych o jego wcze�niejszym po�o�eniu. Przyjmujemy, �e za dane wej�ciowe pos�u�� nam wsp�rz�dne X,Y,Z wyznaczonej liczby punkt�w zmierzone w sta�ych odst�pach czasu dt, za� za dane wyj�ciowe przewidywane wsp�rz�dne X,Y,Z po up�ywie czasu dt od ostatniego pomiaru. W przypadku sieci rekurencyjnych, za dane wej�ciowe mog� dodatkowo pos�u�y� wsp�rz�dne z poprzedniej predykcji.

#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a, sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz�cego z rekordami w postaci 5 znanych po�o�e� obiektu i sz�stego, kt�ry ma zosta� przewidziany przez neuron. W przypadku perceptronu, ze wzgl�du na skokowy charakter funkcji aktywacji (wykorzystana zostanie funkcja bipolarna), zadaniem sieci neuronowej b�dzie predykcja jedynie znak�w zmian wsp�rz�dnych po�o�enia w punkcie P6, rozumianej dla wsp�rz�dnej k jako sgn(k6-k5). Dla sgn(k6-k5)=0 przyjmujemy, �e funkcja aktywacji powinna przyj�� warto�� 1.

