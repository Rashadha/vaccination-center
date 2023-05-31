import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class ServiceCenterExample
{
    private static Scanner input = new Scanner(System.in);
    private static int boothNum;
    private static String patientName;

    private String excelFilePathOne = "D:\\Java_codes\\Defer_CW_TaskOne\\src\\PatientDetails.xlsx";
    private String excelFilePathTwo = "D:\\Java_codes\\Defer_CW_TaskOne\\src\\VaccineDetails.xlsx";
    private FileInputStream inputStreamOne =  new FileInputStream(excelFilePathOne);
    private FileInputStream inputStreamTwo =  new FileInputStream(excelFilePathTwo);
    private XSSFWorkbook workbookOne = new XSSFWorkbook(inputStreamOne);
    private XSSFWorkbook workbookTwo = new XSSFWorkbook(inputStreamTwo);
    private final String[] sheetArray = {"BoothZero","BoothOne","BoothTwo","BoothThree","BoothFour","BoothFive"};
    private static String[] ServiceCenter = new String[6];

    public ServiceCenterExample() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        int exitOption = 0;
        // this will print the menu until you enter the exit option
        while (exitOption != 1)
        {
            System.out.println("View all Vaccination Booths-->press 1\n" +
                    "View all Empty Booths-->press 2\n" +
                    "Add Patient to a Booth-->press 3\n" +
                    "Remove Patient from a Booth-->press 4\n" +
                    "View Patients Sorted in alphabetical order-->press 5\n" +
                    "Load Program Data from file-->press 6\n" +
                    "View Remaining Vaccinations-->press 7\n" +
                    "Add Vaccinations to the Stock-->press 8");
            ServiceCenterExample serCenter = new ServiceCenterExample();
            int menuOption = serCenter.selectMenuOption();
            if (menuOption == 1)
            {
                serCenter.viewAllVaccinationBooths();
                serCenter.printVaccinationBoothDetails();
            } else if (menuOption == 2)
            {
                serCenter.viewAllEmptyBooths();
            }
            else if (menuOption == 3)
            {
                serCenter.addPatientToABooth();
            }
            else if (menuOption == 4)
            {
                serCenter.removePatientFromABooth();
            }
            else if (menuOption == 5)
            {
                int sortOption = serCenter.enterSortingOption();
                if (sortOption == 1)
                {
                    serCenter.viewPatientsSortedInAlphabeticalOrder();
                }
                else
                {
                    serCenter.viewPatientsInAlphabeticalOrderBasedOnTheBoothNumber();
                }
            }
            else if (menuOption == 6)
                serCenter.loadProgramDataFromFile();
            else if (menuOption == 7)
                serCenter.viewRemainingVaccinations();
            else
                serCenter.addVaccinationsToTheStock();
            System.out.println("If you want to exit --> press 1\nIf not --> press 2");
            exitOption = serCenter.checkToStoreProgramDataOrToExit();
        }

    }

    public int selectMenuOption()
    {
        // to select the user preferred menu option
        int menuOption = 0;
        while(menuOption!=1 && menuOption!=2 && menuOption!=3 && menuOption!=4 && menuOption!=5 && menuOption!=6 && menuOption!=7 && menuOption!=8)
        {
            System.out.println("Enter your preference: ");
            try
            {
                menuOption = input.nextInt();
                if (menuOption!=1 && menuOption!=2 && menuOption!=3 && menuOption!=4 && menuOption!=5 && menuOption!=6 && menuOption!=7 && menuOption!=8)
                    throw new Exception("Enter the correct menu option");
            }
            catch (InputMismatchException e )
            {
                System.out.println("Only integers are allowed");

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            finally
            {
                input.nextLine();
            }
        }
        return menuOption;
    }
    public int enterSortingOption()
    {
        // to enter the sort option
        int sortOption = 0;
        System.out.println("If You want to sort current patients' names --> press 1\nIf you want to sort patients' names according to a particular --> press 2");
        while (sortOption != 1 &&  sortOption != 2 ) {
            System.out.print("Enter your preference: ");
            try {
                sortOption = input.nextInt();
                if (sortOption != 1 && sortOption != 2) {
                    throw new Exception("Please, enter an integer 1 or 2");
                }
            } catch (InputMismatchException e) {
                System.out.println("Only integers are allowed(1 or 2)");

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                input.nextLine();
            }
        }
        return sortOption;
    }

    public void viewAllVaccinationBooths() {

        // this array is to take the actual serviceCenter array this will run for 6 times since there are six booths
        for (int i = 0; i<6; i++)
        {
            XSSFSheet sheet = workbookOne.getSheet(sheetArray[i]);
            int maxRow = sheet.getLastRowNum();
            XSSFRow row = sheet.getRow(maxRow);
            XSSFCell cell = row.getCell(2);
            XSSFCell cellTwo = row.getCell(1);

            // to take each person who are currently in each booth save it is particular index of serviceCenter array if there is no person in the particular booth then the particular index of serviceCenter array will be taken the value of e
            if (Objects.equals(cell.getStringCellValue(), "__"))
            {
                ServiceCenter[i] = cellTwo.getStringCellValue();
            }

            else if(Objects.equals(cell.getStringCellValue(), "vaccinated"))
            {
                ServiceCenter[i] = "e";
            }
            else if (Objects.equals(cell.getStringCellValue(), "vaccinatedOrNot"))
            {
                ServiceCenter[i] = "e";
            }
        }
    }
    public void printVaccinationBoothDetails()
    {
        /*This method will print who is allocated to each booth or is it empty*/
        for (int x = 0; x < 6; x++ )
        {
            System.out.println("booth " + x + " occupied by " +
                    ServiceCenter[x]);
        }
    }
    public void viewAllEmptyBooths() throws IOException {
        /*This method is to print which booths are empty*/
        ServiceCenterExample serCenter = new ServiceCenterExample();
        serCenter.viewAllVaccinationBooths();
        for (int x = 0; x < 6; x++ )
        {
            if (ServiceCenter[x].equals("e"))
                System.out.println("booth "+ x + " is empty");
        }
    }
    public void addPatientToABooth()
    {
        boothNum = 0;

        XSSFSheet sheetOne = workbookTwo.getSheet("Sheet1");
        XSSFRow rowOne = sheetOne.getRow(1);
        XSSFCell cellOne = rowOne.getCell(1);

        if(cellOne.getNumericCellValue() == 0)
            System.out.println("The vaccine stock is empty");
        else
        {
            while (boothNum!=6)
            {
                System.out.println("Enter booth number (0-5) or 6 to stop:" );
                try
                {
                    boothNum = input.nextInt();
                    if (boothNum!=0 && boothNum!=1 && boothNum!=2 && boothNum!=3 && boothNum!=4 && boothNum!=5 && boothNum!=6)
                        throw new Exception("Enter the correct booth Number");
                    else if(boothNum == 6)
                        break;
                    else
                    {
                        ServiceCenterExample serCenter = new ServiceCenterExample();
                        serCenter.viewAllVaccinationBooths();
                        // this will check is the user entered booth is empty or not
                        if (!Objects.equals(ServiceCenter[boothNum], "e"))
                            System.out.println(boothNum+" has already occupied to a patient");
                        else
                        {
                            // to enter patient name
                            input.nextLine();
                            System.out.println("Enter customer name for booth " + boothNum
                                    +" :" ) ;
                            patientName = input.next();
                            System.out.println("If You want to store data --> press 1\nIf not --> press 2");
                            int checkOption = serCenter.checkToStoreProgramDataOrToExit();
                            if (checkOption == 1)
                            {
                                // to store program data
                                serCenter.storeProgramDataIntoPatientFile();
                                int currentVaccineCount = serCenter.storeProgramDataIntoVaccinationsFile();
                                // to check whether the  vaccinations stock is reached to a value of 20 or is it under 20
                                if (currentVaccineCount  == 20)
                                    System.out.println("Warning!  The Vaccinations stock reaches a value of 20");
                                if (currentVaccineCount  < 20)
                                    System.out.println("Warning!  The Vaccinations stock is under value of 20\nThe current vaccine count is "+currentVaccineCount);
                            }
                        }
                    }
                }
                catch (InputMismatchException e )
                {
                    System.out.println("Only enter integers according to the related booth");

                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }
    public void removePatientFromABooth(){
        // method to remove a patient from a booth
        boothNum = 0;
        while (boothNum!=6)
        {
            System.out.println("Enter booth number (0-5) or 6 to stop:" );
            try
            {
                boothNum = input.nextInt();
                if (boothNum != 0 && boothNum != 1 && boothNum != 2 && boothNum != 3 && boothNum != 4 && boothNum != 5 && boothNum != 6)
                    throw new Exception("Enter the correct booth Number");
                else if (boothNum == 6)
                    break;
                else {
                    ServiceCenterExample serCenter = new ServiceCenterExample();
                    serCenter.viewAllVaccinationBooths();
                    // this will check is there a patient to remove from the particular booth
                    if (Objects.equals(ServiceCenter[boothNum], "e"))
                        System.out.println(boothNum + " is empty");
                    else{
                        System.out.println("If You want to store data --> press 1\nIf not --> press 2");
                        int checkOption = serCenter.checkToStoreProgramDataOrToExit();
                        if (checkOption == 1)
                        {
                            serCenter.storeProgramDataIntoPatientFile2();
                        }
                    }
                }
            }
            catch (InputMismatchException e )
            {
                System.out.println("Only enter integers according to the related booth");

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
    public void viewPatientsSortedInAlphabeticalOrder() throws IOException {
        // this method is to sort patients in alphabetical order who are currently available in booths
        ServiceCenterExample serCenter = new ServiceCenterExample();
        serCenter.viewAllVaccinationBooths();
        String[] patientArray = new String[6];

        for (int i = 0; i<6; i++)
        {
            // if the booth is not empty this will make patient's name in to lowercase and convert to a patient array
            if (!Objects.equals(ServiceCenter[i], "e"))
            {
                patientArray[i] = ServiceCenter[i].toLowerCase(Locale.ROOT);
            }
            else
            {
                // if the booth is empty then this will save a black in particular booth index in patient array
                patientArray[i] = " ";
            }
        }
        // this is to sort the patient array
        for(int i = 0; i<patientArray.length; i++)
        {
            for (int j = i+1; j<patientArray.length; j++)
            {
                // to compare the elemets
                if(patientArray[i].compareTo(patientArray[j])>0)
                {
                    //swapping array elements
                    String temp = patientArray[i];
                    patientArray[i] = patientArray[j];
                    patientArray[j] = temp;
                }
            }
        }
        // this is to print the sorted patient elements in order
        System.out.print("Patients: ");
        for (int i = 0; i<patientArray.length; i++)
        {
            // this is to omit the blank in patients array
            if (!Objects.equals(patientArray[i], " "))
                System.out.print(patientArray[i]+" , ");
        }
        System.out.println();
    }
    public void viewPatientsInAlphabeticalOrderBasedOnTheBoothNumber() throws IOException {
        // this method is to sort patients in alphabetical order according to user entered booth number(remember in here you are taking all the patients name who are vaccinated and also who is going to be vaccinated)
        ServiceCenterExample serCenter = new ServiceCenterExample();
        int boothNum = serCenter.enterBoothNumber();
        XSSFSheet sheet = workbookOne.getSheet(sheetArray[boothNum]);
        int maxRow = sheet.getLastRowNum();

        // this is to take patients name array of particular booth
        String[] patientNamesArray = new String[maxRow];
        for (int i =1; i<=maxRow; i++)
        {
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(1);
            patientNamesArray[i-1] = cell.getStringCellValue().toLowerCase(Locale.ROOT);
        }

        for(int i = 0; i<patientNamesArray.length; i++)
        {
            for (int j = i+1; j<patientNamesArray.length; j++)
            {

                if(patientNamesArray[i].compareTo(patientNamesArray[j])>0)
                {

                    String temp = patientNamesArray[i];
                    patientNamesArray[i] = patientNamesArray[j];
                    patientNamesArray[j] = temp;
                }
            }
        }
        System.out.print("Patients: ");
        for (int i = 0; i<patientNamesArray.length; i++)
        {
            System.out.print(patientNamesArray[i]+" , ");
        }
        System.out.println();
    }
    public int enterBoothNumber()
    {
        // method to enter booth number
        int boothNum = -1;
        while (boothNum != 0 && boothNum != 1 && boothNum != 2 && boothNum != 3 && boothNum != 4 && boothNum != 5) {
            System.out.println("Enter booth number (0-5):");
            try {
                boothNum = input.nextInt();
                if (boothNum != 0 && boothNum != 1 && boothNum != 2 && boothNum != 3 && boothNum != 4 && boothNum != 5)
                    throw new Exception("Enter the correct booth Number");

            }
            catch (InputMismatchException e )
            {
                System.out.println("Only enter integers according to the related booth");

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            finally
            {
                input.nextLine();
            }
        }
        return boothNum;
    }
    public void loadProgramDataFromFile()
    {
        // method to load program data according to user entered booth number
        boothNum = 0;
        while (boothNum!=6) {
            System.out.println("Enter booth number (0-5) or 6 to stop:");
            try {
                boothNum = input.nextInt();
                if (boothNum != 0 && boothNum != 1 && boothNum != 2 && boothNum != 3 && boothNum != 4 && boothNum != 5 && boothNum != 6)
                    throw new Exception("Enter the correct booth Number");
                else if (boothNum == 6)
                    break;
                else{
                    // to take particular booth data into a 2D array
                    XSSFSheet sheet = workbookOne.getSheet(sheetArray[boothNum]);
                    int maxRow = sheet.getLastRowNum();
                    Object[][] detailArray = new Object[maxRow][];
                    for (int i = 1; i<=maxRow; i++)
                    {
                        XSSFRow row = sheet.getRow(i);
                        Object[] recordArray = new Object[3];
                        for (int j = 0; j<3; j++)
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
                    // to print the title row of the table
                    System.out.printf("|    %-5s   |     %-11s     |     %-11s     |","Token Number","Patient Name","Vaccinated Or Not");
                    System.out.println("\n===========================================================================================================================================================");

                    // to print the patients records of the particular booth
                    for (int i = 0; i<detailArray.length; i++)
                    {
                        System.out.printf("|    %-11s    |     %-12s     |     %-17s     |",
                                detailArray[i][0],detailArray[i][1],detailArray[i][2]);
                        System.out.println();
                    }
                }
            }
            catch (InputMismatchException e )
            {
                System.out.println("Only enter integers according to the related booth");

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
    public void viewRemainingVaccinations()
    {
        // method to print the remaining vaccination count
        XSSFSheet sheet = workbookTwo.getSheet("Sheet1");
        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(1);
        System.out.println("No of Remaining Vaccinations "+(int)cell.getNumericCellValue());
    }
    public void addVaccinationsToTheStock()
    {
        // method to add vaccinations to the stock(remember in here you cannot add vaccination if the vaccination count is above 20)
        int numberOfVaccines = 170;

        XSSFSheet sheet = workbookTwo.getSheet("Sheet1");
        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(1);
        // check whether the vaccine count is equals to 20 or below 20
        if (cell.getNumericCellValue() <= 20)
        {
            // this will run whether the user entered vaccination count is greater than 150 bcs total vaccination count should be maximum of 150
            while((cell.getNumericCellValue()+numberOfVaccines)>150)
            {
                System.out.println("Enter number of vaccines that you are going to add: ");
                try{
                    numberOfVaccines = input.nextInt();
                    if ((cell.getNumericCellValue()+numberOfVaccines)>150)
                        throw new Exception("Your amount of vaccinations is high");
                    else
                    {
                        System.out.println("If You want to store data --> press 1\nIf not --> press 2");
                        ServiceCenterExample serCenter = new ServiceCenterExample();
                        int checkOption = serCenter.checkToStoreProgramDataOrToExit();
                        if (checkOption == 1)
                        {
                            int totalVaccinations = (int) (cell.getNumericCellValue()+numberOfVaccines);
                            serCenter.storeProgramDataIntoVaccinationsFile2(totalVaccinations);

                            // to check after adding vaccinations  also is it under the value of 20 or equals to 20
                            if (totalVaccinations == 20)
                                System.out.println("Warning!  The Vaccinations is a value of 20");
                            if (totalVaccinations < 20)
                                System.out.println("Warning!  The Vaccinations stock is under value of 20\nThe current vaccine count is "+totalVaccinations);
                        }
                    }
                }
                catch (InputMismatchException e )
                {
                    System.out.println("Only enter integers are allowed");

                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                finally
                {
                    input.nextLine();
                }
            }
        }
        else
        {
            System.out.println("You have more than 20 vaccines in the stock");
        }
    }
    public void storeProgramDataIntoPatientFile() throws IOException {
        // this is to store the patients details in particular excel sheet
        XSSFSheet sheet = workbookOne.getSheet(sheetArray[boothNum]);
        XSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);
        XSSFCell cell = row.createCell(0);
        XSSFCell cellTwo = row.createCell(1);
        XSSFCell cellThree = row.createCell(2);
        cell.setCellValue(sheet.getLastRowNum());
        cellTwo.setCellValue(patientName);
        cellThree.setCellValue("__");
        FileOutputStream outStream = new FileOutputStream(excelFilePathOne);
        workbookOne.write(outStream);
        outStream.close();
    }
    public int storeProgramDataIntoVaccinationsFile() throws IOException {
        // this method is to store the current  vaccination count after adding a patient to a booth and will return the current vaccination count
        XSSFSheet sheetOne = workbookTwo.getSheet("Sheet1");
        XSSFRow rowOne = sheetOne.getRow(1);
        XSSFCell cellOne = rowOne.getCell(1);
        int currentVaccineCount = (int) cellOne.getNumericCellValue();
        int totalVaccineCount = currentVaccineCount-1;
        cellOne.setCellValue(totalVaccineCount);
        FileOutputStream outStreamTwo = new FileOutputStream(excelFilePathTwo);
        workbookTwo.write(outStreamTwo);
        outStreamTwo.close();
        return totalVaccineCount;
    }
    public void storeProgramDataIntoPatientFile2() throws IOException {
        // to make a removed person as vaccinated in particular excel sheet
        XSSFSheet sheet = workbookOne.getSheet(sheetArray[boothNum]);
        XSSFRow row = sheet.getRow(sheet.getLastRowNum());
        XSSFCell cell = row.getCell(2);
        cell.setCellValue("vaccinated");
        FileOutputStream outStream = new FileOutputStream(excelFilePathOne);
        workbookOne.write(outStream);
        outStream.close();
        System.out.println("You removed a patient from the booth");
    }
    public void storeProgramDataIntoVaccinationsFile2( int totalVaccinations) throws IOException {
        // this method is to store  current  vaccination count after adding vaccinations to the stock
        XSSFSheet sheet = workbookTwo.getSheet("Sheet1");
        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(1);
        cell.setCellValue(totalVaccinations);

        FileOutputStream outStream = new FileOutputStream(excelFilePathTwo);
        workbookTwo.write(outStream);
        outStream.close();
    }
    public int checkToStoreProgramDataOrToExit()
    {
        int checkOption = 0;
        while (checkOption != 1 &&  checkOption != 2 ) {
            System.out.print("Enter your preference: ");
            try {
                checkOption = input.nextInt();
                if (checkOption != 1 && checkOption != 2) {
                    throw new Exception("Please, enter an integer 1 or 2");
                }
            } catch (InputMismatchException e) {
                System.out.println("Only integers are allowed(1 or 2)");

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                input.nextLine();
            }
        }
        return checkOption;
    }
}