package com.korzh86a.LessonJUnit.JDBC.DAO;

import com.korzh86a.LessonJUnit.JDBC.DAO.Exception.DaoException;
import com.korzh86a.LessonJUnit.JDBC.DTO.MarkDto;
import com.korzh86a.LessonJUnit.JDBC.DTO.StudentDto;
import com.korzh86a.LessonJUnit.JDBC.DTO.SubjectDto;
import org.junit.jupiter.api.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import org.apache.ibatis.jdbc.ScriptRunner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentDaoMySqlTest {
    private static StudentDaoMySql studentDaoMySql;
    private static Connection connection;
    private static ScriptRunner sr;
    private static Reader reader;
    private static Reader closeReader;

    @BeforeAll
    static void init(){
        String pass;
        String user_name;
        String url;
        try {
            studentDaoMySql = new StudentDaoMySql();

            ResourceBundle bundle = ResourceBundle.getBundle("app");
            pass = bundle.getString("PASS");
            user_name = bundle.getString("USER_NAME");
            url = bundle.getString("URL");
            Class.forName(bundle.getString("Driver"));
            connection = DriverManager.getConnection(url, user_name, pass);

            sr = new ScriptRunner(connection);
            reader = new BufferedReader(new FileReader("src/test/resources/fill_db_test.sql"));
            sr.runScript(reader);

        } catch (SQLException | IOException | ClassNotFoundException t) {
            t.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void getAllStudents() {
        try {
            List<StudentDto> allStudents = studentDaoMySql.getAllStudents();

            List<StudentDto> test = new ArrayList<>();
            test.add(new StudentDto(1,"Pavel","Soloviev","1985-03-09","1991"));
            test.add(new StudentDto(2,"Sergei","Pavlov","1985-10-06","1992"));
            test.add(new StudentDto(3,"Valik","Durov","1987-01-26","1993"));

            assertEquals(test, allStudents);
        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void getStudent() {
        try {
            StudentDto studentDto = studentDaoMySql.getStudent(2);

            StudentDto test = new StudentDto(2,"Sergei","Pavlov","1985-10-06","1992");

            assertEquals(test, studentDto);
        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(3)
    void getStudentMarks() {
        try {
            StudentDto studentDto = new StudentDto(
                    2,"Sergei","Pavlov","1985-10-06","1992");
            Map<SubjectDto, List<MarkDto>> studentMarks = studentDaoMySql.getStudentMarks(studentDto);

            Map<SubjectDto, List<MarkDto>> test = new HashMap<>();

            test.put(new SubjectDto("biology"), List.of(new MarkDto(7)));
            test.put(new SubjectDto( "chemistry"), List.of(new MarkDto(7)));
            test.put(new SubjectDto("literature"), List.of(new MarkDto(4)));

            assertEquals(test, studentMarks);
        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(4)
    void getStudentMarksById() {
        try {
            Map<SubjectDto, List<MarkDto>> studentMarks = studentDaoMySql.getStudentMarksById(2);

            Map<SubjectDto, List<MarkDto>> test = new HashMap<>();

            test.put(new SubjectDto("biology"), List.of(new MarkDto(7)));
            test.put(new SubjectDto( "chemistry"), List.of(new MarkDto(7)));
            test.put(new SubjectDto("literature"), List.of(new MarkDto(4)));

            assertEquals(test, studentMarks);
        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(5)
    void update() {
        try {
            StudentDto test = new StudentDto(2,"Ivan","Padlov","1988-11-09","1989");

            studentDaoMySql.update(test);

            StudentDto studentDto = studentDaoMySql.getStudent(2);

            assertEquals(test, studentDto);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    @Order(6)
    void add() {
        try {
            StudentDto test = new StudentDto("Ivan","Padlov","1988-11-09","1989");

            studentDaoMySql.add(test);

            StudentDto studentDto = studentDaoMySql.getStudent(4);

            assertEquals(test, studentDto);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(7)
    void removeStudentById() {
        try {
            int test = 3;

            studentDaoMySql.removeStudentById(2);
            int amountOfStudents = studentDaoMySql.getAllStudents().size();

            assertEquals(test, amountOfStudents);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterAll
    static void closeResources(){
        try {
            closeReader = new BufferedReader(new FileReader("src/test/resources/drop_db_test.sql"));
            sr.runScript(closeReader);

            if (reader != null) {
                reader.close();
            }
            if (closeReader != null) {
                closeReader.close();
            }
            if (sr != null) {
                sr.closeConnection();
            }
            if (connection != null) {
                connection.close();
            }
            if (studentDaoMySql != null) {
                studentDaoMySql.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}