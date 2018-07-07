package edu.uiowa

import java.io.File

class Response(_userName: String, _listOfResponse: List<String>, _whichSurvey: String ){
    val username=_userName
    val listOfResponse=_listOfResponse
    val whichSurvey=_whichSurvey
}

// TODO only ADMIN can access this function.
internal fun clearResponseFile():String { // deletes all contents in the user file
    val size = listOf<String>()
    File("ResponseData").writeText(size.joinToString { System.lineSeparator() })
    println("Response Data File has been cleared")
    return ("Response Data File has been cleared")
}

/*
fun saveResponseAsServer(response:Response):String{// save a list of responses in hierarchical order of the questions asked in persistent storage.
    // NOTE: This function is built(in-construction) only for a single user at tun-time. So it only records one survey response until the server is down.
        val read=File("ResponseData").bufferedReader()
        var list = mutableListOf<String>()
        if(read!=null) {
            read.useLines { lines -> lines.forEach { list.add(it) } }
            read.close()
        }
        var input = File("ResponseData").bufferedWriter()
        for (i in list) {
            input.write(i)
         input.newLine()
        }
        input.write(response.username)
        input.newLine()
        input.write(response.whichSurvey+" = ")
        for(i in response.listOfResponse){
            input.write(i)
            if(i==response.listOfResponse[response.listOfResponse.size-1]){
                input.write(" ;;;; ")
            }
            else input.write(" ## ")
        }
        input.newLine()
        input.newLine()
        input.newLine()
        input.close()
        return "Saved Your Response! ThankYou for your time!"

    //TODO. please use sorting algorithm to save the response data by username.
    }

*/
fun saveResponseAsServer(response:Response):String{
    val map= readResponseDataAsServer().toMutableMap()
    //val write=File("Response").bufferedWriter()
    if(!(map.isEmpty())) {
        if (map.containsKey(response.username)) {
            if (map[response.username]?.contains(response.whichSurvey) == true) {
                var str = ""
                for ((p,i) in response.listOfResponse.withIndex()) {
                    if(p==response.listOfResponse.size-1){
                        str+=i
                    }else str += i + " ## "
                }
                str += " ;;;;"
                val emp = map[response.username]?.toMutableMap()
                var o = 0
                if (emp != null) {
                    val p = emp.keys
                    for ((i, w) in p.withIndex()) {
                        val r = response.whichSurvey + i
                        if (w != r) {
                            o = i+1
                        }
                    }
                }
                val surveyName = response.whichSurvey + o
                emp!![surveyName] = str
                map[response.username] = emp
                writeResponseData(map)
                return "Till now $o times ${response.username}'s  ${response.whichSurvey} has been updated"
            }
        }
    }
        var str = ""
        for ((p,i) in response.listOfResponse.withIndex()) {
            if(p==response.listOfResponse.size-1){
                str+=i
            }else str += i + " ## "
        }
        str += " ;;;;"
        map[response.username] = mutableMapOf(response.whichSurvey to str)
        writeResponseData(map)
        return "Added for the first time //${response.whichSurvey}// to this username= ${response.username}"
}

// TODO Network CANNOT ACCESS writeResponseData()
internal fun writeResponseData(map :Map<String,Map<String,String>>){
    val map2=map.toSortedMap()
    val write=File("ResponseData").bufferedWriter()
    for ( i in map2){
        write.write(i.key)
        write.newLine()
        var str=""
        var t=0
        for(w in i.value){
            write.write(w.key+" = "+w.value)
            write.newLine()
        }
        write.newLine()
        write.newLine()
    }
    write.close()
}

// TODO : implement deleteResponse only for administrator

fun deleteResponse(username: String, whichSurvey: String):String{
    val map= readResponseDataAsServer().toMutableMap()
    if(map.containsKey(username)){
        if(map[username]!!.containsKey(whichSurvey)){
            var temp= map[username]!!.toMutableMap()
            temp?.remove(whichSurvey)
            map[username] = temp
            writeResponseData(map)
            return "Successfully deleted $whichSurvey for $username"
        }
        else{
            return "No such survey found!"
        }
    }
    else return "No such userName found!"
}

// TODO : implement deleteResponse only for administrator

fun getResponse(username: String, whichSurvey: String): String{
    val map= readResponseDataAsAdmin()
    if(map.containsKey(username)){
        if(map[username]?.containsKey(whichSurvey)!!) {
            return map.getValue(username).get(whichSurvey)!!
        }
        println("No $whichSurvey record found in $username's records!!")
        return "No $whichSurvey record found in $username's records!!"
    }
    println("No $username record found")
    return "No $username record found"
}

// TODO Network CANNOT ACCESS readResponseDataAsAdmin()

