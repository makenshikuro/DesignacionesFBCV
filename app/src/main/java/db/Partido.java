package db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Ubustus on 08/10/2017.
 */
@Entity (tableName = "partido")
public class Partido {

    @PrimaryKey
    @ColumnInfo(name = "codigo")
    private String codigo;

    @ColumnInfo(name = "competicion")
    private String competicion;

    @ColumnInfo(name = "encuentro")
    private String encuentro;

    @ColumnInfo(name = "lugar")
    private String lugar;

    @ColumnInfo(name = "fecha")
    private Date fecha;

    @ColumnInfo(name = "distancia")
    private float distancia;

    @ColumnInfo(name = "tiempo")
    private String tiempo;

    @ColumnInfo(name = "total")
    private float total;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCompeticion() {
        return competicion;
    }

    public void setCompeticion(String competicion) {
        this.competicion = competicion;
    }

    public String getEncuentro() {
        return encuentro;
    }

    public void setEncuentro(String encuentro) {
        this.encuentro = encuentro;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
