import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        VaccinationCenter serCenter = new VaccinationCenter();
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
                    "Add Vaccinations to the Stock-->press 8\n");

            int menuOption = selectMenuOption();
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
                int sortOption = enterSortOption();
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

            System.out.println("\nIf you want to exit --> press 1\nIf not --> press 2");
            exitOption = checkToExit();
        }
    }

    private static int enterSortOption()
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

    public static int checkToExit()
    {
        // to enter the option to exit or not
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
    public static int selectMenuOption()
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
}
