package com.sv.stars.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.sv.stars.entities.fields.Effector;

public class Entity {
	Vector3 pos;
	Vector3 vel;
	Vector3 acc;
	float mass;
	Matrix4 transform;
	Quaternion w_pos;
	Quaternion w_vel;
	ModelInstance MI;
	PlayerController controller = null;


	public Vector3 get_pos(){return pos;}
	public Vector3 get_acc(){return acc;}
	public Vector3 get_vel(){return vel;}

	public Matrix4 get_transform() {
		return transform;
	}

	public Quaternion get_orientation() {
		return w_pos;
	}

	public void apply_force(Vector3 F, float dt){
		this.vel.mulAdd(F, dt/mass);
	}

	public void apply_acc(Vector3 A, float dt){
		this.vel.mulAdd(A, dt);
	}

	public ModelInstance get_MI(){
		return MI;
	}
	public float get_mass(){return mass;}

}
