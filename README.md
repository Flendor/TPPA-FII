Descriere Laborator 5 TPPA

În aplicația mea, Options Menu are 3 iteme: "Options", "Save Product Data Locally" și "Help".

"Options" deschide o nouă activitate, în care pornește un fragment ce extinde PreferenceFragmentCompat. 
Am setat un OnSharedPreferenceChangeListener pentru preferințele din PreferenceScreen, în care am scris informația în alte SharedPreferences de la 'com.tppa.sharedpreferences', accesibile din toată aplicația. Setarea pentru care am optat este culoarea background-ului, care poate fi verde, roșu, galben sau alb. Celelalte activități o să seteze culoarea la fiecare onCreate() și vor avea și un OnSharedPreferenceChangeListener setat pentru preferințele din 'com.tppa.sharedpreferences', care va schimba culoarea corespunzător. Fragmentul în care sunt afișate detaliile despre produs își va schimba culoarea în onActivityCreated() și în onResume().

"Save Product Data Locally" apelează o funcție care inserează toate produsele în baza de date SQLite, numită "Grocery Store", în tabelul "FOOD" cu coloanele "name", "description", "price". Tabelul este șters înainte de operațiile de insert pentru o scriere de la 0. După inserare, am afișat în Log-uri produsele pentru a verifica scrierea în baza de date.
