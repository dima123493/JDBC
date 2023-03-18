import java.time.LocalDate;

public class TripRecords {
    private String firstName;
    private String lastName;
    private String city;
    private double perDiem;
    private LocalDate dateFirst;
    private LocalDate dateLast;

    public TripRecords(String firstName, String lastName, String city, double perDiem, String dateFirst, String dateLast) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.perDiem = perDiem;
        this.dateFirst = LocalDate.parse(dateFirst);
        this.dateLast = LocalDate.parse(dateLast);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public double getPerDiem() {
        return perDiem;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPerDiem(float perDiem) {
        this.perDiem = perDiem;
    }

    public LocalDate getDateFirst() {
        return dateFirst;
    }

    public void setDateFirst(LocalDate dateFirst) {
        this.dateFirst = dateFirst;
    }

    public LocalDate getDateLast() {
        return dateLast;
    }

    public void setDateLast(LocalDate dateLast) {
        this.dateLast = dateLast;
    }
}
