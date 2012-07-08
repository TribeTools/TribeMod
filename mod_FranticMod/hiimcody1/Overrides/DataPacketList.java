package hiimcody1.Overrides;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet3Chat;
import hiimcody1.FranticMod;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
* Overrides the dataPacket list in NetworkManager
* Uses reflection to replace the list with this. It overrides the add()
* method to check if it's a Packet3Chat event.
*
* @author yetanotherx
*
*/
public class DataPacketList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 275687258276L;
    protected FranticMod controller;
    protected Class<T> typeClass;

    public DataPacketList(FranticMod controller, Class<T> typeClass) {
        this.controller = controller;
        this.typeClass = typeClass;
    }

    /**
* Overrides the packet addition class. If a Packet3Chat is added, there's an outgoing
* message and we need to parse it. If it's a command, send a command event. If it's
* cancelled, let's not add it at all.
*
* @param packet
* @return
*/
    public boolean add(T packet) {
        if (packet instanceof Packet3Chat) {
            
            boolean cancelled = false;
            String s = ((Packet3Chat) packet).message;
            cancelled = controller.processOutgoingMessage(s);
            //ChatEvent chatevent = new ChatEvent(controller, s, ChatEvent.Direction.OUTGOING);
            //controller.getEventManager().callEvent(chatevent);
            //if (!chatevent.isCancelled() && s.startsWith("/") && s.length() > 1) {
            //    ChatCommandEvent commandevent = new ChatCommandEvent(controller, s);
            //    controller.getEventManager().callEvent(commandevent);
            //    if (commandevent.isHandled() || commandevent.isCancelled()) {
            //        cancelled = true;
            //    }
            //}

            if (!cancelled) {
                return super.add(packet);
            }
            return true;
        }
        return super.add(packet);
    }

    /**
* Attaches the new packet handler to the actual NetworkManager class
*
* @param controller
*/
    public static void register(FranticMod controller) {

        DataPacketList<Packet> list = new DataPacketList<Packet>(controller, Packet.class);
        Minecraft obf = ModLoader.getMinecraftInstance();

        //Checks if it's a multiplayer world
        if (!obf.isMultiplayerWorld()) {
            return;
        }

        EntityClientPlayerMP player = (EntityClientPlayerMP) obf.thePlayer;

        try {
            NetClientHandler nch = player.sendQueue;
            
            Field nmFielda[] = NetClientHandler.class.getDeclaredFields();
            
//            for(int i=0; i < nmFielda.length; i++) {
//            	System.out.println("Field Number: "+i+ "; Real name:"  +nmFielda[i].getName());
//            }
            
            Field nmField = nmFielda[1];
            nmField.setAccessible(true);
            NetworkManager nm = (NetworkManager) nmField.get(nch);
            
            Field listFielda[] = NetworkManager.class.getDeclaredFields();
            
//            for(int i=0; i < listFielda.length; i++) {
//            	System.out.println("Field Number: "+i+ "; Real name:"  +listFielda[i].getName());
//            }
            
            Field listField = listFielda[10];
            listField.setAccessible(true);
            List oldPacketList = (List) listField.get(nm);
            for (Object item : oldPacketList) {
                list.add((Packet) item);
            }

            listField.set(nm, list);
            nmField.set(nch, nm);
            
        } catch (Exception e) {
            throw new RuntimeException("Error inserting outgoing chat handler - Certain parts of WorldEditCUI will not work!", e);
        }

        System.out.println("Outgoing chat handler registered.");
    }
}