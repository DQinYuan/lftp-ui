package com.github.lftpui.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

class Job(id: Int, description: String) {
    val descriptionProperty = SimpleStringProperty(description)
    var description by descriptionProperty

    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty
}
