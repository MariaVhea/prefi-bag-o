package Pepito;

import java.util.Scanner;
import java.sql.*;

public class Record {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    Student_List sl = new Student_List();
    Violation v = new Violation();
    
    public void record(){
        boolean exit = true;
        do{
            System.out.println("\tReport");
            System.out.println("---1. General Report");
            System.out.println("---2. Individual Report");
            System.out.println("---3. Exit");
            System.out.print("Enter Choice: ");
            int choice;
            while(true){
                try{
                    choice = input.nextInt();
                    if(choice>0 && choice<4){
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
                    general();
                    break;
                case 2:
                    sl.view();
                    individual();
                    break;
                default:
                    exit = false;
                    break;
            }
        }while(exit);
    }
    public void general(){
        sl.view();
        v.view();
    }
    public void individual(){
        boolean exit = true;
        System.out.println("\t--Select Student--");
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
                    System.out.print("Enter ID to Edit Again: ");
                }
            }catch(Exception e){
                input.next();
                System.out.print("Enter ID to Edit Again: ");
            }
        }
        while(exit){
            Rview(id);
            exit = false;
        }
    }
    
    public void Rview(int id){
        String tbl_view = "SELECT * FROM Student_List Where S_Id = "+id;
        String[] tbl_Headers = {"ID", "First Name", "Middle Name", "Last Name", "Gender", "Birth Date"};
        String[] tbl_Columns = {"S_Id", "S_fname", "S_mname","S_lname", "S_gender", "S_bdate"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
        
        String tbl_view2 = "SELECT * FROM violation_history Where S_Id = "+id;
        String[] tbl_Headers2 = {"ID", "Student ID", "Violation Name", "Violation Punishment"};
        String[] tbl_Columns2 = {"v_Id", "S_Id", "v_vname","v_pname"};
        conf.viewRecords(tbl_view2, tbl_Headers2, tbl_Columns2);
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
    private boolean doesIDexists2(int id, config conf) {
        String query = "SELECT COUNT(*) FROM violation_history Where v_Id = ?";
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
