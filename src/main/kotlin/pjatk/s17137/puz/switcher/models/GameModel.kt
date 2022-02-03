package pjatk.s17137.puz.switcher.models

import javafx.beans.binding.Bindings
import javafx.beans.binding.IntegerBinding
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.image.ImageView
import tornadofx.*
import kotlin.random.Random

class GameModel {
    val allDoors = FXCollections.observableArrayList<ImageView>()

    private val winningDoorsIndexBinding = object: IntegerBinding() {
        init {
            super.bind(Bindings.size(allDoors))
        }

        override fun computeValue(): Int {
            return Random.nextInt(0, allDoors.sizeProperty.value)
        }
    }

    val winningDoorsIndex get() = winningDoorsIndexBinding.value

    var otherDoorsAreOpened = false

    fun indexOf(doors: ImageView): Int {
        return allDoors.indexOf(doors)
    }

    fun reset() {
        otherDoorsAreOpened = false
        winningDoorsIndexBinding.invalidate()
    }
}
