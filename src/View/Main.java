package View;

import Controller.Manager;
import Controller.Validation;
import Model.Student;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Student> ls = new ArrayList<>();
        Validation validation = new Validation();
        ls.add(new Student("1", "Nguyen Khanh", "Spring", "c/c++"));
        ls.add(new Student("2", "Nguyen Cong Hoai Bao", "Summer", ".net"));
        ls.add(new Student("3", "Tran Phuoc Tuan", "Spring", "c/c++"));
        ls.add(new Student("3", "Duong Dai Nhan", "Spring", "Java"));
        int count = 3;
        while (true) {
            Manager.menu();
            int choice = validation.checkInputIntLimit(1, 5);
            switch (choice) {
                case 1:
                    Manager.createStudent(count, ls);
                    break;
                case 2:
                    Manager.findAndSort(ls);
                    break;
                case 3:
                    Manager.updateOrDelete(count, ls);
                    break;
                case 4:
                    Manager.displayTotalCoursesReport(ls);
                    break;

                case 5:
                    return;
            }

        }
    }

}
