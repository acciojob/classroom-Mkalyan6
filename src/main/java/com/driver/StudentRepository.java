package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {

    // For student data
    HashMap<String,Student>StudentsList=new HashMap<>();
    // For teachers data
    HashMap<String,Teacher>TeacherList=new HashMap<>();
    //For teacher and student pairs
    HashMap<String, List<String>>StudentListForTeacher=new HashMap<>();

    public void addStudent(Student student) {
        String name=student.getName();
        if(!StudentsList.containsKey(name)){
            StudentsList.put(name,student);
        }
    }

    public void addTeacher(Teacher teacher) {
        String name=teacher.getName();
        if(!TeacherList.containsKey(name)){
            TeacherList.put(name,teacher);
        }
    }

    public void addStudentTeacherPair(String student, String teacher) {
        // Check if teacher is present in our lists of teachers
        if(TeacherList.containsKey(teacher)){

            List<String>listForteacher=StudentListForTeacher.getOrDefault(teacher,new ArrayList<>());
            if(StudentsList.containsKey(student)) {
               if(!listForteacher.contains(student)){
                   listForteacher.add(student);
                   StudentListForTeacher.put(teacher,listForteacher);
                   TeacherList.get(teacher).setNumberOfStudents(listForteacher.size());
                }
            }
        }
    }

    public Student getStudentByName(String name) {
        if(StudentsList.containsKey(name)){
            return StudentsList.get(name);
        }
        return null;
    }

    public Teacher getTeacherByName(String name) {
        if(TeacherList.containsKey(name)){
            return TeacherList.get(name);
        }
        return null;
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        if(StudentListForTeacher.containsKey(teacher)){
            return StudentListForTeacher.get(teacher);
        }
           return null;
    }

    public List<String> getAllStudents() {
       List<String>studentList=new ArrayList<>();
       if(StudentsList.size()==0)return null;
       for(Map.Entry<String,Student>mapElement: StudentsList.entrySet()){
           String name=mapElement.getKey();
           studentList.add(name);
       }
       return studentList;
    }

    public void deleteTeacherByName(String teacher) {
        if(StudentListForTeacher.containsKey(teacher)){
            // First delete the teacher data base from the list of teachers;
            TeacherList.remove(teacher);
            StudentListForTeacher.remove(teacher);

        }
    }

    public void deleteAllTeachers() {
        // delete teachers list
        TeacherList.clear();
        StudentListForTeacher.clear();
    }
}
