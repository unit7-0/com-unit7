/**
 * Date:	21 дек. 2013 г.
 * File:	Resources.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.resources;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.exceptions.ResourceNotFoundException;
import com.unit7.services.pokerservice.client.model.CardType;
import com.unit7.services.pokerservice.client.model.Suit;

/**
 * @author unit7
 *
 */
public class Resources {
    public static Image getImageByName(String name) throws ResourceNotFoundException {
        return deckCache.get(name);
    }
    
    public static final Object STUB = new Object();
    
    public static final String REQUEST_SMALL_BLIND_TITLE = "Введите значение малого блайнда";
    public static final String REQUEST_BIG_BLIND_TITLE = "Введите значение большого блайнда";
    public static final String REQUEST_BET_TITLE = "Выберите ставку";
    public static final String REGEX_DOUBLE = "\\d(\\.(([1-9])|([0-9][1-9]?)))?\\s*";
    
    public static final String DIAMONDS_FILE = "cards_diamonds.png";
    public static final String CLUBS_FILE = "cards_clubs.png";
    public static final String SPADES_FILE = "cards_spades.png";
    public static final String HEARTS_FILE = "cards_hearts.png";
    public static final String UNKNOWN_FILE = "unknown.png";
    
    public static final String DECK_PATH = "/imgs/";
    
    private static final Map<String, Image> deckCache = new HashMap<String, Image>();
    
    static {
        Image[] images = getCardsFromFile(/*DECK_PATH + */DIAMONDS_FILE);
        putToDeckCache(Suit.DIAMONDS.getName(), images);
        
        images = getCardsFromFile(/*DECK_PATH + */CLUBS_FILE);
        putToDeckCache(Suit.CLUBS.getName(), images);
        
        images = getCardsFromFile(/*DECK_PATH + */HEARTS_FILE);
        putToDeckCache(Suit.HEARTS.getName(), images);
        
        images = getCardsFromFile(/*DECK_PATH + */SPADES_FILE);
        putToDeckCache(Suit.SPADES.getName(), images);
        
        try {
			Image unk = ImageIO.read(Resources.class.getResource(UNKNOWN_FILE));
			deckCache.put(CardType.UNKNOWN.getName(), unk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Добавляет массив картинок в кэш по масти.
     * @param suit
     * @param images
     */
    private static void putToDeckCache(String suit, Image[] images) {
        CardType[] types = CardType.values();
        for (int i = 0; i < types.length - 1; ++i) {
            CardType type = types[i];
            String name = type.getName() + "_" + suit;
            deckCache.put(name, images[i]);
        }
    }
    
    /**
     * Загружает картинку карт из файла и режет ее по кускам, каждый кусок - карта.
     * @param name
     * @return
     */
    private static Image[] getCardsFromFile(String name) {
        BufferedImage[] images = new BufferedImage[CardType.values().length - 1];
        int rows = 1;
        int cols = images.length;
        
        URL url = Resources.class.getResource(name);
        try {
            BufferedImage image = ImageIO.read(url);
            int width = image.getWidth() / cols;
            int height = image.getHeight() / rows;
            for (int i = 0; i < images.length; ++i) {
                images[i] = new BufferedImage(width, height, image.getType());
                Graphics2D gr = images[i].createGraphics();
                gr.drawImage(image, 0, 0, width, height, i * width, 0, i * width + width, height, null);
                gr.dispose();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return images;
    }
    
//    private Logger log = Logger.getLogger(Resources.class);
}
