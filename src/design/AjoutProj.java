/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package design;

import action.data.Csv;
import action.data.DateDefinExtraction;
import static action.data.DateDefinExtraction.getCurrentDateAsString;
import action.data.Pair;
import gestionproj.fenetreprincipal;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import javaswingdev.message.AlertDialog;
import javaswingdev.message.MessageDialog;

/**
 *
 * @author joseph.baert
 */
public class AjoutProj extends javax.swing.JFrame {

    private String filePath;
    private String filePathAll;
    private String filePathId;
    private int LastId;
    private fenetreprincipal mainFrame;
    private Csv csv;
    private DateDefinExtraction date = new DateDefinExtraction();
    private String currentDate = getCurrentDateAsString("dd-MM-yyyy");

    /**
     * Creates new form AjoutProj
     */
    public AjoutProj(fenetreprincipal mainFrame) {
        mainFrame.setVisible(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmerFermeture();
            }
        });
        initComponents();
        this.csv = new Csv(mainFrame);
        SuppListe.setVisible(false);
        Remove.setVisible(false);
        txtFin.setText("");
        this.mainFrame = mainFrame;
        String chemin = System.getProperty("user.dir");
        System.out.println("Le répertoire de travail actuel est : " + chemin);
        this.filePath = "L:\\Gestion_Projet/gestion.csv";
        this.filePathId = "L:\\Gestion_Projet/ProjetCSV/";
        this.filePathAll = "L:\\Gestion_Projet/AllProjects.csv";
        CompareLastId(filePath, filePathAll);
        jScrollPane6.setVerticalScrollBar(new ScrollBarCustom());
        SuppListe.setVerticalScrollBar(new ScrollBarCustom());
    }

    private void confirmerFermeture() {
        MessageDialog obj = new MessageDialog(this);
        obj.showMessage("Annuler l'ajout !", "Êtes-vous sur de vouloir annuler l'ajout ? \nAucune informations ne sera gardées !");
        if (obj.getMessageType() == MessageDialog.MessageType.OK) {
            this.dispose();
            this.mainFrame.setVisible(true);
        } else {
        }
    }

    //Ajout du projet dans un fichier csv 
    private void CsvFichier() {
        String line = "id,Projet,Chef de projet,Suppléant,";
        String Line2 = "";
        String FileId = filePathId + this.LastId + ".csv";
        if (!this.nomS.getText().trim().isEmpty() && !this.prenomS.getText().trim().isEmpty()) {
            Line2 = this.LastId + "," + Titre.getText() + "," + NomC.getText() + " " + PrenomC.getText() + "," + prenomS.getText() + " " + nomS.getText();
        } else if (this.nomS.getText().trim().isEmpty() && this.prenomS.getText().trim().isEmpty()) {
            Line2 = this.LastId + "," + Titre.getText() + "," + NomC.getText() + " " + PrenomC.getText() + "," + " ";
        }
        try {
            // Vérifier si le fichier existe, sinon le créer
            File file = new File(FileId);
            if (!file.exists()) {
                file.createNewFile();
            }

            java.util.List<Pair<String, String>> supplList = getAllDataPairs();
            int nombreSupp = supplList.size();

            for (int i = 1; i <= nombreSupp; i++) {
                Pair<String, String> Pair = supplList.get(i - 1);
                line += "Suppléant" + String.valueOf(i) + ",";
                String one = Pair.getFirst();
                String twice = Pair.getSecond();
                Line2 += "," + one + " " + twice;
            }
            if (!txtFin.getText().trim().isEmpty() && txtFin.getText().matches("\\d{2}-\\d{2}-\\d{4}")) {
                Line2 += "," + Descr.getText() + "," + Txtdate.getText() + "," + txtFin.getText();
            } else {
                Line2 += "," + Descr.getText() + "," + Txtdate.getText() + ", ";
            }

            line += "Description,Date de Début,Date de fin";
            // Utiliser BufferedWriter pour écrire dans le fichier
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(line);
                writer.newLine();
                writer.write(Line2);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error appending line to CSV file: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(line);

        mainFrame.repaint();
        mainFrame.revalidate();
    }

//Ajouts des suppléants
    private void AddSuppl() {
        design.TextField newTextField1 = new design.TextField();
        design.TextField newTextField2 = new design.TextField();
        Color CouleurShadow = new Color(255, 153, 0);
        newTextField1.setShadowColor(CouleurShadow);
        newTextField2.setShadowColor(CouleurShadow);

        newTextField1.setPreferredSize(new java.awt.Dimension(176, 36));
        newTextField2.setPreferredSize(new java.awt.Dimension(176, 36));

        javax.swing.JPanel panel = (javax.swing.JPanel) SuppListe.getViewport().getView();
        if (panel == null) {
            panel = new javax.swing.JPanel();
            panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
            SuppListe.setViewportView(panel);
        }

        javax.swing.JPanel pairPanel = new javax.swing.JPanel();
        pairPanel.setLayout(new java.awt.FlowLayout());
        pairPanel.add(newTextField1);
        pairPanel.add(newTextField2);

        panel.add(pairPanel);

        SuppListe.setViewportView(panel);
        getAllDataPairs();
    }

    //retirer le dernier suppléants ajouter
    private void RetirerSupll() {
        javax.swing.JPanel panel = (javax.swing.JPanel) SuppListe.getViewport().getView();
        if (panel != null) {
            int componentCount = panel.getComponentCount();
            if (componentCount > 0) {
                panel.remove(componentCount - 1); // Remove the last pair panel
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    //A l'aide de la class pair et de cette méthode cela prend tout les supplléants ajouter pour les mettre dans un tableau
    public java.util.List<Pair<String, String>> getAllDataPairs() {
        java.util.List<Pair<String, String>> dataPairs = new java.util.ArrayList<>();

        javax.swing.JPanel panel = (javax.swing.JPanel) SuppListe.getViewport().getView();
        if (panel != null) {
            int componentCount = panel.getComponentCount();

            for (int i = 0; i < componentCount; i++) {
                javax.swing.JPanel pairPanel = (javax.swing.JPanel) panel.getComponent(i);
                if (pairPanel != null) {
                    javax.swing.JTextField textField1 = (javax.swing.JTextField) pairPanel.getComponent(0);
                    javax.swing.JTextField textField2 = (javax.swing.JTextField) pairPanel.getComponent(1);

                    String text1 = textField1.getText();
                    String text2 = textField2.getText();

                    // Ajoutez les données sous forme de paire à la liste
                    dataPairs.add(new Pair<>(text1, text2));
                }
            }
        }
        return dataPairs;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new date.DateChooser();
        dateChooser2 = new date.DateChooser();
        jScrollPane6 = new javax.swing.JScrollPane();
        Descr = new javax.swing.JTextArea();
        SuppListe = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Titre = new design.TextField();
        Add = new design.Button();
        Remove = new design.Button();
        nomS = new design.TextField();
        NomC = new design.TextField();
        PrenomC = new design.TextField();
        prenomS = new design.TextField();
        Txtdate = new design.TextField();
        DD = new javax.swing.JLabel();
        DF = new javax.swing.JLabel();
        txtFin = new design.TextField();
        Annuler = new design.Button();
        Valider = new design.Button();

        dateChooser1.setForeground(new java.awt.Color(102, 102, 255));
        dateChooser1.setTextRefernce(Txtdate);

        dateChooser2.setTextRefernce(txtFin);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ajout d'un projet");
        setBackground(new java.awt.Color(204, 204, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        Descr.setColumns(20);
        Descr.setLineWrap(true);
        Descr.setRows(5);
        Descr.setWrapStyleWord(true);
        Descr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DescrKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(Descr);

        SuppListe.setBorder(null);

        jLabel2.setText("Chef De Projet");

        jLabel3.setText("Suppléants");

        jLabel4.setText("Description du Projet");

        jLabel5.setText("Titre du Projet");

        Titre.setShadowColor(new java.awt.Color(0, 102, 255));
        Titre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TitreActionPerformed(evt);
            }
        });

        Add.setText("+");
        Add.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Add.setPreferredSize(new java.awt.Dimension(21, 23));
        Add.setRadius(500);
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        Remove.setText("-");
        Remove.setBorderColor(new java.awt.Color(204, 0, 0));
        Remove.setColorClick(new java.awt.Color(255, 51, 51));
        Remove.setColorOver(new java.awt.Color(255, 102, 102));
        Remove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Remove.setPreferredSize(new java.awt.Dimension(21, 23));
        Remove.setRadius(500);
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });

        nomS.setShadowColor(new java.awt.Color(255, 153, 0));

        NomC.setShadowColor(new java.awt.Color(255, 0, 0));

        PrenomC.setShadowColor(new java.awt.Color(255, 0, 0));

        prenomS.setShadowColor(new java.awt.Color(255, 153, 0));

        Txtdate.setShadowColor(new java.awt.Color(102, 102, 255));

        DD.setText("Date de début :");

        DF.setText("Date de fin :");

        txtFin.setShadowColor(new java.awt.Color(204, 93, 93));

        Annuler.setText("Annuler");
        Annuler.setBorderColor(new java.awt.Color(204, 0, 0));
        Annuler.setColorClick(new java.awt.Color(204, 0, 51));
        Annuler.setColorOver(new java.awt.Color(255, 51, 51));
        Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerActionPerformed(evt);
            }
        });

        Valider.setText("Valider");
        Valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValiderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(348, 348, 348)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Annuler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Valider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel2))
                                                .addGap(45, 45, 45)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(NomC, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(PrenomC, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(nomS, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(prenomS, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(124, 124, 124))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(DD)
                                                .addGap(18, 18, 18)
                                                .addComponent(Txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(DF)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(106, 106, 106)
                                        .addComponent(SuppListe, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(359, 359, 359)
                                .addComponent(jLabel5)))
                        .addGap(0, 54, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NomC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(PrenomC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nomS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prenomS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SuppListe, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DD)
                    .addComponent(DF)
                    .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Annuler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Valider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TitreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TitreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TitreActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        // TODO add your handling code here:
        AddSuppl();
        SuppListe.setVisible(true);
        Remove.setVisible(true);
    }//GEN-LAST:event_AddActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        // TODO add your handling code here:
        RetirerSupll();
        //verifier qu'il existe encore des suppléants
        java.util.List<Pair<String, String>> suppPairs = getAllDataPairs();
        if (suppPairs.isEmpty()) {
            SuppListe.setVisible(false);
            Remove.setVisible(false);
        }
    }//GEN-LAST:event_RemoveActionPerformed

    private void AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerActionPerformed
        // TODO add your handling code here:
        dispose();
        mainFrame.setVisible(true);
        mainFrame.repaint();
        mainFrame.revalidate();
    }//GEN-LAST:event_AnnulerActionPerformed

    private void ValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValiderActionPerformed
        if (areFieldsNotEmpty()) {
            if (!txtFin.getText().trim().isEmpty()) {
                if (txtFin.getText().matches("\\d{2}-\\d{2}-\\d{4}")) {
                    if (date.compareDates(Txtdate.getText(), txtFin.getText())) {
                        if (currentDate.equals(txtFin.getText()) || date.compareDates(currentDate, txtFin.getText())) {
                            this.CsvFichier();
                            csv.appendLineToCSV(filePath, this.LastId + "," + Titre.getText() + "," + NomC.getText() + " " + PrenomC.getText());
                            dispose();
                            mainFrame.repaint();
                            mainFrame.revalidate();
                            mainFrame.populateTable();
                            mainFrame.setupCustomTableColumn();
                            mainFrame.setVisible(true);
                            return;
                        } else if (!date.compareDates(currentDate, txtFin.getText())) {
                            MessageDialog obj = new MessageDialog(this);
                            obj.showMessage("Archivage !", "Êtes-vous sur de vouloir archiver votre projet ?\nLa date de fin est inférieur à celle du jour il sera donc archivé");
                            if (obj.getMessageType() == MessageDialog.MessageType.OK) {
                                this.CsvFichier();
                                csv.appendLineToCSV(filePathAll, this.LastId + "," + Titre.getText() + "," + NomC.getText() + " " + PrenomC.getText());
                                dispose();
                                mainFrame.repaint();
                                mainFrame.revalidate();
                                mainFrame.populateTableTotal();
                                mainFrame.setupCustomTableColumnTotal();
                                mainFrame.setVisible(true);
                            } else {
                            }

                        }
                    } else {
                        AlertDialog obj = new AlertDialog(this);
                        obj.showMessage("Erreur date de fin !", "Veuillez remplir une date valide ou laisser le champ vide pour la date de fin !\nLa date doit être au format jj-mm-aaaa");
                    }
                } else {
                    AlertDialog obj = new AlertDialog(this);
                    obj.showMessage("Erreur date de fin !", "Veuillez remplir une date valide ou laisser le champ vide pour la date de fin !\nLa date doit être au format jj-mm-aaaa");
                }
            } else {
                this.CsvFichier();
                csv.appendLineToCSV(filePath, this.LastId + "," + Titre.getText() + "," + NomC.getText() + " " + PrenomC.getText());
                dispose();
                mainFrame.repaint();
                mainFrame.revalidate();
                mainFrame.populateTable();
                mainFrame.setupCustomTableColumn();
                mainFrame.setVisible(true);
                return;
            }
        } else {
            AlertDialog obj = new AlertDialog(this);
            obj.showMessage("Champs vide !", "Veuillez remplir tous les champs obligatoires");
        }

    }//GEN-LAST:event_ValiderActionPerformed

    private void DescrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DescrKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            evt.consume();
        }
    }//GEN-LAST:event_DescrKeyPressed
    private void CompareLastId(String filePath, String filePathAll) {
        LastId(filePath);
        LastId(filePathAll);
        this.LastId = this.LastId + 1;
        System.out.println("LastId final = " + LastId);
    }

    private void LastId(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                // Ignorer la première ligne
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length > 0) {
                    int currentId = Integer.parseInt(fields[0]);
                    System.out.println("Currentid = " + currentId);
                    if (currentId > this.LastId) {
                        System.out.println(currentId + ">" + this.LastId);
                        this.LastId = currentId;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println(this.LastId);
    }

// Méthode pour vérifier si tous les champs obligatoires sont remplis
    private boolean areFieldsNotEmpty() {
        return !Titre.getText().trim().isEmpty()
                && !NomC.getText().trim().isEmpty()
                && !PrenomC.getText().trim().isEmpty()
                && areSupplFieldsNotEmpty()
                && !((!nomS.getText().trim().isEmpty() && prenomS.getText().trim().isEmpty())
                || (nomS.getText().trim().isEmpty() && !prenomS.getText().trim().isEmpty()))
                && !Descr.getText().trim().isEmpty()
                && !Txtdate.getText().trim().isEmpty();
    }

// Méthode pour vérifier si les champs de suppléants ne sont pas vides
    private boolean areSupplFieldsNotEmpty() {
        java.util.List<Pair<String, String>> suppPairs = getAllDataPairs();
        for (Pair<String, String> pair : suppPairs) {
            if (pair.getFirst().trim().isEmpty() || pair.getSecond().trim().isEmpty()) {
                return false;
            }
        }
        return true; // Tous les champs de suppléants sont remplis
    }

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
            java.util.logging.Logger.getLogger(AjoutProj.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AjoutProj.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AjoutProj.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AjoutProj.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private design.Button Add;
    private design.Button Annuler;
    private javax.swing.JLabel DD;
    private javax.swing.JLabel DF;
    private javax.swing.JTextArea Descr;
    private design.TextField NomC;
    private design.TextField PrenomC;
    private design.Button Remove;
    private javax.swing.JScrollPane SuppListe;
    private design.TextField Titre;
    private design.TextField Txtdate;
    private design.Button Valider;
    private date.DateChooser dateChooser1;
    private date.DateChooser dateChooser2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane6;
    private design.TextField nomS;
    private design.TextField prenomS;
    private design.TextField txtFin;
    // End of variables declaration//GEN-END:variables
}
