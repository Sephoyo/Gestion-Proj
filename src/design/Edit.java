/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package design;

import action.data.Csv;
import action.data.Pair;
import gestionproj.fenetreprincipal;
import java.awt.Color;
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

/**
 *
 * @author joseph
 */
public class Edit extends javax.swing.JFrame {

    private fenetreprincipal mainFrame;
    private Csv csv;
    private String filePath;
    private String id;
    private String file;
    private String[] data1;
    private String[] data2;
    private int row;
    private int ActifNon;

    /**
     * Creates new form View
     */
    //fermer la fenetre
    public void close() {
        WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }

    //Initialisation de la fenetre
    public Edit(fenetreprincipal mainFrame, String id, int row, String file, int actif) {
        initComponents();
        if (actif == 0) {
            ActifArchiv.setVisible(false);
        } else {
            ActifArchiv.setVisible(true);
        }
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom());
        this.mainFrame = mainFrame;
        this.csv = new Csv(this.mainFrame);
        this.id = id;
        this.row = row;
        this.file = file;
        String chemin = System.getProperty("user.dir");
        System.out.println("Le répertoire de travail actuel est : " + chemin);
        this.filePath = chemin + "/src/gestionproj/ProjetCSV/" + this.id + ".csv";
        System.out.println("L'id : " + id + " et le fichier : " + filePath);
        LectLine(filePath);
        int length = data2.length;
        this.Titre.setText(data2[1]);
        this.ChefN.setText(enleverEspaces(data2[2])[0]);
        this.ChefP.setText(enleverEspaces(data2[2])[1]);
        this.SuppN.setText(enleverEspaces(data2[3])[0]);
        this.SuppC.setText(enleverEspaces(data2[3])[1]);
        this.Descr.setText(data2[length - 1]);
        //A modifier lors de l'ajout des dates
        for (int i = 4; i < length - 1; i++) {
            addSuppl(data2[i]);
        }
    }

    public static String[] enleverEspaces(String chaine) {
        // Séparer en tableau
        String[] tableau = chaine.split("\\s+");
        return tableau;
    }

    //Ajouter les suppléant dans le jScroll
    private void addSuppl(String text) {
        String[] tableau = enleverEspaces(text);
        design.TextField newTextField1 = new design.TextField();
        design.TextField newTextField2 = new design.TextField();
        Color CouleurShadow = new Color(255, 153, 0);
        newTextField1.setShadowColor(CouleurShadow);
        newTextField2.setShadowColor(CouleurShadow);
        newTextField1.setPreferredSize(new java.awt.Dimension(136, 36));
        newTextField2.setPreferredSize(new java.awt.Dimension(136, 36));
        newTextField1.setText(tableau[0]);
        newTextField2.setText(tableau[1]);

        javax.swing.JPanel panel = (javax.swing.JPanel) jScrollPane2.getViewport().getView();
        if (panel == null) {
            panel = new javax.swing.JPanel();
            panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
            jScrollPane2.setViewportView(panel);
        }

        javax.swing.JPanel pairPanel = new javax.swing.JPanel();
        pairPanel.setLayout(new java.awt.FlowLayout());
        pairPanel.add(newTextField1);
        pairPanel.add(newTextField2);

        panel.add(pairPanel);

        jScrollPane2.setViewportView(panel);
    }

    private void addSupplWt() {
        design.TextField newTextField1 = new design.TextField();
        design.TextField newTextField2 = new design.TextField();
        Color CouleurShadow = new Color(255, 153, 0);
        newTextField1.setShadowColor(CouleurShadow);
        newTextField2.setShadowColor(CouleurShadow);

        newTextField1.setPreferredSize(new java.awt.Dimension(176, 36));
        newTextField2.setPreferredSize(new java.awt.Dimension(176, 36));

        javax.swing.JPanel panel = (javax.swing.JPanel) jScrollPane2.getViewport().getView();
        if (panel == null) {
            panel = new javax.swing.JPanel();
            panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
            jScrollPane2.setViewportView(panel);
        }

        javax.swing.JPanel pairPanel = new javax.swing.JPanel();
        pairPanel.setLayout(new java.awt.FlowLayout());
        pairPanel.add(newTextField1);
        pairPanel.add(newTextField2);

        panel.add(pairPanel);

        jScrollPane2.setViewportView(panel);
        getAllDataPairs();
    }

    //Lecture la ligne
    private void LectLine(String filePath) {
        System.out.println("Je suis dans ma fonction lectline");
        String[] data = null;
        String[] data2 = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String premierLigne = br.readLine();
            String deuxiemeLigne = br.readLine();
            if (deuxiemeLigne != null) {
                // Séparez les données en utilisant la virgule comme séparateur
                data = deuxiemeLigne.split(",");
                data2 = premierLigne.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.data1 = data2;
        this.data2 = data;
    }

    public java.util.List<Pair<String, String>> getAllDataPairs() {
        java.util.List<Pair<String, String>> dataPairs = new java.util.ArrayList<>();

        javax.swing.JPanel panel = (javax.swing.JPanel) jScrollPane2.getViewport().getView();
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

    private void RetirerSupll() {
        javax.swing.JPanel panel = (javax.swing.JPanel) jScrollPane2.getViewport().getView();
        if (panel != null) {
            int componentCount = panel.getComponentCount();
            if (componentCount > 0) {
                panel.remove(componentCount - 1); // Remove the last pair panel
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    private void CsvFichier() {
        String line = "id,Projet,Chef de projet,Suppléant,";

        String FileId = filePath;
        String Line2 = "" + this.id + "," + Titre.getText() + "," + ChefN.getText() + " " + ChefP.getText() + "," + SuppN.getText() + " " + SuppC.getText();
        try {
            // Vérifier si le fichier existe, sinon le créer
            File file = new File(FileId);
            if (file.exists()) {
                file.delete();
            }
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
            Line2 += "," + Descr.getText();

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
        String gestion = this.id + "," + this.Titre.getText() + "," + this.ChefN.getText() + " " + this.ChefP.getText();
        System.out.println(gestion);
        csv.updateCsv(file, this.row+1, gestion);
        System.out.println(line);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Terminer = new design.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        Descr = new javax.swing.JTextArea();
        DescrLab = new javax.swing.JLabel();
        Titre = new design.TextField();
        TitreLab = new javax.swing.JLabel();
        ChefLab = new javax.swing.JLabel();
        SuppLab = new javax.swing.JLabel();
        SuppC = new design.TextField();
        ChefP = new design.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        ChefN = new design.TextField();
        SuppN = new design.TextField();
        SuppA = new javax.swing.JLabel();
        Remove = new design.Button();
        Add = new design.Button();
        ActifArchiv = new design.JCheckBoxCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Terminer.setText("Terminer");
        Terminer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminerActionPerformed(evt);
            }
        });

        Descr.setColumns(20);
        Descr.setRows(5);
        jScrollPane1.setViewportView(Descr);

        DescrLab.setText("Description du projet");

        TitreLab.setText("Titre");

        ChefLab.setText("Chef de projet");

        SuppLab.setText("Suppléant");

        jScrollPane2.setBorder(null);

        SuppA.setText("Autres suppléants :");

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

        Add.setText("+");
        Add.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Add.setPreferredSize(new java.awt.Dimension(21, 23));
        Add.setRadius(500);
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        ActifArchiv.setText("Rendre ce projet actif.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(TitreLab))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SuppN, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SuppA)
                            .addComponent(ChefN, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ActifArchiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DescrLab))))
                .addGap(0, 353, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(Terminer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(58, 58, 58))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(129, 129, 129)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ChefLab)
                            .addComponent(SuppLab))
                        .addGap(270, 270, 270)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SuppC, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ChefP, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(TitreLab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChefLab)
                    .addComponent(ChefP, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChefN, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SuppC, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuppN, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuppLab)
                    .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(SuppA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(ActifArchiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(DescrLab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(Terminer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TerminerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminerActionPerformed
        //Enregistrer les modifications dans le fichier csv
        CsvFichier();
        String chemin = System.getProperty("user.dir");
        //si le check box et selectionner alors
        if (ActifArchiv.isSelected()) {
            csv.deleteLineFromCsv(this.file, this.row+1);
            String filePatH = chemin + "/src/gestionproj/gestion.csv";
            csv.appendLineToCSV(filePatH, this.id + "," + this.Titre.getText() + "," + this.ChefN.getText() + " " + this.ChefP.getText());
            close();
            mainFrame.populateTableTotal();
            mainFrame.setupCustomTableColumnTotal();
            mainFrame.repaint();
            mainFrame.revalidate();
            mainFrame.setVisible(true);
        } else //sinon
        {
            close();
            mainFrame.populateTable();
            mainFrame.setupCustomTableColumn();
            mainFrame.setVisible(true);
            mainFrame.repaint();
            mainFrame.revalidate();
        }
    }//GEN-LAST:event_TerminerActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        // TODO add your handling code here:
        RetirerSupll();
    }//GEN-LAST:event_RemoveActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        // TODO add your handling code here:
        addSupplWt();
    }//GEN-LAST:event_AddActionPerformed

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
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private design.JCheckBoxCustom ActifArchiv;
    private design.Button Add;
    private javax.swing.JLabel ChefLab;
    private design.TextField ChefN;
    private design.TextField ChefP;
    private javax.swing.JTextArea Descr;
    private javax.swing.JLabel DescrLab;
    private design.Button Remove;
    private javax.swing.JLabel SuppA;
    private design.TextField SuppC;
    private javax.swing.JLabel SuppLab;
    private design.TextField SuppN;
    private design.Button Terminer;
    private design.TextField Titre;
    private javax.swing.JLabel TitreLab;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
