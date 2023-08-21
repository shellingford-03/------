package business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Nurse;
import models.Patient;
import tools.InputHandle;
import view.Menu;

public class PatientList extends HashMap<String, Patient> {

    public void addPatient(NurseList nList) {
        String id = "";
        while (true) {
            id = InputHandle.getString("Enter id: ", "[P****], * as a number", "P\\d{4}");
            if (this.containsKey(id)) {
                System.out.println("This staff already exist!");
            } else {
                break;
            }
        }
        String name = InputHandle.getString("Enter patient's name: ", "This field cannot be empty!");
        int age = InputHandle.getPositiveInt("Enter patient's age: ", "Must be a integer number!");
        String gender = InputHandle.getString("Enter patient's gender: ", "Male or Female", "(Male)|(Female)");
        String address = InputHandle.getString("Enter patient's address: ", "This field cannot be empty!");
        String phone = InputHandle.getString("Enter patient's phone: ", "0|(+84)*********", "^((0)|(\\+84))\\d{9}");
        String diagnosis = InputHandle.getString("Enter patient's diagnosis: ", "This field cannot be empty!");
        Date admissionDate = InputHandle.getDate("Enter admission date (dd/MM/yyyy): ");
        Date dischargDate = InputHandle.getDate("Enter discharge date (dd/MM/yyyy): ");
        HashMap<String, Nurse> n1 = new HashMap<>();
        HashMap<String, Nurse> n2 = new HashMap<>();
        
        
        while(n1.size()==0) {
            Nurse nurse = null;
            String nId1 = InputHandle.getString("Enter nurse assigned's id 1: ", "[N****], * as a number", "N\\d{4}");
            if ( (nurse = nList.find(nId1)) !=null && nurse.getPatientCount()<2  ) {
                n1.put(nId1, nurse); 
                nurse.incrementPatientCount();
            } else if((nurse = nList.find(nId1)) == null){
                System.out.println("This nurse does not exist");
            } else if(nurse.getPatientCount() >=2){
                System.out.println("This nurse already assigned 2 patients!");
            }
            
        }               
        
        while(n2.size()==0) {
            Nurse nurse = null;   
            boolean check = false ;
            String nId2 = InputHandle.getString("Enter nurse assigned's id 2: ", "[N****], * as a number", "N\\d{4}");
            if ( (nurse = nList.find(nId2)) !=null && nurse.getPatientCount()<2 ) {
                n2.put(nId2, nurse);
                nurse.incrementPatientCount();
                check = true;
            }
            if((nurse = nList.find(nId2)) == null){
                System.out.println("This nurse does not exist");
            } 
            if(n1.get(nId2)==n2.get(nId2) && n1.get(nId2)!=null){
                System.out.println("This nurse already chooseed in nurse 1!");
                n2.clear();
            }
            if(nurse.getPatientCount() >=2 && check == false){
                System.out.println("This nurse already assigned 2 patients!");
                n2.clear();
            }

        }  
        this.put(id, new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargDate, n1, n2));
    }

    public void displayPatient() {
        System.out.println("LIST OF PATIENT");
        Date startDate = InputHandle.getDate("Start date: ");
        Date endDate = InputHandle.getDate("End date: ");
        String title = "No.|Patient Id|Admission Date|Full Name       |Phone       |Diagnosis";
        System.out.println(title);
        HashMap<Integer, Patient> displayingList = new HashMap<>();
        List<Map.Entry<String, Patient>> _pList = new ArrayList<>(this.entrySet());
        Collections.sort(_pList, (Map.Entry<String, Patient> o1, Map.Entry<String, Patient> o2) -> (o1.getValue().getAddmissionDate().compareTo(o2.getValue().getAddmissionDate())));
        int count = 1;
        for (Map.Entry<String, Patient> item : _pList) {
            Date eleDate = item.getValue().getAddmissionDate();
            if (startDate.before(eleDate) && eleDate.before(endDate)) {
                displayingList.put(count, item.getValue());
                count++;
            }
        }
        for (Map.Entry<Integer, Patient> item : displayingList.entrySet()) {
            String msg = String.format("%-3d|", item.getKey());
            System.out.print(msg);
            item.getValue().show();
        }
    }

    public void sortPatient(){
        System.out.println("LIST OF PATIENT");
        Menu sortingBy = new Menu("Sort by:") ;
        sortingBy.addOption("Patient Id");
        sortingBy.addOption("Name");
        Menu sortingOrder = new Menu("Sort order:");
        sortingOrder.addOption("ASC");
        sortingOrder.addOption("DESC");
        sortingBy.printMenu();
        int sBy = sortingBy.getChoice() ;
        sortingOrder.printMenu();
        boolean sOr = sortingOrder.getChoice() == 1 ;
        List<Entry<String,Patient>> displayList = new ArrayList<>(this.entrySet()) ;
        switch(sBy){
            case 1:{
                if (sOr){
                    Collections.sort(displayList,
                            (Entry<String,Patient> o1 , Entry<String,Patient> o2) -> (o1.getValue().getId().compareTo(o2.getValue().getId()))) ;
                }else{
                    Collections.sort(displayList,
                            (Entry<String,Patient> o1 , Entry<String,Patient> o2) -> -(o1.getValue().getId().compareTo(o2.getValue().getId()))) ;
                }
            }
           
            case 2:{
                if (sOr){
                    Collections.sort(displayList,
                            (Entry<String,Patient> o1 , Entry<String,Patient> o2) -> (o1.getValue().getName().compareTo(o2.getValue().getName()))) ;
                }else{
                    Collections.sort(displayList,
                            (Entry<String,Patient> o1 , Entry<String,Patient> o2) -> -(o1.getValue().getName().compareTo(o2.getValue().getName()))) ;
                }
            }
            
        }
        String title = "No.|Patient Id|Admission Date|       Full Name|Phone       |Diagnosis" ;
        System.out.println(title);
        int count = 1 ;
        for(Entry<String,Patient> item : displayList){
            String msg = String.format("%-3d|",count) ;
            count++ ;
            System.out.print(msg);
            item.getValue().show();
        }
    }
    
    public void findPatient() {
        String srch = InputHandle.getString("Enter patient's name or part of the name: ", "Cannot be empty!");
        ArrayList<Patient> foundPatient = new ArrayList<>();

        for (Entry<String, Patient> item : this.entrySet()) {
            String name = item.getValue().getName();
            if (srch.equals(name) || name.contains(srch)) {
                foundPatient.add(item.getValue());
            }
        }

        if (!foundPatient.isEmpty()) {
            System.out.println("Found patients:");
            System.out.println("Patient Id|Admission Date|       Full Name|Phone       |Diagnosis");         
            for (Patient patient : foundPatient) {               
                patient.show();
            }
        } else {
            System.out.println("No nurses found!");
        }
    }
    
    public void findPatientByPhone() {
        String srch = InputHandle.getString("Enter patient's phone: ", "Cannot be empty!");
        ArrayList<Patient> foundPatient = new ArrayList<>();

        for (Entry<String, Patient> item : this.entrySet()) {
            String phone = item.getValue().getPhone();
            if (srch.equals(phone) || phone.contains(srch)) {
                foundPatient.add(item.getValue());
            }
        }

        if (!foundPatient.isEmpty()) {
            System.out.println("Found patients:");
            System.out.println("Patient Id|Admission Date|       Full Name|Phone       |Diagnosis");         
            for (Patient patient : foundPatient) {               
                patient.show();
            }
        } else {
            System.out.println("No nurses found!");
        }
    }
    
    
    
    
    
    
}
