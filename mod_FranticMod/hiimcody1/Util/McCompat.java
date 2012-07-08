package hiimcody1.Util;

import net.minecraft.src.GuiTextField;

public class McCompat {
	public boolean isFocused(GuiTextField tf) {
		return tf.getIsFocused();
	}
	
	public void setFocused(GuiTextField tf, boolean b) {
		tf.setFocused(b);
	}
	
	public boolean keyTyped(GuiTextField tf, char c, int i) {
		return tf.textboxKeyTyped(c, i);
	}
}
