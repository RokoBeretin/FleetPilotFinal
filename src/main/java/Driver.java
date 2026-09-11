import java.io.Serializable;

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