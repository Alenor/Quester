package com.gmail.molnardad.quester.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.molnardad.quester.Quester;
import com.gmail.molnardad.quester.commandbase.QCommand;
import com.gmail.molnardad.quester.commandbase.QCommandContext;
import com.gmail.molnardad.quester.commandbase.QCommandLabels;
import com.gmail.molnardad.quester.exceptions.QuesterException;
import com.gmail.molnardad.quester.profiles.ProfileManager;
import com.gmail.molnardad.quester.quests.QuestFlag;
import com.gmail.molnardad.quester.quests.QuestManager;

public class ModifierCommands {
	
	final QuestManager qMan;
	final ProfileManager profMan;
	
	public ModifierCommands(final Quester plugin) {
		qMan = plugin.getQuestManager();
		profMan = plugin.getProfileManager();
	}
	
	private QuestFlag[] getModifiers(final String[] args) {
		final Set<QuestFlag> modifiers = new HashSet<QuestFlag>();
		
		for(int i = 0; i < args.length; i++) {
			final QuestFlag flag = QuestFlag.getByName(args[i]);
			if(flag != null && flag != QuestFlag.ACTIVE) {
				modifiers.add(flag);
			}
		}
		
		return modifiers.toArray(new QuestFlag[0]);
	}
	
	@QCommandLabels({ "add", "a" })
	@QCommand(section = "QMod", desc = "adds quest modifier", min = 1, usage = "<modifier1> ...")
	public void add(final QCommandContext context, final CommandSender sender) throws QuesterException {
		final QuestFlag[] modArray = getModifiers(context.getArgs());
		if(modArray.length < 1) {
			sender.sendMessage(ChatColor.RED + context.getSenderLang().get("ERROR_MOD_UNKNOWN"));
			sender.sendMessage(ChatColor.RED + context.getSenderLang().get("USAGE_MOD_AVAIL")
					+ ChatColor.WHITE + QuestFlag.stringize(QuestFlag.values()));
			return;
		}
		qMan.addQuestFlag(profMan.getProfile(sender.getName()), modArray, context.getSenderLang());
		sender.sendMessage(ChatColor.GREEN + context.getSenderLang().get("Q_MOD_ADDED"));
	}
	
	@QCommandLabels({ "remove", "r" })
	@QCommand(section = "QMod", desc = "removes quest modifier", min = 1, usage = "<modifier1> ...")
	public void set(final QCommandContext context, final CommandSender sender) throws QuesterException {
		final QuestFlag[] modArray = getModifiers(context.getArgs());
		qMan.removeQuestFlag(profMan.getProfile(sender.getName()), modArray,
				context.getSenderLang());
		sender.sendMessage(ChatColor.GREEN + context.getSenderLang().get("Q_MOD_REMOVED"));
	}
}
