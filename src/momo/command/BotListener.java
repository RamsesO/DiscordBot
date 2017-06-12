package momo.command;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import momo.main.Config;
import momo.report.BotResponder;

public class BotListener extends ListenerAdapter{
	
	Hashtable<String, ArrayList<String>> responseTable;
	public static Role defaultRole;
	public static BotResponder botResponse;
	private static Random numGen;
	private static Pattern checkRegex;
	private static Matcher regexMatcher;
	
	public BotListener() {
		super();
		botResponse = new BotResponder();
		responseTable = new Hashtable<>();
		numGen = new Random();

		populateResponseTable();
	}
	

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		Member member = event.getMember();
		User user = event.getMember().getUser();
		
		if(user.isBot())
			return;
		
		TextChannel textChannel = event.getTextChannel();
		Guild guild = event.getGuild();
		
		String messRecvd = event.getMessage().getContent().trim().toLowerCase();	
		String[] messArr = messRecvd.split(" ");
		
		if(messRecvd.equals("fuck you")) {
			String response = event.getAuthor().getAsMention() + " fuck you too";
			String botMessage = "fuck you sent to '" + user.getName() + "'";
			
			botResponse.sendChannelMessage(guild, textChannel, response);
			botResponse.sendChannelMessage(guild, Config.botLogID, botMessage);
			//event.getChannel().sendMessage( + response).queue();
		}
		
		
		if(messRecvd.startsWith("fuck you") && messArr.length == 3) {
			String response = "yeah fuck you " + messArr[2];
			String botMessage = "fuck you sent to '" + messArr[2] + "'";
			
			botResponse.sendChannelMessage(guild, textChannel, response);
			botResponse.sendChannelMessage(guild, Config.botLogID, botMessage);
			//event.getChannel().sendMessage(response).queue();
		}
		
		if(messRecvd.equals("!shutdown") && member.isOwner()) {
			String response = "okay T___T shutting Down...";
			String botMessage = "Shut Down.";
			
			botResponse.sendChannelMessage(guild, Config.botLogID, botMessage);
			botResponse.sendChannelMessage(guild, textChannel, response);
			
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
			System.exit(-1);
			}
		}
		
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Member member = event.getMember();
		Guild guild = event.getGuild();
		
		if(defaultRole == null) {
			List<Role> roleList = guild.getRoles();
			for(Role r: roleList) {
				if(r.getName().equals(Config.defaultRole)) {
					defaultRole = r;
				}
			}
		}
		
		if(defaultRole != null) {
			guild.getController().addRolesToMember(member, defaultRole).complete();
		}
		
		String botMessage = event.getMember().getAsMention() + " has been moved to '" 
				+ Config.defaultRole + "'";
		
		
		botResponse.sendChannelMessage(guild, Config.botLogID, botMessage);
	}
	
	private void populateResponseTable() {
		String csvFile = "./resources/responses.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] messResponse = line.split(cvsSplitBy);
                if(messResponse.length < 1 || messResponse.length > 2)
                	continue;
                String message = messResponse[0].toLowerCase();
                String response = messResponse[1].toLowerCase();
                
                if(!responseTable.contains(message)) {
                	responseTable.put(message, response);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

	

}
