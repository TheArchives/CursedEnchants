package com.archivesmc.cursedenchants.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class Target {
    private Block block = null;
    private Entity entity = null;

    private Type type;

    public Target(Object object) {
        if (object == null) {
            this.type = Type.NONE;
        } else if (object instanceof Block) {
            this.type = Type.BLOCK;
            this.block = (Block) object;
        } else if (object instanceof Entity) {
            this.type = Type.ENTITY;
            this.entity = (Entity) object;
        } else {
            this.type = Type.UNKNOWN;
        }
    }

    public Block getBlock() {
        return block;
    }

    public Entity getEntity() {
        return entity;
    }

    public Type getType() {
        return type;
    }
}
