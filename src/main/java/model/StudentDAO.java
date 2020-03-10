package model;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String DB_NAME="hibernatetutdb";//"Your DB name"; i properties faila
    private static final String URL = "jdbc:postgresql://localhost/"+DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "rootroot";

    private static Connection conn;

    private static final String GET_STUDENT_QUERY="SELECT id, firstname, lastname, universitygroup, email FROM students WHERE id=?";
    private static final String GET_ALL_STUDENTS_QUERY="SELECT id, firstname, lastname, universitygroup, email FROM students";
    private static final String INSERT_NEW_INTO_STUDENTS_QUERY="INSERT INTO students (firstName, lastName, universityGroup, email) VALUES (?,?,?,?)";
    private static final String UPDATE_STUDENT_QUERY="UPDATE students SET firstName=?, lastName=?, universityGroup=?, email=? WHERE id= ?";
    private static final String DELETE_STUDENT_QUERY="DELETE FROM students WHERE id= ?";

    public static StudentDAO instance=new StudentDAO();

    private StudentDAO(){
        //dont let instantiate (Singleton)
    }

    public static StudentDAO getInstance(){
        return instance;
    }

    public boolean connect() {
        try {
            conn=DBUtil.getDataSource().getConnection(); //get connection from connection pool
//            conn=DriverManager.getConnection(URL,USER,PASSWORD);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean disconnect(){
        try{
            if(conn!=null){
                conn.close();
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public List<Student> getStudents(){
        List<Student> students=new ArrayList<>();
        try(PreparedStatement getAllStudentsQuery=conn.prepareStatement(GET_ALL_STUDENTS_QUERY)){
            ResultSet result=getAllStudentsQuery.executeQuery();
            while(result.next()){
                int id = result.getInt(1);
                String firstName = result.getString(2);
                String lastName = result.getString(3);
                String universityGroup = result.getString(4);
                String email = result.getString(5);
                students.add(new Student(id, firstName, lastName, universityGroup, email));
            }
        }catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
        return students;
    }


    public Student getStudentById(int id){
        Student student=null;
        try(PreparedStatement getStudentQuery=conn.prepareStatement(GET_STUDENT_QUERY)){
            getStudentQuery.setInt(1, id);
            ResultSet results=getStudentQuery.executeQuery();
            while(results.next()){
                int studentId=results.getInt(1);
                String firstName=results.getString(2);
                String lastName=results.getString(3);
                String universityGroup=results.getString(4);
                String email=results.getString(5);
                student=new Student(studentId, firstName, lastName, universityGroup, email);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return student;
    }


    public boolean createStudent(String firstName, String lastName, String universityGroup, String email){
        try(PreparedStatement insertNewIntoStudentsQuery=conn.prepareStatement(INSERT_NEW_INTO_STUDENTS_QUERY)){
            insertNewIntoStudentsQuery.setString(1, firstName);
            insertNewIntoStudentsQuery.setString(2, lastName);
            insertNewIntoStudentsQuery.setString(3, universityGroup);
            insertNewIntoStudentsQuery.setString(4, email);
            int executedRows=insertNewIntoStudentsQuery.executeUpdate();
            if(executedRows==1){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateStudent(Student oldStudent, String firstName, String lastName, String universityGroup, String email){
        try(PreparedStatement updateStudentQuery=conn.prepareStatement(UPDATE_STUDENT_QUERY)){
            int id=oldStudent.getId();
            updateStudentQuery.setString(1, firstName);
            updateStudentQuery.setString(2, lastName);
            updateStudentQuery.setString(3, universityGroup);
            updateStudentQuery.setString(4, email);
            updateStudentQuery.setInt(5, id);
            int updatedRows=updateStudentQuery.executeUpdate();
            if(updatedRows==1){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteStudent(Student student){
        try(PreparedStatement deleteStudentQuery=conn.prepareStatement(DELETE_STUDENT_QUERY)){
            deleteStudentQuery.setInt(1,student.getId());
            int deletedRows=deleteStudentQuery.executeUpdate();
            if(deletedRows==1){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
