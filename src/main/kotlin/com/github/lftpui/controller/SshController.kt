package com.github.lftpui.controller

import com.github.lftpui.model.SshInfo
import com.github.lftpui.ssh.SshConns
import tornadofx.*

class SshController : Controller() {

    var sshConns: SshConns? = null

    fun testValid(sshInfo: SshInfo): Boolean {
        val curSshConns = sshConns
        if (curSshConns == null || !curSshConns.reusable(sshInfo.sshUser())) {
            curSshConns?.close()
            sshConns = SshConns(sshInfo.sshUser())
        }

        return testValid(sshConns!!, sshInfo)
    }

    fun testValid(sshConns: SshConns, sshInfo: SshInfo): Boolean {
        val (status, _) = sshConns.exec("cd ${sshInfo.dowloadPath}")

        return status == 0
    }

}