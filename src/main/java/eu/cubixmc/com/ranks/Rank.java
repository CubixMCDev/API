package eu.cubixmc.com.ranks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.List;

@Getter
@Setter
public class Rank {

    private String id;
    private String prefixTab;
    private String prefixAboveHead;
    private ChatColor colorChat;
    private List<String> permissions;
    private List<String> inherits;

    public Rank(String id, String prefixTab, String prefixAboveHead, ChatColor colorChat, List<String> perms) {
        this.id = id;
        this.prefixTab = prefixTab;
        this.prefixAboveHead = prefixAboveHead;
        this.colorChat = colorChat;
        this.permissions = perms;
    }

}
