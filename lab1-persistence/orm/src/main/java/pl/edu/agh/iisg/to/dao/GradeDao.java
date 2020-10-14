package pl.edu.agh.iisg.to.dao;

import org.hibernate.Session;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.SessionService;

public class GradeDao extends GenericDao<Grade> {

    public boolean gradeStudent(final Student student, final Course course, final float grade) {
        Grade gr = new Grade(student,course,grade);
        student.gradeSet().add(gr);
        course.gradeSet().add(gr);

        Session session = SessionService.getSession();
        session.save(gr);
        return true;
    }
}
