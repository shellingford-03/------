
package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

public class Patient extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String diagnosis;
    private Date addmissionDate;
    private Date dischargeDate;
    private HashMap<String,Nurse> nursesAssigned1 = new HashMap<>() ;
    private HashMap<String,Nurse> nursesAssigned2 = new HashMap<>() ;
    
    public Patient() {
    }

    public Patient(String id, String name, int age, String gender, String address, String phone, String diagnosis,
            Date addmissionDate, Date dischargeDate , HashMap<String,Nurse> nursesAssigned1, HashMap<String,Nurse> nursesAssigned2) {
        super(id, name, age, gender, address, phone);
        this.diagnosis = diagnosis;
        this.addmissionDate = addmissionDate;
        this.dischargeDate = dischargeDate;
        this.nursesAssigned1 = nursesAssigned1 ;
        this.nursesAssigned2 = nursesAssigned2;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Date getAddmissionDate() {
        return addmissionDate;
    }

    public void setAddmissionDate(Date addmissionDate) {
        this.addmissionDate = addmissionDate;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public HashMap<String, Nurse> getNursesAssigned1() {
        return nursesAssigned1;
    }

    public void setNursesAssigned1(HashMap<String, Nurse> nursesAssigned1) {
        this.nursesAssigned1 = nursesAssigned1;
    }
    
    public HashMap<String, Nurse> getNursesAssigned2() {
        return nursesAssigned2;
    }
    
    public void setNursesAssigned2(HashMap<String, Nurse> nursesAssigned2) {
        this.nursesAssigned2 = nursesAssigned2;
    }

    public String formatPrintDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy") ;
        String ret = sdf.format(date) ;
        return ret;
    }
    
    public static Date toDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy") ;
        try{
            Date ret = sdf.parse(date) ;
            return ret ;
        }catch(Exception e){}
        return null ;
    }
    
    @Override
    public void show() {
        String str = String.format("%-10s|%-14s|%-16s|%-12s|%-15s", 
                id, formatPrintDate(addmissionDate), name, phone, diagnosis);
        System.out.println(str);
    }
    
    private String toStringNursesAssigned1(){
        String str = "";
        for(Entry<String,Nurse> item : nursesAssigned1.entrySet()){
            str += String.format("%s|",item.getKey());
        }
        
        return str ;
    }
    
    private String toStringNursesAssigned2(){
        String str = "";
        for(Entry<String,Nurse> item : nursesAssigned2.entrySet()){
            str += String.format("%s|",item.getKey());
        }
        
        return str ;
    }
    
    @Override
    public String toString() {
        String str = String.format("%s,%s,%d,%s,%s,%s,%s,%s,%s,%s,%s",
                id, name, age, gender, address, phone, diagnosis, formatPrintDate(addmissionDate), formatPrintDate(dischargeDate),
                toStringNursesAssigned1(), toStringNursesAssigned2());
        return str;
    }
}
