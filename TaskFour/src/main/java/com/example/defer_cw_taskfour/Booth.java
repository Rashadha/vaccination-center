package com.example.defer_cw_taskfour;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Booth {
    private String boothId;

    public void setData(String boothId)
    {
        this.boothId = boothId;
    }
    public String[] takePatientNamesFromExcelSheet(XSSFWorkbook workbook, String[] serviceCenter, int i)
    {
        XSSFSheet sheet = workbook.getSheet(boothId);
        int maxRow = sheet.getLastRowNum();
        XSSFRow row = sheet.getRow(maxRow);
        XSSFCell cellOne = row.getCell(7);
        XSSFCell cellTwo = row.getCell(1);

        if (Objects.equals(cellOne.getStringCellValue(), "__"))
        {
            serviceCenter[i] = cellTwo.getStringCellValue();
        }
        else if(Objects.equals(cellOne.getStringCellValue(), "vaccinated"))
        {
            serviceCenter[i] = "e";
        }
        else if (Objects.equals(cellOne.getStringCellValue(), "vaccinatedOrNot"))
        {
            serviceCenter[i] = "e";
        }
        return serviceCenter;
    }
    public void storeProgramDataIntoRelatedBoothFile(XSSFWorkbook workbook, Object[] patientDetails,String excelFilePath) throws IOException {
        XSSFSheet sheet = workbook.getSheet(boothId);
        XSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);

        XSSFCell cell = row.createCell(0);
        XSSFCell cellTwo = row.createCell(1);
        XSSFCell cellThree = row.createCell(2);
        XSSFCell cellFour = row.createCell(3);
        XSSFCell cellFive = row.createCell(4);
        XSSFCell cellSix = row.createCell(5);
        XSSFCell cellSeven = row.createCell(6);
        XSSFCell cellEight = row.createCell(7);

        cell.setCellValue(sheet.getLastRowNum());
        cellTwo.setCellValue((String)patientDetails[0]);
        cellThree.setCellValue((String) patientDetails[1]);
        cellFour.setCellValue((Integer)patientDetails[2]);
        cellFive.setCellValue((String) patientDetails[3]);
        cellSix.setCellValue((String)patientDetails[4]);
        cellSeven.setCellValue((String) patientDetails[5]);
        cellEight.setCellValue("__");

        FileOutputStream outStream = new FileOutputStream(excelFilePath);
        workbook.write(outStream);
        outStream.close();
    }

    public void storeProgramDataIntoRelatedBoothFile2(XSSFWorkbook workbook, String excelFilePath) throws IOException
    {
        XSSFSheet sheet = workbook.getSheet(boothId);
        XSSFRow row = sheet.getRow(sheet.getLastRowNum());
        XSSFCell cell = row.getCell(7);
        cell.setCellValue("vaccinated");
        FileOutputStream outStream = new FileOutputStream(excelFilePath);
        workbook.write(outStream);
        outStream.close();
    }
    public Object[][] toTakeDataOutFromBoothFile(XSSFWorkbook workbook)
    {
        XSSFSheet sheet = workbook.getSheet(boothId);
        int maxRow = sheet.getLastRowNum();
        Object[][] detailArray = new Object[maxRow][];
        for (int i = 1; i<=maxRow; i++)
        {
            XSSFRow row = sheet.getRow(i);
            Object[] recordArray = new Object[8];
            for (int j = 0; j<8; j++)
            {
                XSSFCell cell = row.getCell(j);
                switch(cell.getCellType())
                {
                    case STRING: recordArray[j]=(cell.getStringCellValue()); break;
                    case NUMERIC: recordArray[j]=((int)cell.getNumericCellValue()); break;
                    case BOOLEAN: recordArray[j]=(cell.getBooleanCellValue()); break;
                }
            }
            detailArray[i-1] = recordArray;
        }
        return detailArray;
    }
    public String[] getPatientNamesFromParticularBooth(XSSFWorkbook workbook)
    {
        XSSFSheet sheet = workbook.getSheet(boothId);
        int maxRow = sheet.getLastRowNum();
        String[] patientNamesArray = new String[maxRow];
        for (int i =1; i<=maxRow; i++)
        {
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(1);
            patientNamesArray[i-1] = cell.getStringCellValue().toLowerCase(Locale.ROOT);
        }
        return patientNamesArray;
    }
}
