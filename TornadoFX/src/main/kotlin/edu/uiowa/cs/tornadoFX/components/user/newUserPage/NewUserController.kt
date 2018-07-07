package edu.uiowa.cs.tornadoFX.components.user.newUserPage

import edu.uiowa.cs.tornadoFX.components.dataStructure.User
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterController
import edu.uiowa.cs.tornadoFX.components.Popup
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.UserWelcomePageView
import tornadofx.*
import java.io.File

/*
*   @@author Layton Gao
*
*   class @NewUserController
*       This is the class finish all action that read, write, send and,check user profile.
*
*   fun @checkCompletion
*       It will check the correctness of email address and prevent weak password being created.
*       It also checks the whether Password matches.
*
*   fun @showNewUserView
*       If Passwords don't match and Email Address is Invalid, or it is weak password, then popup will jump out and states
*       the problem. If all condition satisfied, then it will send user information to server
*
*   fun @sendUserProfile
*        Try to send the user profile for multiple times, in case there are network issue.
*
*   fun @weakPasswordFinder
*       Check the weak password in a text file, Those are bad password, can be found inside many common hacking tools.
* */

class NewUserController: MasterController() {
    private val newUserView: NewUserView by inject()
    private val userWelcomePageView: UserWelcomePageView by inject()

    // there will check the correctness of email address and prevent weak password being created.
    // check the whether Password matches.
    fun checkCompletion(user: User, confirmPassword: String){
        runAsync {
            when {
                user.password != confirmPassword -> 1
                !user.userName.matches("^[a-z0-9](\\.?[a-z0-9_-]){0,}@[a-z0-9-]+\\.([a-z]{1,6}\\.)?[a-z]{2,6}\$".toRegex()) -> 2
                weakPasswordFinder(user.password) -> 3
                else -> 0
            }
        } ui { check ->
            when(check) {
                0 -> sendUserProfile(user)
                1 -> showNewUserView("Passwords don't match", true)
                2 -> showNewUserView("Invalid Email Address", true)
                3 -> showNewUserView("Weak Password, Please Try Another Password", true)
            }
        }
    }

    // If Passwords don't match and Email Address is Invalid, or it is weak password, then popup will jump out and states
    // the problem. If all condition satisfied, then it will send user information to server.
    private fun showNewUserView(message: String, shake: Boolean = false) {
        userWelcomePageView.replaceWith(newUserView, sizeToScene = true, centerOnScreen = true)
        Popup.message = message
        find(Popup::class).openModal()
        runLater {
            if (shake) newUserView.shakeStage()
        }
    }

    private fun showWelcomePageView() {
        newUserView.replaceWith(userWelcomePageView, sizeToScene = true, centerOnScreen = true)
    }

    // Try to send the user profile for multiple times, in case there are network issue.
    private fun sendUserProfile(user: User){
        //val end = System.currentTimeMillis() + 15*1000 // 15 seconds * 1000 ms/sec
        //while (System.currentTimeMillis() < end)
        println(
                """
TornadoFX: ----------------New User-----------------------
| sending............
|
| try to create new user
|
             """
        )
        val info = networkPortal.createNewUser(user.toStringUser())
        if (info){
            showWelcomePageView()
            println(
                    """
TornadoFX: ----------------New User-----------------------
| Create New User Success
|
|
|
             """
            )
            // auto login as new user
            setUserNAME(user.userName)
            setUserPASSWORD(user.password)
            networkPortal.frontEndLogin(user.userName, user.password)
            return
        }else showNewUserView("Network Error, Please Check Your Network Sitting And Then Try Again", true)
            // Wait for a second, so it won't send too many requests.

    }

    // Check the weak password in a text file, Those are bad password, can be found inside many common hacking tools.
    // I used index finder algorithm to find match.
    private fun weakPasswordFinder(string: String): Boolean{
        val br = File("src/main/resources/lib/grimwepa_pw.txt").bufferedReader()
        var line:String
        while ( br.readLine() != null){
            line = br.readLine()
            val index = -1
            while(line.indexOf(string, index+1) != -1){
                return true
            }
        }
        return false
    }
}