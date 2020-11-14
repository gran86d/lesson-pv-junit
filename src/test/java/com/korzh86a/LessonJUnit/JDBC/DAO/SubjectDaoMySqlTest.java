package com.korzh86a.LessonJUnit.JDBC.DAO;

import com.korzh86a.LessonJUnit.JDBC.DAO.Exception.DaoException;
import com.korzh86a.LessonJUnit.JDBC.DTO.MarkDto;
import com.korzh86a.LessonJUnit.JDBC.DTO.StudentDto;
import com.korzh86a.LessonJUnit.JDBC.DTO.SubjectDto;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.*;

import javax.security.auth.Subject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubjectDaoMySqlTest {
    private static SubjectDaoMySql subjectDaoMySql;
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
            subjectDaoMySql = new SubjectDaoMySql();

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
    void getAllSubjects() {
        try {
            List<SubjectDto> allSubjects = subjectDaoMySql.getAllSubjects();

            List<SubjectDto> test = new ArrayList<>();
            test.add(new SubjectDto(1,"biology"));
            test.add(new SubjectDto(2,"chemistry"));
            test.add(new SubjectDto(3,"math"));
            test.add(new SubjectDto(4,"literature"));
            test.add(new SubjectDto(5,"physics"));

            assertEquals(test, allSubjects);
        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void getSubject() {
        try {
            SubjectDto subject = subjectDaoMySql.getSubject(2);

            SubjectDto test = new SubjectDto("chemistry");

            assertEquals(test, subject);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(3)
    void update() {
        try {
            SubjectDto test = new SubjectDto(4,"english");

            subjectDaoMySql.update(test);

            SubjectDto subject = subjectDaoMySql.getSubject(4);

            assertEquals(test, subject);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(4)
    void add() {
        try {
            SubjectDto test = new SubjectDto("astronomy");

            subjectDaoMySql.add(test);

            SubjectDto subject = subjectDaoMySql.getSubject(6);

            assertEquals(test, subject);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(5)
    void removeSubjectById() {
        try {
            int test = 5;

            subjectDaoMySql.removeSubjectById(1);
            int amountOfSubjects = subjectDaoMySql.getAllSubjects().size();

            assertEquals(test, amountOfSubjects);

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
            if (subjectDaoMySql != null) {
                subjectDaoMySql.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}