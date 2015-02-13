package com.ericfaerber.gauntletgdk.components;

import com.badlogic.ashley.core.Component;
import com.ericfaerber.gauntletgdk.systems.RenderingSystem;

public class CharacterComponent extends Component {
    // player types
    public static final int TYPE_WARRIOR = 0;
    public static final int TYPE_WIZARD = 1;
    public static final int TYPE_VALKYIRE = 2;
    public static final int TYPE_ELF = 3;
    
    // enemy types
    public static final int TYPE_GHOST = 4;
    public static final int TYPE_GRUNT = 5;
    public static final int TYPE_DEMON = 6;
    public static final int TYPE_SORCERER = 7;
    public static final int TYPE_DEATH = 8;
    
    // movement states
    public static final int STATE_NORTH = 0;
    public static final int STATE_NORTH_EAST = 1;
    public static final int STATE_EAST = 2;
    public static final int STATE_SOUTH_EAST = 3;
    public static final int STATE_SOUTH = 4;
    public static final int STATE_SOUTH_WEST = 5;
    public static final int STATE_WEST = 6;
    public static final int STATE_NORTH_WEST = 7;
    
    public static final float CHARACTER_WIDTH = 32 * RenderingSystem.PIXELS_TO_METERS;
    public static final float CHARACTER_HEIGHT = 32 * RenderingSystem.PIXELS_TO_METERS;
    
    public int health;
    public int speed;
    public int range_damage;
    public int melee_damage;
    public int magic_damage;
    public int armor;
    
    public int type;
    
    public CharacterComponent(int type) {
        this.type = type;
        
        switch(type) {
            case TYPE_WARRIOR:
                health = 980;
                range_damage = 2;
                melee_damage = 3;
                magic_damage = 2;
                armor = 5;
                speed = 3;
            break;
            case TYPE_WIZARD:
                health = 800;
                range_damage = 4;
                melee_damage = 1;
                magic_damage = 5;
                armor = 1;
                speed = 3;
            break;
            case TYPE_VALKYIRE:
                health = 900;
                range_damage = 2;
                melee_damage = 3;
                magic_damage = 2;
                armor = 5;
                speed = 3;
            break;
            case TYPE_ELF:
                health = 820;
                range_damage = 2;
                melee_damage = 1;
                magic_damage = 3;
                armor = 2;
                speed = 5;
            break;
            case TYPE_GHOST:
                health = 3;
                range_damage = 0;
                melee_damage = 3;
                magic_damage = 0;
                armor = 0;
                speed = 2;
            break;
        }
    }
}
