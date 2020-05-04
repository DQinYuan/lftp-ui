package com.github.lftpui.ssh

import net.schmizz.sshj.connection.channel.direct.Session
import java.io.ByteArrayOutputStream

class SshConns(val sshUser: SshUser) {

    private val sshClient = SshTools.sshClient(sshUser.host, sshUser.user, sshUser.password)
    private val session: Session by lazy { sshClient.startSession() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SshConns) return false

        if (sshUser != other.sshUser) return false

        return true
    }

    override fun hashCode(): Int {
        return sshUser.hashCode()
    }

    fun reusable(sshUser: SshUser): Boolean {
        return sshUser == this.sshUser
    }

    fun exec(cmdStr: String): Pair<Int, String> {
        val cmd = session.exec(cmdStr)
        val resStream = ByteArrayOutputStream()
        cmd.inputStream.copyTo(resStream)
        cmd.errorStream.copyTo(System.err)
        //cmd.join()
        cmd.close()
        return Pair(cmd.exitStatus, String(resStream.toByteArray()))
    }

    fun close() {
        sshClient.close()
    }

}