package edu.uiowa.cs.networkController
//some data classes for the networking portion -Greg M

// for address: client/submit
data class NetworkMessageSubmitAnswers(val username:String, val surveyName: String, val responseList: List<String>)

// for address: admin/addSurvey
data class NetworkMessageAddSurvey(val user: User, val surveyType: String, val questions: List<String>)

// for address: admin/addSurveyMC
data class NetworkMessageAddMCSurvey(val user: User, val surveyType: String, val questionsWithOptions: Map<String, List<String>>)

//for client/forgotpassword
data class NetworkMessageForgotPassword(val email: String, val birthday: String)