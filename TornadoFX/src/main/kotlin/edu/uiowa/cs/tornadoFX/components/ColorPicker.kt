package edu.uiowa.cs.tornadoFX.components


import java.awt.Color
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.ImageReader

// This is the testing function.
object ImageTester {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val file = File("C:\\Users\\ThisUser\\Pictures\\Capture.png")
        val `is` = ImageIO.createImageInputStream(file)
        val iter = ImageIO.getImageReaders(`is`)

        if (!iter.hasNext()) {
            println("Cannot load the specified file $file")
            System.exit(1)
        }
        val imageReader = iter.next() as ImageReader
        imageReader.input = `is`

        val image = imageReader.read(0)

        val height = image.height
        val width = image.width

        val m = HashMap<Int,Int>()
        for (i in 0 until width) {
            for (j in 0 until height) {
                val rgb = image.getRGB(i, j)
                val rgbArr = getRGBArr(rgb)
                // Filter out grays....
                if (!isGray(rgbArr)) {
                    var counter: Int? = m.get(rgb)
                    if (counter == null)
                        counter = 0
                    counter++
                    m.put(rgb, counter)
                }
            }
        }
        val colourHex = getMostCommonColour(m)
        println(colourHex.toString() )
        println("TornadoFX: ColorPicker      -->     Ok")
    }

    // This is the main function of find most common color in the picture.
    fun colorPicker(file:File) : javafx.scene.paint.Color{
        val `is` = ImageIO.createImageInputStream(file)
        val iter = ImageIO.getImageReaders(`is`)

        if (!iter.hasNext()) {
            println("Cannot load the specified file $file")
            System.exit(1)
        }
        val imageReader = iter.next() as ImageReader
        imageReader.input = `is`

        val image = imageReader.read(0)
        val height = image.height
        val width = image.width

        val m = HashMap<Int,Int>()
        for (i in 0 until width) {
            for (j in 0 until height) {
                val rgb = image.getRGB(i, j)
                val rgbArr = getRGBArr(rgb)
                // Filter out grays....
                if (!isGray(rgbArr)) {
                    var counter: Int? = m.get(rgb)
                    if (counter == null)
                        counter = 0
                    counter++
                    m.put(rgb, counter)
                }
            }
        }

        var colourHex = getMostCommonColour(m)
        println("TornadoFX: ColorPicker      -->     Ok")

        // if the picture is completely black and white, then I pick a random bright color.
        if (colourHex == null){
            val randValue = Random()
            val r = randValue.nextDouble()/2F + 0.5F
            val g = randValue.nextDouble()/2F + 0.5F
            val b = randValue.nextDouble()/2F + 0.5F
            return javafx.scene.paint.Color.color(r,g,b)
        }else {
            var colourHexCopy = javafx.scene.paint.Color.color(colourHex.red.toDouble() / 255.0,
                    colourHex.green.toDouble() / 255.0,
                    colourHex.blue.toDouble() / 255.0,
                    colourHex.alpha.toDouble() / 255.0)

            // Since I am using shadow effect on the text, I want new text being identical from black color but also
            // want those color derived from same color family.
            colourHexCopy = when {
                colourHexCopy.brightness > 0.75 -> colourHexCopy.brighter()
                colourHexCopy.brightness > 0.5 -> colourHexCopy.brighter().brighter()
                colourHexCopy.brightness > 0.25 -> colourHexCopy.darker()
                else -> colourHexCopy.darker()
            }

            return colourHexCopy
        }
    }

    private fun getMostCommonColour(map: Map<*, *>): Color? {
        val list = map.entries.toMutableList()
        list.sortWith(Comparator { o1, o2 ->
            (o1.value as Int) //as Comparable<*>
                    .compareTo(o2.value  as Int)
        })
        return try {
            val me = list[list.size - 1] as Map.Entry<*, *>
            val rgb = getRGBArr(me.key as Int)
            Color(rgb[0],rgb[1],rgb[2])
        }catch (e : IndexOutOfBoundsException){
            null
        }

    }

    private fun getRGBArr(pixel: Int): IntArray {
        //val alpha = pixel shr 24 and 0xff
        val red = pixel shr 16 and 0xff
        val green = pixel shr 8 and 0xff
        val blue = pixel and 0xff
        return intArrayOf(red, green, blue)

    }

    private fun isGray(rgbArr: IntArray): Boolean {
        val rgDiff = rgbArr[0] - rgbArr[1]
        val rbDiff = rgbArr[0] - rgbArr[2]
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        val tolerance = 10
        if (rgDiff > tolerance || rgDiff < -tolerance)
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false
            }
        return true
    }
}