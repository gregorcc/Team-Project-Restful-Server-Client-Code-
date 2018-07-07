package edu.uiowa.cs.tornadoFX.components.admin

/*
*   @@author Layton Gao
*
*   A welcome Page for admin, I don't think this is the best way to login as admin, but since my group mates are not
*   ready on data structure, maybe what I am going to do is just do everything separately.
*
* */

//
//class AdminLogin: View(), Shakeable {
//    private val model = object: ViewModel(){
//        var userName = bind{ SimpleStringProperty() }
//        var password = bind{ SimpleStringProperty() }
//    }
//
//    override val root: BorderPane = BorderPane()
//    init {
//        with(root) {
//            addClass(Styles.wrapper)
//
//            center = vbox(10) {
//
//
//                button("Login") {
//                    useMaxWidth = true
//                    shortcut("Enter")
//                    isDefaultButton = true
//
//                    action {
//                        replaceWith(AdminWelcomePageView::class)
////                        runAsync {
////                            masterController.networkPortal.frontEndLogin(model.userName.value, model.password.value)
////                        } ui { successfulLogin ->
////                            if (successfulLogin) {
////                                replaceWith(AdminWelcomePageView::class)
////                            } else {
////                                shakeStage()
////                                Popup.message ="Login failed"
////                                find(Popup::class).openModal()
////                            }
////
////                        }
//                    }
//
//                }
//            }
//            bottom = button("Go back to Login Page") {
//                //hide()
//                shortcut("Home")
//                action {
//                    replaceWith(UserLoginView())
//                }
//            }
//        }
//
//    }
//}