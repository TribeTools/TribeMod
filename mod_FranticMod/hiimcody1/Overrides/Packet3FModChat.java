package hiimcody1.Overrides;

import net.minecraft.src.IntHashMap;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet3Chat;
import hiimcody1.FranticMod;

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
* @obfuscated 1.2
*/
public class Packet3FModChat extends net.minecraft.src.Packet3Chat {

    protected static FranticMod controller;
    protected static boolean registered = false;
    protected boolean cancelled = false;

    public Packet3FModChat() {
        super();
    }

    public Packet3FModChat(String s) {
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

        Packet3FModChat.controller = controller;

        try {
        	
        	Field nmFielda[] = Packet.class.getDeclaredFields();
            
//            for(int i=0; i < nmFielda.length; i++) {
//            	System.out.println("Field Number: "+i+ "; Real name:"  +nmFielda[i].getName());
//            }
            Field idstoclassesfield = nmFielda[0];
            Field classestoidsfield = nmFielda[1];

            idstoclassesfield.setAccessible(true);
            classestoidsfield.setAccessible(true);

            IntHashMap idstoclasses = (IntHashMap) idstoclassesfield.get(null);
            Map<Class<?>, Integer> classestoids = (Map<Class<?>, Integer>) classestoidsfield.get(null);
            idstoclasses.addKey(3, Packet3FModChat.class);
            //Obfuscation.putToMCHash(idstoclasses, 3, Packet3CUIChat.class);
            classestoids.put(Packet3FModChat.class, 3);

        } catch (Exception e) {
            throw new RuntimeException("Error inserting chat handler!", e);
        }

        System.out.println("Incoming chat handler registered.");
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
        //ChatEvent chatevent = new ChatEvent(controller, a, ChatEvent.Direction.INCOMING);
        //controller.getEventManager().callEvent(chatevent);
        //if (!chatevent.isCancelled()) {
    	if(!controller.processIncomingMessage(this.message)) {
            nethandler.handleChat(this);
    	}
        //}
    }
}
