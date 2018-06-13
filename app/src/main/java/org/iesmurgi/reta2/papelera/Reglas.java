/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/

package org.iesmurgi.reta2.papelera;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.iesmurgi.reta2.Data.entidades.Partidas;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by usuario on 16/02/18.
 */
@Entity(tableName = "reglas",
        foreignKeys =
        @ForeignKey(entity = Partidas.class,
                parentColumns ="idPartida" ,
                childColumns ="idPartida",onDelete = CASCADE,onUpdate = CASCADE))
public class Reglas {

    @PrimaryKey(autoGenerate = true)
    private int idRegla;
    private int idPartida;
    private String texto;

    public Reglas(int idRegla, int idPartida, String texto) {
        this.idRegla = idRegla;
        this.idPartida = idPartida;
        this.texto = texto;
    }

    @Ignore
    public Reglas(int idPartida, String texto) {
        this.idPartida = idPartida;
        this.texto = texto;
    }

    public int getIdRegla() {
        return idRegla;
    }

    public void setIdRegla(int idRegla) {
        this.idRegla = idRegla;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


}
