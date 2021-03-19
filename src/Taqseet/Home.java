package Taqseet;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;

/*
 * @author ahmed
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public String QestValues[] = new String[19];
    static public int pId = -1;
    static String filePath = "E:\\Hash";

    String id, name, cardNum, add, mob, tahsel;
    int panel_num = 1;
    static String desktopPath = System.getProperty("user.home") + "\\Desktop\\";
    public DefaultTableModel stockTable, recipt, receivedMony, areaTable, tahsealMan, clientsTahsel, qestTahsel, returnT, notesT, reportT, userTable, blockedTable;
    Process process;

    public Home() {
        try {
            process = Runtime.getRuntime().exec(new String[]{"wmic", "bios", "get", "SerialNumber"});
            process.getOutputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

        File directory = new File(filePath);
        try {
            if (!directory.isDirectory()) {
                directory.mkdir();
                if (!directory.isDirectory()) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            filePath = "D:\\Hash";
            directory = new File(filePath);
            if (!directory.isDirectory()) {
                directory.mkdir();
            }
        }
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        // System.out.println(serial);
        if (!serial.equals("2CE9030BKJ")) {

            initComponents();
            makeTable();
            this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setColor(pane1, label1, markerPanel1);
            resetColor(new JPanel[]{pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label2, label3, label4, label5, label6, label7, label8, label9, label14, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(homePanel);
            hidePanel(new JPanel[]{stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            tahsel();
            tahselTables();
            report();
            combobox();
            mandobReceivedItemsTable();
            numbers0();
            recipt();
            styleTable(new JTable[]{stock_table, reciptT, returnTable, reports, recivedMonyT, qest, notesTable, mandob, clients, blockedT, areaT});
            totalSalesPrice();
        } else {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>عذرا لا يمكنك تشغيل هذا البرنامج .. تواصل معنا للحصول علي نسختك </h3></html>");
        }

    }

    public Home(String id, String name, String cardNum, String add, String mob, String tahsel) {
        initComponents();
        mandobId.setText(id);
        mandobName.setText(name);
        mandobCardName.setText(cardNum);
        mandobLocation.setText(add);
        mandobMobile.setText(mob);
        mandobCollectionRate.setText(tahsel);
    }

    void addDefaultBranch() {

        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `branch` WHERE branch.id = 0";
        int flag = 0;
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                flag = 1;
                break;
            }
            if (flag == 0) {
                String sql = "INSERT INTO `branch` (id,`name`) VALUES ('0','فرع جديد');";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql);
            }
            d.con.close();
        } catch (Exception e) {

        }

    }

    void totalAqsatVal() {
        float val = 0;

        for (int i = 0; i < reciptT.getRowCount(); i++) {
            val += Float.valueOf((String) reciptT.getValueAt(i, 6));
        }
        jLabel112.setText("" + val);
    }

//
//    int uniqe(String table, String col, String filed) {
//        DBcon d = new DBcon();
//        int counter = 0;
//        try {
//
//            d.rs = d.st.executeQuery("SELECT * FROM '" + table + "'");
//            while (d.rs.next()) {
//                if (Integer.valueOf(filed) == d.rs.getInt(col)) {
//                    counter++;
//                }
//            }
//            d.con.close();
//        } catch (Exception e) {
//        }
//        if (counter >= 2) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }
    void getMandob() {
        if (pId != -1) {
            String name1, id = null, password = null;
            DBcon d = new DBcon();

            String sql2 = "SELECT * FROM `mandob` WHERE id = '" + pId + "';";
            try {
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    mandobId.setText(String.valueOf(d.rs.getString("id")));
                    mandobName.setText(d.rs.getString("name"));
                    mandobCardName.setText(String.valueOf(d.rs.getInt("card_num")));
                    mandobLocation.setText(d.rs.getString("	address"));
                    mandobMobile.setText(d.rs.getString("mob"));
                    mandobCollectionRate.setText(String.valueOf(d.rs.getFloat("Tahsel_percentage")));

                }
                d.con.close();
            } catch (SQLException ex) {

            }
        }
    }

    void totalSalesPrice() {

        DBcon d = new DBcon();
        float count = 0;
        String sql2 = "SELECT * FROM `item`;";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {

                count += d.rs.getFloat("buy_price") * d.rs.getInt("quantity");

            }
            d.con.close();

            totalSalesPrice.setText(count + "");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
        }

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
            d.con.close();
        } catch (Exception e) {
        }
    }

    void area(JComboBox j) {
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `area`";
        j.removeAllItems();
        j.addItem("اختر المنطقة");

        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                j.addItem(d.rs.getString("area_name"));
            }
            d.con.close();
        } catch (Exception e) {

        }
    }

    void branch(JComboBox j) {
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `branch`";
        j.removeAllItems();
        j.addItem("اختر الفرع");

        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                j.addItem(d.rs.getString("name"));
            }
            d.con.close();
        } catch (Exception e) {

        }
    }

    void combobox() {
        area(jComboBox6);
        area(jComboBox5);
        branch(itemComboBox);
        branch(itemComboBox1);
        branch(itemComboBox2);
        branch(branch);
        branch(branch1);
        branch(itemComboBox3);
        branch(itemComboBox4);
        branch(itemComboBox5);
        branch(itemComboBox6);
        mandob(jComboBox4);

    }

    String getUser() {
        String j = null;
        int x = 0;
        DBcon d = new DBcon();
        String sql = "SELECT name FROM users WHERE logged = 1";
        try {
            d.rs = d.st.executeQuery(sql);
            while (d.rs.next()) {
                x++;
                //j = d.rs.getString("User_name");
            }
            if (x != 0) {
                d.rs = d.st.executeQuery(sql);
                while (d.rs.next()) {
                    j = d.rs.getString("name");
                }
            } else {
                j = "Hash";

            }
            d.con.close();

        } catch (SQLException ex) {
            Logger.getLogger(Home.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return j;
    }

    int getUserId() {
        int j = 0;
        int x = 0;
        DBcon d = new DBcon();
        String sql = "SELECT * FROM users WHERE logged = '1'";
        try {
            d.rs = d.st.executeQuery(sql);
            while (d.rs.next()) {
                x++;
                //j = d.rs.getString("User_name");
            }
            if (x != 0) {
                d.rs = d.st.executeQuery(sql);
                while (d.rs.next()) {
                    j = d.rs.getInt("id");
                }
            } else {
                j = -1;

            }
            d.con.close();

        } catch (SQLException ex) {
            Logger.getLogger(Home.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return j;
    }

    void viewUsers() {
        String name1, id = null, password = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `users`";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("name");
                id = d.rs.getString("id");
                password = d.rs.getString("password");

                String[] rowData = {password, name1, id};
                userTable.addRow(rowData);

            }
            d.con.close();
        } catch (SQLException ex) {

        }
    }

    void blocks() {
        String name1, id = null, password = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM blocked_client , client WHERE blocked_client.client_id = client.id ";
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                name1 = d.rs.getString("client.name");
                id = d.rs.getString("client.id");
                String[] rowData = {name1, id};
                blockedTable.addRow(rowData);

            }
            d.con.close();
        } catch (SQLException ex) {

        }
    }

    public void delTblCol(DefaultTableModel table) {
        if (table.getRowCount() != 0) {
            for (int i = table.getRowCount() - 1; i >= 0; i--) {
                table.removeRow(i);
            }
        }
    }
//
//    int n = qest.getSelectedRow();
//        order = qestTahsel.getValueAt(n, 0).toString();

//    void color(){
//
//        for(int i = 0 ; i < qestTahsel.getRowCount() ; i++){
//            if(qest.getValueAt(i, 3).toString().equals(" ")){
//
//                qest.setEnabled(maximized);//.setBackground(Color.yellow);
//
//            }
//        }
//
//    }
    void deleteRepeted() {
        try {
            String sql2;
            DBcon d = new DBcon();
            int c = 0;
            int id = 0;
            int n = mandob.getSelectedRow();
            sql2 = "SELECT * FROM `client` , mandob ,`aqsat` WHERE  aqsat.client_id = client.id  AND  aqsat.mandob_id = mandob.id  AND mandob.name = '" + tahsealMan.getValueAt(n, 0).toString() + "';";

            d.rs = d.st.executeQuery(sql2);

            while ((d.rs).next()) {
                c = 0;
                id = d.rs.getInt("client.id");
                for (int i = 0; i < clientsTahsel.getRowCount(); i++) {
                    if (id == Integer.valueOf((String) clientsTahsel.getValueAt(i, 2))) {
                        c++;
                    }
                    if (c > 1) {
                        clientsTahsel.removeRow(i);
                        c--;
                    }
                }
            }
            d.con.close();
        } catch (Exception e) {

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

    void numbers0() {
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        int counter = 0;

        try {
            int count = 0;
            d.rs = d.st.executeQuery("SELECT * FROM tahsel,mandob WHERE tahsel.mandob_id = mandob.id AND done = '0'");
            int temp;
            while (d.rs.next()) {
                int mandobId = d.rs.getInt("tahsel.mandob_id");

                db.rs = db.st.executeQuery("SELECT * FROM aqsat WHERE mandob_id = '" + mandobId + "' AND tahsel = '0' AND  done = '0' ;");
                while (db.rs.next()) {
                    count++;
                }
                if (count == 0) {
                    counter++;
                }
            }
            jLabel125.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }
        try {
            int cons = 0;
            String sql2 = "SELECT * FROM `item` ";
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                cons = d.rs.getInt("cons_rate");
                if (cons > d.rs.getInt("quantity")) {
                    counter++;
                }
            }
            jLabel124.setText(String.valueOf(counter));
            counter = 0;
            d.con.close();
        } catch (SQLException ex) {

        }

    }

    void numbers() {
        DBcon d = new DBcon();
        int counter = 0;

        String sql2 = "SELECT * FROM `mandob_received_items` , mandob , item WHERE mandob_received_items.mandob_id = mandob.id AND mandob_received_items.item_id = item.id AND mandob.id = '" + mandobId.getText() + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel38.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }
        sql2 = "SELECT DISTINCT client.id FROM `client` , mandob ,`aqsat` WHERE aqsat.client_id = client.id AND aqsat.mandob_id = mandob.id AND mandob.name = '" + mandobName.getText() + "' AND aqsat.order = 1";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel47.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }
        sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND mandob.name = '" + mandobName.getText() + "'  AND aqsat.done = '0' AND month_payment = '" + LocalDate.now().getMonthValue() + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel49.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }
        sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND mandob.name = '" + mandobName.getText() + "' AND aqsat.done = '1' AND month_payment = '" + LocalDate.now().getMonthValue() + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel111.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }

        int cons = 0;
        sql2 = "SELECT * FROM `received_money`, mandob WHERE received_money.mandob_id = mandob.id AND mandob.id = '" + mandobId.getText() + "';";
        String quant;
        try {
            d.rs = d.st.executeQuery(sql2);

            d.rs = d.st.executeQuery(sql2);
            float recived = 0;
            while ((d.rs).next()) {
                recived += d.rs.getFloat("received_money.value");;
            }
            jLabel51.setText(String.valueOf(recived));
            counter = 0;
        } catch (SQLException ex) {

        }
        sql2 = "SELECT * FROM `mandob` WHERE id = '" + mandobId.getText() + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            d.rs.last();
            float total;
            total = d.rs.getFloat("total_recieved_money");
            jLabel53.setText(String.valueOf(total));
            counter = 0;
        } catch (SQLException ex) {

        }
        try {
            d.rs = d.st.executeQuery("select * from notes WHERE type = 'mandob' AND person_id = '" + mandobId.getText() + "';");
            while ((d.rs).next()) {
                counter++;
            }
            jLabel57.setText(String.valueOf(counter));
            counter = 0;
            d.con.close();
        } catch (SQLException ex) {

        }
    }

    void numbers2() {
        DBcon d = new DBcon();
        int counter = 0;

        String sql2 = "SELECT * FROM `client_recieved_items` , client , item WHERE client_recieved_items.clientId = client.id AND client_recieved_items.item_id = item.id AND client.id = '" + this.Cid.getText() + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel12.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }

        sql2 = "SELECT * FROM `aqsat` , client WHERE  aqsat.client_id = client.id AND client.name = '" + Cname.getText() + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel15.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }
        sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND client.name = '" + Cname.getText() + "' AND  aqsat.done = false ;";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel32.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }

        sql2 = "SELECT * FROM `aqsat` , mandob , client WHERE aqsat.mandob_id = mandob.id AND aqsat.client_id = client.id AND client.name = '" + Cname.getText() + "' AND  aqsat.done = true ;";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                counter++;
            }
            jLabel31.setText(String.valueOf(counter));
            counter = 0;
        } catch (SQLException ex) {

        }
        try {
            d.rs = d.st.executeQuery("select * from notes WHERE type = 'client' AND person_id = '" + Cid.getText() + "';");
            while ((d.rs).next()) {
                counter++;
            }
            jLabel59.setText(String.valueOf(counter));
            counter = 0;
            d.con.close();
        } catch (SQLException ex) {

        }

    }

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
            d.con.close();
        } catch (SQLException ex) {

        }
        return x;
    }

    public void makeTable() {
        stockTable = createTableCols(stock_table, new String[]{"الكمية", "الصنف", "الكود"});
        tahsealMan = createTableCols(mandob, new String[]{"الاسم", "الكود"});
        clientsTahsel = createTableCols(clients, new String[]{"الاسم", "الكود"});
        qestTahsel = createTableCols(qest, new String[]{"الترتيب", "القيمة", "الشهر", "الكود"});
        returnT = createTableCols(returnTable, new String[]{"الصنف", "العميل", "المندوب", "الكود"});
        notesT = createTableCols(notesTable, new String[]{"التاريخ", "العنوان", "الكود"});
        reportT = createTableCols(reports, new String[]{"التاريخ", "العملية", "الاسم", "الكود"});
        userTable = createTableCols(userT, new String[]{"كلمة المرور", "الاسم", "الكود"});
        areaTable = createTableCols(areaT, new String[]{"اسم المنطقة", "الكود"});
        receivedMony = createTableCols(recivedMonyT, new String[]{"التاريخ", "المبلغ", "المسمى", "اسم العميل", "الكود"});
        blockedTable = createTableCols(blockedT, new String[]{"الاسم", "الكود"});
        recipt = createTableCols(reciptT, new String[]{"تاريخ التحصيل", "اسم المحصل", "اسم البائع", "تاريخ الشراء", "الوظيفة", "العمل", "قيمة القسط", "الرقم القومي", "العنوان", "ترتيب القسط", "رقم الموبايل", "اسم العميل", "عدد الاقساط", "المتبقي", "المدفوع", "نوع المنتج", "المنطقة", "كود العميل" ,"عدد الاقساط"});
    }

    void mandobReceivedItemsTable() {
        delTblCol(receivedMony);
        DBcon d = new DBcon();
        String mandobName, id, title, date, value = null;
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `received_money`, mandob WHERE received_money.mandob_id = mandob.id ;");
            while ((d.rs).next()) {
                mandobName = d.rs.getString("mandob.name");
                id = String.valueOf(d.rs.getInt("received_money.id"));
                title = String.valueOf(d.rs.getString("received_money.title"));
                date = String.valueOf(d.rs.getDate("received_money.date"));
                value = String.valueOf(d.rs.getFloat("received_money.value"));
                String[] rowData = {date, value, title, mandobName, id};
                receivedMony.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }
    }

    void recipt() {
        reciptT.getTableHeader().setReorderingAllowed(false);
        delTblCol(recipt);
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        String id, value = null, multiName = null,monthCount, clientName, item, add1, add2, add, item_id, area, job, total, overHand, count, reciveDate, tahselDate, CRI, mob, recivedName = null, tahselName = null, order, clientNum, jobPlace;
        float allPaid, rest;
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `client`, client_recieved_items ,aqsat ,mandob ,item WHERE client_recieved_items.mandob_id = mandob.id AND client_recieved_items.clientId = client.id AND client_recieved_items.id = aqsat.CRI AND client_recieved_items.item_id = item.id AND aqsat.done = '0';");

            while ((d.rs).next()) {
                recivedName = "";
                float qestPaid = 0;
                CRI = String.valueOf(d.rs.getInt("client_recieved_items.id"));

                if (d.rs.getInt("client_recieved_items.mandob_id") == 0) {

                    db.rs = db.st.executeQuery("SELECT * FROM `multiman`,mandob WHERE multiman.mandob_id = mandob.id AND CRI = '" + CRI + "';");
                    while ((db.rs).next()) {
                        if ((db.rs).isLast()) {
                            recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0]);
                        } else {
                            recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0] + " - ");
                        }
                    }
                } else {
                    recivedName = d.rs.getString("mandob.name");
                }
                clientName = d.rs.getString("client.name");
                clientNum = d.rs.getString("client.card_num");
                id = String.valueOf(d.rs.getInt("client.id"));
                area = String.valueOf(d.rs.getString("client.areaName"));
                job = String.valueOf(d.rs.getString("client.job"));
                jobPlace = String.valueOf(d.rs.getString("client.job_place"));
                mob = String.valueOf(d.rs.getString("client.mob1"));
                item = String.valueOf(d.rs.getString("client_recieved_items.quantity"))+" "+String.valueOf(d.rs.getString("item.name"));
                count = months(Integer.valueOf(CRI));
                overHand = String.valueOf(d.rs.getFloat("client_recieved_items.handOver"));
                total = String.valueOf(d.rs.getFloat("client_recieved_items.total_payment"));
                order = String.valueOf(d.rs.getInt("aqsat.order"));
                add1 = String.valueOf(d.rs.getString("client.address1"));
                add2 = String.valueOf(d.rs.getString("client.address2"));
                add = add1 + " , " + add2;
                value = String.valueOf(d.rs.getFloat("aqsat.value"));
                monthCount = String.valueOf(d.rs.getInt("client_recieved_items.month_count"));
                db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' AND done = '1';");
                while ((db.rs).next()) {
                    qestPaid += db.rs.getFloat("aqsat.value");

                }

                db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' ;");
                while ((db.rs).next()) {
                    tahselName = db.rs.getString("mandob.name");
                }
                allPaid = qestPaid + Float.valueOf(overHand);
                rest = Float.valueOf(total) - (qestPaid + Float.valueOf(overHand));
                tahselDate = String.valueOf(d.rs.getString("aqsat.received_date"));
                reciveDate = String.valueOf(d.rs.getDate("client_recieved_items.received_date"));

                String[] rowData = {tahselDate, tahselName, recivedName, reciveDate, job, jobPlace, value, clientNum, add, order, mob, clientName, count + "", rest + "", allPaid + "", item, area, id,monthCount};
                recipt.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }
        totalAqsatVal();
    }

    void tahselTables() {
        DBcon d = new DBcon();
        String name1 = null, id1 = null;
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `mandob`");
            while (d.rs.next()) {
                name1 = d.rs.getString("name");
                id1 = String.valueOf(d.rs.getInt("id"));
                String[] rowData = {name1, id1};
                tahsealMan.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

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
            d.rs = d.st.executeQuery("select * from aqsat WHERE month_payment != '" + LocalDate.now().getMonthValue() + "' AND tahsel = true");
            while (d.rs.next()) {
                id = d.rs.getInt("id");
                manId = d.rs.getInt("mandob_id");
                db.con.createStatement();
                db.st.executeUpdate("DELETE FROM `tahsel` WHERE `qest_id` = '" + id + "';");

            }
            d.con.close();
        } catch (Exception e) {
        }
    }

    void notes() {
        DBcon d = new DBcon();
        String id;
        String title, date;

        try {
            d.rs = d.st.executeQuery("select * from notes");
            while (d.rs.next()) {
                id = String.valueOf(d.rs.getInt("id"));
                date = String.valueOf(d.rs.getDate("date"));
                title = d.rs.getString("title");
                String[] rowData = {date, title, id};
                notesT.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }
    }

    void areaTable() {
        DBcon d = new DBcon();
        String id;
        String title, name;

        try {
            d.rs = d.st.executeQuery("select * from area");
            while (d.rs.next()) {
                id = String.valueOf(d.rs.getInt("id"));
                name = String.valueOf(d.rs.getString("area_name"));

                String[] rowData = {name, id};
                areaTable.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }
    }

    void report() {
        DBcon d = new DBcon();
        String id, title, date, Name, type, operation;
        delTblCol(reportT);
        try {
            d.rs = d.st.executeQuery("select * from history");
            while (d.rs.next()) {
                id = String.valueOf(d.rs.getInt("person_id"));
                date = String.valueOf(d.rs.getDate("date"));
                Name = d.rs.getString("person_type");
                operation = d.rs.getString("operation");
                String[] rowData = {date, operation, Name, id};
                reportT.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }
    }

    public void deleteFromTable(String tableName, String ID) {
        DBcon d = new DBcon();
        try {
            String sql = "DELETE FROM " + tableName + " WHERE `id` = '" + ID + "';";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.con.close();
        } catch (Exception e) {

        }
    }

    void tahselMandobTable() {
        String id = null, name = "unknown", quant = null, price = null;
        int n = mandob.getSelectedRow();
        name = tahsealMan.getValueAt(n, 0).toString();
        id = tahsealMan.getValueAt(n, 1).toString();
        jTextField51.setText(id);
        mand.setText(name);
        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `client`,aqsat WHERE client.id = aqsat.client_id AND mandob_id = '" + id + "' AND aqsat.order = '1'  ;");
            while ((d.rs).next()) {
                name = d.rs.getString("client.name");
                id = String.valueOf(d.rs.getInt("client.id"));

                String[] rowData = {name, id};
                clientsTahsel.addRow(rowData);

            }
            d.con.close();
        } catch (Exception e) {
        }
        //.....................................................................................................

    }

    static boolean maximized = true;

    private void showPanel(JPanel panel) {
        panel.setVisible(true);
    }

    private void hidePanel(JPanel[] panel) {
        for (int i = 0; i < panel.length; i++) {
            panel[i].setVisible(false);
        }
    }

    private void setColor(JPanel pane, JLabel lbl, JPanel marker) {
        pane.setBackground(new Color(255, 255, 255));
        lbl.setForeground(new Color(53, 59, 72));
        marker.setBackground(new Color(255, 107, 107));
    }

    private void resetColor(JPanel[] pane, JLabel[] lbl, JPanel[] marker) {
        for (int i = 0; i < pane.length; i++) {
            pane[i].setBackground(new Color(53, 59, 72));
        }
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setForeground(new Color(255, 255, 255));
        }

        for (int i = 0; i < marker.length; i++) {
            marker[i].setBackground(new Color(53, 59, 72));
        }
    }

    private static void tableCenter(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void setLabelIcon(JLabel lbl, String im) {
        lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource(im)));
    }

    void TablePdf(DefaultTableModel table, String[] ColumnsName) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        String[][] rowData = new String[rowCount][columnCount];//= null ;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                rowData[row][col] = String.valueOf(table.getValueAt(row, col));
            }
        }
        pdfi(ColumnsName, rowData);
    }

    void pdfi(String s[], String s2[][]) {
        try {

            String fileName = JOptionPane.showInputDialog("  : أدخل اسم الملف");
            fileName += " " + Date.getText();
            createSamplePDF(fileName, s, s2);
            JOptionPane.showMessageDialog(null, "برجاء الانتظار سيتم فتح الملف ");
            Desktop.getDesktop().open(new File(filePath + "\\PDF\\" + fileName + ".pdf"));

        } catch (Exception ex) {

        }
    }

    public static void styleTable(JTable[] tables) {

        for (int i = 0; i < tables.length; i++) {
            //  tables[i].getTableHeader().setPreferredSize(new Dimension(100, 50));
            tables[i].getTableHeader().setFont(new java.awt.Font("Arial", Font.BOLD, 20));
            tables[i].setFont(new java.awt.Font("Arial", Font.BOLD, 18)); // NOI18N
            tables[i].setRowHeight(40);
            tables[i].setToolTipText(null);
            //  tables[i].setSelectionBackground(new java.awt.Color(232, 57, 95));
            tableCenter(tables[i]);

        }

    }

    static void createSamplePDF(String fileName, String header[], String body[][]) throws Exception {
        Document documento = new Document();

        File directory = new File(filePath + "\\PDF");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(filePath + "\\PDF\\" + fileName + ".pdf");

        file.createNewFile();
        FileOutputStream fop = new FileOutputStream(file);
        PdfWriter.getInstance(documento, fop);
        // get current user pass

        // end pass
        documento.open();
        //Fonts
        Font fontHeader = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 18);
        Font fontBody = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 14);

        //Table for header
        PdfPTable cabetabla = new PdfPTable(header.length);
        // handling arabic header
        cabetabla.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

        for (int j = 0; j < header.length; j++) {
            Phrase frase = new Phrase(header[j], fontHeader);
            PdfPCell cell = new PdfPCell(frase);
            cell.setBackgroundColor(BaseColor.ORANGE);
            cabetabla.addCell(cell);
        }
        documento.add(cabetabla);
        //Tabla for body
        PdfPTable table = new PdfPTable(header.length);
        //handling arabic body
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        for (int i = 0; i < body.length; i++) {
            for (int j = 0; j < body[i].length; j++) {
                table.addCell(new Phrase(body[i][j], fontBody));

            }
        }
        documento.add(table);
        documento.close();
        fop.flush();
        fop.close();
    }

    // start Json
    public static String getNameFromJson(String clientId) throws IOException {
        HttpURLConnection conection = null;
        String readLine = null;
        String name = "غير معروف";
        try {
            URL urlForGetRequest = new URL("https://qsatha.herokuapp.com/api/v1/premiums");
            conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No Internet   --  لا يوجد اتصال");
        }

        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            //System.out.println("JSON String Result " + response.toString());
            JsonObject jsonObject = (new JsonParser()).parse(response.toString()).getAsJsonObject();

            for (int i = 0; i < jsonObject.getAsJsonArray("data").size(); i++) {
                if ((jsonObject.getAsJsonArray("data").get(i).getAsJsonObject().get("client").getAsJsonObject().get("id").toString()).equals(clientId)) {
                    name = jsonObject.getAsJsonArray("data").get(i).getAsJsonObject().get("client").getAsJsonObject().get("firstname").getAsString() + " " + jsonObject.getAsJsonArray("data").get(i).getAsJsonObject().get("client").getAsJsonObject().get("lastname").getAsString();
                    break;
                }
            }

        }
        return name;
    }

    public static String getCardFromJson(String clientId) throws IOException {
        HttpURLConnection conection = null;
        String readLine = null;
        String cardNum = "غير معروف";
        try {
            URL urlForGetRequest = new URL("https://qsatha.herokuapp.com/api/v1/premiums");
            conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No Internet   --  لا يوجد اتصال");
        }
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            //System.out.println("JSON String Result " + response.toString());
            JsonObject jsonObject = (new JsonParser()).parse(response.toString()).getAsJsonObject();

            for (int i = 0; i < jsonObject.getAsJsonArray("data").size(); i++) {
                if ((jsonObject.getAsJsonArray("data").get(i).getAsJsonObject().get("client").getAsJsonObject().get("id").toString()).equals(clientId)) {
                    cardNum = jsonObject.getAsJsonArray("data").get(i).getAsJsonObject().get("client").getAsJsonObject().get("cardNum").getAsString();
                    break;
                }
            }

        }
        return cardNum;
    }

    public static void exportExcel(JTable table, String fileName) {
        File directory = new File(filePath + "\\Excel\\");
        if (!directory.exists()) {
            directory.mkdir();
        }
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("hh-mm-ss");
        LocalDateTime now = LocalDateTime.now();  //2016/11/16 12:08:43
        String EXCEL_FILE_LOCATION = filePath + "\\Excel\\" + fileName + "___" + String.valueOf(dtf1.format(now)) + "___" + String.valueOf(dtf2.format(now)) + ".xls";

        //1. Create an Excel file
        WritableWorkbook Wbook = null;
        try {

            Wbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet FirstSheet = Wbook.createSheet("Sheet 1", 0);

            WritableCellFormat cFormat = new WritableCellFormat();
            WritableCellFormat cFormat2 = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);

            cFormat.setAlignment(jxl.format.Alignment.CENTRE);
            cFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);
            cFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THICK, jxl.format.Colour.BLUE_GREY);
            cFormat2.setAlignment(jxl.format.Alignment.CENTRE);

            cFormat.setFont(font);
            cFormat2.setFont(font2);

            for (int i = 0; i < table.getColumnCount(); i++) {
                Label label = new Label(i, 0, table.getColumnName(i).toString(), cFormat);
                //Label label = new Label(i, 0, table.getValueAt(0, i).toString(), cFormat);
                FirstSheet.addCell(label);

            }
            for (int i = 1; i <= table.getRowCount(); i++) {
                for (int j = 1; j <= table.getColumnCount(); j++) {
                    Label label = new Label(j - 1, i, table.getValueAt(i - 1, j - 1).toString(), cFormat2);
                    FirstSheet.addCell(label);
                }

            }

            Wbook.write();

        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (Wbook != null) {
                try {
                    Wbook.close();
                    JOptionPane.showMessageDialog(null, "الرجاء الانتظار سيتم فتح الملف");
                    Desktop.getDesktop().open(new File(EXCEL_FILE_LOCATION));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    void getQestData() {
        String[] arr = new String[20];
        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("SELECT * FROM hamada ;");

            while (d.rs.next()) {
                arr[0] = d.rs.getString("clientId");
                arr[1] = d.rs.getString("firstPay");
                arr[2] = d.rs.getString("restPay");
                arr[3] = d.rs.getString("qestNum");
                arr[4] = d.rs.getString("qestOrder");
                arr[5] = d.rs.getString("Item");
                arr[6] = d.rs.getString("qestValue");
                arr[7] = d.rs.getString("qestText");
                arr[8] = d.rs.getString("clientName");
                arr[9] = d.rs.getString("mob");
                arr[10] = d.rs.getString("nationalId");
                arr[11] = d.rs.getString("area");
                arr[12] = d.rs.getString("address");
                arr[13] = d.rs.getString("work_place");
                arr[14] = d.rs.getString("job");
                arr[15] = d.rs.getString("selling_man");
                arr[16] = d.rs.getString("tahseel_man");
                arr[17] = d.rs.getString("purch_date");
                arr[18] = d.rs.getString("qest_date");
            }

        } catch (Exception ex) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    void showTahsel() {
        String id = null, name = "unknown", quant = null, price = null;

        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `client`,aqsat WHERE client.id = aqsat.client_id AND mandob_id = '" + jTextField51.getText() + "' AND aqsat.order = '1'  ;");
            while ((d.rs).next()) {
                name = d.rs.getString("client.name");
                id = String.valueOf(d.rs.getInt("client.id"));

                String[] rowData = {name, id};
                clientsTahsel.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {
        }

        //.................................................................
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` WHERE client_id = '" + jTextField35.getText() + "';");
            String month, id1, val, order;
            while ((d.rs).next()) {
                month = String.valueOf(d.rs.getString("month_payment"));
                id1 = String.valueOf(d.rs.getInt("id"));
                val = String.valueOf(d.rs.getFloat("value"));
                order = String.valueOf(d.rs.getInt("order"));
                String[] rowData = {order, val, month, id1};
                qestTahsel.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {
        }
        //......................................................................................

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fullPanel = new javax.swing.JPanel();
        logoPanel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        sideBorderPanel = new javax.swing.JPanel();
        bottomBorderPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        close_lbl = new javax.swing.JLabel();
        lock_lbl = new javax.swing.JLabel();
        sidePanel = new javax.swing.JPanel();
        pane1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        markerPanel1 = new javax.swing.JPanel();
        pane2 = new javax.swing.JPanel();
        label2 = new javax.swing.JLabel();
        markerPanel2 = new javax.swing.JPanel();
        pane3 = new javax.swing.JPanel();
        label3 = new javax.swing.JLabel();
        markerPanel3 = new javax.swing.JPanel();
        pane4 = new javax.swing.JPanel();
        label4 = new javax.swing.JLabel();
        markerPanel4 = new javax.swing.JPanel();
        pane5 = new javax.swing.JPanel();
        label5 = new javax.swing.JLabel();
        markerPanel5 = new javax.swing.JPanel();
        pane6 = new javax.swing.JPanel();
        label6 = new javax.swing.JLabel();
        markerPanel6 = new javax.swing.JPanel();
        pane7 = new javax.swing.JPanel();
        label7 = new javax.swing.JLabel();
        markerPanel7 = new javax.swing.JPanel();
        pane8 = new javax.swing.JPanel();
        label8 = new javax.swing.JLabel();
        markerPanel8 = new javax.swing.JPanel();
        pane9 = new javax.swing.JPanel();
        label9 = new javax.swing.JLabel();
        markerPanel9 = new javax.swing.JPanel();
        pane10 = new javax.swing.JPanel();
        label10 = new javax.swing.JLabel();
        markerPanel10 = new javax.swing.JPanel();
        pane11 = new javax.swing.JPanel();
        label11 = new javax.swing.JLabel();
        markerPanel11 = new javax.swing.JPanel();
        pane12 = new javax.swing.JPanel();
        label12 = new javax.swing.JLabel();
        markerPanel12 = new javax.swing.JPanel();
        pane13 = new javax.swing.JPanel();
        label13 = new javax.swing.JLabel();
        markerPanel13 = new javax.swing.JPanel();
        pane14 = new javax.swing.JPanel();
        label14 = new javax.swing.JLabel();
        markerPanel14 = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        homePanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        user_lbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        stockPanel = new javax.swing.JPanel();
        stockPanel1 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        text1_1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text1_2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        text1_3 = new javax.swing.JTextField();
        text1_5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        text1_6 = new javax.swing.JTextField();
        text1_7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        text1_8 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel70 = new javax.swing.JLabel();
        text1_4 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        itemComboBox = new javax.swing.JComboBox<>();
        totalSalesPrice = new javax.swing.JLabel();
        excelStock = new javax.swing.JLabel();
        stockPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stock_table = new javax.swing.JTable();
        mandobPanel = new javax.swing.JPanel();
        mandobPanel1 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        mandobId = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        mandobName = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        mandobCardName = new javax.swing.JTextField();
        mandobLocation = new javax.swing.JTextField();
        mandobCollectionRate = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        mandobMobile = new javax.swing.JTextField();
        mandobReceivedItems = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        mandobClients = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        mandobRemainingCollections = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        mandobResevedMoney = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        mandobCompletedCollections = new javax.swing.JButton();
        jButton66 = new javax.swing.JButton();
        jButton68 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        itemComboBox1 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jButton23 = new javax.swing.JButton();
        clientPanel = new javax.swing.JPanel();
        clientPanel1 = new javax.swing.JPanel();
        online_icon = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        Cid = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Cname = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cNum = new javax.swing.JTextField();
        add2 = new javax.swing.JTextField();
        mob2 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        mob1 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jobPlace = new javax.swing.JTextField();
        add1 = new javax.swing.JTextField();
        Cjob = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton69 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox<>();
        itemComboBox2 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        areaPanel = new javax.swing.JPanel();
        areaPanel1 = new javax.swing.JPanel();
        jTextField13 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        itemComboBox4 = new javax.swing.JComboBox<>();
        excelStock1 = new javax.swing.JLabel();
        areaPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaT = new javax.swing.JTable();
        PayPanel = new javax.swing.JPanel();
        PayPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        mand = new javax.swing.JTextField();
        jButton32 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jLabel77 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jTextField36 = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jButton38 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton71 = new javax.swing.JButton();
        jLabel130 = new javax.swing.JLabel();
        jTextField51 = new javax.swing.JTextField();
        jLabel131 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        light_lbl = new javax.swing.JLabel();
        PayPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        qest = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        mandob = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        clients = new javax.swing.JTable();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        returnPanel = new javax.swing.JPanel();
        returnPanel1 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
        itemId = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        itemComboBox5 = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        itemId1 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        excelStock2 = new javax.swing.JLabel();
        returnPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        returnTable = new javax.swing.JTable();
        CalculationPanel = new javax.swing.JPanel();
        CalculationPanel1 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jButton45 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jLabel86 = new javax.swing.JLabel();
        jButton67 = new javax.swing.JButton();
        jLabel132 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jButton70 = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        itemComboBox6 = new javax.swing.JComboBox<>();
        excelStock3 = new javax.swing.JLabel();
        CalculationPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        recivedMonyT = new javax.swing.JTable();
        notePanel = new javax.swing.JPanel();
        notePanel1 = new javax.swing.JPanel();
        date = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton44 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jLabel99 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
        jLabel127 = new javax.swing.JLabel();
        jTextField53 = new javax.swing.JTextField();
        itemComboBox3 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        excelStock4 = new javax.swing.JLabel();
        notePanel2 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        notesTable = new javax.swing.JTable();
        reciptPanel = new javax.swing.JPanel();
        reciptPanel1 = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        excelStock5 = new javax.swing.JLabel();
        reciptPanel2 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        reciptT = new javax.swing.JTable();
        reportPanel = new javax.swing.JPanel();
        reciptPanel3 = new javax.swing.JPanel();
        reportType = new javax.swing.JComboBox<>();
        reportType1 = new javax.swing.JComboBox<>();
        jComboBox9 = new javax.swing.JComboBox<>();
        excelStock6 = new javax.swing.JLabel();
        reciptPanel4 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        reports = new javax.swing.JTable();
        usersPanel = new javax.swing.JPanel();
        notePanel3 = new javax.swing.JPanel();
        itemPanel12 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        user3 = new javax.swing.JCheckBox();
        user4 = new javax.swing.JCheckBox();
        user5 = new javax.swing.JCheckBox();
        user6 = new javax.swing.JCheckBox();
        user10 = new javax.swing.JCheckBox();
        user11 = new javax.swing.JCheckBox();
        user12 = new javax.swing.JCheckBox();
        user2 = new javax.swing.JCheckBox();
        user9 = new javax.swing.JCheckBox();
        user8 = new javax.swing.JCheckBox();
        check_lbl11 = new javax.swing.JLabel();
        check_lbl12 = new javax.swing.JLabel();
        check_lbl13 = new javax.swing.JLabel();
        check_lbl14 = new javax.swing.JLabel();
        check_lbl15 = new javax.swing.JLabel();
        check_lbl16 = new javax.swing.JLabel();
        check_lbl17 = new javax.swing.JLabel();
        check_lbl18 = new javax.swing.JLabel();
        check_lbl19 = new javax.swing.JLabel();
        check_lbl20 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        check_lbl21 = new javax.swing.JLabel();
        check_lbl22 = new javax.swing.JLabel();
        user1 = new javax.swing.JCheckBox();
        user7 = new javax.swing.JCheckBox();
        branch = new javax.swing.JComboBox<>();
        branch_lbl12 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        excelStock7 = new javax.swing.JLabel();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        notePanel4 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        userT = new javax.swing.JTable();
        BlockPanel = new javax.swing.JPanel();
        BlockPanel1 = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jButton61 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jButton65 = new javax.swing.JButton();
        branch1 = new javax.swing.JComboBox<>();
        branch_lbl13 = new javax.swing.JLabel();
        excelStock8 = new javax.swing.JLabel();
        BlockPanel2 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        blockedT = new javax.swing.JTable();
        aboutPanel = new javax.swing.JPanel();
        BlockPanel3 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 30));
        setUndecorated(true);
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fullPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mlogo.png"))); // NOI18N

        javax.swing.GroupLayout logoPanelLayout = new javax.swing.GroupLayout(logoPanel);
        logoPanel.setLayout(logoPanelLayout);
        logoPanelLayout.setHorizontalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoPanelLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(26, 26, 26))
        );
        logoPanelLayout.setVerticalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoPanelLayout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        fullPanel.add(logoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 0, -1, 110));

        sideBorderPanel.setBackground(new java.awt.Color(189, 195, 199));

        javax.swing.GroupLayout sideBorderPanelLayout = new javax.swing.GroupLayout(sideBorderPanel);
        sideBorderPanel.setLayout(sideBorderPanelLayout);
        sideBorderPanelLayout.setHorizontalGroup(
            sideBorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        sideBorderPanelLayout.setVerticalGroup(
            sideBorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        fullPanel.add(sideBorderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 3, 720));

        bottomBorderPanel.setBackground(new java.awt.Color(189, 195, 199));

        javax.swing.GroupLayout bottomBorderPanelLayout = new javax.swing.GroupLayout(bottomBorderPanel);
        bottomBorderPanel.setLayout(bottomBorderPanelLayout);
        bottomBorderPanelLayout.setHorizontalGroup(
            bottomBorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1310, Short.MAX_VALUE)
        );
        bottomBorderPanelLayout.setVerticalGroup(
            bottomBorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        fullPanel.add(bottomBorderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 717, 1310, 3));

        titlePanel.setBackground(new java.awt.Color(53, 59, 72));
        titlePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close_lbl.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        close_lbl.setForeground(new java.awt.Color(255, 255, 255));
        close_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        close_lbl.setText("X");
        close_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                close_lblMousePressed(evt);
            }
        });
        titlePanel.add(close_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 28));

        lock_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lock_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock.png"))); // NOI18N
        lock_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lock_lblMousePressed(evt);
            }
        });
        titlePanel.add(lock_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 40, 50));

        fullPanel.add(titlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 50));

        sidePanel.setBackground(new java.awt.Color(53, 59, 72));
        sidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pane1.setBackground(new java.awt.Color(255, 255, 255));
        pane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane1MousePressed(evt);
            }
        });
        pane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label1.setBackground(new java.awt.Color(255, 255, 255));
        label1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label1.setForeground(new java.awt.Color(44, 62, 80));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label1.setText("الرئيسية");
        pane1.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel1.setBackground(new java.awt.Color(255, 107, 107));

        javax.swing.GroupLayout markerPanel1Layout = new javax.swing.GroupLayout(markerPanel1);
        markerPanel1.setLayout(markerPanel1Layout);
        markerPanel1Layout.setHorizontalGroup(
            markerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel1Layout.setVerticalGroup(
            markerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane1.add(markerPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 41));

        pane2.setBackground(new java.awt.Color(53, 59, 72));
        pane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane2MousePressed(evt);
            }
        });
        pane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label2.setText("المخزن");
        pane2.add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel2.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel2Layout = new javax.swing.GroupLayout(markerPanel2);
        markerPanel2.setLayout(markerPanel2Layout);
        markerPanel2Layout.setHorizontalGroup(
            markerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel2Layout.setVerticalGroup(
            markerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane2.add(markerPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, 41));

        pane3.setBackground(new java.awt.Color(53, 59, 72));
        pane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane3MousePressed(evt);
            }
        });
        pane3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label3.setText("المندوب");
        pane3.add(label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel3.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel3Layout = new javax.swing.GroupLayout(markerPanel3);
        markerPanel3.setLayout(markerPanel3Layout);
        markerPanel3Layout.setHorizontalGroup(
            markerPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel3Layout.setVerticalGroup(
            markerPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane3.add(markerPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 41));

        pane4.setBackground(new java.awt.Color(53, 59, 72));
        pane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane4MousePressed(evt);
            }
        });
        pane4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label4.setText("العملاء");
        pane4.add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel4.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel4Layout = new javax.swing.GroupLayout(markerPanel4);
        markerPanel4.setLayout(markerPanel4Layout);
        markerPanel4Layout.setHorizontalGroup(
            markerPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel4Layout.setVerticalGroup(
            markerPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane4.add(markerPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 41));

        pane5.setBackground(new java.awt.Color(53, 59, 72));
        pane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane5MousePressed(evt);
            }
        });
        pane5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label5.setForeground(new java.awt.Color(255, 255, 255));
        label5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label5.setText("المناطق");
        pane5.add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel5.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel5Layout = new javax.swing.GroupLayout(markerPanel5);
        markerPanel5.setLayout(markerPanel5Layout);
        markerPanel5Layout.setHorizontalGroup(
            markerPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel5Layout.setVerticalGroup(
            markerPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane5.add(markerPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 41));

        pane6.setBackground(new java.awt.Color(53, 59, 72));
        pane6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane6MousePressed(evt);
            }
        });
        pane6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label6.setForeground(new java.awt.Color(255, 255, 255));
        label6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label6.setText("التحصيل");
        pane6.add(label6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel6.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel6Layout = new javax.swing.GroupLayout(markerPanel6);
        markerPanel6.setLayout(markerPanel6Layout);
        markerPanel6Layout.setHorizontalGroup(
            markerPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel6Layout.setVerticalGroup(
            markerPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane6.add(markerPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 160, 41));

        pane7.setBackground(new java.awt.Color(53, 59, 72));
        pane7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane7MousePressed(evt);
            }
        });
        pane7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label7.setForeground(new java.awt.Color(255, 255, 255));
        label7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label7.setText("المرتجعات");
        pane7.add(label7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel7.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel7Layout = new javax.swing.GroupLayout(markerPanel7);
        markerPanel7.setLayout(markerPanel7Layout);
        markerPanel7Layout.setHorizontalGroup(
            markerPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel7Layout.setVerticalGroup(
            markerPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane7.add(markerPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, 41));

        pane8.setBackground(new java.awt.Color(53, 59, 72));
        pane8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane8MousePressed(evt);
            }
        });
        pane8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label8.setForeground(new java.awt.Color(255, 255, 255));
        label8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label8.setText("الحسابات");
        pane8.add(label8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel8.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel8Layout = new javax.swing.GroupLayout(markerPanel8);
        markerPanel8.setLayout(markerPanel8Layout);
        markerPanel8Layout.setHorizontalGroup(
            markerPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel8Layout.setVerticalGroup(
            markerPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane8.add(markerPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 160, 41));

        pane9.setBackground(new java.awt.Color(53, 59, 72));
        pane9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane9MousePressed(evt);
            }
        });
        pane9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label9.setForeground(new java.awt.Color(255, 255, 255));
        label9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label9.setText("الملاحظات");
        pane9.add(label9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel9.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel9Layout = new javax.swing.GroupLayout(markerPanel9);
        markerPanel9.setLayout(markerPanel9Layout);
        markerPanel9Layout.setHorizontalGroup(
            markerPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel9Layout.setVerticalGroup(
            markerPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane9.add(markerPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 160, 41));

        pane10.setBackground(new java.awt.Color(53, 59, 72));
        pane10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane10MousePressed(evt);
            }
        });
        pane10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label10.setForeground(new java.awt.Color(255, 255, 255));
        label10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label10.setText("الإيصالات");
        pane10.add(label10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel10.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel10Layout = new javax.swing.GroupLayout(markerPanel10);
        markerPanel10.setLayout(markerPanel10Layout);
        markerPanel10Layout.setHorizontalGroup(
            markerPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel10Layout.setVerticalGroup(
            markerPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane10.add(markerPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 160, 41));

        pane11.setBackground(new java.awt.Color(53, 59, 72));
        pane11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane11MousePressed(evt);
            }
        });
        pane11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label11.setForeground(new java.awt.Color(255, 255, 255));
        label11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label11.setText("التقارير");
        pane11.add(label11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel11.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel11Layout = new javax.swing.GroupLayout(markerPanel11);
        markerPanel11.setLayout(markerPanel11Layout);
        markerPanel11Layout.setHorizontalGroup(
            markerPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel11Layout.setVerticalGroup(
            markerPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane11.add(markerPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 160, 41));

        pane12.setBackground(new java.awt.Color(53, 59, 72));
        pane12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane12MousePressed(evt);
            }
        });
        pane12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label12.setForeground(new java.awt.Color(255, 255, 255));
        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("المستخدمين");
        pane12.add(label12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel12.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel12Layout = new javax.swing.GroupLayout(markerPanel12);
        markerPanel12.setLayout(markerPanel12Layout);
        markerPanel12Layout.setHorizontalGroup(
            markerPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel12Layout.setVerticalGroup(
            markerPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane12.add(markerPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 160, 41));

        pane13.setBackground(new java.awt.Color(53, 59, 72));
        pane13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane13MousePressed(evt);
            }
        });
        pane13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label13.setForeground(new java.awt.Color(255, 255, 255));
        label13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label13.setText("قائمة الحظر");
        pane13.add(label13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel13.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel13Layout = new javax.swing.GroupLayout(markerPanel13);
        markerPanel13.setLayout(markerPanel13Layout);
        markerPanel13Layout.setHorizontalGroup(
            markerPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel13Layout.setVerticalGroup(
            markerPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane13.add(markerPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 160, 41));

        pane14.setBackground(new java.awt.Color(53, 59, 72));
        pane14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pane14MousePressed(evt);
            }
        });
        pane14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label14.setForeground(new java.awt.Color(255, 255, 255));
        label14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label14.setText("حول البرنامج");
        pane14.add(label14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 40));

        markerPanel14.setBackground(new java.awt.Color(53, 59, 72));

        javax.swing.GroupLayout markerPanel14Layout = new javax.swing.GroupLayout(markerPanel14);
        markerPanel14.setLayout(markerPanel14Layout);
        markerPanel14Layout.setHorizontalGroup(
            markerPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        markerPanel14Layout.setVerticalGroup(
            markerPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        pane14.add(markerPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 0, 5, 42));

        sidePanel.add(pane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 160, 41));

        fullPanel.add(sidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 110, 160, 610));

        homePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homePanel1.setBackground(new java.awt.Color(113, 128, 147));
        homePanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 70)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(53, 59, 72));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("تاريخ اليوم");
        homePanel1.add(jLabel1);
        jLabel1.setBounds(0, 230, 1150, 130);

        user_lbl.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        user_lbl.setForeground(new java.awt.Color(255, 255, 255));
        user_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user_lbl.setText(getUser());
        homePanel1.add(user_lbl);
        user_lbl.setBounds(0, 100, 1150, 130);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 70)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(53, 59, 72));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("مرحبا بك");
        homePanel1.add(jLabel2);
        jLabel2.setBounds(0, 10, 1150, 130);

        Date.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        Date.setForeground(new java.awt.Color(255, 255, 255));
        Date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Date.setText(String.valueOf(LocalDate.now()));
        homePanel1.add(Date);
        Date.setBounds(0, 330, 1150, 130);

        jLabel124.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 51, 0));
        jLabel124.setText("5");
        homePanel1.add(jLabel124);
        jLabel124.setBounds(130, 490, 220, 80);

        jLabel123.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cons.png"))); // NOI18N
        jLabel123.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel123MouseClicked(evt);
            }
        });
        homePanel1.add(jLabel123);
        jLabel123.setBounds(30, 520, 104, 120);

        jLabel125.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(255, 51, 0));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel125.setText("2");
        homePanel1.add(jLabel125);
        jLabel125.setBounds(790, 480, 260, 90);

        jLabel126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/last.png"))); // NOI18N
        jLabel126.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel126MouseClicked(evt);
            }
        });
        homePanel1.add(jLabel126);
        jLabel126.setBounds(1030, 520, 100, 120);

        homePanel.add(homePanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1150, 670));

        fullPanel.add(homePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        stockPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stockPanel1.setBackground(new java.awt.Color(189, 195, 199));
        stockPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        stockPanel1.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 50));

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        stockPanel1.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 50));

        text1_1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_1ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 390, 60));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("الكود : ");
        stockPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 150, 60));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("الصنف : ");
        stockPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 150, 60));

        text1_2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                text1_2CaretUpdate(evt);
            }
        });
        text1_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_2ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 390, 60));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("سعر الشراء : ");
        stockPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 360, 150, 60));

        text1_3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_3ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 390, 60));

        text1_5.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_5ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 390, 60));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("سعر الكاش :");
        stockPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, 150, 60));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("سعر التقسيط :");
        stockPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 480, 150, 60));

        text1_6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_6ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 390, 50));

        text1_7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_7ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 390, 50));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("الكمية :");
        stockPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 150, 60));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("الفرع :");
        stockPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 70, 50));

        text1_8.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_8ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 390, 50));

        jButton1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton1.setText("عرض الكل");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        stockPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 615, 110, 40));

        jButton2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton2.setText("إضافة");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        stockPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 615, 100, 40));

        jButton4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton4.setText("إزالة");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        stockPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 615, 100, 40));

        jButton3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton3.setText("تعديل");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        stockPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 615, 100, 40));

        jButton5.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton5.setText("إعادة");
        jButton5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        stockPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 615, 100, 40));

        jLabel70.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel70.setText("حد النقص :");
        stockPanel1.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 290, 150, 60));

        text1_4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        text1_4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text1_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        text1_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1_4ActionPerformed(evt);
            }
        });
        stockPanel1.add(text1_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 390, 60));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("عائد المبيعات :");
        stockPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 540, 160, 60));

        itemComboBox.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemComboBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemComboBoxMouseClicked(evt);
            }
        });
        itemComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComboBoxActionPerformed(evt);
            }
        });
        itemComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemComboBoxKeyPressed(evt);
            }
        });
        stockPanel1.add(itemComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 170, 60));

        totalSalesPrice.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        totalSalesPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalSalesPrice.setText("000");
        totalSalesPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        stockPanel1.add(totalSalesPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 150, 60));

        excelStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStockMouseClicked(evt);
            }
        });
        stockPanel1.add(excelStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 10, -1, -1));

        stockPanel.add(stockPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 600, 670));

        stockPanel2.setBackground(new java.awt.Color(236, 240, 241));
        stockPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        stock_table.setAutoCreateRowSorter(true);
        stock_table.getTableHeader().setPreferredSize(new Dimension(100,50));
        stock_table.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 14));
        stock_table.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        stock_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        stock_table.setToolTipText("");
        stock_table.setGridColor(new java.awt.Color(153, 153, 153));
        stock_table.setRowHeight(45);
        stock_table.setRowMargin(3);
        stock_table.setShowHorizontalLines(true);
        stock_table.setShowVerticalLines(true);
        stock_table.setSurrendersFocusOnKeystroke(true);
        tableCenter(stock_table);
        stock_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stock_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(stock_table);

        stockPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 670));

        stockPanel.add(stockPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 550, 670));

        fullPanel.add(stockPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        mandobPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mandobPanel1.setBackground(new java.awt.Color(189, 195, 199));
        mandobPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        mandobPanel1.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 30, 60));

        jLabel18.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("%");
        mandobPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 510, 50, 60));

        mandobId.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mandobId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mandobId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mandobId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mandobIdFocusGained(evt);
            }
        });
        mandobId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobIdActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobId, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 390, 60));

        jLabel38.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mandobPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 150, 50));

        jLabel39.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("اسم المندوب :");
        mandobPanel1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 230, 160, 60));

        mandobName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mandobName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mandobName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mandobName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobNameActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobName, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, 390, 60));

        jLabel40.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("الموبايل :");
        mandobPanel1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 440, 160, 60));

        mandobCardName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mandobCardName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mandobCardName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mandobCardName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobCardNameActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobCardName, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 300, 390, 60));

        mandobLocation.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mandobLocation.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mandobLocation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mandobLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobLocationActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, 390, 60));

        mandobCollectionRate.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mandobCollectionRate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mandobCollectionRate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mandobCollectionRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobCollectionRateActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobCollectionRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 510, 390, 60));

        jLabel41.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("رقم البطاقة :");
        mandobPanel1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 300, 160, 60));

        mandobMobile.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        mandobMobile.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mandobMobile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mandobMobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobMobileActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobMobile, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 440, 390, 60));

        mandobReceivedItems.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        mandobReceivedItems.setText("عرض");
        mandobReceivedItems.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        mandobReceivedItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mandobReceivedItemsMouseClicked(evt);
            }
        });
        mandobPanel1.add(mandobReceivedItems, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 110, 50));

        jButton18.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton18.setText("إختيار");
        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton18MouseClicked(evt);
            }
        });
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        mandobPanel1.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 170, -1, 40));

        jButton19.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton19.setText("تعديل");
        jButton19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton19MouseClicked(evt);
            }
        });
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        mandobPanel1.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 590, 110, 50));

        jLabel43.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("نسبة التحصيل :");
        mandobPanel1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 510, 170, 60));

        jLabel44.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel44.setText("الكود : ");
        mandobPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 160, 160, 60));

        jLabel45.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("الأصناف المستلمة");
        mandobPanel1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 210, 50));

        jLabel46.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("عملاء التحصيل");
        mandobPanel1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 210, 50));

        jLabel47.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mandobPanel1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 150, 50));

        mandobClients.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        mandobClients.setText("عرض");
        mandobClients.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        mandobClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mandobClientsMouseClicked(evt);
            }
        });
        mandobClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobClientsActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 110, 50));

        jLabel48.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("التحصيلات المتبقية ");
        mandobPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 210, 50));

        jLabel49.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mandobPanel1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 150, 50));

        mandobRemainingCollections.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        mandobRemainingCollections.setText("عرض");
        mandobRemainingCollections.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        mandobRemainingCollections.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mandobRemainingCollectionsMouseClicked(evt);
            }
        });
        mandobRemainingCollections.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobRemainingCollectionsActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobRemainingCollections, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 110, 50));

        jLabel50.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("المبالغ المستلمة");
        mandobPanel1.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 210, 50));

        jLabel51.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mandobPanel1.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 150, 50));

        mandobResevedMoney.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        mandobResevedMoney.setText("عرض");
        mandobResevedMoney.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        mandobResevedMoney.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mandobResevedMoneyMouseClicked(evt);
            }
        });
        mandobResevedMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandobResevedMoneyActionPerformed(evt);
            }
        });
        mandobPanel1.add(mandobResevedMoney, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 110, 50));

        jLabel52.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("المستحقات");
        mandobPanel1.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 450, 210, 50));

        jLabel53.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(153, 0, 0));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mandobPanel1.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 150, 50));

        jLabel58.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel58.setText("العنوان :");
        mandobPanel1.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 370, 160, 60));

        jLabel110.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("التحصيلات المكتملة ");
        mandobPanel1.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 210, 50));

        jLabel111.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mandobPanel1.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 150, 50));

        mandobCompletedCollections.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        mandobCompletedCollections.setText("عرض");
        mandobCompletedCollections.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        mandobCompletedCollections.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mandobCompletedCollectionsMouseClicked(evt);
            }
        });
        mandobPanel1.add(mandobCompletedCollections, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 110, 50));

        jButton66.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton66.setText("إعادة");
        jButton66.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton66MouseClicked(evt);
            }
        });
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });
        mandobPanel1.add(jButton66, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 590, 110, 50));

        jButton68.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton68.setText("إضافة");
        jButton68.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton68MouseClicked(evt);
            }
        });
        mandobPanel1.add(jButton68, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 590, 110, 50));

        jComboBox5.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "المنطقة" }));
        jComboBox5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mandobPanel1.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 390, 60));

        itemComboBox1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemComboBox1MouseClicked(evt);
            }
        });
        itemComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComboBox1ActionPerformed(evt);
            }
        });
        mandobPanel1.add(itemComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 390, 60));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("المنطقة :");
        mandobPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 90, 160, 60));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/block.png"))); // NOI18N
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        mandobPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 590, -1, -1));

        jButton22.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton22.setText("عرض");
        jButton22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton22MouseClicked(evt);
            }
        });
        mandobPanel1.add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 110, 50));

        jLabel55.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("الملاحظات");
        mandobPanel1.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, 210, 50));

        jLabel57.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mandobPanel1.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 150, 50));

        jLabel60.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel60.setText("الفرع :");
        mandobPanel1.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 160, 60));

        jButton23.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton23.setText("عرض");
        jButton23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton23MouseClicked(evt);
            }
        });
        mandobPanel1.add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 110, 50));

        mandobPanel.add(mandobPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1150, 670));

        fullPanel.add(mandobPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        clientPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        clientPanel1.setBackground(new java.awt.Color(189, 195, 199));
        clientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        online_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/online_blue.png"))); // NOI18N
        online_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                online_iconMouseClicked(evt);
            }
        });
        clientPanel1.add(online_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, -1, 20));

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        clientPanel1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, -1, 60));

        Cid.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        Cid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Cid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        Cid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CidActionPerformed(evt);
            }
        });
        clientPanel1.add(Cid, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 390, 60));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 180, 50));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("اسم العميل :");
        clientPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 230, 160, 60));

        Cname.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        Cname.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Cname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        Cname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CnameActionPerformed(evt);
            }
        });
        clientPanel1.add(Cname, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, 390, 60));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("الموبايل :");
        clientPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 440, 160, 60));

        cNum.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        cNum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        cNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cNumActionPerformed(evt);
            }
        });
        clientPanel1.add(cNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 300, 390, 60));

        add2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        add2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        add2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add2ActionPerformed(evt);
            }
        });
        clientPanel1.add(add2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, 195, 60));

        mob2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        mob2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mob2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mob2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mob2ActionPerformed(evt);
            }
        });
        clientPanel1.add(mob2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 440, 195, 60));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("رقم البطاقة :");
        clientPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 300, 160, 60));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("المهنة / المكان :");
        clientPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 510, 160, 60));

        mob1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        mob1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mob1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mob1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mob1ActionPerformed(evt);
            }
        });
        clientPanel1.add(mob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 440, 200, 60));

        jButton6.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton6.setText("عرض");
        jButton6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        clientPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 110, 50));

        jButton7.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton7.setText("إضافة");
        jButton7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        clientPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 590, 110, 50));

        jButton8.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton8.setText("تعديل");
        jButton8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        clientPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 590, 110, 50));

        jButton10.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton10.setText("إعادة");
        jButton10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });
        clientPanel1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 590, 110, 50));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("الكود : ");
        clientPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 160, 70, 60));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("الأصناف المستلمة");
        clientPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 210, 50));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("الملاحظات");
        clientPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 210, 50));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 180, 50));

        jButton12.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton12.setText("عرض");
        jButton12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        clientPanel1.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 110, 50));

        jLabel36.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("الحي / المصلحة :");
        clientPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 370, 160, 60));

        jobPlace.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jobPlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jobPlace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jobPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jobPlaceActionPerformed(evt);
            }
        });
        clientPanel1.add(jobPlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 510, 195, 60));

        add1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        add1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add1ActionPerformed(evt);
            }
        });
        clientPanel1.add(add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 370, 200, 60));

        Cjob.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        Cjob.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Cjob.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        Cjob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CjobActionPerformed(evt);
            }
        });
        clientPanel1.add(Cjob, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, 200, 60));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/block.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        clientPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, -1, -1));

        jButton69.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton69.setText("إختيار");
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });
        clientPanel1.add(jButton69, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 170, -1, 40));

        jComboBox6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "المنطقة" }));
        jComboBox6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        clientPanel1.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 390, 60));

        itemComboBox2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemComboBox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComboBox2ActionPerformed(evt);
            }
        });
        clientPanel1.add(itemComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 390, 60));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("المنطقة :");
        clientPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 90, 160, 60));

        jButton15.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton15.setText("عرض");
        jButton15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton15MouseClicked(evt);
            }
        });
        clientPanel1.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 110, 50));

        jButton16.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton16.setText("عرض");
        jButton16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton16MouseClicked(evt);
            }
        });
        clientPanel1.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 110, 50));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 180, 50));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 180, 50));

        jButton20.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton20.setText("عرض");
        jButton20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(131, 149, 167), 1, true));
        jButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton20MouseClicked(evt);
            }
        });
        clientPanel1.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 110, 50));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("الأقساط غير المكتملة");
        clientPanel1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 210, 50));

        jLabel42.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("الاقساط المكتملة");
        clientPanel1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 210, 50));

        jLabel54.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("الأقساط");
        clientPanel1.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 210, 50));

        jLabel59.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientPanel1.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 180, 50));

        jLabel61.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel61.setText("الفرع :");
        clientPanel1.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 160, 60));

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calendar.png"))); // NOI18N
        jLabel76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel76MouseClicked(evt);
            }
        });
        clientPanel1.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 430, -1, -1));

        clientPanel.add(clientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1150, 670));

        fullPanel.add(clientPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        areaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        areaPanel1.setBackground(new java.awt.Color(189, 195, 199));
        areaPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField13.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField13.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        areaPanel1.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 390, 60));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("الكود : ");
        areaPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 150, 60));

        jLabel26.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("اسم المنطقة :");
        areaPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 150, 60));

        jTextField25.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField25.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField25ActionPerformed(evt);
            }
        });
        areaPanel1.add(jTextField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 390, 60));

        jButton13.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton13.setText("عرض الكل");
        jButton13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });
        areaPanel1.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 130, 50));

        jButton14.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton14.setText("إضافة");
        jButton14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });
        areaPanel1.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 110, 50));

        jButton17.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton17.setText("إزالة");
        jButton17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton17MouseClicked(evt);
            }
        });
        areaPanel1.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 110, 50));

        jButton29.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton29.setText("إعادة");
        jButton29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton29MouseClicked(evt);
            }
        });
        areaPanel1.add(jButton29, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 110, 50));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("الفرع :");
        areaPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 140, 60));

        itemComboBox4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemComboBox4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComboBox4ActionPerformed(evt);
            }
        });
        itemComboBox4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemComboBox4KeyPressed(evt);
            }
        });
        areaPanel1.add(itemComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 390, 60));

        excelStock1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock1MouseClicked(evt);
            }
        });
        areaPanel1.add(excelStock1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 600, -1, -1));

        areaPanel.add(areaPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 600, 670));

        areaPanel2.setBackground(new java.awt.Color(236, 240, 241));
        areaPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        areaT.setAutoCreateRowSorter(true);
        areaT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        areaT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        areaT.setToolTipText("");
        areaT.setGridColor(new java.awt.Color(153, 153, 153));
        areaT.setRowHeight(45);
        areaT.setRowMargin(3);
        areaT.setShowHorizontalLines(true);
        areaT.setShowVerticalLines(true);
        areaT.setSurrendersFocusOnKeystroke(true);
        areaT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                areaTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(areaT);

        areaPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 670));

        areaPanel.add(areaPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 550, 670));

        fullPanel.add(areaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        PayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PayPanel1.setBackground(new java.awt.Color(189, 195, 199));
        PayPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        PayPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 60));

        mand.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        mand.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mand.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        mand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandActionPerformed(evt);
            }
        });
        PayPanel1.add(mand, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 300, 58));

        jButton32.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton32.setText("عرض الكل");
        jButton32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton32MouseClicked(evt);
            }
        });
        PayPanel1.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, 120, 50));

        jButton36.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton36.setText("ترحيل للشهر المقبل");
        jButton36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton36MouseClicked(evt);
            }
        });
        PayPanel1.add(jButton36, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 550, 210, 50));

        jLabel77.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel77.setText("المندوب :");
        PayPanel1.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 170, 58));

        jLabel88.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel88.setText("كود العميل :");
        PayPanel1.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 170, 58));

        jTextField35.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField35.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField35.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField35CaretUpdate(evt);
            }
        });
        jTextField35.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField35FocusGained(evt);
            }
        });
        jTextField35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField35ActionPerformed(evt);
            }
        });
        PayPanel1.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 300, 58));

        jLabel89.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel89.setText("اسم العميل :");
        PayPanel1.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 170, 58));

        jTextField36.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField36.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField36ActionPerformed(evt);
            }
        });
        PayPanel1.add(jTextField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 300, 58));

        jLabel90.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel90.setText("ترتيب القسط :");
        PayPanel1.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 170, 58));

        jTextField37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField37.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField37ActionPerformed(evt);
            }
        });
        PayPanel1.add(jTextField37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 300, 58));

        jLabel91.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel91.setText("الشهر :");
        PayPanel1.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 420, 170, 58));

        jTextField38.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField38.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField38ActionPerformed(evt);
            }
        });
        PayPanel1.add(jTextField38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 300, 58));

        jTextField39.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField39.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField39.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField39ActionPerformed(evt);
            }
        });
        PayPanel1.add(jTextField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 300, 58));

        jLabel94.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel94.setText("قيمة القسط :");
        PayPanel1.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, 170, 58));

        jButton38.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton38.setText("إعادة");
        jButton38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton38MouseClicked(evt);
            }
        });
        PayPanel1.add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 610, 100, 50));

        jButton43.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton43.setText("إضافة شهر");
        jButton43.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton43MouseClicked(evt);
            }
        });
        PayPanel1.add(jButton43, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 550, 200, 50));

        jButton71.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton71.setText("إختيار");
        PayPanel1.add(jButton71, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, -1, 40));

        jLabel130.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel130.setText("كود المندوب :");
        PayPanel1.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 170, 58));

        jTextField51.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField51.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField51.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField51ActionPerformed(evt);
            }
        });
        PayPanel1.add(jTextField51, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 58));

        jLabel131.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel131.setText("كود القسط :");
        PayPanel1.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, 170, 58));

        jTextField52.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTextField52.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField52.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField52ActionPerformed(evt);
            }
        });
        PayPanel1.add(jTextField52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 300, 58));

        jButton11.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton11.setText("تحصيل الكل");
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        PayPanel1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 610, 130, 50));

        jButton9.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton9.setText("تحصيل");
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        PayPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 610, 120, 50));

        light_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_off.png"))); // NOI18N
        light_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                light_lblMouseClicked(evt);
            }
        });
        PayPanel1.add(light_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 50, 60));

        PayPanel.add(PayPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 500, 670));

        PayPanel2.setBackground(new java.awt.Color(189, 195, 199));
        PayPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        qest.setAutoCreateRowSorter(true);
        qest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        qest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        qest.setToolTipText("");
        qest.setGridColor(new java.awt.Color(153, 153, 153));
        qest.setRowHeight(45);
        qest.setRowMargin(3);
        qest.setShowHorizontalLines(true);
        qest.setShowVerticalLines(true);
        qest.setSurrendersFocusOnKeystroke(true);
        qest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qestMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                qestMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(qest);

        PayPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 300, 590));

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        mandob.setAutoCreateRowSorter(true);
        mandob.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mandob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        mandob.setToolTipText("");
        mandob.setGridColor(new java.awt.Color(153, 153, 153));
        mandob.setRowHeight(45);
        mandob.setRowMargin(3);
        mandob.setShowHorizontalLines(true);
        mandob.setShowVerticalLines(true);
        mandob.setSurrendersFocusOnKeystroke(true);
        mandob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mandobMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mandobMouseEntered(evt);
            }
        });
        jScrollPane6.setViewportView(mandob);

        PayPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 170, 590));

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        clients.setAutoCreateRowSorter(true);
        clients.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        clients.setToolTipText("");
        clients.setGridColor(new java.awt.Color(153, 153, 153));
        clients.setRowHeight(45);
        clients.setRowMargin(3);
        clients.setShowHorizontalLines(true);
        clients.setShowVerticalLines(true);
        clients.setSurrendersFocusOnKeystroke(true);
        clients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientsMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(clients);

        PayPanel2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 160, 590));

        jLabel95.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setText("الاقساط");
        PayPanel2.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 300, 60));

        jLabel96.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setText("المندوب");
        PayPanel2.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 170, 60));

        jLabel97.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("العملاء");
        PayPanel2.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 160, 60));

        PayPanel.add(PayPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, 670));

        fullPanel.add(PayPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        returnPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        returnPanel1.setBackground(new java.awt.Color(189, 195, 199));
        returnPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        returnPanel1.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, 60));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        returnPanel1.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, 60));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        returnPanel1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 60));

        jLabel79.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel79.setText("الصنف : ");
        returnPanel1.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 150, 58));

        jTextField29.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField29.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });
        returnPanel1.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 390, 58));

        jLabel80.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel80.setText("كود المندوب :");
        returnPanel1.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 290, 150, 58));

        jTextField31.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField31.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField31ActionPerformed(evt);
            }
        });
        jTextField31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField31KeyPressed(evt);
            }
        });
        returnPanel1.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 390, 58));

        jLabel81.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel81.setText("اسم المندوب :");
        returnPanel1.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 150, 58));

        jTextField32.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField32.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField32ActionPerformed(evt);
            }
        });
        returnPanel1.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 390, 58));

        jButton37.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton37.setText("عرض الكل");
        jButton37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton37MouseClicked(evt);
            }
        });
        returnPanel1.add(jButton37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 580, 130, 50));

        jButton39.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton39.setText("إضافة");
        jButton39.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton39MouseClicked(evt);
            }
        });
        returnPanel1.add(jButton39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 580, 120, 50));

        jButton42.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton42.setText("إعادة");
        jButton42.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton42MouseClicked(evt);
            }
        });
        returnPanel1.add(jButton42, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 580, 120, 50));

        jLabel128.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel128.setText("كود العميل :");
        returnPanel1.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 410, 150, 58));

        jLabel129.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel129.setText("اسم العميل :");
        returnPanel1.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 470, 150, 58));

        jTextField47.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField47.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField47.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField47ActionPerformed(evt);
            }
        });
        returnPanel1.add(jTextField47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 390, 58));

        jTextField50.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField50.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField50.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });
        jTextField50.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField50KeyPressed(evt);
            }
        });
        returnPanel1.add(jTextField50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 390, 58));

        itemId.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        itemId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemIdActionPerformed(evt);
            }
        });
        itemId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemIdKeyPressed(evt);
            }
        });
        returnPanel1.add(itemId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 390, 58));

        jLabel93.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel93.setText("كود الشيك :");
        returnPanel1.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 150, 58));

        itemComboBox5.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemComboBox5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComboBox5ActionPerformed(evt);
            }
        });
        returnPanel1.add(itemComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 390, 58));

        jLabel35.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("الفرع :");
        returnPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 90, 58));

        itemId1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemId1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        itemId1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemId1ActionPerformed(evt);
            }
        });
        itemId1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemId1KeyPressed(evt);
            }
        });
        returnPanel1.add(itemId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 390, 58));

        jLabel98.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel98.setText("كود الصنف :");
        returnPanel1.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 150, 58));

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enter.png"))); // NOI18N
        returnPanel1.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 60));

        excelStock2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock2MouseClicked(evt);
            }
        });
        returnPanel1.add(excelStock2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        returnPanel.add(returnPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 600, 670));

        returnPanel2.setBackground(new java.awt.Color(236, 240, 241));
        returnPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        returnTable.setAutoCreateRowSorter(true);
        returnTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        returnTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        returnTable.setToolTipText("");
        returnTable.setGridColor(new java.awt.Color(153, 153, 153));
        returnTable.setRowHeight(45);
        returnTable.setRowMargin(3);
        returnTable.setShowHorizontalLines(true);
        returnTable.setShowVerticalLines(true);
        returnTable.setSurrendersFocusOnKeystroke(true);
        jScrollPane4.setViewportView(returnTable);

        returnPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 670));

        returnPanel.add(returnPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 550, 670));

        fullPanel.add(returnPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        CalculationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CalculationPanel1.setBackground(new java.awt.Color(189, 195, 199));
        CalculationPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        CalculationPanel1.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, 40));

        jTextField33.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField33.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField33ActionPerformed(evt);
            }
        });
        CalculationPanel1.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 390, 60));

        jLabel84.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel84.setText("المبلغ :");
        CalculationPanel1.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, 150, 60));

        jTextField34.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField34.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField34.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField34CaretUpdate(evt);
            }
        });
        jTextField34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField34MouseClicked(evt);
            }
        });
        jTextField34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField34ActionPerformed(evt);
            }
        });
        CalculationPanel1.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 390, 60));

        jButton45.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton45.setText("عرض الكل");
        jButton45.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton45MouseClicked(evt);
            }
        });
        CalculationPanel1.add(jButton45, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 130, 50));

        jButton47.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton47.setText("تعديل");
        jButton47.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton47MouseClicked(evt);
            }
        });
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });
        CalculationPanel1.add(jButton47, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 370, 120, 50));

        jButton48.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton48.setText("إزالة");
        jButton48.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton48MouseClicked(evt);
            }
        });
        CalculationPanel1.add(jButton48, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 120, 50));

        jButton52.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton52.setText("إعادة");
        jButton52.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton52MouseClicked(evt);
            }
        });
        CalculationPanel1.add(jButton52, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 370, 120, 50));

        jLabel86.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel86.setText("المسمي :");
        CalculationPanel1.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 150, 60));

        jButton67.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jButton67.setText("الشركاء");
        jButton67.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton67MouseClicked(evt);
            }
        });
        CalculationPanel1.add(jButton67, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 570, 210, 60));

        jLabel132.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel132.setText("الكود :");
        CalculationPanel1.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 150, 60));

        jTextField40.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField40.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField40.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField40ActionPerformed(evt);
            }
        });
        CalculationPanel1.add(jTextField40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 390, 60));

        jButton70.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jButton70.setText("الحسابات");
        jButton70.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton70MouseClicked(evt);
            }
        });
        CalculationPanel1.add(jButton70, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 570, 210, 60));

        jLabel67.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel67.setText("الفرع :");
        CalculationPanel1.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 90, 58));

        itemComboBox6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemComboBox6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComboBox6ActionPerformed(evt);
            }
        });
        CalculationPanel1.add(itemComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 390, 58));

        excelStock3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock3MouseClicked(evt);
            }
        });
        CalculationPanel1.add(excelStock3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));

        CalculationPanel.add(CalculationPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 600, 670));

        CalculationPanel2.setBackground(new java.awt.Color(236, 240, 241));
        CalculationPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        recivedMonyT.setAutoCreateRowSorter(true);
        recivedMonyT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        recivedMonyT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        recivedMonyT.setToolTipText("");
        recivedMonyT.setGridColor(new java.awt.Color(153, 153, 153));
        recivedMonyT.setRowHeight(45);
        recivedMonyT.setRowMargin(3);
        recivedMonyT.setShowHorizontalLines(true);
        recivedMonyT.setShowVerticalLines(true);
        recivedMonyT.setSurrendersFocusOnKeystroke(true);
        recivedMonyT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recivedMonyTMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(recivedMonyT);

        CalculationPanel2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 670));

        CalculationPanel.add(CalculationPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 550, 670));

        fullPanel.add(CalculationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        notePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        notePanel1.setBackground(new java.awt.Color(189, 195, 199));
        notePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        date.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        date.setText(String.valueOf(LocalDate.now()));
        date.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateActionPerformed(evt);
            }
        });
        notePanel1.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 380, 60));

        jLabel78.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel78.setText("التاريخ :");
        notePanel1.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 120, 60));

        jLabel87.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel87.setText("الملاحظة :");
        notePanel1.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, 120, 60));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jScrollPane8.setViewportView(jTextArea1);

        notePanel1.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 380, 230));

        jButton44.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton44.setText("إضافة");
        jButton44.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton44MouseClicked(evt);
            }
        });
        notePanel1.add(jButton44, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 610, 95, 50));

        jButton49.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton49.setText("تعديل");
        jButton49.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton49MouseClicked(evt);
            }
        });
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        notePanel1.add(jButton49, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 610, 95, 50));

        jButton50.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton50.setText("إزالة");
        jButton50.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton50MouseClicked(evt);
            }
        });
        notePanel1.add(jButton50, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 610, 95, 50));

        jButton51.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton51.setText("إعادة");
        jButton51.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton51MouseClicked(evt);
            }
        });
        notePanel1.add(jButton51, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 610, 100, 50));

        jButton55.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton55.setText("عرض الكل");
        jButton55.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167)));
        jButton55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton55MouseClicked(evt);
            }
        });
        notePanel1.add(jButton55, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 120, 50));

        jLabel99.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel99.setText("الموضوع :");
        notePanel1.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 290, 120, 60));

        jTextField41.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField41.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField41.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField41ActionPerformed(evt);
            }
        });
        notePanel1.add(jTextField41, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 380, 60));

        jLabel127.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel127.setText("الكود :");
        notePanel1.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 120, 60));

        jTextField53.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField53.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField53.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField53ActionPerformed(evt);
            }
        });
        notePanel1.add(jTextField53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 380, 60));

        itemComboBox3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        itemComboBox3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComboBox3ActionPerformed(evt);
            }
        });
        notePanel1.add(itemComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 380, 60));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("النوع :");
        notePanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 120, 60));

        jLabel56.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel56.setText("الفرع :");
        notePanel1.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 70, 60));

        jComboBox7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "الكل", "مندوب", "عميل", "اخرى" }));
        jComboBox7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jComboBox7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox7MouseClicked(evt);
            }
        });
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        notePanel1.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 380, 60));

        excelStock4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock4MouseClicked(evt);
            }
        });
        notePanel1.add(excelStock4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        notePanel.add(notePanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 560, 670));

        notePanel2.setBackground(new java.awt.Color(236, 240, 241));
        notePanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane9.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        notesTable.setAutoCreateRowSorter(true);
        notesTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        notesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        notesTable.setToolTipText("");
        notesTable.setGridColor(new java.awt.Color(153, 153, 153));
        notesTable.setRowHeight(45);
        notesTable.setRowMargin(3);
        notesTable.setShowHorizontalLines(true);
        notesTable.setShowVerticalLines(true);
        notesTable.setSurrendersFocusOnKeystroke(true);
        notesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notesTableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(notesTable);

        notePanel2.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 670));

        notePanel.add(notePanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 590, 670));

        fullPanel.add(notePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        reciptPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reciptPanel1.setBackground(new java.awt.Color(189, 195, 199));
        reciptPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel112.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(255, 0, 0));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel112MouseClicked(evt);
            }
        });
        reciptPanel1.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 220, 70));

        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/receipt.png"))); // NOI18N
        jLabel116.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel116MouseClicked(evt);
            }
        });
        reciptPanel1.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 60));

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
        reciptPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 180, 60));

        jComboBox4.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "المندوب", "المندوب 1", "المندوب 2", "المندوب 3", "" }));
        jComboBox4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jComboBox4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox4KeyPressed(evt);
            }
        });
        reciptPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 240, 60));

        jComboBox8.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "الشهر", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jComboBox8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        reciptPanel1.add(jComboBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 180, 60));

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
        reciptPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, 260, 60));

        excelStock5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock5MouseClicked(evt);
            }
        });
        reciptPanel1.add(excelStock5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        reciptPanel.add(reciptPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1150, 90));

        reciptPanel2.setBackground(new java.awt.Color(236, 240, 241));
        reciptPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane10.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        reciptT.getTableHeader().setReorderingAllowed(false);
        reciptT.setAutoCreateRowSorter(true);
        reciptT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reciptT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        reciptT.setToolTipText("");
        reciptT.setGridColor(new java.awt.Color(153, 153, 153));
        reciptT.setRowHeight(45);
        reciptT.setRowMargin(3);
        reciptT.setShowHorizontalLines(true);
        reciptT.setShowVerticalLines(true);
        reciptT.setSurrendersFocusOnKeystroke(true);
        jScrollPane10.setViewportView(reciptT);

        reciptPanel2.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 580));

        reciptPanel.add(reciptPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1150, 580));

        fullPanel.add(reciptPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        reportPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reciptPanel3.setBackground(new java.awt.Color(189, 195, 199));
        reciptPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportType.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        reportType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "عرض التقارير", "المندوب", "العملاء", "الاصناف", "الشركاء" }));
        reportType.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        reportType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportTypeActionPerformed(evt);
            }
        });
        reciptPanel3.add(reportType, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 390, 60));

        reportType1.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        reportType1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "الشهر", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        reportType1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        reportType1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportType1ActionPerformed(evt);
            }
        });
        reciptPanel3.add(reportType1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 180, 60));

        jComboBox9.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "السنة", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035" }));
        jComboBox9.setToolTipText("");
        jComboBox9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });
        jComboBox9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox9KeyPressed(evt);
            }
        });
        reciptPanel3.add(jComboBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 260, 60));

        excelStock6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock6MouseClicked(evt);
            }
        });
        reciptPanel3.add(excelStock6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        reportPanel.add(reciptPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1150, 90));

        reciptPanel4.setBackground(new java.awt.Color(236, 240, 241));
        reciptPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane11.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        reports.setAutoCreateRowSorter(true);
        reports.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        reports.setToolTipText("");
        reports.setGridColor(new java.awt.Color(153, 153, 153));
        reports.setRowHeight(45);
        reports.setRowMargin(3);
        reports.setShowHorizontalLines(true);
        reports.setShowVerticalLines(true);
        reports.setSurrendersFocusOnKeystroke(true);
        jScrollPane11.setViewportView(reports);

        reciptPanel4.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 580));

        reportPanel.add(reciptPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1150, 580));

        fullPanel.add(reportPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        usersPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        notePanel3.setBackground(new java.awt.Color(189, 195, 199));
        notePanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        itemPanel12.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, -1, -1));
        itemPanel12.add(user3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, -1, 30));
        itemPanel12.add(user4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 400, -1, 30));

        user5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user5ActionPerformed(evt);
            }
        });
        itemPanel12.add(user5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, -1, 30));
        itemPanel12.add(user6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 480, -1, 30));
        itemPanel12.add(user10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, -1, 30));
        itemPanel12.add(user11, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 440, -1, 30));
        itemPanel12.add(user12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, -1, 30));
        itemPanel12.add(user2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, -1, 30));
        itemPanel12.add(user9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, 30));
        itemPanel12.add(user8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, -1, 30));

        check_lbl11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl11.setText("المستخدمين");
        itemPanel12.add(check_lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 100, 30));

        check_lbl12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl12.setText("الحسابات");
        itemPanel12.add(check_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 100, 30));

        check_lbl13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl13.setText("الملاحظات");
        itemPanel12.add(check_lbl13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 100, 30));

        check_lbl14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl14.setText("الايصالات");
        itemPanel12.add(check_lbl14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 100, 30));

        check_lbl15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl15.setText("التقارير");
        itemPanel12.add(check_lbl15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 100, 30));

        check_lbl16.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl16.setText("التحصيل");
        itemPanel12.add(check_lbl16, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 440, 100, 30));

        check_lbl17.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl17.setText("المناطق");
        itemPanel12.add(check_lbl17, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 400, 100, 30));

        check_lbl18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl18.setText("العملاء");
        itemPanel12.add(check_lbl18, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 100, 30));

        check_lbl19.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl19.setText("المندوب");
        itemPanel12.add(check_lbl19, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, 100, 30));

        check_lbl20.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl20.setText("المخزن");
        itemPanel12.add(check_lbl20, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, 100, 30));

        userName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        userName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        userName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });
        itemPanel12.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 280, 60));

        jLabel100.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel100.setText("اسم المستخدم :");
        itemPanel12.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 170, 60));

        password.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        password.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        itemPanel12.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 280, 60));

        jLabel102.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel102.setText("رمز الدخول :");
        itemPanel12.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 170, 60));

        check_lbl21.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl21.setText("الحظر");
        itemPanel12.add(check_lbl21, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 100, 30));

        check_lbl22.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        check_lbl22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        check_lbl22.setText("المرتجعات");
        itemPanel12.add(check_lbl22, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 480, 100, 30));
        itemPanel12.add(user1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 280, -1, 30));

        user7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user7ActionPerformed(evt);
            }
        });
        itemPanel12.add(user7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, -1, 30));

        branch.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        branch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        branch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchActionPerformed(evt);
            }
        });
        itemPanel12.add(branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 280, 60));

        branch_lbl12.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        branch_lbl12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        branch_lbl12.setText("الفرع :");
        itemPanel12.add(branch_lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 80, 60));

        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minus.png"))); // NOI18N
        jLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel71MouseClicked(evt);
            }
        });
        itemPanel12.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, -1, -1));

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel72MouseClicked(evt);
            }
        });
        itemPanel12.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, -1));

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_database.png"))); // NOI18N
        jLabel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel73MouseClicked(evt);
            }
        });
        itemPanel12.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 500, -1, -1));

        excelStock7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock7MouseClicked(evt);
            }
        });
        itemPanel12.add(excelStock7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        notePanel3.add(itemPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 530, 540));

        jButton56.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton56.setText("إضافة");
        jButton56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton56MouseClicked(evt);
            }
        });
        notePanel3.add(jButton56, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, -1, 40));

        jButton57.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton57.setText("تعديل");
        jButton57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton57MouseClicked(evt);
            }
        });
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });
        notePanel3.add(jButton57, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 590, -1, 40));

        jButton58.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton58.setText("إزالة");
        jButton58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton58MouseClicked(evt);
            }
        });
        notePanel3.add(jButton58, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 590, 80, 40));

        jButton59.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton59.setText("إعادة");
        jButton59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton59MouseClicked(evt);
            }
        });
        notePanel3.add(jButton59, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 590, 90, 40));

        jButton60.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton60.setText("عرض الكل");
        jButton60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton60MouseClicked(evt);
            }
        });
        notePanel3.add(jButton60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, -1, 40));

        usersPanel.add(notePanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 560, 670));

        notePanel4.setBackground(new java.awt.Color(236, 240, 241));
        notePanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane14.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        userT.setAutoCreateRowSorter(true);
        userT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        userT.setToolTipText("");
        userT.setGridColor(new java.awt.Color(153, 153, 153));
        userT.setRowHeight(45);
        userT.setRowMargin(3);
        userT.setShowHorizontalLines(true);
        userT.setShowVerticalLines(true);
        userT.setSurrendersFocusOnKeystroke(true);
        userT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(userT);

        notePanel4.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 580, 670));

        usersPanel.add(notePanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 590, 670));

        fullPanel.add(usersPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        BlockPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BlockPanel1.setBackground(new java.awt.Color(189, 195, 199));
        BlockPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel101.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel101.setText("الكود :");
        BlockPanel1.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 140, 60));

        jTextField44.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField44.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField44.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField44ActionPerformed(evt);
            }
        });
        BlockPanel1.add(jTextField44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 380, 60));

        jTextField45.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField45.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField45.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        jTextField45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField45ActionPerformed(evt);
            }
        });
        BlockPanel1.add(jTextField45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 380, 60));

        jLabel103.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel103.setText("اسم العميل :");
        BlockPanel1.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, 140, 60));

        jButton61.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton61.setText("إضافة");
        jButton61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton61MouseClicked(evt);
            }
        });
        BlockPanel1.add(jButton61, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 120, -1));

        jButton63.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton63.setText("إزالة");
        jButton63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton63MouseClicked(evt);
            }
        });
        BlockPanel1.add(jButton63, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, 110, -1));

        jButton64.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton64.setText("إعادة");
        BlockPanel1.add(jButton64, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 110, -1));

        jButton65.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jButton65.setText("عرض الكل");
        jButton65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton65MouseClicked(evt);
            }
        });
        BlockPanel1.add(jButton65, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 140, -1));

        branch1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        branch1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(131, 149, 167), 3));
        branch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branch1ActionPerformed(evt);
            }
        });
        BlockPanel1.add(branch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 380, 60));

        branch_lbl13.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        branch_lbl13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        branch_lbl13.setText("الفرع :");
        BlockPanel1.add(branch_lbl13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 70, 60));

        excelStock8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/excel2.png"))); // NOI18N
        excelStock8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelStock8MouseClicked(evt);
            }
        });
        BlockPanel1.add(excelStock8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, -1, -1));

        BlockPanel.add(BlockPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 580, 670));

        BlockPanel2.setBackground(new java.awt.Color(236, 240, 241));
        BlockPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane12.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        blockedT.setAutoCreateRowSorter(true);
        blockedT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        blockedT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        blockedT.setToolTipText("");
        blockedT.setGridColor(new java.awt.Color(153, 153, 153));
        blockedT.setRowHeight(45);
        blockedT.setRowMargin(3);
        blockedT.setShowHorizontalLines(true);
        blockedT.setShowVerticalLines(true);
        blockedT.setSurrendersFocusOnKeystroke(true);
        blockedT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blockedTMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(blockedT);

        BlockPanel2.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 670));

        BlockPanel.add(BlockPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 570, 670));

        fullPanel.add(BlockPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        aboutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BlockPanel3.setBackground(new java.awt.Color(189, 195, 199));
        BlockPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel106.setFont(new java.awt.Font("Segoe UI", 1, 100)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(53, 59, 72));
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setText("قسّط");
        BlockPanel3.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 330, 130));

        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
        BlockPanel3.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, -1, -1));

        jLabel108.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(53, 59, 72));
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setText("All Rights reserved 2019    © Hash Company ©     جميع الحقوق محفوظة");
        BlockPanel3.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 570, -1, -1));

        jLabel109.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(53, 59, 72));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setText("WebSite : www.HashSC.com      ---------------------------       Mobile : 01068322486");
        BlockPanel3.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 610, -1, -1));

        aboutPanel.add(BlockPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1150, 670));

        fullPanel.add(aboutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(fullPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane1MousePressed
        // TODO add your handling code here:
        setColor(pane1, label1, markerPanel1);
        resetColor(new JPanel[]{pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
        showPanel(homePanel);
        hidePanel(new JPanel[]{stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
        panel_num = 1;
        numbers0();
    }//GEN-LAST:event_pane1MousePressed

    private void pane2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane2MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p1")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane2, label2, markerPanel2);
            resetColor(new JPanel[]{pane1, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(stockPanel);
            hidePanel(new JPanel[]{homePanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 2;
        } else {
            pane2.setBackground(new Color(232, 65, 24));
            label2.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane2MousePressed

    private void pane3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane3MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p2")) {
                        allowed = 1;
                    }
                }
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane3, label3, markerPanel3);
            resetColor(new JPanel[]{pane1, pane2, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(mandobPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 3;
        } else {
            pane3.setBackground(new Color(232, 65, 24));
            label3.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane3MousePressed

    private void pane4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane4MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p3")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane4, label4, markerPanel4);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(clientPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 4;
        } else {
            pane4.setBackground(new Color(232, 65, 24));
            label4.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane4MousePressed

    private void pane5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane5MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p4")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane5, label5, markerPanel5);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label6, label7, label8, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(areaPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 5;
        } else {
            pane5.setBackground(new Color(232, 65, 24));
            label5.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane5MousePressed

    private void pane6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane6MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p5")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane6, label6, markerPanel6);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label7, label8, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(PayPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 6;
        } else {
            pane6.setBackground(new Color(232, 65, 24));
            label6.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane6MousePressed

    private void pane7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane7MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p6")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane7, label7, markerPanel7);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane8, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label8, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(returnPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 7;
        } else {
            pane7.setBackground(new Color(232, 65, 24));
            label7.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane7MousePressed

    private void pane8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane8MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p7")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane8, label8, markerPanel8);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane9, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label9, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(CalculationPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 8;
        } else {
            pane8.setBackground(new Color(232, 65, 24));
            label8.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane8MousePressed

    private void pane9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane9MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p8")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane9, label9, markerPanel9);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane10, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label10, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel10, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(notePanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, reciptPanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 9;
        } else {
            pane9.setBackground(new Color(232, 65, 24));
            label9.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane9MousePressed

    private void pane10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane10MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p9")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane10, label10, markerPanel10);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane11, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label11, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel11, markerPanel12, markerPanel13, markerPanel14});
            showPanel(reciptPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reportPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 10;
            recipt();
        } else {
            pane10.setBackground(new Color(232, 65, 24));
            label10.setForeground(new Color(255, 255, 255));
        }

    }//GEN-LAST:event_pane10MousePressed

    private void pane11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane11MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p10")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane11, label11, markerPanel11);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane12, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label12, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel12, markerPanel13, markerPanel14});
            showPanel(reportPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, usersPanel, BlockPanel, aboutPanel});
            panel_num = 11;
            report();
        } else {
            pane11.setBackground(new Color(232, 65, 24));
            label11.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane11MousePressed

    private void pane12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane12MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p11")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane12, label12, markerPanel12);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane13, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label13, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel13, markerPanel14});
            showPanel(usersPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, BlockPanel, aboutPanel});
            panel_num = 12;
        } else {
            pane12.setBackground(new Color(232, 65, 24));
            label12.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane12MousePressed

    private void pane13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane13MousePressed
        int allowed = 0;
        if (user_lbl.getText().equals("Hash")) {
            allowed = 1;
        } else {
            String sql = "SELECT * FROM users WHERE name = '" + user_lbl.getText() + "';";
            DBcon db = new DBcon();
            try {

                db.rs = db.st.executeQuery(sql);

                while (db.rs.next()) {

                    if (db.rs.getBoolean("p12")) {
                        allowed = 1;
                    }
                }
                db.con.close();
            } catch (Exception e) {

            }
        }
        if (allowed == 1) {
            setColor(pane13, label13, markerPanel13);
            resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane14}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label14}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel14});
            showPanel(BlockPanel);
            hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, aboutPanel});
            panel_num = 13;
        } else {
            pane13.setBackground(new Color(232, 65, 24));
            label3.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_pane13MousePressed

    private void pane14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane14MousePressed
        setColor(pane14, label14, markerPanel14);
        resetColor(new JPanel[]{pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13}, new JLabel[]{label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13}, new JPanel[]{markerPanel1, markerPanel2, markerPanel3, markerPanel4, markerPanel5, markerPanel6, markerPanel7, markerPanel8, markerPanel9, markerPanel10, markerPanel11, markerPanel12, markerPanel13});
        showPanel(aboutPanel);
        hidePanel(new JPanel[]{homePanel, stockPanel, mandobPanel, clientPanel, areaPanel, PayPanel, returnPanel, CalculationPanel, notePanel, reciptPanel, reportPanel, usersPanel, BlockPanel});
        panel_num = 14;
    }//GEN-LAST:event_pane14MousePressed

    int posX, posY;

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        posX = evt.getX();
        posY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if (maximized == true) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - posX, y - posY);
        }
    }//GEN-LAST:event_formMouseDragged

    private void close_lblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_lblMousePressed
        System.exit(0);
    }//GEN-LAST:event_close_lblMousePressed

    private void lock_lblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lock_lblMousePressed
        new login().setVisible(true);
        dispose();
    }//GEN-LAST:event_lock_lblMousePressed

    //DefaultTableModel modle;
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void text1_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_1ActionPerformed
        String id = null, name = "unknown", quant = null, price = null;
        id = text1_1.getText();
        String alarm = null, buyPrice = null, cashPrice = null, salesPresentage = null, taksetprice = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `item` WHERE id = " + id + ";";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                quant = String.valueOf(d.rs.getInt("quantity"));
                name = String.valueOf(d.rs.getString("name"));
                buyPrice = String.valueOf(d.rs.getFloat("buy_price"));
                alarm = String.valueOf(d.rs.getInt("cons_rate"));
                cashPrice = String.valueOf(d.rs.getFloat("cash_price"));
                taksetprice = String.valueOf(d.rs.getFloat("Taqset_price"));
                salesPresentage = String.valueOf(d.rs.getFloat("selling_percentage"));
                itemComboBox.setSelectedItem(d.rs.getString("branchName"));
            }

            d.con.close();
        } catch (SQLException ex) {

        }
        text1_1.setText(id);
        text1_2.setText(name);
        text1_3.setText(quant);
        text1_4.setText(alarm);
        text1_5.setText(buyPrice);
        text1_6.setText(cashPrice);
        text1_7.setText(taksetprice);
        text1_8.setText(salesPresentage);

    }//GEN-LAST:event_text1_1ActionPerformed

    private void text1_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_2ActionPerformed

    }//GEN-LAST:event_text1_2ActionPerformed

    private void text1_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1_3ActionPerformed

    private void text1_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1_5ActionPerformed

    private void text1_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1_6ActionPerformed

    private void text1_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1_7ActionPerformed

    private void text1_8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1_8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void CidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CidActionPerformed
        numbers2();
    }//GEN-LAST:event_CidActionPerformed

    private void CnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CnameActionPerformed

    private void cNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cNumActionPerformed

    private void add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add2ActionPerformed

    private void mob2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mob2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mob2ActionPerformed

    private void mob1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mob1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mob1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void mandobIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobIdActionPerformed
        // TODO add your handling code here:
        numbers();
    }//GEN-LAST:event_mandobIdActionPerformed

    private void mandobNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobNameActionPerformed

    private void mandobCardNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobCardNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobCardNameActionPerformed

    private void mandobLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobLocationActionPerformed

    private void mandobCollectionRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobCollectionRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobCollectionRateActionPerformed

    private void mandobMobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobMobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobMobileActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void mandobResevedMoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobResevedMoneyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobResevedMoneyActionPerformed

    private void add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add1ActionPerformed

    private void CjobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CjobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CjobActionPerformed

    private void jobPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jobPlaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jobPlaceActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25ActionPerformed

    private void mandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandActionPerformed

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    private void jTextField31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31ActionPerformed

    private void jTextField32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField32ActionPerformed

    private void jTextField33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33ActionPerformed

    private void jTextField34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField34ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jTextField35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField35ActionPerformed
        DBcon d = new DBcon();
        String month, id1, val, order;
        int clientId = Integer.valueOf(jTextField35.getText());
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat`,client_recieved_items,client WHERE aqsat.CRI = client_recieved_items.id AND client_recieved_items.id = client.id AND client.id = '" + clientId + "' ");
            while ((d.rs).next()) {
                month = String.valueOf(d.rs.getDate("month_payment"));
                id1 = String.valueOf(d.rs.getInt("id"));
                val = String.valueOf(d.rs.getFloat("value"));
                order = String.valueOf(d.rs.getInt("order"));
                String[] rowData = {order, val, month, id1};
                qestTahsel.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextField35ActionPerformed

    private void jTextField36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField36ActionPerformed

    private void jTextField37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField37ActionPerformed

    private void jTextField38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38ActionPerformed

    private void jTextField39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField39ActionPerformed

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton49ActionPerformed

    private void jTextField41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41ActionPerformed

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton57ActionPerformed

    private void mandobReceivedItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mandobReceivedItemsMouseClicked
        JFrame frame = new JFrame("Frame");
        recieved_items r = new recieved_items("مندوب", mandobName.getText(), Integer.valueOf(mandobId.getText()));
        frame.setSize(1150, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_mandobReceivedItemsMouseClicked

    private void mandobClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mandobClientsMouseClicked
        related_client r = new related_client(Integer.valueOf(mandobId.getText()), mandobName.getText());
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_mandobClientsMouseClicked

    private void mandobRemainingCollectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mandobRemainingCollectionsMouseClicked
        tahselat r = new tahselat(Integer.valueOf(mandobId.getText()), mandobName.getText(), 1);
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_mandobRemainingCollectionsMouseClicked

    private void mandobResevedMoneyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mandobResevedMoneyMouseClicked
        recieved_money r = new recieved_money(Integer.valueOf(mandobId.getText()), mandobName.getText());
        JFrame frame = new JFrame("Frame");
        frame.setSize(1150, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_mandobResevedMoneyMouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        clientRI r = new clientRI(Cname.getText(), "عميل", Integer.valueOf(Cid.getText()));
        JFrame frame = new JFrame("Frame");
        frame.setSize(1170, 730);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        month_payment r = new month_payment(Cname.getText(), Integer.valueOf(Cid.getText()));
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton12MouseClicked

    private void text1_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1_4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1_4ActionPerformed

    private void mandobCompletedCollectionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mandobCompletedCollectionsMouseClicked
        tahselat r = new tahselat(Integer.valueOf(mandobId.getText()), mandobName.getText(), 2);
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_mandobCompletedCollectionsMouseClicked

    private void jButton67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton67MouseClicked
        partners r = new partners();
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 740);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton67MouseClicked

    private void jTextField47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47ActionPerformed

    private void jTextField50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField50ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        delTblCol(stockTable);
        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String stockName = null, id = null, quant = null, consAlarm = null, buyPrice = null, cashPrice = null, installmentPrice = null, PercentageSales = null;
        if (!itemComboBox.getSelectedItem().equals("اختر الفرع")) {
            try {
                if (this.text1_1.getText().isEmpty() == true) {
                    id = String.valueOf(auto_insert("item", "id"));
                } else {
                    id = String.valueOf(Long.parseLong(this.text1_1.getText()));
                }

                stockName = text1_2.getText();
                quant = text1_3.getText();
                consAlarm = text1_4.getText();
                buyPrice = text1_5.getText();
                cashPrice = text1_6.getText();
                installmentPrice = text1_7.getText();
                PercentageSales = text1_8.getText();
                //if (text1_1.getText().isEmpty()) {
                String sql = "INSERT INTO `item` (`id`, `name`, `quantity`, `cons_rate`, `buy_price`, `cash_price`, `Taqset_price`, `selling_percentage`, `cons` , branchName) VALUES ('" + id + "', '" + stockName + "', '" + quant + "', '" + consAlarm + "', '" + buyPrice + "', '" + cashPrice + "', '" + installmentPrice + "', '" + PercentageSales + "', '1' , '" + itemComboBox.getSelectedItem() + "' );";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql);
                // } else {
                //    JOptionPane.showMessageDialog(null, "Clear Fields And Try Again");
                // }
                String sql2 = "SELECT * FROM `item`";
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    name1 = d.rs.getString("name");
                    id1 = String.valueOf(d.rs.getInt("id"));
                    quant1 = String.valueOf(d.rs.getInt("quantity"));
                    String[] rowData = {quant1, name1, id1};
                    stockTable.addRow(rowData);
                }
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation` , `type`) VALUES (NULL, '" + id + "', '" + stockName + "', '" + LocalDateTime.now() + "', 'اضافة صنف', 'item');");

                d.con.close();
                JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
            }
        } else {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        delTblCol(stockTable);
        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String stockName = null, id = null, quant = null, consAlarm = null, buyPrice = null, cashPrice = null, installmentPrice = null, PercentageSales = null;
        try {

            id = text1_1.getText();
            stockName = text1_2.getText();
            quant = text1_3.getText();
            consAlarm = text1_4.getText();
            buyPrice = text1_5.getText();
            cashPrice = text1_6.getText();
            installmentPrice = text1_7.getText();
            PercentageSales = text1_8.getText();
            //if (text1_1.getText().isEmpty()) {
            String sql = "UPDATE `item` SET `name` = '" + stockName + "',`quantity` = '" + quant + "',`cons_rate` = '" + consAlarm + "',`buy_price` = '" + buyPrice + "',`cash_price` = '" + cashPrice + "',`Taqset_price` = '" + installmentPrice + "',`selling_percentage` = '" + PercentageSales + "', branchName = '" + itemComboBox.getSelectedItem() + "' WHERE id = '" + text1_1.getText() + "'";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            // } else {
            //    JOptionPane.showMessageDialog(null, "Clear Fields And Try Again");
            // }
            String sql2 = "SELECT * FROM `item`";
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("name");
                id1 = String.valueOf(d.rs.getInt("id"));
                quant1 = String.valueOf(d.rs.getInt("quantity"));
                String[] rowData = {quant1, name1, id1};
                stockTable.addRow(rowData);
            }
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + id + "', '" + stockName + "', '" + LocalDate.now() + "', 'تعديل صنف', 'item');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        delTblCol(stockTable);
        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String id = null;
        try {
            id = text1_1.getText();
            d.st = d.con.createStatement();
            d.st.executeUpdate("DELETE FROM item WHERE item.id = '" + id + "';");
            String sql2 = "SELECT * FROM `item`";
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("name");
                id1 = String.valueOf(d.rs.getInt("id"));
                quant1 = String.valueOf(d.rs.getInt("quantity"));
                String[] rowData = {quant1, name1, id1};
                stockTable.addRow(rowData);
            }
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + id + "', '" + text1_2.getText() + "', '" + LocalDate.now() + "', 'ازالة صنف','item');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        delTblCol(stockTable);
        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String id = null;
        try {
            String sql2 = "SELECT * FROM `item`";
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("name");
                id1 = String.valueOf(d.rs.getInt("id"));
                quant1 = String.valueOf(d.rs.getInt("quantity"));
                String[] rowData = {quant1, name1, id1};
                stockTable.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton68MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton68MouseClicked
        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String mandobId = null, mandobCardName = null, mandobMobile = null, mandobCollectionRate = null, mandobSalesRate = null, PercentageSales = null;
        try {
            if (this.mandobId.getText().isEmpty() == true) {
                mandobId = String.valueOf(auto_insert("mandob", "id"));
            } else {
                mandobId = String.valueOf(Integer.parseInt(this.mandobId.getText()));
            }
            StringBuilder mandobName = new StringBuilder(this.mandobName.getText());
            mandobCardName = this.mandobCardName.getText();
            StringBuilder mandobLocation = new StringBuilder(this.mandobLocation.getText());
            mandobMobile = this.mandobMobile.getText();
            mandobCollectionRate = this.mandobCollectionRate.getText();
            String sql = "INSERT INTO `mandob` (`id`, `name`, `card_num`, `address`, `mob`, `total_recieved_money`, `total_required_money`,`Tahsel_percentage`,areaName,branchName) VALUES ('" + mandobId + "', '" + mandobName + "', '" + mandobCardName + "', '" + mandobLocation + "', '" + mandobMobile + "', '" + 0 + "', '" + 0 + "', '" + mandobCollectionRate + "' , '" + jComboBox5.getSelectedItem() + "' , '" + itemComboBox1.getSelectedItem() + "'  );";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + mandobId + "', '" + mandobName + "', '" + LocalDate.now() + "', 'اضافة مندوب','mandob');");
            this.mandobId.setText(mandobId + "");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
    }//GEN-LAST:event_jButton68MouseClicked

    private void jButton18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MouseClicked

    }//GEN-LAST:event_jButton18MouseClicked

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed

        JFrame frame = new JFrame("Frame");
        person p = new person(1);
        frame.setSize(1250, 700);
        frame.add(p);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void mandobClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobClientsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobClientsActionPerformed

    private void mandobRemainingCollectionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandobRemainingCollectionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobRemainingCollectionsActionPerformed

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked

        String clientId;
        DBcon d = new DBcon();
        try {
            if (this.Cid.getText().isEmpty() == true) {
                clientId = String.valueOf(auto_insert("client", "id"));
            } else {
                clientId = this.Cid.getText();
            }
            StringBuilder clientName = new StringBuilder(this.Cname.getText());
            StringBuilder clientLoc1 = new StringBuilder(this.add1.getText());
            StringBuilder clientLoc2 = new StringBuilder(this.add2.getText());

            d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `client` (`id`, `name`, `card_num`, `address1`, `address2`, `mob1`, `mob2`, `job`, `job_place`, `blocked` , areaName , branchName ) VALUES ('" + clientId + "', '" + clientName + "', '" + cNum.getText() + "', '" + clientLoc1 + "', '" + clientLoc2 + "' , '" + mob1.getText() + "', '" + mob2.getText() + "', '" + Cjob.getText() + "', '" + jobPlace.getText() + "', '0'  , '" + jComboBox6.getSelectedItem() + "' , '" + itemComboBox2.getSelectedItem() + "' );");
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + clientId + "', '" + clientName + "', '" + LocalDate.now() + "', 'اضافة عميل' , 'client');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void mandobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mandobMouseClicked
        delTblCol(clientsTahsel);

        String id = null, name = "unknown", quant = null, price = null;
        int n = mandob.getSelectedRow();
        name = tahsealMan.getValueAt(n, 0).toString();
        id = tahsealMan.getValueAt(n, 1).toString();
        jTextField51.setText(id);
        mand.setText(name);
        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("SELECT DISTINCT client.id , client.name FROM `client`,aqsat WHERE client.id = aqsat.client_id AND mandob_id = '" + id + "' AND aqsat.order = '1'  ;");
            while ((d.rs).next()) {
                name = d.rs.getString("client.name");
                id = String.valueOf(d.rs.getInt("client.id"));

                String[] rowData = {name, id};
                clientsTahsel.addRow(rowData);

            }
//            d.rs = d.st.executeQuery("SELECT * FROM `client`,aqsat WHERE client.id = aqsat.client_id AND mandob_id = '" + id + "' AND aqsat.order = '1'  ;");
//            while ((d.rs).next()) {
//
//                int c = 0;
//                for (int i = 0; i < clientsTahsel.getRowCount(); i++) {
//                    if (id.equals((String) clientsTahsel.getValueAt(i, 2))) {
//                        c++;
//                    }
//                    if (c > 1) {
//                        clientsTahsel.removeRow(i);
//                        c--;
//                    }
//                }
//            }
            d.con.close();
        } catch (Exception e) {
        }
        delTblCol(qestTahsel);
    }//GEN-LAST:event_mandobMouseClicked

    private void clientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientsMouseClicked
        String id = null, name = "unknown";
        int n = clients.getSelectedRow();
        name = clientsTahsel.getValueAt(n, 0).toString();
        id = clientsTahsel.getValueAt(n, 1).toString();

        int m = mandob.getSelectedRow();
        String manid = tahsealMan.getValueAt(m, 1).toString();
        jTextField35.setText(id);
        jTextField36.setText(name);
        DBcon d = new DBcon();
        delTblCol(qestTahsel);
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` , client  WHERE client.id = aqsat.client_id AND client.name = '" + name + "' AND mandob_id = '" + manid + "' ORDER BY CRI;");
            String month, id1, val, order;
            d.rs.first();
            int CRI = d.rs.getInt("CRI");
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` , client  WHERE client.id = aqsat.client_id AND client.name = '" + name + "' AND mandob_id = '" + manid + "' ORDER BY CRI;");

            int temp = CRI;
            while ((d.rs).next()) {
                CRI = d.rs.getInt("CRI");
                if (temp != CRI) {
                    month = String.valueOf(" ");
                    id1 = String.valueOf(" ");
                    val = String.valueOf(" ");
                    order = String.valueOf(" ");
                    String[] rowData = {order, val, month, id1};
                    qestTahsel.addRow(rowData);
                    temp = CRI;
                }
                month = String.valueOf(d.rs.getString("month_payment"));
                id1 = String.valueOf(d.rs.getInt("id"));
                val = String.valueOf(d.rs.getFloat("value"));
                order = String.valueOf(d.rs.getInt("order"));

                String[] rowData = {order, val, month, id1};
                qestTahsel.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_clientsMouseClicked

    private void qestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qestMouseClicked
        String id = null, month = "unknown", val, order;
        int n = qest.getSelectedRow();
        order = qestTahsel.getValueAt(n, 0).toString();
        val = qestTahsel.getValueAt(n, 1).toString();
        month = qestTahsel.getValueAt(n, 2).toString();
        id = qestTahsel.getValueAt(n, 3).toString();
        DBcon d = new DBcon();

        //  "checked" القسط اندفع ولا لا من هنا.............................
        String sql2 = "SELECT * FROM `aqsat` WHERE id = " + id + ";";
        boolean checked = false;
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                checked = d.rs.getBoolean("done");
            }
            if (checked == false) {
                light_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_off.png")));

            } else {
                light_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_on.png")));

            }
            d.con.close();
        } catch (SQLException ex) {

        }
        // "checked" القسط اندفع ولا لا من هنا.............................
        jTextField37.setText(order);
        jTextField38.setText(month);
        jTextField39.setText(val);
        jTextField52.setText(id);

    }//GEN-LAST:event_qestMouseClicked

    private void jTextField51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField51ActionPerformed
        String name1, id1;
        DBcon d = new DBcon();
        try {
            delTblCol(tahsealMan);
            d.rs = d.st.executeQuery("SELECT * FROM `mandob` where id = '" + jTextField51.getText() + "'");
            while ((d.rs).next()) {
                name1 = d.rs.getString("name");
                id1 = String.valueOf(d.rs.getInt("id"));
                String[] rowData = {name1, id1};
                tahsealMan.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jTextField51ActionPerformed

    private void jTextField52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52ActionPerformed

    private void jButton36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton36MouseClicked

        try {
            String id = null;
            int order = 0;

            order = Integer.valueOf(jTextField37.getText());
            float val = Float.valueOf(jTextField39.getText());
            float val2 = 0;
            id = jTextField52.getText();
            DBcon d = new DBcon();
            int CRI = 0;
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` WHERE id = " + id + ";");
            while ((d.rs).next()) {
                CRI = d.rs.getInt("CRI");
            }

            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` WHERE CRI = '" + CRI + "' AND aqsat.order = '" + (order + 1) + "';");
            while ((d.rs).next()) {
                val2 = d.rs.getInt("value");
            }

            d.con.createStatement();
            d.st.executeUpdate("UPDATE `aqsat` SET `value` = '" + (val + val2) + "' WHERE `aqsat`.`CRI` = '" + CRI + "' AND aqsat.order = '" + (order + 1) + "';");
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `aqsat` SET `value` = '" + 0 + "' WHERE `aqsat`.`CRI` = '" + CRI + "' AND aqsat.order = '" + order + "';");
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        String id = null, name = "unknown";
        int n = clients.getSelectedRow();
        name = clientsTahsel.getValueAt(n, 0).toString();
        id = clientsTahsel.getValueAt(n, 1).toString();

        int m = mandob.getSelectedRow();
        String manid = tahsealMan.getValueAt(m, 1).toString();
        jTextField35.setText(id);
        jTextField36.setText(name);
        DBcon d = new DBcon();
        delTblCol(qestTahsel);
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` , client  WHERE client.id = aqsat.client_id AND client.name = '" + name + "' AND mandob_id = '" + manid + "' ORDER BY CRI;");
            String month, id1, val, order;

            while ((d.rs).next()) {
                month = String.valueOf(d.rs.getString("month_payment"));
                id1 = String.valueOf(d.rs.getInt("id"));
                val = String.valueOf(d.rs.getFloat("value"));
                order = String.valueOf(d.rs.getInt("order"));
                String[] rowData = {order, val, month, id1};
                qestTahsel.addRow(rowData);
            }

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + id + "', '" + name + "', '" + LocalDate.now() + "',  'ترحيل شهر','client');");
            d.con.close();
        } catch (Exception e) {
        }
        jTextField52.setText(null);
        jTextField37.setText(null);
        jTextField38.setText(null);
        jTextField39.setText(null);
    }//GEN-LAST:event_jButton36MouseClicked

    private void jButton43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton43MouseClicked

        try {

            String id = null;
            int order = 1;
            int Lorder = 0;
            int val2 = 0;
            float val = Float.valueOf(jTextField39.getText());
            String dat = jTextField38.getText();
            String RD = null;
            id = jTextField52.getText();
            int CRI = 0;
            DBcon d = new DBcon();
            DBcon db = new DBcon();
            int Mcounter = 0;
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` WHERE id = '" + id + "';");
            while ((d.rs).next()) {
                CRI = d.rs.getInt("CRI");
                RD = String.valueOf(d.rs.getDate("received_date"));
            }
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` WHERE CRI ='" + CRI + "';");
            d.rs.last();
            Mcounter = d.rs.getInt("month_payment") + 1;
            order = d.rs.getInt("order") + 1;
            Lorder = order;
            d.st.executeUpdate("INSERT INTO `aqsat` (`id`, `mandob_id`, `client_id`, `month_payment`, `value`, `tahsel`, `received_date`,`order`, `CRI`) VALUES (NULL,'" + jTextField51.getText() + "','" + jTextField35.getText() + "', '" + Mcounter + "', '" + 0 + "', '0', '" + RD + "', '" + order + "', '" + CRI + "');");

            order = Integer.valueOf(jTextField37.getText());
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` WHERE CRI ='" + CRI + "' ;");
            float hand = 0;
            val = 0;
            while ((d.rs).next()) {
                db.rs = db.st.executeQuery("SELECT * FROM `aqsat` WHERE CRI ='" + CRI + "' AND aqsat.order = '" + order + "';");
                while ((db.rs).next()) {
                    if (order == Lorder) {
                        break;
                    }
                    hand = db.rs.getInt("value");
                }
                db.con.createStatement();
                db.st.executeUpdate("UPDATE `aqsat` SET `value` = '" + val + "' WHERE `aqsat`.`CRI` = '" + CRI + "' AND aqsat.order = '" + (order) + "';");
                order++;
                val = hand;
            }
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        // delTblCol(qestTahsel);
        //  delTblCol(clientsTahsel);
        // showTahsel();
        String id = null, name = "unknown";
        int n = clients.getSelectedRow();
        name = clientsTahsel.getValueAt(n, 0).toString();
        id = clientsTahsel.getValueAt(n, 1).toString();
        int m = mandob.getSelectedRow();
        String manid = tahsealMan.getValueAt(m, 1).toString();
        jTextField35.setText(id);
        jTextField36.setText(name);
        DBcon d = new DBcon();
        delTblCol(qestTahsel);
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat` , client  WHERE client.id = aqsat.client_id AND client.name = '" + name + "' AND mandob_id = '" + manid + "' ORDER BY CRI;");
            String month, id1, val, order;

            while ((d.rs).next()) {
                month = String.valueOf(d.rs.getString("month_payment"));
                id1 = String.valueOf(d.rs.getInt("id"));
                val = String.valueOf(d.rs.getFloat("value"));
                order = String.valueOf(d.rs.getInt("order"));
                String[] rowData = {order, val, month, id1};
                qestTahsel.addRow(rowData);
            }
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + id + "', '" + name + "', '" + LocalDate.now() + "',  'اضافة شهر','client');");
            d.con.close();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jButton43MouseClicked

    private void qestMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qestMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_qestMouseEntered

    private void jTextField35CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField35CaretUpdate

    }//GEN-LAST:event_jTextField35CaretUpdate

    private void jTextField35FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField35FocusGained

        delTblCol(qestTahsel);
        DBcon d = new DBcon();
        String month, id1, val, order;
        int clientId = Integer.valueOf(jTextField35.getText());
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `aqsat`,client_recieved_items,client WHERE aqsat.CRI = client_recieved_items.id AND client_recieved_items.clientId = client.id AND client.id = '" + clientId + "' ;");
            while ((d.rs).next()) {
                month = String.valueOf(d.rs.getDate("month_payment"));
                id1 = String.valueOf(d.rs.getInt("id"));
                val = String.valueOf(d.rs.getFloat("value"));
                order = String.valueOf(d.rs.getInt("order"));
                String[] rowData = {order, val, month, id1};
                qestTahsel.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }

    }//GEN-LAST:event_jTextField35FocusGained

    private void jButton32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton32MouseClicked
        delTblCol(tahsealMan);
        delTblCol(clientsTahsel);
        delTblCol(qestTahsel);
        tahselTables();
    }//GEN-LAST:event_jButton32MouseClicked

    private void itemIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemIdActionPerformed

    private void jButton39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton39MouseClicked
        // TODO add your handling code here:
        DBcon d = new DBcon();
        DBcon db = new DBcon();
        try {
            int qestId = -2;
            int id = -1;
            String name;
            int manId = 0, itemQuant;
            d.rs = d.st.executeQuery("SELECT * FROM client_recieved_items , item , client , mandob WHERE client.id = client_recieved_items.clientId AND client_recieved_items.item_id = item.id AND client_recieved_items.mandob_id = mandob.id AND mandob.id = '" + jTextField31.getText() + "' AND client.id = '" + jTextField50.getText() + "'  AND item.id = '" + itemId.getText() + "';");
            d.rs.last();
            float totalPayment = d.rs.getFloat("item.selling_percentage") * d.rs.getInt("client_recieved_items.quantity");
            id = d.rs.getInt("client_recieved_items.id");
            int quant = d.rs.getInt("client_recieved_items.quantity");
            d.rs = d.st.executeQuery("SELECT * FROM aqsat WHERE CRI = '" + id + "' AND aqsat.order = 1 ;");
            d.rs.last();
            qestId = d.rs.getInt("id");
            d.con.createStatement();
            d.st.executeUpdate("DELETE FROM tahsel WHERE qest_id = '" + qestId + "';");
            d.con.createStatement();

            d.st.executeUpdate("DELETE FROM aqsat WHERE CRI = '" + id + "';");
            d.con.createStatement();
            if (!(jTextField32.getText().equals("متعدد"))) {
                d.rs = d.st.executeQuery("select * from mandob where id = '" + jTextField31.getText() + "';");
                d.rs.last();
                float tot = d.rs.getFloat("total_recieved_money");
                d.st = d.con.createStatement();
                d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + (tot - totalPayment) + "' WHERE id = '" + jTextField31.getText() + "' ;");

                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `mandobrecivables` (`id`, `mandobId`, `value`, `type`, `clientId`, `date`) VALUES (NULL, '" + jTextField31.getText() + "', '" + totalPayment + "', 'ارجاع', '" + jTextField50.getText() + "', '" + LocalDate.now() + "');");

                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + id + "', '" + jTextField32.getText() + "', '" + LocalDate.now() + "', '" + totalPayment + " خصم من المندوب ','mandob');");

                d.st = d.con.createStatement();

            } else {
                int counter = 0;
                d.rs = d.st.executeQuery("SELECT * FROM client_recieved_items , multiman WHERE   client_recieved_items.id = multiman.CRI AND multiman.CRI = '" + id + "' ");
                while (d.rs.next()) {
                    counter++;
                }
                d.rs = d.st.executeQuery("SELECT * FROM client_recieved_items , multiman WHERE   client_recieved_items.id = multiman.CRI AND CRI = '" + id + "' ");
                while (d.rs.next()) {
                    manId = d.rs.getInt("multiman.mandob_id");
                    db.rs = db.st.executeQuery("select * from mandob where id = '" + manId + "';");
                    db.rs.last();
                    float tot = db.rs.getFloat("total_recieved_money");
                    name = db.rs.getString("mandob.name");
                    db.st = db.con.createStatement();
                    db.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + (tot - totalPayment / counter) + "' WHERE id = '" + manId + "';");

                    d.st = d.con.createStatement();
                    d.st.executeUpdate("INSERT INTO `mandobrecivables` (`id`, `mandobId`, `value`, `type`, `clientId`, `date`) VALUES (NULL, '" + manId + "', '" + (totalPayment / counter) + "', 'ارجاع', '" + jTextField50.getText() + "', '" + LocalDate.now() + "');");

                    db.st = db.con.createStatement();
                    db.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + manId + "', '" + name + "', '" + LocalDate.now() + "', '" + totalPayment + "خصم من المندوب ','mandob');");
                }
                d.st.executeUpdate("DELETE FROM multiman WHERE CRI = '" + id + "';");
                d.con.createStatement();
            }
            d.st.executeUpdate("DELETE FROM client_recieved_items WHERE id = '" + id + "';");
            d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `return_items` (`id`, `mandob_id`, `item_id`, `client_id` , branchName) VALUES (NULL, '" + jTextField31.getText() + "', '" + jTextField50.getText() + "', '" + itemId.getText() + "' , '" + itemComboBox5.getSelectedItem() + "');");
            d.rs = d.st.executeQuery("select * from item where item.id = '" + itemId.getText() + "' and branchName = '" + itemComboBox5.getSelectedItem() + "';");
            d.rs.last();
            int oldq = d.rs.getInt("quantity");
            int newq = oldq + quant;
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE item set quantity = '" + newq + "' WHERE item.id = '" + itemId.getText() + "';");

            d.st.executeUpdate("INSERT INTO `notes` (`id`, `title`, `note`, `date`, `branchName`,type,person_id) VALUES (NULL, 'ارجاع', 'تم ارجاع الصنف " + jTextField29.getText() + "', '" + LocalDate.now() + "', '" + itemComboBox5.getSelectedItem() + "' ,'client','" + jTextField50.getText() + "');");
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField50.getText() + "', '" + jTextField47.getText() + "', '" + LocalDate.now() + "',  'ارجاع','client');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
    }//GEN-LAST:event_jButton39MouseClicked

    private void jButton37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton37MouseClicked
        delTblCol(returnT);
        DBcon d = new DBcon();
        String item, id, client, mandob;
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `return_items`,client,mandob,item WHERE return_items.client_id = client.id AND return_items.mandob_id = mandob.id AND return_items.item_id = item.id ");
            while ((d.rs).next()) {
                item = String.valueOf(d.rs.getString("item.name"));
                id = String.valueOf(d.rs.getInt("return_items.id"));
                client = String.valueOf(d.rs.getString("client.name"));
                mandob = String.valueOf(d.rs.getString("mandob.name"));
                String[] rowData = {item, client, mandob, id};
                returnT.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
    }//GEN-LAST:event_jButton37MouseClicked

    private void itemIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemIdKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            long stock_id = Long.valueOf(this.itemId.getText());
            String itemName = null;
            DBcon d = new DBcon();
            String sql1 = "SELECT * FROM `item`WHERE  item.id='" + stock_id + "';";
            try {
                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                itemName = d.rs.getString("item.name");
                d.con.close();
            } catch (SQLException ex) {

            }
            this.jTextField29.setText(itemName);
        }

    }//GEN-LAST:event_itemIdKeyPressed

    private void jTextField31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField31KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int mandobId = Integer.valueOf(this.jTextField31.getText());
            String itemName = null;
            DBcon d = new DBcon();
            String sql1 = "SELECT * FROM `mandob`WHERE  mandob.id='" + mandobId + "';";
            try {
                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                itemName = d.rs.getString("mandob.name");
                d.con.close();
            } catch (SQLException ex) {

            }
            this.jTextField32.setText(itemName);
        }

    }//GEN-LAST:event_jTextField31KeyPressed

    private void jTextField50KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField50KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int client = Integer.valueOf(this.jTextField50.getText());
            String itemName = null;
            DBcon d = new DBcon();
            String sql1 = "SELECT * FROM `client`WHERE  client.id='" + client + "';";
            try {
                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                itemName = d.rs.getString("client.name");
                d.con.close();
            } catch (SQLException ex) {

            }
            this.jTextField47.setText(itemName);
        }

    }//GEN-LAST:event_jTextField50KeyPressed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        int row = mandob.getRowCount();
        DBcon d = new DBcon();
        try {
            for (int i = 0; i < row; i++) {
                String id = String.valueOf(mandob.getValueAt(i, 1));
                d.con.createStatement();
                d.st.executeUpdate("update aqsat SET `tahsel` = '1', `done` = '1' where mandob_id = '" + id + "' AND month_payment = '" + LocalDate.now().getMonthValue() + "';");
                d.con.createStatement();
                d.st.executeUpdate("UPDATE tahsel SET  tahsel.done = '1' WHERE mandob_id = '" + id + "'");
            }
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + getUserId() + "', 'user', '" + LocalDate.now() + "',  'تحصيل الكل','client');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton44MouseClicked
        // TODO add your handling code here:
        String noteId;
        if (this.jTextField53.getText().isEmpty() == true) {
            noteId = String.valueOf(auto_insert("area", "id"));
        } else {
            noteId = String.valueOf(Integer.parseInt(this.jTextField53.getText()));
        }

        String sql = null;
        String type = jComboBox7.getSelectedItem().toString();
        if (jComboBox7.getSelectedItem().equals("عميل")) {
            sql = "INSERT INTO `notes` (`id`, `title`, `note`, `date` ,`branchName`,type, person_id ) VALUES ('" + noteId + "', '" + jTextField41.getText() + "', '" + jTextArea1.getText() + "', '" + this.date.getText() + "', '" + this.itemComboBox3.getSelectedItem() + "' , 'client' ,0 );";
        } else if (jComboBox7.getSelectedItem().equals("مندوب")) {
            sql = "INSERT INTO `notes` (`id`, `title`, `note`, `date` ,`branchName`,type, person_id ) VALUES ('" + noteId + "', '" + jTextField41.getText() + "', '" + jTextArea1.getText() + "', '" + this.date.getText() + "', '" + this.itemComboBox3.getSelectedItem() + "' , 'mandob' ,0 );";
        } else if (jComboBox7.getSelectedItem().equals("اخرى")) {
            sql = "INSERT INTO `notes` (`id`, `title`, `note`, `date` ,`branchName`,type , person_id) VALUES ('" + noteId + "', '" + jTextField41.getText() + "', '" + jTextArea1.getText() + "', '" + this.date.getText() + "', '" + this.itemComboBox3.getSelectedItem() + "' , 'other',0);";
        } else {
            sql = "INSERT INTO `notes` (`id`, `title`, `note`, `date` ,`branchName`,type , person_id) VALUES ('" + noteId + "', '" + jTextField41.getText() + "', '" + jTextArea1.getText() + "', '" + this.date.getText() + "', '" + this.itemComboBox3.getSelectedItem() + "' , 'all',0);";
        }
        DBcon d = new DBcon();
        try {
            d.con.createStatement();
            d.st.executeUpdate(sql);
            delTblCol(notesT);
            notes();
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField53.getText() + "', 'ملحوظة', '" + LocalDate.now() + "',  ' اضافة ملحوظة '," + jComboBox7.getSelectedItem() + ");");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jButton44MouseClicked

    private void notesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notesTableMouseClicked
        // TODO add your handling code here:
        String date, id, title, note;

        int n = notesTable.getSelectedRow();
        id = notesTable.getValueAt(n, 2).toString();

        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `notes` WHERE id = '" + id + "';";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                jTextField41.setText(d.rs.getString("title"));
                jTextArea1.setText(d.rs.getString("note"));
                this.date.setText(String.valueOf(d.rs.getDate("date")));
                itemComboBox3.setSelectedItem(String.valueOf(d.rs.getString("branchName")));
                String type = String.valueOf(d.rs.getString("type"));

                if (type.equals("mandob")) {
                    jComboBox7.setSelectedItem("مندوب");
                } else if (type.equals("client")) {
                    jComboBox7.setSelectedItem("عميل");
                } else {
                    jComboBox7.setSelectedItem("اخرى");
                }

            }
            jTextField53.setText(id);
            delTblCol(notesT);
            notes();
            d.con.close();
        } catch (SQLException ex) {

        }

    }//GEN-LAST:event_notesTableMouseClicked

    private void jButton49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton49MouseClicked
        DBcon d = new DBcon();
        try {
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `notes` SET `title` = '" + jTextField41.getText() + "', `note` = '" + jTextArea1.getText() + "', `date` = '" + date.getText() + "' WHERE `notes`.`id` = " + jTextField53.getText() + " ;");
            delTblCol(notesT);
            notes();

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField53.getText() + "', 'ملحوظة', '" + LocalDate.now() + "',  ' تعديل ملحوظة '," + jComboBox7.getSelectedItem() + ");");
            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jButton49MouseClicked

    private void jTextField53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53ActionPerformed

    private void jButton50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton50MouseClicked

        DBcon d = new DBcon();
        try {
            d.con.createStatement();
            d.st.executeUpdate("DELETE FROM `notes` WHERE id = '" + jTextField53.getText() + "'");
            delTblCol(notesT);
            notes();
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField53.getText() + "', 'ملحوظة', '" + LocalDate.now() + "',  ' حذف ملحوظة '," + jComboBox7.getSelectedItem() + ");");

            d.con.close();
        } catch (Exception e) {
        } // TODO add your handling code here:
    }//GEN-LAST:event_jButton50MouseClicked

    private void jButton55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton55MouseClicked
        delTblCol(notesT);
        notes();
    }//GEN-LAST:event_jButton55MouseClicked

    private void jButton19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton19MouseClicked
        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String mandobName = null, mandobId = null, mandobCardName = null, mandobLocation = null, mandobMobile = null, mandobCollectionRate = null, mandobSalesRate = null, PercentageSales = null;
        try {

            mandobId = this.mandobId.getText();
            mandobName = this.mandobName.getText();
            mandobCardName = this.mandobCardName.getText();
            mandobLocation = this.mandobLocation.getText();
            mandobMobile = this.mandobMobile.getText();
            mandobCollectionRate = this.mandobCollectionRate.getText();

            String sql = "Update `mandob` set `name` = '" + mandobName + "', `card_num` = '" + mandobCardName + "', `address`='" + mandobLocation + "', `mob` = '" + mandobMobile + "',`Tahsel_percentage` =  '" + mandobCollectionRate + "' WHERE id = '" + mandobId + "';";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + mandobId + "', '" + mandobName + "', '" + LocalDate.now() + "', 'تعديل مندوب' , 'mandob');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jButton19MouseClicked

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton66ActionPerformed

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked

        DBcon d = new DBcon();
        try {
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `client` SET `name`= '" + Cname.getText() + "',`card_num` = '" + cNum.getText() + "', `address1` =  '" + add1.getText() + "', `address2`= '" + add2.getText() + "', `mob1` = '" + mob1.getText() + "', `mob2` = '" + mob2.getText() + "', `job` = '" + Cjob.getText() + "', `job_place` = '" + jobPlace.getText() + "' WHERE id = '" + Cid.getText() + "';");
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation` , type) VALUES (NULL, '" + Cid.getText() + "', '" + Cname.getText() + "', '" + LocalDate.now() + "', 'تعديل عميل'  , 'client');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:

        //if (jLabel71.getText().equals("تفعيل العميل")) {
        DBcon d = new DBcon();
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `blocked_client` (`id`, `client_id`, `branchName`) VALUES (NULL, '" + Cid.getText() + "','" + itemComboBox2.getSelectedItem() + "');");
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `client` SET `blocked`= '1' WHERE id = '" + Cid.getText() + "';");
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation` , type) VALUES (NULL, '" + Cid.getText() + "', '" + Cname.getText() + "', '" + LocalDate.now() + "', 'ايقاف عميل' , 'client');");
            //jLabel71.setText("ايقاف العميل");

            d.con.close();
        } catch (Exception e) {

        }
//        } else {
//            DBcon d = new DBcon();
//            try {
//                d.st = d.con.createStatement();
//                d.st.executeUpdate("DELETE FROM `blocked_client` WHERE `client_id` '" + Cid.getText() + "';");
//                d.con.createStatement();
//                d.st.executeUpdate("UPDATE `client` SET `blocked`= '0' WHERE id = '" + Cid.getText() + "';");
//                d.st = d.con.createStatement();
//                d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation` , type) VALUES (NULL, '" + Cid.getText() + "', '" + Cname.getText() + "', '" + LocalDate.now() + "', 'تفعيل عميل' , 'client');");
//            } catch (Exception e) {
//
//            }
//            jLabel71.setText("تفعيل العميل");
//
//        }

    }//GEN-LAST:event_jLabel11MouseClicked

    private void reportTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportTypeActionPerformed
        // TODO add your handling code here:
        delTblCol(reportT);

        if ((String) reportType1.getSelectedItem() != "الشهر") {
            String month = (String) reportType1.getSelectedItem();
            if (reportType.getSelectedItem().equals("عرض التقارير")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("المندوب")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'mandob' AND date BETWEEN '2019-" + month + "-1' AND '2019-" + month + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("العملاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'client' AND date BETWEEN '2019-" + month + "-1' AND '2019-" + month + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الاصناف")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'item' AND date BETWEEN '2019-" + month + "-1' AND '2019-" + month + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الشركاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'partners' AND date BETWEEN '2019-" + month + "-1' AND '2019-" + month + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                } catch (Exception e) {

                }
            }
        } else {
            delTblCol(reportT);
            if (reportType.getSelectedItem().equals("عرض التقارير")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("المندوب")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'mandob'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("العملاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'client'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الاصناف")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'item'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الشركاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'partners'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            }
        }

    }//GEN-LAST:event_reportTypeActionPerformed

    private void jButton57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton57MouseClicked
        try {

            delTblCol(userTable);
            String name1 = null, id1 = null, type = null, type1;
            String password = this.password.getText().toString();
            String name = userName.getText().toString();
            DBcon d = new DBcon();
            int[] userPanels = new int[10];
            userPanels[0] = java.lang.Boolean.valueOf(user1.isSelected()).compareTo(false);
            userPanels[1] = java.lang.Boolean.valueOf(user2.isSelected()).compareTo(false);
            userPanels[2] = java.lang.Boolean.valueOf(user3.isSelected()).compareTo(false);
            userPanels[3] = java.lang.Boolean.valueOf(user4.isSelected()).compareTo(false);
            userPanels[4] = java.lang.Boolean.valueOf(user5.isSelected()).compareTo(false);
            userPanels[5] = java.lang.Boolean.valueOf(user6.isSelected()).compareTo(false);
            userPanels[6] = java.lang.Boolean.valueOf(user7.isSelected()).compareTo(false);
            userPanels[7] = java.lang.Boolean.valueOf(user8.isSelected()).compareTo(false);
            userPanels[8] = java.lang.Boolean.valueOf(user9.isSelected()).compareTo(false);
            userPanels[9] = java.lang.Boolean.valueOf(user10.isSelected()).compareTo(false);
            userPanels[10] = java.lang.Boolean.valueOf(user11.isSelected()).compareTo(false);
            userPanels[11] = java.lang.Boolean.valueOf(user12.isSelected()).compareTo(false);
            String sql = "UPDATE `users` SET `Password` = '" + password + "',`p1` = '" + userPanels[0] + "', `p2` = '" + userPanels[1] + "', `p3` = '" + userPanels[2] + "', `p4` = '" + userPanels[3] + "', `p5` = '" + userPanels[4] + "', `p6` = '" + userPanels[5] + "', `p7` = '" + userPanels[6] + "', `p8` = '" + userPanels[7] + "', `p9` = '" + userPanels[8] + "', `p10` = '" + userPanels[9] + "', `p11` = '" + userPanels[10] + "', `p12` = '" + userPanels[11] + "' WHERE `name` = '" + name + "';";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + getUserId() + "', '" + getUser() + "', '" + LocalDate.now() + "',  ' تعديل مستخدم ','فرع');");

            viewUsers();
            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jButton57MouseClicked

    private void user5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_user5ActionPerformed

    private void user7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_user7ActionPerformed

    private void jButton56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton56MouseClicked
        delTblCol(userTable);
        //add user

        DBcon d = new DBcon();
        int[] userPanels = new int[12];
        String name1 = "unknown", id1 = null, type = null;
        String password = this.password.getText();
        String name = userName.getText();
        int branchId = 0;
        if (!name.equals("Hash")) {
            int id = auto_insert("users", "id");
            userPanels[0] = java.lang.Boolean.valueOf(user1.isSelected()).compareTo(false);
            userPanels[1] = java.lang.Boolean.valueOf(user2.isSelected()).compareTo(false);
            userPanels[2] = java.lang.Boolean.valueOf(user3.isSelected()).compareTo(false);
            userPanels[3] = java.lang.Boolean.valueOf(user4.isSelected()).compareTo(false);
            userPanels[4] = java.lang.Boolean.valueOf(user5.isSelected()).compareTo(false);
            userPanels[5] = java.lang.Boolean.valueOf(user6.isSelected()).compareTo(false);
            userPanels[6] = java.lang.Boolean.valueOf(user7.isSelected()).compareTo(false);
            userPanels[7] = java.lang.Boolean.valueOf(user8.isSelected()).compareTo(false);
            userPanels[8] = java.lang.Boolean.valueOf(user9.isSelected()).compareTo(false);
            userPanels[9] = java.lang.Boolean.valueOf(user10.isSelected()).compareTo(false);
            userPanels[10] = java.lang.Boolean.valueOf(user11.isSelected()).compareTo(false);
            userPanels[11] = java.lang.Boolean.valueOf(user12.isSelected()).compareTo(false);
            try {
                String sql = "INSERT INTO `users` (`ID`, `name`,`Password`,`logged`,branchName, `p1`, `p2`, `p3`, `p4`, `p5`, `p6`, `p7`, `p8`, `p9`, `p10`, `p11`, `p12` ) VALUES ('" + id + "', '" + name + "', '" + password + "','0','" + this.branch.getSelectedItem() + "', '" + userPanels[0] + "', '" + userPanels[1] + "', '" + userPanels[2] + "', '" + userPanels[3] + "', '" + userPanels[4] + "', '" + userPanels[5] + "', '" + userPanels[6] + "', '" + userPanels[7] + "', '" + userPanels[8] + "', '" + userPanels[9] + "' , '" + userPanels[10] + "', '" + userPanels[11] + "');";
                d.st = d.con.createStatement();
                d.st.executeUpdate(sql);
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + getUserId() + "', '" + getUser() + "', '" + LocalDate.now() + "',  ' اضافة مستخدم ','فرع');");

                d.con.close();
                JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
            }
            viewUsers();
        } else {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>جرب اسما اخر </h3></html>");
        }
    }//GEN-LAST:event_jButton56MouseClicked

    private void jButton60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton60MouseClicked
        // TODO add your handling code here:
        delTblCol(userTable);
        viewUsers();

    }//GEN-LAST:event_jButton60MouseClicked

    private void userTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTMouseClicked

        String pass = null, name = null, id = null;
        int n = userT.getSelectedRow();

        pass = userT.getValueAt(n, 0).toString();
        name = userT.getValueAt(n, 1).toString();
        id = userT.getValueAt(n, 2).toString();
        String branch = null;

        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("select * from users where users.name = '" + name + "' AND users.password = '" + pass + "' and id = '" + id + "';");
            d.rs.last();
            user1.setSelected(d.rs.getBoolean("p1"));
            user2.setSelected(d.rs.getBoolean("p2"));
            user3.setSelected(d.rs.getBoolean("p3"));
            user4.setSelected(d.rs.getBoolean("p4"));
            user5.setSelected(d.rs.getBoolean("p5"));
            user6.setSelected(d.rs.getBoolean("p6"));
            user7.setSelected(d.rs.getBoolean("p7"));
            user8.setSelected(d.rs.getBoolean("p8"));
            user9.setSelected(d.rs.getBoolean("p9"));
            user10.setSelected(d.rs.getBoolean("p10"));
            user11.setSelected(d.rs.getBoolean("p11"));
            user12.setSelected(d.rs.getBoolean("p12"));
            password.setText(pass);
            userName.setText(name);
            this.branch.setSelectedItem(d.rs.getString("branchName"));
            d.con.close();
        } catch (Exception e) {

        }

    }//GEN-LAST:event_userTMouseClicked

    private void jButton58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton58MouseClicked
        // TODO add your handling code here:
        try {
            delTblCol(userTable);

            DBcon d = new DBcon();
            String sql = "DELETE FROM `users` WHERE `name` = '" + userName.getText() + "';";

            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);

            viewUsers();
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + getUserId() + "', '" + getUser() + "', '" + LocalDate.now() + "',  ' ازالة مستخدم ','فرع');");

            //dm.getDataVector();
            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

    }//GEN-LAST:event_jButton58MouseClicked

    private void mandobIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mandobIdFocusGained

    }//GEN-LAST:event_mandobIdFocusGained

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed

        JFrame frame = new JFrame("Frame");
        person p = new person(2);
        frame.setSize(1250, 800);
        frame.add(p);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton69ActionPerformed

    private void stock_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stock_tableMouseClicked
        String id = null, name = "unknown", quant = null, price = null;
        int n = stock_table.getSelectedRow();
        name = stockTable.getValueAt(n, 1).toString();
        id = stockTable.getValueAt(n, 2).toString();
        quant = stockTable.getValueAt(n, 0).toString();
        String alarm = null, buyPrice = null, cashPrice = null, salesPresentage = null, taksetprice = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `item` WHERE id = " + id + ";";
        try {
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                buyPrice = String.valueOf(d.rs.getFloat("buy_price"));
                alarm = String.valueOf(d.rs.getInt("cons_rate"));
                cashPrice = String.valueOf(d.rs.getFloat("cash_price"));
                taksetprice = String.valueOf(d.rs.getFloat("Taqset_price"));
                salesPresentage = String.valueOf(d.rs.getFloat("selling_percentage"));
                itemComboBox.setSelectedItem(String.valueOf(d.rs.getString("branchName")));
            }
            d.con.close();
        } catch (SQLException ex) {

        }
        text1_1.setText(id);
        text1_2.setText(name);
        text1_3.setText(quant);
        text1_4.setText(alarm);
        text1_5.setText(buyPrice);
        text1_6.setText(cashPrice);
        text1_7.setText(taksetprice);
        text1_8.setText(salesPresentage);

    }//GEN-LAST:event_stock_tableMouseClicked

    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked

        DBcon d = new DBcon();
        String areaId;
        if (this.jTextField13.getText().isEmpty() == true) {
            areaId = String.valueOf(auto_insert("area", "id"));
        } else {
            areaId = String.valueOf(Integer.parseInt(this.jTextField13.getText()));
        }

        String sql = "INSERT INTO `area` (id,`area_name`,branchName) VALUES ('" + areaId + "','" + jTextField25.getText() + "' , '" + String.valueOf(itemComboBox4.getSelectedItem()) + "');";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField13.getText() + "', '" + jTextField25.getText() + "', '" + LocalDate.now() + "',  ' اضافة منطقة '," + areaId + ");");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        jTextField13.setText(areaId);
        delTblCol(areaTable);
        areaTable();
        combobox();
    }//GEN-LAST:event_jButton14MouseClicked

    private void jButton29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton29MouseClicked
        jTextField13.setText(null);
        jTextField25.setText(null);
    }//GEN-LAST:event_jButton29MouseClicked

    private void jButton17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MouseClicked

        DBcon d = new DBcon();
        String sql = "DELETE FROM `area` WHERE id = '" + jTextField13.getText() + "';";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField13.getText() + "', '" + jTextField25.getText() + "', '" + LocalDate.now() + "',  ' ازالة منطقة '," + jTextField13.getText() + ");");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        delTblCol(areaTable);
        areaTable();
        combobox();
    }//GEN-LAST:event_jButton17MouseClicked

    private void areaTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaTMouseClicked
        String id = null, name = "unknown", quant = null, price = null;
        int n = areaT.getSelectedRow();
        id = areaTable.getValueAt(n, 1).toString();
        name = areaTable.getValueAt(n, 0).toString();
        jTextField13.setText(id);
        jTextField25.setText(name);

    }//GEN-LAST:event_areaTMouseClicked

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        delTblCol(areaTable);
        areaTable();
    }//GEN-LAST:event_jButton13MouseClicked

    private void itemComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComboBoxActionPerformed

//          delTblCol(stockTable);
//        DBcon d = new DBcon();
//        String name1 = null, id1 = null, quant1 = null;
//        String id = null;
//        try {
//            String sql2 = "SELECT * FROM `item` WHERE branchName = '"+itemComboBox.getSelectedItem()+"'";
//            d.rs = d.st.executeQuery(sql2);
//            while ((d.rs).next()) {
//                name1 = d.rs.getString("name");
//                id1 = String.valueOf(d.rs.getInt("id"));
//                quant1 = String.valueOf(d.rs.getInt("quantity"));
//                String[] rowData = {quant1, name1, id1};
//                stockTable.addRow(rowData);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري");
//        }
    }//GEN-LAST:event_itemComboBoxActionPerformed

    private void itemComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComboBox1ActionPerformed
        // TODO add your handling code here:
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `area` WHERE branchName = '" + itemComboBox1.getSelectedItem() + "'";
        jComboBox5.removeAllItems();
        jComboBox5.addItem("اختر المنطقة");

        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                jComboBox5.addItem(d.rs.getString("area_name"));
            }
            d.con.close();
        } catch (Exception e) {

        }

    }//GEN-LAST:event_itemComboBox1ActionPerformed

    private void itemComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComboBox2ActionPerformed

        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM `area` WHERE branchName = '" + itemComboBox2.getSelectedItem() + "'";
        jComboBox6.removeAllItems();
        jComboBox6.addItem("اختر المنطقة");

        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                jComboBox6.addItem(d.rs.getString("area_name"));
            }
            d.con.close();
        } catch (Exception e) {

        }

    }//GEN-LAST:event_itemComboBox2ActionPerformed

    private void jTextField40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40ActionPerformed

    private void recivedMonyTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recivedMonyTMouseClicked

        String id = null, name = "unknown", quant = null, price = null;
        int n = recivedMonyT.getSelectedRow();
        name = recivedMonyT.getValueAt(n, 2).toString();
        id = recivedMonyT.getValueAt(n, 4).toString();
        price = recivedMonyT.getValueAt(n, 1).toString();;

        this.jTextField40.setText(id);
        this.jTextField34.setText(name);
        jTextField33.setText(price);

    }//GEN-LAST:event_recivedMonyTMouseClicked

    private void jButton47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton47MouseClicked
        DBcon d = new DBcon();
        try {

            float presentage = 0;
            d.rs = d.st.executeQuery("select * from received_money where id = '" + jTextField40.getText() + "';");
            d.rs.last();
            String manId = String.valueOf(d.rs.getInt("mandob_id"));
            float val = d.rs.getFloat("value");
            d.rs = d.st.executeQuery("select * from mandob where id = '" + manId + "';");
            d.rs.last();
            float totalRicivedMony = d.rs.getFloat("total_recieved_money");
            float newRecived = -Float.valueOf(jTextField33.getText()) + totalRicivedMony;
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + (totalRicivedMony + val) + "' WHERE id = '" + manId + "';");

            d.con.createStatement();
            d.st.executeUpdate("UPDATE `received_money`  set `title` = '" + jTextField34.getText() + "',`value` = '" + jTextField33.getText() + "' WHERE id = '" + jTextField40.getText() + "';");

            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + newRecived + "' WHERE id = '" + manId + "';");

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + manId + "', 'مندوب', '" + LocalDate.now() + "',  '" + jTextField33.getText() + "استلام من المندوب مبلغ قدره (تعديل)  ' , 'mandob');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        mandobReceivedItemsTable();

    }//GEN-LAST:event_jButton47MouseClicked

    private void itemComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemComboBox3ActionPerformed

    private void jButton45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton45MouseClicked
        mandobReceivedItemsTable();
    }//GEN-LAST:event_jButton45MouseClicked

    private void jTextField45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField45ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField45ActionPerformed

    private void jTextField44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField44ActionPerformed
        DBcon d = new DBcon();
        try {

            d.rs = d.st.executeQuery("select * from client WHERE id = '" + jTextField44.getText() + "';");
            while (d.rs.next()) {

                jTextField45.setText(d.rs.getString("name"));

            }
            d.con.close();

        } catch (Exception e) {

        }

    }//GEN-LAST:event_jTextField44ActionPerformed

    private void jButton61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton61MouseClicked
        DBcon d = new DBcon();
        String sql = "INSERT INTO `blocked_client` (`id`, `client_id`, `branchName`) VALUES (NULL, '" + jTextField44.getText() + "', '" + branch1.getSelectedItem() + "');";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `client` SET `blocked`= '1' WHERE id = '" + jTextField44.getText() + "';");

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField44.getText() + "', '" + jTextField45.getText() + "', '" + LocalDate.now() + "',  ' حظر عميل ','عميل');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        combobox();
    }//GEN-LAST:event_jButton61MouseClicked

    private void jButton65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton65MouseClicked
        delTblCol(blockedTable);
        blocks();
    }//GEN-LAST:event_jButton65MouseClicked

    private void branch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branch1ActionPerformed
        delTblCol(blockedTable);
        String name1, id = null, password = null;
        DBcon d = new DBcon();
        String sql2 = "SELECT * FROM blocked_client , client WHERE blocked_client.client_id = client.id AND blocked_client.branchName = '" + branch1.getSelectedItem() + "' ";
        try {
            d.rs = d.st.executeQuery(sql2);
            while (d.rs.next()) {
                name1 = d.rs.getString("client.name");
                id = d.rs.getString("client.id");
                String[] rowData = {name1, id};
                blockedTable.addRow(rowData);

            }
            d.con.close();
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_branch1ActionPerformed

    private void jButton63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton63MouseClicked

        DBcon d = new DBcon();
        String sql = "DELETE FROM `blocked_client` WHERE `client_id` = '" + jTextField44.getText() + "';";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `client` SET `blocked`= '0' WHERE id = '" + jTextField44.getText() + "';");

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + jTextField44.getText() + "', '" + jTextField45.getText() + "', '" + LocalDate.now() + "',  ' الغاء حظر عميل ','عميل');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        combobox();
    }//GEN-LAST:event_jButton63MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        try {

            String id = jTextField52.getText();
            boolean checked = false;
            int itemId = 0;
            DBcon d = new DBcon();
            String manId = jTextField51.getText();
            String val = jTextField39.getText();
            float presentage = 0;
            d.rs = d.st.executeQuery("select * from mandob WHERE id = '" + manId + "';");
            while (d.rs.next()) {
                presentage = d.rs.getFloat("Tahsel_percentage");
            }
            float totalRicivedMony = 0;
            d.rs = d.st.executeQuery("select * from mandob WHERE id = '" + manId + "';");
            while (d.rs.next()) {
                totalRicivedMony = d.rs.getFloat("total_recieved_money");
            }
            float total = Float.valueOf(val);
            float newRecived = (total * presentage) / 100 + totalRicivedMony;
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + newRecived + "' WHERE id = '" + manId + "';");
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `mandobrecivables` (`id`, `mandobId`, `value`, `type`, `clientId`, `date`) VALUES (NULL, '" + manId + "', '" + (total * presentage) / 100 + "', 'تحصيل', '" + jTextField35.getText() + "', '" + LocalDate.now() + "');");

            d.con.createStatement();
            d.st.executeUpdate("UPDATE `aqsat` SET `tahsel` = '1' , done = 1  WHERE `aqsat`.`id` = '" + id + "' ;");
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `tahsel` SET `done` = '1'  WHERE `qest_id` = '" + id + "';");
            light_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_on.png")));

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + id + "', '" + name + "', '" + LocalDate.now() + "',  'تحصيل قسط','client');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
    }//GEN-LAST:event_jButton9MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked

        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String mandobName = null, mandobId = null, mandobCardName = null, mandobLocation = null, mandobMobile = null, mandobCollectionRate = null, mandobSalesRate = null, PercentageSales = null;
        try {
            mandobId = this.mandobId.getText();
            String sql = "Update `mandob` set `stopped` = '1' WHERE id = '" + mandobId + "';";
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + mandobId + "', '" + mandobName + "', '" + LocalDate.now() + "', 'ايقاف مندوب' , 'mandob');");

            d.con.close();
        } catch (Exception e) {

        }

    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel126MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel126MouseClicked
        // TODO add your handling code here:
        lastClientQest l = new lastClientQest();
        l.setVisible(true);

    }//GEN-LAST:event_jLabel126MouseClicked

    private void text1_2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_text1_2CaretUpdate
        delTblCol(stockTable);
        DBcon d = new DBcon();
        String name1 = null, id1 = null, quant1 = null;
        String id = null;
        String s = text1_2.getText();
        try {
            String sql2 = "SELECT * FROM `item` WHERE name LIKE '" + s + "%'";
            d.rs = d.st.executeQuery(sql2);
            while ((d.rs).next()) {
                name1 = d.rs.getString("name");
                id1 = String.valueOf(d.rs.getInt("id"));
                quant1 = String.valueOf(d.rs.getInt("quantity"));
                String[] rowData = {quant1, name1, id1};
                stockTable.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_text1_2CaretUpdate

    private void jLabel123MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel123MouseClicked
        cons l = new cons();
        l.setVisible(true);
    }//GEN-LAST:event_jLabel123MouseClicked

    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseClicked
        tahselat r = new tahselat(Integer.valueOf(Cid.getText()), Cname.getText(), 4);
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton15MouseClicked

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MouseClicked
        tahselat r = new tahselat(Integer.valueOf(Cid.getText()), Cname.getText(), 3);
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton16MouseClicked

    private void itemComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComboBox4ActionPerformed

    }//GEN-LAST:event_itemComboBox4ActionPerformed

    private void mandobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mandobMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_mandobMouseEntered

    private void itemComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemComboBox5ActionPerformed

    private void jButton20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton20MouseClicked
        notes r = new notes("client", Integer.valueOf(Cid.getText()));
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton20MouseClicked

    private void jButton22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton22MouseClicked
        notes r = new notes("mandob", Integer.valueOf(mandobId.getText()));
        JFrame frame = new JFrame("Frame");
        frame.setSize(1300, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton22MouseClicked

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        delTblCol(notesT);

        String sql;
        if (jComboBox7.getSelectedItem().equals("عميل")) {
            sql = "select * from notes WHERE type = 'client'";
        } else if (jComboBox7.getSelectedItem().equals("مندوب")) {
            sql = "select * from notes WHERE type = 'mandob'";
        } else if (jComboBox7.getSelectedItem().equals("اخرى")) {
            sql = "select * from notes WHERE type = 'other'";
        } else {
            sql = "select * from notes ";
        }

        DBcon d = new DBcon();
        String id;
        String title, date;

        try {
            d.rs = d.st.executeQuery(sql);
            while (d.rs.next()) {
                id = String.valueOf(d.rs.getInt("id"));
                date = String.valueOf(d.rs.getDate("date"));
                title = d.rs.getString("title");
                String[] rowData = {date, title, id};
                notesT.addRow(rowData);
            }
            d.con.close();
        } catch (Exception e) {

        }

    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jButton48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton48MouseClicked
        DBcon d = new DBcon();
        try {

            float presentage = 0;
            d.rs = d.st.executeQuery("select * from received_money where id = '" + jTextField40.getText() + "';");
            d.rs.last();
            String manId = String.valueOf(d.rs.getInt("mandob_id"));
            float val = d.rs.getFloat("value");
            d.rs = d.st.executeQuery("select * from mandob where id = '" + manId + "';");
            d.rs.last();
            float totalRicivedMony = d.rs.getFloat("total_recieved_money");
            float newRecived = -Float.valueOf(jTextField33.getText()) + totalRicivedMony;
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + (totalRicivedMony + val) + "' WHERE id = '" + manId + "';");

            d.con.createStatement();
            d.st.executeUpdate("DELETE FROM received_money WHERE id = '" + jTextField40.getText() + "';");

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + manId + "', 'مندوب', '" + LocalDate.now() + "',  '" + jTextField33.getText() + "استلام من المندوب مبلغ قدره (حذف)  ' , 'mandob');");

            d.con.close();
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        mandobReceivedItemsTable();
    }//GEN-LAST:event_jButton48MouseClicked

    private void reportType1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportType1ActionPerformed
        delTblCol(reportT);

        if ((String) reportType1.getSelectedItem() != "الشهر") {

            String month = (String) reportType1.getSelectedItem();
            if (reportType.getSelectedItem().equals("عرض التقارير")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("المندوب")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'mandob' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("العملاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'client' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الاصناف")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'item' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الشركاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'partners' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            }
        } else {
            delTblCol(reportT);
            if (reportType.getSelectedItem().equals("عرض التقارير")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("المندوب")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'mandob' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("العملاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'client' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الاصناف")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'item' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الشركاء")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'partners' AND date between '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-1' AND '" + jComboBox9.getSelectedItem() + "-" + reportType1.getSelectedItem() + "-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            }
        }

    }//GEN-LAST:event_reportType1ActionPerformed

    private void branchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_branchActionPerformed

    private void jButton70MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton70MouseClicked
        money r = new money();
        JFrame frame = new JFrame("Frame");
        frame.setSize(800, 700);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton70MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        text1_1.setText(null);
        text1_2.setText(null);
        text1_3.setText(null);
        text1_4.setText(null);
        text1_5.setText(null);
        text1_6.setText(null);
        text1_7.setText(null);
        text1_8.setText(null);
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton66MouseClicked

        mandobId.setText(null);
        mandobName.setText(null);
        mandobCardName.setText(null);
        mandobLocation.setText(null);
        mandobMobile.setText(null);
        mandobCollectionRate.setText(null);

    }//GEN-LAST:event_jButton66MouseClicked

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        Cid.setText(null);
        Cname.setText(null);
        cNum.setText(null);
        add1.setText(null);
        add2.setText(null);
        mob1.setText(null);
        mob2.setText(null);
        Cjob.setText(null);
        jobPlace.setText(null);

    }//GEN-LAST:event_jButton10MouseClicked

    private void jButton38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton38MouseClicked
        jTextField51.setText(null);
        mand.setText(null);
        jTextField35.setText(null);
        jTextField36.setText(null);
        jTextField52.setText(null);
        jTextField37.setText(null);
        jTextField38.setText(null);
        jTextField39.setText(null);

        delTblCol(tahsealMan);
        delTblCol(clientsTahsel);
        delTblCol(qestTahsel);
        tahselTables();

    }//GEN-LAST:event_jButton38MouseClicked

    private void jButton42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton42MouseClicked
        itemId.setText(null);
        jTextField29.setText(null);
        jTextField31.setText(null);
        jTextField32.setText(null);
        jTextField50.setText(null);
        jTextField47.setText(null);
    }//GEN-LAST:event_jButton42MouseClicked

    private void jButton52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton52MouseClicked
        jTextField40.setText(null);
        jTextField33.setText(null);
        jTextField34.setText(null);
    }//GEN-LAST:event_jButton52MouseClicked

    private void jButton51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton51MouseClicked
        // TODO add your handling code here:
        jTextField53.setText(null);
        jTextField41.setText(null);
        jTextField53.setText(null);
        date.setText(null);
        jTextArea1.setText(null);
    }//GEN-LAST:event_jButton51MouseClicked

    private void jButton59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton59MouseClicked
        // TODO add your handling code here:
        userName.setText(null);
        password.setText(null);
    }//GEN-LAST:event_jButton59MouseClicked

    private void jComboBox7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7MouseClicked

    private void itemComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComboBox6ActionPerformed
        String branch = (String) itemComboBox6.getSelectedItem();
        delTblCol(receivedMony);
        DBcon d = new DBcon();
        String mandobName, id, title, date, value = null;
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `received_money`, mandob WHERE received_money.mandob_id = mandob.id AND mandob.branchName = '" + branch + "';");
            while ((d.rs).next()) {
                mandobName = d.rs.getString("mandob.name");
                id = String.valueOf(d.rs.getInt("received_money.id"));
                title = String.valueOf(d.rs.getString("received_money.title"));
                date = String.valueOf(d.rs.getDate("received_money.date"));
                value = String.valueOf(d.rs.getFloat("received_money.value"));
                String[] rowData = {date, value, title, mandobName, id};
                receivedMony.addRow(rowData);
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_itemComboBox6ActionPerformed

    private void jLabel72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseClicked
        DBcon d = new DBcon();
        String s = JOptionPane.showInputDialog("Enter branch Name -- ادخل اسم الفرع");
        String sql = "INSERT INTO `branch` (`name`) VALUES ('" + s + "');";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + getUserId() + "', '" + getUser() + "', '" + LocalDate.now() + "',  ' اضافة فرع ','فرع');");
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        combobox();
    }//GEN-LAST:event_jLabel72MouseClicked

    private void jLabel71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel71MouseClicked
        DBcon d = new DBcon();
        String s = branch.getSelectedItem().toString();
        String sql = "DELETE FROM `branch` WHERE `name` ='" + s + "';";
        try {
            d.st = d.con.createStatement();
            d.st.executeUpdate(sql);
            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + getUserId() + "', '" + getUser() + "', '" + LocalDate.now() + "',  ' ازالة فرع ','فرع');");
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px;'>تم </h3></html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }
        combobox();
    }//GEN-LAST:event_jLabel71MouseClicked

    private void jLabel73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel73MouseClicked
        int dialogButton = JOptionPane.showConfirmDialog(null, "هل أنت متأكد من مسح جميع البيانات ؟.. لا يمكن التراجع", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Please wait   --  برجاء الانتظار");
            DBcon d = new DBcon();
            String[] sql = {
                "delete from `tahsel`;",
                "delete from `return_items`;",
                "delete from `blocked_client`;",
                "delete from `reciepts`;",
                "delete from `received_money`;",
                "delete from `multiman`;",
                "delete from `mandob_received_items`;",
                "delete from `history`;",
                "delete from `aqsat`;",
                "delete from `client_recieved_items`;",
                "delete from `mandobrecivables`;",
                "delete from `item`;",
                "delete from `partners`;",
                "delete from `payment`;",
                "delete from `mandob`;",
                "delete from `client`;",
                "delete from `notes`;",
                "delete from `users`;",
                "delete from `area`;",
                "delete from `branch` WHERE id != 0;"};
            try {
//             d.st = d.con.createStatement();
//            d.st.executeUpdate("INSERT INTO `mandob` (`id`, `name`, `card_num`, `address`, `mob`, `total_recieved_money`, `total_required_money`, `Tahsel_percentage`, `stopped`, `areaName`, `branchName`) VALUES ('0', 'multii', '0', '0', '0', '0', '0', '0', '0', 'العريش', 'hamada');");
                //INSERT INTO `mandob` (`id`, `name`, `card_num`, `address`, `mob`, `total_recieved_money`, `total_required_money`, `Tahsel_percentage`, `stopped`, `areaName`, `branchName`) VALUES ('50', 'multii', '0', '0', '0', '0', '0', '0', '0', 'العريش', 'hamada');
                for (int i = 0; i < sql.length; i++) {

                    d.st = d.con.createStatement();
                    d.st.executeUpdate(sql[i]);
                }

                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `branch` (id,`name`) VALUES ('0','الاسماعيلية');");
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `area` (id,`area_name`,branchName) VALUES ('0','حي السلام' ,'الاسماعيلية' );");
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `users` (`id`, `name`, `password`, `logged`, `branchName`, `p1`, `p2`, `p3`, `p4`, `p5`, `p6`, `p7`, `p8`, `p9`, `p10`, `p11`, `p12`) VALUES (0, 'admin', '123456789', '1', 'الاسماعيلية', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1');");
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `client` (`id`, `name`, `card_num`, `address1`, `address2`, `mob1`, `mob2`, `job`, `job_place`, `blocked`, `areaName`, `branchName`) VALUES ('0', '--', '--', '--', '--', '--', '--', '--', '--', '0', 'حي السلام', 'الاسماعيلية');");
                d.st = d.con.createStatement();
                d.st.executeUpdate("INSERT INTO `mandob` (`id`, `name`, `card_num`, `address`, `mob`, `total_recieved_money`, `total_required_money`, `Tahsel_percentage`, `stopped`, `areaName`, `branchName`) VALUES ('0', 'متعدد', '0', '0', '0', '0', '0', '0', '0', 'حي السلام', 'الاسماعيلية');");
                JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:10px'>تم </h3></html>");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
            }
        } else {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>لا تقلق لم يتم المسح</h3></html>");
        }
    }//GEN-LAST:event_jLabel73MouseClicked

    private void online_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_online_iconMouseClicked
        if (!Cid.getText().equals("")) {
            String client_name = "غير معروف", card_num = "غير معروف";

            try {
                client_name = getNameFromJson(Cid.getText());
                card_num = getCardFromJson(Cid.getText());
                // System.out.println("Client Name : " + client_name + "\nCard Num : " + card_num);
                online_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/online_green.png")));
                Cname.setText(client_name);
                cNum.setText(card_num);
            } catch (IOException ex) {
                online_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/online_red.png")));
            }
        } else {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:10px'>ادخل كود العميل </h3></html>");
        }

    }//GEN-LAST:event_online_iconMouseClicked

    private void jLabel112MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel112MouseClicked
        String[] arr = new String[20];
        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("SELECT * FROM hamada ;");

            while (d.rs.next()) {
                arr[0] = d.rs.getString("clientId");
                arr[1] = d.rs.getString("firstPay");
                arr[2] = d.rs.getString("restPay");
                arr[3] = d.rs.getString("qestNum");
                arr[4] = d.rs.getString("qestOrder");
                arr[5] = d.rs.getString("Item");
                arr[6] = d.rs.getString("qestValue");
                arr[7] = d.rs.getString("qestText");
                arr[8] = d.rs.getString("clientName");
                arr[9] = d.rs.getString("mob");
                arr[10] = d.rs.getString("nationalId");
                arr[11] = d.rs.getString("area");
                arr[12] = d.rs.getString("address");
                arr[13] = d.rs.getString("work_place");
                arr[14] = d.rs.getString("job");
                arr[15] = d.rs.getString("selling_man");
                arr[16] = d.rs.getString("tahseel_man");
                arr[17] = d.rs.getString("purch_date");
                arr[18] = d.rs.getString("qest_date");
                TaqseetPDF.fullRec(arr);
            }

        } catch (Exception ex) {

        }

        String pdfFilename = "C:\\Users\\ahmed\\OneDrive\\Desktop\\Qst.pdf";
        TaqseetPDF generateInvoice = new TaqseetPDF();
        generateInvoice.createPDF(pdfFilename);
        try {
            Desktop.getDesktop().open(new File(pdfFilename));
        } catch (Exception ex) {
            System.out.println("Can't be opened");
        }
    }//GEN-LAST:event_jLabel112MouseClicked

    private void jTextField34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField34MouseClicked

    }//GEN-LAST:event_jTextField34MouseClicked

    private void jTextField34CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField34CaretUpdate

        delTblCol(receivedMony);
        DBcon d = new DBcon();
        String mandobName, id, title, date, value = null;
        try {
            d.rs = d.st.executeQuery("SELECT * FROM `received_money`, mandob WHERE received_money.mandob_id = mandob.id WHERE received_money.title like '" + jTextField34.getText() + "%';");
            while ((d.rs).next()) {
                mandobName = d.rs.getString("mandob.name");
                id = String.valueOf(d.rs.getInt("received_money.id"));
                title = String.valueOf(d.rs.getString("received_money.title"));
                date = String.valueOf(d.rs.getDate("received_money.date"));
                value = String.valueOf(d.rs.getFloat("received_money.value"));
                String[] rowData = {date, value, title, mandobName, id};
                receivedMony.addRow(rowData);
            }
        } catch (Exception e) {

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField34CaretUpdate

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void itemComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemComboBoxKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            delTblCol(stockTable);
            DBcon d = new DBcon();
            String name1 = null, id1 = null, quant1 = null;
            String id = null;
            try {
                String sql2 = "SELECT * FROM `item` WHERE branchName = '" + itemComboBox.getSelectedItem() + "'";
                d.rs = d.st.executeQuery(sql2);
                while ((d.rs).next()) {
                    name1 = d.rs.getString("name");
                    id1 = String.valueOf(d.rs.getInt("id"));
                    quant1 = String.valueOf(d.rs.getInt("quantity"));
                    String[] rowData = {quant1, name1, id1};
                    stockTable.addRow(rowData);
                }
            } catch (Exception e) {

            }

        }
    }//GEN-LAST:event_itemComboBoxKeyPressed

    private void itemComboBox4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemComboBox4KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            delTblCol(areaTable);

            DBcon d = new DBcon();
            String id;
            String title, name;

            try {
                d.rs = d.st.executeQuery("SELECT * FROM area WHERE branchName = '" + itemComboBox4.getSelectedItem() + "'");
                while (d.rs.next()) {
                    id = String.valueOf(d.rs.getInt("id"));
                    name = String.valueOf(d.rs.getString("area_name"));
                    String[] rowData = {name, id};
                    areaTable.addRow(rowData);
                }
            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_itemComboBox4KeyPressed

    private void jComboBox4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox4KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            delTblCol(recipt);
            DBcon d = new DBcon();
            DBcon db = new DBcon();
            String id, value = null, multiName = null, clientName, item, add1, add2, add, item_id, area, job, total, overHand, count, reciveDate, tahselDate, CRI, mob, recivedName = null, tahselName = null, order, clientNum, jobPlace;
            float allPaid, rest;
            try {

                String mandob = jComboBox4.getSelectedItem().toString();
                String manId = null;
                String sql1 = "SELECT * FROM `mandob`WHERE  mandob.name='" + mandob + "';";

                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                manId = d.rs.getString("mandob.id");
                d.rs = d.st.executeQuery("SELECT * FROM `client`, client_recieved_items ,aqsat ,mandob ,item WHERE client_recieved_items.mandob_id = mandob.id AND client_recieved_items.clientId = client.id AND client_recieved_items.id = aqsat.CRI AND client_recieved_items.item_id = item.id AND aqsat.done = '0' AND aqsat.received_date between '" + jComboBox2.getSelectedItem() + "-1-1' AND '" + (Integer.valueOf(jComboBox2.getSelectedItem().toString()) + 1) + "-1-1' AND aqsat.month_payment = '" + jComboBox3.getSelectedItem() + "' AND aqsat.mandob_id = '" + manId + "';");
                //JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
                while ((d.rs).next()) {
                    recivedName = "";
                    float qestPaid = 0;
                    CRI = String.valueOf(d.rs.getInt("client_recieved_items.id"));

                    if (d.rs.getInt("client_recieved_items.mandob_id") == 0) {

                        db.rs = db.st.executeQuery("SELECT * FROM `multiman`,mandob WHERE multiman.mandob_id = mandob.id AND CRI = '" + CRI + "';");
                        while ((db.rs).next()) {
                            if ((db.rs).isLast()) {
                                recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0]);
                            } else {
                                recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0] + " - ");
                            }
                        }
                    } else {
                        recivedName = d.rs.getString("mandob.name");
                    }
                    clientName = d.rs.getString("client.name");
                    clientNum = d.rs.getString("client.card_num");
                    id = String.valueOf(d.rs.getInt("client.id"));
                    area = String.valueOf(d.rs.getString("client.areaName"));
                    job = String.valueOf(d.rs.getString("client.job"));
                    jobPlace = String.valueOf(d.rs.getString("client.job_place"));
                    mob = String.valueOf(d.rs.getString("client.mob1"));
                    item = String.valueOf(d.rs.getString("item.name"));
                    count = String.valueOf(d.rs.getInt("client_recieved_items.month_count"));
                    overHand = String.valueOf(d.rs.getFloat("client_recieved_items.handOver"));
                    total = String.valueOf(d.rs.getFloat("client_recieved_items.total_payment"));
                    order = String.valueOf(d.rs.getInt("aqsat.order"));
                    add1 = String.valueOf(d.rs.getString("client.address1"));
                    add2 = String.valueOf(d.rs.getString("client.address2"));
                    add = add1 + " , " + add2;
                    value = String.valueOf(d.rs.getFloat("aqsat.value"));
                    db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' AND done = '1';");
                    while ((db.rs).next()) {
                        qestPaid += db.rs.getFloat("aqsat.value");

                    }
                    db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' ;");
                    while ((db.rs).next()) {
                        tahselName = db.rs.getString("mandob.name");
                    }
                    allPaid = qestPaid + Float.valueOf(overHand);
                    rest = Float.valueOf(total) - (qestPaid + Float.valueOf(overHand));
                    System.out.println(qestPaid);
                    tahselDate = String.valueOf(d.rs.getString("aqsat.received_date"));
                    reciveDate = String.valueOf(d.rs.getDate("client_recieved_items.received_date"));

                    String[] rowData = {tahselDate, tahselName, recivedName, reciveDate, job, jobPlace, value, clientNum, add, order, mob, clientName, count + "", rest + "", allPaid + "", item, area, id};
                    recipt.addRow(rowData);
                }
            } catch (Exception e) {

            }
        }
        totalAqsatVal();
    }//GEN-LAST:event_jComboBox4KeyPressed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            delTblCol(recipt);
            DBcon d = new DBcon();
            DBcon db = new DBcon();
            String id, value = null, multiName = null, clientName, item, add1, add2, add, item_id, area, job, total, overHand, count, reciveDate, tahselDate, CRI, mob, recivedName = null, tahselName = null, order, clientNum, jobPlace;
            float allPaid, rest;
            try {
                d.rs = d.st.executeQuery("SELECT * FROM `client`, client_recieved_items ,aqsat ,mandob ,item WHERE client_recieved_items.mandob_id = mandob.id AND client_recieved_items.clientId = client.id AND client_recieved_items.id = aqsat.CRI AND client_recieved_items.item_id = item.id AND aqsat.done = '0' AND aqsat.received_date between '" + jComboBox2.getSelectedItem() + "-1-1' AND '" + (Integer.valueOf(jComboBox2.getSelectedItem().toString()) + 1) + "-1-1';");
                //JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
                while ((d.rs).next()) {
                    recivedName = "";
                    float qestPaid = 0;
                    CRI = String.valueOf(d.rs.getInt("client_recieved_items.id"));

                    if (d.rs.getInt("client_recieved_items.mandob_id") == 0) {

                        db.rs = db.st.executeQuery("SELECT * FROM `multiman`,mandob WHERE multiman.mandob_id = mandob.id AND CRI = '" + CRI + "';");
                        while ((db.rs).next()) {
                            if ((db.rs).isLast()) {
                                recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0]);
                            } else {
                                recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0] + " - ");
                            }
                        }
                    } else {
                        recivedName = d.rs.getString("mandob.name");
                    }
                    clientName = d.rs.getString("client.name");
                    clientNum = d.rs.getString("client.card_num");
                    id = String.valueOf(d.rs.getInt("client.id"));
                    area = String.valueOf(d.rs.getString("client.areaName"));
                    job = String.valueOf(d.rs.getString("client.job"));
                    jobPlace = String.valueOf(d.rs.getString("client.job_place"));
                    mob = String.valueOf(d.rs.getString("client.mob1"));
                    item = String.valueOf(d.rs.getString("item.name"));
                    count = String.valueOf(d.rs.getInt("client_recieved_items.month_count"));
                    overHand = String.valueOf(d.rs.getFloat("client_recieved_items.handOver"));
                    total = String.valueOf(d.rs.getFloat("client_recieved_items.total_payment"));
                    order = String.valueOf(d.rs.getInt("aqsat.order"));
                    add1 = String.valueOf(d.rs.getString("client.address1"));
                    add2 = String.valueOf(d.rs.getString("client.address2"));
                    add = add1 + " , " + add2;
                    value = String.valueOf(d.rs.getFloat("aqsat.value"));
                    db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' AND done = '1';");
                    while ((db.rs).next()) {
                        qestPaid += db.rs.getFloat("aqsat.value");

                    }
                    db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' ;");
                    while ((db.rs).next()) {
                        tahselName = db.rs.getString("mandob.name");
                    }
                    allPaid = qestPaid + Float.valueOf(overHand);
                    rest = Float.valueOf(total) - (qestPaid + Float.valueOf(overHand));
                    System.out.println(qestPaid);
                    tahselDate = String.valueOf(d.rs.getString("aqsat.received_date"));
                    reciveDate = String.valueOf(d.rs.getDate("client_recieved_items.received_date"));

                    String[] rowData = {tahselDate, tahselName, recivedName, reciveDate, job, jobPlace, value, clientNum, add, order, mob, clientName, count + "", rest + "", allPaid + "", item, area, id};
                    recipt.addRow(rowData);
                }
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);

            }

        }
        totalAqsatVal();
    }//GEN-LAST:event_jComboBox2KeyPressed

    private void jComboBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            delTblCol(recipt);
            DBcon d = new DBcon();
            DBcon db = new DBcon();
            String id, value = null, multiName = null, clientName, item, add1, add2, add, item_id, area, job, total, overHand, count, reciveDate, tahselDate, CRI, mob, recivedName = null, tahselName = null, order, clientNum, jobPlace;
            float allPaid, rest;
            try {
                d.rs = d.st.executeQuery("SELECT * FROM `client`, client_recieved_items ,aqsat ,mandob ,item WHERE client_recieved_items.mandob_id = mandob.id AND client_recieved_items.clientId = client.id AND client_recieved_items.id = aqsat.CRI AND client_recieved_items.item_id = item.id AND aqsat.done = '0' AND aqsat.received_date between '" + jComboBox2.getSelectedItem() + "-1-1' AND '" + (Integer.valueOf(jComboBox2.getSelectedItem().toString()) + 1) + "-1-1' AND aqsat.month_payment = '" + jComboBox3.getSelectedItem() + "';");
                //JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
                while ((d.rs).next()) {
                    recivedName = "";
                    float qestPaid = 0;
                    CRI = String.valueOf(d.rs.getInt("client_recieved_items.id"));

                    if (d.rs.getInt("client_recieved_items.mandob_id") == 0) {

                        db.rs = db.st.executeQuery("SELECT * FROM `multiman`,mandob WHERE multiman.mandob_id = mandob.id AND CRI = '" + CRI + "';");
                        while ((db.rs).next()) {
                            if ((db.rs).isLast()) {
                                recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0]);
                            } else {
                                recivedName += (db.rs.getString("mandob.name").split(" ", 2)[0] + " - ");
                            }
                        }
                    } else {
                        recivedName = d.rs.getString("mandob.name");
                    }
                    clientName = d.rs.getString("client.name");
                    clientNum = d.rs.getString("client.card_num");
                    id = String.valueOf(d.rs.getInt("client.id"));
                    area = String.valueOf(d.rs.getString("client.areaName"));
                    job = String.valueOf(d.rs.getString("client.job"));
                    jobPlace = String.valueOf(d.rs.getString("client.job_place"));
                    mob = String.valueOf(d.rs.getString("client.mob1"));
                    item = String.valueOf(d.rs.getString("item.name"));
                    count = String.valueOf(d.rs.getInt("client_recieved_items.month_count"));
                    overHand = String.valueOf(d.rs.getFloat("client_recieved_items.handOver"));
                    total = String.valueOf(d.rs.getFloat("client_recieved_items.total_payment"));
                    order = String.valueOf(d.rs.getInt("aqsat.order"));
                    add1 = String.valueOf(d.rs.getString("client.address1"));
                    add2 = String.valueOf(d.rs.getString("client.address2"));
                    tahselDate = String.valueOf(d.rs.getString("aqsat.received_date"));
                    add = add1 + " , " + add2;
                    value = String.valueOf(d.rs.getFloat("aqsat.value"));
                    db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' AND done = '1';");
                    while ((db.rs).next()) {
                        qestPaid += db.rs.getFloat("aqsat.value");

                    }
                    db.rs = db.st.executeQuery("SELECT * FROM `aqsat`,mandob WHERE mandob.id = aqsat.mandob_id AND CRI = '" + CRI + "' ;");
                    while ((db.rs).next()) {
                        tahselName = db.rs.getString("mandob.name");

                    }
                    allPaid = qestPaid + Float.valueOf(overHand);
                    rest = Float.valueOf(total) - (qestPaid + Float.valueOf(overHand));
                    System.out.println(qestPaid);

                    reciveDate = String.valueOf(d.rs.getDate("client_recieved_items.received_date"));

                    String[] rowData = {tahselDate, tahselName, recivedName, reciveDate, job, jobPlace, value, clientNum, add, order, mob, clientName, count + "", rest + "", allPaid + "", item, area, id};
                    recipt.addRow(rowData);
                }
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, "please try again   --  الرجاء المحاولة مرة أخري" + e);
            }

        }
        totalAqsatVal();
    }//GEN-LAST:event_jComboBox3KeyPressed

    private void jLabel116MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel116MouseClicked
        JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>الرجاء الانتظار </h3></html>");
        for (int i = 0; i < reciptT.getRowCount(); i++) {
            String[] rowData = {reciptT.getValueAt(i, 17).toString(), reciptT.getValueAt(i, 16).toString(), reciptT.getValueAt(i, 15).toString(), reciptT.getValueAt(i, 14).toString(), reciptT.getValueAt(i, 13).toString(), reciptT.getValueAt(i, 12).toString(), reciptT.getValueAt(i, 11).toString(), reciptT.getValueAt(i, 10).toString(), reciptT.getValueAt(i, 9).toString(), reciptT.getValueAt(i, 8).toString(), reciptT.getValueAt(i, 7).toString(), reciptT.getValueAt(i, 6).toString(), reciptT.getValueAt(i, 5).toString(), reciptT.getValueAt(i, 4).toString(), reciptT.getValueAt(i, 3).toString(), reciptT.getValueAt(i, 2).toString(), reciptT.getValueAt(i, 1).toString(), reciptT.getValueAt(i, 0).toString()};
            TaqseetPDF.fullRec(rowData);
        }
        String pdfFilename = filePath + "\\Qst-" + Home.Date.getText() + ".pdf";
        TaqseetPDF generateInvoice = new TaqseetPDF();
        generateInvoice.createPDF(pdfFilename);

        try {
            Desktop.getDesktop().open(new File(pdfFilename));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "<html><h1 style='font-family: Calibri; font-size:20px'>هناك خطأ ما -- حاول مرة اخري </h3></html>");
        }

        //fullRec()
    }//GEN-LAST:event_jLabel116MouseClicked

    private void itemComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemComboBox1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_itemComboBox1MouseClicked

    private void itemComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemComboBoxMouseClicked
        // JOptionPane.showMessageDialog(null, LocalDateTime.now());;
    }//GEN-LAST:event_itemComboBoxMouseClicked

    private void blockedTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blockedTMouseClicked
        String id = null, name = "unknown", quant = null, branch = null;
        int n = blockedT.getSelectedRow();
        id = blockedTable.getValueAt(n, 1).toString();
        name = blockedTable.getValueAt(n, 0).toString();

        DBcon d = new DBcon();
        try {
            d.rs = d.st.executeQuery("SELECT * FROM client WHERE id = '" + id + "';");
            while ((d.rs).next()) {
                branch = d.rs.getString("branchName");

            }

        } catch (Exception e) {
        }

        branch1.setSelectedItem(branch);
        jTextField44.setText(id);
        jTextField45.setText(name);

    }//GEN-LAST:event_blockedTMouseClicked

    private void light_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_light_lblMouseClicked

        try {

            String id = jTextField52.getText();
            boolean checked = false;
            int itemId = 0;
            DBcon d = new DBcon();
            String manId = jTextField51.getText();
            String val = jTextField39.getText();
            float presentage = 0;
            d.rs = d.st.executeQuery("select * from mandob WHERE id = '" + manId + "';");
            while (d.rs.next()) {
                presentage = d.rs.getFloat("Tahsel_percentage");
            }
            float totalRicivedMony = 0;
            d.rs = d.st.executeQuery("select * from mandob WHERE id = '" + manId + "';");
            while (d.rs.next()) {
                totalRicivedMony = d.rs.getFloat("total_recieved_money");
            }
            float total = Float.valueOf(val);
            float newRecived = -(total * presentage) / 100 + totalRicivedMony;
            d.st = d.con.createStatement();
            d.st.executeUpdate("UPDATE mandob set total_recieved_money = '" + newRecived + "' WHERE id = '" + manId + "';");

            d.con.createStatement();
            d.st.executeUpdate("UPDATE `aqsat` SET `tahsel` = '0' , done = 0  WHERE `aqsat`.`id` = '" + id + "' ;");
            d.con.createStatement();
            d.st.executeUpdate("UPDATE `tahsel` SET `done` = '0'  WHERE `qest_id` = '" + id + "';");

            d.st = d.con.createStatement();
            d.st.executeUpdate("INSERT INTO `history` (`id`, `person_id`, `person_type`, `date`, `operation`,type) VALUES (NULL, '" + id + "', '" + name + "', '" + LocalDate.now() + "',  'الغاء تحصيل قسط ','client');");

            light_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_off.png")));
            d.con.close();
        } catch (SQLException ex) {
        }

    }//GEN-LAST:event_light_lblMouseClicked

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed

        if ((String) jComboBox9.getSelectedItem() != "السنة") {
            delTblCol(reportT);
            if (reportType.getSelectedItem().equals("عرض التقارير")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("المندوب")) {
                delTblCol(reportT);
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'mandob' AND date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("العملاء")) {
                delTblCol(reportT);
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'client' AND date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الاصناف")) {
                delTblCol(reportT);
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'item' AND date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الشركاء")) {
                delTblCol(reportT);
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'partners' AND date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            }
        } else {
            delTblCol(reportT);
            if (reportType.getSelectedItem().equals("عرض التقارير")) {
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("المندوب")) {
                DBcon d = new DBcon();
                delTblCol(reportT);
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'mandob' AND date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("العملاء")) {
                DBcon d = new DBcon();
                delTblCol(reportT);
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'client' date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الاصناف")) {
                delTblCol(reportT);
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'item' date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            } else if (reportType.getSelectedItem().equals("الشركاء")) {
                delTblCol(reportT);
                DBcon d = new DBcon();
                String id, title, date, Name, type, operation;
                try {
                    d.rs = d.st.executeQuery("select * from history WHERE type = 'partners' AND date between '" + jComboBox9.getSelectedItem() + "-1-1' AND '" + jComboBox9.getSelectedItem() + "-12-31'");
                    while (d.rs.next()) {
                        id = String.valueOf(d.rs.getInt("person_id"));
                        date = String.valueOf(d.rs.getDate("date"));
                        Name = d.rs.getString("person_type");
                        operation = d.rs.getString("operation");
                        String[] rowData = {date, operation, Name, id};
                        reportT.addRow(rowData);
                    }
                    d.con.close();
                } catch (Exception e) {

                }
            }
        }
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox9KeyPressed

    private void itemId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemId1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemId1ActionPerformed

    private void itemId1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemId1KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            DBcon d = new DBcon();
            String sql1 = "SELECT * FROM client_recieved_items,`item`,mandob,client WHERE  client_recieved_items.item_id = item.id AND client_recieved_items.clientId = client.id AND client_recieved_items.mandob_id = mandob.id AND client_recieved_items.id = '" + itemId1.getText() + "';";
            try {
                d.rs = d.st.executeQuery(sql1);
                d.rs.last();
                itemId.setText(d.rs.getLong("item.id") + "");
                jTextField29.setText(d.rs.getString("item.name"));
                jTextField31.setText(d.rs.getInt("mandob.id") + "");
                jTextField32.setText(d.rs.getString("mandob.name"));
                jTextField50.setText(d.rs.getInt("client.id") + "");
                jTextField47.setText(d.rs.getString("client.name"));
            } catch (SQLException ex) {

            }
        }

    }//GEN-LAST:event_itemId1KeyPressed

    private void excelStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStockMouseClicked
        exportExcel(stock_table, "جدول المخزن");
    }//GEN-LAST:event_excelStockMouseClicked

    private void jButton23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton23MouseClicked
        mandobMoney r = new mandobMoney(Integer.valueOf(mandobId.getText()), mandobName.getText());
        JFrame frame = new JFrame("Frame");
        frame.setSize(1150, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton23MouseClicked

    private void jLabel76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel76MouseClicked
        clientTransactions r = new clientTransactions();
        JFrame frame = new JFrame("Frame");
        frame.setSize(1150, 720);
        frame.add(r);
        frame.setVisible(true);
    }//GEN-LAST:event_jLabel76MouseClicked

    private void excelStock1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock1MouseClicked
        exportExcel(areaT, "جدول المناطق");
    }//GEN-LAST:event_excelStock1MouseClicked

    private void excelStock2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock2MouseClicked
        exportExcel(returnTable, "جدول المرجوعات");
    }//GEN-LAST:event_excelStock2MouseClicked

    private void excelStock3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock3MouseClicked
        exportExcel(recivedMonyT, "جدول الحسابات");
    }//GEN-LAST:event_excelStock3MouseClicked

    private void excelStock4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock4MouseClicked
        exportExcel(notesTable, "جدول الملاحظات");
    }//GEN-LAST:event_excelStock4MouseClicked

    private void excelStock5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock5MouseClicked
        exportExcel(reciptT, "جدول الايصالات");
    }//GEN-LAST:event_excelStock5MouseClicked

    private void excelStock6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock6MouseClicked
        exportExcel(reports, "جدول التقارير");
    }//GEN-LAST:event_excelStock6MouseClicked

    private void excelStock7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock7MouseClicked
        exportExcel(userT, "جدول المستخدمين");
    }//GEN-LAST:event_excelStock7MouseClicked

    private void excelStock8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelStock8MouseClicked
        exportExcel(blockedT, "جدول الحظر");
    }//GEN-LAST:event_excelStock8MouseClicked

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
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel BlockPanel;
    public javax.swing.JPanel BlockPanel1;
    public javax.swing.JPanel BlockPanel2;
    public javax.swing.JPanel BlockPanel3;
    public javax.swing.JPanel CalculationPanel;
    public javax.swing.JPanel CalculationPanel1;
    public javax.swing.JPanel CalculationPanel2;
    public static javax.swing.JTextField Cid;
    public static javax.swing.JTextField Cjob;
    public static javax.swing.JTextField Cname;
    public static javax.swing.JLabel Date;
    public javax.swing.JPanel PayPanel;
    public javax.swing.JPanel PayPanel1;
    public javax.swing.JPanel PayPanel2;
    public javax.swing.JPanel aboutPanel;
    public static javax.swing.JTextField add1;
    public static javax.swing.JTextField add2;
    public javax.swing.JPanel areaPanel;
    public javax.swing.JPanel areaPanel1;
    public javax.swing.JPanel areaPanel2;
    public javax.swing.JTable areaT;
    public javax.swing.JTable blockedT;
    public javax.swing.JPanel bottomBorderPanel;
    public javax.swing.JComboBox<String> branch;
    public javax.swing.JComboBox<String> branch1;
    public javax.swing.JLabel branch_lbl12;
    public javax.swing.JLabel branch_lbl13;
    public static javax.swing.JTextField cNum;
    public javax.swing.JLabel check_lbl11;
    public javax.swing.JLabel check_lbl12;
    public javax.swing.JLabel check_lbl13;
    public javax.swing.JLabel check_lbl14;
    public javax.swing.JLabel check_lbl15;
    public javax.swing.JLabel check_lbl16;
    public javax.swing.JLabel check_lbl17;
    public javax.swing.JLabel check_lbl18;
    public javax.swing.JLabel check_lbl19;
    public javax.swing.JLabel check_lbl20;
    public javax.swing.JLabel check_lbl21;
    public javax.swing.JLabel check_lbl22;
    public javax.swing.JPanel clientPanel;
    public javax.swing.JPanel clientPanel1;
    public javax.swing.JTable clients;
    public javax.swing.JLabel close_lbl;
    public javax.swing.JTextField date;
    public javax.swing.JLabel excelStock;
    public javax.swing.JLabel excelStock1;
    public javax.swing.JLabel excelStock2;
    public javax.swing.JLabel excelStock3;
    public javax.swing.JLabel excelStock4;
    public javax.swing.JLabel excelStock5;
    public javax.swing.JLabel excelStock6;
    public javax.swing.JLabel excelStock7;
    public javax.swing.JLabel excelStock8;
    public javax.swing.JPanel fullPanel;
    public javax.swing.JPanel homePanel;
    public javax.swing.JPanel homePanel1;
    public javax.swing.JComboBox<String> itemComboBox;
    public static javax.swing.JComboBox<String> itemComboBox1;
    public static javax.swing.JComboBox<String> itemComboBox2;
    public javax.swing.JComboBox<String> itemComboBox3;
    public javax.swing.JComboBox<String> itemComboBox4;
    public javax.swing.JComboBox<String> itemComboBox5;
    public javax.swing.JComboBox<String> itemComboBox6;
    public javax.swing.JTextField itemId;
    public javax.swing.JTextField itemId1;
    public javax.swing.JPanel itemPanel12;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton11;
    public javax.swing.JButton jButton12;
    public javax.swing.JButton jButton13;
    public javax.swing.JButton jButton14;
    public javax.swing.JButton jButton15;
    public javax.swing.JButton jButton16;
    public javax.swing.JButton jButton17;
    public javax.swing.JButton jButton18;
    public javax.swing.JButton jButton19;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton20;
    public javax.swing.JButton jButton22;
    public javax.swing.JButton jButton23;
    public javax.swing.JButton jButton29;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton32;
    public javax.swing.JButton jButton36;
    public javax.swing.JButton jButton37;
    public javax.swing.JButton jButton38;
    public javax.swing.JButton jButton39;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton42;
    public javax.swing.JButton jButton43;
    public javax.swing.JButton jButton44;
    public javax.swing.JButton jButton45;
    public javax.swing.JButton jButton47;
    public javax.swing.JButton jButton48;
    public javax.swing.JButton jButton49;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton50;
    public javax.swing.JButton jButton51;
    public javax.swing.JButton jButton52;
    public javax.swing.JButton jButton55;
    public javax.swing.JButton jButton56;
    public javax.swing.JButton jButton57;
    public javax.swing.JButton jButton58;
    public javax.swing.JButton jButton59;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton60;
    public javax.swing.JButton jButton61;
    public javax.swing.JButton jButton63;
    public javax.swing.JButton jButton64;
    public javax.swing.JButton jButton65;
    public javax.swing.JButton jButton66;
    public javax.swing.JButton jButton67;
    public javax.swing.JButton jButton68;
    public javax.swing.JButton jButton69;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton70;
    public javax.swing.JButton jButton71;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JComboBox<String> jComboBox2;
    public javax.swing.JComboBox<String> jComboBox3;
    public javax.swing.JComboBox<String> jComboBox4;
    public static javax.swing.JComboBox<String> jComboBox5;
    public static javax.swing.JComboBox<String> jComboBox6;
    public javax.swing.JComboBox<String> jComboBox7;
    public javax.swing.JComboBox<String> jComboBox8;
    public javax.swing.JComboBox<String> jComboBox9;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel100;
    public javax.swing.JLabel jLabel101;
    public javax.swing.JLabel jLabel102;
    public javax.swing.JLabel jLabel103;
    public javax.swing.JLabel jLabel106;
    public javax.swing.JLabel jLabel107;
    public javax.swing.JLabel jLabel108;
    public javax.swing.JLabel jLabel109;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel110;
    public javax.swing.JLabel jLabel111;
    public javax.swing.JLabel jLabel112;
    public javax.swing.JLabel jLabel116;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel123;
    public javax.swing.JLabel jLabel124;
    public javax.swing.JLabel jLabel125;
    public javax.swing.JLabel jLabel126;
    public javax.swing.JLabel jLabel127;
    public javax.swing.JLabel jLabel128;
    public javax.swing.JLabel jLabel129;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel130;
    public javax.swing.JLabel jLabel131;
    public javax.swing.JLabel jLabel132;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel22;
    public javax.swing.JLabel jLabel23;
    public javax.swing.JLabel jLabel24;
    public javax.swing.JLabel jLabel25;
    public javax.swing.JLabel jLabel26;
    public javax.swing.JLabel jLabel27;
    public javax.swing.JLabel jLabel28;
    public javax.swing.JLabel jLabel29;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel30;
    public javax.swing.JLabel jLabel31;
    public javax.swing.JLabel jLabel32;
    public javax.swing.JLabel jLabel33;
    public javax.swing.JLabel jLabel34;
    public javax.swing.JLabel jLabel35;
    public javax.swing.JLabel jLabel36;
    public javax.swing.JLabel jLabel37;
    public javax.swing.JLabel jLabel38;
    public javax.swing.JLabel jLabel39;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel40;
    public javax.swing.JLabel jLabel41;
    public javax.swing.JLabel jLabel42;
    public javax.swing.JLabel jLabel43;
    public javax.swing.JLabel jLabel44;
    public javax.swing.JLabel jLabel45;
    public javax.swing.JLabel jLabel46;
    public javax.swing.JLabel jLabel47;
    public javax.swing.JLabel jLabel48;
    public javax.swing.JLabel jLabel49;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel50;
    public javax.swing.JLabel jLabel51;
    public javax.swing.JLabel jLabel52;
    public javax.swing.JLabel jLabel53;
    public javax.swing.JLabel jLabel54;
    public javax.swing.JLabel jLabel55;
    public javax.swing.JLabel jLabel56;
    public javax.swing.JLabel jLabel57;
    public javax.swing.JLabel jLabel58;
    public javax.swing.JLabel jLabel59;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel60;
    public javax.swing.JLabel jLabel61;
    public javax.swing.JLabel jLabel62;
    public javax.swing.JLabel jLabel63;
    public javax.swing.JLabel jLabel64;
    public javax.swing.JLabel jLabel65;
    public javax.swing.JLabel jLabel66;
    public javax.swing.JLabel jLabel67;
    public javax.swing.JLabel jLabel68;
    public javax.swing.JLabel jLabel69;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel70;
    public javax.swing.JLabel jLabel71;
    public javax.swing.JLabel jLabel72;
    public javax.swing.JLabel jLabel73;
    public javax.swing.JLabel jLabel74;
    public javax.swing.JLabel jLabel75;
    public javax.swing.JLabel jLabel76;
    public javax.swing.JLabel jLabel77;
    public javax.swing.JLabel jLabel78;
    public javax.swing.JLabel jLabel79;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel80;
    public javax.swing.JLabel jLabel81;
    public javax.swing.JLabel jLabel84;
    public javax.swing.JLabel jLabel86;
    public javax.swing.JLabel jLabel87;
    public javax.swing.JLabel jLabel88;
    public javax.swing.JLabel jLabel89;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JLabel jLabel90;
    public javax.swing.JLabel jLabel91;
    public javax.swing.JLabel jLabel93;
    public javax.swing.JLabel jLabel94;
    public javax.swing.JLabel jLabel95;
    public javax.swing.JLabel jLabel96;
    public javax.swing.JLabel jLabel97;
    public javax.swing.JLabel jLabel98;
    public javax.swing.JLabel jLabel99;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane10;
    public javax.swing.JScrollPane jScrollPane11;
    public javax.swing.JScrollPane jScrollPane12;
    public javax.swing.JScrollPane jScrollPane14;
    public javax.swing.JScrollPane jScrollPane19;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JScrollPane jScrollPane7;
    public javax.swing.JScrollPane jScrollPane8;
    public javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField jTextField13;
    public javax.swing.JTextField jTextField25;
    public javax.swing.JTextField jTextField29;
    public javax.swing.JTextField jTextField31;
    public javax.swing.JTextField jTextField32;
    public javax.swing.JTextField jTextField33;
    public javax.swing.JTextField jTextField34;
    public javax.swing.JTextField jTextField35;
    public javax.swing.JTextField jTextField36;
    public javax.swing.JTextField jTextField37;
    public javax.swing.JTextField jTextField38;
    public javax.swing.JTextField jTextField39;
    public javax.swing.JTextField jTextField40;
    public javax.swing.JTextField jTextField41;
    public javax.swing.JTextField jTextField44;
    public javax.swing.JTextField jTextField45;
    public javax.swing.JTextField jTextField47;
    public javax.swing.JTextField jTextField50;
    public javax.swing.JTextField jTextField51;
    public javax.swing.JTextField jTextField52;
    public javax.swing.JTextField jTextField53;
    public static javax.swing.JTextField jobPlace;
    public javax.swing.JLabel label1;
    public javax.swing.JLabel label10;
    public javax.swing.JLabel label11;
    public javax.swing.JLabel label12;
    public javax.swing.JLabel label13;
    public javax.swing.JLabel label14;
    public javax.swing.JLabel label2;
    public javax.swing.JLabel label3;
    public javax.swing.JLabel label4;
    public javax.swing.JLabel label5;
    public javax.swing.JLabel label6;
    public javax.swing.JLabel label7;
    public javax.swing.JLabel label8;
    public javax.swing.JLabel label9;
    public javax.swing.JLabel light_lbl;
    public javax.swing.JLabel lock_lbl;
    public javax.swing.JPanel logoPanel;
    public javax.swing.JTextField mand;
    public javax.swing.JTable mandob;
    public static javax.swing.JTextField mandobCardName;
    public javax.swing.JButton mandobClients;
    public static javax.swing.JTextField mandobCollectionRate;
    public javax.swing.JButton mandobCompletedCollections;
    public static javax.swing.JTextField mandobId;
    public static javax.swing.JTextField mandobLocation;
    public static javax.swing.JTextField mandobMobile;
    public static javax.swing.JTextField mandobName;
    public javax.swing.JPanel mandobPanel;
    public javax.swing.JPanel mandobPanel1;
    public javax.swing.JButton mandobReceivedItems;
    public javax.swing.JButton mandobRemainingCollections;
    public javax.swing.JButton mandobResevedMoney;
    public javax.swing.JPanel markerPanel1;
    public javax.swing.JPanel markerPanel10;
    public javax.swing.JPanel markerPanel11;
    public javax.swing.JPanel markerPanel12;
    public javax.swing.JPanel markerPanel13;
    public javax.swing.JPanel markerPanel14;
    public javax.swing.JPanel markerPanel2;
    public javax.swing.JPanel markerPanel3;
    public javax.swing.JPanel markerPanel4;
    public javax.swing.JPanel markerPanel5;
    public javax.swing.JPanel markerPanel6;
    public javax.swing.JPanel markerPanel7;
    public javax.swing.JPanel markerPanel8;
    public javax.swing.JPanel markerPanel9;
    public static javax.swing.JTextField mob1;
    public static javax.swing.JTextField mob2;
    public javax.swing.JPanel notePanel;
    public javax.swing.JPanel notePanel1;
    public javax.swing.JPanel notePanel2;
    public javax.swing.JPanel notePanel3;
    public javax.swing.JPanel notePanel4;
    public javax.swing.JTable notesTable;
    public javax.swing.JLabel online_icon;
    public javax.swing.JPanel pane1;
    public javax.swing.JPanel pane10;
    public javax.swing.JPanel pane11;
    public javax.swing.JPanel pane12;
    public javax.swing.JPanel pane13;
    public javax.swing.JPanel pane14;
    public javax.swing.JPanel pane2;
    public javax.swing.JPanel pane3;
    public javax.swing.JPanel pane4;
    public javax.swing.JPanel pane5;
    public javax.swing.JPanel pane6;
    public javax.swing.JPanel pane7;
    public javax.swing.JPanel pane8;
    public javax.swing.JPanel pane9;
    public javax.swing.JTextField password;
    public javax.swing.JTable qest;
    public javax.swing.JPanel reciptPanel;
    public javax.swing.JPanel reciptPanel1;
    public javax.swing.JPanel reciptPanel2;
    public javax.swing.JPanel reciptPanel3;
    public javax.swing.JPanel reciptPanel4;
    public javax.swing.JTable reciptT;
    public javax.swing.JTable recivedMonyT;
    public javax.swing.JPanel reportPanel;
    public javax.swing.JComboBox<String> reportType;
    public javax.swing.JComboBox<String> reportType1;
    public javax.swing.JTable reports;
    public javax.swing.JPanel returnPanel;
    public javax.swing.JPanel returnPanel1;
    public javax.swing.JPanel returnPanel2;
    public javax.swing.JTable returnTable;
    public javax.swing.JPanel sideBorderPanel;
    public javax.swing.JPanel sidePanel;
    public javax.swing.JPanel stockPanel;
    public javax.swing.JPanel stockPanel1;
    public javax.swing.JPanel stockPanel2;
    public javax.swing.JTable stock_table;
    public javax.swing.JTextField text1_1;
    public javax.swing.JTextField text1_2;
    public javax.swing.JTextField text1_3;
    public javax.swing.JTextField text1_4;
    public javax.swing.JTextField text1_5;
    public javax.swing.JTextField text1_6;
    public javax.swing.JTextField text1_7;
    public javax.swing.JTextField text1_8;
    public javax.swing.JPanel titlePanel;
    public javax.swing.JLabel totalSalesPrice;
    public javax.swing.JCheckBox user1;
    public javax.swing.JCheckBox user10;
    public javax.swing.JCheckBox user11;
    public javax.swing.JCheckBox user12;
    public javax.swing.JCheckBox user2;
    public javax.swing.JCheckBox user3;
    public javax.swing.JCheckBox user4;
    public javax.swing.JCheckBox user5;
    public javax.swing.JCheckBox user6;
    public javax.swing.JCheckBox user7;
    public javax.swing.JCheckBox user8;
    public javax.swing.JCheckBox user9;
    public javax.swing.JTextField userName;
    public javax.swing.JTable userT;
    public javax.swing.JLabel user_lbl;
    public javax.swing.JPanel usersPanel;
    // End of variables declaration//GEN-END:variables
}
