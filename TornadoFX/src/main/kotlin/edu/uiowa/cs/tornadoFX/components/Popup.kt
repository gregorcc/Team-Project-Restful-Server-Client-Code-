package edu.uiowa.cs.tornadoFX.components

import edu.uiowa.cs.tornadoFX.app.Styles
import javafx.geometry.Pos
import tornadofx.*

// @@author Layton Gao
// This will popup a window to display important information.
// I did this because I don't like use tornado popup, it does not looks good.
class Popup: Fragment("Notification"){
    companion object {
        var message = ""
    }
    override var root = borderpane{
        addClass(Styles.fragment)
        center = label(message){isWrapText; alignment = Pos.CENTER}
    }
}