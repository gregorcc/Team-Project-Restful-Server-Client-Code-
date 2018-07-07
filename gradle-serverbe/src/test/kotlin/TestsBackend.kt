package edu.uiowa

import org.junit.Test
import org.junit.Assert.*

class TestsBackend {
    val user = UserStat("test", "testing")

    @Test
    fun test1() {
        clearUserFile()
        assertEquals(userList(), mapOf<UserStat, Int>())
    }
    /*
    @Test
    fun test3() {
        clearResponseFile()
        assertEquals(saveResponseAsServer(Response("test", listOf("a", "s", "q", "r", "t"), "educationTestSurvey")), "Added for the first time //educationTestSurvey// to this username= test")
        assertEquals(saveResponseAsServer(Response("test", listOf("q", "w", "e", "r", "t", "y"), "educationTestSurvey")), "Till now 1 times test's  educationTestSurvey has been updated")
        assertEquals(saveResponseAsServer(Response("test2", listOf("a", "s", "q", "r", "t"), "educationTestSurvey")), "Added for the first time //educationTestSurvey// to this username= test2")
        assertEquals(saveResponseAsServer(Response("test2", listOf("q", "w", "e", "r", "t", "y"), "educationTestSurvey")), "Till now 1 times test2's  educationTestSurvey has been updated")
        assertEquals(saveResponseAsServer(Response("aest2", listOf("a", "s", "q", "r", "t"), "educationTestSurvey")), "Added for the first time //educationTestSurvey// to this username= aest2")
        assertEquals(saveResponseAsServer(Response("aest2", listOf("q", "w", "e", "r", "t", "y"), "educationTestSurvey")), "Till now 1 times aest2's  educationTestSurvey has been updated")
        assertEquals(saveResponseAsServer(Response("zest2", listOf("a", "s", "q", "r", "t"), "educationTestSurvey")), "Added for the first time //educationTestSurvey// to this username= zest2")
        assertEquals(saveResponseAsServer(Response("zest2", listOf("q", "w", "e", "r", "t", "y"), "educationTestSurvey")), "Till now 1 times zest2's  educationTestSurvey has been updated")
        assertEquals(getResponse("zest2", "educationTestSurvey"), "a ; s ; q ; r ; t ")
        assertEquals(getResponse("zest2", "educationTestSurvey1"), "q ; w ; e ; r ; t ; y ")
        assertEquals(deleteResponse("test2", "educationTestSurvey"), "Successfully deleted educationTestSurvey for test2")
        assertEquals(deleteResponse("test2", "educationTestSurvey"), "No such survey found!")
        assertEquals(deleteResponse("fake", "educationTestSurvey"), "No such userName found!")
        clearResponseFile()
    }

    @Test
    fun test4() {
        clearUserProfileFile()
        assertEquals(profileList(), listOf<UserProfile>())
        assertEquals(addProfileAsServer(UserProfile("test", "first", "last", "00/00/0000")), "Successfully added profile")
        assertEquals(addProfileAsServer(UserProfile("test", "first", "last", "00/00/0000")), "User Profile already exists!!")
        assertEquals(addProfileAsServer(UserProfile("test2", "first2", "last2", "00/00/0002")), "Successfully added profile")
        var x = getProfileAsServer("test")
        assertEquals(x.name, "test")
        assertEquals(x.firstName, "first")
        assertEquals(x.lastName, "last")
        assertEquals(x.birthday, "00/00/0000")
        deleteProfileAsServer("test")
        x = getProfileAsServer("test2")
        assertEquals(x.name, "test2")
        assertEquals(x.firstName, "first2")
        assertEquals(x.lastName, "last2")
        assertEquals(x.birthday, "00/00/0002")
        deleteProfileAsServer("test2")
        assertEquals(profileList(), listOf<UserProfile>())
        clearUserProfileFile()
    }

    @Test
    fun test5() {
        clearUserFile()
        clearLoginStatFile()
        createUserAsServer(user)
        createUserAsServer(UserStat("test2", "tttt"))
        assertEquals(logInAsServer(UserStat("test", "testing")), "test successfully logged in")
        assertEquals(logInAsServer(UserStat("test2", "tttt")), "test2 successfully logged in")
        assertEquals(logInAsServer(UserStat("test2", "tttt")), "test2 is already loggedIn")
        assertEquals(logInAsServer(UserStat("fake", "fake^^^")), "No such User Exists")
        assertEquals(logOutAsServer("test2"), "logged out!")
        assertEquals(logOutAsServer("test2"), "test2 is not logged in")
        assertEquals(checkLoginStatAsServer("test"), true)
        clearUserFile()
        clearLoginStatFile()
    }

    @Test
    fun test6() {
        clearQuestionsFile()
        assertEquals(addQuestionAsServer(SurveyQuestions("sports", listOf("jhjh", "jhjhh"))), "added the very first question to the Database")
        assertEquals(addQuestionAsServer(SurveyQuestions("education", listOf("789", "987"))), "created new survey type: education")
        assertEquals(addQuestionAsServer(SurveyQuestions("education", listOf("new", "new2"))), "updated education")
        clearQuestionsFile()
    }

    */
    @Test
    // createUserProfile ONLY
    fun test7() {
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
        val x = backendInterface
        assertEquals(x.createUserProfile(User("test@yahoo.com", "first", "last", "pass", "00/00/1995")), "Successfully created user : test@yahoo.com")
        assertEquals(x.createUserProfile(User("test2@yahoo.com", "first", "last", "pass", "00/00/1995")), "Successfully created user : test2@yahoo.com")
        assertEquals(x.createUserProfile(User("test@yahoo.com", "first", "last", "pass", "00/00/1995")), "Username exists! Please choose another one.")
        x.shutDown()
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
    }


