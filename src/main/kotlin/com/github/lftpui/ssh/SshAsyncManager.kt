package com.github.lftpui.ssh

import net.schmizz.sshj.common.LoggerFactory
import net.schmizz.sshj.common.StreamCopier
import net.schmizz.sshj.connection.channel.direct.Session
import net.sf.expectit.Expect
import net.sf.expectit.ExpectBuilder
import java.io.ByteArrayOutputStream

class SshAsyncManager(val sshUser: SshUser) {

    private val sshClient = SshTools.sshClient(sshUser.host, sshUser.user, sshUser.password)
    private val session: Session = sshClient.startSession().apply {
        this.allocateDefaultPTY()
    }
    private val shell: Session.Shell = session.startShell()
    private val expect: Expect = ExpectBuilder().withExceptionOnFailure().build()
    private val prefix = PrefixExtractor.extract(expect)

    fun reusable(sshUser: SshUser): Boolean {
        return sshUser == this.sshUser
    }

    fun close() {
        sshClient.close()
    }

    /**
     * exec should be put in runAsync
     *
     * @return res message and err message
     */
    fun exec(cmd: String) {

    }

}