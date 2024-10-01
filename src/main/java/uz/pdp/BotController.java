package uz.pdp;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static uz.pdp.BotService.*;

public class BotController {
    ExecutorService executorService = Executors.newScheduledThreadPool(10);

    public void start() {
        BotService.telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                executorService.execute(() -> {
                    try {
                        handleUpdates(update);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void handleUpdates(Update update) {
        Message message = update.message();
        if (update.message()!=null){
            TgUser tgUser = generateCreatUser(message.chat().id());
            if (message.text()!=null) {
                if (message.text().equals("/start")) {
                    BotService.acceptStartSendWelcome(tgUser,message);
                }
            }
        } else if (update.callbackQuery() != null) {
            CallbackQuery callbackQuery = update.callbackQuery();
            TgUser tgUser = generateCreatUser(callbackQuery.from().id());
            String data = callbackQuery.data();
            Integer messageId = callbackQuery.message().messageId();
            if (tgUser.getState().equals(State.POST)){
                acceptUserAskPost(tgUser,data);
            } else if (tgUser.getState().equals(State.COMMENT)) {
                BotService.acceptPostAskComment(tgUser,data);
            }
        }
    }
}
