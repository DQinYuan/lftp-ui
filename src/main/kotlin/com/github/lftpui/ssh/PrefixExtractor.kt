package com.github.lftpui.ssh

import net.sf.expectit.Expect
import net.sf.expectit.matcher.Matchers.contains
import net.sf.expectit.matcher.Matchers.regexp
import java.util.*
import java.util.regex.Pattern

object PrefixExtractor {

    private val uuid = UUID.randomUUID().toString()
    private val echoUuid = "echo $uuid"


    fun extract(expect: Expect): String {
        return expect.sendLine(echoUuid)
                .expect(regexp(Pattern.compile("(.*) echo $uuid")))
                .group(1)
    }

    fun exec(cmd: String, expect: Expect): String {
        expect.sendLine(cmd)
                .sendLine(echoUuid)
        expect.expect(regexp("(.*) $cmd"))
        return expect.expect(regexp("(.*) $echoUuid")).before
    }

}