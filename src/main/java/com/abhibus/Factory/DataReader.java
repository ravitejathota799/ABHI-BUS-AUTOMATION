package com.abhibus.Factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader {
	public static FileInputStream fi; // declaring fileinput stream variable
	public static FileOutputStream fo; // declaring fileoutput stream variable
	public static XSSFWorkbook wb; // declaring workbook variable
	public static XSSFSheet ws; // declaring sheet variable
	public static XSSFRow row; // declaring row variable
	public static XSSFCell cell; // declaring cell variable

	// method for getting the row count using xlfile and xlsheet
	public static int getRowCount(String xlfile, String xlsheet) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		int rowcount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;
	}

	// method for getting the cell count using xlfile and xlsheet
	public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		int cellcount = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}

	// method for getting the data using xlfile, xlsheet,rownumber,column number
	public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);

		String data;
		try {
			// data=cell.toString();

			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
			return data;
		} catch (Exception e) {
			data = "";
		}

		wb.close();
		fi.close();
		return data;
	}

	// method for setting the data using xlfile, xlsheet,rownumber,column number
	public static void setCellData(String xlfile, String xlsheet, String rownum, int colnum, String data)
			throws IOException {
		try {
			fi = new FileInputStream(xlfile);
			wb = new XSSFWorkbook(fi);
			ws = wb.getSheet(xlsheet);
			row = ws.getRow(Integer.parseInt(rownum));
			if (row == null) {
				row = ws.createRow(Integer.parseInt(rownum));
				cell = row.createCell(colnum);
				cell.setCellValue(data);
				fo = new FileOutputStream(xlfile);
				wb.write(fo);

			} else {
				cell = row.createCell(colnum);
				cell.setCellValue(data);
				fo = new FileOutputStream(xlfile);
				wb.write(fo);
			}
			wb.close();
			fi.close();
			fo.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