fun readResponseDataAsAdmin(): MutableMap<String,Map<String,String>>{
    val test= mutableMapOf<String,Map<String,String>>()
    val input = File("ResponseData").bufferedReader()
    val tempList = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { tempList.add(it) } }
    var x=0
    var flag=-1
    for ((y, i) in tempList.withIndex()){
        if(flag==100 && i.isBlank()){
            val subList= tempList.subList(x+1,y-1)
            val subMap= mutableMapOf<String,String>()
            for ( i in subList) {
                val q=i.substringBefore(";;;;").split(" = ")
                subMap[q[0]]=q[1].replace(" ## "," ; ")
            }
            test[tempList[x]]=subMap
            x=y+1
        }
        else if(i.isBlank()){
            flag=100
        }
        else flag=-1
    }
    input.close()
    return test
}

// TODO Network CANNOT ACCESS readResponseDataAsServer()

fun readResponseDataAsServer(): MutableMap<String,MutableMap<String,String>>{
    val test= mutableMapOf<String,MutableMap<String,String>>()
    val input = File("ResponseData").bufferedReader()
    val tempList = mutableListOf<String>()
    input.useLines { lines -> lines.forEach { tempList.add(it) } }
    var x=0
    var flag=-1
    for ((y, i) in tempList.withIndex()){
        if(flag==100 && i.isBlank()){
            val subList= tempList.subList(x+1,y-1)
            val subMap= mutableMapOf<String,String>()
            for ( i in subList) {
                val q=i.substringBefore(";;;;").split(" = ")
                subMap[q[0]]=q[1]+";;;;"
            }
            test[tempList[x]]=subMap
            x=y+1
        }
        else if(i.isBlank()){
            flag=100
        }
        else flag=-1
    }
    input.close()
    return test
}

fun saveResponseAsBackend(map:MutableMap<String,MutableMap<String,String>>, surveyTypeList:List<String>, response:Response):String {
    if (!(map.isEmpty())) {
        if (map.containsKey(response.username)) {//check to see if this user has previously saved any responses.
                if (map[response.username]?.contains(response.whichSurvey) == true) {
                    var str = ""
                    for ((p, i) in response.listOfResponse.withIndex()) {
                        if (p == response.listOfResponse.size - 1) {
                            str += i
                        } else str += i + " ## "
                    }
                    str += " ;;;;"
                    map[response.username]!![response.whichSurvey] = str
                    return "Updated ${response.whichSurvey} for ${response.username}"
                } else {
                    var str = ""
                    for ((p, i) in response.listOfResponse.withIndex()) {
                        if (p == response.listOfResponse.size - 1) {
                            str += i
                        } else str += i + " ## "
                    }
                    str += " ;;;;"
                    map[response.username]!![response.whichSurvey] = str
                    return "Added first ${response.whichSurvey} survey response for ${response.username}"
                }
            }
        }
    var str = ""
    for ((p, i) in response.listOfResponse.withIndex()) {
        if (p == response.listOfResponse.size - 1) {
            str += i
        } else str += i + " ## "
    }
    str += " ;;;;"
    map[response.username] = mutableMapOf(response.whichSurvey to str)
    writeResponseData(map)
    return "First Survey added for ${response.username}"
}

fun updateResponseFile(response: MutableMap<String,MutableMap<String,String>>, surveyType: String, number: Int, flag: Int){
    //  TODO move the MC responses by one indent if its a free responses question which was added...
    // TODO Basically if its a MC survey type then add it in the last.
    for( i in response){
        for( j in i.value) {
            if (j.key == surveyType) {
                var str = j.value.substring(0,j.value.length-5)+" ##"
                if( flag==-100) {
                    var number2 = number
                    while (number2 > 0) {
                        if (number2 != 1) {
                            str += " newlyAddedMCQuestion ##"
                            number2--
                        } else {
                            str += " newlyAddedMCQuestion ;;;;"
                            number2--
                        }
                    }
                }else{
                    val str2= str.split("##")
                    val left=str2.subList(0,flag) as MutableList
                    for( (o,t) in left.withIndex()) left[o]= "$t##"
                    var number2 = number
                    while (number2 > 0) {
                        if (number2 != 1) {
                            left.add(" newlyAddedQuestion ##")
                        } else {
                            left.add(" newlyAddedQuestion ;;;;")
                        }
                        number2--
                    }
                    str=""
                    for ( p in left) str+=p
                    if(str2.subList(flag, str2.size).size >1) str=str.substring(0, str.length-4)+"##"
                    for(a in str2.subList(flag+number, str2.size-1)){
                        str+="$a##"
                    }
                    if(str2.subList(flag, str2.size).size >1) str=str.substring(0, str.length-2)+";;;;"
                }
                response[i.key]!![j.key]=str
            }
        }
    }
}
