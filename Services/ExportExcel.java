package Main.Services;

import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.IncomeDetail;
import Main.Entity.Element.IncomeReport;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ExportExcel {
    public void exportXLSX(IncomeReport i, ObservableList<IncomeDetail> list) {
        DAO dao = new DAO();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Income Report");


        CellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 20); // font size
        cellStyleTitle.setFont(font);

        CellStyle cellNormal = workbook.createCellStyle();
        cellNormal.setAlignment(HorizontalAlignment.CENTER);
        Font font2 = sheet.getWorkbook().createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14); // font size
        cellNormal.setFont(font2);

        CellStyle cellHead = workbook.createCellStyle();
        cellNormal.setAlignment(HorizontalAlignment.CENTER);
        Font font3 = sheet.getWorkbook().createFont();
        font3.setFontName("Times New Roman");
        font3.setBold(true);
        font3.setFontHeightInPoints((short) 14); // font size
        cellHead.setFont(font3);


//        Heading
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
        cell.setCellStyle(cellStyleTitle);
        cell.setCellValue("Income Report");
        String[] arr = {"Report ID", "Order Date", "Create By", "Employee's Name","Status", "Supplier"};

//      Body
        int rowIndex = 2;
        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(cellHead);
        cell.setCellValue(arr[0]);
        cell = row.createCell(1);
        cell.setCellStyle(cellNormal);
        cell.setCellValue(i.getReportId());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(cellHead);
        cell.setCellValue(arr[1]);
        cell = row.createCell(1);
        cell.setCellStyle(cellNormal);
        cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(i.getOrderDate()));

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(cellHead);
        cell.setCellValue(arr[2]);
        cell = row.createCell(1);
        cell.setCellStyle(cellNormal);
        cell.setCellValue(i.getEmployeeIdCreate());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(cellHead);
        cell.setCellValue(arr[3]);
        cell = row.createCell(1);
        cell.setCellStyle(cellNormal);
        try {
            cell.setCellValue(dao.findEmployeeNameById(i.getEmployeeIdCreate()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(cellHead);
        cell.setCellValue(arr[4]);
        cell = row.createCell(1);
        cell.setCellStyle(cellNormal);
        cell.setCellValue(i.getStatus());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(cellHead);
        cell.setCellValue(arr[5]);
        cell = row.createCell(1);
        cell.setCellStyle(cellNormal);
        cell.setCellValue(i.getSupplier());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(cellHead);
        cell.setCellValue("Number");
        cell = row.createCell(1);
        cell.setCellStyle(cellHead);
        cell.setCellValue("Ingredient ID");
        cell = row.createCell(2);
        cell.setCellStyle(cellHead);
        cell.setCellValue("Ingredient Name");
        cell = row.createCell(3);
        cell.setCellStyle(cellHead);
        cell.setCellValue("Order Quantity");
        cell = row.createCell(4);
        cell.setCellStyle(cellHead);
        cell.setCellValue("Receive Quantity");


        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index + 9);
            cell = row.createCell(0);
            cell.setCellStyle(cellNormal);
            cell.setCellValue(index + 1);

            cell = row.createCell(1);
            cell.setCellStyle(cellNormal);
            cell.setCellValue(list.get(index).getIngredientChoice().getIngredientId());

            cell = row.createCell(2);
            cell.setCellStyle(cellNormal);
            try {
                cell.setCellValue(dao.findIngredientById(list.get(index).getIngredientChoice().getIngredientId())
                        .getIngredientName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            cell = row.createCell(3);
            cell.setCellStyle(cellNormal);
            cell.setCellValue(list.get(index).getOrderQty());

            cell = row.createCell(4);
            cell.setCellStyle(cellNormal);
            cell.setCellValue(list.get(index).getReceiveQty());

        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);


        File file = new File("IncomeReport.xlsx");
        Alert alert;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            workbook.close();
            System.out.println("Success");
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Export Successfully");
            alert.showAndWait();
        } catch (IOException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Export Successfully");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        ExportExcel e = new ExportExcel();
//        IncomeReport i = new IncomeReport("HD002", new Date(2022, 11, 1),
//                "HU562", "Dalat Farm", "Confirmed");
//        ObservableList<IncomeDetail> list = FXCollections.observableArrayList(
//                new IncomeDetail(new Ingredient("CF01", "Coffee", "Ca phe hat"), 12)
//        );
//        e.exportXLSX(i, list);
//    }

}
