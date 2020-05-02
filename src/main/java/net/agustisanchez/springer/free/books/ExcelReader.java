package net.agustisanchez.springer.free.books;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ExcelReader {

    private List<String> langList = new ArrayList<>();

    private List<String> categoryList = new ArrayList<>();

    private InputStream input;

    public ExcelReader(InputStream input) {
        this.input = input;
    }

    ExcelReader langs(List<String> langs) {
        langs.forEach(l -> langList.add(l.toUpperCase()));
        return this;
    }

    ExcelReader categories(List<String> categories) {
        categories.forEach(c -> categoryList.add(c.toUpperCase()));
        return this;
    }

    List<Book> read() throws Exception {

        int titleIndex = 0;
        int langCodeIndex = 8;
        int categoryIndex = 11;
        int isbnIndex = 14;
        int urlIndex = 17;


        Workbook workbook = new XSSFWorkbook(input);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        return StreamSupport.stream(datatypeSheet.spliterator(), false)
                // Skip header row
                .skip(1)
                .filter(row -> (categoryList.isEmpty() || categoryList.stream().anyMatch(i -> cell(categoryIndex, row).toUpperCase().contains(i))))
                .filter(row -> (langList.isEmpty() || langList.contains(cell(langCodeIndex, row).toUpperCase())))
                .map(row -> new Book(cell(titleIndex, row), cell(isbnIndex, row), cell(urlIndex, row)))
                .collect(Collectors.toList());
    }

    private String cell(int titleIndex, Row row) {
        return row.getCell(titleIndex).getStringCellValue();
    }
}
