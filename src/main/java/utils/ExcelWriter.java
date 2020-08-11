package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ExcelWriter {
    /**
     *
     */
    private String[] columns;

    /**
     *
     */
    private XSSFWorkbook workbook = null;

    /**
     *
     */
    private XSSFSheet sheet = null;

    /**
     *
     */
    private int currentRow = 0;

    /**
     * @param columns
     */
    public ExcelWriter(String[] columns) {
        this.columns = columns;

        this.initial();
    }

    /**
     * @param workbook
     * @return
     */
    private XSSFCellStyle titleStyle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    /**
     *
     */
    public void initial() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Report");

        Row row;
        Cell cell;

        XSSFCellStyle style = titleStyle(workbook);

        row = sheet.createRow(currentRow);

        for (int i = 0; i < this.columns.length; i++) {
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(this.columns[i]);
            cell.setCellStyle(style);
        }
    }

    /**
     * @param values
     * @return
     */
    public boolean write(String[] values) {
        if (values.length != this.columns.length) {
            System.out.println("Number of columns and number of values not matched");

            return false;
        }

        Row row = sheet.createRow(++currentRow);

        for (int i = 0; i < values.length; i++) {
            Cell cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(values[i]);
        }

        return true;
    }

    /**
     * @return
     */
    public String save() {
        try {
            File file = new File("src/main/resources/Reports/" + Calendar.getInstance().getTimeInMillis() + ".xlsx");
            file.getParentFile().mkdirs();

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);

            return file.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());

            return null;
        }
    }
}
