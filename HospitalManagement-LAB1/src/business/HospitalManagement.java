package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import models.Nurse;
import models.Patient;
import tools.FileHandle;
import tools.InputHandle;
import view.Menu;

public class HospitalManagement {

    NurseList nList = new NurseList();
    PatientList pList = new PatientList();
    private final String nursePath = "\\src\\files\\nurses.dat";
    private final String patientPath = "\\src\\files\\patients.dat";

    public void addNewNurse() {
        nList.addNewNurse();
    }

    public void findNurse() {
        nList.findNurse();
    }

    public void updateNurse() {
        nList.updateNurse();
    }

    public void deleteNurse() {
        nList.deleteNurse(pList);
    }

    public void addPatient() {
        pList.addPatient(nList);
    }
    
    public void findPatient(){
        pList.findPatient();
    }
    
    public void findPatientByPhone(){
        pList.findPatientByPhone();
    }

    public void displayPatient() {
        pList.displayPatient();
    }

    public void sortPatient() {
        pList.sortPatient();
    }

    public void saveData() {
        ArrayList<String> dta = new ArrayList<>();
        for (Entry<String, Nurse> item : nList.entrySet()) {
            dta.add(item.getValue().toString());
        }
        FileHandle.writeToFile(nursePath, dta);
        dta.clear();
        for (Entry<String, Patient> item : pList.entrySet()) {
            dta.add(item.getValue().toString());
        }
        FileHandle.writeToFile(patientPath, dta);
        System.out.println("Data saved successfully!");
    }
    
    public void printNurseList(){
        System.out.println("LIST OF NURSE");
        System.out.println("ID     |Name      |Age  |Gender |Address   |phone         |department  |shift  |salary     ");
        for(Nurse nurse : nList.values()){
            nurse.show();
        }
    }
      
    public void printNursePatientCount() {
        for (Nurse nurse : nList.values()) {
            System.out.println("ID y tá: " + nurse.getId() + ", Số lượng bệnh nhân: " + nurse.getPatientCount());
        }
    }

    public void printNurseShiftList(){
        for(Nurse nurse: nList.values()){
            System.out.println(nurse.getShift());
        }
    }
     
    public void loadData() {
        nList.clear();
        pList.clear();
        ArrayList<String> dta = new ArrayList<>();
        dta.addAll(FileHandle.readFromFile(nursePath));
        dta.addAll(FileHandle.readFromFile(patientPath));
        for (String item : dta) {
            String lineSplit[] = item.trim().split(",");
            if (lineSplit[0].matches("N\\d{4}")) {
                nList.put(lineSplit[0],
                        new Nurse(
                                lineSplit[0],
                                lineSplit[1],
                                Integer.parseInt(lineSplit[2]),
                                lineSplit[3],
                                lineSplit[4],
                                lineSplit[5],
                                lineSplit[6],
                                lineSplit[7],
                                Double.parseDouble(lineSplit[8])));
            } else if (lineSplit[0].matches("P\\d{4}")) {
                String[] nl1 = lineSplit[9].split("\\|");
                String[] nl2 = lineSplit[10].split("\\|");
                HashMap<String, Nurse> n1 = new HashMap<>();
                HashMap<String, Nurse> n2 = new HashMap<>();
                
                for(String n : nl1){
                    String nurseId = n.trim().replace("|", "");
                    Nurse nurse = nList.find(nurseId);
                    n1.put(nurseId, nList.find(nurseId));
                    if (nurse != null) {
                        nurse.incrementPatientCount();
                    }
                }
                
                
                for(String n : nl2){
                    String nurseId = n.trim().replace("|", "");
                    Nurse nurse = nList.find(nurseId);
                    n2.put(nurseId, nList.find(nurseId));
                    if (nurse != null) {
                        nurse.incrementPatientCount();
                    }
                }
                
                
                
                pList.put(lineSplit[0],
                        new Patient(
                                lineSplit[0],
                                lineSplit[1],
                                Integer.parseInt(lineSplit[2]),
                                lineSplit[3],
                                lineSplit[4],
                                lineSplit[5],
                                lineSplit[6],
                                Patient.toDate(lineSplit[7]),
                                Patient.toDate(lineSplit[8]),
                                new HashMap<>(n1), new HashMap<>(n2)))
                        ;
                
            }
        }
    }
      
    public void deleteNurseByShift() {
        String shift = InputHandle.getString("Enter shift: ", "[*h]");
        List<String> nursesToDelete = new ArrayList<>();   
        for (Entry<String, Nurse> entry : nList.entrySet()) {
            String nurseId = entry.getKey();
            Nurse nurse = entry.getValue();
            if (nurse.getShift().equalsIgnoreCase(shift)) {
                boolean isAssigned = false;
                for (Entry<String, Patient> patientEntry : pList.entrySet()) {
                    Patient patient = patientEntry.getValue();
                    if (patient.getNursesAssigned1().containsKey(nurseId) || patient.getNursesAssigned2().containsKey(nurseId)) {
                        isAssigned = true;
                        break;
                    }
                }
                if (!isAssigned) {
                    nursesToDelete.add(nurseId);
                } else {
                    System.out.println("Nurse " + nurseId + " is currently assigned to a patient and cannot be deleted.");
                }
            }
        }  
        for (Entry<String, Patient> entry : pList.entrySet()) {
            Patient patient = entry.getValue();
            HashMap<String, Nurse> nursesAssigned1 = patient.getNursesAssigned1();
            HashMap<String, Nurse> nursesAssigned2 = patient.getNursesAssigned2();     
            nursesAssigned1.keySet().removeIf(nursesToDelete::contains);    
            nursesAssigned2.keySet().removeIf(nursesToDelete::contains);
        }    
        for (String nurseId : nursesToDelete) {      
            if (nList.containsKey(nurseId)) {
                System.out.println("Nurse found:\n" + nurseId.toString());
                if(Menu.getYesOrNo("Do you want to delete this nurse?")){
                    nList.remove(nurseId);
                }
                System.out.println("Delete successful!");
            } else {
                System.out.println("Nurse " + nurseId + " does not exist.");
            }
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
