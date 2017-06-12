package momo.report;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class BotResponder {
	
	public BotResponder() {
	}
	
	public void sendChannelMessage(Guild guild, long channelID, String message) {
		if(guild != null && message != null) {
			
			TextChannel tChannel= guild.getTextChannelById(channelID);
			
			tChannel.sendMessage(message).queue();
		}
		
		return;
	}
	
	public void sendChannelMessage(Guild guild, TextChannel channel, String message) {
		if(guild != null && channel != null && message != null) {	
			channel.sendMessage(message).queue();
		}
		
		return;
	}
	
}
