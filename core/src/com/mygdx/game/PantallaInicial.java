package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Config.BaseImageButton;
import com.mygdx.game.Config.BaseScreen;

public class PantallaInicial extends BaseScreen {

    private Texture background;
    public PantallaInicial(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {

        background = new Texture("FondoInicio.png");

        BaseImageButton buttonPlay = new BaseImageButton("play_rojo.png", "play_rojo_botonado.png", 205, 100, 400, 150);
        buttonPlay.onClick(()->setScreen(new SelectionScreen(game)));
        stage.addActor(buttonPlay);

        BaseImageButton buttonQuit = new BaseImageButton("quit.png", "quit_botonado.png", 205, 100, 675, 150);
        buttonQuit.onClick(()->System.exit(0));
        stage.addActor(buttonQuit);
    }

    public void render(float delta) {
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0);
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }
}
