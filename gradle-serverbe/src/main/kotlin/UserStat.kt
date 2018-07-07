package edu.uiowa

import java.io.File

class UserStat (_name: String, _password: String){
    val name=_name
    val password=_password
}

fun userList(): MutableMap<UserStat,Int>{// returns a list of all users
    var input= File("UserList").bufferedReader()
    var list = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { list.add(it)} }
    val mapUsers: MutableMap<UserStat,Int> = mutableMapOf()
    for (i in list){
        var temp=i.split(' ')
        mapUsers[UserStat(temp[0],temp[1])]=temp[2].toInt()
    }
    return mapUsers
}


fun userList2(): MutableMap<String,String>{// returns a list of all users
    var input= File("UserList").bufferedReader()
    var list = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { list.add(it)} }
    val mapUsers: MutableMap<String,String> = mutableMapOf()
    for (i in list){
        var temp=i.split(' ') as MutableList
        temp[0]= decrypt(temp[0])
        temp[1]= decrypt(temp[1])
        mapUsers[temp[0]]=temp[1]
    }
    return mapUsers
}

fun userWriterAsServer(user : UserStat, x:Int){// writes on the user file in the persistent storage.
    val prev= File("UserList").readLines()+"${user.name} ${user.password} $x"
    val file= File("UserList").bufferedWriter()
    for (i in prev){
        file.write(encrypt(i))
        file.newLine()
    }
    file.close()
}

fun saveUserFile(map:MutableMap<String,String>){
    val file= File("UserList").bufferedWriter()
    for (i in map){
        file.write(encrypt(i.key+" "+i.value))
        file.newLine()
    }
    file.close()
}
/*
fun checkUser(user:UserStat):String{// responds if a user has already taken a survey or not.
    // Also checks if an username is valid or not
    val userlist= userList()
    val userslist=userlist.keys.toList()
    var flag=0
    for (i in userslist) {
        if (i.name == user.name && i.password == user.password) {
            if (userlist[i] == 1) {
                println("You have already taken the survey!")
                return "You have already taken the survey!"
            } else if (userlist[i] == 0) {
                println("You haven't yet taken the survey!")
                return "You haven't yet taken the survey!"
            } else if (userlist[i] == -100) {
                println("you are the Admin. You cannot take the survey!!")
                return "you are the Admin. You cannot take the survey!!"
            } else {
                println("error 101: user found! survey record is corrupted.")
                return "error 101: user found! survey record is corrupted."
            }
        }
        if(i.name==user.name) flag=1
    }
    if(flag==1) {
        println("Wrong Password...")
        return "Wrong Password..."
    }
    else {
        println("No such User found")
        return "No such User found"
    }
}
*/

fun clearUserFile():String{ // deletes all contents in the user file
    val size= listOf<String>()
    File("UserList").writeText(size.joinToString { System.lineSeparator() })
    println("User List has been deleted")
    return ("User List has been deleted")
}

fun createUserAsServer(user : UserStat): String{ // tries to create a new user
    val userlist = userList().keys.toList()
    val userNames= mutableListOf<String>()
    userlist.forEach { userNames.add(it.name) }
    if(user.name in userNames){
        println("Username exists! Please choose another one.")
        return "Username exists! Please choose another one."
    }
    else{
        userWriterAsServer(user, 0)
    }
    println("Successfully created user : ${user.name}")
    return "Successfully created user : ${user.name}"
}

fun deleteUserAsServer(user : UserStat):String{// tries to delete a user if possible...
    val users= userList()
    var userlist = users.keys.toMutableList()
    val userid=users.values.toMutableList()
    var flag=0
    var temp=0
    var temp2=0
    for(i in userlist) {
        if (user.name == i.name && user.password == user.password) {
            temp2=temp
            flag=1
        }
        temp++
    }
    if(flag==1) {
        userlist.removeAt(temp2)
        userid.removeAt(temp2)
        temp = 0
        clearUserFile()
        for (i in userlist) {
            userWriterAsServer(i, userid[temp])
            temp++
        }
        println("Successfully deleted ${user.name}")
        return "Successfully deleted ${user.name}"
    }
    println("Unsuccessful! No such user found to delete")
    return "Unsuccessful! No such user found to delete"
}

fun forgotPasswordAsServer(profile: UserProfile, newPassword:String): String{
    val map= profileList()
    val map2 = userList2()
    var flag=-1
    for(i in map) {
        if (i.name == profile.name) {
            if (i.birthday == profile.birthday && i.firstName == profile.firstName && i.lastName == profile.lastName) {
                if (map2[profile.name] != newPassword) {
                    deleteUserAsServer(UserStat(profile.name, map2[profile.name]!!))
                    createUserAsServer(UserStat(profile.name, newPassword))
                    return "Updated Password for ${profile.name}"
                } else return "Same as old Password"
            }
            flag = 23
        }
    }
    if (flag==-1) return "No such User name found for ${profile.name}"
    return "Incorrect credentials provided. Unable to change password for ${profile.name}"
}
