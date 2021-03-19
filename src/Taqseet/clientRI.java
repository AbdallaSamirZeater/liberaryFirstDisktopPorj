/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Taqseet;

import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ahmed
 */
public class clientRI extends javax.swing.JPanel {

    /**
     * Creates new form recieved_items
     */
    static public int[] Mman;
    public DefaultTableModel ReceivedItems;
    static int cID;

    public clientRI(String name, String type, int i) {
        initComponents();
        Home.styleTable(new JTable[]{recivedItemTable});
        cID = i;
        this.name.setText(name);
        //this.total.setText(String.valueOf(TRM(cID)));
        makeTable();
        mandobReceivedItemsTable();
        combobox();
        total();

    }

    void mandob(JComboBox j) {
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `mandob`;";
        j.removeAllItems();
        j.addItem("اختر");
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                j.addItem(d.rs.getString("name"));
            }
        } catch (Exception e) {

        }
    }

    void combobox() {

        mandob(recivedMandob);
        mandob(tahselMandob);
    }

    void total() {

        try {
            int id = 0;
            float total = 0;
            DBcon d = new DBcon();
            d.rs = d.st.executeQuery("select * from client_recieved_items WHERE clientId = '" + cID + "';");
            while (d.rs.next()) {
                total += d.rs.getFloat("total_payment");
            }
            this.total.setText(String.valueOf(total));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    void tahsel() {
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        int id, qestId, manId;
        try {
            d.rs = d.st.executeQuery("select * from aqsat WHERE month_payment = '" + LocalDate.now().getMonthValue() + "' AND tahsel = false");
            while (d.rs.next()) {
                id = d.rs.getInt("id");
                manId = d.rs.getInt("mandob_id");
                db.con.createStatement();
                db.st.executeUpdate("UPDATE `aqsat` SET `tahsel` = '1' WHERE `aqsat`.`id` = '" + id + "';");
                db.con.createStatement();
                db.st.executeUpdate("INSERT INTO `tahsel` (`id`, `mandob_id`, `paid`, `done`, `Tahsel_percentage`, `qest_id`) VALUES (NULL, '" + manId + "', '0', '0', '0', '" + id + "');");
            }
        } catch (Exception e) {
        }
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

    void get_paid_rest() {
        int n = recivedItemTable.getSelectedRow();
        float val1 = 0, val2 = 0;
        DBcon d = new DBcon();
        try {

            d.rs = d.st.executeQuery("select * from client_recieved_items where id =  '" + recivedItemTable.getValueAt(n, 3).toString() + "';");
            d.rs.last();
            val1 = d.rs.getFloat("handOver");

            d.rs = d.st.executeQuery("select * from aqsat where aqsat.done = '1' AND aqsat.CRI = '" + recivedItemTable.getValueAt(n, 3).toString() + "';");
            while (d.rs.next()) {
                val1 += d.rs.getFloat("value");
            }
        } catch (Exception e) {
        }
        total1.setText(val1 + "");

        try {
            d.rs = d.st.executeQuery("select * from aqsat where aqsat.done = '0' AND aqsat.CRI = '" + recivedItemTable.getValueAt(n, 3).toString() + "';");
            while (d.rs.next()) {
                val2 += d.rs.getFloat("value");
            }
        } catch (Exception e) {
        }
        total2.setText(val2 + "");

    }

    float get_price(int id) {
        float price = 0;
        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("select * from item where item.id = '" + id + "';");
            d.rs.last();
            price = d.rs.getFloat("Taqset_price");
        } catch (Exception e) {
        }

        return price;
    }

//      float TRM(int id){
//         float total = 0 ;
//         DBcon d = new DBcon();
//             try {
//             d.rs = d.st.executeQuery("select * from mandob where mandob.id = '"+id+"';");
//             while(d.rs.next())
//             total += d.rs.getFloat("total_recieved_money");
//         } catch (Exception e) {
//         }
//
//         return total;
//     }
    int auto_insert(String s, String s2) {
        int x = 0;
        DBcon d = new DBcon();
        String sql = "SELECT * FROM `" + s + "`";
        try {
            d.rs = d.st.executeQuery(sql);
            while ((d.rs).next()) {

                if ((d.rs.getInt(s2)) == x) {
                    x++;
                }
            }
        } catch (SQLException ex) {

        }
        return x;
    }

    public void makeTable() {
        ReceivedItems = createTableCols(recivedItemTable, new String[]{"التاريخ", "الكمية", "الصنف", "الكود"});
    }

    void mandobReceivedItemsTable() {
        delTblCol(ReceivedItems);
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `client_recieved_items` , client , item WHERE client_recieved_items.clientId = client.id AND client_recieved_items.item_id = item.id AND client.id = '" + cID + "';";
        String name, id, quant, date;
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name = d.rs.getString("item.name");
                id = String.valueOf(d.rs.getInt("client_recieved_items.id"));
                quant = String.valueOf(d.rs.getInt("client_recieved_items.quantity"));
                date = String.valueOf(d.rs.getDate("client_recieved_items.received_date"));
                String[] rowData = {date, quant, name, id};
                ReceivedItems.addRow(rowData);
            }
        } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
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

        jLabel76 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        reciveDate = new javax.swing.JTextField();
        reciveQuant = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        reciveItemId = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        reciveItem = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        recivedItemTable = new javax.swing.JTable();
        jLabel82 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        qestCount = new javax.swing.JTextField();
        PType = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        overHand = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        recivedMandob = new javax.swing.JComboBox<>();
        jLabel87 = new javax.swing.JLabel();
        reciveItemId1 = new javax.swing.JTextField();
        tahselMandob = new javax.swing.JComboBox<>();
        jLabel88 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        pricee = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        excelStock2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(189, 195, 199));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel76.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel76.setText("الكود : ");
        add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 70, 130, 60));

        jLabel79.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel79.setText("النوع :");
        add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 280, 140, 60));

        reciveDate.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        reciveDate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        reciveDate.setText(LocalDate.now()+"");
        reciveDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        reciveDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reciveDateActionPerformed(evt);
            }
        });
        add(reciveDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 160, 60));

        reciveQuant.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        reciveQuant.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        reciveQuant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        reciveQuant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reciveQuantActionPerformed(evt);
            }
        });
        add(reciveQuant, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 160, 60));

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/multi.png"))); // NOI18N
        jLabel77.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel77MouseClicked(evt);
            }
        });
        add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 420, 70, 60));

        reciveItemId.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        reciveItemId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        reciveItemId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        reciveItemId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reciveItemIdActionPerformed(evt);
            }
        });
        add(reciveItemId, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 120, 60));

        jLabel80.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel80.setText("الكمية :");
        add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, 80, 60));

        jLabel81.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel81.setText("التاريخ :");
        add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 90, 60));

        reciveItem.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        reciveItem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        reciveItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        reciveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reciveItemActionPerformed(evt);
            }
        });
        add(reciveItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, 380, 60));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        recivedItemTable.setAutoCreateRowSorter(true);
        recivedItemTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        recivedItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        recivedItemTable.setToolTipText("");
        recivedItemTable.setGridColor(new java.awt.Color(153, 153, 153));
        recivedItemTable.setRowHeight(45);
        recivedItemTable.setRowMargin(3);
        recivedItemTable.setShowHorizontalLines(true);
        recivedItemTable.setShowVerticalLines(true);
        recivedItemTable.setSurrendersFocusOnKeystroke(true);
        recivedItemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recivedItemTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(recivedItemTable);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 580, 630));

        jLabel82.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel82.setText("اسم العميل :");
        add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 140, 60));

        name.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        name.setForeground(new java.awt.Color(153, 0, 0));
        name.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        name.setText("حمادة حمادة");
        add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 380, 60));

        total.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        total.setForeground(new java.awt.Color(153, 0, 0));
        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 160, 60));

        jLabel85.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel85.setText("قيمة الأصناف المستلمة :");
        add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 250, 60));

        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(153, 0, 0));
        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/approval.png"))); // NOI18N
        jLabel86.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel86MouseClicked(evt);
            }
        });
        add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 640, 50, 50));

        jLabel83.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel83.setText("عدد الاقساط :");
        add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 350, 140, 60));

        qestCount.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        qestCount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        qestCount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        qestCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qestCountActionPerformed(evt);
            }
        });
        add(qestCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 350, 120, 60));

        PType.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        PType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "اختر نوع", "قسط", "كاش" }));
        PType.setToolTipText("");
        PType.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        PType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PTypeActionPerformed(evt);
            }
        });
        add(PType, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 280, 120, 60));

        jLabel84.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel84.setText("المقدم :");
        add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 350, 100, 60));

        overHand.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        overHand.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        overHand.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        overHand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overHandActionPerformed(evt);
            }
        });
        add(overHand, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, 160, 60));

        jLabel78.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel78.setText("م التحصيل :");
        add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 490, 140, 60));

        recivedMandob.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        recivedMandob.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        add(recivedMandob, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 420, 320, 60));

        jLabel87.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel87.setText("ك الصنف :");
        add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 140, 140, 60));

        reciveItemId1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        reciveItemId1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        reciveItemId1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        reciveItemId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reciveItemId1ActionPerformed(evt);
            }
        });
        add(reciveItemId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 120, 60));

        tahselMandob.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        tahselMandob.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tahselMandob.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        add(tahselMandob, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, 320, 60));

        jLabel88.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel88.setText("الصنف : ");
        add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 210, 140, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/change.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 490, 60, -1));

        jLabel89.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel89.setText("م التسليم :");
        add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 420, 140, 60));

        jLabel90.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel90.setText("السعر : ");
        add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 280, 80, 60));

        pricee.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        pricee.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pricee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        add(pricee, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, 160, 60));

        jLabel91.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel91.setText("المدفوع :");
        add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 570, 110, 60));

        total1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        total1.setForeground(new java.awt.Color(153, 0, 0));
        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 570, 160, 60));

        jLabel92.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel92.setText("المتبقي :");
        add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 570, 110, 60));

        total2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        total2.setForeground(new java.awt.Color(153, 0, 0));
        total2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(total2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 570, 160, 60));

        excelStock2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock2MouseClicked(evt);
            }
        });
        add(excelStock2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 10, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void reciveDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reciveDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reciveDateActionPerformed

    private void reciveQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reciveQuantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reciveQuantActionPerformed

    private void reciveItemIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reciveItemIdActionPerformed
        DBcon d = new DBcon();
        try {

            d.rs = d.st.executeQuery("select * from item WHERE id = '" + reciveItemId.getText() + "';");
            while (d.rs.next()) {

                reciveItem.setText(d.rs.getString("name"));;

            }

        } catch (Exception e) {

        }
    }//GEN-LAST:event_reciveItemIdActionPerformed

    private void reciveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reciveItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reciveItemActionPerformed

    private void recivedItemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recivedItemTableMouseClicked

        String id = null, ItemName = "unknown", quant = null, price = null, date, itemId = null;
        int n = recivedItemTable.getSelectedRow();
        ItemName = recivedItemTable.getValueAt(n, 2).toString();
        id = recivedItemTable.getValueAt(n, 3).toString();
        quant = recivedItemTable.getValueAt(n, 1).toString();
        date = recivedItemTable.getValueAt(n, 0).toString();
        String overHand = null, qestNum = null, mandobRecived = null, mandobtahsel = null, type = null, tot = null;

        DBcon d = new DBcon();
        try {

            String sql2 = "SELECT * FROM `client_recieved_items`,mandob,client,item WHERE client_recieved_items.clientId = client.id AND client_recieved_items.mandob_id = mandob.id AND client_recieved_items.item_id = item.id AND client_recieved_items.id = '" + id + "';";

            d.rs = d.st.executeQuery(sql2);
            d.rs.last();
            qestNum = String.valueOf(d.rs.getInt("month_count"));
            overHand = String.valueOf(d.rs.getInt("handOver"));
            mandobRecived = d.rs.getString("mandob.name");
            itemId = String.valueOf(d.rs.getInt("item.id"));
            type = d.rs.getString("client_recieved_items.payment_type");
            tot = String.valueOf(d.rs.getFloat("client_recieved_items.total_payment"));

            d.rs = d.st.executeQuery("select * from item where item.id = '" + itemId + "'");
            d.rs.last();
            if (type.equals("كاش")) {
                price = String.valueOf(d.rs.getFloat("item.cash_price")*Integer.valueOf(quant));
            } else {
                price = String.valueOf(d.rs.getFloat("item.Taqset_price")*Integer.valueOf(quant));
            }

//            d.rs = d.st.executeQuery("SELECT * FROM mandob WHERE id = '"+mandobId+"';");
//            while ((d.rs).next()){
//
//            }
            sql2 = "SELECT * FROM mandob,aqsat WHERE  aqsat.mandob_id = mandob.id AND  aqsat.CRI = '" + id + "';";

            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                mandobtahsel = d.rs.getString("mandob.name");
            }

            //   d.st = d.con.createStatement();
            //   d.st.executeUpdate("UPDATE aqsat set mandob_id = '" + mandobId + "' WHERE CRI = '" + id + "';");
        } catch (SQLException ex) {

        }
        reciveItemId1.setText(id);
        pricee.setText(price);
        this.PType.setSelectedItem(type);
        reciveItemId.setText(itemId);
        reciveItem.setText(ItemName);
        reciveQuant.setText(quant);
        this.overHand.setText(overHand);
        tahselMandob.setSelectedItem(mandobtahsel);
        recivedMandob.setSelectedItem(mandobRecived);
        qestCount.setText(qestNum);
        reciveDate.setText(date);
        if (type.equals("قسط")) {
            get_paid_rest();
        } else {
            total1.setText(tot);
            total2.setText("0");

        }
        if (mandobRecived.equals("متعدد")) {

            MultiManTable m = new MultiManTable(Integer.valueOf(id));
            m.setVisible(true);
        }
    }//GEN-LAST:event_recivedItemTableMouseClicked

    private void jLabel86MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel86MouseClicked
        // insert recivingItem

        String CRI;

        CRI = String.valueOf(auto_insert("client_recieved_items", "id"));
        int mandobId2 = 0;
        int mandobId = 0;
        int year = LocalDate.now().getYear();
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        float presentage = 0;
        try {

            if (!(recivedMandob.getSelectedItem().toString().equals("متعدد"))) {
                d.rs = d.st.executeQuery("select * from mandob where name = '" + recivedMandob.getSelectedItem() + "';");
                d.rs.last();
                mandobId = d.rs.getInt("id");
            } else {
                mandobId = 0;
            }

            int Mcounter = (LocalDate.now().getMonthValue());
            d.rs = d.st.executeQuery("select * from item WHERE id = '" + reciveItemId.getText() + "';");
            while (d.rs.next()) {
                presentage = d.rs.getFloat("selling_percentage");
            }

            float total = (Integer.valueOf(reciveQuant.getText())) * (get_price(Integer.valueOf(reciveItemId.getText())));

            if ((this.PType.getSelectedItem()).equals("قسط")) {
                d.rs = d.st.executeQuery("select * from mandob where name = '" + tahselMandob.getSelectedItem() + "';");
                d.rs.last();
                mandobId2 = d.rs.getInt("id");
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `client_recieved_items` (`id`, `clientId`,`mandob_id`, `item_id`, `handOver`, `quantity`, `received_date`, `month_count`, `total_payment`,`payment_type`) VALUES ('" + CRI + "', '" + cID + "','" + mandobId + "','" + reciveItemId.getText() + "','" + overHand.getText() + "',  '" + reciveQuant.getText() + "', '" + reciveDate.getText() + "', '" + qestCount.getText() + "', '" + (Integer.valueOf(reciveQuant.getText())) * (get_price(Integer.valueOf(reciveItemId.getText()))) + "','" + PType.getSelectedItem() + "');");
                float qestvalue = (((Integer.valueOf(reciveQuant.getText())) * (get_price(Integer.valueOf(reciveItemId.getText()))) - (Float.valueOf(overHand.getText()))) / Integer.valueOf(qestCount.getText()));
                for (int i = 0; i < Integer.valueOf(qestCount.getText()); i++) {
                    Mcounter++; //(i + 1);
                    if (Mcounter > 12) {
                        Mcounter -= 12;
                        year += 1;
                        System.out.println(year + " : " + Mcounter);
                    }
                    db.st = db.con.createStatement();
                    db.st.executeUpdate("INSERT INTO `aqsat` (`id`,mandob_id,client_id,`month_payment`, `value`, `tahsel`, `received_date`,`order`, `CRI`) VALUES (NULL,'" + mandobId2 + "', '" + cID + "', '" + Mcounter + "', '" + qestvalue + "', '" + 0 + "', ' " + year + "-" + Mcounter + "-25', '" + (i + 1) + "', '" + CRI + "');");
                }
            } else {

                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `client_recieved_items` (`id`, `clientId`,`mandob_id`, `item_id`, `handOver`, `quantity`, `received_date`, `month_count`, `total_payment`,`payment_type`) VALUES ('" + CRI + "', '" + cID + "','" + mandobId + "','" + reciveItemId.getText() + "','0',  '" + reciveQuant.getText() + "', '" + reciveDate.getText() + "', '" + 0 + "', '" +  Float.valueOf(this.pricee.getText()) + "','" + PType.getSelectedItem() + "');");

//                db.st = db.con.createStatement();
//                db.st.executeUpdate("INSERT INTO `aqsat` (`id`,mandob_id,client_id,`month_payment`, `value`, `tahsel`, `received_date`,`order`,done, `CRI`) VALUES (NULL,'" + mandobId + "', '" + cID + "', '" + ((LocalDate.now().getMonthValue())) + "', '" + Float.valueOf(this.price.getText()) * Integer.valueOf(reciveQuant.getText()) + "', '" + 1 + "', '" + LocalDate.now() + "', '" + 1 + "', '1' ,'" + CRI + "');");
            }

            d.rs = d.st.executeQuery("select * from mandob where name = '" + recivedMandob.getSelectedItem() + "';");
            d.rs.last();
            mandobId = d.rs.getInt("id");
            if (!(recivedMandob.getSelectedItem().toString().equals("متعدد"))) {

                float totalRicivedMony = d.rs.getFloat("total_recieved_money");
                float newRecived = Integer.valueOf(reciveQuant.getText()) * presentage + totalRicivedMony;
                d.st = d.con.createStatement();
                d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + newRecived + "' WHERE id = '" + mandobId + "';");
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `mandobrecivables` (`id`, `mandobId`, `value`, `type`, `clientId`, `date`) VALUES (NULL, '" + mandobId + "', '" + Integer.valueOf(reciveQuant.getText()) * presentage + "', '"+reciveItem.getText()+" تسليم', '" + cID + "', '" + LocalDate.now() + "');");

                d.rs = d.st.executeQuery("select * from mandob_received_items where item_id = '" + reciveItemId.getText() + "' AND mandob_id = '" + mandobId + "';");
                d.rs.last();
                int oldquant = d.rs.getInt("quantity");
                int MRI = d.rs.getInt("id");
                d.st = d.con.createStatement();
                d.st.executeUpdate("UPDATE `mandob_received_items` SET quantity = '" + (oldquant - Integer.valueOf(reciveQuant.getText())) + "' WHERE id = '" + MRI + "' ;");

            } else {
                mandobId = 0;

                int oldq = 0, newq = 0;
                d.rs = d.st.executeQuery("SELECT * FROM item WHERE item.id = '" + reciveItemId.getText() + "';");
                d.rs.last();
                oldq = d.rs.getInt("quantity");
                newq = oldq - Integer.valueOf(reciveQuant.getText());
                d.st = d.con.createStatement();
                d.st.executeUpdate("UPDATE item SET quantity = '" + newq + "' WHERE item.id = '" + reciveItemId.getText() + "';");

                for (int i = 0; i < Mman.length; i++) {
                    System.out.println(Mman.length);
                    d.st = d.con.createStatement();
                    d.st.executeUpdate("INSERT INTO `multiman` (`id`, mandob_id, `CRI`) VALUES (NULL,'" + Mman[i] + "','" + CRI + "');");

                    d.rs = d.st.executeQuery("select * from mandob where id = '" + Mman[i] + "';");
                    d.rs.last();
                    float totalRicivedMony = d.rs.getFloat("total_recieved_money");
                    float newRecived = Integer.valueOf(reciveQuant.getText()) * presentage / (Mman.length - 1) + totalRicivedMony;
                    d.st = d.con.createStatement();
                    d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + newRecived + "' WHERE id = '" + Mman[i] + "';");

                    d.st = d.con.createStatement();
                    d.st.executeUpdate("INSERT INTO `mandobrecivables` (`id`, `mandobId`, `value`, `type`, `clientId`, `date`) VALUES (NULL, '" + Mman[i] + "', '" + Integer.valueOf(reciveQuant.getText()) * presentage / (Mman.length - 1) + "', '"+reciveItem.getText()+" تسليم', '" + cID + "', '" + LocalDate.now() + "');");

                }
            }

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation` , type) VALUES (NULL, '" + cID + "', '" + this.name.getText() + "', '" + LocalDate.now() + "', '" + reciveQuant.getText() + "تسليم عميل بضاعة بكمية ' , 'client');");
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>" + e);
                             // JOptionPane.showMessageDialog(null, e);

        }

        total();
        mandobReceivedItemsTable();
    }//GEN-LAST:event_jLabel86MouseClicked

    private void qestCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qestCountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qestCountActionPerformed

    private void overHandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overHandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_overHandActionPerformed

    private void reciveItemId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reciveItemId1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reciveItemId1ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        int id = Integer.valueOf(reciveItemId1.getText());
        DBcon d = new DBcon();
        String mandobName = (String) tahselMandob.getSelectedItem();
        int mandobId = 0;
        try {
            d.rs = d.st.executeQuery("SELECT * FROM mandob WHERE name = '" + mandobName + "';");
            while ((d.rs).next()) {
                mandobId = d.rs.getInt("id");
            }
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE aqsat SET mandob_id = '" + mandobId + "' WHERE CRI = '" + id + "';");
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel77MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel77MouseClicked
        multiMan r = new multiMan();

        recivedMandob.setSelectedItem("متعدد");
        r.setVisible(true);

    }//GEN-LAST:event_jLabel77MouseClicked

    private void PTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PTypeActionPerformed
        String cash = null, qest = null;
        DBcon d = new DBcon();
        try {

            d.rs = d.st.executeQuery("select * from item WHERE id = '" + reciveItemId.getText() + "';");
            while (d.rs.next()) {
                cash = String.valueOf(d.rs.getFloat("cash_price"));
                qest = String.valueOf(d.rs.getFloat("Taqset_price"));
            }

        } catch (Exception e) {

        }

        if (PType.getSelectedItem().equals("قسط")) {
            qestCount.setEnabled(true);
            overHand.setEnabled(true);
            tahselMandob.setEnabled(true);
            pricee.setText(Integer.valueOf(qest)*Integer.valueOf(reciveQuant.getText())+"");

        } else {
            qestCount.setEnabled(false);
            overHand.setEnabled(false);
            tahselMandob.setEnabled(false);
            pricee.setText(Integer.valueOf(cash)*Integer.valueOf(reciveQuant.getText())+"");
        }

    }//GEN-LAST:event_PTypeActionPerformed

    private void excelStock2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock2MouseClicked
        Home.exportExcel(recivedItemTable, "جدول الاصناف المستلمة");
    }//GEN-LAST:event_excelStock2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> PType;
    private javax.swing.JLabel excelStock;
    private javax.swing.JLabel excelStock1;
    private javax.swing.JLabel excelStock2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel name;
    private javax.swing.JTextField overHand;
    private javax.swing.JTextField pricee;
    private javax.swing.JTextField qestCount;
    private javax.swing.JTextField reciveDate;
    private javax.swing.JTextField reciveItem;
    private javax.swing.JTextField reciveItemId;
    private javax.swing.JTextField reciveItemId1;
    private javax.swing.JTextField reciveQuant;
    private javax.swing.JTable recivedItemTable;
    private javax.swing.JComboBox<String> recivedMandob;
    private javax.swing.JComboBox<String> tahselMandob;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total2;
    // End of variables declaration//GEN-END:variables
}
