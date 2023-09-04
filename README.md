# PlaceReminder

PlaceReminder è un'applicazione che offre una mappa interattiva in cui gli utenti possono inserire e gestire punti di interesse (placemark). L'applicazione fornisce diverse funzionalità per rendere l'esperienza degli utenti intuitiva e completa:


## Schermata iniziale

- L'applicazione si avvia con una schermata principale che include una mappa interattiva.
- Gli utenti possono visualizzare i placemark precedentemente inseriti sulla mappa.
- Ricezione di notifiche quando ci si avvicina entro 500 metri da un placemark.
- Una toolbar in alto presenta tre pulsanti principali:

    1. Pulsante per aggiungere nuovi placemark.
    2. Pulsante per visualizzare la lista dei placemark inseriti.
    3. Pulsante per selezionare e raggiungere un placemark sulla mappa.

## Aggiunta di nuovi placemark

- Il pulsante per aggiungere nuovi placemark apre una schermata dedicata.
- Gli utenti devono compilare campi obbligatori (tranne la descrizione) per aggiungere un nuovo placemark.
- La schermata è divisa in due pagine tramite un ViewPager:
    - Inserimento tramite Indirizzo (con ottenimento delle coordinate tramite Forward Geocoding).
    - Inserimento tramite coordinate (con ottenimento dell'indirizzo tramite Reverse Geocoding).
- Sono presenti pulsanti per salvare e annullare l'aggiunta.

## Visualizzazione dei placemark inseriti

- Il pulsante per visualizzare la lista dei placemark inseriti apre una schermata con una RecyclerView.
- Tutti i placemark sono elencati in ordine di ultima modifica.
- Per ciascun placemark, è possibile modificarlo o rimuoverlo.

### Modifica dei placemark

- Il pulsante per modificare un placemark apre una schermata identica a quella di aggiunta, ma con i campi preimpostati con i dati del placemark da modificare.

### Rimozione dei placemark

- Il pulsante per rimuovere un placemark rimuove il placemark corrente dalla lista.

## Navigazione verso i placemark

- Il pulsante per visualizzare i placemark apre un menu a tendina nella zona superiore destra della mappa.
- Da qui, gli utenti possono selezionare un placemark da raggiungere istantaneamente.


## Requisiti:
- L'applicazione richiede una connessione internet.
- È necessario concedere i permessi per la posizione precisa e la posizione in background.
- Sono richiesti anche i permessi per ricevere notifiche.

L'applicazione PlaceReminder offre una soluzione completa per gestire e esplorare punti di interesse sulla mappa, garantendo una notifica quando ci si trova vicino a un placemark e facilitando la modifica e la rimozione dei dati dei punti di interesse.
