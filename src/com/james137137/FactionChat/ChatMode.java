package com.james137137.FactionChat;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatMode {

    
    protected static ArrayList<Boolean> spyon = new ArrayList<Boolean>();
    protected static ArrayList<String> chatModes = new ArrayList<String>();
    protected static ArrayList<String> playerNames = new ArrayList<String>();

    protected static void initialize() {
    }

    protected static int getPlayerID(Player player) {
        String playerName = player.getName();
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).equalsIgnoreCase(playerName)) {
                return i;
            }
        }



        return -1;
    }

    protected static boolean isSpyOn(Player player) {
        if (!player.hasPermission("FactionChat.spy") && !FactionChat.isDebugger(player.getName() ) ) {
            return false;
        }
        if (spyon.get(getPlayerID(player))) {
            return true;
        }
        return false;
    }

    protected static void changeSpyMode(Player player) {

        if (player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName())) {
            int playerID = getPlayerID(player);
            if (spyon.get(playerID)) {
                spyon.set(playerID, false);
                player.sendMessage(FactionChat.messageSpyModeOff);
            } else {
                spyon.set(playerID, true);
                player.sendMessage(FactionChat.messageSpyModeOn);
            }
        }

    }

    protected static String getChatMode(Player player) {
        String playerName = player.getName();
        int playerid = getPlayerID(player);
        if (playerid >= 0) {
            return chatModes.get(playerid);
        }



        Bukkit.getLogger().info("[FactionChat] player not found in ChatMode" + playerid + " = " + playerName);
        Bukkit.getLogger().info("Trying to create new Chatmode for player" + playerName);
        ChatMode.SetNewChatMode(player);
        playerid = getPlayerID(player);
        if (playerid >= 0) {
            return chatModes.get(playerid);
        }
        Bukkit.getLogger().info("[FactionChat] player not found in ChatMode and could not recreate " + playerid + " = " + playerName);

        return "ERROR";
    }

    protected static void SetNewChatMode(Player player) {
        int playerid = getPlayerID(player);


        if (playerid == -1) {
            playerNames.add(player.getName());
            chatModes.add("protected");

            if (FactionChat.spyModeOnByDefault) {
                spyon.add(true);
            } else {
                spyon.add(false);
            }
        } else {
            chatModes.set(playerid, "protected");
        }

    }

    protected static void NextChatMode(Player player) {
        int playerid = getPlayerID(player);
        if (chatModes.get(playerid).equalsIgnoreCase("protected")) {
            chatModes.set(playerid, "ALLY");
            if (FactionChat.AllyChatEnable)
            {
              player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AllyChat + chatModes.get(playerid));
              return;  
            }
            
        }
        if (chatModes.get(playerid).equalsIgnoreCase("ALLY")) {
            chatModes.set(playerid, "FACTION");
            if (FactionChat.FactionChatEnable)
            {
              player.sendMessage(FactionChat.messageNewChatMode + FactionChat.FactionChatColour + chatModes.get(playerid));
              return;  
            }
            
        }
        
        
        
            chatModes.set(playerid, "protected");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
        



    }

    protected static void setChatMode(Player player, String input) {
        int playerid = getPlayerID(player);





        if (input.equalsIgnoreCase("protected") || input.equalsIgnoreCase("P")) {
            chatModes.set(playerid, "protected");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
        } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))){
            if (!FactionChat.AllyChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "ALLY");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AllyChat + chatModes.get(playerid));
        } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F")))   {
            if (!FactionChat.FactionChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "FACTION");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.FactionChatColour + chatModes.get(playerid));

        } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))) {
            if (!FactionChat.EnemyChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "ENEMY");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.EnemyChat + chatModes.get(playerid));

        } else if ((player.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("UA")|| input.equalsIgnoreCase("UserAssistant"))) {
            if (!FactionChat.UAChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return; 
            }
            chatModes.set(playerid, "UserAssistant");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_PURPLE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD") ) {
            if (!FactionChat.JrModChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return; 
            }
            chatModes.set(playerid, "JrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.ModChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("MOD") ) {
            if (!FactionChat.ModChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return; 
            }
            chatModes.set(playerid, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.ModChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("SrMOD") ) {
            if (!FactionChat.SrModChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return; 
            }
            chatModes.set(playerid, "SrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.ModChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("JrADMIN") ) {
            if (!FactionChat.JrAdminChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return; 
            }
            chatModes.set(playerid, "JrADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AdminChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN") ) {
            if (!FactionChat.AdminChatEnable)
            {
                player.sendMessage(ChatColor.RED+"Sorry this chat mode is disabled");
                return; 
            }
            chatModes.set(playerid, "ADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AdminChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else {
            player.sendMessage(FactionChat.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, /fc e");
        }

    }
    
    protected static void setChatMode(Player player, String input, CommandSender sender) {
        int playerid = getPlayerID(player);





        if (input.equalsIgnoreCase("protected") || input.equalsIgnoreCase("P")) {
            chatModes.set(playerid, "protected");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
        } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))
                && FactionChat.AllyChatEnable){
            chatModes.set(playerid, "ALLY");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AllyChat + chatModes.get(playerid));
        } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F"))
                && FactionChat.FactionChatEnable)   {
            chatModes.set(playerid, "FACTION");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.FactionChatColour + chatModes.get(playerid));

        } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))
                && FactionChat.EnemyChatEnable) {
            chatModes.set(playerid, "ENEMY");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.EnemyChat + chatModes.get(playerid));

        } else if ((player.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("UA")|| input.equalsIgnoreCase("UserAssistant")) && FactionChat.UAChatEnable) {
            chatModes.set(playerid, "UserAssistant");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_PURPLE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD") && FactionChat.JrModChatEnable) {
            chatModes.set(playerid, "JrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.ModChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("MOD") && FactionChat.ModChatEnable) {
            chatModes.set(playerid, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.ModChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("SrMOD") && FactionChat.SrModChatEnable) {
            chatModes.set(playerid, "SrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.ModChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("JrADMIN") && FactionChat.JrAdminChatEnable) {
            chatModes.set(playerid, "JrADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AdminChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN") && FactionChat.AdminChatEnable) {
            chatModes.set(playerid, "ADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AdminChat + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
            && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else {
            sender.sendMessage("player doesn't have that permission or incorrect Chat mode name");
        }
    }

    protected static void RemovePlayer(Player player) {
        int playerid = getPlayerID(player);
        chatModes.remove(playerid);
        playerNames.remove(playerid);


    }

    protected static void fixPlayerNotInFaction(Player player) {

        int playerid = getPlayerID(player);


        if (!chatModes.get(playerid).equalsIgnoreCase("protected")) {
            chatModes.set(playerid, "protected");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
        }

    }
}