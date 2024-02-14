/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package design;

import gestionproj.fenetreprincipal;
import java.awt.Color;
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
public class View extends javax.swing.JFrame {

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
    public View(fenetreprincipal mainFrame, String id) {
        initComponents();
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom());
        this.mainFrame = mainFrame;
        this.id = id;
        String chemin = System.getProperty("user.dir");
        System.out.println("Le répertoire de travail actuel est : " + chemin);
        this.filePath = "L:\\test/ProjetCSV/"+id+".csv";
        System.out.println("L'id : " + id + " et le fichier : " + filePath);
        LectLine(filePath);
        int length = data2.length;
        this.Titre.setText(data2[1]);
        this.ChefN.setText(enleverEspaces(data2[2])[0]);
        this.ChefP.setText(enleverEspaces(data2[2])[1]);
        this.SuppN.setText(enleverEspaces(data2[3])[0]);
        this.SuppC.setText(enleverEspaces(data2[3])[1]);
        this.Descr.setText(data2[length - 3]);
        this.Txtdate.setText(data2[length-2]);
        this.txtFin.setText(data2[length-1]);
        for (int i = 4; i < length - 3; i++) {
            addTextFieldToScroll(data2[i]);
        }
    }

    public static String[] enleverEspaces(String chaine) {
        // Séparer en tableau
        String[] tableau = chaine.split("\\s+");
        return tableau;
    }

    //Ajouter les suppléant dans le jScroll
    private void addTextFieldToScroll(String text) {
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
        newTextField1.setEditable(false);
        newTextField2.setEditable(false);

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
        SuppC = new design.TextField();
        ChefP = new design.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        ChefN = new design.TextField();
        SuppN = new design.TextField();
        SuppA = new javax.swing.JLabel();
        Txtdate = new design.TextField();
        DD = new javax.swing.JLabel();
        txtFin = new design.TextField();
        DF = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Terminer.setText("Terminer");
        Terminer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminerActionPerformed(evt);
            }
        });

        Descr.setEditable(false);
        Descr.setColumns(20);
        Descr.setLineWrap(true);
        Descr.setRows(5);
        Descr.setWrapStyleWord(true);
        jScrollPane1.setViewportView(Descr);

        DescrLab.setText("Description du projet");

        Titre.setEditable(false);
        Titre.setShadowColor(new java.awt.Color(0, 102, 255));

        TitreLab.setText("Titre");

        ChefLab.setText("Chef de projet");

        SuppLab.setText("Suppléant");

        SuppC.setEditable(false);
        SuppC.setShadowColor(new java.awt.Color(255, 153, 0));

        ChefP.setEditable(false);
        ChefP.setShadowColor(new java.awt.Color(255, 0, 0));

        jScrollPane2.setBorder(null);

        ChefN.setEditable(false);
        ChefN.setShadowColor(new java.awt.Color(255, 0, 0));

        SuppN.setEditable(false);
        SuppN.setShadowColor(new java.awt.Color(255, 153, 0));

        SuppA.setText("Autres suppléants :");

        Txtdate.setEditable(false);
        Txtdate.setShadowColor(new java.awt.Color(102, 102, 255));
        Txtdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtdateMouseClicked(evt);
            }
        });
        Txtdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtdateActionPerformed(evt);
            }
        });

        DD.setText("Date de début :");

        txtFin.setEditable(false);
        txtFin.setShadowColor(new java.awt.Color(204, 93, 93));
        txtFin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFinMouseClicked(evt);
            }
        });
        txtFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFinActionPerformed(evt);
            }
        });

        DF.setText("Date de fin :");

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
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ChefLab)
                            .addComponent(SuppLab))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(SuppN, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SuppC, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(ChefN, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(ChefP, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(SuppA))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(377, 377, 377)
                        .addComponent(TitreLab))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(DD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(DF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(ChefP, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChefN, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SuppC, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuppN, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuppLab))
                .addGap(16, 16, 16)
                .addComponent(SuppA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DD)
                    .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(DescrLab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(Terminer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TerminerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminerActionPerformed
        //Enregistrer les modifications
        close();
        mainFrame.setVisible(true);
        mainFrame.repaint();
        mainFrame.revalidate();
    }//GEN-LAST:event_TerminerActionPerformed

    private void TxtdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtdateMouseClicked

    }//GEN-LAST:event_TxtdateMouseClicked

    private void TxtdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtdateActionPerformed

    }//GEN-LAST:event_TxtdateActionPerformed

    private void txtFinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFinMouseClicked

    }//GEN-LAST:event_txtFinMouseClicked

    private void txtFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFinActionPerformed

    }//GEN-LAST:event_txtFinActionPerformed

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
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChefLab;
    private design.TextField ChefN;
    private design.TextField ChefP;
    private javax.swing.JLabel DD;
    private javax.swing.JLabel DF;
    private javax.swing.JTextArea Descr;
    private javax.swing.JLabel DescrLab;
    private javax.swing.JLabel SuppA;
    private design.TextField SuppC;
    private javax.swing.JLabel SuppLab;
    private design.TextField SuppN;
    private design.Button Terminer;
    private design.TextField Titre;
    private javax.swing.JLabel TitreLab;
    private design.TextField Txtdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private design.TextField txtFin;
    // End of variables declaration//GEN-END:variables
}
