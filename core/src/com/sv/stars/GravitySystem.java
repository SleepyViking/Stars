package com.sv.stars;

import com.badlogic.gdx.math.Vector3;
import com.sv.stars.entities.Particle;

public class GravitySystem {
    private Particle[] particles;
    private Vector3 temp = new Vector3();

    private float dt; //use greater values for dt for less precision
    final float G = (float)6.674E-11; // m^3/(kg*s^2)

    public GravitySystem(Particle[] particles){
        this.particles = particles;
    }

    public void start(){
        run();
    }
    public void update(float delta){

        dt = delta;

        //gravitation:  simulate attractive forces between each particle


        //Update to be all fields!
        for(int i = 0; i < particles.length; i++){
            for(int j = 0; j < particles.length; j++){
                if(i!=j){
                    temp.add(particles[j].get_pos())
                            .sub(particles[i].get_pos())
                            .scl(G*particles[j].get_mass()/particles[i].get_pos().dst2(particles[j].get_pos()));
                    //TODO: add applyAcc, which will remove a redundant multiplication by one
                    particles[i].apply_acc(temp, dt);
                    temp.setZero();
                }
            }
        }



        for(int i = 0; i < particles.length; i++){
            //motion:       simulate motion of each particle per frame
            temp.setZero();
            temp.add(particles[i].get_acc()).scl(dt*dt*0.5f);
            particles[i].get_vel().add(temp);
            temp.setZero();
            temp.add(particles[i].get_vel()).scl(dt);
            particles[i].get_pos().add(temp);
            temp.setZero();
        }

    }
    public void run(){
        do{
            //update();
        }while(running());
        end();
    }
    public void end(){

    }


    public boolean running(){
        return true;
    }

}
