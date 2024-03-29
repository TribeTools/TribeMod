package net.minecraft.src;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatClickData
{
    public static final Pattern pattern = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,3})(/\\S*)?$");
    private final FontRenderer fontR;
    private final ChatLine line;
    private final int field_50093_d;
    private final int field_50094_e;
    private final String field_50091_f;
    private final String field_50092_g;

    public ChatClickData(FontRenderer par1FontRenderer, ChatLine par2ChatLine, int par3, int par4)
    {
        fontR = par1FontRenderer;
        line = par2ChatLine;
        field_50093_d = par3;
        field_50094_e = par4;
        field_50091_f = par1FontRenderer.trimStringToWidth(par2ChatLine.message, par3);
        field_50092_g = func_50090_c();
    }

    public String func_50088_a()
    {
        return field_50092_g;
    }

    /**
     * computes the URI from the clicked chat data object
     */
    public URI getURI()
    {
        String s = func_50088_a();

        if (s == null)
        {
            return null;
        }

        Matcher matcher = pattern.matcher(s);

        if (matcher.matches())
        {
            try
            {
                String s1 = matcher.group(0);

                if (matcher.group(1) == null)
                {
                    s1 = (new StringBuilder()).append("http://").append(s1).toString();
                }

                return new URI(s1);
            }
            catch (URISyntaxException urisyntaxexception)
            {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "Couldn't create URI from chat", urisyntaxexception);
            }
        }

        return null;
    }

    public String getWord() 
    {
    	return func_50088_a();
    }
    private String func_50090_c()
    {
        int i = field_50091_f.lastIndexOf(" ", field_50091_f.length()) + 1;

        if (i < 0)
        {
            i = 0;
        }

        int j = line.message.indexOf(" ", i);

        if (j < 0)
        {
            j = line.message.length();
        }

        FontRenderer _tmp = fontR;
        return FontRenderer.stripColorCodes(line.message.substring(i, j));
    }
}
