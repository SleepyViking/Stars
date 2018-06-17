package com.sv.stars.entities.fields;

import com.badlogic.gdx.math.Vector3;
import com.sv.stars.entities.Entity;

public class GravityField extends Field{
	final float G = (float)6.674E-11; // m^3/(kg*s^2)
	float mass = 0;

	private Vector3 temp = new Vector3();

	@Override
	public void effect(Entity e, float dt) {
		if(e.get_pos().dst2(pos) < threshhold*threshhold)
		{
			temp.setZero(); //set temp to 0 at the start of all uses
			temp.add(pos).sub(e.get_pos()).scl(G*e.get_mass()/e.get_pos().dst2(pos));
			e.apply_acc(temp, dt);
		}
	}

	@Override
	Vector3 get_pos() {
		return this.pos;
	}

	Vector3 acc_at(Vector3 v){
		return new Vector3().add(pos).sub(v).scl(mass*G/pos.dst2(v));
	}

	@Override
	void set_pos() {

	}
}
