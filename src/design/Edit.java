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
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

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
    private boolean ActifNon;
    private DateDefinExtraction date = new DateDefinExtraction();
    private String currentDate = getCurrentDateAsString("dd-MM-yyyy");
    private java.util.List<Pair<String, String>> Supps = new java.util.ArrayList<>();

    /**
     * Creates new form View
     */
    //fermer la fenetre
    //Initialisation de la fenetre
    public Edit(fenetreprincipal mainFrame, String id, int row, String file, int actif) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmerFermeture(Edit.this);
            }
        });
        mainFrame.setVisible(false);
        String currentDate = getCurrentDateAsString("dd-MM-yyyy");
        initComponents();
        this.txtFin.setEnabled(true);
        this.txtFin.setEditable(true);
        if (actif == 0) {
            ActifArchiv.setVisible(false);
            this.ActifNon = false;
            this.dateChooser1.setTextRefernceWD(Txtdate);
            this.dateChooser2.setTextRefernceWD(txtFin);
            this.dateChooser1.setEnabled(true);
            this.dateChooser2.setEnabled(true);
        } else {
            ActifArchiv.setVisible(true);
            this.ActifNon = true;
            this.dateChooser1.setTextRefernceWD(Txtdate);
            this.dateChooser2.setTextRefernceWD(txtFin);
            this.dateChooser1.setEnabled(true);
            this.dateChooser2.setEnabled(true);
        }
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom());
        this.mainFrame = mainFrame;
        this.csv = new Csv(this.mainFrame);
        this.id = id;
        this.row = row;
        this.file = file;
        this.filePath = "/Users/joseph/gestionProjet/ProjetCSV/" + id + ".csv";
        LectLine(filePath);
        int length = data2.length;
        this.Titre.setText(data2[1]);
        this.ChefN.setText(enleverEspaces(data2[2])[0]);
        this.ChefP.setText(enleverEspaces(data2[2])[1]);
        if (!data2[3].trim().isEmpty()) {
            this.SuppN.setText(enleverEspaces(data2[3])[0]);
            this.SuppC.setText(enleverEspaces(data2[3])[1]);
        }
        this.Descr.setText(data2[length - 3]);
        this.Txtdate.setText(data2[length - 2]);
        this.txtFin.setText(data2[length - 1]);
        //A modifier lors de l'ajout des dates
        for (int i = 4; i < length - 3; i++) {
            addSuppl(data2[i]);
        }
        if (Supps.isEmpty()) {
            this.jScrollPane2.setVisible(false);
            this.Remove.setVisible(false);
        }

    }

    private void confirmerFermeture(Edit frame) {
        design.Button butOui = new design.Button();
        butOui.setText("Oui");
        butOui.setBorderColor(new java.awt.Color(204, 0, 0));
        butOui.setColorClick(new java.awt.Color(255, 51, 51));
        butOui.setColorOver(new java.awt.Color(255, 102, 102));

        design.Button butNon = new design.Button();
        butNon.setText("Non");

        butOui.addActionListener(e -> {
            System.out.println("Bouton Oui cliqué");
            frame.dispose();
            mainFrame.setVisible(true);
            mainFrame.repaint();
            mainFrame.revalidate();
        });

        butNon.addActionListener(e -> {
            System.out.println("Bouton Non cliqué");
            Container container = butNon.getParent();
            while (!(container instanceof JOptionPane) && container != null) {
                container = container.getParent();
            }
            if (container instanceof JOptionPane) {
                ((JOptionPane) container).setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        Object[] options = {butOui, butNon};

        int option = JOptionPane.showOptionDialog(frame,
                "Êtes-vous sûr vouloir quitter la fenêtre sans prendre en compte \n les éventuelles changements ?",
                "Confirmation de fermeture",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
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
        String Line2 = "";
        if (!this.SuppC.getText().trim().isEmpty() && !this.SuppN.getText().trim().isEmpty()) {
            Line2 = this.id + "," + Titre.getText() + "," + ChefN.getText() + " " + ChefP.getText() + "," + SuppN.getText() + " " + SuppC.getText();
        } else if (this.SuppC.getText().trim().isEmpty() && this.SuppN.getText().trim().isEmpty()) {
            Line2 = this.id + "," + Titre.getText() + "," + ChefN.getText() + " " + ChefP.getText() + "," + " ";
        }
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
            Line2 += "," + Descr.getText() + "," + Txtdate.getText() + "," + txtFin.getText();

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
        csv.updateCsv(file, this.row + 1, gestion);
    }

    private boolean TestDate() {
        if (date.isDateFormatValid(Txtdate.getText())
                && (date.compareDates(Txtdate.getText(), txtFin.getText()) || Txtdate.getText().equals(txtFin.getText()))
                && (currentDate.equals(txtFin.getText()) || date.compareDates(currentDate, txtFin.getText()))) {
            return true;
        } else if (txtFin.getText().trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void AfficheErreur() {
        if (!date.isDateFormatValid(txtFin.getText())) {
            JOptionPane.showMessageDialog(this, "La date de fin n'est pas au format jj-mm-aaaa", "Erreur de date", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!date.isDateFormatValid(Txtdate.getText())) {
            JOptionPane.showMessageDialog(this, "La date de début n'est pas au format jj-mm-aaaa", "Erreur de date", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!date.compareDates(Txtdate.getText(), txtFin.getText())) {
            JOptionPane.showMessageDialog(this, "La date de fin est inférieur à la date de début", "Erreur de date", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!currentDate.equals(txtFin.getText()) || !date.compareDates(currentDate, txtFin.getText())) {
            JOptionPane.showMessageDialog(this, "La date de fin doit être supérieur ou égal à la date du jour", "Erreur de date", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new date.DateChooser();
        dateChooser2 = new date.DateChooser();
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
        Remove = new design.Button();
        Add = new design.Button();
        ActifArchiv = new design.JCheckBoxCustom();
        Txtdate = new design.TextField();
        txtFin = new design.TextField();
        DF = new javax.swing.JLabel();
        DD = new javax.swing.JLabel();
        Terminer1 = new design.Button();

        dateChooser1.setForeground(new java.awt.Color(102, 102, 255));
        dateChooser1.setTextRefernce(Txtdate);

        dateChooser2.setTextRefernce(txtFin);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        Terminer.setText("Modifier");
        Terminer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminerActionPerformed(evt);
            }
        });

        Descr.setColumns(20);
        Descr.setLineWrap(true);
        Descr.setRows(5);
        Descr.setWrapStyleWord(true);
        jScrollPane1.setViewportView(Descr);

        DescrLab.setText("Description du projet");

        Titre.setShadowColor(new java.awt.Color(0, 102, 255));

        TitreLab.setText("Titre");

        ChefLab.setText("Chef de projet");

        SuppLab.setText("Suppléant");

        SuppC.setShadowColor(new java.awt.Color(255, 153, 0));

        ChefP.setShadowColor(new java.awt.Color(255, 0, 0));

        jScrollPane2.setBorder(null);

        ChefN.setShadowColor(new java.awt.Color(255, 0, 0));

        SuppN.setShadowColor(new java.awt.Color(255, 153, 0));

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
        ActifArchiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActifArchivActionPerformed(evt);
            }
        });

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
        txtFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFinKeyPressed(evt);
            }
        });

        DF.setText("Date de fin :");

        DD.setText("Date de début :");

        Terminer1.setText("Annuler");
        Terminer1.setBorderColor(new java.awt.Color(204, 0, 0));
        Terminer1.setColorClick(new java.awt.Color(204, 0, 51));
        Terminer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Terminer1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 52, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(332, 332, 332)
                                        .addComponent(TitreLab))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(282, 282, 282)
                                            .addComponent(DescrLab)
                                            .addGap(213, 213, 213))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(114, 114, 114)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(SuppN, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(ChefN, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(ChefLab)
                                                                    .addComponent(SuppLab))
                                                                .addGap(270, 270, 270)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(SuppC, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(ChefP, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                    .addGap(42, 42, 42))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(DD)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(DF)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addComponent(ActifArchiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Terminer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Terminer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ActifArchiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DF)
                    .addComponent(DD)
                    .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(DescrLab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Terminer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Terminer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TerminerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminerActionPerformed

        // Enregistrer les modifications dans le fichier csv
        if (areFieldsNotEmpty()) {
            System.out.println("Je suis passer par les champs plein");
            if (ActifArchiv.isVisible()) {
                System.out.println("Case visible");
                if (ActifArchiv.isSelected()) {
                    System.out.println("La case est selectionner");
                    if (TestDate()) {
                        System.out.println("Tout est bon, la case est coché et les champs respecte les contraintes ");
                        CsvFichier();
                        csv.deleteLineFromCsvTot(row + 1);
                        String filePatH = "/Users/joseph/gestionProjet/gestion.csv";
                        csv.appendLineToCSV(filePatH, id + "," + Titre.getText() + "," + ChefN.getText() + " " + ChefP.getText());
                        dispose();
                        mainFrame.repaint();
                        mainFrame.revalidate();
                        mainFrame.populateTable();
                        mainFrame.setupCustomTableColumn();
                        mainFrame.setVisible(true);
                    } else {
                        AfficheErreur();
                    }
                } else {
                    System.out.println("La case n'est pas coché");
                    if (TestDate()) {
                        //Si la date de fin est vide est que la case n'est pas coché
                        if (txtFin.getText().trim().isEmpty()) {
                            //Renvoie une erreur que la date de fin dans un projet d'archive ne peut être vide car le projet est dans l'achivage.
                            JOptionPane.showMessageDialog(this, "Si le projet reste dans l'archive, la date de fin ne peut être vide !", "Erreur de date", JOptionPane.WARNING_MESSAGE);
                        } else {
                            System.out.println("Tout est bon, la case n'est pas coché et les champs respecte les contraintes ");
                            System.out.println("J'arrive la ou il faut pas ");
                            CsvFichier();
                            csv.updateCsv(file, row + 1, id + "," + Titre.getText() + "," + ChefN.getText() + " " + ChefP.getText());
                            dispose();
                            mainFrame.repaint();
                            mainFrame.revalidate();
                            if (ActifNon) {
                                mainFrame.populateTableTotal();
                                mainFrame.setupCustomTableColumnTotal();
                                mainFrame.setVisible(true);
                            } else {
                                mainFrame.populateTable();
                                mainFrame.setupCustomTableColumn();
                                mainFrame.setVisible(true);
                            }
                        }
                    } else {
                        AfficheErreur();
                    }
                }
            } else {
                System.out.println("La case n'est pas visible");
                if (TestDate()) {
                    System.out.println("La case n'est pas visible et tout les éléments sont bon");
                    CsvFichier();
                    csv.deleteLineFromCsv(row + 1);
                    String filePatH = "/Users/joseph/gestionProjet/gestion.csv";
                    csv.appendLineToCSV(filePatH, id + "," + Titre.getText() + "," + ChefN.getText() + " " + ChefP.getText());
                    dispose();
                    mainFrame.repaint();
                    mainFrame.revalidate();
                    mainFrame.populateTable();
                    mainFrame.setupCustomTableColumn();
                    mainFrame.setVisible(true);
                } else {
                    AfficheErreur();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tout les champs obligatoire !", "Erreur de date", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_TerminerActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        // TODO add your handling code here:
        RetirerSupll();
        java.util.List<Pair<String, String>> suppPairs = getAllDataPairs();
        if (suppPairs.isEmpty()) {
            this.Remove.setVisible(false);
            this.jScrollPane2.setVisible(false);
        }
    }//GEN-LAST:event_RemoveActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        // TODO add your handling code here:
        addSupplWt();
        this.Remove.setVisible(true);
        this.jScrollPane2.setVisible(true);
    }//GEN-LAST:event_AddActionPerformed

    private void ActifArchivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActifArchivActionPerformed

    }//GEN-LAST:event_ActifArchivActionPerformed

    private void TxtdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtdateActionPerformed

    }//GEN-LAST:event_TxtdateActionPerformed

    private void txtFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFinActionPerformed

    }//GEN-LAST:event_txtFinActionPerformed

    private void TxtdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtdateMouseClicked

    }//GEN-LAST:event_TxtdateMouseClicked

    private void txtFinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFinMouseClicked

    }//GEN-LAST:event_txtFinMouseClicked

    private void Terminer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Terminer1ActionPerformed
        // TODO add your handling code here:
        dispose();
        mainFrame.setVisible(true);
        mainFrame.repaint();
        mainFrame.revalidate();
    }//GEN-LAST:event_Terminer1ActionPerformed

    private void txtFinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFinKeyPressed
        // TODO add your handling code here:
        txtFin.setText(" ");
    }//GEN-LAST:event_txtFinKeyPressed

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
            java.util.logging.Logger.getLogger(Edit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Edit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Edit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Edit.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
    }

    // Méthode pour vérifier si tous les champs obligatoires sont remplis
    private boolean areFieldsNotEmpty() {
        return !Titre.getText().trim().isEmpty()
                && !ChefN.getText().trim().isEmpty()
                && !ChefP.getText().trim().isEmpty()
                && areSupplFieldsNotEmpty()
                && (!(!SuppN.getText().trim().isEmpty() && SuppC.getText().trim().isEmpty())
                || !(SuppN.getText().trim().isEmpty() && !SuppC.getText().trim().isEmpty()))
                && !Descr.getText().trim().isEmpty()
                && !Txtdate.getText().trim().isEmpty();
    }

    //!(!SuppN.getText().trim().isEmpty() && SuppC.getText().trim().isEmpty()) || !(SuppN.getText().trim().isEmpty() && !SuppC.getText().trim().isEmpty()))
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private design.JCheckBoxCustom ActifArchiv;
    private design.Button Add;
    private javax.swing.JLabel ChefLab;
    private design.TextField ChefN;
    private design.TextField ChefP;
    private javax.swing.JLabel DD;
    private javax.swing.JLabel DF;
    private javax.swing.JTextArea Descr;
    private javax.swing.JLabel DescrLab;
    private design.Button Remove;
    private design.TextField SuppC;
    private javax.swing.JLabel SuppLab;
    private design.TextField SuppN;
    private design.Button Terminer;
    private design.Button Terminer1;
    private design.TextField Titre;
    private javax.swing.JLabel TitreLab;
    private design.TextField Txtdate;
    private date.DateChooser dateChooser1;
    private date.DateChooser dateChooser2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private design.TextField txtFin;
    // End of variables declaration//GEN-END:variables
}
