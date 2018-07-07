package kathiresh.pandian.uiowa.surveys

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
