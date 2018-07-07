package edu.uiowa

interface backendInterface{
    companion object {
        private var userList= userList2()
        private var userProfile= profileList()
        private var loginStat= mutableMapOf<String,String>()
        private var loginStatAdmin= mutableMapOf<String,String>()
        private var response= readResponseDataAsServer()
        private var questions= surveyList()
        private var admin= adminList()
        private val MCquestions= surveyMCList()
        private var forgotPassword= mutableMapOf<String, String>()


        //call this function to create an USER for the first time..
        fun createUserProfile(user: User): String {
            val x = createUser(userList , UserStat(user.userName, user.password))
            if (x == "Successfully created user : ${user.userName}") {
                val y = addProfile(userProfile, UserProfile(user.userName, user.firstName, user.lastName, user.birthday))
                if (y == "Successfully added profile") {
                    saveUserFile(userList)
                    saveProfileFile(userProfile)
                    return x
                }
                else y
            }
            return x
            // returns either:
            // "Username exists! Please choose another one."
            // "Successfully created user: ${user.userName}"
        }

        //call this function to log in.
        fun logIn(userName: String, password:String):String{
            val result= logInAsBackend(userList, loginStat, UserStat(userName, password))
            return result
            //returns one of the following:
            //"${userName} is already loggedIn
            //"${userName} successfully logged in"
            //"Incorrect Password"
            //"No such User Exists"  // Meaning there was no such user created in the first place.
        }

        //call this function to log out.
        fun logOut(userName: String):String{
            val result= logOutAsBackend(loginStat, userName)
            return result
            //"logged out"
            //"$userName is not logged in"
        }

        //call this function to check if a particular user is already logged in or not.
        fun checkLoginStat(userName: String):Boolean{
            return checkLoginStatAsBackend(loginStat, userName)
        }

        fun getSurveyList(): List<String>{ // return a list of survey types
            return commonSurveyTypeList(questions, MCquestions)
        }

        //call this function to get a list of survey questions if you know the which type of survey you want.
        fun getSurvey(surveyType: String): List<Any>{
            return getSurveyAsBackend(questions, MCquestions, surveyType)
            // returns a list of question for that particular question or returns an empty list if that type of survey doesn't exist.
        }

        //call this function only if the ADMIN is calling it and not regular clients...
        //You need have an ADMIN's UserName and Password for using this function...
        //For now use a default userName: ADMIN  with   password: WaterMelon
        //Do keep in mind that this default userName and password should ONLY used in your Test CASES and nowhere else in your actual code.
        fun addSurvey(user: User, surveyType: String, listOfQuestions:List<String>):String{
            // the reason we need a user variable here is to check if the person accessing this is a ADMIN or not!
            //if(checkLoginStatForAdminAsBackend(loginStatAdmin, user.userName)) {
                val countTemp = questions[surveyType]?.size
                val temp = addQuestion(admin, user, questions, SurveyQuestions(surveyType, listOfQuestions))
                if (temp == "updated $surveyType" || temp == "created new survey type: $surveyType") {
                    if (countTemp == null) {
                        updateResponseFile(response, surveyType, listOfQuestions.size, 0)
                    } else {
                        updateResponseFile(response, surveyType, listOfQuestions.size, countTemp)
                    }
                    writeResponseData(response)
                }
                writeToQuestionsFile(questions)
                return temp
            //}
            //return "Admin not logged in"


            // returns one of the following:
            // "updated $surveyType"
            // "created new survey type: $surveyType"
            // "You are not Authorised to modify the survey"     // If the user is not an ADMIN
            // "Admin not logged in"  **** COMMENTED OUT BECAUSE TORNADO FX IS STILL UNDER DEVELOPMENT  *****
        }

        //call this function to save a response for a particular userName and surveyType
        fun saveResponse(userName: String, whichSurvey:String, listOfResponse:List<String> ): String{
            // TODO check if the length of the listOfResponse is same as the surveyType questions length and make sure each element in the list is not empty or null
            if(checkLoginStat(userName)){
                if(getSurvey(whichSurvey).isEmpty()){
                    return "No such Survey Type exists"
                }
                else if(getSurvey(whichSurvey).size != listOfResponse.size){
                    return "Survey Size and Response size doesn't match!"
                    }
                return saveResponseAsBackend(response, getSurveyList(), Response(userName, listOfResponse, whichSurvey))

            }
            return "User is not logged in."
            //returns either one of the following Strings:
            //"Updated ${whichSurvey} for ${username}"
            //"Added first ${whichSurvey} survey response for ${username}"
            //"User is not logged in."
            //"No such Survey Type exists" THEREBY I CANNOT SAVE A RESPONSE FOR AN UNKNOWN QUESTION.
            //"Survey Size and Response size doesn't match!"
        }

        // call this function to get multiple-choice survey questions.
        // It returns a map with keyword as question and value as options(String) for that question.
        /*
        fun getMCsurvey(surveyType: String): Map<String,List<String>>{ // MC stands for multiple choice questions.
            return getMCsurveyAsBackend(MCquestions, surveyType)
            // returns either of the following:
            // a map of questions as keys and values a list of options to that question
            // empty map meaning no such survey type exists.
        }
        */

        //call this function only if the ADMIN is calling it and not regular clients...
        //You need have an ADMIN's UserName and Password for using this function...
        //For now use a default userName: ADMIN  with   password: WaterMelon
        //Do keep in mind that this default userName and password should ONLY used in your Test CASES and nowhere else in your actual code.
        fun addMCsurvey(user: User, surveyType: String, mapOfQuestionsWithOptions: Map<String, List<String>>):String{
            //if(checkLoginStatForAdminAsBackend(loginStatAdmin, user.userName)) {
                val temp = addMCquestion(admin, user, MCquestions, MCsurveyQuestions(surveyType, mapOfQuestionsWithOptions))
                if (temp == "updated $surveyType" || temp == "created new survey type: $surveyType for multiple choice") {
                    updateResponseFile(response, surveyType, mapOfQuestionsWithOptions.size, -100)
                    writeResponseData(response)
                }
                writeToMCQuestionsFile(MCquestions)
                return temp
            //}
            //return "Admin not logged in"


            // returns either one of the following:
            // "updated ${surveyType}"
            // "created new survey type: ${surveyType} for multiple choice"
            // "You are not Authorised to modify the survey"
            // "Admin not logged in"  **** COMMENTED OUT BECAUSE TORNADO FX IS STILL UNDER DEVELOPMENT  *****
        }
        fun forgotPassword(email: String, birthday: String): Boolean{
            val result=forgotPasswordAsBackend(email, birthday, userProfile, userList)
            if(result[0]=="true"){
                forgotPassword[email]=result[1]
                return true
            }
            return false
        }

        fun changePassword(email: String, tempPassword: String, newPassword: String):Boolean{
            return changePasswordAsBackend(email, tempPassword, newPassword, forgotPassword, userList)
        }

        fun loginAdmin (userName: String, password:String):String{
            val result= logInAsBackend(admin, loginStatAdmin, UserStat(userName, password))
            return result
            //returns one of the following:
            //"${userName} is already loggedIn
            //"${userName} successfully logged in"
            //"Incorrect Password"
            //"No such Admin Exists"  // Meaning there was no such user created in the first place.
        }

        fun logoutAdmin(userName: String):String{
            val result= logOutAsBackend(loginStatAdmin, userName)
            return result
            //"Admin logged out"
            //"Admin-$userName is not logged in"
        }

        //*********************************IMPORTANT***************************************//
        // PLEASE CALL shutDown() FUNCTION TO SAVE ALL THE DATA. IF YOU FORGET TO CALL THIS, THEN NO DATA SHALL BE SAVED..

        fun shutDown(){
            saveUserFile(userList)
            saveProfileFile(userProfile)
            writeResponseData(response)
            writeToQuestionsFile(questions)
            writeToMCQuestionsFile(MCquestions)
        }
    }

}
