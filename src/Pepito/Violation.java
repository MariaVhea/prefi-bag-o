package Pepito;

import java.util.Scanner;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Violation {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate cdate = LocalDate.now();
    LocalDate bdate;
    Student_List st = new Student_List();
    public void violation(){
        boolean exit = true;
        do{
            System.out.println("\tREGISTER STUDENT VIOLATION");
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
            input.nextLine();
            System.out.print("Enter Violation Name: ");
            String vname = input.nextLine();
            System.out.print("Enter Punishment: ");
            String pname = input.nextLine();
            String SQL = "INSERT INTO violation_history (S_Id, v_vname, v_pname) Values (?,?,?)";
            conf.addRecord(SQL, id, vname, pname);
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
            violation_view(id);
            int vid;
            while(true){
                System.out.print("Enter ID to Edit: ");
                try{
                    vid = input.nextInt();
                    if(doesIDexists2(vid, conf)){
                        break;
                    }else if(vid == 0){
                        exit = false;
                        break;
                    }
                }catch(Exception e){
                    input.next();
                }
            }
            System.out.print("Enter Violation Name: ");
            String vname = input.next();
            System.out.print("Enter Punishment: ");
            String pname = input.next();
            String SQL = "UPDATE violation_history SET v_vname = ?, v_pname = ? Where v_Id = "+vid;
            conf.updateRecord(SQL, vname, pname);
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
        String tbl_view = "SELECT * FROM violation_history";
        String[] tbl_Headers = {"ID", "Violation Name", "Punishment"};
        String[] tbl_Columns = {"v_Id", "v_vname", "v_pname"};
        conf.viewRecords(tbl_view, tbl_Headers, tbl_Columns);
    }
    public void violation_view(int id){
        String tbl_view = "SELECT * FROM violation_history Where S_Id = "+id;
        String[] tbl_Headers = {"ID", "Violation Name", "Punishment"};
        String[] tbl_Columns = {"v_Id", "v_vname", "v_pname"};
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
