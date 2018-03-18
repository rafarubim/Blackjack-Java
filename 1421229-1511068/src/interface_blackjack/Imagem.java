package interface_blackjack;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Imagem {
	
    private int posX, posY;
    private Image im;
    
    public Imagem(Image im) {
        this.im = im;
        posX = 0;
        posY = 0;
    }
    
    public Imagem(Image im, int posX, int posY) {
        this.im = im;
        this.posX = posX;
    	this.posY = posY;
    }
    
    public Imagem(String s) {
        im = carregarImagem(s);
        posX = 0;
        posY = 0;
    }
    
    public Imagem(String s, int posX, int posY) {
        im = carregarImagem(s);
        this.posX = posX;
    	this.posY = posY;
    }
    
    private Image carregarImagem(String s) {
        try {
            Image i = ImageIO.read(new File(s));
            return i;
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
    
    public Image getImagem () {
        return im;
    }
    
    public int getPosX() {
        return posX;
    }
    
    public int getPosY() {
        return posY;
    }
}