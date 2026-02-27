package org.k0rv1nes.skyMinePCrash.discord;

import org.bukkit.entity.Player;
import org.k0rv1nes.skyMinePCrash.SkyMinePCrash;
import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

public class SendDiscord {

    public void sendWebHookKick(Player playerSender, Player target, String method) {

        String tokenWebhook = SkyMinePCrash.getInstance().getConfig().getString("webhookURL");
        String playerSenderName = playerSender.getName();
        String playerName = target.getName();
        String message = SkyMinePCrash.getInstance().getConfig().getString("webhoolText")
                .replace("%playerSenderName%", playerSenderName)
                .replace("%targetName%", playerName)
                .replace("%method%", method);

        String jsonBrut = "";
        jsonBrut += "{"
                + "\"content\": \"" + message + "\""
                + "}";
        try {
            URL url = new URL(tokenWebhook);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.addRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStream stream = con.getOutputStream();
            stream.write(jsonBrut.getBytes());
            stream.flush();
            stream.close();
            con.getInputStream().close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendWebHookGiveStick(Player player, String uniqueID) {

        String tokenWebhook = SkyMinePCrash.getInstance().getConfig().getString("webhookURL");
        String playerSenderName = player.getName();
        String message = SkyMinePCrash.getInstance().getConfig().getString("webhookGiveStick")
                .replace("%player%", playerSenderName)
                .replace("%uniqueID", uniqueID);

        String jsonBrut = "";
        jsonBrut += "{"
                + "\"content\": \"" + message + "\""
                + "}";
        try {
            URL url = new URL(tokenWebhook);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.addRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStream stream = con.getOutputStream();
            stream.write(jsonBrut.getBytes());
            stream.flush();
            stream.close();
            con.getInputStream().close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
