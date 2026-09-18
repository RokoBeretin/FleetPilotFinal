import java.io.Serializable;
/**
 * Domenska klasa (model) koja predstavlja jednog vozača (klijenta) koji
 * može iznajmiti vozilo.
 * <p>
 * Odgovara jednom retku u tablici {@code drivers} u bazi podataka. Popis
 * vozača se koristi za popunjavanje padajućeg izbornika (combo box) u
 * {@code NewActivityPanel}, čime je onemogućen ručni upis imena klijenta.
 * Perzistenciju obavlja {@link DriverDAO}.
 */
public class Driver implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String oib;

    public Driver(String firstName, String lastName, String oib) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
    }

    public Driver(int id, String firstName, String lastName, String oib) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOib() {
        return oib;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", oib='" + oib + '\'' +
                '}';
    }
}