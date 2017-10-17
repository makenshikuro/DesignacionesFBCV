package com.fbcv.ubustus.designacionesfbcv;

import java.util.Date;

/**
 * Created by Ubustus on 13/10/2017.
 */

public class Partido {

    private String codigo;
    private String encuentro;
    private Date fecha;
    private String categoria;
    private String localidad;
    private float cuota;
    private String estado;
    private boolean aceptado;

    public Partido(String codigo, String encuentro, Date fecha, String categoria, String localidad, float cuota, String estado, boolean aceptado) {
        this.codigo = codigo;
        this.encuentro = encuentro;
        this.fecha = fecha;
        this.categoria = categoria;
        this.localidad = localidad;
        this.cuota = cuota;
        this.estado = estado;
        this.aceptado = aceptado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }
}
