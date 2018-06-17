package com.sv.stars.entities.fields;

import com.sv.stars.entities.Entity;

public interface Effector {
	abstract void effect(Entity e, float dt);
}
