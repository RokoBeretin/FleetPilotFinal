import java.io.Serializable;
/**
 * Domenska klasa (model) koja predstavlja jedan zapis iz trajne povijesti
 * iznajmljivanja (tablica {@code rental_history}).
 * <p>
 * Sadrži referencu na vozilo ({@link Car}) na koje se zapis odnosi te
 * gotov, čitljiv tekstualni opis aktivnosti (klijent, tip aktivnosti,
 * vremena preuzimanja/povrata, kilometraža, gorivo) koji se izravno
 * prikazuje u {@code SearchPanel} i {@code CheckoutPanel}. Perzistenciju i
 * pretragu obavlja {@link RentalHistoryDAO}.
 */
public class Activity implements Serializable {
    private Car car;
    private String activity;

    public Activity (){
        this.car = car;
        this.activity = activity;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Car getCar() {
        return car;
    }

    public String getActivity() {
        return activity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "car=" + car +
                ", activity='" + activity + '\'' +
                '}';
    }
}