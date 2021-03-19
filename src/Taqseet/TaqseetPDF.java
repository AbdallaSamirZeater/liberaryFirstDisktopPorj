package Taqseet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;

public class TaqseetPDF {

    static PdfPTable mainTable = new PdfPTable(1);
    static Image image;//Header Image
    static String header = " شركة عباد الرحمن";
    static String information = "مخزن التوزيع / الاسماعيلية شارع العشريني - ت: 01277435880 - الفرع الرئيسي القاهرة - ت: 01117227448 الرجاء الاحتفاظ بالايصالات \nالبضاعة المباعة لا ترد ولا تستبدل بعد 14 يوما من تاريخ الشراء طبقا لقانون الغرفة التجارية 67 لسنة 2016 و ان تكون في حالتها الأصلية";

    public static PdfPTable createRow(String s[]) {
        PdfPTable tempTable = new PdfPTable(s.length);
        for (int i = 0; i < s.length; i++) {
            tempTable.addCell(getIRDCell(s[i]));
        }
        return tempTable;
    }

    public static void tableAppendRow(PdfPTable smallTable) {
        // mainTable.addCell(getIRHCell("شركة عباد الرحمن", PdfPCell.ALIGN_CENTER));
        PdfPCell tempTable = new PdfPCell(smallTable);
        tempTable.setBorder(0);
        mainTable.addCell(tempTable);

    }

    public static void makeRec(String body[][]) {
        mainTable.setWidthPercentage(100);

        PdfPTable tempTable = null;
        for (int i = 0; i < body.length; i++) {
            for (int j = 0; j < body[i].length; j++) {
                tempTable = createRow(body[i]);
                //System.out.println(body[i].length);
            }
            tableAppendRow(tempTable);
        }
        mainTable.addCell(getIRHCell(" ", PdfPCell.ALIGN_CENTER));
    }

    public static void makeHeader(String []arr) {
        String clientId = arr[0] , area = arr[1], itemName = arr[2], paid= arr[3],rest= arr[4],qestNum= arr[5];
        
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 14, Font.BOLD);
        fs.addFont(font);

        PdfPCell cell = new PdfPCell();
        PdfPTable tempTable = new PdfPTable(3);
        Phrase phrase = fs.process(arr[0]);
        cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(7.0f);
        cell.setBorderWidth(2);
        cell.setBorderColor(BaseColor.BLACK);
        tempTable.addCell(cell);
        //phrase = fs.process(header);

