package hiimcody1.Gui;

import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import hiimcody1.FranticMod;
import hiimcody1.Session.Temp;
import hiimcody1.Util.ChatColors;
import hiimcody1.Util.Config;
import hiimcody1.Util.GuiTools;
import net.minecraft.src.Block;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.Item;

public class GuiConfig extends GuiScreen {
	
	HashMap<Integer,GuiTextField> configOptions = new HashMap<Integer,GuiTextField>();
	HashMap<Integer,GuiSmallButton> configToggle = new HashMap<Integer,GuiSmallButton>();
	int activeBox = 0;
	
	
	public GuiConfig(GuiScreen lastScreen) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		//               id     w    h     text
		//new GuiButton(par1, par2, par3, par4Str);
		
		
		//controlList.add(new GuiButton(0, width / 2 - 100, height / 4, "Lookup"));
        //controlList.add(new GuiButton(1, width / 2 - 100, height / 4, 98, 20, "Approve"));
        
        //controlList.add(new GuiButton(2, width / 2 + 2, height / 4 + 48, "Report Ban"));
        
		
		//Because I Hate MCP
		//isFocused has a Getter which is func_50025_j()
		//The setter for it is func_50033_b()
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120, "Save"));
        controlList.add(new GuiButton(99, width / 2 - 100, height / 4 + 140, "Cancel"));
        int hMod = 0;
        //\u2588
        controlList.add(new GuiButton(100, width / 2 - 100, 56+hMod, 20, 20, ChatColors.Aqua+"\u2588"));
        controlList.add(new GuiButton(101, width / 2 - 80, 56+hMod, 20, 20, ChatColors.Blue+"\u2588"));
        controlList.add(new GuiButton(102, width / 2 - 60, 56+hMod, 20, 20, ChatColors.BrightGreen+"\u2588"));
        controlList.add(new GuiButton(103, width / 2 - 40, 56+hMod, 20, 20, ChatColors.DarkBlue+"\u2588"));
        controlList.add(new GuiButton(104, width / 2 - 20, 56+hMod, 20, 20, ChatColors.DarkGray+"\u2588"));
        controlList.add(new GuiButton(105, width / 2 - 0, 56+hMod, 20, 20, ChatColors.DarkGreen+"\u2588"));
        controlList.add(new GuiButton(106, width / 2 + 20, 56+hMod, 20, 20, ChatColors.DarkRed+"\u2588"));
        controlList.add(new GuiButton(107, width / 2 + 40, 56+hMod, 20, 20, ChatColors.Gold+"\u2588"));
        controlList.add(new GuiButton(108, width / 2 + 60, 56+hMod, 20, 20, ChatColors.Gray+"\u2588"));
        controlList.add(new GuiButton(109, width / 2 + 80, 56+hMod, 20, 20, ChatColors.Pink+"\u2588"));
        controlList.add(new GuiButton(110, width / 2 + 100, 56+hMod, 20, 20, ChatColors.Purple+"\u2588"));
        controlList.add(new GuiButton(111, width / 2 + 120, 56+hMod, 20, 20, ChatColors.Red+"\u2588"));
        controlList.add(new GuiButton(112, width / 2 + 140, 56+hMod, 20, 20, ChatColors.Teal+"\u2588"));
        controlList.add(new GuiButton(113, width / 2 + 160, 56+hMod, 20, 20, ChatColors.White+"\u2588"));
        
		configOptions.put(0, new GuiTextField(fontRenderer, width / 2 - 100, 56+hMod-(56*80), 100, 20)); //Irc Color = 
		configOptions.get(0).setFocused(false);
		configOptions.get(0).setMaxStringLength(32);
		configOptions.get(0).setText(Config.props.getProperty("ircColor", "Purple"));
		
		hMod+=24;
		configOptions.put(1, new GuiTextField(fontRenderer, width / 2 - 100, 56+hMod, 100, 20)); //Irc Default Channel =Config.props.getProperty("ircDefaultChannel", "#franticme"); 
		configOptions.get(1).setFocused(false);
		configOptions.get(1).setMaxStringLength(32);
		configOptions.get(1).setText(Config.props.getProperty("ircDefaultChannel", "#franticme"));
		
		
		hMod+=24;
		/*
		width = 200;
        height = 20;
        enabled = true;
        drawButton = true;
        id = par1;
        xPosition = par2;
        yPosition = par3;
        width = par4;
        height = par5;
        displayString = par6Str;
		 */
		controlList.add(new GuiButton(1, width / 2 - 100, 56+hMod, 20, 20, "X"));
		//configOptions.put(2, new GuiTextField(fontRenderer, width / 2 - 100, 56+hMod, 100, 20));
		GuiTools.getButtonById(controlList, 1).enabled = true;
		GuiTools.getButtonById(controlList, 1).displayString = ((Boolean.parseBoolean(Config.props.getProperty("showVanishInChat","False"))) ? "X" : " ");

		
		
		hMod+=24;
		controlList.add(new GuiButton(2, width / 2 - 100, 56+hMod, 20, 20, "X"));
		//configOptions.put(2, new GuiTextField(fontRenderer, width / 2 - 100, 56+hMod, 100, 20));
		GuiTools.getButtonById(controlList, 2).enabled = true;
		GuiTools.getButtonById(controlList, 2).displayString = ((Boolean.parseBoolean(Config.props.getProperty("showWGInChat","False"))) ? "X" : " ");
		
		
		hMod+=24;
		controlList.add(new GuiButton(3, width / 2 - 100, 56+hMod, 20, 20, "X"));
		//configOptions.put(2, new GuiTextField(fontRenderer, width / 2 - 100, 56+hMod, 100, 20));
		GuiTools.getButtonById(controlList, 3).enabled = true;
		GuiTools.getButtonById(controlList, 3).displayString = ((Boolean.parseBoolean(Config.props.getProperty("useIRCShortcut","False"))) ? "X" : " ");
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 0)
        {
        	try {
        		Config.props.setProperty("ircColor", configOptions.get(0).getText());
        		Config.props.setProperty("ircDefaultChannel", configOptions.get(1).getText());
        		Config.props.setProperty("showVanishInChat", Boolean.toString(GuiTools.getButtonById(controlList, 1).displayString.equals("X")));
        		Config.props.setProperty("showWGInChat", Boolean.toString(GuiTools.getButtonById(controlList, 2).displayString.equals("X")));
        		Config.props.setProperty("useIRCShortcut", Boolean.toString(GuiTools.getButtonById(controlList, 3).displayString.equals("X")));
				Config.saveConfig();
				GuiTools.showModMessage("Configuration", "Saved Successfully!", Block.chest);
			} catch (IOException e) {
				GuiTools.showModMessage("Configuration", ChatColors.DarkRed+"Error while saving!", Block.tnt);
				e.printStackTrace();
			}
        	mc.displayGuiScreen(new GuiFModMenu());
        }
        if(guibutton.id == 1) {
        	guibutton.displayString = guibutton.displayString.equals("X") ? " " : "X";
        }
        if(guibutton.id == 2) {
        	guibutton.displayString = guibutton.displayString.equals("X") ? " " : "X";
        }
        if(guibutton.id == 3) {
        	guibutton.displayString = guibutton.displayString.equals("X") ? " " : "X";
        }
        if(guibutton.id == 99) {
        	mc.displayGuiScreen(new GuiFModMenu());
        }
        
        if(guibutton.id >= 100 && guibutton.id <= 113) {
        	configOptions.get(0).setText(ChatColors.getColor.get(guibutton.displayString.replace("\u2588", "").replace("\247", "").trim()));
        }
    }
	
	@Override
	protected void mouseClicked(int i, int j, int k) {
		for(int h=0; h<configOptions.size();h++) {
			configOptions.get(h).mouseClicked(i, j, k);
			if(configOptions.get(h).getIsFocused()) {
				activeBox=h;
			}
		}
		super.mouseClicked(i, j, k);
	}
	
	@Override
	protected void keyTyped(char c, int i) {
		for(int h=0; h<configOptions.size();h++)
			configOptions.get(h).textboxKeyTyped(c, i);
		if (c == '\t') {
			activeBox++;
		}
	}
	
	@Override
	public void updateScreen() {
		for(int i=0; i<configOptions.size();i++) {
			configOptions.get(i).updateCursorCounter();
			configOptions.get(i).setFocused(false);
		if(configOptions.get(activeBox) != null) {
			configOptions.get(activeBox).setFocused(true);
			configOptions.get(activeBox).updateCursorCounter();
		}
		else
			activeBox=0;
		}
	}
	
	@Override
    public void drawScreen(int i, int j, float f)
    {
        drawBackground(0);
        for(int h=0; h<configOptions.size();h++)
			configOptions.get(h).drawTextBox();
        
        drawCenteredString(fontRenderer, "FranticMod Configuration", width / 2, 20, 0xffffff);
        
        int hMod=0;
        
        drawString(fontRenderer, "IRC Color: ", width / 2 - (100 + fontRenderer.getStringWidth("IRC Color: ")), (56+6)+hMod, 0xffffff);
        
        hMod+=24;
        drawString(fontRenderer, "IRC Default Channel: ", width / 2 - (100 + fontRenderer.getStringWidth("IRC Default Channel: ")), (56+6)+hMod, 0xffffff);
        
        hMod+=24;
        drawString(fontRenderer, "Show Vanish In Chat: ", width / 2 - (100 + fontRenderer.getStringWidth("Show Vanish In Chat: ")), (56+6)+hMod, 0xffffff);

        hMod+=24;
        drawString(fontRenderer, "Show WG In Chat: ", width / 2 - (100 + fontRenderer.getStringWidth("Show WG In Chat: ")), (56+6)+hMod, 0xffffff);

        hMod+=24;
        drawString(fontRenderer, "Use /i for IRC: ", width / 2 - (100 + fontRenderer.getStringWidth("Use /i for IRC: ")), (56+6)+hMod, 0xffffff);
        super.drawScreen(i, j, f);
    }
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
