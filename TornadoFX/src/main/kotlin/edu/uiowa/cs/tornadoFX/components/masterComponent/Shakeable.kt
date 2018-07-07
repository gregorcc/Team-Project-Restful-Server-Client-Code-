package edu.uiowa.cs.tornadoFX.components.masterComponent

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.util.Duration
import tornadofx.*

/*
*   This is interface contains Shaking function, it will shake the stage.
*
* */

interface Shakeable{
    fun shakeStage() {
        var x = 0
        var y = 0
        val cycleCount = 10
        val move = 10
        val keyframeDuration = Duration.seconds(0.04)

        val stage = FX.primaryStage

        val timelineX = Timeline(KeyFrame(keyframeDuration, EventHandler {
            if (x == 0) {
                stage.x = stage.x + move
                x = 1
            } else {
                stage.x = stage.x - move
                x = 0
            }
        }))

        timelineX.cycleCount = cycleCount
        timelineX.isAutoReverse = false

        val timelineY = Timeline(KeyFrame(keyframeDuration, EventHandler {
            if (y == 0) {
                stage.y = stage.y + move
                y = 1
            } else {
                stage.y = stage.y - move
                y = 0
            }
        }))

        timelineY.cycleCount = cycleCount
        timelineY.isAutoReverse = false

        timelineX.play()
        timelineY.play()
    }
}