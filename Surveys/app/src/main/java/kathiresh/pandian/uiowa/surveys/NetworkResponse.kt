package kathiresh.pandian.uiowa.surveys
//some data classes for the networking portion -Greg M

// for address: client/submit
data class NetworkMessageSubmitAnswers(val username:String, val surveyName: String, val responseList: List<String>)

//for client/forgotpassword
data class NetworkMessageForgotPassword(val email: String, val birthday: String)