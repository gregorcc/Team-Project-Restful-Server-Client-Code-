package edu.uiowa.cs.networkController

/**
 * @author Greg Mich
 * An interface for Front-End. To use the interface please create an Object that
 * instantiates the FrontEndNetworkingAPI class located in edu.uiowa.cs.networkController
 */
interface FrontEndNetworking {
    /**
     * @author Greg Mich
     * @param username is a string corresponding to a specific User's username
     * @param password is a String corresponding to a specific User's password
     * @return Returns a boolean value, true if the login was successful and false if the login has failed
     */
    fun frontEndLogin(username:String, password: String): Boolean

    /**
     *@author Greg Mich
     * @param newUser is an instance of a user object
     * @return Returns a boolean value, true if a User has been successfully created and false if it has failed
     */
    fun createNewUser(newUser: User): Boolean

    /**
     * @author Greg Mich
     * @return A list of the available surveys
     */
    fun frontEndGetSurveyList(): List<String>

    /**
     * @author Greg Mich
     * @param surveyType is a String corresponding to the type of survey that the client would like to see
     * @return Returns a SurveyQuestions object, which contains a surveyType as well as a list of questions
     */
    fun frontEndGetSurvey(surveyType: String): List<Any>


    /**
     * @author Greg Mich
     * @param username is a string corresponding to a specific User's username
     * @param answers is a list of String corresponding to answers that the user has provided to the survey questions
     * @param surveyType is a String corresponding to the type of survey that the user has finished
     * @return Returns a boolean, if true the submission has been successful, false if it has failed
     */
    fun frontEndSubmitAnswers(username: String, surveyType: String, answers: List<String>): Boolean

    /**
     * @author Greg Mich
     * @param userName The username for the user to be logged out
     * @return A boolean value, true if the logout was successful and false if not
     */
    fun frontEndLogOutUser(userName: String): Boolean

    /**
     * @author Greg Mich
     * @param userName Administrator username
     * @param password Administrator password
     * @param surveyType Name of the new survey
     * @param questions List of free response questions
     * @return Returns a Boolean, false if submission failed and true if successful
     */
    fun adminAddSurvey(userName: String, password: String, surveyType: String, questions: List<String>): Boolean

    /**
     * @author Greg Mich
     * @param userName Administrator username
     * @param password Administrator password
     * @param surveyType Name of the new survey
     * @param questionsWithOptions List of free response questions
     * @return Returns a Boolean, false if submission failed and true if successful
     */
    fun adminAddSurveyMC(userName: String, password: String, surveyType: String, questionsWithOptions: Map<String, List<String>>): Boolean

    /**
     * @author Greg Mich
     * @param email Email address to send password recovery to
     * @param birthday User's birthday (mm/dd/yyyy)
     * @return A boolean, true if ok, false if something went wrong
     */
    fun recoverPassword(email: String, birthday: String): Boolean
}
