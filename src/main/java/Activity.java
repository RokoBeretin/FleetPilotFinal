import java.io.Serializable;
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

    @Override
    public String toString() {
        return "Activity{" +
                "car=" + car +
                ", activity='" + activity + '\'' +
                '}';
    }
}
