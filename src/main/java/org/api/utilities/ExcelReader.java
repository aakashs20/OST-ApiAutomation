package org.api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;

public class ExcelReader {

	public static List<LinkedHashMap<String, String>> getExcelDataAsListOfMap(String excelFileName, String sheetName)
			throws IOException {

		List<LinkedHashMap<String, String>> dataFromExcel = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(new File("src/test/resources/testData/" + excelFileName + ".xlsx"));
		Sheet sheet = workbook.getSheet(sheetName);

		int totalRows = sheet.getPhysicalNumberOfRows();
		LinkedHashMap<String, String> mapData;
		List<String> allKeys = new ArrayList<>();
		DataFormatter dataFormatter = new DataFormatter();

		for (int i = 0; i < totalRows; i++) {
			mapData = new LinkedHashMap<>();

			if (i == 0) {
				int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
				for (int j = 0; j < totalCols; j++) {
					allKeys.add(sheet.getRow(0).getCell(j).getStringCellValue());
				}

			} else {
				int totalCols = sheet.getRow(i).getPhysicalNumberOfCells();
				for (int j = 0; j < totalCols; j++) {
					String cellValue = dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
					mapData.put(allKeys.get(j), cellValue);
				}
				dataFromExcel.add(mapData);
			}
		}
		return dataFromExcel;
	}

	public Map<String, String> testDataMap(String filePath, String sheetName, String filterColumn, String filterValue)
			throws IOException {

		File file = new File(filePath);
		FileInputStream fis = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Map<String, String> data = new LinkedHashMap<>();

		try {
			fis = new FileInputStream(file); // Opening the Excel file
			workbook = WorkbookFactory.create(fis); // Creating workbook from FileInputStream
			sheet = workbook.getSheet(sheetName); // Getting the specified sheet from workbook

			int rowCount = sheet.getLastRowNum(); // Getting the last row number
			int colCount = sheet.getRow(0).getLastCellNum(); // Getting the last column number of the first row

			// Identify the index of the filter column
			int filterColumnIndex = -1;
			for (int i = 0; i < colCount; i++) {
				Cell cell = sheet.getRow(0).getCell(i);
				if (cell != null && cell.toString().equalsIgnoreCase(filterColumn)) {
					filterColumnIndex = i;
					break;
				}
			}

			// Validate filter column index
			if (filterColumnIndex == -1) {
				throw new IllegalArgumentException("Filter column not found: " + filterColumn);
			}

			// Iterate through rows and apply filter
			for (int i = 1; i <= rowCount; i++) {
				Row row = sheet.getRow(i); // Getting the current row
				if (row != null) {
					Cell filterCell = row.getCell(filterColumnIndex);
					if (filterCell != null && filterCell.toString().equalsIgnoreCase(filterValue)) {
						for (int j = 0; j < colCount; j++) { // Iterating over columns
							Cell cell = row.getCell(j); // Getting cell at specified row and column
							Cell headerCell = sheet.getRow(0).getCell(j);
							String header = headerCell != null ? headerCell.toString() : "";
							data.put(header, cell != null ? cell.toString() : ""); // Adding cell value to the map
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace(); // Printing stack trace
			throw e; // Rethrowing exception for further handling
		} catch (Exception ex) { // Catching any other exceptions
			ex.printStackTrace(); // Printing stack trace
		} finally {
			if (fis != null) { // Checking if FileInputStream is not null
				fis.close(); // Closing FileInputStream
			}
			if (workbook != null) {
				workbook.close(); // Closing workbook
			}
		}

		return data;
	}

}
