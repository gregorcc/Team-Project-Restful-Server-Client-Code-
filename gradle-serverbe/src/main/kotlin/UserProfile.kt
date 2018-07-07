package edu.uiowa

import java.io.File

//TODO: implement a common object once the server has started running or for the first call to avoid multiple calls to read and write to file,

class UserProfile(_userName:String, _firstName:String, _lastName:String, _birthday: String){
    val name=_userName
    val firstName=_firstName
    val lastName=_lastName
    val birthday=_birthday
}

//TODO only ADMIN can access clearUserProfileFile()

fun clearUserProfileFile():String { // deletes all contents in the profile file
    val size = listOf<String>()
    File("UserProfile").writeText(size.joinToString { System.lineSeparator() })
    println("User Profile Data File has been cleared")
    return ("User Profile Data File has been cleared")
}

// TODO Network CANNOT access profileList()

fun profileList():MutableList<UserProfile>{
    var input= File("UserProfile").bufferedReader()
    var list = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { list.add(it)} }
    val profileList = mutableListOf<UserProfile>()
    for(i in list){
        val seg= i.split(' ')
        profileList.add(UserProfile(seg[0],seg[1],seg[2],seg[3]))
    }
    return profileList
}

fun getProfileAsServer(name: String):UserProfile{
    val plist= profileList()
    for (i in plist) if (name==i.name) return i
    return UserProfile("User Profile Not FOUND","","","")
}

fun addProfileAsServer(profile:UserProfile):String{
    val test= getProfileAsServer(profile.name)
    if(test.name!="User Profile Not FOUND") return "User Profile already exists!!"
    else{
        val prev= File("UserProfile").readLines()+"${profile.name} ${profile.firstName} ${profile.lastName} ${profile.birthday}"
        val file= File("UserProfile").bufferedWriter()
        for (i in prev){
            file.write(i)
            file.newLine()
        }
        file.close()
    }
    return "Successfully added profile"
}


fun addProfile(list: MutableList<UserProfile>,profile:UserProfile):String{
    for (i in list){
        if(i.name==profile.name){
            return "User Profile already exists"
        }
    }
    list+=profile
    return "Successfully added profile"
}

fun saveProfileFile(profiles: MutableList<UserProfile>){
    val file= File("UserProfile").bufferedWriter()
    for (i in profiles){
        file.write(i.name+" "+i.firstName+" "+i.lastName+" "+i.birthday)
        file.newLine()
    }
    file.close()
}

// TODO Network CANNOT access deleteProfileAsServer()
// TODO when a user is deleted, userProfile should be updated too.

fun deleteProfileAsServer(name: String): String{
    val list= profileList().toMutableList()
    var flag=-1
    for ( (i,o) in list.withIndex()){
        if (o.name==name){
            flag=i
            }
        }
    if(flag!=-1) {
        list.removeAt(flag)
        clearUserProfileFile()
        val file = File("UserProfile").bufferedWriter()
        for (j in list) {
            file.write(j.name + " " + j.firstName + " " + j.lastName + " " + j.birthday)
            file.newLine()
            file.close()
            println("deleted ${name}'s profile")
            return "deleted ${name}'s profile"
        }
    }
    println("User Profile not found!!! ERROR")
    return "User Profile not found!!! ERROR"
}

