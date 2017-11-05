package persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity
public class Competicion implements Serializable{
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "cuotaARB")
    private float cuotaARB;
    @ColumnInfo(name = "cuotaOM")
    private float cuotaOM;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getCuotaARB() {
        return cuotaARB;
    }

    public void setCuotaARB(float cuotaARB) {
        this.cuotaARB = cuotaARB;
    }

    public float getCuotaOM() {
        return cuotaOM;
    }

    public void setCuotaOM(float cuotaOM) {
        this.cuotaOM = cuotaOM;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Competicion(@NonNull String id, String nombre, float cuotaARB, float cuotaOM) {
        this.id = id;
        this.nombre = nombre;
        this.cuotaARB = cuotaARB;
        this.cuotaOM = cuotaOM;
    }


}
