package business;

import java.util.ArrayList;
import java.util.HashMap;
import models.Nurse;
import models.Patient;
import tools.InputHandle;
import view.Menu;

public class NurseList extends HashMap<String, Nurse> {

    public void addNewNurse() {
        String id = "";
        while (true) {
            id = InputHandle.getString("Enter id: ", "[N****], * as a number", "N\\d{4}");
            if (this.containsKey(id)) {
                System.out.println("This staff already exist!");
                continue;
            } else {
                break;
            }
        }
        String name = InputHandle.getString("Enter nurse's name: ", "This field cannot be empty!");
        int age = InputHandle.getPositiveInt("Enter nurse's age: ", "Must be a integer number!");
        String gender = InputHandle.getString("Enter nurse's gender: ", "Male or Female", "(Male)|(Female)");
        String address = InputHandle.getString("Enter nurse's address: ", "This field cannot be empty!");
        String phone = InputHandle.getString("Enter nurse's phone: ", "[0|(+84)]*********", "^((0)|(\\+84))\\d{9}");
        String deparment = InputHandle.getString("Enter nusre's deparment: ", "Length of deparment must be in [3,50]", 3, 50);
        String shift = InputHandle.getString("Enter nurse's shift: ", "This field cannot be empty!");
        double salary = InputHandle.getPositiveReal("Enter nurse's salary: ", "Must be a real number!");
        this.put(id, new Nurse(id, name, age, gender, address, phone, deparment, shift, salary));
    }

    public void findNurse() {
        String srch = InputHandle.getString("Enter nurse's name or part of the name: ", "Cannot be empty!");
        ArrayList<Nurse> foundNurses = new ArrayList<>();

        for (Entry<String, Nurse> item : this.entrySet()) {
            String name = item.getValue().getName();
            if (srch.equals(name) || name.contains(srch)) {
                foundNurses.add(item.getValue());
            }
        }

        if (!foundNurses.isEmpty()) {
            System.out.println("Found nurses:");
            System.out.println("ID     |Name      |Age  |Gender |Address   |phone         |department  |shift  |salary     ");
            for (Nurse nurse : foundNurses) {               
                nurse.show();
            }
        } else {
            System.out.println("No nurses found!");
        }
    }

    public Nurse find(String id) {
        if (this.containsKey(id)) {
            return this.get(id);
        } else {
            return null;
        }
    }
       
    public void updateNurse() {
        String id = InputHandle.getString("Enter staff id: ", "[N****], * as a number", "N\\d{4}");
        Nurse n = find(id);
        if (n != null) {
            System.out.println("Nurse found:\n" + n.toString() + "\n Updating nurse [Enter blank for any field does not need to be updated]:");
            String name = InputHandle.getString("Enter nurse's name: ");
            if (name.trim().isEmpty()) {
                name = null;
            }
            String age = InputHandle.getString("Enter nurse's age: ");
            if (age.trim().isEmpty()) {
                age = null;
            }
            String gender = InputHandle.getString("Enter nurse's gender: ");
            if (gender.trim().isEmpty()) {
                gender = null;
            }
            String address = InputHandle.getString("Enter nurse's address: ");
            if (address.trim().isEmpty()) {
                address = null;
            }
            String phone = InputHandle.getString("Enter nurse's phone: ");
            if (phone.trim().isEmpty()) {
                phone = null;
            }
            String department = InputHandle.getString("Enter nurse's department: ");
            if (department.trim().isEmpty()) {
                department = null;
            }
            String shift = InputHandle.getString("Enter nurse's shift: ");
            if (shift.trim().isEmpty()) {
                shift = null;
            }
            String salary = InputHandle.getString("Enter nurse's salary: ");
            if (salary.trim().isEmpty()) {
                salary = null;
            }

            try {
                if (age != null && Integer.parseInt(age) <= 0) {
                    throw new Exception("Age must be a positive integer number");
                }
                if (gender != null && !gender.matches("(Male)|(Female)")) {
                    throw new Exception("Gender must be Male or Female");
                }
                if (phone != null && !phone.matches("^((0)|(\\+84))\\d{9}")) {
                    throw new Exception("Invalid phone number");
                }
                if (department != null && (department.length() < 3 || department.length() > 50)) {
                    throw new Exception("Department length must be in [3,50]");
                }
                if (salary != null && Double.parseDouble(salary) < 0) {
                    throw new Exception("Salary must be a non-negative real number");
                }
            } catch (Exception e) {
                System.out.println("Update failed! " + e.getMessage());
                return;
            }

            if (name != null) {
                n.setName(name);
            }
            if (age != null) {
                n.setAge(Integer.parseInt(age));
            }
            if (gender != null) {
                n.setGender(gender);
            }
            if (address != null) {
                n.setAddress(address);
            }
            if (phone != null) {
                n.setPhone(phone);
            }
            if (department != null) {
                n.setDeparment(department);
            }
            if (shift != null) {
                n.setShift(shift);
            }
            if (salary != null) {
                n.setSalary(Double.parseDouble(salary));
            }

            System.out.println("Update successfully");
        } else {
            System.out.println("The nurse does not exist");
        }
    }

    public void deleteNurse(PatientList pList) {
        String id = InputHandle.getString("Enter id: ", "[N****], * as a number", "N\\d{4}");
        Nurse n = find(id);
        if (n != null) {
            System.out.println("Nurse found:\n" + n.toString());
            if (Menu.getYesOrNo("Do you want to delete this nurse?")) {
                try {
                    for (Entry<String, Patient> item : pList.entrySet()) {
                        if (item.getValue().getNursesAssigned1().containsKey(n.getId()) || item.getValue().getNursesAssigned2().containsKey(n.getId())) {
                            throw new Exception("This nurse is having patient(s)");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Delete failed! " + e.getMessage());
                    return;
                } finally {
                    this.remove(n.getId(), n);
                    System.out.println("Delete successful!");
                }
            }
        } else {
            System.out.println("The nurse does not exist");
        }
    }
  
}
