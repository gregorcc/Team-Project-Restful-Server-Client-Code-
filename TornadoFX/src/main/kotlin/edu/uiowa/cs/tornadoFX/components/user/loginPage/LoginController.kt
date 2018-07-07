package edu.uiowa.cs.tornadoFX.components.user.loginPage

import edu.uiowa.cs.networkController.User
import edu.uiowa.cs.tornadoFX.components.ImageTester.colorPicker
import edu.uiowa.cs.tornadoFX.components.Popup
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.UserWelcomePageView
import edu.uiowa.cs.tornadoFX.components.admin.welcomePage.AdminWelcomePageView
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterController
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Effect
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import oshi.SystemInfo
import tornadofx.*
import java.net.InetAddress
import java.net.URI

/*
*  @@author Layton Gao
*
*  LoginController Class:
*
*  fun init():              Remember username and password, so user don't need to login again.
*
*  fun showLoginView(message: String, shake: Boolean = false):
*                           This feature will shake the stage.
*
*  fun showWelcomePageView():
*                           When login success, it will bring user to Welcome Page that can modify user profile, and take survey.
*
*  fun @forgetMe:
*                           This will forget the username and password that has been recoded
*
*  fun tryLogin(username: String, password: String, remember: Boolean):
*                           Try to login, it will call Network function to access data through 127.0.0.1:8080
*
*  fun setBackground():     This function allows user to change Background Image, It will also call ColorPicker to set New color for texts.
*
*  fun anonymousLogin():    This function checks anonymous's IP address and motherBoard Number, so we don't get duplicate Anonymous Survey
*
*  companion object:        This contains default setting of Background Image and text color. I made them private, and left getter to access.
*
*  class IPAddress:         This is the IP address class to get network through internet.
*
* */

class LoginController: MasterController() {
    private val loginView: UserLoginView by inject()
    private val userWelcomePageView: UserWelcomePageView by inject()
    private val adminWelcomePageView: AdminWelcomePageView by inject()

    //Remember username and password, so user don't need to login again.
    fun init() {
        with(config) {
            println("""
TornadoFX: ----------------Saved Value-----------------------
| saved password:  ${config["0"]}
| saved username:  ${config["1"]}
| Note: try to auto login with saved username and password.
|
"""
            )

            if ((!config.isEmpty )&& config["0"] != null) tryLogin(config["0"].toString(), config["1"].toString(), true, popup = false)
        }
    }

    //This feature will shake the stage.
    fun showLoginView(message: String, shake: Boolean = false) {
        userWelcomePageView.replaceWith(loginView, sizeToScene = true, centerOnScreen = true)
        Popup.message = message
        runLater {
            find(Popup::class).openModal()
            if (shake) loginView.shakeStage()
        }
    }

    //When login success, it will bring user to Welcome Page that can modify user profile, and take survey.
    private fun showWelcomePageView(other: View) {
        loginView.replaceWith(other, sizeToScene = true, centerOnScreen = true)
//        if (getUser() is User){
//            loginView.replaceWith(userWelcomePageView, sizeToScene = true, centerOnScreen = true)
//        }else if (getUser() is Administrator){
//            loginView.replaceWith(adminWelcomePageView, sizeToScene = true, centerOnScreen = true)
//        }
    }

    //Try to login, it will call Network function to access data through 127.0.0.1:8080
    fun tryLogin(username: String, password: String, remember: Boolean, popup: Boolean = true) {
        runAsync {
            println("TornadoFX: $username  $password")
            try {
                networkPortal.frontEndLogin(username,password)
            }catch (e: javax.ws.rs.ProcessingException){
                false
            }
        } ui { successfulLogin ->
            // If network response successful then we will save password and username if remeber me option is selected.
            if (successfulLogin) {
                setUserNAME(username)
                setUserPASSWORD(password)
                if (remember) {
                    with(config) {
                        config.clear()
                        set("0" to username)
                        set("1" to password)
                        println(config)
                        save()
                    }
                }
                loginView.clear()
                // Change to Welcomepage
                showWelcomePageView(userWelcomePageView)
            } else {
                if (popup) showLoginView("Login failed. Please try again.", true)
            }
        }
    }

    // This will forget the username and password that has been recoded
    fun forgetMe(){
        with(config) {
            clear()
            save()
        }
    }

    // This function allows user to change Background Image, It will also call ColorPicker to set New color for texts.
    fun setBackground(){
        val fileChooser = FileChooser()
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png", "*.gif","*.mpo", "*.jpeg")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("JPG", "*.jpg")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("PNG", "*.png")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("GIF", "*.gif")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("MPO", "*.mpo")
        fileChooser.extensionFilters += FileChooser.ExtensionFilter("JPEG", "*.jpeg")
        val file = fileChooser.showOpenDialog(null)
        if (file!=null){
            println(file.absolutePath)
            backgroundImage = file.toURI()
            textFill = colorPicker(file)
        }
    }

    fun adminLogin(username: String, password: String){
        setUserNAME(username)
        setUserPASSWORD(password)
        runAsync {

        } ui {
            showWelcomePageView(adminWelcomePageView)
        }

//
//        runAsync {
//            networkPortal.frontEndLogin(username, password)
//        } ui { successfulLogin ->
//            if (successfulLogin) {
//                showWelcomePageView(adminWelcomePageView)
//            } else {
//                showLoginView("Login failed",true)
//            }
//        }
    }

    // This function checks anonymous's IP address and motherBoard Number, so we don't get duplicate Anonymous Survey.
    fun anonymousLogin(): Boolean{
        // call oshi library to get motherBoard
        val hal = SystemInfo().hardware
        // call java.net.InetAddress library to get Ip address
        val ip = IPAddress()
        ip.setIpAdd()
        if (ip.thisIpAddress != null) {
            if (networkPortal.createNewUser(User(hal.computerSystem.baseboard.serialNumber,ip.thisIpAddress.toString(),"Anonymous","Null","Null"))){
            //if (networkPortal.frontEndLogin(hal.computerSystem.baseboard.serialNumber,ip.thisIpAddress.toString())){
                return true
            }else{
                showLoginView("Cannot Take Survey As Anonymous Twice", true)
            }
        }else{
            showLoginView("Network Error. Please try again.", true)
            return false
        }
        return false
    }

    // This contains default setting of Background Image and text color. I made them private, and left getter to access.
    companion object {
        private var textFill = Color.DARKVIOLET
        private var backgroundImage = URI("file:src/main/resources/splash001.png")
        fun setBackgroundImage():URI{
            return backgroundImage
        }

        fun setTextFill():Color{
            return textFill
        }

        fun setEffect():Effect{
            return if (textFill == Color.DARKVIOLET || (textFill.brightness<0.55 && textFill.brightness>0.65) ) {
                javafx.scene.effect.Reflection()
            } else DropShadow(BlurType.GAUSSIAN, setTextFill().invert().grayscale(),2.0,2.0,0.0,0.0)
        }
    }

}

// This is the IP address class to get network through internet. I did this instead get information from oshi.
// Because hardware information less reliable. They could be multiple network controller in the computer.
class IPAddress {
    var thisIpAddress: String? = null
    fun setIpAdd() {
        try {
            val thisIp = InetAddress.getLocalHost()
            thisIpAddress = thisIp.hostAddress.toString()
        } catch (e: Exception) {

            // It will through any exception it encountered.
            Popup.message = e.toString()
            find(Popup::class).openModal()
        }
    }
}