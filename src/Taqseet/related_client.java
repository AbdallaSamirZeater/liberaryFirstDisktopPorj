/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Taqseet;

import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ahmed
 */
public class related_client extends javax.swing.JPanel {

    /**
     * Creates new form recieved_items
     */
    public DefaultTableModel clients;
    static int cID = 0;

    public related_client(int id, String name) {
        initComponents();
        mandobeName.setText(name);
        makeTable();
        mandobClientTable();
        Home.styleTable(new JTable[]{clientTable});
        cID = id;
    }

    public void delTblCol(DefaultTableModel table) {
        if (table.getRowCount() != 0) {
            for (int i = table.getRowCount() - 1; i >= 0; i--) {
                table.removeRow(i);

            }
        }
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
        clients = createTableCols(clientTable, new String[]{"المنطقة", "اسم العميل", "الكود"});
    }

    void deleteRepeted() {
        try {
            String sql2;
            DBcon d = new DBcon();
            int c = 0;
            int id = 0;
            sql2 = "SELECT * FROM `client` , mandob ,`aqsat` WHERE  aqsat.client_id = client.id  AND  aqsat.mandob_id = mandob.id  AND mandob.name = '" + mandobeName.getText() + "';";

            d.rs = d.st.executeQuery(sql2);

            while ((d.rs).next()) {
                c = 0;
                id = d.rs.getInt("client.id");
                for (int i = 0; i < clientTable.getRowCount(); i++) {
                    if (id == Integer.valueOf((String) clientTable.getValueAt(i, 2))) {
                        c++;
                    }
                    if (c > 1) {
                        clients.removeRow(i);
                        c--;
                    }
                }
            }

        } catch (Exception e) {

        }
    }

