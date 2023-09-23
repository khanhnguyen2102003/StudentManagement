package Controller;

import Model.Report;
import Model.Student;
import java.util.ArrayList;
import java.util.Collections;

public class Manager {

    public static void menu() {
        System.out.println("================ MENU ===============");
        System.out.println("||1.	    Create                 ||");
        System.out.println("||2.	    Find and Sort          ||");
        System.out.println("||3.	    Update/Delete          ||");
        System.out.println("||4.	    Report                 ||");
        System.out.println("||5.	    Exit                   ||");
        System.out.println("==========STUDENT MANAGEMENT=========");
        System.out.print(" Enter your choice: ");
    }

    public static void createStudent(int count, ArrayList<Student> ls) {
        if (count > 10) {
            System.out.print("Do you want to continue (Y/N)");
            if (!Validation.checkInputYN()) {
                return;
            }
        }

        while (true) {
            System.out.print("Enter id: ");
            String id = Validation.checkInputString();
            System.out.print("Enter name student: ");
            String name = Validation.checkInputString();
            if (!Validation.checkIdExist(ls, id, name)) {
                System.err.println("Id has existed for a student. Please re-enter.");
                continue;
            }
            System.out.print("Enter semester (Fall/Spring/Summer): ");
            String semester = Validation.checkInputSemester();
            System.out.print("Enter name course: ");
            String course = Validation.checkInputCourse();
            if (Validation.checkStudentExist(ls, id, name, semester, course)) {
                ls.add(new Student(id, name, semester, course));
                count++;
                System.out.println("Add student successfully.");
                return;
            }
            System.err.println("Duplicate.");
        }
    }
    

    public static void findAndSort(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        ArrayList<Student> listStudentFindByName = listStudentFindByName(ls);
        if (listStudentFindByName.isEmpty()) {
            System.err.println("Not exist.");
        } else {
            Collections.sort(listStudentFindByName);
            System.out.printf("%-15s%-15s%-15s\n", "Student name", "semester", "Course Name");
            for (Student student : listStudentFindByName) {
                student.print();
            }
        }
    }

    public static ArrayList<Student> listStudentFindByName(ArrayList<Student> ls) {
        ArrayList<Student> listStudentFindByName = new ArrayList<>();
        System.out.print("Enter name to search: ");
        String name = Validation.checkInputString();
        for (Student student : ls) {
            if (student.getStudentName().contains(name)) {
                listStudentFindByName.add(student);
            }
        }
        return listStudentFindByName;
    }

    public static void updateOrDelete(int count, ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.print("Enter id: ");
        String id = Validation.checkInputString();
        ArrayList<Student> listStudentFindByName = getListStudentById(ls, id);
        if (listStudentFindByName.isEmpty()) {
            System.err.println("Not found student.");
            return;
        } else {
            Student student = getStudentByListFound(listStudentFindByName);
            System.out.print("Do you want to update (U) or delete (D) student: ");
            if (Validation.checkInputUD()) {
                System.out.print("Enter id: ");
                String idStudent = Validation.checkInputString();
                System.out.print("Enter name student: ");
                String name = Validation.checkInputString();
                System.out.print("Enter semester: ");
                String semester = Validation.checkInputString();
                System.out.print("Enter name course: ");
                String course = Validation.checkInputCourse();
                if (!Validation.checkChangeInfomation(student, id, name, semester, course)) {
                    System.err.println("Nothing change.");
                }
                if (Validation.checkStudentExist(ls, id, name, semester, course)) {
                    student.setId(idStudent);
                    student.setStudentName(name);
                    student.setSemester(semester);
                    student.setCourseName(course);
                    System.err.println("Update success.");
                }
                return;
            } else {
                ls.remove(student);
                count--;
                System.err.println("Delete success.");
                return;
            }
        }
    }

    public static Student getStudentByListFound(ArrayList<Student> listStudentFindByName) {
        System.out.println("List student found: ");
        int count = 1;
        System.out.printf("%-10s%-15s%-15s%-15s\n", "Number", "Student name",
                "semester", "Course Name");
        for (Student student : listStudentFindByName) {
            System.out.printf("%-10d%-15s%-15s%-15s\n", count,
                    student.getStudentName(), student.getSemester(),
                    student.getCourseName());
            count++;
        }
        System.out.print("Enter student: ");
        int choice = Validation.checkInputIntLimit(1, listStudentFindByName.size());
        return listStudentFindByName.get(choice - 1);
    }

    public static ArrayList<Student> getListStudentById(ArrayList<Student> ls, String id) {
        ArrayList<Student> getListStudentById = new ArrayList<>();
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())) {
                getListStudentById.add(student);
            }
        }
        return getListStudentById;
    }

    public static void displayTotalCoursesReport(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }

        ArrayList<Report> reportList = generateTotalCoursesReport(ls);

        if (reportList.isEmpty()) {
            System.err.println("No report data available.");
            return;
        }

        System.out.printf("%-20s%-10s%-5s\n", "Student name", "Course", "Total");
        for (Report report : reportList) {
            System.out.printf("%-20s%-10s%-5d\n", report.getStudentName(), report.getCourseName(), report.getTotalCourse());
        }
    }

    public static ArrayList<Report> generateTotalCoursesReport(ArrayList<Student> ls) {
        ArrayList<Report> reportList = new ArrayList<>();

        for (Student student : ls) {
            String studentName = student.getStudentName();
            String courseName = student.getCourseName();
            int total = 0;

            for (Student s : ls) {
                if (s.getStudentName().equals(studentName) && s.getCourseName().equals(courseName)) {
                    total++;
                }
            }

            boolean exists = false;
            for (Report report : reportList) {
                if (report.getStudentName().equals(studentName) && report.getCourseName().equals(courseName)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                reportList.add(new Report(studentName, courseName, total));
            }
        }

        return reportList;
    }

}
