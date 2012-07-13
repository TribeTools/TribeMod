package net.minecraft.FranticMod.Overrides;

//import net.minecraft.src.MC;
import net.minecraft.FranticMod.FranticMod;
import net.minecraft.FranticMod.Event.ChatEvent;
import net.minecraft.src.IntHashMap;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet3Chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;


/**
* Replacement for Packet3Chat, in order to listen
* to incoming chat without conflicting with mods
*
* This uses reflection to be set as the packet.
* Don't look at it, it's horribly ugly.
*
* @author lahwran
* @author yetanotherx
*
* @obfuscated
*/
public class Packet3FMChat extends Packet3Chat {

    protected static boolean registered = false;
	private static FranticMod controller;
    protected boolean cancelled = false;

    public Packet3FMChat() {
        super();
    }

    public Packet3FMChat(String s) {
        super(s);
    }

    /**
* Replaces the incoming packet ID 3 with this class
* @param controller
*/
    @SuppressWarnings("unchecked")
    public static void register(FranticMod controller) {

        if (registered) {
            return;
        }
        registered = true;

        Packet3FMChat.controller = controller;

        try {
        	Field idstoclassesfield = Packet.class.getDeclaredField("k"); //field_471_a which is packetIdToClassMap  k
            Field classestoidsfield = Packet.class.getDeclaredField("a"); //field_470_b which is packetClassToIdMap  a

            idstoclassesfield.setAccessible(true);
            classestoidsfield.setAccessible(true);

            IntHashMap idstoclasses = (IntHashMap) idstoclassesfield.get(null);
            Map<Class<?>, Integer> classestoids = (Map<Class<?>, Integer>) classestoidsfield.get(null);
            //ModLoader.putToMCHash(idstoclasses, 3, Packet3FMChat.class);
            idstoclasses.addKey(3, Packet3FMChat.class);
            classestoids.put(Packet3FMChat.class, 3);

        } catch (Exception e) {
            throw new RuntimeException("Error, FranticMod cannot hook into the ChatManager. None of the UI chat enhancements will work.", e);
        }

        controller.debug("Registered inbound chat handler.");
    }

    public void writePacketData(DataOutputStream dataoutputstream) {
        if (!cancelled) {
        	try {
				writeString(message, dataoutputstream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    /**
* Called on incoming Packet3Chat, calls a new ChatEvent
* @param nethandler
*/
    public void processPacket(NetHandler nethandler) {
    	ChatEvent chatevent = new ChatEvent(controller, message, ChatEvent.Direction.INCOMING);
        controller.callEvent(chatevent);
        if (!chatevent.isCancelled()) {
            nethandler.handleChat(this);
        }
    }
}