package Pepito;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean exit = true;
        do{
            System.out.println("\tVIOLATION TRACKER");
            System.out.println("---1. Student List");
            System.out.println("---2. Violation");
            System.out.println("---3. Student Violation");
            System.out.println("---4. Report");
            System.out.println("---5. Exit");
            System.out.print("Enter Choice: ");
            int choice;
            while(true){
                try{
                    choice = input.nextInt();
                    if(choice>0 && choice<5){
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
                    Student_List sl = new Student_List();
                    sl.list();
                break;
                case 2:
                    Violation v = new Violation();
                    v.violation();
                break;
                case 3:
                    Student_Violation st = new Student_Violation();
                    st.violation();
                break;
                case 4:
                    Record r = new Record();
                    r.record();
                break;
                default:
                    System.out.print("Do you really want to exit (Yes/No): ");
                    String exit2;
                    while(true){
                        try{
                            exit2 = input.next();
                            if(exit2.equalsIgnoreCase("yes")){
                                exit = false;
                                break;
                            }else{
                                break;
                            }
                        }catch(Exception e){
                            System.out.print("Again: ");
                        }
                    }
                break;
            }
        }while(exit);
    }
}
