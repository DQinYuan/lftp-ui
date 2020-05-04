package com.github.lftpui.model

import com.github.lftpui.ssh.SshUser
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

class SshInfo {
    val dowloadPathProperty = SimpleStringProperty()
    var dowloadPath by dowloadPathProperty

    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    val userNameProperty = SimpleStringProperty()
    var userName by userNameProperty

    val hostProperty = SimpleStringProperty()
    var host by hostProperty

    fun sshUser(): SshUser {
        return SshUser(host, userName, password)
    }
}
