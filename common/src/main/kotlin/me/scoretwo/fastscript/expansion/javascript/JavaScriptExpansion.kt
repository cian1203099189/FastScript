package me.scoretwo.fastscript.expansion.javascript

import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.script.AbstractScript
import me.scoretwo.fastscript.api.script.FileScript
import me.scoretwo.utils.sender.GlobalSender

class JavaScriptExpansion: FastScriptExpansion() {
    override val name: String = "JavaScript"
    override val sign: String = name.toLowerCase()

    val scripts = mutableListOf<JavaScript>()

    override fun processScripts(scripts: MutableList<FileScript>) = scripts.forEachIndexed { i, fileScript ->

    }

    override fun executeScript(sender: GlobalSender, script: FileScript, function: String, args: Array<Any?>) {
        TODO("Not yet implemented")
    }
}