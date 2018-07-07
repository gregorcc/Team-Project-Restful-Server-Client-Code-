package edu.uiowa.cs.tornadoFX.app

import edu.uiowa.cs.tornadoFX.components.user.loginPage.LoginController
import javafx.geometry.Pos
import javafx.scene.effect.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles: Stylesheet() {
    companion object {
        val radioButton by cssclass()
        val label by cssclass()
        val wrapper by cssclass()
        val row by cssclass()
        val fragment by cssclass()
        val textArea by cssclass()
        val textField by cssclass()
        val button by cssclass()
        val questionText by cssclass()
        val noStyles by cssclass()
        val menuBar by cssclass()
        val vbox by cssclass()
        val hbox by cssclass()
        val form by cssclass()
        val layout by cssclass()
    }

    init {
        s(wrapper) {
            prefHeight = 800.px
            prefWidth = 1200.px
            textFill = LoginController.setTextFill()
            backgroundImage  +=  LoginController.setBackgroundImage()


            padding = box(45.px, 95.px)
            s(radioButton){
                textFill = LoginController.setTextFill()
            }

            s(label) {
                minWidth = 10.px
                textFill = LoginController.setTextFill()
                padding = box(3.px)
                fontSize = 18.px
                fontWeight = FontWeight.BOLD
                effect = LoginController.setEffect()
            }

            s(vbox, hbox, form) {

                s(button) {
                    minWidth =140.px
                    maxWidth = 180.px
                    alignment = Pos.BOTTOM_CENTER
                }
            }
            s(button) {
                minWidth =140.px
                maxWidth = 180.px
                alignment = Pos.BOTTOM_CENTER
            }

            s(textField) {

                textFill = LoginController.setTextFill().darker().darker()
                minWidth = 240.px
                maxWidth = 400.px
            }

            s(questionText){
                //minWidth = 10.px
                //textFill = Color.AQUA
                //padding = box(7.px)
                fontSize = 24.px
                fontWeight = FontWeight.BOLD
            }
        }
        s(menuBar){
            padding = box(1.px)
            backgroundColor += Color.TRANSPARENT
            effect = InnerShadow(6.0,LoginController.setTextFill())
            alignment = Pos.TOP_LEFT
            s(label){
                textFill = javafx.scene.paint.Color.BLACK
                loadFont("src/main/resources/Inconsolata-Regular.ttf", 10)
                font = Font.font("Inconsolata-Regular.ttf")
                fontSize = 14.px
            }
        }
        s(noStyles){
            label{
                textFill = Color.BLACK
                effect = javafx.scene.effect.Reflection()
            }
        }

        s(fragment) {
            prefHeight = 90.px
            prefWidth = 420.px
        }
    }


}

