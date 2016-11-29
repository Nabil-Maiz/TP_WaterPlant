package maizn.fa.eservices.waterplant.entities;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Plant implements Parcelable{

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String plantName;

    @NotNull
    private Integer wateringFrequency;

    @NotNull
    private Date lastWatering;

    @Generated(hash = 1586953843)
    public Plant(Long id, @NotNull String plantName,
            @NotNull Integer wateringFrequency, @NotNull Date lastWatering) {
        this.id = id;
        this.plantName = plantName;
        this.wateringFrequency = wateringFrequency;
        this.lastWatering = lastWatering;
    }

    @Generated(hash = 878011190)
    public Plant() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlantName() {
        return this.plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Integer getWateringFrequency() {
        return this.wateringFrequency;
    }

    public void setWateringFrequency(Integer wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public Date getLastWatering() {
        return this.lastWatering;
    }

    public void setLastWatering(Date lastWatering) {
        this.lastWatering = lastWatering;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(plantName);
        dest.writeInt(wateringFrequency);
        dest.writeSerializable(lastWatering);
    }

    private Plant(Parcel in){
        this.id = in.readLong();
        this.plantName = in.readString();
        this.wateringFrequency = in.readInt();
        this.lastWatering = (Date) in.readSerializable();
    }

    public static final Creator CREATOR =
            new Parcelable.Creator(){
                public Plant createFromParcel(Parcel in){
                    return new Plant(in);
                }

                @Override
                public Object[] newArray(int size) {
                    return new Plant[size];
                }
            };
}
