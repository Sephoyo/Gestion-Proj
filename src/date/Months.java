package date;

public final class Months extends javax.swing.JPanel {

    private Event event;
    private int m;

    public Months() {
        initComponents();
    }

    private void addEvent() {
        for (int i = 0; i < getComponentCount(); i++) {
            ((Button) getComponent(i)).setEvent(event);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmd1 = new date.Button();
        cmd2 = new date.Button();
        cmd3 = new date.Button();
        cmd4 = new date.Button();
        cmd5 = new date.Button();
        cmd6 = new date.Button();
        cmd7 = new date.Button();
        cmd8 = new date.Button();
        cmd9 = new date.Button();
        cmd10 = new date.Button();
        cmd11 = new date.Button();
        cmd12 = new date.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridLayout(4, 4));

        cmd1.setForeground(new java.awt.Color(75, 75, 75));
        cmd1.setText("Janvier");
        cmd1.setName("1"); // NOI18N
        cmd1.setOpaque(true);
        add(cmd1);

        cmd2.setForeground(new java.awt.Color(75, 75, 75));
        cmd2.setText("Février");
        cmd2.setName("2"); // NOI18N
        cmd2.setOpaque(true);
        cmd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd2ActionPerformed(evt);
            }
        });
        add(cmd2);

        cmd3.setForeground(new java.awt.Color(75, 75, 75));
        cmd3.setText("Mars");
        cmd3.setName("3"); // NOI18N
        cmd3.setOpaque(true);
        cmd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd3ActionPerformed(evt);
            }
        });
        add(cmd3);

        cmd4.setForeground(new java.awt.Color(75, 75, 75));
        cmd4.setText("Avril");
        cmd4.setName("4"); // NOI18N
        cmd4.setOpaque(true);
        add(cmd4);

        cmd5.setForeground(new java.awt.Color(75, 75, 75));
        cmd5.setText("Mai");
        cmd5.setName("5"); // NOI18N
        cmd5.setOpaque(true);
        add(cmd5);

        cmd6.setForeground(new java.awt.Color(75, 75, 75));
        cmd6.setText("Juin");
        cmd6.setName("6"); // NOI18N
        cmd6.setOpaque(true);
        add(cmd6);

        cmd7.setForeground(new java.awt.Color(75, 75, 75));
        cmd7.setText("Juillet");
        cmd7.setName("7"); // NOI18N
        cmd7.setOpaque(true);
        add(cmd7);

        cmd8.setForeground(new java.awt.Color(75, 75, 75));
        cmd8.setText("Aout");
        cmd8.setName("8"); // NOI18N
        cmd8.setOpaque(true);
        add(cmd8);

        cmd9.setForeground(new java.awt.Color(75, 75, 75));
        cmd9.setText("Septembre");
        cmd9.setName("9"); // NOI18N
        cmd9.setOpaque(true);
        add(cmd9);

        cmd10.setForeground(new java.awt.Color(75, 75, 75));
        cmd10.setText("Octobre");
        cmd10.setName("10"); // NOI18N
        cmd10.setOpaque(true);
        add(cmd10);

        cmd11.setForeground(new java.awt.Color(75, 75, 75));
        cmd11.setText("Novembre");
        cmd11.setName("11"); // NOI18N
        cmd11.setOpaque(true);
        cmd11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd11ActionPerformed(evt);
            }
        });
        add(cmd11);

        cmd12.setForeground(new java.awt.Color(75, 75, 75));
        cmd12.setText("Décembre");
        cmd12.setName("12"); // NOI18N
        cmd12.setOpaque(true);
        add(cmd12);
    }// </editor-fold>//GEN-END:initComponents

    private void cmd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd2ActionPerformed

    private void cmd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd3ActionPerformed

    private void cmd11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd11ActionPerformed

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
        addEvent();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private date.Button cmd1;
    private date.Button cmd10;
    private date.Button cmd11;
    private date.Button cmd12;
    private date.Button cmd2;
    private date.Button cmd3;
    private date.Button cmd4;
    private date.Button cmd5;
    private date.Button cmd6;
    private date.Button cmd7;
    private date.Button cmd8;
    private date.Button cmd9;
    // End of variables declaration//GEN-END:variables

}
