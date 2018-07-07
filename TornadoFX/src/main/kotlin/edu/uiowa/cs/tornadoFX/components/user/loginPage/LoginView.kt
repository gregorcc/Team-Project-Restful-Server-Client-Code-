package edu.uiowa.cs.tornadoFX.components.user.loginPage

import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Hyperlink

interface LoginView{
    fun anonymousLink(): Hyperlink

    fun loginButton(): Button

    val adminLogin: CheckBox
}