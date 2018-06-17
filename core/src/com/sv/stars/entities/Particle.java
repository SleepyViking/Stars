
package com.sv.stars.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Particle extends Entity{

  //private Model model;

  private int rm = 0;

  public Matrix4 get_transform() {
    return null;
  }
  public Quaternion get_orientation() {
    return null;
  }
  Vector3 get_forward() {
    return new Vector3(0, 0, 1);
  }

  Vector3 get_back() {
    return new Vector3(0, 0, -1);
  }

  Vector3 get_up() {
    return new Vector3(0, 1, 0);
  }

  Vector3 get_down() {
    return new Vector3(0, -1, 0);
  }

  Vector3 get_right() {
    return new Vector3(1, 0, 0);
  }

  Vector3 get_left() {
    return new Vector3(-1, 0, 0);
  }

  public void apply_force(Vector3 F, float dt) {

  }

  public ModelInstance get_MI() {
    return mi;
  }

  //-1  = none
  //0   = point
  //1   = sprite
  //2   = sphere
  //3   = model
  private ModelInstance mi;

  private Vector3 pos;
  private Vector3 vel;
  private Vector3 acc;

  private float mass = 0;

  public Particle(){
    this(new Vector3(), new Vector3(), new Vector3(), 1.0f);
  }

  public Particle(Vector3 pos){
    this(pos, new Vector3(), new Vector3(), 1.0f);
  }

  public Particle(Vector3 pos, Vector3 vel, Vector3 acc, float mass) {
    this(pos, vel, acc, mass, 2);
  }
  public Particle(Vector3 pos, float mass) {
    this(pos, new Vector3(), new Vector3(), mass, 2);
  }

  public Particle(Vector3 pos, Vector3 vel, Vector3 acc, float mass, int rm) {
    this.pos = pos;
    this.vel = vel;
    this.acc = acc;
    this.mass = mass;
    this.rm = rm;
  }

  public float get_mass(){return mass;}
  
  public Vector3 get_vel(){return vel;}
  public Vector3 get_acc(){return acc;}
  public Vector3 get_pos(){return pos;}

  public Vector3 get_force(){
    return acc.cpy().scl(mass);
  }

  public void applyForce(Vector3 F, float dt){
    this.vel.add(F.scl(dt/mass));
  }

  public void apply_acc(Vector3 A, float dt){
    this.vel.add(A.scl(dt));
  }

  public void die(){

  }

  public void set_mass(float m){
    this.mass = m;
  }

  public int get_rm(){
    return rm;
  }

  public void set_mi(ModelInstance mi){
    this.mi = mi;
  }
  public ModelInstance get_mi(){
    return mi;
  }
  
}