    @Test
    // Login and log out.
    fun test8() {
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
        val z = backendInterface
        assertEquals(z.createUserProfile(User("test@yahoo.com", "first", "last", "pass", "00/00/1995")), "Successfully created user : test@yahoo.com")
        assertEquals(z.createUserProfile(User("test2@yahoo.com", "first", "last", "pass", "00/00/1995")), "Successfully created user : test2@yahoo.com")
        assertEquals(z.createUserProfile(User("test@yahoo.com", "first", "last", "pass", "00/00/1995")), "Username exists! Please choose another one.")
        assertEquals(z.logIn("test@yahoo.com", "pass"),"test@yahoo.com successfully logged in")
        assertEquals(z.checkLoginStat("test@yahoo.com"), true)
        assertEquals(z.logIn("test@yahoo.com", "passs"),"Incorrect Credentials")
        assertEquals(z.logIn("testtttttttt@yahoo.com", "passs"),"No such User Exists")
        assertEquals(z.logOut("test@yahoo.com"),"logged out")
        assertEquals(z.logOut("test@yahoo.com"),"test@yahoo.com is not logged in")
        assertEquals(z.checkLoginStat("test@yahoo.com"), false)
        z.shutDown()
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
    }

    @Test
    // getSurveyList and addSurvey
    fun test9() {
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
        clearAdminFile()
        clearMCQuestionsFile()
        createAdminAsServer(Admin(UserStat("Admin", "Watermelon")))
        val x = backendInterface
        assertEquals(x.getSurveyList(), listOf<String>())
        assertEquals(x.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test","test2","test3")),"created new survey type: sports")
        assertEquals(x.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test4","test5","test6")),"updated sports")
        assertEquals(x.addSurvey(User("Admin2", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test4","test5","test6")),"You are not Authorised to modify the survey")
        assertEquals(x.getSurveyList(), listOf("sports"))
        x.shutDown()
    }



    @Test
    // saveResponse
    fun test10() {
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
        clearAdminFile()
        clearMCQuestionsFile()
        createAdminAsServer(Admin(UserStat("Admin", "Watermelon")))
        val y = backendInterface
        assertEquals(y.createUserProfile(User("test@yahoo.com", "first", "last", "pass", "00/00/1995")), "Successfully created user : test@yahoo.com")
        y.logIn("test@yahoo.com", "pass")
        //assertEquals(y.createUserProfile(User("Admin786", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111")), "Successfully created user : Admin786")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test","test2","test3")),"created new survey type: sports")
        assertEquals(y.getSurvey("sports"), listOf("test","test2","test3"))
        assertEquals(y.getSurvey("education"), listOf<String>())
        assertEquals(y.saveResponse("test@yahoo.com","sports", listOf("response1","response2","response3")),"First Survey added for test@yahoo.com")
        assertEquals(y.saveResponse("test@yahoo.com","sports", listOf("response4","response5","response6")),"Updated sports for test@yahoo.com")
        assertEquals(y.saveResponse("test@yahoo.com","education", listOf("response4","response5","response6")),"No such Survey Type exists")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"education", listOf("test","test2","test3")),"created new survey type: education")
        assertEquals(y.saveResponse("test@yahoo.com","education", listOf("response4","response5","response6")),"Added first education survey response for test@yahoo.com")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test4","test5","test6")),"updated sports")
        y.shutDown()
    }


    @Test
    //update Response file check and addMCsurvey
    fun test11(){
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
        clearAdminFile()
        clearMCQuestionsFile()
        createAdminAsServer(Admin(UserStat("Admin", "Watermelon")))
        val y = backendInterface
        assertEquals(y.createUserProfile(User("test@yahoo.com", "first", "last", "pass", "00/00/1995")), "Successfully created user : test@yahoo.com")
        y.logIn("test@yahoo.com", "pass")
        //assertEquals(y.createUserProfile(User("Admin786", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111")), "Successfully created user : Admin786")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test","test2","test3")),"created new survey type: sports")
        assertEquals(y.saveResponse("test@yahoo.com","sports", listOf("response1","response2","response3")),"First Survey added for test@yahoo.com")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test4","test5","test6")),"updated sports")
        assertEquals(y.addMCsurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", mapOf("where was the world cup hosted at?" to listOf("USA", "UK", "Russia", "IDK"))), "created new survey type: sports for multiple choice")
        assertEquals(y.addMCsurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", mapOf("where was the world cup hosted attttt?" to listOf("USA", "UK", "Russia", "IDK"))), "updated sports")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test7","test8","test9")),"updated sports")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test7","test8","test9")),"updated sports")
        assertEquals(y.addMCsurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", mapOf("where was the world cup hosted at?" to listOf("USA", "UK", "Russia", "IDK"))), "updated sports")

    }



    @Test
    fun test12(){
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
        clearAdminFile()
        clearMCQuestionsFile()
        createAdminAsServer(Admin(UserStat("Admin", "Watermelon")))
        val y = backendInterface
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", listOf("test","test2","test3")),"created new survey type: sports")
        assertEquals(y.getSurvey("sports"), listOf("test","test2","test3"))
        assertEquals(y.getSurvey("education"), listOf<String>())
        assertEquals(y.addMCsurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"sports", mapOf("where was the world cup hosted at?" to listOf("USA", "UK", "Russia", "IDK"))), "created new survey type: sports for multiple choice")
        assertEquals(y.getSurvey("sports"), listOf("test","test2","test3", Pair( "where was the world cup hosted at?" , listOf("USA", "UK", "Russia", "IDK"))))
        assertEquals(y.createUserProfile(User("test@yahoo.com", "first", "last", "pass", "00/00/1995")), "Successfully created user : test@yahoo.com")
        y.logIn("test@yahoo.com", "pass")
        assertEquals(y.saveResponse("test@yahoo.com","sports", listOf("response1","response2","response3")),"Survey Size and Response size doesn't match!")
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"education", listOf("test4","test5","test6")),"created new survey type: education")
        println(y.getSurvey("sports"))
    }

