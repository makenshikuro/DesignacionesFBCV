package db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ubustus on 08/10/2017.
 */

@Entity(tableName = "usuario")
public class Usuario {

    @PrimaryKey
    @ColumnInfo(name = "licencia")
    private String licencia;

    @ColumnInfo(name = "email")
    private int email;

    @ColumnInfo(name = "dni")
    private String dni;

    @ColumnInfo(name = "passWeb")
    private int passWeb;

    @ColumnInfo(name = "origen")
    private int origen;

    @ColumnInfo(name = "nivel")
    private String nivel;

    @ColumnInfo(name = "tipo")
    private String tipo;

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getPassWeb() {
        return passWeb;
    }

    public void setPassWeb(int passWeb) {
        this.passWeb = passWeb;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
