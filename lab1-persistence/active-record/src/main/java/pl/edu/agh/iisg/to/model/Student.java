package pl.edu.agh.iisg.to.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import pl.edu.agh.iisg.to.executor.QueryExecutor;

public class Student {
    private final int id;

    private final String firstName;

    private final String lastName;

    private final int indexNumber;

    Student(final int id, final String firstName, final String lastName, final int indexNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.indexNumber = indexNumber;
    }

    public static Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        String insertSql = "INSERT INTO student (first_name, last_name, index_number) VALUES (?, ?, ?)";

        Object[] args = {
                firstName,
                lastName,
                indexNumber
        };

        try {
            int id = QueryExecutor.createAndObtainId(insertSql, args);
            return Student.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Student> findByIndexNumber(final int indexNumber) {
        String findByIdSql = "SELECT * FROM Student WHERE index_number = ?";
        return find(indexNumber, findByIdSql);
    }

    public static Optional<Student> findById(final int id) {
        String sql = "SELECT * FROM student WHERE id = (?)";
        return find(id, sql);
    }

    private static Optional<Student> find(int value, String sql) {
        Object[] args = {value};
        try {
            ResultSet rs = QueryExecutor.read(sql, args);
            return Optional.of(new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("index_number")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Map<Course, Float> createReport() {
        String findCourseListSql = "SELECT c.id as course_id, avg(g.grade) as avg FROM course AS c " +
                "JOIN grade AS g ON g.course_id = c.id " +
                "WHERE c.id in (SELECT course_id FROM Student_Course WHERE student_id = (?)) " +
                "AND g.student_id = ? " +
                "GROUP BY c.id";

        Object[] args = {
                this.id,
                this.id
        };

        Map<Course, Float> map = new HashMap<>();

        ResultSet resultSet = null;
        try {
            resultSet = QueryExecutor.read(findCourseListSql, args);

            while (resultSet.next()) {
                var course = Course.findById(resultSet.getInt("course_id")).get();
                var avg = resultSet.getFloat("avg");
                map.put(course, avg);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return map;
    }

    public int id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public int indexNumber() {
        return indexNumber;
    }

    public static class Columns {

        public static final String ID = "id";

        public static final String FIRST_NAME = "first_name";

        public static final String LAST_NAME = "last_name";

        public static final String INDEX_NUMBER = "index_number";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Student student = (Student) o;

        if (id != student.id)
            return false;
        if (indexNumber != student.indexNumber)
            return false;
        if (!firstName.equals(student.firstName))
            return false;
        return lastName.equals(student.lastName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + indexNumber;
        return result;
    }
}
