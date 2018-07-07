package edu.uiowa.cs.tornadoFX.components.user.loginPage

import edu.uiowa.cs.tornadoFX.app.Styles
import tornadofx.*

// this is useless login class to stop bots; all functions inside are useless
class FakeLoginView: UserLoginView(){
    override fun anonymousLink() = hyperlink("Take Survey as Anonymous?") {
        action {
            webview {
                style {
                    prefHeight = 800.px
                    prefWidth = 800.px
                }
                engine.load("https://duckduckgo.com/?q=mysurvey")
            }
        }
    }
    override fun loginButton() = button("Login"){
        useMaxWidth = true
        shortcut("Enter")
        isDefaultButton = true
        var count = 0
        action {
            if (count>12) isDisable = true
            count++
        }
    }

    override val adminLogin = checkbox {
        hide()
        isDisable = true
    }

    init {
        root.addClass(Styles.wrapper)
    }
}