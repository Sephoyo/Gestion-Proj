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

    public void close() {
        WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }

    /**
     * Creates new form AjoutProj
     */
    public AjoutProj(fenetreprincipal mainFrame) {
        initComponents();
        this.csv = new Csv(mainFrame);
        SuppListe.setVisible(false);
        Remove.setVisible(false);
        this.mainFrame = mainFrame;
        String chemin = System.getProperty("user.dir");
        System.out.println("Le répertoire de travail actuel est : " + chemin);
        this.filePath = chemin+"/src/gestionproj/gestion.csv";
        this.filePathId = chemin+"/src/gestionproj/ProjetCSV/";
        this.filePathAll = chemin+"/src/gestionproj/AllProjects.csv";
        CompareLastId(filePath,filePathAll);
        jScrollPane6.setVerticalScrollBar(new ScrollBarCustom());
        SuppListe.setVerticalScrollBar(new ScrollBarCustom());
    }

    //Ajout du projet dans un fichier csv 
    private void CsvFichier() {
        String line = "id,Projet,Chef de projet,Suppléant,";

        String FileId = filePathId + this.LastId + ".csv";
        String Line2 = "" + this.LastId + "," + Titre.getText() + "," + NomC.getText() + " " + PrenomC.getText() + "," + nomS.getText() + " " + nomS.getText();
        try {
            // Vérifier si le fichier existe, sinon le créer
            File file = new File(FileId);
            if (!file.exists()) {
                file.createNewFile();
            }

            java.util.List<Pair<String, String>> supplList = getAllDataPairs();
            int nombreSupp = supplList.size();

            for (int i = 1; i <= nombreSupp; i++) {
                Pair<String, String> Pair = supplList.get(i-1);
                line += "Suppléant" + String.valueOf(i) + ",";
                String one = Pair.getFirst();
                String twice = Pair.getSecond();
                Line2 += ","+one+" "+twice;
            }
            Line2 += ","+Descr.getText();

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
        Color CouleurShadow = new Color(255,153,0);
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

        Valider = new javax.swing.JButton();
        Annuler = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ajout d'un projet");
        setBackground(new java.awt.Color(204, 204, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Valider.setText("Valider");
        Valider.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValiderActionPerformed(evt);
            }
        });

        Annuler.setText("Annuler");
        Annuler.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerActionPerformed(evt);
            }
        });

        Descr.setColumns(20);
        Descr.setRows(5);
        jScrollPane6.setViewportView(Descr);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Annuler)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Valider))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(9, 9, 9)
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
                                            .addGap(138, 138, 138))
                                        .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(94, 94, 94)
                                            .addComponent(SuppListe, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(326, 326, 326)
                                .addComponent(jLabel4)))
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NomC, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrenomC, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nomS, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prenomS, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(SuppListe, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Valider)
                    .addComponent(Annuler))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    //Bouton annuler
    private void AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerActionPerformed
        close();
        mainFrame.setVisible(true);
        mainFrame.repaint();
        mainFrame.revalidate();
    }//GEN-LAST:event_AnnulerActionPerformed
    //Bouton valider
    private void ValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValiderActionPerformed
        close();
        this.CsvFichier();
        csv.appendLineToCSV(filePath, this.LastId + "," + Titre.getText() + "," + NomC.getText() + " " + PrenomC.getText());
        mainFrame.populateTable();
        mainFrame.setupCustomTableColumn();
        mainFrame.repaint();
        mainFrame.revalidate();
        mainFrame.setVisible(true);
    }//GEN-LAST:event_ValiderActionPerformed

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
    }//GEN-LAST:event_RemoveActionPerformed
    private void CompareLastId(String filePath, String filePathAll){
        int id1 = LastId(filePath);
        int id2 = LastId(filePathAll);
        this.LastId = id1>id2 ? id1 : id2;
        System.out.println(LastId);
    }

//Savoir le dernier id dans le fichier avec tout les projets
    private int LastId(String filePath) {
        int id = 0;
        String lastLine = ""; // Stocke la dernière ligne 
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!lastLine.isEmpty()) {
            String[] parts = lastLine.split(",");
            if (parts.length > 0) {
                String lastIdString = parts[0].trim();
                try {
                    int lastId = Integer.parseInt(lastIdString);
                    id = lastId + 1;
                } catch (NumberFormatException e) {
                    System.err.println("Erreur lors de la conversion de l'ID en entier : " + e.getMessage());
                    id = 1; // En cas d'erreur, réinitialisez à 0 
                }
            }
        } else {
            id = 1; // Si le fichier est vide, réinitialisez à 0 
        }
        return id;
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
    private javax.swing.JButton Annuler;
    private javax.swing.JTextArea Descr;
    private design.TextField NomC;
    private design.TextField PrenomC;
    private design.Button Remove;
    private javax.swing.JScrollPane SuppListe;
    private design.TextField Titre;
    private javax.swing.JButton Valider;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane6;
    private design.TextField nomS;
    private design.TextField prenomS;
    // End of variables declaration//GEN-END:variables
}
