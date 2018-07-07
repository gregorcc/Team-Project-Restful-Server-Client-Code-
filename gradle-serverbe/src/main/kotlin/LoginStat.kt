package edu.uiowa

import java.io.File

class LoginStat(userName : String, status:String)

fun logInAsBackend(list: MutableMap<String,String>, log: MutableMap<String, String>, user: UserStat):String{
    for (i in list){
        if(i.key==user.name && i.value==user.password){
            return if(log[user.name]=="loggedIn"){
                println("${user.name} is already loggedIn")
                ("${user.name} is already loggedIn")
            } else{
                log[user.name]="loggedIn"
                "${user.name} successfully logged in"
            }
        }
        else if(i.key==user.name && i.value!=user.password){
            println("Incorrect password")
            return "Incorrect Credentials"
        }
    }
    return "No such User Exists"
}

fun logOutAsBackend(log: MutableMap<String, String>, userName: String): String{
    for (i in log){
        if(i.key==userName){
            log.remove(i.key)
            return "logged out"
        }
    }
    return "$userName is not logged in"
}

fun checkLoginStatAsBackend(log: MutableMap<String, String>, userName: String):Boolean{
    for ( i in log){
        if(i.key==userName) return true
    }
    return false
}


fun adminLogInAsBackend(list: MutableMap<String,String>, log: MutableMap<String, String>, user: UserStat):String{
    for (i in list){
        if(i.key==user.name && i.value==user.password){
            return if(log[user.name]=="loggedIn"){
                println("${user.name} is already loggedIn")
                ("${user.name} is already loggedIn")
            } else{
                log[user.name]="loggedIn"
                "${user.name} successfully logged in"
            }
        }
        else if(i.key==user.name && i.value!=user.password){
            println("Incorrect password")
            return "Incorrect Credentials"
        }
    }
    return "No such Admin Exists"
}

fun adminLogOutAsBackend(log: MutableMap<String, String>, userName: String): String{
    for (i in log){
        if(i.key==userName){
            log.remove(i.key)
            return "Admin logged out"
        }
    }
    return "Admin-$userName is not logged in"
}


fun logInAsServer(user: UserStat): String{
    val userL = userList().keys.toList()
    for (i in userL){
        if(i.name==user.name && i.password==user.password){
            var map=readLoginStatFile().toMutableMap()
            if(map[user.name]=="loggedIn"){
                println("${user.name} is already loggedIn")
                return ("${user.name} is already loggedIn")
            }
            else{
                var input= File("LoginStatus").bufferedWriter()
                map[user.name]="loggedIn"
                for ( j in map){
                    input.write(j.key+" "+j.value)
                    input.newLine()
                }
                input.close()
                return "${user.name} successfully logged in"
            }
        }
        else if(i.name==user.name && i.password!=user.password){
            println("Incorrect password")
            return "Incorrect Password"
        }
    }
    println("No such User Exists in the User List File!! Basically the file is Empty")
    return "No such User Exists"
}

fun logOutAsServer(userName: String): String{
    var map = readLoginStatFile().toMutableMap()
    for (i in map){
        if(i.key==userName){
            map.remove(i.key)
            val input= File("LoginStatus").bufferedWriter()
            for ( j in map){
                input.write(j.key+" "+j.value)
                input.newLine()
            }
            input.close()
            return "logged out!"
        }
    }
    return "$userName is not logged in"
}

fun checkLoginStatForAdminAsBackend(log: MutableMap<String, String>, userName: String):Boolean{
    for ( i in log){
        if(i.key==userName) return true
    }
    return false
}

fun checkLoginStatAsServer(userName: String):Boolean{
    val map=readLoginStatFile()
    for ( i in map){
        if(i.key==userName) return true
    }
    return false
}

fun readLoginStatFile(): MutableMap<String, String>{
    var input= File("LoginStatus").bufferedReader()
    var list = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { list.add(it)} }
    var test: List<String>
    var map = mutableMapOf<String, String>()
    for (i in list){
        test=i.split(" ")
        map[test[0]]=test[1]
    }
    return map
}

fun writeToLoginStatFile(map: Map<String, String>){
    val map2=map.toSortedMap()
    val write=File("LoginStatus").bufferedWriter()
    var temp: String
    for ( i in map2) {
        temp=i.key+" "+i.value
        write.write(temp)
        write.newLine()
    }
    }

//TODO clearLoginStatFile cannot be accessed by client

fun clearLoginStatFile():String { // deletes all contents in the profile file
    val size = listOf<String>()
    File("LoginStatus").writeText(size.joinToString { System.lineSeparator() })
    println("User Profile Data File has been cleared")
    return ("User Profile Data File has been cleared")
}
