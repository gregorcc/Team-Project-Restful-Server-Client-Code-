package edu.uiowa.cs.tornadoFX.components.user.loginPage


import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterView
import edu.uiowa.cs.tornadoFX.components.user.newUserPage.NewUserView
import edu.uiowa.cs.tornadoFX.components.user.RecoverPageView
import edu.uiowa.cs.tornadoFX.components.masterComponent.Shakeable
import edu.uiowa.cs.tornadoFX.components.anonymous.AnonymousWelcomePageView
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/*
*   @@author Layton Gao
*
*   class LoginView:
*
*   private val model:          contain all the data that can be access through the View and Controller
*
*   anonymousLink():            will take you to Anonymous Page, so you can take survey as anonymous.
*
*   loginButton():              will let you login as normal user or admin.
*
*   open fun adminLogin():      click checkbox for login as Admin,
*
*
*
*   override val root: BorderPane:          UI components
*
*   textfield(model.userName):              username field
*
*   passwordfield(model.password):          password field
*
*   checkbox(property = model.rememberMe):  remember option
*
*   hyperlink("New User?"):                 to NewUser page
*
*   hyperlink: anonymousLink():             to Anonymous page
*
*   add(loginButton())                      to login and invoke login function
*
*   add(adminLogin())                       to login as admin and invoke login function
*
*   button("Set Background Image"):         Change background
*
*   fun clear()                             Change password and username in the model
*
*   class UserLoginView: LoginView():       A copy of LoginView
*
*   class FakeLoginView: LoginView():       A class to prevent bots; all functions inside are useless
* */

open class UserLoginView : MasterView("Welcome to Survey"), LoginView, Shakeable {
    private val loginController: LoginController by inject()
    //override val root = BorderPane()

    // contain all the data that can be access through the View and Controller
    private val model = object: ViewModel(){
        var userName = bind{ SimpleStringProperty() }
        var password = bind{ SimpleStringProperty() }
        var rememberMe = bind{ SimpleBooleanProperty() }
    }

    /*
    *   anonymousLink() will take you to Anonymous Page, so you can take survey as anonymous.
    *
    *   loginButton() will let you login as normal user.
    *
    *   anonymousLink() and loginButton() are two component that is detachable.
    *   open those fun So I can modify them in the Children of this class.
    * */

    override fun anonymousLink() = hyperlink("Take Survey as Anonymous?") {
        action {
            //replaceWith(UserWelcomePageView::class)
            if(loginController.anonymousLogin()) replaceWith(AnonymousWelcomePageView::class)
        }
    }

    override fun loginButton() = button("Login"){
        useMaxWidth = true
        shortcut("Enter")
        isDefaultButton = true
        var count = 0
        setOnAction {
            // this filter some bot; prevent bot login
            if (count>10) replaceWith(FakeLoginView::class)
            count++
            //replaceWith(UserWelcomePageView::class)
            if (adminLogin.isSelected){
                println("TornadoFX: AdminLogin")
                //Note: My teammate have no come anything for login as admin, I don't blame them. They already did many thing.
                loginController.adminLogin(model.userName.value, model.password.value)
            }else{
                    // when very condition of password length and correct email address was satisfied, then it will let user to login.
                model.commit{
                    loginController.tryLogin(
                            model.userName.value,
                            model.password.value,
                            model.rememberMe.value
                    )
                }
            }
        }
    }

    // click secret button "End" to login as Admin, I did that because I think it's cool.
    override val adminLogin = checkbox("Admin Login") {
        println(
                """
TornadoFX: ----------------Saved Value-----------------------
| Try Admin Login
|
|
|
             """
        )
    }

    // This is the main UI body of LoginView class.
    init{

        with(root){
            //Styles Wrapper is the most widely used CSS rule in this project, it defined many feature.

            // this vbox contains username and password text field; it will auto check the completeness of input.
            center = borderpane {
                addClass(Styles.wrapper)
                center = vbox(10) {
                    form {
                        fieldset {
                            field("Email Address"){
                                textfield(model.userName){
                                    promptText = "Type Your Email Address Here"
                                    validator {
                                        if( true != (it?.matches("^[a-z0-9](\\.?[a-z0-9_-])*@[a-z0-9-]+\\.([a-z]{1,6}\\.)?[a-z]{2,6}\$".toRegex())))
                                            error("Please Inter correct Email Address") else null
                                    }
                                }.required()

                            }
                            field("Passwords") {
                                passwordfield(model.password) {
                                    requestFocus()
                                    promptText = "Type Your Password Here"
                                    required()
                                    validator {
                                        when {
                                            it.isNullOrBlank() -> error("Please Inter Your Password")
                                            it!!.length < 8 -> error("Password Too Short")
                                            else -> null
                                        }
                                    }
                                }
                            }

                            // this function will remember username and passsword, and next time it will auto login.
                            field("Remember Me!"){
                                checkbox(property = model.rememberMe)
                            }
                        }


                        hbox {
                            // Let people create new user
                            hyperlink("New User?").action {
                                replaceWith(NewUserView::class)
                            }
                            // Let lazy people take survey without create user.
                            add(anonymousLink())

                            // Let user find password
                            hyperlink("Forget Password?").action {
                                replaceWith(RecoverPageView::class)
                            }
                        }
                    }

                    // user login and admin login Button
                    vbox(15) {
                        add(loginButton())
                        add(adminLogin)
                    }

                    bottom = vbox(15) {
                        // Let user choose the Background Image as they want.
                        // It will auto change its text color.
                        button("Set Background Image") {
                            setOnAction {
                                loginController.setBackground()
                                runLater {
                                    root.styleClass.clear()
                                    replaceWith(UserLoginView())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // when user logged, clean the password.
    fun clear() {
        model.userName.value = ""
        model.password.value = ""
        model.rememberMe.value = false
    }
}


