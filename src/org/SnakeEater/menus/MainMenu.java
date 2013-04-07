package org.SnakeEater.menus;


import org.SnakeEater.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

public class MainMenu extends Menu {
    
    private TiledMap map;

    public MainMenu(int fontSize) {
        super(fontSize);
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame game) {
        map = ((Game) game).getResourceManager().getMap("title");
        options.add(loadOptions((int)(gc.getWidth()*.7), (int)(gc.getHeight()*.1), "Start", "Options", "Quit"));
        options.add(loadOptions((int)(gc.getWidth()*.1), (int)(gc.getHeight()*.1), "Video", "Audio", "Return"));
        super.init(gc, game);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        map.render(0, 0);
        super.render(gc, game, g);
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if(key == Input.KEY_ENTER) {
            click();
        }
    }
    
    private void click() {
        if(curPage == 0) {
            if(options.get(curPage)[curOption].getTitle().equals("Start")) {
                game.enterState(Game.IN_GAME_STATE_ID, new FadeOutTransition(), new FadeInTransition());
            } else if(options.get(curPage)[curOption].getTitle().equals("Options")) {
                curPage++;
                setCurOption(0);
                map = ((Game) game).getResourceManager().getMap("mainOptions");
            } else if(options.get(curPage)[curOption].getTitle().equals("Quit")) {
                System.exit(0);
            }
        }
        
        else if(curPage == 1) {
            if(options.get(curPage)[curOption].getTitle().equals("Video")) {
            } else if(options.get(curPage)[curOption].getTitle().equals("Audio")) {
            } else if(options.get(curPage)[curOption].getTitle().equals("Return")) {
                curPage--;
                setCurOption(0);
                map = ((Game) game).getResourceManager().getMap("title");
            }
        }
    }
}
