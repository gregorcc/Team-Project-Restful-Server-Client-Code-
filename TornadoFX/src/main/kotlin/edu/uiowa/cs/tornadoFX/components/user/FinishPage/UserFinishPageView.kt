package edu.uiowa.cs.tornadoFX.components.user.FinishPage

import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterView
import edu.uiowa.cs.tornadoFX.components.user.loginPage.LoginController
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.UserWelcomePageView
import javafx.geometry.Pos
import javafx.scene.paint.Color

import tornadofx.*
import java.io.File

/*
*   @@author Layton Gao
*
*   class @UserFinishPageView
*       This page shows that user has finished the survey.
*
*   button( go back to WelcomePage )
*       This button will send user go back to welcome page.
*
* */

open class UserFinishPageView : MasterView("Survey"), FinishPageView {

    override fun gobackButton()= button("Go back to WelcomePage") {
        action {
            replaceWith(UserWelcomePageView::class)
        }
        style { maxWidth = 240.px }
    }


    private val web = webview {
        val string = File("src/main/resources/web/canvas.html")
        engine.load(string.toURI().toString())
    }
    init{
        with(root){
            currentStage?.heightProperty()?.addListener{ _ -> web.engine.reload()}
            currentStage?.widthProperty()?.addListener{ _ -> web.engine.reload()}
            center = borderpane{
                //addClass(Styles.wrapper)
                style {
                    //fixedCellSize
                    prefHeight = 800.px
                    prefWidth = 1200.px
                    textFill = LoginController.setTextFill()
                    backgroundColor += Color.WHITE
                }
                center = web
                bottom = borderpane {
                    left = vbox {
                        add(gobackButton())
                    }
                    right = vbox {
                        alignment = Pos.BOTTOM_RIGHT

                        /*
                        *  My Group mates love to play Super Mario, So I include this game at end of Survey page as a bonus.
                        *
                        *  Adopted From com.golden.gamedev
                        *  Copyright (c) 2008 Golden T Studios.
                        *
                        * */


                        button("Play Super Mario") {
                            action {
                                val prod = Runtime.getRuntime().exec("java.exe -jar src/main/resources/lib/Mario.jar")
                                while (prod.isAlive) {
                                    Thread.sleep(2000)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

