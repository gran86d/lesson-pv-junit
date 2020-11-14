package com.korzh86a.LessonJUnit.JDBC.DAO;



import com.korzh86a.LessonJUnit.JDBC.DAO.Exception.DaoException;
import com.korzh86a.LessonJUnit.JDBC.DTO.MarkDto;
import com.korzh86a.LessonJUnit.JDBC.DTO.StudentDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkDaoMySql extends DaoMySql implements AutoCloseable {
    private static final String GET_MARK = "select id, STUDENT_ID, " +
            "SUBJECT_ID, MARK from marks WHERE id = ?";
    private static final String UPDATE_MARK = "UPDATE marks SET mark = ? WHERE id = ?";
    private static final String ADD_MARK = "INSERT INTO marks (student_id, subject_id, mark) " +
            "VALUE (?, ?, ?)";
    private static final String ADD_SUBJECT_TO_STUDENT = "INSERT INTO marks (student_id, subject_id) " +
            "VALUE (?, ?)";
    private static final String REMOVE_MARK = "DELETE FROM marks WHERE id = ?";

    private PreparedStatement preparedStatementGetMark;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementAdd;
    private PreparedStatement preparedStatementAddSubjectToStudent;
    private PreparedStatement preparedStatementRemove;

    public MarkDaoMySql() throws DaoException {
    }

    public MarkDto getMark(int markId) throws DaoException {
        MarkDto markDto = null;
        ResultSet resultSet = null;

        try {
            preparedStatementGetMark = getStatement(preparedStatementGetMark, GET_MARK);
            preparedStatementGetMark.setInt(1, markId);
            resultSet = preparedStatementGetMark.executeQuery();

            if (resultSet.next()) {
                markDto = new MarkDto(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4));
            }
        } catch (SQLException e) {
            throw new DaoException("Problem with getStudent()", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Problem with closing resultSet");
                e.printStackTrace();
            }
        }
        return markDto;
    }

    public void update(MarkDto markDto) throws DaoException {
        try {
            preparedStatementUpdate = getStatement(preparedStatementUpdate, UPDATE_MARK);
            preparedStatementUpdate.setInt(1, markDto.getMark());
            preparedStatementUpdate.setInt(2, markDto.getId());
            preparedStatementUpdate.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with updateMark()", e);
        }
    }

    public void add(MarkDto markDto) throws DaoException {
        try {
            preparedStatementAdd = getStatement(preparedStatementAdd, ADD_MARK);
            preparedStatementAdd.setInt(1, markDto.getStudentId());
            preparedStatementAdd.setInt(2, markDto.getSubjectId());
            preparedStatementAdd.setInt(3, markDto.getMark());
            preparedStatementAdd.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with addMark()", e);
        }
    }

    public void addSubjectToStudent(MarkDto markDto) throws DaoException {
        try {
            preparedStatementAddSubjectToStudent = getStatement(preparedStatementAddSubjectToStudent,
                    ADD_SUBJECT_TO_STUDENT);
            preparedStatementAddSubjectToStudent.setInt(1, markDto.getStudentId());
            preparedStatementAddSubjectToStudent.setInt(2, markDto.getSubjectId());
            preparedStatementAddSubjectToStudent.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with addMark()", e);
        }
    }

    public void removeStudentById(int markId) throws DaoException {
        try {
            preparedStatementRemove = getStatement(preparedStatementRemove, REMOVE_MARK);
            preparedStatementRemove.setInt(1, markId);
            preparedStatementRemove.execute();
        } catch (SQLException e) {
            throw new DaoException("Problem with removeMark()", e);
        }
    }

    @Override
    public void close() {
        try {
            if (preparedStatementGetMark != null) {
                preparedStatementGetMark.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementUpdate != null) {
                preparedStatementUpdate.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementAdd != null) {
                preparedStatementAdd.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementAddSubjectToStudent != null) {
                preparedStatementAddSubjectToStudent.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (preparedStatementRemove != null) {
                preparedStatementRemove.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing Statement");
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Problem with closing connection");
            e.printStackTrace();
        }
    }
}


