/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package design;

import static design.View.enleverEspaces;
import gestionproj.fenetreprincipal;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BoxLayout;

/**
 *
 * @author joseph
 */
public class Edit extends javax.swing.JFrame {

    private fenetreprincipal mainFrame;
    private String filePath;
    private String id;
    private String[] data1;
    private String[] data2;

    /**
     * Creates new form View
     */
    //fermer la fenetre
    public void close() {
        WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }

    //Initialisation de la fenetre
    public Edit(fenetreprincipal mainFrame, String id) {
        initComponents();
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom());
        this.mainFrame = mainFrame;
        this.id = id;
        String chemin = System.getProperty("user.dir");
        System.out.println("Le répertoire de travail actuel est : " + chemin);
        this.filePath = chemin+"/src/gestionproj/ProjetCSV/" + this.id + ".csv";
        System.out.println("L'id : " + id + " et le fichier : " + filePath);
        LectLine(filePath);
        int length = data2.length;
        this.Titre.setText(data2[1]);
        this.Chef.setText(data2[2]);
        this.Supp.setText(data2[3]);
        this.Descr.setText(data2[length - 1]);
        //A modifier lors de l'ajout des datesw
        for (int i = 4; i < length-1; i++) {
            addTextFieldToScroll(data2[i]);
        }
    }
    

    //Ajouter les suppléant dans le jscroll
    private void addTextFieldToScroll(String text) {
        String[] tableau = enleverEspaces(text);
        design.TextField newTextField1 = new design.TextField();
        design.TextField newTextField2 = new design.TextField();


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

    //Lecture la ligne
    private void LectLine(String filePath) {
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
        Supp = new design.TextField();
        Chef = new design.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(Terminer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(58, 58, 58)))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent(DescrLab))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(TitreLab))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ChefLab)
                            .addComponent(SuppLab))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Supp, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Chef, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
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
                    .addComponent(Chef, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SuppLab)
                    .addComponent(Supp, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
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
        //Enregistrer les modifications
        close();
        mainFrame.setVisible(true);
        mainFrame.repaint();
        mainFrame.revalidate();
    }//GEN-LAST:event_TerminerActionPerformed

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
    private design.TextField Chef;
    private javax.swing.JLabel ChefLab;
    private javax.swing.JTextArea Descr;
    private javax.swing.JLabel DescrLab;
    private design.TextField Supp;
    private javax.swing.JLabel SuppLab;
    private design.Button Terminer;
    private design.TextField Titre;
    private javax.swing.JLabel TitreLab;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
