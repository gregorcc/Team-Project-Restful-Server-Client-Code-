package edu.uiowa

import java.io.File

class Admin(_user: UserStat){
    val user=_user
}


fun adminList(): MutableMap<String, String> {// returns a list of all users
    var input = File("admin").bufferedReader()
    var list = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { list.add(it) } }
    val mapUsers: MutableMap<String, String> = mutableMapOf()
    for (i in list) {
        var temp = i.split(' ') as MutableList
        temp[0] = decrypt(temp[0])
        temp[1] = decrypt(temp[1])
        mapUsers[temp[0]] = temp[1]
    }
    return mapUsers
}

fun saveAdminFile(map:MutableMap<String,String>){
    val file= File("admin").bufferedWriter()
    for (i in map){
        file.write(i.key+" "+i.value)
        file.newLine()
    }
    file.close()
}

fun clearAdminFile():String{ // deletes all contents in the user file
    val size= listOf<String>()
    File("admin").writeText(size.joinToString { System.lineSeparator() })
    println("Admin List has been deleted")
    return ("Admin List has been deleted")
}

fun adminWriterAsServer(user : UserStat, x:Int){// writes on the user file in the persistent storage.
    val prev= adminList()
    prev[user.name]=user.password
    val file= File("admin").bufferedWriter()
    for (i in prev){
        file.write(encrypt(i.key)+" "+ encrypt(i.value))
        file.newLine()
    }
    file.close()
}

fun createAdminAsServer(user : Admin): String{ // tries to create a new user
    val userlist = adminList().keys.toList()
    val userNames= mutableListOf<String>()
    userlist.forEach { userNames.add(it) }
    if(user.user.name in userNames){
        println("Admin userName exists! Please choose another one.")
        return "Admin_userName exists! Please choose another one."
    }
    else{
        adminWriterAsServer(user.user, 0)
    }
    println("Successfully created Admin : ${user.user.name}")
    return "Successfully created Admin : ${user.user.name}"
}

fun deleteAdminAsServer(user : UserStat):String{// tries to delete a user if possible...
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
            adminWriterAsServer(i, userid[temp])
            temp++
        }
        println("Successfully deleted ADMIN- ${user.name}")
        return "Successfully deleted ADMIN- ${user.name}"
    }
    println("Unsuccessful! No such Admin found to delete")
    return "Unsuccessful! No such Admin found to delete"
}

fun encrypt(text: String):String{
    var encryptedText=""
    for ( i in text) {
        if (i == 'q') encryptedText += 'a'
        else if (i == 'w') encryptedText += 's'
        else if (i == 'e') encryptedText += 'd'
        else if (i == 'r') encryptedText += 'f'
        else if (i == 't') encryptedText += 'g'
        else if (i == 'y') encryptedText += 'h'
        else if (i == 'u') encryptedText += 'j'
        else if (i == 'i') encryptedText += 'k'
        else if (i == 'o') encryptedText += 'l'
        else if (i == 'p') encryptedText += 'q'
        else if (i == 'a') encryptedText += 'w'
        else if (i == 's') encryptedText += 'e'
        else if (i == 'd') encryptedText += 'r'
        else if (i == 'f') encryptedText += 't'
        else if (i == 'g') encryptedText += 'y'
        else if (i == 'h') encryptedText += 'u'
        else if (i == 'j') encryptedText += 'i'
        else if (i == 'k') encryptedText += 'o'
        else if (i == 'l') encryptedText += 'p'
        else if (i == 'z') encryptedText += 'm'
        else if (i == 'x') encryptedText += 'n'
        else if (i == 'c') encryptedText += 'v'
        else if (i == 'v') encryptedText += 'c'
        else if (i == 'b') encryptedText += 'x'
        else if (i == 'n') encryptedText += 'z'
        else if (i == 'm') encryptedText += 'b'
        else if (i == '1') encryptedText += '5'
        else if (i == '2') encryptedText += '7'
        else if (i == '3') encryptedText += '0'
        else if (i == '4') encryptedText += '1'
        else if (i == '5') encryptedText += '3'
        else if (i == '6') encryptedText += '4'
        else if (i == '7') encryptedText += '6'
        else if (i == '8') encryptedText += '9'
        else if (i == '9') encryptedText += '8'
        else if (i == '0') encryptedText += '2'
        else encryptedText += i
    }
    return encryptedText
}

fun decrypt(text: String):String{
    var decrypted=""
    for ( i in text) {
        if (i == 'a') decrypted += 'q'
        else if (i == 's') decrypted += 'w'
        else if (i == 'd') decrypted += 'e'
        else if (i == 'f') decrypted += 'r'
        else if (i == 'g') decrypted += 't'
        else if (i == 'h') decrypted += 'y'
        else if (i == 'j') decrypted += 'u'
        else if (i == 'k') decrypted += 'i'
        else if (i == 'l') decrypted += 'o'
        else if (i == 'q') decrypted += 'p'
        else if (i == 'w') decrypted += 'a'
        else if (i == 'e') decrypted += 's'
        else if (i == 'r') decrypted += 'd'
        else if (i == 't') decrypted += 'f'
        else if (i == 'y') decrypted += 'g'
        else if (i == 'u') decrypted += 'h'
        else if (i == 'i') decrypted += 'j'
        else if (i == 'o') decrypted += 'k'
        else if (i == 'p') decrypted += 'l'
        else if (i == 'm') decrypted += 'z'
        else if (i == 'n') decrypted += 'x'
        else if (i == 'v') decrypted += 'c'
        else if (i == 'c') decrypted += 'v'
        else if (i == 'x') decrypted += 'b'
        else if (i == 'z') decrypted += 'n'
        else if (i == 'b') decrypted += 'm'
        else if (i == '5') decrypted += '1'
        else if (i == '7') decrypted += '2'
        else if (i == '0') decrypted += '3'
        else if (i == '1') decrypted += '4'
        else if (i == '3') decrypted += '5'
        else if (i == '4') decrypted += '6'
        else if (i == '6') decrypted += '7'
        else if (i == '9') decrypted += '8'
        else if (i == '8') decrypted += '9'
        else if (i == '2') decrypted += '0'
        else decrypted += i
    }
    return decrypted
}

