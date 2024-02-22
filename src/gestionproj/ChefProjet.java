package gestionproj;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class ChefProjet {

    // Méthode pour effectuer une recherche et enregistrer les résultats dans un nouveau fichier CSV ID
    public boolean searchAndSaveResultsID(String filePath, String searchTerm, String outputFilePath, fenetreprincipal frame) {
        List<String[]> searchResults = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Ignorer la première ligne (entête du CSV) 
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Vérifier si la ligne a suffisamment de colonnes 
                    String chefNom = parts[0].trim();
                    if (chefNom.equalsIgnoreCase(searchTerm)) {
                        // Ajouter la ligne entière aux résultats de la recherche
                        searchResults.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchResults.size() > 0) {
            // Enregistrer les résultats dans un nouveau fichier CSV
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                // Écrire l'entête
                writer.println("id,projet,Chef de projet,Action"); // Assurez-vous de remplacer par les vrais noms de colonnes
                // Écrire les résultats de la recherche
                for (String[] result : searchResults) {
                    writer.println(String.join(",", result));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                // Écrire l'entête
                writer.println("id,projet,Chef de projet,Action");
                JOptionPane.showMessageDialog(frame, "L'id " + searchTerm + " n'existe pas ! ", "id INVALIDE", JOptionPane.WARNING_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Méthode pour effectuer une recherche et enregistrer les résultats dans un nouveau fichier CSV PROJET
    public boolean searchAndSaveResultsPROJET(String filePath, String searchTerm, String outputFilePath, fenetreprincipal frame) {
        List<String[]> searchResults = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Ignorer la première ligne (entête du CSV) 
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Vérifier si la ligne a suffisamment de colonnes 
                    String chefNom = parts[1].trim();
                    // Séparer les espaces dans le nom du chef de projet
                    String[] chefNomParts = chefNom.toLowerCase().split("\\s+");
                    // Comparer chaque partie avec le terme de recherche
                    for (String part : chefNomParts) {
                        if (part.equals(searchTerm.toLowerCase())) {
                            // Ajouter la ligne entière aux résultats de la recherche
                            searchResults.add(parts);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (searchResults.size() > 0) {
            // Enregistrer les résultats dans un nouveau fichier CSV
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                // Écrire l'entête
                writer.println("id,projet,Chef de projet,Action"); // Assurez-vous de remplacer par les vrais noms de colonnes
                // Écrire les résultats de la recherche
                for (String[] result : searchResults) {
                    writer.println(String.join(",", result));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                // Écrire l'entête
                writer.println("id,projet,Chef de projet,Action");
                JOptionPane.showMessageDialog(frame, "Le nom de projet " + searchTerm + " n'existe pas ! ", "Nom de projet INVALIDE", JOptionPane.WARNING_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    //Méthode pour effectuer une recherche et enrigstrer les résultats dans un nouveau fichier CSV NOM
    public boolean searchAndSaveResultsNOM(String filePath, String searchTerm, String outputFilePath, fenetreprincipal frame) {
        List<String[]> searchResults = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Ignorer la première ligne (entête du CSV) 
            reader.readLine();
            String line;
            System.out.println("Search Term: " + searchTerm);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Vérifier si la ligne a suffisamment de colonnes 
                    String chefNom = parts[2].trim();
                    // Séparer les espaces dans le nom du chef de projet
                    String[] chefNomParts = chefNom.toLowerCase().split("\\s+");
                    // Comparer chaque partie avec le terme de recherche
                    for (String part : chefNomParts) {
                        if (part.equals(searchTerm.toLowerCase())) {
                            // Ajouter la ligne entière aux résultats de la recherche
                            searchResults.add(parts);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchResults.size() > 0) {
            // Enregistrer les résultats dans un nouveau fichier CSV
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                // Écrire l'entête
                writer.println("id,projet,Chef de projet,Action"); // Assurez-vous de remplacer par les vrais noms de colonnes
                // Écrire les résultats de la recherche
                for (String[] result : searchResults) {
                    writer.println(String.join(",", result));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                // Écrire l'entête
                writer.println("id,projet,Chef de projet,Action");
                JOptionPane.showMessageDialog(frame, "Le chef " + searchTerm + " n'existe pas ! ", "Chef INVALIDE", JOptionPane.WARNING_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Méthode pour obtenir les noms uniques des chefs de projets sans doublon
    public static String[] getUniqueChefNoms(String filePath, String filePath2) {
        HashSet<String> uniqueChefNoms = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Ignorer la première ligne (entête du CSV) 
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Vérifier si la ligne a suffisamment de colonnes 
                    uniqueChefNoms.add(parts[2]); // Ajouter le nom du chef de projet 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
            // Ignorer la première ligne (entête du CSV) 
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Vérifier si la ligne a suffisamment de colonnes 
                    uniqueChefNoms.add(parts[2]); // Ajouter le nom du chef de projet 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Comparer les 2 hashset pour ne faire qu'un tableau
        
        // Convertir HashSet en tableau 
        return uniqueChefNoms.toArray(new String[0]);
    }

}