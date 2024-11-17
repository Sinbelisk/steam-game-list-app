package com.example.unplayedgameslist.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class NavigationMenuViewModel : ViewModel() {

    // Métodos para manejar las acciones de los botones
    fun onLogoutClicked() {
        // Lógica para la acción de cerrar sesión
        // Ejemplo: Manejar la lógica de cierre de sesión o navegar a otra pantalla
        Log.d("NavigationMenu", "Cerrar sesión clickeado")
        // Aquí puedes añadir código para gestionar el cierre de sesión
    }

    fun onChangeClicked() {
        // Lógica para la acción de cambiar algo
        // Ejemplo: Navegar a una pantalla de configuración
        Log.d("NavigationMenu", "Cambiar clickeado")
        // Aquí puedes añadir código para manejar el cambio de configuración o lo que necesites
    }

    fun onOptionsClicked() {
        // Lógica para la acción de opciones
        // Ejemplo: Mostrar un cuadro de opciones o configurar algo
        Log.d("NavigationMenu", "Opciones clickeado")
        // Aquí puedes añadir código para mostrar opciones, configuraciones, etc.
    }
}
