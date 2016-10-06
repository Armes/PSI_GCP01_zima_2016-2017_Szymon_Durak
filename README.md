# Szymon_Durak_PSI_2016

### Projekt: Przewidywanie przysz³ego po³o¿enia poruszaj¹cego siê obiektu na podstawie znanych wczeœniejszych po³o¿eñ z wykorzystaniem sieci neuronowych

#### Cel projektu

Wykorzystanie ró¿nego typu sieci neuronowych do predykcji po³o¿enia obiektu poruszaj¹cego siê w przestrzeni 3D (np rzuconej pi³ki, opadaj¹cego pocisku artyleryjskiego, lec¹cego samolotu) na podstawie zebranych danych o jego wczeœniejszym po³o¿eniu. Przyjmujemy, ¿e za dane wejœciowe pos³u¿¹ nam wspó³rzêdne X,Y,Z wyznaczonej liczby punktów zmierzone w sta³ych odstêpach czasu dt, zaœ za dane wyjœciowe przewidywane wspó³rzêdne X,Y,Z po up³ywie czasu dt od ostatniego pomiaru. W przypadku sieci rekurencyjnych, za dane wejœciowe mog¹ dodatkowo pos³u¿yæ wspó³rzêdne z poprzedniej predykcji.

#### Harmonogram prac

1. Implementacja neuronu McCullocha-Pitts'a, sieci neuronowej-perceptronu oraz przygotowanie zestawu ucz¹cego z rekordami w postaci 5 znanych po³o¿eñ obiektu i szóstego, który ma zostaæ przewidziany przez neuron. W przypadku perceptronu, ze wzglêdu na skokowy charakter funkcji aktywacji (wykorzystana zostanie funkcja bipolarna), zadaniem sieci neuronowej bêdzie predykcja jedynie znaków zmian wspó³rzêdnych po³o¿enia w punkcie P6, rozumianej dla wspó³rzêdnej k jako sgn(k6-k5). Dla sgn(k6-k5)=0 przyjmujemy, ¿e funkcja aktywacji powinna przyj¹æ wartoœæ 1.

