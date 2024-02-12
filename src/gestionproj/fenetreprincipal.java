package gestionproj;

import design.AjoutProj;
import action.cell.TableAcionEvent.TableActionEvent;
import action.cell.TableActionCellEditor;
import action.cell.TableActionCellEditorTotal;
import action.cell.TableActionCellRender;
import action.cell.TableActionCellRenderTotal;
import action.data.Csv;
import action.data.DateDefinExtraction;
import static action.data.DateDefinExtraction.getCurrentDateAsString;
import design.Edit;
import design.ScrollBarCustom;
import design.View;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author joseph.baert
 */
public class fenetreprincipal extends javax.swing.JFrame {

    private Csv csv = new Csv(this);
    private String filePath;
    private String filePathAll;
    private int NbrPA;
    private static String NbrPAS;
    private int NbrPT;
    private static String NbrPTS;
    private int NbrC;
    private static String NbrCS;
    private ChefProjet CP;
    private javax.swing.JCheckBox[] checkBoxArray;
    private ChefProjet Demander = new ChefProjet();
    private DateDefinExtraction DateFin = new DateDefinExtraction();
    private String currentDate = getCurrentDateAsString("dd-MM-yyyy");

    /**
     * Creates new form fenetreprincipal
     */
    //fermer la fentrer
    public void close() {
        WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }

    public void Review() {

    }

    public fenetreprincipal() {
        //Chemin d'accès
        System.out.println(DateFin.Datedefin);
        String Deplacer = "Les projets : ";
        Set<String> uniqueElements = new HashSet<>();
        System.out.println("Les projets dépasser : " + DateFin.Depasser);
        if (DateFin.Depasser.size() > 0) {
            for (String element : DateFin.Depasser) {
                if (uniqueElements.add(element)) {
                    Deplacer += element + ",";
                    try {
                        int intValue = Integer.parseInt(element);
                        System.out.println("Valuer entré dans la fonction deleteLineModifDossier " + intValue);
                        csv.deleteLineModifDossier(intValue);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing integer: " + e.getMessage());
                    }
                }
            }

            if (!uniqueElements.isEmpty()) {
                Deplacer += "sont déplacés dans les projets archivés car leur date butoire est arrivée.";
                JOptionPane.showMessageDialog(null, Deplacer, "Attention fichier déplacé", JOptionPane.WARNING_MESSAGE);
            }
        }
        this.filePath = System.getProperty("user.home")+"/gestionProjet/gestion.csv";
        this.filePathAll = System.getProperty("user.home")+"/gestionProjet/AllProjects.csv";
        initComponents();
        populateTable();
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());
        //titre et logo de l'application
        setIconImage(new ImageIcon(getClass().getResource("/gestionproj/logo/logo-icon.png")).getImage());
        this.setTitle("Gestion des projets");

