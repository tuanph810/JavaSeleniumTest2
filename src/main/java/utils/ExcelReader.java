package utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelReader {
    /**
     *
     */
    private String path = "";

    /**
     *
     */
    private boolean hasHeader = true;

    /**
     *
     */
    private Iterator<Row> rowIterator;

    /**
     *
     */
    public ExcelReader(String path) {
        this.path = path;
    }

    /**
     *
     */
    public ExcelReader(String path, boolean hasHeader) {
        this.path = path;
        this.hasHeader = hasHeader;
    }

    /**
     * @throws IOException
     */
    public void open() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(this.path));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        rowIterator = sheet.iterator();

        if (this.hasHeader && rowIterator.hasNext()) rowIterator.next();
    }

    /**
     * @return
     */
    public Row readRow() {
        if (hasNext()) return rowIterator.next();

        return null;
    }

    /**
     * @return
     */
    public boolean hasNext() {
        return rowIterator.hasNext();
    }
}
