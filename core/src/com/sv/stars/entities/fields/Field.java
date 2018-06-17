package com.sv.stars.entities.fields;

import com.badlogic.gdx.math.Vector3;
import com.sv.stars.entities.Entity;

public abstract class Field implements Effector {

	Vector3 pos; 										// Fields may not store motion information and must instead be attached to another entity
	float threshhold; 							// The final kill distance beyond which a field has 0 logical effect.
																	// if set to a negative value, is considered to be universal
	//abstract void effect(Entity e, float dt); //The effect of a field on an entity is a function of the type of field and the kind
																	// of entity, especially the Materials possessed by the entity.
	abstract Vector3 get_pos();			//Returns the center of the field
	abstract void set_pos();

}