        cell = new PdfPCell();
        cell.setRowspan(3);
        cell.setPaddingTop(12);
        //cell.setMinimumHeight(60);
        Paragraph p1 = new Paragraph(header, FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 24, Font.BOLD));

        Paragraph p2 = new Paragraph("شاشات - لابات - أجهزة كهربائية - موبايلات", FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 11, Font.BOLD));
        cell.setPaddingRight(20);
        cell.setPaddingTop(5);
        cell.addElement(p1);
        cell.addElement(p2);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderWidth(2);

        tempTable.addCell(cell);

        phrase = fs.process(arr[1]);
        cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(7.0f);
        cell.setBorderWidth(2);
        cell.setBorderColor(BaseColor.BLACK);
        tempTable.addCell(cell);

        phrase = fs.process(arr[2]);
        cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(7.0f);
        cell.setBorderWidth(2);
        cell.setBorderColor(BaseColor.BLACK);
        tempTable.addCell(cell);

        phrase = fs.process(arr[3]);
        cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(7.0f);
        cell.setBorderWidth(2);
        cell.setBorderColor(BaseColor.BLACK);
        tempTable.addCell(cell);

        phrase = fs.process(arr[4]);
        cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(7.0f);
        cell.setBorderWidth(2);
        cell.setBorderColor(BaseColor.BLACK);
        tempTable.addCell(cell);

        phrase = fs.process(arr[5]);
        cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(7.0f);
        cell.setBorderWidth(2);
        cell.setBorderColor(BaseColor.BLACK);
        tempTable.addCell(cell);
        PdfPCell tTable = new PdfPCell(tempTable);
        tTable.setBorderWidth(2);
        mainTable.addCell(tTable);
    }

    public static void fullRec(String values[]) {
        float pursh;
        String paid = values[3], rest = values[4], cNum = values[0], qTotal = values[5], qOrder = values[8], payment = values[11];
        String qNum = values[0], area = values[1], item = values[2], cName = values[6], mobile = values[7], purchaseDate = values[14], currentDate = values[17];
        String address = values[9], nationalID = values[10], workPlace = values[12], job = values[13];
        String mandobSelling = values[15], mandobCollecting = values[16];
        String strLeft1 ,strLeft11,strLeft111, strRight1,strRight11,strRight111, strLLeft2, strLeft2, strMid2, strRight2, strLeft3, strRight3, strMid4, strLeft4, strRight4, strLeftMid5, strRightMid5, strLeft5, strRight5;
        String Statement, s1 = "بموجب هذا الايصال اتعهد بدفع   ", s2 = " جنيه ﻓﻲ ﺍﻟﺘﺎﺭﻳﺦ ﺍﻟﻤﺤﺪﺩ.. ﻋﻠﻤﺎ ﺑﺄﻥ ﺍﻟﻘﻴﻤﺔ ﻭﺻﻠﺘﻨﺎ ﺑﻀﺎﻋﺔ ﻭ ﺍﻟﺪﻓﻊ ﻭ ﺍﻟﺘﻘﺎﺿﻲ ﻭﻓﻘﺎ ﻹﺭﺍﺩﺓ ﺍﻟﺪﺍﺋﻦ ﻭ ﻟﻠﺪﺍﺋﻦ ﺍﻟﺤﻖ ﻓﻲ ﺗﺤﻮﻳﻞ ﻫﺬﻩ ﺍﻟﻜﻤﺒﻴﺎﻟﺔ ﻟﻤﻦ ﻳﺸﺎﺀ ﺩﻭﻥ ﺍﻟﺮﺟﻮﻉ ﺇﻟﻲ  ";
        Statement = s1 + payment + s2;
        pursh = Float.parseFloat(paid) + Float.parseFloat(rest);
        strLeft1 = "المدفوع : " + paid ;
        strLeft11 = "المتبقي : " + rest ;
        strLeft111 = "عدد الأقساط : " + qNum;
        strRight1 = "المنطقة : " + area ;
        strRight11="رقم العميل : " + cNum ;
        strRight111="نوع المنتج : " + item;
        strLLeft2 = "قيمة القسط : " + payment;
        strLeft2 = "القسط رقم : " + qOrder + " من " + qTotal + " قسط";
        strMid2 = "رقم الموبايل : " + mobile;
        strRight2 = "الإسم : " + cName;
        strLeft3 = "الرقم القومي : " + nationalID;
        strRight3 = "العنوان : " + address;
        strLeft4 = "قيمة الشراء : " + pursh;
        strMid4 = "الوظيفة : " + job;
        strRight4 = "العمل : " + workPlace;
        strLeft5 = "تاريخ التحصيل : " + currentDate;
        strLeftMid5 = "اسم المحصل : " + mandobCollecting;
        strRightMid5 = "اسم البائع : " + mandobSelling;
        strRight5 = "تاريخ الشراء : " + purchaseDate;
        makeHeader(new String[] {strLeft1,strRight11,strLeft11,strRight1,strLeft111,strRight111});
        makeRec(new String[][]{{strLeft2, strMid2, strRight2}, {strLLeft2, strLeft3, strRight3, "", "", "", "", ""}, {strLeft4, strMid4, strRight4, "", "", "", "", ""}, {Statement}, {strLeft5, strLeftMid5, strRightMid5, strRight5,"" ,"","","","",""}, {information}});

    }

    public static void main(String[] args) {

        
       // String pdfFilename = "C:\\Users\\hamada\\Desktop\\Qst.pdf";
       String pdfFilename = "D:\\Qst-" + Home.Date.getText()+".pdf";
        TaqseetPDF generateInvoice = new TaqseetPDF();
        generateInvoice.createPDF(pdfFilename);

        try {
            Desktop.getDesktop().open(new File(pdfFilename));
        } catch (Exception ex) {
            System.out.println("Can't be opened");
        }

    }

    public static void createPDF(String pdfFilename) {

        try {

            OutputStream file = new FileOutputStream(new File(pdfFilename));
            Document document = new Document();
            document.setMargins(1, 1, 0, 0);
            PdfWriter.getInstance(document, file);

            //Inserting Image in PDF
            //image = Image.getInstance("src/images/mini_mini_logo.png");
            document.open();//PDF document opened........

            //document.add(image);
            document.add(mainTable);
            document.close();

            file.close();

            System.out.println("Pdf created successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setPadding(2);
        cell.setFixedHeight(18);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getIRDCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 14, Font.BOLD);
        if (text.equals(header)) {
            font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 24, Font.BOLD);
        }
        if (text.equals(information)) {
            font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 12, Font.BOLD);
        }
        if (text.contains("الموبايل")) {
            font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 13, Font.BOLD);
        }
        if (text.contains("اسم البائع") || text.contains("اسم المحصل") || text.contains("تاريخ التحصيل : ") || text.contains("تاريخ الشراء : ")) {
            font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 12, Font.BOLD);
        }
        if (text.contains("تاريخ التحصيل : ") || text.contains("تاريخ الشراء : ")) {
            font = FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 10, Font.BOLD);
        }
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6.0f);
        cell.setPaddingLeft(1.0f);
        cell.setPaddingRight(1.0f);
        cell.setBorderWidth(2);
        // cell.setBackgroundColor(new java.awt.Color(255, 255, 255));
        cell.setBorderColor(BaseColor.BLACK);
        if (text.equals(header)) {
            cell.setPaddingTop(12);
            cell.setMinimumHeight(60);
            //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // image.scaleAbsolute(55f, 30f);
            //cell.addElement(image);
            Paragraph p = new Paragraph("شاشات - لابات - أجهزة كهربائية - موبايلات", FontFactory.getFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 11, Font.BOLD));
            cell.setRowspan(2);
            cell.addElement(phrase);
            cell.setPaddingRight(20);
            cell.addElement(p);

        }
//        if (text.contains("المنطقة") || text.contains("المقدم") || text.equals(information) || text.equals(Statement)) {
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        }
        if (text.contains("العمل :")) {
            cell.setColspan(5);
        }
        if (text.contains("اسم المحصل : ")||text.contains("اسم البائع : ")||text.contains("الرقم القومي") || text.contains("العنوان") || text.contains("الوظيفة") || text.contains("قيمة الشراء")) {
            cell.setColspan(3);
        }
        if (text.contains("تاريخ التحصيل : ") || text.contains("تاريخ الشراء : ")||text.contains("قيمة القسط :") || text.contains("قيمة الشراء :")) {
            cell.setColspan(2);
            cell.setPaddingLeft(1.0f);
            cell.setPaddingRight(1.0f);
        }


        return cell;
    }

}
