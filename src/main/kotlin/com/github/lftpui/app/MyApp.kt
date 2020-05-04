package com.github.lftpui.app

import com.github.lftpui.view.MainView
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {
/*    init {
        reloadViewsOnFocus()
        reloadStylesheetsOnFocus()
    }*/

    override fun start(stage: Stage) {
        super.start(stage)
        stage.isMaximized = true
    }
}