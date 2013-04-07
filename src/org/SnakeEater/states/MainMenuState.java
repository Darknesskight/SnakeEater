package org.SnakeEater.states;


import org.SnakeEater.Game;
import org.SnakeEater.menus.MainMenu;
import org.SnakeEater.menus.Menu;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
    private Menu menu;
    
    private boolean loaded = false;
    
    public void init(GameContainer gc, StateBasedGame game) throws SlickException { 
    }
    
    @Override 
    public void enter(GameContainer gc, StateBasedGame game) {
    }
    
    @Override
    public void leave(GameContainer gc, StateBasedGame game) {
        gc.getInput().disableKeyRepeat();
    }
    
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        menu.update(gc, game, delta);
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
    }

    @Override
    public int getID() {
        return Game.MAIN_MENU_STATE_ID;
    }

}
