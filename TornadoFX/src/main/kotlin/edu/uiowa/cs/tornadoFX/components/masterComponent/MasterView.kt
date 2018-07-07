package edu.uiowa.cs.tornadoFX.components.masterComponent

import com.sun.prism.paint.Color
import edu.uiowa.cs.tornadoFX.app.Styles
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.input.KeyCombination
import javafx.scene.layout.BorderPane
import tornadofx.*

/*
*   @@author Layton Gao
*
*   MasterView contains the top toolbar for the, which will show function like @Feedback and @About
*   Originally, I plan to inject this view to each others, but I encounter problem with wrapping in CSS
*   I am not sure how to prevent the bar being wrapped to the center of the stage.
*   So I add It as the parent of other Views.
* */

open class MasterView(string: String): View(string){
    final override val root = borderpane {
        top = menubar {
            addClass(Styles.menuBar)
            menu("Option") {
                item("About", "Shortcut+E").action {
                    find(About::class).openModal()
                }
                item("Question?", "Shortcut+Q").action {
                    find(Feedback::class).openModal()
                }
                item("Feedback", "Shortcut+F").action {
                    find(Feedback::class).openModal()
                }
            }
        }
    }
}

