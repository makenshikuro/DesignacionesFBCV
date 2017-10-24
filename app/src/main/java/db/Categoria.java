package db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Ubustus on 23/10/2017.
 */

@Entity
public class Categoria {

    @PrimaryKey
    private String codPartido;

    @ColumnInfo(name = "categoria")
    private String categoria;

    @ColumnInfo(name = "encuentro")
    private String encuentro;

    @ColumnInfo(name = "fecha")
    private Date fecha;

    @ColumnInfo(name = "lugar")
    private String lugar;

    @ColumnInfo(name = "distancia")
    private String distancia;

    @ColumnInfo(name = "tiempo")
    private String tiempo;

    @ColumnInfo(name = "desplazamiento")
    private String deplazamiento;

    @ColumnInfo(name = "total")
    private String total;

    public String getCodPartido() {
        return codPartido;
    }

    public void setCodPartido(String codPartido) {
        this.codPartido = codPartido;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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

    public String getDeplazamiento() {
        return deplazamiento;
    }

    public void setDeplazamiento(String deplazamiento) {
        this.deplazamiento = deplazamiento;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
