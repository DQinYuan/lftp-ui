package com.github.lftpui.app

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val subject by cssclass()
    }

    init {
        subject {
            fontWeight = FontWeight.BOLD
            fontSize = 20.px
            padding = box(10.px)
        }
    }
}