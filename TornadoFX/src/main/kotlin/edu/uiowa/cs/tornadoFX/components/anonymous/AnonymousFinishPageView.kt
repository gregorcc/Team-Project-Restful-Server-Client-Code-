package edu.uiowa.cs.tornadoFX.components.anonymous

import edu.uiowa.cs.tornadoFX.components.user.FinishPage.UserFinishPageView
import tornadofx.*

class AnonymousFinishPageView: UserFinishPageView(){
    override fun gobackButton() = button("Go back to WelcomePage") {
        action {
            replaceWith(AnonymousWelcomePageView::class)
        }
        style { maxWidth = 240.px }
    }
}