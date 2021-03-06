package com.gmail.molnardad.quester.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.molnardad.quester.Quester;
import com.gmail.molnardad.quester.commandbase.QCommand;
import com.gmail.molnardad.quester.commandbase.QCommandContext;
import com.gmail.molnardad.quester.commandbase.QCommandLabels;
import com.gmail.molnardad.quester.commandbase.exceptions.QCommandException;
import com.gmail.molnardad.quester.exceptions.QuesterException;
import com.gmail.molnardad.quester.profiles.ProfileManager;
import com.gmail.molnardad.quester.quests.QuestManager;
import com.gmail.molnardad.quester.utils.SerUtils;

public class LocationCommands {
	
	final QuestManager qMan;
	final ProfileManager profMan;
	
	public LocationCommands(final Quester plugin) {
		qMan = plugin.getQuestManager();
		profMan = plugin.getProfileManager();
	}
	
	@QCommandLabels({ "set", "s" })
	@QCommand(
			section = "QMod",
			desc = "sets quest location",
			min = 2,
			max = 2,
			usage = "{<location>} <range>")
	public void set(final QCommandContext context, final CommandSender sender) throws QuesterException, QCommandException {
		try {
			final int range = context.getInt(1);
			if(range < 1) {
				throw new NumberFormatException();
			}
			qMan.setQuestLocation(profMan.getProfile(sender.getName()),
					SerUtils.getLoc(sender, context.getString(0)), range, context.getSenderLang());
			sender.sendMessage(ChatColor.GREEN + context.getSenderLang().get("Q_LOC_SET"));
		}
		catch (final NumberFormatException e) {
			throw new QCommandException(context.getSenderLang().get("ERROR_CMD_RANGE_INVALID"));
		}
		catch (final IllegalArgumentException e) {
			throw new QCommandException(e.getMessage());
		}
	}
	
	@QCommandLabels({ "remove", "r" })
	@QCommand(section = "QMod", desc = "removes quest location", max = 0)
	public void remove(final QCommandContext context, final CommandSender sender) throws QuesterException {
		qMan.removeQuestLocation(profMan.getProfile(sender.getName()), context.getSenderLang());
		sender.sendMessage(ChatColor.GREEN + context.getSenderLang().get("Q_LOC_REMOVED"));
	}
}
