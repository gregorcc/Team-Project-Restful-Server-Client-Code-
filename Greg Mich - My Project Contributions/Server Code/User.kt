package edu.uiowa

open class User {
    //call the super constructor, takes the same params as a Person
    val userName: String
    val firstName: String
    val lastName: String
    val password: String
    val birthday: String

    constructor(userName: String, firstName: String, lastName: String, password: String, birthday: String) {
        this.userName = userName
        this.firstName = firstName
        this.lastName = lastName
        this.password = password
        this.birthday = birthday
    }

    override fun toString(): String {
        return "Username: $userName\nName: $firstName $lastName\nPassword: $password\nAge: $birthday"
    }
}

fun createUser(list: MutableMap<String,String>, user: UserStat): String {
    val userNames = list.keys.toList()
    if (user.name in userNames) {
        return "Username exists! Please choose another one."
    }
    list[user.name] = user.password
    return "Successfully created user : ${user.name}"
}
