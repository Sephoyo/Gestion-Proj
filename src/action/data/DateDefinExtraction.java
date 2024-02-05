package action.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class DateDefinExtraction {

    public static List<Pair<String, String>> Datedefin = new ArrayList<>();
    public static List<String> Depasser=new ArrayList<>();
    private static String filePath;

    public DateDefinExtraction() {
        String chemin = System.getProperty("user.dir");
        this.filePath = chemin + "/src/gestionproj/gestion.csv";
        String dossierCSV = "/Users/joseph/Desktop/GestionProj/src/gestionproj/ProjetCSV/";

        // Liste des fichiers dans le dossier
        File dossier = new File(dossierCSV);
        File[] fichiersCSV = dossier.listFiles((dir, name) -> name.endsWith(".csv"));

        if (fichiersCSV != null) {
            // Parcours de chaque fichier CSV
            for (File fichierCSV : fichiersCSV) {
                System.out.println("Traitement du fichier : " + fichierCSV.getName());
                extraireDateDeFin(fichierCSV);
            }
        } else {
            System.out.println("Aucun fichier CSV trouvé dans le dossier spécifié.");
        }
    }

    public static void extraireDateDeFin(File fichierCSV) {
        String[] activeIds = TabActifId();
        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            // Lire la première ligne pour obtenir les en-têtes
            String ligne = reader.readLine();
            String[] entetes = ligne.split(",");

            // Vérifier si le fichier CSV contient une colonne "Date de fin" et "ID du projet"
            int indiceDateDeFin = -1;
            int indiceIdProjet = -1;

            for (int i = 0; i < entetes.length; i++) {
                if (entetes[i].equalsIgnoreCase("Date de fin")) {
                    indiceDateDeFin = i;
                } else if (entetes[i].equalsIgnoreCase("id")) {
                    indiceIdProjet = i;
                }
            }

            if (indiceDateDeFin != -1 && indiceIdProjet != -1) {
                // Parcourir les lignes restantes et extraire l'ID du projet et sa date de fin
                while ((ligne = reader.readLine()) != null) {
                    StringTokenizer tokenizer = new StringTokenizer(ligne, ",");
                    int colonneActuelle = 0;
                    String idProjet = "";
                    String dateDeFin = "";

                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();

                        if (colonneActuelle == indiceIdProjet) {
                            idProjet = token;
                        } else if (colonneActuelle == indiceDateDeFin) {
                            dateDeFin = token;
                        }

                        colonneActuelle++;
                    }

                    // Vérifier si la date de fin est présente avant d'ajouter à la liste
                    if (!dateDeFin.isEmpty() && isIdActive(idProjet, activeIds) && isDateFormatValid(dateDeFin)) {
                        // Ajouter la paire ID du projet - Date de fin à la liste
                        Datedefin.add(new Pair<>(idProjet, dateDeFin));
                            checkDatesAndPerformAction();
                    }
                }
            } else {
                System.out.println("Le fichier CSV ne contient pas les colonnes 'Date de fin' ou 'ID du projet'.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkDatesAndPerformAction() {
        // Récupérer la date du jour
        String currentDate = getCurrentDateAsString("dd-MM-yyyy");
        System.out.println("Date du jour : "+currentDate);
        // Parcourir le tableau et comparer les dates
        for (Pair<String, String> pair : Datedefin) {
            String idProjet = pair.getFirst();
            String dateDeFin = pair.getSecond();

            if(!compareDates(currentDate, dateDeFin)){
                Depasser.add(idProjet);
            }
            System.out.println("ID du projet: " + idProjet + ", Date de fin: " + dateDeFin);
        }
    }

    public static boolean compareDates(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);

            // Comparer les dates
            return d1.before(d2);
        } catch (ParseException e) {
            // Gérer les erreurs de format de date
            e.printStackTrace();
            return false;
        }
    }

    public static String getCurrentDateAsString(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date());
    }
    
    public static String[] TabActifId() {
        List<String> idList = new ArrayList<>();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length > 0) {
                        idList.add(columns[0]);
                    } else {
                        System.out.println("La ligne ne contient pas assez d'éléments.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] idArray = idList.toArray(new String[0]);

        return idArray;
    }
    public static boolean isIdActive(String id, String[] activeIds) {
        for (String activeId : activeIds) {
            if (activeId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDateFormatValid(String dateString) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.setLenient(false);

    try {
        sdf.parse(dateString);
        return true;
    } catch (ParseException e) {
        return false;
    }
}


}
