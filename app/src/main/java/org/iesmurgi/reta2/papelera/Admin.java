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
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by usuario on 15/02/18.
 */
@Entity(tableName = "admin")
public class Admin  {
    @PrimaryKey(autoGenerate = true)
    private int id_admin;
    private String user_name;
    private String passwd;
    private int superAdmin;

    @Ignore
    public Admin(String user_name, String pass, int superAdmin) {
        this.user_name = user_name;
        this.passwd = pass;
        this.superAdmin = superAdmin;
    }

    public Admin(int id_admin, String user_name, String passwd, int superAdmin) {
        this.id_admin = id_admin;
        this.user_name = user_name;
        this.passwd = passwd;
        this.superAdmin = superAdmin;
    }

    public int getId_admin() {
        return id_admin;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(int superAdmin) {
        this.superAdmin = superAdmin;
    }
}
