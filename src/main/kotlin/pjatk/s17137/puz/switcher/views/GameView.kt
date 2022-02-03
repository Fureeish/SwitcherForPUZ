package pjatk.s17137.puz.switcher.views

import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ListCell
import javafx.scene.control.Spinner
import javafx.util.Callback
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import pjatk.s17137.puz.switcher.viewmodels.GameViewViewModel
import tornadofx.*

class GameView : View("To switch or not to switch? ( ͡° ͜ʖ ͡°)") {
    private val viewModel: GameViewViewModel by inject()

    private val marginInsets = Insets(10.0)

    override val root = borderpane {
        top = spinner<Int>() {
            id = "doorsCountSpinner"

            BorderPane.setAlignment(this, Pos.CENTER)
            BorderPane.setMargin(this, marginInsets)

            valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(3, 10)
            valueFactory.valueProperty().bindBidirectional(viewModel.prop)

            valueFactory.valueProperty().addListener { _, _, _ ->
                viewModel.rearrangeDoors()
            }
        }

        center = listview(viewModel.allDoors) {
            orientation = Orientation.HORIZONTAL

            onUserSelect {
                viewModel.doorsClicked(it)
            }

            cellFactory = Callback { listView ->
                object : ListCell<ImageView>() {
                    init {
                        style = "-fx-background-color: #FFFFFF"
                    }

                    override fun updateItem(item: ImageView?, empty: Boolean) {
                        super.updateItem(item, empty)
                        graphic = item
                    }

//                    override fun setMinSize(prefWidth: Double, prefHeight: Double) {
//                        val newPrefWidth = listView.prefWidth / listView.items.size
//                        super.setMinSize(newPrefWidth, prefHeight)
//                    }


                }
            }
        }

        bottom = button("Randomize") {
            BorderPane.setMargin(this, marginInsets)
            BorderPane.setAlignment(this, Pos.CENTER)

            action { viewModel.rearrangeDoors() }
        }
    }
}