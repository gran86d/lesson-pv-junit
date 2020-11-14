package com.korzh86a.LessonJUnit.JDBC.DAO;

import com.korzh86a.LessonJUnit.JDBC.DAO.Exception.DaoException;
import com.korzh86a.LessonJUnit.JDBC.DTO.MarkDto;
import com.korzh86a.LessonJUnit.JDBC.DTO.StudentDto;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MarkDaoMySqlTest {
    private static MarkDaoMySql markDaoMySql;
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
            markDaoMySql = new MarkDaoMySql();

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
    void getMark() {
        try {
            MarkDto markDto = markDaoMySql.getMark(4);

            MarkDto test = new MarkDto(4, 2, 1, 7);

            assertEquals(test, markDto);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void update() {
        try {
            MarkDto test = new MarkDto(4, 2, 1, 9);

            markDaoMySql.update(test);

            MarkDto markDto = markDaoMySql.getMark(4);

            assertEquals(test, markDto);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(3)
    void add() {
        try {
            MarkDto test = new MarkDto(2, 1, 9);

            markDaoMySql.add(test);

            MarkDto markDto = markDaoMySql.getMark(10);

            assertEquals(test, markDto);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(4)
    void addSubjectToStudent() {
        try {
            MarkDto test = new MarkDto(2, 1, 0);

            markDaoMySql.addSubjectToStudent(test);

            MarkDto markDto = markDaoMySql.getMark(11);

            assertEquals(test, markDto);

        } catch (DaoException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(5)
    void removeStudentById() {
        try {
            markDaoMySql.removeStudentById(1);

            MarkDto markDto = markDaoMySql.getMark(1);

            assertEquals(null, markDto);

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
            if (markDaoMySql != null) {
                markDaoMySql.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}