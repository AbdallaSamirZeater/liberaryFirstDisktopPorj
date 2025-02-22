/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Taqseet;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ahmed
 */
public class clientTransactions extends javax.swing.JPanel {

    /**
     * Creates new form recieved_items
     */
    public DefaultTableModel tah;

    public clientTransactions() {
        initComponents();
        makeTable();
        mandobClientTable();
    }

    public void delTblCol(DefaultTableModel table) {
        if (table.getRowCount() != 0) {
            for (int i = table.getRowCount() - 1; i >= 0; i--) {
                table.removeRow(i);

            }
        }
    }

     String months(int CRI) {

        String str = "";
        float val = 0;
        int count = 1;
        try {
            DBcon d = new DBcon();
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` WHERE aqsat.CRI = '" + CRI + "';");
            d.rs.first();
            val = d.rs.getFloat("aqsat.value");
            while ((d.rs).next()) {
                if (d.rs.getFloat("aqsat.value") != val) {
                    str = str + val + "*" + count + " + ";
                    val = d.rs.getFloat("aqsat.value");
                    count = 1;
                } else if (d.rs.isLast()) {
                    str = str + val + "*" + (count + 1);
                } else {
                    count++;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(str);
        return str;
    }
    
    DefaultTableModel createTableCols(JTable table, String[] cols) {
        DefaultTableModel dmodel;
        dmodel = (DefaultTableModel) table.getModel();
        for (int i = 0; i < cols.length; i++) {
            dmodel.addColumn(cols[i]);
        }
        return dmodel;
    }

    public void makeTable() {
        tah = createTableCols(money, new String[]{"اسم المندوب", "العنوان", "عدد الاقساط", "الباقي", "المقدم", "المنتج", "اسم العميل", "رقم العميل"});
    }

    void mandobClientTable() {

        delTblCol(tah);
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `client_recieved_items` , mandob , client,item WHERE client_recieved_items.mandob_id = mandob.id AND client_recieved_items.clientId = client.id AND client_recieved_items.item_id = item.id ;";
        String clientName, clientId, itemName, overHand, rest, qestNum, add, mandobName;
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                clientId = d.rs.getInt("client.id") + "";
                clientName = d.rs.getString("client.name");
                mandobName = d.rs.getString("mandob.name");
                itemName = d.rs.getString("item.name");
                add = d.rs.getString("client.areaName");
                qestNum = months(d.rs.getInt("client_recieved_items.id"));
                rest = d.rs.getFloat("client_recieved_items.total_payment") - d.rs.getFloat("client_recieved_items.handOver") + "";
                overHand = d.rs.getFloat("client_recieved_items.handOver") + "";
                String[] rowData = {mandobName, add, qestNum, rest, overHand, itemName, clientName, clientId};
                tah.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
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

        jScrollPane4 = new javax.swing.JScrollPane();
        money = new javax.swing.JTable();
        jLabel83 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(189, 195, 199));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        money.setAutoCreateRowSorter(true);
        money.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        money.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        money.setToolTipText("");
        money.setGridColor(new java.awt.Color(153, 153, 153));
        money.setRowHeight(45);
        money.setRowMargin(3);
        money.setShowHorizontalLines(true);
        money.setShowVerticalLines(true);
        money.setSurrendersFocusOnKeystroke(true);
        jScrollPane4.setViewportView(money);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 1120, 570));

        jLabel83.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(153, 0, 0));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 370, 60));

        jComboBox3.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "الشهر", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jComboBox3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox3KeyPressed(evt);
            }
        });
        add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 180, 60));

        jComboBox2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "السنة", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035" }));
        jComboBox2.setToolTipText("");
        jComboBox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox2KeyPressed(evt);
            }
        });
        add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, 260, 60));

        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        jLabel82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel82MouseClicked(evt);
            }
        });
        add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            delTblCol(tah);
            DBcon d = new DBcon();
            String sql2 = "SELECT * FROM `client_recieved_items` , mandob , client,item WHERE client_recieved_items.mandob_id = mandob.id AND client_recieved_items.clientId = client.id AND client_recieved_items.item_id = item.id AND client_recieved_items.received_date between '" + jComboBox2.getSelectedItem() + "-" + jComboBox3.getSelectedItem() + "-1' AND '" + (Integer.valueOf(jComboBox2.getSelectedItem().toString())) + "-" + jComboBox3.getSelectedItem() + "-31' ;";
            String clientName, clientId, itemName, overHand, rest, qestNum, add, mandobName;
            try {
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    clientId = d.rs.getInt("client.id") + "";
                    clientName = d.rs.getString("client.name");
                    mandobName = d.rs.getString("mandob.name");
                    itemName = d.rs.getString("item.name");
                    add = d.rs.getString("client.areaName");
                    qestNum = d.rs.getInt("client_recieved_items.month_count") + "";
                    rest = d.rs.getFloat("client_recieved_items.total_payment") - d.rs.getFloat("client_recieved_items.handOver") + "";
                    overHand = d.rs.getFloat("client_recieved_items.handOver") + "";
                    String[] rowData = {mandobName, add, qestNum, rest, overHand, itemName, clientName, clientId};
                    tah.addRow(rowData);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
            }
        }
    }//GEN-LAST:event_jComboBox3KeyPressed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            //JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
            delTblCol(tah);
            DBcon d = new DBcon();
            String sql2 = "SELECT * FROM `client_recieved_items` , mandob , client,item WHERE client_recieved_items.mandob_id = mandob.id AND client_recieved_items.clientId = client.id AND client_recieved_items.item_id = item.id AND client_recieved_items.received_date between '" + jComboBox2.getSelectedItem() + "-1-1' AND '" + (Integer.valueOf(jComboBox2.getSelectedItem().toString()) + 1) + "-1-1' ;";
            String clientName, clientId, itemName, overHand, rest, qestNum, add, mandobName;
            try {
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    clientId = d.rs.getInt("client.id") + "";
                    clientName = d.rs.getString("client.name");
                    mandobName = d.rs.getString("mandob.name");
                    itemName = d.rs.getString("item.name");
                    add = d.rs.getString("client.areaName");
                    qestNum = d.rs.getInt("client_recieved_items.month_count") + "";
                    rest = d.rs.getFloat("client_recieved_items.total_payment") - d.rs.getFloat("client_recieved_items.handOver") + "";
                    overHand = d.rs.getFloat("client_recieved_items.handOver") + "";
                    String[] rowData = {mandobName, add, qestNum, rest, overHand, itemName, clientName, clientId};
                    tah.addRow(rowData);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
            }

        }

    }//GEN-LAST:event_jComboBox2KeyPressed

    private void jLabel82MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel82MouseClicked
        Home.exportExcel(money, "جدول حسابات المندوب");
    }//GEN-LAST:event_jLabel82MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable money;
    // End of variables declaration//GEN-END:variables
}
