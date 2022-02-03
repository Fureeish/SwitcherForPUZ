package pjatk.s17137.puz.switcher.viewmodels

import javafx.animation.FadeTransition
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import pjatk.s17137.puz.switcher.models.GameModel
import tornadofx.*
import java.util.*
import kotlin.random.Random

class GameViewViewModel : ViewModel() {
    companion object {
        private val closedDoorsResource =
            GameViewViewModel::class.java.getResource("/closed_doors.png")
        private val emptyOpenedDoorsResource =
            GameViewViewModel::class.java.getResource("/empty_opened_doors.png")
        private val winnerDoorsResource =
            GameViewViewModel::class.java.getResource("/winner_doors.png")
    }

    private val model = GameModel()
    val allDoors: ObservableList<ImageView> get() = model.allDoors
    val prop = SimpleObjectProperty(3)
    private val spinnerValueProperty: IntegerProperty = IntegerProperty.integerProperty(prop)

    init {
        repeat(3) {
            allDoors.add(ImageView(Image(closedDoorsResource.toExternalForm())))
        }
    }

    fun doorsClicked(doors: ImageView) {
        if (model.otherDoorsAreOpened) {
            openFinalDoors(doors)

            Timer(true).also { timer ->
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        runLater {
                            rearrangeDoors()
                        }
                    }
                }, 1000)
            }
        } else {
            model.otherDoorsAreOpened = true

            val initialOpacity = 1.0
            val targetOpacity = 0.5
            val halfOfDuration = 0.5.seconds

            val firstAnimationPart = FadeTransition(halfOfDuration, doors).apply {
                fromValue = initialOpacity
                toValue = targetOpacity
            }
            val secondAnimationPart = FadeTransition(halfOfDuration, doors).apply {
                fromValue = targetOpacity
                toValue = initialOpacity
            }

            val doorsFlashAnimation = firstAnimationPart.and(secondAnimationPart).apply {
                onFinished = EventHandler {
                    reactToInitialDoorsClicked(doors)
                }
            }

            doorsFlashAnimation.play()
        }
    }

    private fun openFinalDoors(doors: ImageView) {
        val indexOfDoorsClicked = model.indexOf(doors)

        if (indexOfDoorsClicked == model.winningDoorsIndex) {
            doors.image = Image(winnerDoorsResource.toExternalForm())
        } else {
            doors.image = Image(emptyOpenedDoorsResource.toExternalForm())
        }
    }

    private fun reactToInitialDoorsClicked(doors: ImageView) {
        val indexOfDoorsClicked = model.indexOf(doors)
        var indexOfOtherClosedDoors = indexOfDoorsClicked

        if (indexOfDoorsClicked == model.winningDoorsIndex) {
            while (indexOfOtherClosedDoors == model.winningDoorsIndex) {
                indexOfOtherClosedDoors = Random.nextInt(0, model.allDoors.size)
            }
        } else {
            indexOfOtherClosedDoors = model.winningDoorsIndex
        }

        allDoors
            .filterIndexed { index, _ ->
                index != indexOfDoorsClicked && index != indexOfOtherClosedDoors
            }
            .forEach {
                it.image = Image(emptyOpenedDoorsResource.toExternalForm())
            }
    }

    fun rearrangeDoors() {
        allDoors.apply {
            allDoors.clear()
            repeat(spinnerValueProperty.value) {
                add(ImageView(Image(closedDoorsResource.toExternalForm())))
            }
        }
        model.reset()
    }
}