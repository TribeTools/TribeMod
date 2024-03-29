// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.FranticMod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

import net.minecraft.FranticMod.Util.UrlLib;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenshotHelperUploadable
{

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static ByteBuffer buffer;
    private static byte pixelData[];
    private static int imageData[];

    public static String saveScreenshot(File file, int i, int j)
    {
        return func_35879_a(file, null, i, j);
    }

    public static String func_35879_a(File file, String s, int i, int j)
    {
        try
        {
            File file1 = new File(file, "screenshots");
            file1.mkdir();
            if(buffer == null || buffer.capacity() < i * j)
            {
                buffer = BufferUtils.createByteBuffer(i * j * 3);
            }
            if(imageData == null || imageData.length < i * j * 3)
            {
                pixelData = new byte[i * j * 3];
                imageData = new int[i * j];
            }
            GL11.glPixelStorei(3333 /*GL_PACK_ALIGNMENT*/, 1);
            GL11.glPixelStorei(3317 /*GL_UNPACK_ALIGNMENT*/, 1);
            buffer.clear();
            GL11.glReadPixels(0, 0, i, j, 6407 /*GL_RGB*/, 5121 /*GL_UNSIGNED_BYTE*/, buffer);
            buffer.clear();
            String s1 = (new StringBuilder()).append("").append(dateFormat.format(new Date())).toString();
            File file2;
            if(s == null)
            {
                for(int k = 1; (file2 = new File(file1, (new StringBuilder()).append(s1).append(k != 1 ? (new StringBuilder()).append("_").append(k).toString() : "").append(".png").toString())).exists(); k++) { }
            } else
            {
                file2 = new File(file1, s);
            }
            buffer.get(pixelData);
            for(int l = 0; l < i; l++)
            {
                for(int i1 = 0; i1 < j; i1++)
                {
                    int j1 = l + (j - i1 - 1) * i;
                    int k1 = pixelData[j1 * 3 + 0] & 0xff;
                    int l1 = pixelData[j1 * 3 + 1] & 0xff;
                    int i2 = pixelData[j1 * 3 + 2] & 0xff;
                    int j2 = 0xff000000 | k1 << 16 | l1 << 8 | i2;
                    imageData[l + i1 * i] = j2;
                }

            }

            BufferedImage bufferedimage = new BufferedImage(i, j, 1);
            bufferedimage.setRGB(0, 0, i, j, imageData, 0, i);
            ImageIO.write(bufferedimage, "png", file2);
            UrlLib.UploadFile(file2.getName(),file2.getAbsolutePath());
            return file2.getName();
            
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return "";
        }
    }

}
