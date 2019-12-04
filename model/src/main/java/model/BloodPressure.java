package model;

public class BloodPressure
{
    final String unit = "mmHg";
    int systolic;
    int diastolic;

    public BloodPressure(int systolic, int diastolic)
    {
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public BloodPressure(String s)
    {
        String[] arr = s.split("/", 2);
        systolic = Integer.parseInt(arr[0]);
        diastolic = Integer.parseInt(arr[1]);
    }

    public String toString()
    {
        return systolic + "/" + diastolic;
    }
}
