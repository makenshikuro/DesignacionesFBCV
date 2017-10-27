package persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;


@Entity
public class Partido implements Serializable{
    @PrimaryKey
    @NonNull
    private String codigo;
    @ColumnInfo(name = "encuentro")
    private String encuentro;
    @ColumnInfo(name = "fecha")
    private Date fecha;
    @ColumnInfo(name = "categoria")
    private String categoria;
    @ColumnInfo(name = "localidad")
    private String localidad;
    @ColumnInfo(name = "cuota")
    private float cuota;
    @ColumnInfo(name = "distancia")
    private String distancia;
    @ColumnInfo(name = "tiempo")
    private String tiempo;
    @ColumnInfo(name = "desplazamiento")
    private float desplazamiento;
    @ColumnInfo(name = "total")
    private float total;
    @ColumnInfo(name = "estado")
    private String estado;

    public Partido(@NonNull String codigo, String encuentro, Date fecha, String categoria, String localidad, float cuota, String distancia, String tiempo, float desplazamiento, float total, String estado) {
        this.codigo = codigo;
        this.encuentro = encuentro;
        this.fecha = fecha;
        this.categoria = categoria;
        this.localidad = localidad;
        this.cuota = cuota;
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.desplazamiento = desplazamiento;
        this.total = total;
        this.estado = estado;
    }

    @NonNull
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(@NonNull String codigo) {
        this.codigo = codigo;
    }

    public String getEncuentro() {
        return encuentro;
    }

    public void setEncuentro(String encuentro) {
        this.encuentro = encuentro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public float getCuota() {
        return cuota;
    }

    public void setCuota(float cuota) {
        this.cuota = cuota;
    }



    public float getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(float desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
