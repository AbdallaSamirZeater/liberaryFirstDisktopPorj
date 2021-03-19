/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Taqseet;

import java.time.LocalDate;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ahmed
 */
public class tahselat extends javax.swing.JPanel {

    /**
     * Creates new form recieved_items
     */
    public DefaultTableModel tah;
    static int cID = 0, type = 0;

    public tahselat(int id, String name, int type) {
        initComponents();
        jLabel78.setText(name);
        cID = id;
        this.type = type;
        makeTable();
        mandobClientTable();
        Home.styleTable(new JTable[]{tahsel});
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
        tah = createTableCols(tahsel, new String[]{"الشهر", "المبلغ ", "الاسم ", "الكود"});
    }

    void mandobClientTable() {
        if (this.type == 1) {
            delTblCol(tah);
            DBcon d = new DBcon();

            String sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND mandob.name = '" + jLabel78.getText() + "'  AND aqsat.done = 'false' AND month_payment = '" + LocalDate.now().getMonthValue() + "';";
            String clientName, id, val, date;
            try {
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    clientName = d.rs.getString("client.name");
                    id = String.valueOf(d.rs.getInt("aqsat.id"));
                    val = d.rs.getFloat("aqsat.value") + "";
                    date = "" + d.rs.getDate("aqsat.received_date");
                    String[] rowData = {date, val, clientName, id};
                    tah.addRow(rowData);
                }
            } catch (Exception e) {

            }
        } else if (this.type == 2) {

            delTblCol(tah);
            DBcon d = new DBcon();
            String sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND mandob.name = '" + jLabel78.getText() + "' AND aqsat.tahsel = true AND aqsat.done = true AND month_payment = '" + LocalDate.now().getMonthValue() + "';";
            String clientName, id, val, date;
            try {
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    clientName = d.rs.getString("client.name");
                    id = String.valueOf(d.rs.getInt("aqsat.id"));
                    val = d.rs.getFloat("aqsat.value") + "";
                    date = "" + d.rs.getDate("aqsat.received_date");
                    String[] rowData = {date, val, clientName, id};
                    tah.addRow(rowData);
                }
            } catch (Exception e) {

            }

        } else if (this.type == 3) {
            System.out.println("3");
            jLabel77.setText("اسم العميل");
            jLabel13.setText("اسم المندوب");
            delTblCol(tah);
            DBcon d = new DBcon();
            String sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND client.id = '" + cID + "' AND  aqsat.done = 1 ;";
            String clientName, id, val, date;
            try {
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    clientName = d.rs.getString("mandob.name");
                    id = String.valueOf(d.rs.getInt("aqsat.id"));
                    val = d.rs.getFloat("aqsat.value") + "";
                    date = "" + d.rs.getDate("aqsat.received_date");
                    String[] rowData = {date, val, clientName, id};
                    tah.addRow(rowData);
                }
            } catch (Exception e) {

            }

        } else if (this.type == 4) {
            System.out.println("4");
            jLabel77.setText("اسم العميل");
            jLabel13.setText("اسم المندوب");
            delTblCol(tah);
            DBcon d = new DBcon();
            String sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND client.id = '" + cID + "' AND  aqsat.done = 0 ;";
            String clientName, id, val, date;
            try {
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    clientName = d.rs.getString("mandob.name");
                    id = String.valueOf(d.rs.getInt("aqsat.id"));
                    val = d.rs.getFloat("aqsat.value") + "";
                    date = "" + d.rs.getDate("aqsat.received_date");
                    String[] rowData = {date, val, clientName, id};
                    tah.addRow(rowData);
                }
            } catch (Exception e) {

            }

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

        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tahsel = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        clientName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        item = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        handOver = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        qestNum = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        qestOrder = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();

        setBackground(new java.awt.Color(189, 195, 199));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel77.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel77.setText("اسم المندوب : ");
        add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 10, 180, 60));

        jLabel78.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(153, 0, 0));
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel78.setText("المندوب");
        add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 390, 60));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tahsel.setAutoCreateRowSorter(true);
        tahsel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tahsel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tahsel.setToolTipText("");
        tahsel.setGridColor(new java.awt.Color(153, 153, 153));
        tahsel.setRowHeight(45);
        tahsel.setRowMargin(3);
        tahsel.setShowHorizontalLines(true);
        tahsel.setShowVerticalLines(true);
        tahsel.setSurrendersFocusOnKeystroke(true);
        tahsel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tahselMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tahsel);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 690));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("الكود : ");
        add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 80, 160, 60));

        id.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        id.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });
        add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 390, 60));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("اسم العميل :");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 150, 160, 60));

        clientName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        clientName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        clientName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        clientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientNameActionPerformed(evt);
            }
        });
        add(clientName, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 390, 60));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("الصنف :");
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 290, 160, 60));

        item.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        item.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemActionPerformed(evt);
            }
        });
        add(item, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, 390, 60));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("التكلفة الكلية :");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 360, 160, 60));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("المقدم :");
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 360, 90, 60));

        handOver.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        handOver.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        handOver.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        handOver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handOverActionPerformed(evt);
            }
        });
        add(handOver, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, 110, 60));

        total.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalActionPerformed(evt);
            }
        });
        add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 360, 120, 60));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("عدد الأقساط :");
        add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 430, 160, 60));

        qestNum.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        qestNum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        qestNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        qestNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qestNumActionPerformed(evt);
            }
        });
        add(qestNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 430, 120, 60));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("ترتيب القسط :");
        add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 430, 140, 60));

        qestOrder.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        qestOrder.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        qestOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        qestOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qestOrderActionPerformed(evt);
            }
        });
        add(qestOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 430, 110, 60));

        jPanel1.setBackground(new java.awt.Color(189, 195, 199));
        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 0, 20, 560));

        jPanel2.setBackground(new java.awt.Color(189, 195, 199));
        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 380));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("شهر التحصيل :");
        add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 220, 160, 60));

        date.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        date.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        date.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateActionPerformed(evt);
            }
        });
        add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, 390, 60));
    }// </editor-fold>//GEN-END:initComponents

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

    private void clientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientNameActionPerformed

    private void itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemActionPerformed

    private void handOverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handOverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_handOverActionPerformed

    private void totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalActionPerformed

    private void qestNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qestNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qestNumActionPerformed

    private void qestOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qestOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qestOrderActionPerformed

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void tahselMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tahselMouseClicked
        int n = tahsel.getSelectedRow();

        String date = tahsel.getValueAt(n, 0).toString();
        String val = tahsel.getValueAt(n, 1).toString();
        String client = tahsel.getValueAt(n, 2).toString();
        String id = tahsel.getValueAt(n, 3).toString();
        float hand = 0, value = 0;
        DBcon d = new DBcon();
        try {
            float temp = 0;
            int count = 0;
            System.out.println("abdo");
            d.rs = d.st.executeQuery("SELECT * FROM aqsat,client WHERE aqsat.client_id = client.id AND client.name ='" + client + "';");
            while (d.rs.next()) {
                temp += d.rs.getFloat("value");

            }

            d.rs = d.st.executeQuery("SELECT * FROM client,aqsat,client_recieved_items,item WHERE  aqsat.client_id = client.id AND aqsat.CRI = client_recieved_items.id AND client_recieved_items.item_id = item.id AND aqsat.id ='" + id + "';");
            while (d.rs.next()) {

                hand = d.rs.getFloat("client_recieved_items.handOver");
                d.rs.getString("client_recieved_items.handOver");
                this.id.setText(id);
                this.clientName.setText(client);
                this.date.setText(date);
                this.handOver.setText(d.rs.getString("client_recieved_items.handOver"));
                this.item.setText(d.rs.getString("item.name"));
                this.total.setText(d.rs.getString("client_recieved_items.total_payment"));
                this.qestNum.setText(d.rs.getString("client_recieved_items.month_count"));
                this.qestOrder.setText(d.rs.getString("aqsat.order"));
            }

        } catch (Exception e) {

        }
    }//GEN-LAST:event_tahselMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField clientName;
    private javax.swing.JTextField date;
    private javax.swing.JTextField handOver;
    private javax.swing.JTextField id;
    private javax.swing.JTextField item;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField qestNum;
    private javax.swing.JTextField qestOrder;
    private javax.swing.JTable tahsel;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
