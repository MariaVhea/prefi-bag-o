package Pepito;

import java.util.Scanner;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Student_List {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate cdate = LocalDate.now();
    LocalDate bdate;
    
    public void list(){
        boolean exit = true;
        do{
            System.out.println("\t---STUDENT LIST---");
            System.out.println("---1. Add");
            System.out.println("---2. Edit");
            System.out.println("---3. Delete");
            System.out.println("---4. View");
            System.out.println("---5. Exit");
            System.out.print("Enter Choice: ");
            int choice;
            while(true){
                try{
                    choice = input.nextInt();
                    if(choice>0 && choice<6){
                        break;
                    }else{
                        System.out.print("Again: ");
                    }
                }catch(Exception e){
                    input.next();
                    System.out.print("Again: ");
                }
            }
            switch(choice){
                case 1:
                    add();
                    break;
                case 2:
                    view();
                    edit();
                    view();
                    break;
                case 3:
                    view();
                    delete();
                    view();
                    break;
                case 4:
                    view();
                    break;
                default:
                    exit = false;
                    break;
            }
        }while(exit);
    }
    
    private void add(){
        System.out.println("\t---LIST OF STUDENTS---");
        System.out.print("Enter First Name: ");
        String fname = input.next();
        System.out.print("Enter Middle Name: ");
        String mname = input.next();
        System.out.print("Enter Last Name: ");
        String lname = input.next();
        String gender;
        while(true){
            System.out.print("Gender: ");
            try{
                gender = input.next();
                if(gender.equalsIgnoreCase("Male")||gender.equalsIgnoreCase("Female")){
                    break;
                }
            }catch(Exception e){

            }
        }
        String bdate2;
        while(true){
            System.out.print("Birth Date (YYYY-MM-DD): ");
            try{
                bdate2 = input.next();
                bdate = LocalDate.parse(bdate2,dateFormat);
                if(bdate.isBefore(cdate.minusYears(5))&&bdate.isAfter(cdate.minusYears(80))){
                    break;
                }else{
                    System.out.println("Should be more than 5 but less than 80 years old!");
                }
            }catch(Exception e){
                System.out.println("Error Date/Invalid Date");
            }
        }
        String SQL = "INSERT INTO Student_List (S_fname, S_mname, S_lname, S_gender, S_bdate) Values (?,?,?,?,?)";
        conf.addRecord(SQL, fname, mname, lname, gender, bdate);
    }
    private void edit(){
        boolean exit = true;
        System.out.println("\t---Edit Student---");
        System.out.print("Enter ID to Edit: ");
        int id;
        while(true){
            try{
                id = input.nextInt();
                if(doesIDexists(id, conf)){
                    break;
                }else if(id == 0){
                    exit = false;
                    break;
                }else{
                    System.out.print("Again: ");
                }
            }catch(Exception e){
                input.next();
                System.out.print("Again: ");
            }
        }
        while(exit){
            System.out.print("Enter New First Name: ");
            String fname = input.next();
            System.out.print("Enter New Middle Name: ");
            String mname = input.next();
            System.out.print("Enter New Last Name: ");
            String lname = input.next();
            String gender;
            while(true){
                System.out.print("Enter New Gender: ");
                try{
                    gender = input.next();
                    if(gender.equalsIgnoreCase("Male")||gender.equalsIgnoreCase("Female")){
                        break;
                    }
                }catch(Exception e){

                }
            }
            String bdate2;
            while(true){
                System.out.print("Birth Date (YYYY-MM-DD): ");
                try{
                    bdate2 = input.next();
                    bdate = LocalDate.parse(bdate2,dateFormat);
                    if(bdate.isBefore(cdate.minusYears(5))&&bdate.isAfter(cdate.minusYears(80))){
                        break;
                    }else{
                        System.out.println("Should be more than 5 but less than 80 years old!");
                    }
                }catch(Exception e){
                    System.out.println("Error Date/Invalid Date");
                }
            }
            String SQL = "UPDATE Student_List SET S_fname = ?, S_mname = ?, S_lname = ?, S_gender = ?, S_bdate = ? Where S_Id = ?";
            conf.updateRecord(SQL, fname, mname, lname, gender, bdate, id);
            exit = false;
        }
    }
    public void view(){
        String tbl_view = "SELECT * FROM Student_List";
        String[] tbl_Headers = {"ID", "First Name", "Last Name"};
        String[] tbl_Columns = {"S_Id", "S_fname", "S_lname"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
    }
    private void delete(){
        boolean exit = true;
        System.out.println("\tDELETE STUDENT INFORMATION");
        System.out.print("Enter ID to Delete: ");
        int id;
        while(true){
            try{
                id = input.nextInt();
                if(doesIDexists(id, conf)){
                    break;
                }else if(id == 0){
                    exit = false;
                    break;
                }else{
                    System.out.print("Enter ID to Delete Again: ");
                }
            }catch(Exception e){
                input.next();
                System.out.print("Enter ID to Delete Again: ");
            }
        }
        while(exit){
            String SQL = "DELETE FROM Student_List Where S_Id = ?";
            conf.deleteRecord(SQL, id);
            exit = false;
        }
    }
    private boolean doesIDexists(int id, config conf) {
        String query = "SELECT COUNT(*) FROM Student_List Where S_Id = ?";
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("|\tError checking Report ID: " + e.getMessage());
        }
        return false;
    }
}
