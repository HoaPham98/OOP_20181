/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n06.oop.gui;

import n06.oop.database.ConnectionManager;
import n06.oop.config.Setting;
import n06.oop.generator.DatabaseGenerator;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author HOA
 */
public class GenerateDialog extends javax.swing.JDialog implements PropertyChangeListener {
    
    private int numEntities;
    private int numRelations;

    /**
     * Creates new form GenerateDialog
     */
    public GenerateDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        repositorySelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnGroupType = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        radio100 = new javax.swing.JRadioButton();
        radio5k = new javax.swing.JRadioButton();
        radio60k = new javax.swing.JRadioButton();
        radio1M = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sinh dữ liệu");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tập dữ liệu"));
        jPanel2.setLayout(new java.awt.GridLayout(4, 1));

        btnGroupType.add(radio100);
        radio100.setSelected(true);
        radio100.setText("100 - 200");
        radio100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio100ActionPerformed(evt);
            }
        });
        jPanel2.add(radio100);

        btnGroupType.add(radio5k);
        radio5k.setText("5000 - 7000");
        radio5k.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio5kActionPerformed(evt);
            }
        });
        jPanel2.add(radio5k);

        btnGroupType.add(radio60k);
        radio60k.setText("60k - 80k");
        radio60k.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio60kActionPerformed(evt);
            }
        });
        jPanel2.add(radio60k);

        btnGroupType.add(radio1M);
        radio1M.setText("1M - 2M");
        radio1M.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio1MActionPerformed(evt);
            }
        });
        jPanel2.add(radio1M);

        btnGroupType.add(jRadioButton2);
        jRadioButton2.setText("15M - 17M");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Sinh dữ liệu");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jProgressBar1, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radio100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio100ActionPerformed
        // TODO add your handling code here:
        repositorySelection();
    }//GEN-LAST:event_radio100ActionPerformed

    private void radio5kActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio5kActionPerformed
        // TODO add your handling code here:
        repositorySelection();
    }//GEN-LAST:event_radio5kActionPerformed

    private void radio60kActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio60kActionPerformed
        // TODO add your handling code here:
        repositorySelection();
    }//GEN-LAST:event_radio60kActionPerformed

    private void radio1MActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio1MActionPerformed
        // TODO add your handling code here:
        repositorySelection();
    }//GEN-LAST:event_radio1MActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        repositorySelection();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        btnGenerateAction();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    public void repositorySelection() {
        String repositoryId = null;
        if (radio100.isSelected()) {
            repositoryId = Setting.REPO_NAME_100_200;
            numEntities = 100;
            numRelations = 200;
        } else if (radio5k.isSelected()) {
            repositoryId = Setting.REPO_NAME_5k_7k;
            numEntities = 5000;
            numRelations = 7000;
        } else if (radio60k.isSelected()) {
            repositoryId = Setting.REPO_NAME_60k_80k;
            numEntities = 60000;
            numRelations = 80000;
        } else if (radio1M.isSelected()) {
            repositoryId = Setting.REPO_NAME_1m_2m;
            numEntities = 1000000;
            numRelations = 2000000;
        } else {
            repositoryId = Setting.REPO_NAME_15m_17m;
            numEntities = 15000000;
            numRelations = 17000000;
        }

        System.out.println(repositoryId);
        System.out.println("" + numEntities + "-" + numRelations);

        ConnectionManager.getInstance().setRepository(repositoryId);
    }
    
    public void btnGenerateAction() {
        ConnectionManager.getInstance().getConnection().clear();
        jButton1.setEnabled(false);

        long t1 = System.currentTimeMillis();
        DatabaseGenerator databaseGenerator = new DatabaseGenerator();
        databaseGenerator.generator(numEntities, numRelations);

        long t2 = System.currentTimeMillis() - t1;
        jButton1.setEnabled(true);
        JOptionPane.showMessageDialog(this, "Sinh thực thể thành công trong " + t2 + " ms!");
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GenerateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GenerateDialog dialog = new GenerateDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupType;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton radio100;
    private javax.swing.JRadioButton radio1M;
    private javax.swing.JRadioButton radio5k;
    private javax.swing.JRadioButton radio60k;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
    // End of variables declaration//GEN-END:variables

    public static class W extends SwingWorker<Void, Void> {

        private int numEntities;
        private int numRelations;

        public W(int numEntities, int numRelations) {
            this.numEntities = numEntities;
            this.numRelations = numRelations;
        }

        @Override
        protected Void doInBackground() throws Exception {
            DatabaseGenerator databaseGenerator = new DatabaseGenerator();
            databaseGenerator.generator(numEntities, numRelations);
            return null;
        }

        @Override
        protected void done() {

        }
    }
}
