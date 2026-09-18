/**
 * Domenska klasa (model) koja predstavlja jedan redak u tablici
 * {@code reservations} - odnosno jednu aktivnu ili trenutno unajmljenu
 * stavku (rezervaciju, pranje vozila ili točenje goriva).
 * <p>
 * Status rezervacije prolazi kroz jednostavan tok:
 * {@code ACTIVE} (čeka preuzimanje) &rarr; {@code CHECKED_OUT} (vozilo je
 * preuzeto, čeka se povrat) &rarr; brisanje retka po završetku (trajni trag
 * ostaje u {@code rental_history}, vidi {@link RentalHistoryDAO}).
 * Perzistenciju obavlja {@link ReservationDAO}.
 */
public class Reservation {
    private int id;
    private String licensePlate;
    private String client;
    private String timeOfRes;
    private String status;
    private String activityType;
    private String expectedReturnTime;

    public Reservation(String licensePlate, String client, String timeOfRes) {
        this.licensePlate = licensePlate;
        this.client = client;
        this.timeOfRes = timeOfRes;
        this.status = "ACTIVE";
        this.activityType = "RESERVATION";
        this.expectedReturnTime = null;
    }

    // Used when creating a direct checkout (Car Wash / Gas Refill), which skips the pickup step
    public Reservation(String licensePlate, String client, String timeOfRes, String activityType, String status) {
        this.licensePlate = licensePlate;
        this.client = client;
        this.timeOfRes = timeOfRes;
        this.status = status;
        this.activityType = activityType;
        this.expectedReturnTime = null;
    }

    // Used when reading a full row back from the database
    public Reservation(int id, String licensePlate, String client, String timeOfRes, String status,
                       String activityType, String expectedReturnTime) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.client = client;
        this.timeOfRes = timeOfRes;
        this.status = status;
        this.activityType = activityType;
        this.expectedReturnTime = expectedReturnTime;
    }

    public int getId() { return id; }
    public String getLicensePlate() { return licensePlate; }
    public String getClient() { return client; }
    public String getTimeOfRes() { return timeOfRes; }
    public String getStatus() { return status; }
    public String getActivityType() { return activityType; }
    public String getExpectedReturnTime() { return expectedReturnTime; }

    public void setStatus(String status) { this.status = status; }
    public void setExpectedReturnTime(String expectedReturnTime) { this.expectedReturnTime = expectedReturnTime; }
}