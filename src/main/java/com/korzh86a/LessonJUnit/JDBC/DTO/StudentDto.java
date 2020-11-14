package com.korzh86a.LessonJUnit.JDBC.DTO;

import java.util.Objects;

public class StudentDto {
    private int id;
    private String firstName;
    private String secondName;
    private String birthDate;
    private String enterYear;

    public StudentDto() {
    }
    public StudentDto(int id, String firstName, String secondName, String birthDate, String enterYear) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.enterYear = enterYear;
    }
    public StudentDto(String firstName, String secondName, String birthDate, String enterYear) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.enterYear = enterYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEnterYear() {
        return enterYear;
    }

    public void setEnterYear(String enterYear) {
        this.enterYear = enterYear;
    }

    @Override
    public String toString() {
        return firstName + " " + secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(enterYear, that.enterYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, birthDate, enterYear);
    }
}
