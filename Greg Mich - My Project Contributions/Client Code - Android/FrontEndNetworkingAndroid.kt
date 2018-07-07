package kathiresh.pandian.uiowa.surveys
import android.content.Context

/**
 * @author Greg Mich
 * An interface for Android Front-End. To use the interface please create an Object that
 * instantiates the FrontEndNetworkingAPI class located in app.java.kathiresh.pandian.uiowa.surveys.FrontEndNetworkingAndroidAPI
 */
interface FrontEndNetworkingAndroid {
    /**
     * @author Greg Mich
     * @param username is a string corresponding to a specific User's username
     * @param password is a String corresponding to a specific User's password
     * @param P Context
     * @return Returns a boolean value, true if the login was successful and false if the login has failed
     */
    fun frontEndLogin(username:String, password: String, P: Context): Boolean

    /**
     *@author Greg Mich
     * @param newUser is an instance of a user object
     * @param P Context
     * @return Returns a boolean value, true if a User has been successfully created and false if it has failed
     */
    fun createNewUser(newUser: User, P: Context): Boolean

    /**
     * @author Greg Mich
     * @param username is a string corresponding to a specific User's username
     * @param answers is a list of String corresponding to answers that the user has provided to the survey questions
     * @param surveyType is a String corresponding to the type of survey that the user has finished
     * @param P Context
     * @return Returns a boolean, if true the submission has been successful, false if it has failed
     */
    fun frontEndSubmitAnswers(username: String,surveyType: String, answers: List<String>, P: Context): Boolean

    /**
     * @author Greg Mich
     * @param userName The username for the user to be logged out
     * @param P The main activity for Context
     * @return A boolean value, true if the logout was successful and false if not
     */
    fun frontEndLogOutUser(userName: String, P: Context): Boolean

    /**
     * @author Greg Mich
     * @param P Context
     * @return A list of available surveys
     */
    fun frontEndGetSurveyList(P: Context): List<String>

    /**
     * @author Greg Mich
     * @param surveyType is a String corresponding to the type of survey that the client would like to see
     * @param P Context
     * @return Returns a SurveyQuestions object, which contains a surveyType as well as a list of questions
     */
    fun frontEndGetSurvey(surveyType: String, P: Context): List<Any>

    /**
     * @author Greg Mich
     * @param email Email address to send password recovery to
     * @param birthday User's birthday (mm/dd/yyyy)
     * @param P Context
     * @return A boolean, true if ok, false if something went wrong
     */
    fun recoverPassword(email: String, birthday: String, P: Context): Boolean
}
