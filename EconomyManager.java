package dev.julian.smpplus;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;import java.io.IOException;import java.util.*;
import java.util.stream.Collectors;
public class EconomyManager{
    private final SMPPlus plugin;
    private final Map<java.util.UUID, Double> balances=new HashMap<>();
    private final File file;
    EconomyManager(SMPPlus plugin){
        this.plugin=plugin;
        this.file=new File(plugin.getDataFolder(),"balances.yml");
        load();
    }
    public double get(java.util.UUID id){return balances.getOrDefault(id,0.0);}
    public void set(java.util.UUID id,double amount){balances.put(id,Math.max(0,amount));}
    public void add(java.util.UUID id,double amount){set(id,get(id)+amount);}
    public void remove(java.util.UUID id,double amt){set(id,get(id)-amt);}
    public java.util.List<Map.Entry<java.util.UUID,Double>> top(int l){
        return balances.entrySet().stream().sorted(Map.Entry.<java.util.UUID,Double>comparingByValue().reversed()).limit(l).collect(Collectors.toList());
    }
    private void load(){
        if(!file.exists())return;
        YamlConfiguration cfg=YamlConfiguration.loadConfiguration(file);
        for(String k:cfg.getKeys(false))balances.put(java.util.UUID.fromString(k),cfg.getDouble(k));
    }
    public void save(){
        YamlConfiguration cfg=new YamlConfiguration();
        balances.forEach((id,m)->cfg.set(id.toString(),m));
        try{cfg.save(file);}catch(IOException e){e.printStackTrace();}
    }
}
