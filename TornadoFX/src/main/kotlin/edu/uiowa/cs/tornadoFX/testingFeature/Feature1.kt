package edu.uiowa.cs.tornadoFX.testingFeature


import oshi.SystemInfo
import java.util.*
import java.net.InetAddress


fun main(args: Array<String>) {
    val hal = SystemInfo().hardware

    println(hal.computerSystem.baseboard.serialNumber)
    for(net in hal.networkIFs){
        println(net.name)
        println(Arrays.toString(net.iPv4addr))
        println(Arrays.toString(net.iPv6addr))
        println(net.macaddr)
    }
    println("-------------------")
    val ip = IPAddress()
    ip.setIpAdd()
    println(ip.thisIpAddress)
}

class IPAddress {

    val thisIp: InetAddress? = null

    var thisIpAddress: String? = null

    protected val ipAddress: String?
        get() {
            setIpAdd()
            return thisIpAddress
        }

    fun setIpAdd() {
        try {
            val thisIp = InetAddress.getLocalHost()
            thisIpAddress = thisIp.hostAddress.toString()
        } catch (e: Exception) {

        }

    }
}