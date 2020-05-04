package com.github.lftpui.ssh

import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier

object SshTools {

    fun sshClient(host: String, user: String, password: String): SSHClient {
        val sshc = SSHClient()
        sshc.addHostKeyVerifier(PromiscuousVerifier())
        sshc.connect(host)
        sshc.authPassword(user, password)
        return sshc
    }

}