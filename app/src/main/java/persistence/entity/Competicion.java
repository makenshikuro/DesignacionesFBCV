package persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class Competicion implements Serializable{
    @PrimaryKey
    private String id;
    @ColumnInfo(name = "cuotaARB")
    private String cuotaARB;
    @ColumnInfo(name = "cuotaOM")
    private String cuotaOM;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuotaARB() {
        return cuotaARB;
    }

    public void setCuotaARB(String cuotaARB) {
        this.cuotaARB = cuotaARB;
    }

    public String getCuotaOM() {
        return cuotaOM;
    }

    public void setCuotaOM(String cuotaOM) {
        this.cuotaOM = cuotaOM;
    }

    public Competicion(String id, String cuotaARB, String cuotaOM) {
        this.id = id;
        this.cuotaARB = cuotaARB;
        this.cuotaOM = cuotaOM;
    }
}
