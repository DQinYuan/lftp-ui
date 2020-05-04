package com.github.lftpui.view

import com.github.lftpui.app.Styles
import com.github.lftpui.controller.SshController
import com.github.lftpui.model.FtpFile
import com.github.lftpui.model.Job
import com.github.lftpui.model.SshInfo
import javafx.scene.control.Alert
import javafx.scene.control.ListCell
import javafx.scene.image.Image
import tornadofx.*

class MainView : View("lftp ui") {

    val sshController: SshController by inject()

    val sshInfo = SshInfo()


    override val root = borderpane {
        top {
            hbox {
                spacing = 10.0
                label("SSH 主机:")
                textfield(sshInfo.hostProperty)
                label("用户名:")
                textfield(sshInfo.userNameProperty)
                label("密码:")
                passwordfield(sshInfo.passwordProperty)
                label("下载路径:")
                textfield(sshInfo.dowloadPathProperty)

                button("测试") {
                    action {
                        val header = "ssh 测试状态"
                        if (sshController.testValid(sshInfo)) {
                            alert(
                                    type = Alert.AlertType.INFORMATION,
                                    header = header,
                                    content = "状态异常"
                            )
                        } else {
                            alert(
                                    type = Alert.AlertType.ERROR,
                                    header = header,
                                    content = "状态正常"
                            )
                        }
                    }
                }
            }
        }
        center {
            vbox {
                spacing = 10.0
                label("Tasks:") {
                    addClass(Styles.subject)
                }
                tableview<Job> {
                    val idSize = 30.0
                    column("ID", Job::idProperty) {
                        isResizable = false
                        prefWidth = idSize
                    }
                    column("State", Job::descriptionProperty) {
                        isResizable = false
                        prefWidthProperty().bind(tableView.widthProperty().subtract(idSize))
                    }
                    //columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                    items.add(Job(0, "20%"))
                }
            }
        }
        bottom {
            vbox {
                spacing = 10.0
                paddingAll = 50.0
                hbox {
                    spacing = 10.0
                    label("FTP 服务器:")
                    textfield { }
                    label("用户名:")
                    textfield { }
                    label("密码:")
                    textfield { }
                    button("连接(刷新目录结构)") { }
                }

                listview<FtpFile> {
                    fixedCellSize = 40.0
                    setCellFactory {
                        object : ListCell<FtpFile>() {
                            override fun updateItem(item: FtpFile?, empty: Boolean) {
                                super.updateItem(item, empty)
                                if (!empty) {
                                    val ftpFile = item!!
                                    graphic = imageview {
                                        image = Image(
                                                when (ftpFile.isDir) {
                                                    true -> MainView::class.java
                                                            .getResource("/imgs/dir.png").toString()
                                                    false -> MainView::class.java
                                                            .getResource("/imgs/file.png").toString()
                                                }
                                        ).apply {
                                            scaleX = 0.1
                                            scaleY = 0.1
                                        }
                                    }
                                    text = ftpFile.path.last()
                                } else {
                                    graphic = null
                                    text = null
                                }
                            }
                        }
                    }
                    items.addAll(FtpFile(true, listOf("..")),
                            FtpFile(true, listOf(".")),
                            FtpFile(false, listOf("aaaa.txt"))
                    )
                }
            }
        }
    }
}