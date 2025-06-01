package dev.julian.smpplus.cmd;
import dev.julian.smpplus.EconomyManager;
import org.bukkit.ChatColor;import org.bukkit.command.*;import org.bukkit.entity.Player;
public class BalanceCmd implements CommandExecutor{
    private final EconomyManager eco;public BalanceCmd(EconomyManager e){eco=e;}
    @Override public boolean onCommand(CommandSender s,Command c,String l,String[] a){if(!(s instanceof Player p)){s.sendMessage("Nur im Spiel nutzbar.");return true;}double money=eco.get(p.getUniqueId());p.sendMessage(ChatColor.GOLD+"Dein Kontostand: "+String.format("%.0f",money));return true;}
}
