package persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Categoria {


    public @PrimaryKey @NonNull String nombre;

    @ColumnInfo(name = "deudaOM")
    public float deudaOM;

    @ColumnInfo(name = "deudaARB")
    public  float deudaARB;

    public Categoria(String nombre, float deudaOM, float deudaARB) {
        this.nombre = nombre;
        this.deudaOM = deudaOM;
        this.deudaARB = deudaARB;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getDeudaOM() {
        return deudaOM;
    }

    public void setDeudaOM(float deudaOM) {
        this.deudaOM = deudaOM;
    }

    public float getDeudaARB() {
        return deudaARB;
    }

    public void setDeudaARB(float deudaARB) {
        this.deudaARB = deudaARB;
    }
}
