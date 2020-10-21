/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import com.badlogic.gdx.files.FileHandle;

/**
 *
 * @author Harsh Pandey
 */
public class Test
{
    public static void main(String[] args)
      {
        FileHandle handle = null;
        try
        {
        handle = new FileHandle("src/abc.txt");
        String[] l = handle.readString().split("\n");
        System.out.println(l.length);
        for(int i=0;i<l.length;i++)
                System.out.println(i+"--->"+l[i]);
        }
        catch(com.badlogic.gdx.utils.GdxRuntimeException ex)
        {
            for(int i=0;i<3;i++)
            {
                handle.writeString("assets/Harsh\n", true);
            }
            handle.writeString("assets/Harsh", true);
        }
      }
}
