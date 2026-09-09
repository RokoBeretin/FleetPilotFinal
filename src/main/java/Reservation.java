public class Reservation {
    private int id;
    private String licensePlate;
    private String client;
    private String timeOfRes;
    private String status;

    public Reservation(String licensePlate, String client, String timeOfRes) {
        this.licensePlate = licensePlate;
        this.client = client;
        this.timeOfRes = timeOfRes;
        this.status = "ACTIVE";
    }

    public Reservation(int id, String licensePlate, String client, String timeOfRes, String status) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.client = client;
        this.timeOfRes = timeOfRes;
        this.status = status;
    }

    public int getId() { return id; }
    public String getLicensePlate() { return licensePlate; }
    public String getClient() { return client; }
    public String getTimeOfRes() { return timeOfRes; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}