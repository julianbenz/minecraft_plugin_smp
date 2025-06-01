package dev.julian.smpplus;
import org.bukkit.configuration.file.YamlConfiguration;import org.bukkit.entity.Player;
import java.io.File;import java.io.IOException;import java.util.*;
public class GroupManager{
    public static class Group{public final String name;public final java.util.UUID owner;public final Set<java.util.UUID> members=new HashSet<>();Group(String n,java.util.UUID o){name=n;owner=o;members.add(o);} }
    private final SMPPlus plugin;private final Map<String,Group> groups=new HashMap<>();private final Map<java.util.UUID,String> pg=new HashMap<>();private final File file;
    GroupManager(SMPPlus plugin){this.plugin=plugin;file=new File(plugin.getDataFolder(),"groups.yml");load();}
    public boolean exists(String n){return groups.containsKey(n.toLowerCase());}
    public Group get(String n){return groups.get(n.toLowerCase());}
    public String ofPlayer(java.util.UUID id){return pg.get(id);}
    public boolean create(Player owner,String name,double cost){
        if(exists(name))return false;
        if(plugin.economy().get(owner.getUniqueId())<cost)return false;
        plugin.economy().remove(owner.getUniqueId(),cost);
        Group g=new Group(name,owner.getUniqueId());
        groups.put(name.toLowerCase(),g);pg.put(owner.getUniqueId(),g.name);return true;
    }
    public boolean join(Player p,String n){Group g=get(n);if(g==null)return false;g.members.add(p.getUniqueId());pg.put(p.getUniqueId(),g.name);return true;}
    public void leave(Player p){String gname=ofPlayer(p.getUniqueId());if(gname==null)return;Group g=get(gname);g.members.remove(p.getUniqueId());pg.remove(p.getUniqueId());if(g.members.isEmpty())groups.remove(g.name.toLowerCase());}
    private void load(){if(!file.exists())return;YamlConfiguration cfg=YamlConfiguration.loadConfiguration(file);for(String gName:cfg.getKeys(false)){java.util.UUID owner=java.util.UUID.fromString(cfg.getString(gName+".owner"));Group g=new Group(gName,owner);cfg.getStringList(gName+".members").forEach(id->g.members.add(java.util.UUID.fromString(id)));groups.put(gName.toLowerCase(),g);g.members.forEach(id->pg.put(id,gName));}}
    public void save(){YamlConfiguration cfg=new YamlConfiguration();groups.values().forEach(g->{cfg.set(g.name+".owner",g.owner.toString());java.util.List<String> ids=new java.util.ArrayList<>();g.members.forEach(id->ids.add(id.toString()));cfg.set(g.name+".members",ids);});try{cfg.save(file);}catch(IOException e){e.printStackTrace();}}
}
