package eu.cubixmc.com.ranks;

import eu.cubixmc.com.CubixAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.List;

@Getter
@Setter
public class Rank {

    private String id;
    private ChatColor colorChat;
    private List<String> permissions;
    private List<String> inherits;

    public Rank(String id, ChatColor colorChat, List<String> perms) {
        this.id = id;
        this.colorChat = colorChat;
        this.permissions = perms;
    }

    public String getRankToStringWithColor() {
        return ChatColor.valueOf(CubixAPI.getInstance().getConfig().getString("ranks." + id + ".rankColor")) + "" + CubixAPI.getInstance().getConfig().getString("ranks." + id + ".rankToString");
    }
}
