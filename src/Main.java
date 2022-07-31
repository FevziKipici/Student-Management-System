import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            //variable for making the choice
            int choice;
            Student s=new Student();

            do {
                System.out.println("WELCOME TO STUDENT RECORD SYSTEM\n" +
                        "Please select the option from menu\n" +
                        "1-Student Registration\n" +
                        "2-Password Update\n" +
                        "3-Delete Record\n" +
                        "4-Search for a student\n" +
                        "5-Show all students\n" +
                        "6-Exit Application");

                //input for selecting the menu
                Scanner ch=new Scanner(System.in);
                choice=ch.nextInt();
                switch (choice){
                    case 1:
                        s.getStudentDetail();
                        s.saveStudent();
                        break;
                    case 2:
                        s.updatePassword();
                        break;
                    case 3:
                        s.deleteStudent();
                        break;
                    case 4:
                        s.searchStudent();
                        break;
                    case 5:
                        s.showAll();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("please enter a valid number");
                }

            }while(choice!=6);
            System.out.println("Thanks for using application");

        }catch (Exception e){
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
    public void getStudentDetail(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Student Name:");
        name = input.nextLine();
        System.out.print("Enter Student Email:");
        email = input.nextLine();
        System.out.print("Enter Student Password:");
        password = input.nextLine();
        System.out.print("Enter Student Country:");
        country = input.nextLine();
        System.out.print("Enter Student Total Marks:");
        mark = input.nextInt();
        System.out.print("Enter Student Age:");
        age = input.nextInt();

    }

    //method takes data from user and save into database

    public void saveStudent() throws SQLException {
        //call the database class
        DbmsConnection db=new DbmsConnection();
        Connection con=db.getConnection("postgres","Gariplerden65");
        String sql="insert into student values (?,?,?,?,?,?)";

        //
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,name);
        stmt.setString(2,email);
        stmt.setString(3,password);
        stmt.setString(4,country);
        stmt.setInt(5,mark);
        stmt.setInt(6,age);
        stmt.executeUpdate();
        System.out.println("data has been saved successfully");

    }

    //method to update the password
    public void updatePassword() throws SQLException {
        DbmsConnection db=new DbmsConnection();
        Connection con=db.getConnection("postgres","Gariplerden65");
        System.out.println("please enter your email");
        Scanner input=new Scanner(System.in);
        String inputemail=input.nextLine();

        System.out.println("Enter your new password");
        String newpass=input.nextLine();

        String sql= "update student set password=? where email=?";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,newpass);
        stmt.setString(2,inputemail);

        int i=stmt.executeUpdate();

        if (i==0){

            System.out.println("The email does not exist");
        }else{
            System.out.println("password has been updated Successfully");
        }
    }

    //method to delete student

    public void deleteStudent() throws SQLException {
        DbmsConnection db=new DbmsConnection();
        Connection con=db.getConnection("postgres","Gariplerden65");
        System.out.println("please enter the student's email");
        Scanner input=new Scanner(System.in);
        String inputemail=input.nextLine();
        String sql= "delete from student where email=?;";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,inputemail);
        int i=stmt.executeUpdate();

        if (i>0){

            System.out.println("record has been deleted Successfully");
        }else{
            System.out.println("The email does not exist");
        }


    }

    // method to make a search for any student

    public void searchStudent() throws SQLException {
        DbmsConnection db=new DbmsConnection();
        Connection con=db.getConnection("postgres","Gariplerden65");
        System.out.println("please enter the student's name");
        Scanner input=new Scanner(System.in);
        String inputname=input.nextLine();

        String sql = "select * from student where name = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,inputname);
        ResultSet rs = stmt.executeQuery();
        //if we are getting no record
        while(rs.next()){
            //print all the columns
            System.out.print(rs.getString("name")+" ");
            System.out.print(rs.getString("email")+" ");
            System.out.print(rs.getString("password")+" ");
            System.out.print(rs.getString("country")+" ");
            System.out.print(rs.getString("mark")+" ");
            System.out.println(rs.getString("age")+" ");
        }
    }

    // Method to show all students

    public void showAll() throws SQLException {
        DbmsConnection db=new DbmsConnection();
        Connection con=db.getConnection("postgres","Gariplerden65");
        String sql = "select * from student;";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        //if we are getting no record
        while(rs.next()){
            //print all the columns
            System.out.print(rs.getString("name")+" ");
            System.out.print(rs.getString("email")+" ");
            System.out.print(rs.getString("password")+" ");
            System.out.print(rs.getString("country")+" ");
            System.out.print(rs.getString("mark")+" ");
            System.out.println(rs.getString("age")+" ");
        }

    }




}

class DbmsConnection{
    //make a function to establish the connection
    public Connection getConnection(String user,String pass){

        Connection conn=null;

        try {
            //load the driver
            Class.forName("org.postgresql.Driver");
            //setting the connection
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/techprojdbc",user,pass);

            if (conn!=null){
                System.out.println("Connection established");
            }else{
                System.out.println("Connection failed");

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return conn;

    }

}