    @Test
    fun setUpTheBackendWithDefaultValues(){
        clearUserFile()
        clearUserProfileFile()
        clearQuestionsFile()
        clearResponseFile()
        clearAdminFile()
        clearMCQuestionsFile()
        createAdminAsServer(Admin(UserStat("Admin", "Watermelon")))
        createAdminAsServer(Admin(UserStat("greg", "Watermelon")))
        createAdminAsServer(Admin(UserStat("layton", "Watermelon")))
        createAdminAsServer(Admin(UserStat("manish", "Watermelon")))
        createAdminAsServer(Admin(UserStat("kathir", "Watermelon")))
        val y = backendInterface
        assertEquals(y.addSurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"General",
                listOf("How Should Parents Handle a Bad Report Card?" ,
                        "How Would You Like to Help Our World?" ,
                        "Do You Have a Tutor?" ,
                        "To What Piece of Technology Would You Write a ‘Love Letter’?" ,
                        "How Do You Use Wikipedia?" ,
                        "What Are Your Personal Superstitions?" ,
                        "Should Stores Sell Violent Video Games to Minors?")),"created new survey type: General")
        assertEquals(y.addMCsurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"Sports",
                mapOf("I like sports?" to listOf("Agree", "Disagree"),
                        "Why do you participate in sports?"
                                to listOf("Health and fitness", "Personal Interest", "Entertainment and recreation", "Sport skills enhancement"),
                        "Where do you like to do sports?"
                                to listOf("Indoor", "Outdoor", "No Special preference"),
                        "How much time to do you spend playing sports?"
                                to listOf("< 30 mins", "30-60 mins", " 1 to 2 hrs", "> 2 hrs"),
                        "Which of the following sports do you like?"
                                to listOf("Basketball", "Football", "Table Tennis", "Badminton", "Tennis","Volleyball","Squash","Others"),
                        "How likely are you to participate in a University of Iowa sporting event?"
                                to listOf("Very Likely", "Likely", "Neither likely nor unlikely", "Unlikely", "Very Unlikely!")
                        )),"created new survey type: Sports for multiple choice")
        assertEquals(y.addMCsurvey(User("Admin", "ADMINISTRATOR", "last", "Watermelon", "01/01/1111"),"Math Test",
                mapOf("What is 2 + 2??" to listOf("5","-4","4","3"),
                        "What is 10 / 2" to listOf("5","10","2","0"),
                        "What is 10 * 2" to listOf("10","20","30","40"),
                        "What is the integral of the function: 10x^2" to listOf("10x^3/3","20x","10x","30x^3")
                )),"created new survey type: Math Test for multiple choice")
    }

    @Test
    fun test14(){
        val y = backendInterface
        y.createUserProfile(User("chavanmanish018@gmail.com", "greg", "mich", "test", "01/01/1972"))
        y.forgotPassword("chavanmanish018@gmail.com","01/01/1972")
    }
}
