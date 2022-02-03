package pjatk.s17137.puz.switcher.views

import javafx.geometry.Pos
import pjatk.s17137.puz.switcher.styles.Styles
import pjatk.s17137.puz.switcher.viewmodels.StartingViewViewModel
import tornadofx.*


class StartingView : View("To switch or not to switch? ( ͡° ͜ʖ ͡°)") {
    private val viewModel: StartingViewViewModel by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 10.0

        label(title) {
            addClass(Styles.heading)
        }

        button("Start!") {
            action {
                replaceWith(GameView::class, transition = ViewTransition.FadeThrough(0.5.seconds))
            }
        }
    }
}