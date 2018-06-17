package com.sv.stars.entities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

public class ParticleEntity extends Entity {

	ModelInstance MI;

	public ParticleEntity(Vector3 pos, Vector3 vel,
												Vector3 acc, float mass, ModelInstance MI) {


	}

	public Vector3 get_pos() {
		return null;
	}
	public Vector3 get_acc() {
		return null;
	}
	public Vector3 get_vel() {
		return null;
	}
	public Matrix4 get_transform() {
		return null;
	}
	public Quaternion get_orientation() {
		return null;
	}
	public Vector3 get_forward() {
		return null;
	}
	public Vector3 get_back() {
		return null;
	}
	public Vector3 get_up() {
		return null;
	}
	public Vector3 get_down() {
		return null;
	}
	public Vector3 get_right() {
		return null;
	}
	public Vector3 get_left() {
		return null;
	}

	public void apply_force(Vector3 F, float dt){
		this.vel.mulAdd(F, dt/mass);
	}
	public void apply_acc(Vector3 A, float dt) {
		this.vel.mulAdd(A, dt);
	}

	public void set_pos(Vector3 pos) {
		this.pos = pos;
	}

	public void set_vel(Vector3 vel) {
		this.vel = vel;
	}

	public void set_acc(Vector3 acc) {
		this.acc = acc;
	}
	public ModelInstance get_MI() {
		return MI;
	}

	public float get_mass() {
		return mass;
	}
}
