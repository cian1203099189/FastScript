package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.utils.sender.GlobalSender

/**
 * @author Scre2
 * @date 2021/2/8 13:33
 *
 * @project FastScript
 */
abstract class Script(
    val description: ScriptDescription,
    val option: ScriptOption,
    // sign, text
    var texts : MutableMap<String, String> = mutableMapOf()
) {

    val name = description.name

    fun bindExpansions() =
        mutableListOf<FastScriptExpansion>().also { expansions ->
            texts.keys.forEach {
                expansions.add(FastScript.instance.expansionManager.getExpansionBySign(it) ?: return@forEach)
            }
        }

    open fun eval(sign: String, sender: GlobalSender, vararg args: String): Any? =
        eval(FastScript.instance.expansionManager.getExpansionBySign(sign), sender, *args)

    fun eval(expansion: FastScriptExpansion?, sender: GlobalSender, vararg args: String): Any? =
        expansion?.eval(this, sender, arrayOf(*args))

    open fun execute(sign: String, sender: GlobalSender, main: String = option.main, args: Array<Any?> = arrayOf()): Any? =
        execute(FastScript.instance.expansionManager.getExpansionBySign(sign), sender, main, args)

    fun execute(expansion: FastScriptExpansion?, sender: GlobalSender, main: String = option.main, args: Array<Any?> = arrayOf()): Any? =
        expansion?.execute(this, sender, main, args)
}