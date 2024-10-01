package uz.pdp;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

public class BotService {
    static TelegramBot telegramBot = new TelegramBot("7517319983:AAGUim3jLh4k2SXefN0aSHjmZ4yd7h09T6g");

    public static TgUser generateCreatUser(Long chatId) {
        for (TgUser tgUser : DB.TG_USERS) {
            if (tgUser.getChatId().equals(chatId)) {
                return tgUser;
            }
        }
        TgUser tgUser = new TgUser();
        tgUser.setChatId(chatId);
        DB.TG_USERS.add(tgUser);
        return tgUser;
    }


    public static void acceptStartSendWelcome(TgUser tgUser, Message message) {
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), "Assalom aleykum :" + "\nUserName: @" + message.chat().username() + " bo'lgan " + message.chat().firstName() + "jon Botimizda ko'rib turganimizdan xursandmiz😉");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        SendMessage sendMessage1 = new SendMessage(tgUser.getChatId(), "Userlardan birini tanlang :");

        for (User user : DB.USERS) {
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(user.getName()).callbackData(user.getId().toString()),
                    new InlineKeyboardButton("Posts").callbackData(user.getId()+"")
            );
        }
        sendMessage1.replyMarkup(inlineKeyboardMarkup);
        telegramBot.execute(sendMessage);
        telegramBot.execute(sendMessage1);
        tgUser.setState(State.POST);

    }

    public static void acceptUserAskPost(TgUser tgUser, String data) {
        for (User user : DB.USERS) {
            if (user.getId().toString().equals(data)) {
                tgUser.setSelectedUser(user);
            }
        }
        User selecteduser = tgUser.getSelectedUser();
        if (selecteduser != null) {

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            for (Post post : DB.POSTS) {
                if (post.getUserId().equals(selecteduser.getId())) {
                    inlineKeyboardMarkup.addRow(new InlineKeyboardButton(post.getTitle()).callbackData("comment"),
                            new InlineKeyboardButton("COMMENT").callbackData(post.getId()+"")
                    );
                }
            }
            SendMessage sendMessage = new SendMessage(tgUser.getChatId(), "Comentlardan birini tanlang: "+selecteduser.getName());
            sendMessage.replyMarkup(inlineKeyboardMarkup);
            telegramBot.execute(sendMessage);
            tgUser.setState(State.COMMENT);

        }
    }

    public static void acceptPostAskComment(TgUser tgUser, String data) {
        for (Post post : DB.POSTS) {
            if (post.getId().toString().equals(data)) {
                tgUser.setSelectedPost(post);
            }
        }
        Post selectedPost = tgUser.getSelectedPost();
        if (selectedPost!=null) {
            SendMessage sendMessage=new SendMessage(tgUser.getChatId(),"tanlagan commentingiz: "+selectedPost.getTitle());
            telegramBot.execute(sendMessage);
        }
    }
}
