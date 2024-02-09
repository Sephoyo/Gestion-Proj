/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package action.data;

import static action.data.DateDefinExtraction.getCurrentDateAsString;
import design.Edit;
import gestionproj.fenetreprincipal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joseph
 */
public class Csv {

    private fenetreprincipal frame;
    private String filePath;
    private String filePathAll;
    private String File;

    public Csv(fenetreprincipal Frame) {
        this.frame = Frame;
        this.filePath = System.getProperty("user.home")+"/gestionProjet/gestion.csv";
        this.filePathAll = System.getProperty("user.home")+"/gestionProjet/AllProjects.csv";
        this.File = System.getProperty("user.home")+"/gestionProjet/ProjetCSV/";

    }

    public void appendLineToCSV(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending line to CSV file: " + e.getMessage());
        }
        frame.repaint();
        frame.revalidate();
    }

    public void deleteLineModifDossier(int lineIndexToDelete) {
        String deletedLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); FileWriter writer = new FileWriter(filePath + ".tmp")) {

            String line;
            int currentLineIndex = 0;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 0) {
                    String idInCurrentLine = columns[0];
                    if (idInCurrentLine.equals(Integer.toString(lineIndexToDelete))) {
                        deletedLine = line;
                        System.out.println("Deleted line: " + deletedLine);
                    } else {
                        writer.write(line);
                        writer.write(System.lineSeparator());
                    }
                } else {
                    System.out.println("La ligne ne contient pas assez d'éléments.");
                }
                currentLineIndex++;
            }

            writer.flush();
            System.out.println("Line deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error reading or writing the file: " + e.getMessage());
        }
        Path originalPath = Paths.get(filePath);
        Path tempPath = Paths.get(filePath + ".tmp");

        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed successfully.");
        } catch (IOException e) {
            System.err.println("Error renaming the file: " + e.getMessage());
        }
        if (deletedLine != null) {
            appendLineToCSV(filePathAll, deletedLine);
        }

        frame.repaint();
        frame.revalidate();
    }

    public void deleteLineFromCsv(int lineIndexToDelete) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); FileWriter writer = new FileWriter(filePath + ".tmp")) {
            String line;
            int currentLineIndex = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLineIndex != lineIndexToDelete) {
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }
                currentLineIndex++;
            }
            writer.flush();
            System.out.println("Line deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error reading or writing the file: " + e.getMessage());
        }
        // Renommer le fichier temporaire au fichier d'origine 
        Path originalPath = Paths.get(filePath);
        Path tempPath = Paths.get(filePath + ".tmp");
        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed successfully.");
        } catch (IOException e) {
            System.err.println("Error renaming the file: " + e.getMessage());
        }
        frame.repaint();
        frame.revalidate();
    }

    public void deleteLineFromCsvTot(int lineIndexToDelete) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathAll)); FileWriter writer = new FileWriter(filePathAll + ".tmp")) {
            String line;
            int currentLineIndex = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLineIndex != lineIndexToDelete) {
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }
                currentLineIndex++;
            }
            writer.flush();
            System.out.println("Line deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error reading or writing the file: " + e.getMessage());
        }
        // Renommer le fichier temporaire au fichier d'origine 
        Path originalPath = Paths.get(filePathAll);
        Path tempPath = Paths.get(filePathAll + ".tmp");
        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed successfully.");
        } catch (IOException e) {
            System.err.println("Error renaming the file: " + e.getMessage());
        }
        frame.repaint();
        frame.revalidate();
    }

    //Supprimer une ligne du total + le fichier correspondant à celui-ci
    public void deleteLineFromCsvTotal(int lineIndexToDelete) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathAll)); FileWriter writer = new FileWriter(filePathAll + ".tmp")) {
            String line;
            int currentLineIndex = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLineIndex != lineIndexToDelete) {
                    writer.write(line);
                    System.out.println(line);
                    writer.write(System.lineSeparator());
                } else {
                    String[] columns = line.split(",");
                    if (columns.length > 0) {
                        String id = columns[0];
                        System.out.println("ID to be deleted: " + id);
                        // Suppression du fichier en dehors de la condition else
                        File file = new File("/Users/joseph/Desktop/GestionProj/src/gestionproj/ProjetCSV/" + id + ".csv");
                        if (file.delete()) {
                            System.out.println(file);
                            System.out.println(file.getName() + " est supprimé.");
                        } else {
                            System.out.println(file);
                            System.out.println("Suppression échouée");
                        }
                    }
                }
                currentLineIndex++;
            }
            writer.flush();
            System.out.println("Line deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error reading or writing the file: " + e.getMessage());
        }

        // Renommer le fichier temporaire au fichier d'origine 
        Path originalPath = Paths.get(filePathAll);
        Path tempPath = Paths.get(filePathAll + ".tmp");

        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed successfully.");
        } catch (IOException e) {
            System.err.println("Error renaming the file: " + e.getMessage());
        }
        frame.repaint();
        frame.revalidate();
    }

    private void IdEdit(int row, String filePath, int a) {
        String id = "";
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                int currentRow = 0;

                // Parcourir le fichier CSV
                while ((line = br.readLine()) != null) {
                    // Vérifier si c'est la ligne recherchée
                    if (currentRow == row + 1) {
                        // Diviser la ligne en colonnes (supposant une virgule comme séparateur)
                        String[] columns = line.split(",");
                        // Assurez-vous que la ligne a suffisamment d'éléments
                        if (columns.length > 0) {
                            // Récupérer l'ID à partir de la première colonne (index 0)
                            id = columns[0];
                            System.out.println("Edit row : " + (row + 1) + ", ID : " + id);
                        } else {
                            System.out.println("La ligne ne contient pas assez d'éléments.");
                        }
                        break; // Sortir de la boucle une fois que la ligne est trouvée
                    }

                    currentRow++; // Déplacer cette ligne après la vérification
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        edit(id, row, filePath, a);
        frame.repaint();
        frame.revalidate();
    }

    private void edit(String id, int row, String filePath, int a) {
        frame.close();
        Edit edit = new Edit(frame, id, row, filePath, a);
        edit.setVisible(true);
    }

    public void updateCsv(String filePath, int lineIndexToUpdate, String newLine) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); FileWriter writer = new FileWriter(filePath + ".tmp")) {

            String line;
            int currentLineIndex = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLineIndex == lineIndexToUpdate) {
                    // Ajouter la nouvelle ligne à la place de la ligne supprimée
                    writer.write(newLine);
                    writer.write(System.lineSeparator());
                } else {
                    // Copier les lignes existantes sauf celle qui est supprimée
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }
                currentLineIndex++;
            }

            writer.flush();
            System.out.println("Update successful.");

        } catch (IOException e) {
            System.err.println("Error reading or writing the file: " + e.getMessage());
        }

        // Renommer le fichier temporaire au fichier d'origine
        Path originalPath = Paths.get(filePath);
        Path tempPath = Paths.get(filePath + ".tmp");

        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed successfully.");
        } catch (IOException e) {
            System.err.println("Error renaming the file: " + e.getMessage());
        }
        frame.repaint();
        frame.revalidate();
    }

    public void UpCsvFichier(String id) {
        String currentDate = getCurrentDateAsString("dd-MM-yyyy");
        File file = new File(File+id+".csv");
        System.out.println(file);
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lines.add(line);
                lineNumber++;
            }

            if (lineNumber >= 2) {
                // Modifier le dernier élément de la deuxième ligne
                String[] data = lines.get(1).split(",");
                if (data.length > 0) {
                    data[data.length - 1] = currentDate;
                    lines.set(1, String.join(",", data));
                }
                // Réécrire tout le contenu du fichier avec les modifications
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (String updatedLine : lines) {
                        writer.write(updatedLine);
                        writer.newLine();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Csv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Csv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void findId(int row){
        String id = "";
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                int currentRow = 0;

                // Parcourir le fichier CSV
                while ((line = br.readLine()) != null) {
                    // Vérifier si c'est la ligne recherchée
                    if (currentRow == row + 1) {
                        // Diviser la ligne en colonnes (supposant une virgule comme séparateur)
                        String[] columns = line.split(",");
                        // Assurez-vous que la ligne a suffisamment d'éléments
                        if (columns.length > 0) {
                            // Récupérer l'ID à partir de la première colonne (index 0)
                            id = columns[0];
                            System.out.println("Edit row : " + (row + 1) + ", ID : " + id);
                        } else {
                            System.out.println("La ligne ne contient pas assez d'éléments.");
                        }
                        break; // Sortir de la boucle une fois que la ligne est trouvée
                    }

                    currentRow++; // Déplacer cette ligne après la vérification
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpCsvFichier(id);
    }
}
