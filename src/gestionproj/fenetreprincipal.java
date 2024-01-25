package gestionproj;

import design.AjoutProj;
import action.cell.TableAcionEvent.TableActionEvent;
import action.cell.TableActionCellEditor;
import action.cell.TableActionCellEditorTotal;
import action.cell.TableActionCellRender;
import action.cell.TableActionCellRenderTotal;
import design.ScrollBarCustom;
import design.View;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joseph.baert
 */
public class fenetreprincipal extends javax.swing.JFrame {

    private String filePath;
    private String filePathAll;
    private String filePathRC;
    private int NbrPA;
    private static String NbrPAS;
    private int NbrPT;
    private static String NbrPTS;
    private int NbrC;
    private static String NbrCS;
    private ChefProjets CP;
    private javax.swing.JCheckBox[] checkBoxArray;
    private ChefProjets Demander = new ChefProjets();

    /**
     * Creates new form fenetreprincipal
     */
    //fermer la fentrer
    public void close() {
        WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }

    //ajouter une ligne au csv
    public void appendLineToCSV(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending line to CSV file: " + e.getMessage());
        }
        repaint();
        revalidate();
    }

    public fenetreprincipal() {
        //Chemin d'accès
        String chemin = System.getProperty("user.dir");
        System.out.println("Le répertoire de travail actuel est : " + chemin);
        this.filePath = chemin+"/src/gestionproj/gestion.csv";
        this.filePathAll = chemin+"/src/gestionproj/AllProjects.csv";
        this.filePathRC = chemin+"/src/gestionproj/ChefProject.csv";
        initComponents();
        populateTable();
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());
        //tableau unique des noms de chef
        //String[] uniqueChefNoms = CP.getUniqueChefNoms("/Users/joseph/Desktop/GestionProj/src/gestionproj/AllProjects.csv",);
        //System.out.println("Noms uniques des chefs de projets : " + Arrays.toString(uniqueChefNoms));
        //Définition du titre et du logo de l'application
        setIconImage(new ImageIcon(getClass().getResource("/gestionproj/logo/logo-icon.png")).getImage());
        this.setTitle("Gestion des projets");

        jTable2.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });

        //Ecouteur d'action sur les checkbox
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxActionPerformed(evt, 0);
            }
        });

        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxActionPerformed(evt, 1);
            }
        });

        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxActionPerformed(evt, 2);
            }
        });
        checkBoxArray = new javax.swing.JCheckBox[]{jCheckBox1, jCheckBox2, jCheckBox3};
        //Ecoute de clique sur le bouton rechercher
        rechercher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });
        //Ecouteur de clique sur les cartes
        carLayout1.card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                populateTable();
                setupCustomTableColumn();
            }
        });

        carLayout1.card3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                populateTableTotal();
                setupCustomTableColumnTotal();
            }
        });
        setupCustomTableColumn();
    }

    //actionner lors du check d'une checkbox
    private void jCheckBoxActionPerformed(java.awt.event.ActionEvent evt, int index) {
        for (int i = 0; i < checkBoxArray.length; i++) {
            if (i != index) {
                checkBoxArray[i].setSelected(false);
            }
        }
    }

    //Button edit delet view table actif
    public void setupCustomTableColumn() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
            }

            @Override
            public void onDelete(int row) {
                if (jTable2.isEditing()) {
                    jTable2.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                Object rowData[] = new Object[model.getColumnCount()];
                String dataAdd = "";
                for (int i = 0; i < model.getColumnCount(); i++) {
                    rowData[i] = model.getValueAt(row, i);
                    dataAdd += "," + rowData[i];
                }
                dataAdd = dataAdd.replaceFirst(",", "");
                int i = JOptionPane.showConfirmDialog(null, "Le projet vas être archiver. Êtes-vous sûr de vouloir continuer?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    ///l'utilisateur a dit oui
                    // Ajouter la ligne au fichier CSV
                    appendLineToCSV(filePathAll, dataAdd);
                    deleteLineFromCsv(filePath, row + 1);
                    model.removeRow(row);
                    NbrPA = NbrPA - 1;
                    NbrPAS = String.valueOf(NbrPA);
                    NbrPT = NbrPT + 1;
                    NbrPTS = String.valueOf(NbrPT);
                    fenetreprincipal.this.carLayout1.card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/dossier.png")), "Nombre de projet total", NbrPTS, "12000"));
                    fenetreprincipal.this.carLayout1.card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/lumiere.png")), "Nombre de projet actif", NbrPAS, "12000"));
                } else {
                    // l'utilisateur a dit non
                }
            }

            @Override
            public void onView(int row) {
                Idview(row, filePath);
            }
        };
        jTable2.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        jTable2.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
    }

    //Button edit view delete mais pour le dossier d'archive
    public void setupCustomTableColumnTotal() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {

            }

            @Override
            public void onDelete(int row) {
                if (jTable2.isEditing()) {
                    jTable2.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                int i = JOptionPane.showConfirmDialog(null, "La suppression est irréversible. Êtes-vous sûr de vouloir continuer?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    ///l'utilisateur a dit oui
                    deleteLineFromCsvTotal(filePathAll, row + 1);
                    model.removeRow(row);
                    NbrPT = NbrPT - 1;
                    NbrPTS = String.valueOf(NbrPT);
                    fenetreprincipal.this.carLayout1.card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/dossier.png")), "Nombre de projet total", NbrPTS, "12000"));
                    fenetreprincipal.this.carLayout1.card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/lumiere.png")), "Nombre de projet actif", NbrPAS, "12000"));
                } else {
                    // l'utilisateur a dit non
                }
            }

            @Override
            public void onView(int row) {
                Idview(row, filePathAll);
            }
        };
        jTable2.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRenderTotal());
        jTable2.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditorTotal(event));
    }

    private void view(String id) {
        close();
        View view = new View(this, id);
        view.setVisible(true);

    }

    private void Idview(int row, String filePath) {
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
        view(id);
    }

    //Population de la table
    public void populateTable() {
        DefaultTableModel model = buildTableModel(filePath);
        DefaultTableModel modeltotal = buildTableModelTotal(filePathAll);
        jTable2.setModel(model);
        //Projets total
        NbrPT = modeltotal.getRowCount();
        NbrPTS = String.valueOf(NbrPT);
        //Projet Actif
        NbrPA = model.getRowCount();
        NbrPAS = String.valueOf(NbrPA);
        //Nom des chefs de projets
        String[] commonChefNoms = CP.getUniqueChefNoms(filePath, filePathAll);
        NbrC = commonChefNoms.length;
        NbrCS = String.valueOf(NbrC);
        fenetreprincipal.carLayout1.card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/chef.png")), "Chef de projet", NbrCS, "12000"));
        fenetreprincipal.carLayout1.card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/lumiere.png")), "Nombre de projet actif", NbrPAS, "12000"));
        fenetreprincipal.carLayout1.card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/dossier.png")), "Nombre de projet total", NbrPTS, "12000"));
    }

    //Population de la table pour tout 
    public void populateTableTotal() {
        DefaultTableModel model = buildTableModel(filePath);
        DefaultTableModel modeltotal = buildTableModelTotal(filePathAll);
        jTable2.setModel(modeltotal);
        //Projets total
        NbrPT = modeltotal.getRowCount();
        NbrPTS = String.valueOf(NbrPT);
        //Projet Actif
        NbrPA = model.getRowCount();
        NbrPAS = String.valueOf(NbrPA);
        //Nom des chefs de projets
        String[] commonChefNoms = CP.getUniqueChefNoms(filePath, filePathAll);
        NbrC = commonChefNoms.length;
        NbrCS = String.valueOf(NbrC);
        fenetreprincipal.carLayout1.card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/chef.png")), "Chef de projet", NbrCS, "12000"));
        fenetreprincipal.carLayout1.card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/lumiere.png")), "Nombre de projet actif", NbrPAS, "12000"));
        fenetreprincipal.carLayout1.card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/dossier.png")), "Nombre de projet total", NbrPTS, "12000"));
    }

    //Population de la table pour une recherche
    public void populateTableR() {
        DefaultTableModel model = buildTableModel(filePath);
        DefaultTableModel modeltotal = buildTableModelTotal(filePathAll);
        DefaultTableModel modelRc = buildTableModelRc(filePathRC);
        jTable2.setModel(modelRc);
        //Projets total
        NbrPT = modeltotal.getRowCount();
        NbrPTS = String.valueOf(NbrPT);
        //Projet Actif
        NbrPA = model.getRowCount();
        NbrPAS = String.valueOf(NbrPA);
        //Nom des chefs de projets
        String[] commonChefNoms = CP.getUniqueChefNoms(filePath, filePathAll);
        NbrC = commonChefNoms.length;
        NbrCS = String.valueOf(NbrC);
        fenetreprincipal.carLayout1.card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/chef.png")), "Chef de projet", NbrCS, "12000"));
        fenetreprincipal.carLayout1.card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/lumiere.png")), "Nombre de projet actif", NbrPAS, "12000"));
        fenetreprincipal.carLayout1.card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/dossier.png")), "Nombre de projet total", NbrPTS, "12000"));
    }

    //Table avec information du csv actif
    private DefaultTableModel buildTableModel(String filePath) {
        DefaultTableModel model = new DefaultTableModel();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Lire la première ligne pour obtenir les noms de colonnes
            String[] headers = reader.readLine().split(",");
            model.setColumnIdentifiers(headers);
            // Lire le reste des lignes
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
                NbrPA = NbrPA + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        NbrPAS = String.valueOf(NbrPA);
        return model;
    }

    //Table avec information du csv total
    private DefaultTableModel buildTableModelTotal(String filePath) {
        DefaultTableModel model = new DefaultTableModel();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Lire la première ligne pour obtenir les noms de colonnes
            String[] headers = reader.readLine().split(",");
            model.setColumnIdentifiers(headers);
            // Lire le reste des lignes
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
                NbrPT = NbrPT + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        NbrPTS = String.valueOf(NbrPT);
        return model;
    }

    //Table avec information du csv chef
    private DefaultTableModel buildTableModelRc(String filePath) {
        DefaultTableModel model = new DefaultTableModel();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Lire la première ligne pour obtenir les noms de colonnes
            String[] headers = reader.readLine().split(",");
            model.setColumnIdentifiers(headers);
            // Lire le reste des lignes
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    //Supprimer une ligne du csv
    public void deleteLineFromCsv(String filePath, int lineIndexToDelete) {
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
        repaint();
        revalidate();
    }

    //Supprimer une ligne du total + le fichier correspondant à celui-ci
    public void deleteLineFromCsvTotal(String filePath, int lineIndexToDelete) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); FileWriter writer = new FileWriter(filePath + ".tmp")) {
            String line;
            int currentLineIndex = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLineIndex != lineIndexToDelete) {
                    writer.write(line);
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
        Path originalPath = Paths.get(filePath);
        Path tempPath = Paths.get(filePath + ".tmp");

        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed successfully.");
        } catch (IOException e) {
            System.err.println("Error renaming the file: " + e.getMessage());
        }

        repaint();

        revalidate();
    }

    //Recherche dans Actif
    private void RechercheActif(String demandeText) {
        if (jCheckBox1.isSelected()) {
            boolean rep = Demander.searchAndSaveResultsID(filePath, demandeText, filePathRC, this);
            if (rep) {
                populateTableR();
                setupCustomTableColumn();
                Demande.setText("");
                jCheckBox1.setSelected(false);
            }
        }
        //Projet
        if (jCheckBox2.isSelected()) {
            boolean rep = Demander.searchAndSaveResultsPROJET(filePath, demandeText, filePathRC, this);
            if (rep) {
                populateTableR();
                setupCustomTableColumn();
                Demande.setText("");
                jCheckBox2.setSelected(false);
            }
        }
        //Chef
        if (jCheckBox3.isSelected()) {
            boolean rep = Demander.searchAndSaveResultsNOM(filePath, demandeText, filePathRC, this);
            if (rep) {
                populateTableR();
                setupCustomTableColumn();
                Demande.setText("");
                jCheckBox3.setSelected(false);
            }
        }
    }

    //Recherche dans l'archive
    private void RechercheArchive(String demandeText) {
        if (jCheckBox1.isSelected()) {
            boolean rep = Demander.searchAndSaveResultsID(filePathAll, demandeText, filePathRC, this);
            if (rep) {
                populateTableR();
                setupCustomTableColumn();
                Demande.setText("");
                jCheckBox1.setSelected(false);
            }
        }
        //Projet
        if (jCheckBox2.isSelected()) {
            boolean rep = Demander.searchAndSaveResultsPROJET(filePathAll, demandeText, filePathRC, this);
            if (rep) {
                populateTableR();
                setupCustomTableColumn();
                Demande.setText("");
                jCheckBox2.setSelected(false);
            }
        }
        //Chef
        if (jCheckBox3.isSelected()) {
            boolean rep = Demander.searchAndSaveResultsNOM(filePathAll, demandeText, filePathRC, this);
            if (rep) {
                populateTableR();
                setupCustomTableColumn();
                Demande.setText("");
                jCheckBox3.setSelected(false);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        carLayout1 = new design.CarLayout();
        jComboBox1 = new javax.swing.JComboBox<>();
        jCheckBox1 = new design.JCheckBoxCustom();
        jCheckBox2 = new design.JCheckBoxCustom();
        jCheckBox3 = new design.JCheckBoxCustom();
        Demande = new design.TextField();
        rechercher = new design.Button();
        ajouts = new design.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTable2.setBackground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null"
            }
        ) {}
    );
    jTable2.setRowHeight(40);
    jScrollPane1.setViewportView(jTable2);
    if (jTable2.getColumnModel().getColumnCount() > 0) {
        jTable2.getColumnModel().getColumn(0).setResizable(false);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    carLayout1.setBackground(new java.awt.Color(255, 255, 255));

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rechercher dans projet actif", "Recherche dans archive" }));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox1ActionPerformed(evt);
        }
    });

    jCheckBox1.setText("id");

    jCheckBox2.setBackground(new java.awt.Color(0, 204, 0));
    jCheckBox2.setText("Projet");

    jCheckBox3.setBackground(new java.awt.Color(255, 153, 0));
    jCheckBox3.setText("Chef");

    Demande.setBackground(new java.awt.Color(255, 255, 255));
    Demande.setShadowColor(new java.awt.Color(255, 0, 0));

    rechercher.setText("Rechercher");
    rechercher.setBorderColor(new java.awt.Color(102, 102, 255));
    rechercher.setBorderPainted(false);
    rechercher.setColorClick(new java.awt.Color(153, 153, 255));
    rechercher.setColorOver(new java.awt.Color(102, 102, 255));
    rechercher.setRadius(17);
    rechercher.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            rechercherActionPerformed(evt);
        }
    });

    ajouts.setText("Nouveau projet");
    ajouts.setBorderColor(new java.awt.Color(255, 255, 255));
    ajouts.setBorderPainted(false);
    ajouts.setColorClick(new java.awt.Color(0, 153, 255));
    ajouts.setColorOver(new java.awt.Color(153, 204, 255));
    ajouts.setRadius(17);
    ajouts.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ajoutsActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(carLayout1, javax.swing.GroupLayout.PREFERRED_SIZE, 1258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jScrollPane1)
                    .addGap(20, 20, 20))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(Demande, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(24, 24, 24)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(rechercher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ajouts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(28, 28, 28))))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(carLayout1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(Demande, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rechercher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(ajouts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        //Afficher la jtable correspondant à la selection dans le combo box
        String selectedOption = (String) jComboBox1.getSelectedItem();
        if (selectedOption == "Rechercher dans projet actif") {
            populateTable();
            setupCustomTableColumn();
        } else if (selectedOption == "Recherche dans archive") {
            populateTableTotal();
            setupCustomTableColumnTotal();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void rechercherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercherActionPerformed
        // TODO add your handling code here:
        //Verifie si le champs est vide et si au moins un checkbox est valide
        String demandeText = Demande.getText().trim();
        if (!jCheckBox1.isSelected() && !jCheckBox2.isSelected() && !jCheckBox3.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vous devez selectionner au moins 1 attributs (id,projet,chef de projet) !", "Attribut non selectionner", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (demandeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs de text vide !", "Champs vide", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //prend le résultat de la combobox
        String selectedOption = (String) jComboBox1.getSelectedItem();
        //Recherche dans l'actif pour l'archive
        if (selectedOption == "Rechercher dans projet actif") {
            RechercheActif(demandeText);
        } else if (selectedOption == "Recherche dans archive") {
            RechercheArchive(demandeText);
        }
    }//GEN-LAST:event_rechercherActionPerformed

    private void ajoutsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajoutsActionPerformed
        // TODO add your handling code here:
        close();
        AjoutProj aj = new AjoutProj(this);
        aj.setVisible(true);
    }//GEN-LAST:event_ajoutsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(fenetreprincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fenetreprincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fenetreprincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fenetreprincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                fenetreprincipal frame = new fenetreprincipal();
                frame.setVisible(true);
                frame.carLayout1.card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/chef.png")), "Chef de projet", NbrCS, "12000"));
                frame.carLayout1.card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/lumiere.png")), "Nombre de projet actif", NbrPAS, "12000"));
                frame.carLayout1.card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/dossier.png")), "Nombre de projet total", NbrPTS, "12000"));
                frame.repaint();
                frame.revalidate();
            }
        }
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private design.TextField Demande;
    private design.Button ajouts;
    private static design.CarLayout carLayout1;
    private design.JCheckBoxCustom jCheckBox1;
    private design.JCheckBoxCustom jCheckBox2;
    private design.JCheckBoxCustom jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    private design.Button rechercher;
    // End of variables declaration//GEN-END:variables
}
