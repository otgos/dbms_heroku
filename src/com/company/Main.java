package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            int choice = 0;
            //calling thte student class
            Student student = new Student();
            do{
                System.out.println("WELCOME TO STUDENT RECORD SYSTEM\nPlease select the option from meny\n1-Student Registration\n2-Password update\n3-Delete Record\n4-Search for a student\n5-Show all student\n6-Exit from the Application");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        student.getStudentDetails();
                        student.saveStudent();
                        break;
                    case 2:
                        student.updatePassword();
                        break;
                    case 3:
                        student.deleteRecord();
                        break;
                    case 4:
                        student.searchRecordByName();
                        break;
                    case 5:
                        student.displayAllStudents();
                        break;
                    case 6:
                        System.out.println("Exit the App");
                        break;
                    default:
                        System.out.println("Please enter the correct choice");
                }
            }while (choice!=6);{
                System.out.println("Thank you for using the application");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
class Student{
    private String name;
    private String email;
    private String password;
    private String country;
    private int mark;
    private int age;
    public void getStudentDetails(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Student name: ");
        name = input.nextLine();
        System.out.print("Enter Student email: ");
        email = input.nextLine();
        System.out.print("Enter Student password: ");
        password = input.nextLine();
        System.out.print("Enter Student country: ");
        country = input.nextLine();
        System.out.print("Enter Student mark: ");
        mark = input.nextInt();
        System.out.print("Enter Student age: ");
        age = input.nextInt();

    }
    //function for saving students
    public void saveStudent() throws SQLException {
        DbmsConnection db = new DbmsConnection();
        Connection conn = db.getConnection();
        String sql = "Insert into students values (?,?,?,?,?,?);";
        PreparedStatement sttmnt = conn.prepareStatement(sql);
        //saving data to columns
        sttmnt.setString(1, name);
        sttmnt.setString(2, email);
        sttmnt.setString(3, password);
        sttmnt.setString(4, country);
        sttmnt.setInt(5, mark);
        sttmnt.setInt(6, age);

        sttmnt.executeUpdate();
        System.out.println("data has been added successfully");

    }
    public void updatePassword() throws SQLException {
        DbmsConnection db = new DbmsConnection();
        Connection conn = db.getConnection();
        System.out.println("Enter email to change the password");
        Scanner scanner =  new Scanner(System.in);
        String emailToUpdate = scanner.nextLine();

        System.out.println("Enter new password: ");
        String newPass = scanner.nextLine();
        String sql = "Update students set password = ? where email = ? ;";
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, newPass);
        stmnt.setString(2, emailToUpdate);
        int i  = stmnt.executeUpdate();
        //checking email correct
        if(i>0){
            System.out.println("Password has been updated");
        }else {
            System.out.println(i);

            System.out.println("Email does not exist");
        }
    }
    public void deleteRecord() throws SQLException {
        DbmsConnection db = new DbmsConnection();
        Connection conn = db.getConnection();
        System.out.println("Enter email to delete the record");
        Scanner scanner =  new Scanner(System.in);
        String emailDelete = scanner.nextLine();
        String sql = "DELETE FROM students where email = ? ;";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, emailDelete);
        int i  = statement.executeUpdate();
        if(i>0){
            System.out.println("Password has been deleted");
        }else {
            System.out.println(i);

            System.out.println("Email does not exist");
        }
    }
    public void searchRecordByName() throws SQLException {
        DbmsConnection connection = new DbmsConnection();
        Connection conn =  connection.getConnection();
        System.out.println("Type name to reach record");
        Scanner scanner = new Scanner(System.in);
        String inputName= scanner.nextLine();
        String sql = "SELECT * FROM students where name =?;";
        PreparedStatement stmnt = conn.prepareStatement(sql);
        stmnt.setString(1, inputName);
        ResultSet rs = stmnt.executeQuery();

        if(rs!=null){
            System.out.println("==========Search result for the entered name==========");
            System.out.println("Name - Email -  Password - Country - Mark - Age");
            while (rs.next()){
                System.out.print(rs.getString("name")+" - ");
                System.out.print(rs.getString("email")+" - ");
                System.out.print(rs.getString("password")+" - ");
                System.out.print(rs.getString("country")+" - ");
                System.out.print(rs.getInt("mark")+"  ");
                System.out.println(rs.getInt("age"));
            }
        }else{
            System.out.println("==========Search result for the entered name==========");
            System.out.println("No record found for the name entered");
        }
    }
    public void displayAllStudents() throws SQLException {
        DbmsConnection connection = new DbmsConnection();
        Connection conn =  connection.getConnection();
        String sql = "SELECT * FROM students;";
        PreparedStatement stmnt = conn.prepareStatement(sql);

        ResultSet rs = stmnt.executeQuery();
        System.out.println("==========List Of All Students==========");
        System.out.println("Name - Email -  Password - Country - Mark - Age");
        while (rs.next()){
            System.out.print(rs.getString("name")+" - ");
            System.out.print(rs.getString("email")+" - ");
            System.out.print(rs.getString("password")+" - ");
            System.out.print(rs.getString("country")+" - ");
            System.out.print(rs.getInt("mark")+"  ");
            System.out.println(rs.getInt("age"));
        }
    }
}
//Making class for data connection
class DbmsConnection{
    public Connection getConnection (){
        Connection conn = null;
        try {
            //load the driver
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://ec2-52-207-15-147.compute-1.amazonaws.com:5432/d2r87logbj1nf4", "aqssajhgfxddpe", "1b4f11b1f2fcb2bb39be264220660127fd3bfcf418542ad4777117e33ae1ab3c");
            if (conn!=null) {
                System.out.println("Connection established");
            }else{
                System.out.println("Connection failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

}
