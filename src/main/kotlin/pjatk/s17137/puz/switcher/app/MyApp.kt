package pjatk.s17137.puz.switcher.app

import javafx.stage.Stage
import pjatk.s17137.puz.switcher.styles.Styles
import pjatk.s17137.puz.switcher.views.StartingView
import tornadofx.App

class MyApp: App(StartingView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.minWidth = 600.0
        stage.minHeight = 400.0
        stage.isAlwaysOnTop = true
        super.start(stage)
    }
}