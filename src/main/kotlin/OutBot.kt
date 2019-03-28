import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import javax.security.auth.login.LoginException
import java.io.File
import kotlin.random.Random

private var generalChannel: TextChannel? = null

fun main() {

    try {
        val jda = JDABuilder("NTYwNTkxMzYwMTQ3NTg3MDkz.D32NRg.o_s98Sr4oL49GhzwTRgmM2kbK0c")
            .addEventListener(MessageListener())
            .build()
        jda.awaitReady()

        val channels = jda.textChannels

        if (channels != null) {
            for (channel in channels) {
                if (channel.name == "\uD83D\uDCACgeneral") {
                    println("General Channel ID: ${channel.id}")
                    generalChannel = channel
                }
            }
        }
    } catch (e: LoginException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}

class MessageListener: ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {

        if (event.message.contentDisplay == "@OutBot notify") {
            println("Notifying CSA that ${event.author.asMention} will not be attending this week.")

            val excuses = File("excuses").readLines()

            generalChannel?.sendMessage("We've got another drop out for the week! ${event.author.asMention} ${excuses[Random.nextInt(0, excuses.size)]}")!!.queue()
        }
    }
}