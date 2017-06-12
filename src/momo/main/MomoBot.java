package momo.main;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import momo.command.BotListener;
import momo.report.BotResponder;

public class MomoBot {
	public static JDA jda;
	
	public static void main(String[] args) {
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(Config.appToken).buildBlocking();
		}
		catch (LoginException  | IllegalArgumentException  | InterruptedException | RateLimitedException e){
			e.printStackTrace();
		}
		

		jda.addEventListener(new BotListener());
		
		String message = "MomoBot Online!";
		String botMessage = "Bot Online.";
		Guild guild = jda.getGuildById(Config.mainGuildID);
		BotResponder greeting = new BotResponder();
		
//		greeting.sendChannelMessage(guild, Config.mainChatID, message);
//		greeting.sendChannelMessage(guild, Config.botLogID, botMessage);

	}

}

