Rezolvarea cerințelor din laboratoarele de până acum se află in proiectul "Lab1".
În acest README va fi explicată rezolvarea ultimului laborator, săptămânal.

Descriere Laborator 6 TPPA

Pentru acest laborator, am mai adăugat două iteme în Options Menu: Sensor Info și Location Info.

Opțiunea "Sensor Info" deschide o nouă activitate care afișeasa în log-uri toate informațiile despre toți senzorii din dispozitiv, iar pe ecran sunt afișate valori înregistrate de 5 dintre acestea: proximitate, gravitatie, presiune, câmp magnetic și gyroscop. În activity, am implementat interfața SensorEventListener și am suprascris funcțiile onAccuracyChanged() - nu am avut nevoie de cod aici - și onSensorChanged() - În funcție de tipul de senzor, am afișat valoarea în textview-ul care trebuie. Am inițializat toți cei 5 senzori si am setat listener pe "this", deoarece am implementat interfața SensorEventListener. La onStop() și onPause() am scos listener-ul, iar la onCreate() și la onResume() l-am setat.

Location "Info" va cere prima dată permisiunile utilizatorului pentru a-i accesa locația prin GPS (ACCESS_FINE_LOCATION). Dacă le are deja, nu vor mai fi cerute. Programul va afișa coordonatele pe ecran (latitudine, longitudine și altitudine). Am setat un LocationListener cu ajutorul funcției requestLocationUpdates(), pe LocationManager. La fel ca la senzori, am scos listener-ul la onStop() și onPause().
