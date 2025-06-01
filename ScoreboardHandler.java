package dev.julian.smpplus;
import org.bukkit.Bukkit;import org.bukkit.ChatColor;import org.bukkit.entity.Player;import org.bukkit.scoreboard.*;
import java.util.*;import java.util.concurrent.atomic.AtomicInteger;
public class ScoreboardHandler{
    private final SMPPlus plugin;private final Scoreboard board=Bukkit.getScoreboardManager().getNewScoreboard();private final Objective obj=board.registerNewObjective("smpplus","dummy","SMPPlus");private final AtomicInteger tick=new AtomicInteger();private final List<String> frames=new ArrayList<>();private int task;
    public ScoreboardHandler(SMPPlus p,String name){plugin=p;obj.setDisplaySlot(DisplaySlot.SIDEBAR);String pad="     ";String sc=pad+name+pad;for(int i=0;i<sc.length()-5;i++)frames.add(ChatColor.AQUA+sc.substring(i,i+6));}
    public void start(){task=Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,()->{obj.setDisplayName(frames.get(tick.getAndIncrement()%frames.size()));board.getEntries().forEach(board::resetScores);int score=10;for(var e:plugin.economy().top(10)){Player pl=Bukkit.getPlayer(e.getKey());String n=(pl!=null)?pl.getName():"Offline";obj.getScore(ChatColor.YELLOW+n+ChatColor.GRAY+": "+ChatColor.GOLD+String.format("%.0f",e.getValue())).setScore(score--);}for(Player pl:Bukkit.getOnlinePlayers())pl.setScoreboard(board);},0L,20L);}
    public void stop(){if(task!=0)Bukkit.getScheduler().cancelTask(task);}
}
