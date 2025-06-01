package dev.julian.smpplus;
import org.bukkit.ChatColor;import org.bukkit.event.*;import org.bukkit.event.player.AsyncPlayerChatEvent;
public class PlayerTagListener implements Listener{
    private final GroupManager groups;public PlayerTagListener(GroupManager g){groups=g;}
    @EventHandler public void onChat(AsyncPlayerChatEvent e){String grp=groups.ofPlayer(e.getPlayer().getUniqueId());String prefix=grp!=null?ChatColor.GREEN+"["+grp+"] ":"";e.setFormat(prefix+ChatColor.WHITE+e.getPlayer().getName()+ChatColor.GRAY+": "+ChatColor.RESET+"%2$s");}
}
