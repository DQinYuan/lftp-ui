package com.github.lftpui

import com.github.lftpui.ssh.PrefixExtractor
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import net.sf.expectit.Expect
import net.sf.expectit.ExpectBuilder
import net.sf.expectit.matcher.Matchers.*
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


class SshTest {

    fun getExpect(): Expect {
        val sshc = SSHClient()
        sshc.addHostKeyVerifier(PromiscuousVerifier())
        sshc.connect("192.168.2.112")
        sshc.authPassword("dqyuan", "dqyuan")
        val session = sshc.startSession()
        session.allocateDefaultPTY()

        val shell = session.startShell()

        return ExpectBuilder()
                .withOutput(shell.outputStream)
                .withInputs(shell.inputStream)
                //.withEchoOutput(System.out)
                .withEchoInput(System.out)
                //.withInputFilters(removeColors(), removeNonPrintable())
                .withExceptionOnFailure()
                .build()
    }

    @Test
    fun testConnect() {
        val expect = getExpect()

        expect.sendLine("\n").expect()

        // try-with-resources is omitted for simplicity
        expect.sendLine("ls -lh")
        // capture the total
        val total: String = expect.expect(regexp(Pattern.compile("^total (.*)", Pattern.MULTILINE))).group(1)
        println("Size: $total")
        // capture file list
        val list: String = expect.expect(regexp("dqyuan@")).before
        // print the result
        println("List: $list")

/*        session.use {
            val cmd = it.exec("cd /home/avi")
            cmd.join()
            println(cmd.exitStatus) // 0 表示正常退出
            cmd.inputStream.copyTo(System.out)
            cmd.errorStream.copyTo(System.err)
        }

        val session2 = sshc.startSession()
        session2.use {
            val cmd2 = it.exec("ls")
            cmd2.join()
            println(cmd2.exitStatus)
            cmd2.inputStream.copyTo(System.out)
            cmd2.errorStream.copyTo(System.err)
        }*/


    }

    @Test
    fun testMultiExec() {
        val expect = getExpect()

        val cdOut = PrefixExtractor.exec("cd /home/avi", expect)
        val lsOut = PrefixExtractor.exec("ls -al", expect)

        println()
        println("============")
        println(cdOut)
        println("============")
        println(lsOut)
    }

    @Test
    fun testExtratPrefix() {
        val expect = getExpect()
        val prefix = PrefixExtractor.extract(expect)
        println("=================")
        println(prefix)
    }

    @Test
    fun testSshConns() {
        val process = Runtime.getRuntime().exec("/bin/sh")

        val expect = ExpectBuilder()
                .withInputs(process.inputStream)
                .withOutput(process.outputStream)
                .withTimeout(1, TimeUnit.SECONDS)
                .withExceptionOnFailure()
                .build()
        // try-with-resources is omitted for simplicity
        expect.sendLine("ls -lh")
        // capture the total
        val total = expect.expect(regexp("^总用量 (.*)")).group(1)
        println("Size: $total")
        // capture file list
        val list = expect.expect(regexp("\n$")).before
        // print the result
        println("List: $list")
        expect.sendLine("exit")
        // expect the process to finish
        expect.expect(eof())
        // finally is omitted
        process.waitFor()
        expect.close()
    }

}