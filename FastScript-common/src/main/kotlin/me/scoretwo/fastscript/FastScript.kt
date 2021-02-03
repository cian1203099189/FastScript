package me.scoretwo.fastscript

import me.scoretwo.fastscript.api.expansion.ExpansionManager
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.language.LanguageManager
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.command.ScriptCommandNexus
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.api.script.ScriptManager
import me.scoretwo.fastscript.utils.Utils
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.syntaxes.StreamUtils
import net.md_5.bungee.api.ChatColor
import java.io.File

class FastScript(val plugin: ScriptPlugin) {

    val commandNexus: ScriptCommandNexus
    val scriptManager: ScriptManager
    val expansionManager: ExpansionManager

    fun setPlaceholder(player: GlobalPlayer, string: String) = plugin.setPlaceholder(player, string)

    init {
        instance = this
        me.scoretwo.fastscript.plugin = plugin

        printLogo()

        plugin.server.console.sendMessage(FormatHeader.INFO, "Initializing...")

        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }

        settings = SettingConfig()

        language = LanguageManager()
        language.current = language.languages[settings.getString("Options.Language")]!!

        commandNexus = ScriptCommandNexus()
        scriptManager = ScriptManager()
        expansionManager = ExpansionManager()
    }

    /**
    * 初始化内置脚本
    * 暂时弃坑
    */
    fun initInternalScripts() {

    }

    fun onReload() {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }
        plugin.reload()
        initInternalScripts()
        scriptManager.loadScripts()
    }

    /**
     * 用于随机点亮 FastScript 的 Logo.
     * 该创意来源于 TrMenu
     * @author Arasple
     */
    fun printLogo() = arrayOf(
       "___________                __   _________            .__        __   ",
       "\\_   _____/____    _______/  |_/   _____/ ___________|__|______/  |_ ",
       " |    __) \\__  \\  /  ___/\\   __\\_____  \\_/ ___\\_  __ \\  \\____ \\   __\\",
       " |     \\   / __ \\_\\___ \\  |  | /        \\  \\___|  | \\/  |  |_> >  |  ",
       " \\___  /  (____  /____  > |__|/_______  /\\___  >__|  |__|   __/|__|  ",
       "     \\/        \\/     \\/              \\/     \\/         |__|         "
   ).also {
        it.forEachIndexed { index, raw ->
            if (raw.isNotBlank()) {
                val line = raw.toCharArray()
                val width = (2..8).random()
                var randomIndex: Int
                do {
                    randomIndex = (2..line.size - width).random()
                } while (String(line.copyOfRange(randomIndex, randomIndex + width)).isBlank())
                val replace = String(line.copyOfRange(randomIndex, randomIndex + width))
                it[index] = String(line).replaceFirst(replace, "§${arrayOf('a', 'b', '2').random()}$replace§8")
            }
        }
        plugin.server.console.sendMessage(it)
    }

    companion object {
        lateinit var instance: FastScript

        fun setBootstrap(plugin: ScriptPlugin) {
            if (::instance.isInitialized) {
                throw UnsupportedOperationException("Cannot redefine instance")
            }
            FastScript(plugin)
        }

    }

}
lateinit var plugin: ScriptPlugin
val scripts = mutableListOf<Script>()

lateinit var settings: SettingConfig
lateinit var language: LanguageManager

fun GlobalSender.sendMessage(formatHeader: FormatHeader, strings: Array<String>, colorIndex: Boolean = true) {
    strings.forEach {
        this.sendMessage(formatHeader, it, colorIndex)
    }
}

fun GlobalSender.sendMessage(formatHeader: FormatHeader, string: String, colorIndex: Boolean = true) {
    if (colorIndex)
        this.sendMessage("${language["format-header.${formatHeader.name}"]}${string}")
    else
        this.sendMessage(
            ChatColor.translateAlternateColorCodes('&',"${"${language["format-header.${formatHeader.name}"]}${string}"}${string}"))
}

fun String.setPlaceholder(player: GlobalPlayer): String {
    return FastScript.instance.setPlaceholder(player, this)
}