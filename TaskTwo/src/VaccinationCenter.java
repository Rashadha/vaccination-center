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

public class VaccinationCenter
{
    private final Scanner input = new Scanner(System.in);
    private static final String excelFilePathOne = "D:\\Java_codes\\Defer_CW_TaskTwo\\src\\PatientDetails.xlsx";
    private static final String excelFilePathTwo = "D:\\Java_codes\\Defer_CW_TaskTwo\\src\\VaccineDetails.xlsx";
    private final FileInputStream inputStreamOne =  new FileInputStream(excelFilePathOne);
    private final FileInputStream inputStreamTwo =  new FileInputStream(excelFilePathTwo);
    private final XSSFWorkbook workbookOne = new XSSFWorkbook(inputStreamOne);
    private final XSSFWorkbook workbookTwo = new XSSFWorkbook(inputStreamTwo);
    private final String[] sheetArray = {"BoothZero","BoothOne","BoothTwo","BoothThree","BoothFour","BoothFive"};
    private static String[] serviceCenter;
    private Booth[] obj ;

    public VaccinationCenter() throws IOException {
        obj = new Booth[6];
        serviceCenter = new String[6];
    }

    public void viewAllVaccinationBooths() {
        // this array is to take the actual serviceCenter array this will run for 6 times since there are six booths
        for (int i = 0; i<6; i++)
        {
            obj[i] = new Booth();
            obj[i].setData(sheetArray[i]);
            serviceCenter = obj[i].takePatientNamesFromExcelSheet(workbookOne, serviceCenter,i);
        }
    }
    public void printVaccinationBoothDetails()
    {
        /*This method will print who is allocated to each booth or is it empty*/
        for (int x = 0; x < 6; x++ )
        {
            System.out.println("booth " + x + " occupied by " +
                    serviceCenter[x]);
        }
    }
    public void viewAllEmptyBooths() throws IOException {
        /*This method is to print which booths are empty*/
        VaccinationCenter serCenter = new VaccinationCenter();
        serCenter.viewAllVaccinationBooths();

        for (int x = 0; x < 6; x++ )
        {
            if (serviceCenter[x].equals("e"))
                System.out.println("booth "+ x + " is empty");
        }
    }
    public void addPatientToABooth() throws IOException {
        // in here ask to select the requested vaccine type
        System.out.println("If the requested vaccine is AstraZeneca --> press 1\n" +
                "If the requested vaccine is Sinopharm --> press 2\n" +
                "If the requested vaccine is Pfizer --> press 3");

        VaccinationCenter serCenter = new VaccinationCenter();
        serCenter.viewAllVaccinationBooths();

        int vaccineOption = serCenter.selectRequestedVaccineType();

        XSSFSheet sheetOne = workbookTwo.getSheet("Sheet1");

        if (vaccineOption == 1)
        {
            XSSFRow rowOne = sheetOne.getRow(1);
            XSSFCell cellOne = rowOne.getCell(1);

            // this will check the particular vaccine type is empty or not
            if(cellOne.getNumericCellValue() == 0)
                System.out.println("The \"AstraZeneca\" vaccine stock is empty");
            else
            {
                // this will check is the each booth is empty or not according to the requested vaccine type
                if (!Objects.equals(serviceCenter[0], "e"))
                {
                    if (!Objects.equals(serviceCenter[1], "e"))
                        System.out.println("Both boothZero and boothOne have already occupied to patients");
                    else
                    {
                        // this is to get the patients details in to an array
                        Object[] patientDetails = serCenter.getPatientDetails();

                        // to ask user to store data or not
                        int checkOption = serCenter.checkToStoreProgramData();
                        if (checkOption == 1)
                        {
                            // to store program data
                            obj[1] = new Booth();
                            obj[1].setData(sheetArray[1]);
                            obj[1].storeProgramDataIntoRelatedBoothFile(workbookOne,patientDetails,excelFilePathOne);
                            // to check whether the particular vaccination type is reached to a value of 20 or is it under 20
                            int currentVaccineCount = serCenter.storeProgramDataIntoVaccinationsFile(1);
                            if (currentVaccineCount  == 20)
                                System.out.println("Warning!  The \"AstraZeneca\" Vaccinations stock reaches a value of 20");
                            if (currentVaccineCount  < 20)
                                System.out.println("Warning!  The \"AstraZeneca\" Vaccinations stock is under value of 20\nThe current \"AstraZeneca\" vaccine count is "+currentVaccineCount);
                        }
                    }
                }
                else
                {
                    Object[] patientDetails = serCenter.getPatientDetails();

                    int checkOption = serCenter.checkToStoreProgramData();
                    if (checkOption == 1)
                    {
                        obj[0] = new Booth();
                        obj[0].setData(sheetArray[0]);
                        obj[0].storeProgramDataIntoRelatedBoothFile(workbookOne,patientDetails,excelFilePathOne);
                        int currentVaccineCount = serCenter.storeProgramDataIntoVaccinationsFile(1);
                        if (currentVaccineCount  == 20)
                            System.out.println("Warning!  The \"AstraZeneca\" Vaccinations stock reaches a value of 20");
                        if (currentVaccineCount  < 20)
                            System.out.println("Warning!  The \"AstraZeneca\" Vaccinations stock is under value of 20\nThe current \"AstraZeneca\" vaccine count is "+currentVaccineCount);
                    }
                }

            }
        }
        else if (vaccineOption == 2)
        {
            XSSFRow rowOne = sheetOne.getRow(2);
            XSSFCell cellOne = rowOne.getCell(1);

            if(cellOne.getNumericCellValue() == 0)
                System.out.println("The \"Sinopharm\" vaccine stock is empty");
            else
            {
                if (!Objects.equals(serviceCenter[2], "e"))
                {
                    if (!Objects.equals(serviceCenter[3], "e"))
                        System.out.println("Both boothTwo and boothFour have already occupied to patients");
                    else
                    {
                        Object[] patientDetails = serCenter.getPatientDetails();

                        int checkOption = serCenter.checkToStoreProgramData();
                        if (checkOption == 1)
                        {
                            obj[3] = new Booth();
                            obj[3].setData(sheetArray[3]);
                            obj[3].storeProgramDataIntoRelatedBoothFile(workbookOne,patientDetails,excelFilePathOne);
                            int currentVaccineCount = serCenter.storeProgramDataIntoVaccinationsFile(2);
                            if (currentVaccineCount  == 20)
                                System.out.println("Warning!  The \"Sinopharm\" Vaccinations stock reaches a value of 20");
                            if (currentVaccineCount  < 20)
                                System.out.println("Warning!  The \"Sinopharm\" Vaccinations stock is under value of 20\nThe current \"Sinopharm\" vaccine count is "+currentVaccineCount);
                        }
                    }
                }
                else
                {
                    Object[] patientDetails = serCenter.getPatientDetails();

                    int checkOption = serCenter.checkToStoreProgramData();
                    if (checkOption == 1)
                    {
                        obj[2] = new Booth();
                        obj[2].setData(sheetArray[2]);
                        obj[2].storeProgramDataIntoRelatedBoothFile(workbookOne,patientDetails,excelFilePathOne);
                        int currentVaccineCount = serCenter.storeProgramDataIntoVaccinationsFile(2);
                        if (currentVaccineCount  == 20)
                            System.out.println("Warning!  The \"Sinopharm\" Vaccinations stock reaches a value of 20");
                        if (currentVaccineCount  < 20)
                            System.out.println("Warning!  The \"Sinopharm\" Vaccinations stock is under value of 20\nThe current \"Sinopharm\" vaccine count is "+currentVaccineCount);
                    }
                }
            }
        }
        else
        {
            XSSFRow rowOne = sheetOne.getRow(3);
            XSSFCell cellOne = rowOne.getCell(1);

            if(cellOne.getNumericCellValue() == 0)
                System.out.println("The \"Pfizer\" vaccine stock is empty");
            else
            {
                if (!Objects.equals(serviceCenter[4], "e"))
                {
                    System.out.println();
                    if (!Objects.equals(serviceCenter[5], "e"))
                        System.out.println("Both boothFour and boothFive have already occupied to patients");
                    else
                    {
                        Object[] patientDetails = serCenter.getPatientDetails();

                        int checkOption = serCenter.checkToStoreProgramData();
                        if (checkOption == 1)
                        {
                            obj[5] = new Booth();
                            obj[5].setData(sheetArray[5]);
                            obj[5].storeProgramDataIntoRelatedBoothFile(workbookOne,patientDetails,excelFilePathOne);
                            int currentVaccineCount = serCenter.storeProgramDataIntoVaccinationsFile(3);
                            if (currentVaccineCount  == 20)
                                System.out.println("Warning!  The \"Pfizer\" Vaccinations stock reaches a value of 20");
                            if (currentVaccineCount  < 20)
                                System.out.println("Warning!  The \"Pfizer\" Vaccinations stock is under value of 20\nThe current \"Pfizer\" vaccine count is "+currentVaccineCount);
                        }
                    }
                }
                else
                {
                    Object[] patientDetails = serCenter.getPatientDetails();

                    int checkOption = serCenter.checkToStoreProgramData();
                    if (checkOption == 1)
                    {
                        obj[4] = new Booth();
                        obj[4].setData(sheetArray[4]);
                        obj[4].storeProgramDataIntoRelatedBoothFile(workbookOne,patientDetails,excelFilePathOne);

                        int currentVaccineCount = serCenter.storeProgramDataIntoVaccinationsFile(3);
                        if (currentVaccineCount  == 20)
                            System.out.println("Warning!  The \"Pfizer\" Vaccinations stock reaches a value of 20");
                        if (currentVaccineCount  < 20)
                            System.out.println("Warning!  The \"Pfizer\" Vaccinations stock is under value of 20\nThe current \"Pfizer\" vaccine count is "+currentVaccineCount);
                    }
                }
            }
        }
    }
    public int selectRequestedVaccineType()
    {
        // method to select the requested vaccine type option
        int vaccineOption = 0;
        while(vaccineOption!=1 && vaccineOption!=2 && vaccineOption!=3)
        {
            System.out.println("Enter your preference: ");
            try
            {
                vaccineOption = input.nextInt();
                if (vaccineOption!=1 && vaccineOption!=2 && vaccineOption!=3)
                    throw new Exception("Enter the correct vaccine option");
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
        return vaccineOption;
    }
    public Object[] getPatientDetails() throws IOException {
        // this method is to convert patient details into an array
        VaccinationCenter serCenter = new VaccinationCenter();
        Patient patient = new Patient();
        Object[] patientDetails = new Object[6];

        serCenter.enterPatientFirstName(patient);
        patientDetails[0] = patient.getFirstName();

        serCenter.enterPatientSurName(patient);
        patientDetails[1] = patient.getSurName();

        serCenter.enterPatientAge(patient);
        patientDetails[2] = patient.getAge();

        serCenter.enterPatientCity(patient);
        patientDetails[3] = patient.getCity();

        int option = serCenter.selectOptionToEnterPassportOrNICNumber();
        if (option == 1)
        {
            serCenter.enterNICNumber(patient);
            patientDetails[4] = patient.getNICNumber();
            patientDetails[5] = "__";
        }
        else if (option == 2)
        {
            serCenter.enterPassportNumber(patient);
            patientDetails[4] = "__";
            patientDetails[5] = patient.getPassportNumber();
        }
        else
        {
            serCenter.enterNICNumber(patient);
            serCenter.enterPassportNumber(patient);
            patientDetails[4] = patient.getNICNumber();
            patientDetails[5] = patient.getPassportNumber();
        }
        return patientDetails;
    }
    public void enterPatientFirstName(Patient patient)
    {
        // to take patient's first name
        String patientFirstName = patient.getFirstName();
        while (patientFirstName == null)
        {
            try{
                System.out.println("Enter the patient's first name: ");
                patientFirstName = input.next();
                if (patientFirstName == null)
                    throw new Exception("Name cannot be null");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        patient.setFirstName(patientFirstName);
    }
    public void enterPatientSurName(Patient patient)
    {
        // to take patient's surname
        String patientSurName = patient.getSurName();
        while (patientSurName == null)
        {
            try{
                System.out.println("Enter the patient's surname: ");
                patientSurName = input.next();
                if (patientSurName == null)
                    throw new Exception("Surname cannot be null");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        patient.setSurName(patientSurName);
    }
    public void enterPatientAge(Patient patient)
    {
        // to take patient's age
        int patientAge = -1;
        while(patientAge<0)
        {
            try
            {
                System.out.println("Enter the patient's age: ");
                patientAge = input.nextInt();
                if (patientAge < 0)
                    throw new Exception("Enter the correct age");
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
        patient.setAge(patientAge);
    }
    public void enterPatientCity(Patient patient)
    {
        // to enter patient's city
        String patientCity = patient.getCity();
        while (patientCity == null)
        {
            try{
                System.out.println("Enter the patient's city: ");
                patientCity = input.next();
                if (patientCity == null)
                    throw new Exception("City cannot be null");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        patient.setCity(patientCity);
    }
    public int selectOptionToEnterPassportOrNICNumber()
    {
        // to ask the user are you going to enter the NICNumber or the passport number
        int option = 0;
        System.out.println("If you want to enter NIC number --> press 1\n" +
                "If you want to enter Passport number --> press 2\n" +
                "If you want to enter both Passport and NIC numbers --> press 2");
        while(option!=1 && option!=2 && option!=3)
        {
            System.out.println("Enter the preference: ");
            try
            {
                option = input.nextInt();
                if (option!=1 && option!=2 && option!=3)
                    throw new Exception("Enter the correct option");
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
        return option;
    }
    public void enterNICNumber(Patient patient)
    {
        // to enter nic number
        String patientNICNumber = patient.getNICNumber();
        while (Objects.equals(patientNICNumber, null))
        {
            try
            {
                System.out.println("Enter the NIC Number:");
                patientNICNumber = input.next();
                if (patientNICNumber.length()!=10 && patientNICNumber.length()!= 12)
                {
                    patientNICNumber = null;
                    throw new Exception("Wrong NIC Number");
                }
                else if (patientNICNumber.length()==12)
                {
                    try
                    {
                        Long.parseLong(patientNICNumber);
                    }
                    catch (IllegalArgumentException e)
                    {
                        patientNICNumber = null;
                        System.out.println("Wrong NIC Number");
                    }
                }
                else
                {
                    String str = patientNICNumber.substring(0,9);
                    String str1 = patientNICNumber.substring(9);
                    try
                    {
                        Integer.parseInt(str);
                        if (!"V".equals(str1))
                        {
                            patientNICNumber = null;
                            System.out.println("Wrong NIC Number");
                        }
                    }
                    catch (IllegalArgumentException e)
                    {
                        patientNICNumber = null;
                        System.out.println("Wrong NIC Number");
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        patient.setNICNumber(patientNICNumber);
    }
    public void enterPassportNumber(Patient patient)
    {
        // to enter passport number
        String patientPassportNumber = patient.getPassportNumber();
        while (Objects.equals(patientPassportNumber, null))
        {
            try
            {
                System.out.println("Enter the passport number:");
                patientPassportNumber = input.next();
                if (patientPassportNumber.length() != 8)
                {
                    patientPassportNumber = null;
                    throw new Exception("Wrong passport number");
                }
                else
                {
                    String str = patientPassportNumber.substring(1);
                    try
                    {
                        Integer.parseInt(str);
                        if (!"N".equals(patientPassportNumber.substring(0,1)))
                        {
                            patientPassportNumber = null;
                            System.out.println("Wrong passport number");
                        }
                    }
                    catch (IllegalArgumentException e)
                    {
                        patientPassportNumber= null;
                        System.out.println("Wrong passport number");
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        patient.setPassportNumber(patientPassportNumber);
    }
    public int checkToStoreProgramData()
    {
        // to check whether you want to store data or not
        int checkOption = 0;
        System.out.println("If You want to store data --> press 1\nIf not --> press 2");
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
    public int storeProgramDataIntoVaccinationsFile(int rowNum) throws IOException {
        // this method is to store the current particular vaccination count after adding a patient to a booth and will return the current vaccination count of the particular vaccine type
        XSSFSheet sheetOne = workbookTwo.getSheet("Sheet1");
        XSSFRow rowOne = sheetOne.getRow(rowNum);
        XSSFCell cellOne = rowOne.getCell(1);
        int currentVaccineCount = (int) cellOne.getNumericCellValue();
        int totalVaccineCount = currentVaccineCount-1;
        cellOne.setCellValue(totalVaccineCount);
        FileOutputStream outStreamTwo = new FileOutputStream(excelFilePathTwo);
        workbookTwo.write(outStreamTwo);
        outStreamTwo.close();
        return totalVaccineCount;
    }
    public void removePatientFromABooth(){
        // method to remove a patient from a booth
        int boothNum = 0;
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
                    VaccinationCenter serCenter = new VaccinationCenter();
                    serCenter.viewAllVaccinationBooths();
                    // this will check is there a patient to remove from the particular booth
                    if (Objects.equals(serviceCenter[boothNum], "e"))
                        System.out.println(boothNum + " is empty");
                    else{
                        // if there is a patient in that particular booth then ask the user do you want to store program data
                        int checkOption = serCenter.checkToStoreProgramData();
                        if (checkOption == 1)
                        {
                            // this will go the particular booth and after removing that person make that person as vaccinated
                            obj[boothNum] = new Booth();
                            obj[boothNum].setData(sheetArray[boothNum]);
                            obj[boothNum].storeProgramDataIntoRelatedBoothFile2(workbookOne,excelFilePathOne);
                            System.out.println("You removed a patient from the booth");
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
            finally
            {
                input.nextLine();
            }
        }
    }
    public void viewPatientsSortedInAlphabeticalOrder() throws IOException {
        // this method is to sort patients in alphabetical order who are currently available in booths
        VaccinationCenter serCenter = new VaccinationCenter();
        serCenter.viewAllVaccinationBooths();
        String[] patientArray = new String[6];

        // if the booth is not empty this will make patient's name in to lowercase and convert to a patient array
        for (int i = 0; i<6; i++)
        {
            if (!Objects.equals(serviceCenter[i], "e"))
            {
                patientArray[i] = serviceCenter[i].toLowerCase(Locale.ROOT);
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
                if(patientArray[i].compareTo(patientArray[j])>0) // to compare the elemets
                {
                    // to swap the elements
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
        // this method is to sort patients in alphabetical order according to user entered booth number(remember in here you are taking all the patients name who are vaccinated and also who is going to vaccinated)
        VaccinationCenter serCenter = new VaccinationCenter();
        int boothNum = serCenter.enterBoothNumber();
        obj[boothNum] = new Booth();
        obj[boothNum].setData(sheetArray[boothNum]);
        // this is to take patients name array of particular booth
        String[] patientNamesArray = obj[boothNum].getPatientNamesFromParticularBooth(workbookOne);

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
        int boothNum = 0;
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
                    obj[boothNum] = new Booth();
                    obj[boothNum].setData(sheetArray[boothNum]);
                    Object[][] detailArray = obj[boothNum].toTakeDataOutFromBoothFile(workbookOne);

                    // to print the title row of the table

                    System.out.printf("|    %-5s   |     %-11s     |     %-14s     |     %-11s     |     %-11s     |     %-11s     |     %-11s     |     %-11s     |","Token Number","Patient First Name",
                            "Patient Sur Name","Patient Age","Patient City",
                            "Patient NIC Number","Patient Passport Number","Vaccinated Or Not");
                    System.out.println("\n===========================================================================================================================================================" +
                            "==========================================================");

                    // to print the patients records of the particular booth
                    for (int i = 0; i<detailArray.length; i++)
                    {
                        System.out.printf("|    %-11s    |     %-18s     |     %-16s     |     %-11s     |     %-12s     |     %-18s     |     %-23s     |     %-17s     |",
                                detailArray[i][0],detailArray[i][1],detailArray[i][2]
                        ,detailArray[i][3],detailArray[i][4],detailArray[i][5]
                        ,detailArray[i][6],detailArray[i][7]);
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
    public void viewRemainingVaccinations() {
        // method to print the remaining vaccination count of each vaccine types
        XSSFSheet sheet = workbookTwo.getSheet("Sheet1");

        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(1);
        System.out.println("No of Remaining \"AstraZeneca\" Vaccinations "+(int)cell.getNumericCellValue());

        row = sheet.getRow(2);
        cell = row.getCell(1);
        System.out.println("No of Remaining \"Sinopharm\" Vaccinations "+(int)cell.getNumericCellValue());

        row = sheet.getRow(3);
        cell = row.getCell(1);
        System.out.println("No of Remaining \"Pfizer\" Vaccinations "+(int)cell.getNumericCellValue());
    }
    public void addVaccinationsToTheStock() throws IOException {
        // method to add vaccinations to the stock (remember in here you cannot add vaccination if the particular vaccination count is above 20)
        VaccinationCenter serCenter = new VaccinationCenter();
        // ask user to enter the option of vaccination type that you are going to add
        System.out.println("If you want to add \"AstraZeneca\" vaccinations --> press 1\n" +
                "If you want to add \"Sinopharm\" vaccinations --> press 2\n" +
                "If you want to add \"Pfizer\" vaccinations --> press 3\n");
        int vaccineOption = serCenter.selectRequestedVaccineType();

        XSSFSheet sheet = workbookTwo.getSheet("Sheet1");
        XSSFRow row;
        XSSFCell cell;

        // to select the particular row according to the vaccine type
        if (vaccineOption == 1)
        {
            row = sheet.getRow(1);
        }
        else if (vaccineOption == 2)
        {
            row = sheet.getRow(2);
        }
        else
        {
            row = sheet.getRow(3);
        }
        cell = row.getCell(1);

        int numberOfVaccines = 70;
        // check whether the particular vaccine count is equals to 20 or below 20
        if (cell.getNumericCellValue() <= 20)
        {
            // this will run whether the user entered particular vaccination count is greater than fifty bcs each vaccine types should be maximum of 50
            while((cell.getNumericCellValue()+numberOfVaccines)>50)
            {
                System.out.println("Enter number of vaccines that you are going to add: ");
                try{
                    numberOfVaccines = input.nextInt();
                    if ((cell.getNumericCellValue()+numberOfVaccines)>50)
                        throw new Exception("Your amount of vaccinations is high");
                    else
                    {
                        int checkOption = serCenter.checkToStoreProgramData();
                        if (checkOption == 1)
                        {
                            int totalVaccinations = (int) (cell.getNumericCellValue()+numberOfVaccines);
                            serCenter.storeProgramDataIntoVaccinationsFile2(totalVaccinations,vaccineOption);

                            String vaccineName;
                            if (vaccineOption == 1)
                            {
                                vaccineName = "AstraZeneca";
                            }
                            else if (vaccineOption == 2)
                            {
                                vaccineName = "Sinopharm";
                            }
                            else
                            {
                                vaccineName = "Pfizer";
                            }

                            // to check after adding particular vaccination type also is it under the value of 20 or equals to 20

                            if (totalVaccinations == 20)
                                System.out.println("Warning!  The Vaccinations is a value of 20");
                            if (totalVaccinations < 20)
                                System.out.println("Warning!  The "+vaccineName+ " Vaccinations stock is under value of 20\nThe current "+vaccineName+" vaccine count is "+totalVaccinations);
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

    private void storeProgramDataIntoVaccinationsFile2(int totalVaccinations, int vaccineOption) throws IOException {
        // this method is to store the current particular vaccination type count after adding new same type vaccinations to the stock
        XSSFSheet sheetOne = workbookTwo.getSheet("Sheet1");
        XSSFRow row = sheetOne.getRow(vaccineOption);
        XSSFCell cell = row.getCell(1);

        cell.setCellValue(totalVaccinations);

        FileOutputStream outStream = new FileOutputStream(excelFilePathTwo);
        workbookTwo.write(outStream);
        outStream.close();
    }
}