    void mandobClientTable() {
        delTblCol(clients);
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `client` , mandob , client_recieved_items WHERE  client.id = client_recieved_items.clientId AND mandob.id = client_recieved_items.mandob_id AND mandob.name = '" + mandobeName.getText() + "';  ";
        String name, id, address;

        try {

            sql2 = "SELECT * FROM `client` , mandob ,`aqsat` WHERE  aqsat.client_id = client.id  AND  aqsat.mandob_id = mandob.id  AND mandob.name = '" + mandobeName.getText() + "';";

            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name = d.rs.getString("client.name");
                id = String.valueOf(d.rs.getInt("client.id"));
                address = d.rs.getString("client.address1");

                String[] rowData = {address, name, id};
                clients.addRow(rowData);
            }

        } catch (Exception e) {

        }
        deleteRepeted();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel74 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        mandobeName = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        add2 = new javax.swing.JTextField();
        jopPlace = new javax.swing.JTextField();
        clientId = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        clientName = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cardNum = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        add1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        mob2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        mob1 = new javax.swing.JTextField();
        jop = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        qestNum = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(189, 195, 199));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 30, 60));

        jLabel77.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel77.setText("اسم المندوب : ");
        add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 160, 60));

        mandobeName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mandobeName.setForeground(new java.awt.Color(153, 0, 0));
        mandobeName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mandobeName.setText("المندوب");
        add(mandobeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 300, 60));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        clientTable.setAutoCreateRowSorter(true);
        clientTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        clientTable.setToolTipText("");
        clientTable.setGridColor(new java.awt.Color(153, 153, 153));
        clientTable.setRowHeight(45);
        clientTable.setRowMargin(3);
        clientTable.setShowHorizontalLines(true);
        clientTable.setShowVerticalLines(true);
        clientTable.setSurrendersFocusOnKeystroke(true);
        clientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(clientTable);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 700));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("الكود : ");
        add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 90, 170, 60));

        add2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        add2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        add2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add2ActionPerformed(evt);
            }
        });
        add(add2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 300, 195, 60));

        jopPlace.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jopPlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jopPlace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jopPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jopPlaceActionPerformed(evt);
            }
        });
        add(jopPlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 440, 195, 60));

        clientId.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        clientId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        clientId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        clientId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientIdActionPerformed(evt);
            }
        });
        add(clientId, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 390, 60));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("اسم العميل :");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 160, 170, 60));

        clientName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        clientName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        clientName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        clientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientNameActionPerformed(evt);
            }
        });
        add(clientName, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, 390, 60));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("رقم البطاقة :");
        add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 230, 170, 60));

        cardNum.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        cardNum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cardNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        cardNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardNumActionPerformed(evt);
            }
        });
        add(cardNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 230, 390, 60));

        jLabel36.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("الحي / المصلحة :");
        add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 300, 190, 60));

        add1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        add1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add1ActionPerformed(evt);
            }
        });
        add(add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 300, 200, 60));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("الموبايل  :");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 370, 170, 60));

        mob2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mob2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mob2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mob2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mob2ActionPerformed(evt);
            }
        });
        add(mob2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 370, 195, 60));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("المهنة / المكان :");
        add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 440, 180, 60));

        mob1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mob1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mob1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mob1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mob1ActionPerformed(evt);
            }
        });
        add(mob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 370, 200, 60));

        jop.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jop.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jopActionPerformed(evt);
            }
        });
        add(jop, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 440, 200, 60));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("عدد الأقساط :");
        add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 510, 170, 60));

        qestNum.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        qestNum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        qestNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        qestNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qestNumActionPerformed(evt);
            }
        });
        add(qestNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 510, 390, 60));

        jPanel1.setBackground(new java.awt.Color(189, 195, 199));
        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 0, 20, 560));

        jPanel2.setBackground(new java.awt.Color(189, 195, 199));
        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 380));

        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        jLabel87.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel87MouseClicked(evt);
            }
        });
        add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void clientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientIdActionPerformed

    private void clientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientNameActionPerformed

    private void cardNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cardNumActionPerformed

    private void add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add1ActionPerformed

    private void add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add2ActionPerformed

    private void mob2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mob2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mob2ActionPerformed

    private void mob1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mob1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mob1ActionPerformed

    private void jopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jopActionPerformed

    private void jopPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jopPlaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jopPlaceActionPerformed

    private void qestNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qestNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qestNumActionPerformed

    private void clientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientTableMouseClicked

        int counter = 0;
        String clientID, job = null;
        String cardNum = null;
        String add2 = null, add1 = null;
        String jopPlace = null;
        String mob1 = null;
        String clientName = null;
        int n = clientTable.getSelectedRow();
        String mob2 = null;
        DBcon d = new DBcon();
        clientID = clients.getValueAt(n, 2).toString();
        clientName = clients.getValueAt(n, 1).toString();
        add1 = clients.getValueAt(n, 0).toString();

        String sql2 = "SELECT * FROM client WHERE id = " + clientID + ";";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                cardNum = d.rs.getString("card_num");
                add2 = d.rs.getString("address2");
                mob1 = d.rs.getString("mob1");
                mob2 = d.rs.getString("mob2");
                jopPlace = d.rs.getString("job_place");
                job = d.rs.getString("job");
            }
            d.rs = d.st.executeQuery("select * from aqsat where client_id = '" + clientID + "'");
            while ((d.rs).next()) {
                counter++;
            }
        } catch (SQLException ex) {

        }
        this.mob1.setText(mob1);
        this.mob2.setText(mob2);
        this.jopPlace.setText(jopPlace);
        this.cardNum.setText(String.valueOf(cardNum));
        this.add2.setText(add2);
        this.add1.setText(add1);
        this.jop.setText(job);
        this.clientName.setText(clientName);
        this.clientId.setText(clientID);
        this.qestNum.setText(String.valueOf(counter));

    }//GEN-LAST:event_clientTableMouseClicked

    private void jLabel87MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel87MouseClicked
        Home.exportExcel(clientTable, "جدول عملاء المندوب");
    }//GEN-LAST:event_jLabel87MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField add1;
    private javax.swing.JTextField add2;
    private javax.swing.JTextField cardNum;
    private javax.swing.JTextField clientId;
    private javax.swing.JTextField clientName;
    private javax.swing.JTable clientTable;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jop;
    private javax.swing.JTextField jopPlace;
    private javax.swing.JLabel mandobeName;
    private javax.swing.JTextField mob1;
    private javax.swing.JTextField mob2;
    private javax.swing.JTextField qestNum;
    // End of variables declaration//GEN-END:variables
}
