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
                    handle = new FileHandle("main.dd");
                } catch (com.badlogic.gdx.utils.GdxRuntimeException e)
                {
                    handle.writeString("0", false);
                }
                
                try
                {
                    handle = new FileHandle("src/assets/sc.txt");
                } catch (com.badlogic.gdx.utils.GdxRuntimeException e)
                {
                    handle.writeString("0\n", false);
                }
      }
    
}
