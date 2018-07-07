package edu.uiowa.cs.tornadoFX.components.masterComponent

import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*


/*
*   @@author Layton Gao
*
*   This is the page present the information of this group project and team member.
* */


class About: Fragment(){
    override val root = borderpane {

        top = vbox {
            label (
                    "Survey Project"
            ){
                style {
                    fontSize = 32.px
                    fontWeight = FontWeight.EXTRA_BOLD
                    alignment = Pos.CENTER
                }
            }
            alignment = Pos.CENTER
        }


        center = vbox {
            label(
                    """
Team0A01 Member:

Kathiresh Pandian
                                    -- Android Front End Specialist
Greg Mich
                                    -- Controller/Networking Specialist
Manish Chavan Sunil Kumar
                                    -- Back End Specialist
Layton Gao
                                    -- Non-Android Front End Specialist
"""
            ) {
                style {
                    isWrapText
                    //alignment = Pos.CENTER
                    loadFont("src/main/resources/Inconsolata-Regular.ttf", 10)
                    font = Font.font("Inconsolata-Regular.ttf")
                    fontSize = 16.px
                }
            }
            alignment = Pos.CENTER
        }
        bottom = vbox {
            label("""
                (`.
                 \ `.
                  )  `._..---._
\`.       __...---`         o  )
 \ `._,--'           ,    ___,'
  ) ,-._          \  )   _,-'
 /,'    ``--.._____\/--''


"""
            ) {
                alignment = Pos.CENTER
                font = Font.font("Courier New")
            }
            alignment = Pos.CENTER
        }

        style {
            prefHeight = 520.px
            prefWidth = 800.px
        }
    }
}