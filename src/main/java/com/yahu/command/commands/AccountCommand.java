package com.yahu.command.commands;

import com.yahu.account.AccountManager;
import com.yahu.account.Account;
import net.minecraft.network.chat.Component;

public class AccountCommand {
    public static int add(String type, String data) {
        AccountManager.getInstance().addAccount(type, data);
        sendMessage("Added account: " + type);
        return 1;
    }

    public static int remove(String name) {
        AccountManager.getInstance().removeAccount(name);
        sendMessage("Removed account: " + name);
        return 1;
    }

    public static int switchAccount(String name) {
        Account account = AccountManager.getInstance().getAccount(name);
        if (account == null) {
            sendMessage("Account not found: " + name);
            return 0;
        }
        AccountManager.getInstance().switchAccount(account);
        sendMessage("Switched to account: " + name);
        return 1;
    }

    public static int list() {
        sendMessage("Accounts:");
        for (Account acc : AccountManager.getInstance().getAccounts()) {
            sendMessage("  - " + acc.getUsername() + " (" + acc.getType() + ")" + (acc.isActive() ? " §a[ACTIVE]§r" : ""));
        }
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}