        jTable2.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
        //Ecouteur de clique sur les cartes
        carLayout1.card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                populateTable();
                setupCustomTableColumn();
                resetSearch(jTable2, Demande, resultat);
            }
        });

        carLayout1.card3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                populateTableTotal();
                setupCustomTableColumnTotal();
                resetSearch(jTable2, Demande, resultat);
            }
        });
        setupCustomTableColumn();
    }

    //Button edit delet view table actif
    public void setupCustomTableColumn() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                IdEdit(row, filePath, 0);
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
                int i = JOptionPane.showConfirmDialog(null, "Le projet vas être archiver et la date de fin de projet sera la date du jour .\n Êtes-vous sûr de vouloir continuer?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    ///l'utilisateur a dit oui
                    // Ajouter la ligne au fichier CSV
                    csv.findId(row);
                    csv.appendLineToCSV(filePathAll, dataAdd);
                    csv.deleteLineFromCsv(row + 1);
                    model.removeRow(row);
                    NbrPA = NbrPA - 1;
                    NbrPAS = String.valueOf(NbrPA);
                    NbrPT = NbrPT + 1;
                    NbrPTS = String.valueOf(NbrPT);
                    fenetreprincipal.this.carLayout1.card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/dossier.png")), "Nombre de projet total", NbrPTS, "12000"));
                    fenetreprincipal.this.carLayout1.card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/lumiere.png")), "Nombre de projet actif", NbrPAS, "12000"));
                    fenetreprincipal.this.carLayout1.card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/chef.png")), "Chef de projet", NbrCS, "12000"));
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
                IdEdit(row, filePathAll, 1);
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
                    csv.deleteLineFromCsvTotal(row + 1);
                    model.removeRow(row);
                    NbrPT = NbrPT - 1;
                    NbrPTS = String.valueOf(NbrPT);
                    fenetreprincipal.this.carLayout1.card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/gestionproj/asset/chef.png")), "Chef de projet", NbrCS, "12000"));
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

    private void edit(String id, int row, String filePath, int a) {
        System.out.println("Je suis dans ma fonction edit avec comme argument : " + id + " " + row + " " + filePath);
        close();
        Edit edit = new Edit(this, id, row, filePath, a);
        edit.setVisible(true);
    }

    private void IdEdit(int row, String filePath, int a) {
        System.out.println("Je suis dans mon idEdit avec comment argument : " + row + filePath);
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

    public static void rechercheAuto(JTable jt, JTextField jtf, JLabel jl) {
        DefaultTableModel dtm = (DefaultTableModel) jt.getModel();
        String mot = jtf.getText().trim().toLowerCase();

        if (mot.isEmpty()) {
            jl.setText("");
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(dtm);
            jt.setRowSorter(trs);
            return;
        }

        // Créer un filtre pour toutes les colonnes
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter("(?i)" + mot);

        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(dtm);
        trs.setRowFilter(filter);
        jt.setRowSorter(trs);

        int nbr = jt.getRowCount();
        if (nbr == 0) {
            jl.setForeground(Color.red);
            jl.setText("Aucun projet trouvé");
        } else if (nbr == 1) {
            jl.setForeground(new Color(0, 102, 0));
            jl.setText("Un Projet trouvé");
        } else {
            jl.setForeground(new Color(0, 102, 0));
            jl.setText("Retrouvé :" + nbr);
        }
    }

    public static void resetSearch(JTable jt, JTextField jtf, JLabel jl) {
        jtf.setText("");
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>((DefaultTableModel) jt.getModel());
        jt.setRowSorter(trs);
        jl.setText("");
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
        Demande = new design.TextField();
        ajouts = new design.Button();
        resultat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTable2.setBackground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Rend toutes les cellules non modifiables
                return false;
            }
        }
    );
    jTable2.setRowHeight(40);
    jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTable2MouseClicked(evt);
        }
    });
    jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            jTable2KeyPressed(evt);
        }
    });
    jScrollPane1.setViewportView(jTable2);
    if (jTable2.getColumnModel().getColumnCount() > 0) {
        jTable2.getColumnModel().getColumn(0).setResizable(false);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    carLayout1.setBackground(new java.awt.Color(255, 255, 255));

    Demande.setShadowColor(new java.awt.Color(255, 0, 0));
    Demande.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            DemandeKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            DemandeKeyTyped(evt);
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
                    .addComponent(resultat, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ajouts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(28, 28, 28))))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(carLayout1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(resultat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Demande, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ajouts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ajoutsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajoutsActionPerformed
        // TODO add your handling code here:
        close();
        AjoutProj aj = new AjoutProj(this);
        aj.setVisible(true);
    }//GEN-LAST:event_ajoutsActionPerformed

    private void DemandeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DemandeKeyReleased
        // TODO add your handling code here:
        rechercheAuto(jTable2, Demande, resultat);
    }//GEN-LAST:event_DemandeKeyReleased

    private void DemandeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DemandeKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_DemandeKeyTyped

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            // Double-clic détecté
            System.out.println("Double-clic détecté");
            // Ajoutez le traitement que vous souhaitez pour le double-clic ici
        } else if (evt.getClickCount() == 1) {
            // Clic simple détecté
            System.out.println("Clic simple détecté");
            if (jTable2.isEditing()) {
                jTable2.getCellEditor().stopCellEditing();
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
        if (jTable2.isEditing()) {
            jTable2.getCellEditor().stopCellEditing();
        }
    }//GEN-LAST:event_jTable2KeyPressed

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel resultat;
    // End of variables declaration//GEN-END:variables
}
