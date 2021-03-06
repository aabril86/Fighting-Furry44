package com.mygdx.game.Objects;

import com.mygdx.game.Cosingas;
import com.mygdx.game.MyGdxGame;
import main.java.Mensaje;

public class Juego {
    public Gatito P1, P2;
    public Mano mano;
    public boolean torn;

    private final MyGdxGame game;

    public Juego(MyGdxGame game) {
        this.game = game;
    }

    public void mensaje(Mensaje mensaje){
        game.getBaseScreen().mensaje(mensaje);
    }

    public void jugarCarta(Carta carta, Gatito activo, Gatito rival){
        int damages;
        switch (carta.tipo){
            case "ataque":
                if(rival.defensa == 0)
                    rival.salud -= carta.valor;
                else{
                    if(rival.defensa - carta.valor >= 0)
                        rival.defensa -= carta.valor;
                    else{
                        damages = carta.valor - rival.defensa;
                        rival.defensa = 0;
                        rival.salud -= damages;
                    }
                }
                break;

            case "defensa":
                activo.defensa += carta.valor;
                break;

            case "curacion":
                if(activo.salud + carta.valor <= 100)
                    activo.salud += carta.valor;
                else
                    activo.salud = 100;
                break;

            case "especial":

                switch (carta.nombre){
                    case "canvi de lloc instantani":
                    //TODO
                        break;

                    case "heehee":
                    //TODO
                        break;

                    case "antigravity lean":
                        activo.atBoost = carta.valor;
                        break;

                    case "cabezazo":
                        rival.atBoost = carta.valor;
                        //comprobar que si el ataque es menos de 5 no
                        // le suba vida al otro, se queda en 0
                        break;

                    case "pisoton":
                    //TODO
                        break;

                    case "pacto de acero":
                        activo.defBoost = carta.valor;
                        break;

                    case "furia oriental":
                        rival.salud -= 3 + Math.random() * 5;
                        //entre 3 y 5 de damage
                        break;

                    case "vomito radioactivo":
                        rival.envenenado = true;
                        break;

                    case "polonio o plomo":
                        if (rival.envenenado) rival.salud -= 25;
                        else rival.salud -= carta.valor;
                        break;
                }

            break;
        }

        //LA IDEA ES QUE UNA VEZ PROCESADA LA JUGADA A NIVEL LOCAL ENVIEMOS LA CARTA AL SERVER Y QUE LA PASE AL RIVAL PARA PROCESARLA
        System.out.println("SE JUEGA LA CARTA");

        Mensaje.Carta mCarta = new Mensaje.Carta();
        mCarta.nombre = carta.nombre;
        mCarta.valor = carta.valor;
        mCarta.costeMana = carta.coste_mana;
        mCarta.tipo = carta.tipo;
        Cosingas.cliente.enviar(Mensaje.jugada(mCarta));

    }

    public void ready(String pjSeleccionado) {
        Cosingas.cliente.enviar(Mensaje.ready(pjSeleccionado));
    }

    public void changeTurn() {
        Cosingas.cliente.enviar(Mensaje.cambiarTurno());
    }

    public void procesarJugada(Carta carta, Gatito destPlayer, Gatito sourcePlayer) {
        int damages;

        sourcePlayer.cristales -= carta.coste_mana;

        switch(carta.tipo){
            case "ataque":
                if(destPlayer.defensa == 0)
                    destPlayer.salud -= carta.valor;
                else{
                    if(destPlayer.defensa - carta.valor >= 0)
                        destPlayer.defensa -= carta.valor;
                    else{
                        damages = carta.valor - destPlayer.defensa;
                        destPlayer.defensa = 0;
                        destPlayer.salud -= damages;

                    }
                }
                break;
            case "defensa":
                sourcePlayer.defensa += carta.valor;
                break;
            case "curacion":
                if(sourcePlayer.salud + carta.valor <= 100)
                    sourcePlayer.salud += carta.valor;
                else
                    sourcePlayer.salud = 100;
                break;
            case "especial":
                switch(carta.nombre){
                    case "canvi de lloc instantani":
                        //TODO
                        break;

                    case "heehee":
                        //TODO
                        break;

                    case "antigravity lean":
                        sourcePlayer.atBoost = carta.valor;
                        break;

                    case "cabezazo":
                        destPlayer.atBoost = carta.valor;
                        //comprobar que si el ataque es menos de 5 no
                        // le suba vida al otro, se queda en 0
                        break;

                    case "pisoton":
                        //TODO
                        break;

                    case "pacto de acero":
                        sourcePlayer.defBoost = carta.valor;
                        break;

                    case "furia oriental":
                        destPlayer.salud -= 3 + Math.random() * 5;
                        //entre 3 y 5 de damage
                        break;

                    case "vomito radioactivo":
                        destPlayer.envenenado = true;
                        break;

                    case "polonio o plomo":
                        if (destPlayer.envenenado) destPlayer.salud -= 25;
                        else destPlayer.salud -= carta.valor;
                        break;
                }
                break;
        }
    }

}
