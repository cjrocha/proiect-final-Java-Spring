# proiect-final
Comparator produse foloseste java, Spring, Selenium, javascript.

Acest program permite compararea datelor aceluiasi tip produse de la diferiti comercianti in idea in care este necesara compararea preturilor si/sau 
specificatiilor produselor sau update-ul datelor deja existente despre un produs (pret, imagini, descriere, etc.) in baza de date.
Idee:
- Programul va implementa urmatorul comportament:
1. Pagina de pornire(home) cat si celelalte pagini sunt structurate in 3 sectiuni: header + continut + footer. Paginile ‘statice’ 
(termen, despre, acasa, cum functioneaza) pot fi accesate de cater toti utilizatorii.
2. Din pagina de pornire (home) utilizatorul v-a trebui sa se logeze sau sa se inregistreze pentru a putea accesa comparatorul si 
functionalitatile sale.
3. Datele si paginile ulterioare vor fi accesibile doar userilor logati – in cazul in care se incearca accesarea paginilor comparatorului de 
catre un utilizator ne-inregistrat/ne-logat -> sitemul v-a servi utilizatorului o pagina de erroare (cod 401 eventual imbunatatita cu un text 
de ajutor la inregistrare/login).
4. O data inregistrat/logat utilizatorul va fi redirectionat catre pagina ‘contul meu’ unde se vor regasi datele utilizatorului, 
folosite la inregistrare, cat si data/ora currenta.
Din aceasta pagina un utilizator va avea optiunile: ‘logout’ si ‘comparator’ (cat si celalalt continut static oferit the header-ul si 
footer-ul paginilor). 
P.S.: Ma folosesc de imagini si nume ale unui proiect mai vechi pe care n-am apucat sa-l finalizez 😊
5. Din pagina ‘contul meu’ accesand butonul ‘logout’ utilizatorul v-a fi redirectionat catre pagina ‘home’, pierzand toate privilegiile de a
cesare a continutului comparatorului.
6. Din pagina ‘contul meu’ accesand butonul ‘comparator’ utilizatorul v-a fi directional catre pagina ‘main-comparator’.
Aceasta pagina afiseaza un formular de search: un camp de cuvinte cheie + 4 checkboxes ale siteurilor disponibile pentru cautare + butonul ‘cauta’. 
- campul cuvinte cheie foloseste validarea din thymeleaf pentru ‘empty field’, deasemenea acest camp nu accepta semne de punctuatie -> arata 
mesaj de erroare cu rosu in cazul in care se incearca trimiterea de semne de punctuatie.
- partea de checkboxes foloseste si ea validare – cel putin un camp trebuie sa fie activat. In caz contrar arata mesaj cu rosu si formularul 
nu este trimis.
7. O data formularul trimis cu success -> click pe cauta:
Front-end: utilizatorul v-a vedea o noua pagina: ‘filtrare’ care va afisa text de asteptare a datelor pana cand cautarea are loc 
(sper sa fie un lucru care se poate face … in caz contrar voi afisa un text in asteptarea datelor).
Back-end: in functie de cuvinte cheie si site-uri alese se face cautarea in site-urile target, fiecare site pe un thread diferit. 
8. Cand datele sunt primite, utilizatorului i-se oferii variante de filtrare a cautarii, bazate pe primele 4 categorii de produse gasite in 
urma cautarii: (pentru a restrange aria de cautare si nr. produselor).
Aceasta se va realiza prin 4  formulare simple  cu radio buttons in care vor afisate variantele disponibile (4 la numar) – utilizatorul v-a 
alege una din variante pentru fiecare site ales.
9. Fiecare din butoanele acestor formulare va porni cautarea / achizitia si salvarea in baza de date a produselor gasite. Procesul va beneficia 
de multi-threading (fiecare pagina gasita din fiecare site va fi prelucrata de un thread + fiecare url de produs va fi prelucrat de cate un thread). 
Injectarea in baza de date se va face pentru fiecare produs gasit. In baza de date se va adauga si data in care s-a facut inserarea. 
10. In acest moment as vrea sa am un buton(link url) – la finalul aceleaiasi pagini ‘filtrare’ -  
[care sa fie disponibil in momentul in care un process de achizitie s-a terminat (chiar daca celelalte inca ruleaza)] si care sa directioneze 
catre o noua pagina ‘lista-produse’.
11. Pagina (‘lista-produse’) v-a afisa tot continutul actual din baza  de date, trebuie sa aiba paginare (30-50 intrari pe pagina). Informatia va 
afisata in forma tabelara:
Continutul va fi sortabil in functie de titlu, sku, brand, site_sursa.
La final continutul prelucrat va putea fi exportat in format csv.



