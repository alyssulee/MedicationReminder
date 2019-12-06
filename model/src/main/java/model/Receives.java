package model;

import java.util.UUID;

public class Receives {
    private UUID pharmacistId;
    private UUID patientId;
    private UUID orderNum;

    public Receives(Pharmacist pharmacist, Patient patient, UUID orderNum) {
        this(pharmacist.getId(), patient.getId(), orderNum);
    }

    public Receives(UUID pharmacistId, UUID patientId, UUID orderNum) {
        this.pharmacistId = pharmacistId;
        this.patientId = patientId;
        this.orderNum = orderNum;
    }

    public UUID getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(UUID pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(UUID orderNum) {
        this.orderNum = orderNum;
    }
}
