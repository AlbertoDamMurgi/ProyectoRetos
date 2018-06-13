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

package org.iesmurgi.reta2.Chat;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


/**
 * Viewmodel que usamos para el historial del chat.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see ChatAdapter
 * @see ChatAdminActivity
 */

public class ChatModel extends ViewModel {

   MutableLiveData<String> texto = new MutableLiveData<>();

    /**
     * Método que devuelve el texto
     * @return texto
     */
    public MutableLiveData<String> getTexto() {
        return texto;
    }

    /**
     * Método que asigna un valor al texto
     * @param texto
     */
    public void setTexto(MutableLiveData<String> texto) {
        this.texto = texto;
    }
}
