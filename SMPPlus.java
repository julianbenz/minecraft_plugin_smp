package dev.julian.smpplus;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
public final class SMPPlus extends JavaPlugin {
    private static SMPPlus instance;
    private EconomyManager economy;
    private GroupManager groups;
    private ScoreboardHandler board;
    public static SMPPlus get() { return instance; }
    @Override public void onEnable() {
        instance = this;
        saveDefaultConfig();
        economy = new EconomyManager(this);
        groups  = new GroupManager(this);
        board   = new ScoreboardHandler(this, getConfig().getString("server-name", "Julian SMP"));
        getCommand("balance").setExecutor(new cmd.BalanceCmd(economy));
        getCommand("pay").setExecutor(new cmd.PayCmd(economy));
        getCommand("eco").setExecutor(new cmd.EcoCmd(economy));
        getCommand("sellhand").setExecutor(new cmd.SellCmd(economy, getConfig().getConfigurationSection("prices")));
        getCommand("creategroup").setExecutor(new cmd.CreateGroupCmd(groups, economy, getConfig()));
        getCommand("joingroup").setExecutor(new cmd.JoinGroupCmd(groups));
        getCommand("leavegroup").setExecutor(new cmd.LeaveGroupCmd(groups));
        Bukkit.getPluginManager().registerEvents(new PlayerTagListener(groups), this);
        board.start();
    }
    @Override public void onDisable() {
        economy.save();
        groups.save();
        board.stop();
    }
}
