package Pepito;

import java.util.Scanner;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Student_Violation {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate cdate = LocalDate.now();
    LocalDate bdate;
    Student_List st = new Student_List();
    Violation v = new Violation();
    
    public void violation(){
        boolean exit = true;
        do{
            System.out.println("\tSTUDENT VIOLATION");
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
                        System.out.print("Enter Choice Again: ");
                    }
                }catch(Exception e){
                    input.next();
                    System.out.print("Enter Choice Again: ");
                }
            }
            switch(choice){
                case 1:
                    st.view();
                    add();
                    break;
                case 2:
                    st.view();
                    edit();
                    break;
                case 3:
                    view();
                    delete();
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
        boolean exit = true;
        int id;
        while(true){
            System.out.print("Enter ID to Edit: ");
            try{
                id = input.nextInt();
                if(doesIDexists(id, conf)){
                    break;
                }else if(id == 0){
                    exit = false;
                    break;
                }
            }catch(Exception e){
                input.next();
            }
        }
        while(exit){
            v.view();
            boolean exit2 = true;
            int vid;
            while(true){
                System.out.print("Enter ID to Edit: ");
                try{
                    vid = input.nextInt();
                    if(doesIDexists2(id, conf)){
                        break;
                    }else if(id == 0){
                        exit2 = false;
                        break;
                    }
                }catch(Exception e){
                    input.next();
                }
            }
            while(exit2){
                System.out.print("Status: ");
                input.nextLine();
                String stat = input.nextLine();
                String SQL = "INSERT INTO tbl_Record (S_Id, V_Id, violation_date, status) Values (?,?,?,?)";
                conf.addRecord(SQL, id, vid, cdate, stat);
                exit2 = false;
            }
            exit = false;
        }
    }
    
    private void edit(){
        boolean exit = true;
        int id;
        while(true){
            System.out.print("Enter ID to Edit: ");
            try{
                id = input.nextInt();
                if(doesIDexists2(id, conf)){
                    break;
                }else if(id == 0){
                    exit = false;
                    break;
                }
            }catch(Exception e){
                input.next();
            }
        }
        while(exit){
            System.out.print("Enter Violation Name: ");
            String vname = input.next();
            System.out.print("Enter Punishment: ");
            String pname = input.next();
            String SQL = "UPDATE violation_history SET v_vname = ?, v_pname = ? Where v_Id = ?";
            conf.updateRecord(SQL, vname, pname, id);
            exit = false;
        }
    }
    private void delete(){
        boolean exit = true;
        int id;
        while(true){
            System.out.print("Enter ID to Delete: ");
            try{
                id = input.nextInt();
                if(doesIDexists2(id, conf)){
                    break;
                }else if(id == 0){
                    exit = false;
                    break;
                }
            }catch(Exception e){
                input.next();
            }
        }
        while(exit){
            String SQL = "DELETE FROM violation_history Where v_Id = ?";
            conf.deleteRecord(SQL, id);
            exit = false;
        }
    }
    public void view(){
        String tbl_view = "SELECT * FROM tbl_Record";
        String[] tbl_Headers = {"ID", "Student ID", "Violation ID"};
        String[] tbl_Columns = {"record_id", "S_Id", "V_Id"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
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
