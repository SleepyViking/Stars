package com.sv.stars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sv.stars.entities.Particle;

public class Stars extends ApplicationAdapter {

	PerspectiveCamera cam;
	CameraInputController cam_con;
	ModelBatch mod_bat;
	Array<ModelInstance> instances;
	Environment env;

	Model model;

	FileHandle log_handle;


	Particle[] particles;
	GravitySystem grav_sys;

	Batch batch;
	BitmapFont font;

	long f = 0;
	double sum = 0.0;
	double avg = 0.0;
	double E_k = 0.0;

	@Override
	public void create () {
		//Bullet.init();
		Gdx.graphics.setWindowedMode(1200,600);
		log_handle = Gdx.files.local("log.csv");
		particles = new Particle[800];


		int clustersize = 41;
		int clusters = particles.length/clustersize;

		Vector3 temp = new Vector3();

		for (int i = 0; i < clusters; i++) {
			for (int j = 0; j < clustersize && i*clustersize+j < particles.length; j++) {
				if(j==0){
					temp = new Vector3().setToRandomDirection().scl((float)Math.random()*6000f);
					particles[i*clustersize + j] = new Particle(temp);
					particles[i*clustersize + j].set_mass(2.2E25f - (float)Math.random()*5E24f); //Approximate mass of the sun and similar stars = 2E31
				}
				else{
					particles[i*clustersize + j] = new Particle(temp.cpy().add(new Vector3().setToRandomDirection().scl((float)Math.random()*3000f)), (float)Math.random()*5.0E14f);
				}

			}
		}
		if(particles.length%clustersize!=0){
			for (int i = 0; i < particles.length%clustersize; i++) {
				particles[clustersize*clusters + i] = new Particle(new Vector3().setToRandomDirection().scl(5000f), (float)Math.random()*5.0E14f);
			}
		}

		for(Particle p : particles){

		}

		/*for(int i = 0; i < particles.length; i++){
			particles[i] = new Particle(new Vector3().setToRandomDirection().scl(60f), new Vector3().setToRandomDirection().scl((float)Math.random()*0.2f), new Vector3(), 1.0f, 2);
			if(i == 0){
				particles[i].set_mass(6E18f);
				particles[i].get_pos().set(0.0f, 0.0f, 0.0f);
				particles[i].get_vel().set(0.0f, 0.0f, 0.0f);
			}
			else particles[i].set_mass(5E21f*(float)Math.random());

		}*/

		grav_sys = new GravitySystem(particles);

		mod_bat = new ModelBatch();
		batch = new SpriteBatch();
		font = new BitmapFont();

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));



		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, 1600f, 0f);
		cam.lookAt(0, 0f, 0f);
		cam.near = 0.1f;
		cam.far = 100000;
		cam.update();

		cam_con = new CameraInputController(cam);
		Gdx.input.setInputProcessor(cam_con);

		instances = new Array<ModelInstance>();

		ModelBuilder mb = new ModelBuilder(); //build default models
		mb.begin();
		mb.node().id = "ball";
		mb.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.WHITE)))
				.sphere(8f, 8f, 8f, 4, 4);
		model = mb.end();

		instances = new Array<ModelInstance>();
		float r = 0;
		for(Particle p : particles){ // For each entity, assign it a modelinstance: either the default model, a sphere, or one defined within the class.
			switch(p.get_rm()){
				case(-1): break;
				case(0)	: break;
				case(1)	: break;
				case(2)	: p.set_mi(new ModelInstance(model, "ball"));
					instances.add(p.get_mi());
					r = (float)Math.pow(p.get_mass(), 1f/3f);
					p.get_mi().transform.setToScaling(r,r,r);
					p.get_mi().materials.get(0).set(ColorAttribute.createDiffuse(Color.RED));
					break;
				case(3)	: break;
			}
		}

	}

	@Override
	public void render () {
		final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime())*0.000001f;
		grav_sys.update(delta);



		for (int j = 0; j < particles.length; j++) {
			particles[j].get_mi().transform.setToTranslation(particles[j].get_pos());
		}
		if(f++ % 120 == 0){
			sum = 0.0;
			E_k = 0;
			for (int i = 0; i < particles.length; i++) {
				for (int j = 0; j < particles.length; j++) {
					if(j!=i) {
						sum += particles[j].get_pos().cpy().sub(particles[i].get_pos()).len();
					}
				}
				E_k += 0.5*particles[i].get_vel().len2()*particles[i].get_mass();
			}
			avg = sum/(double)particles.length;
			//System.out.println("avg = " + avg + " m");
			log_csv(new String[]{f+"", avg+"", E_k+""});
		}

		cam_con.update();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		mod_bat.begin(cam);
		mod_bat.render(instances, env);
		mod_bat.end();

		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		/*for(Particle p : particles){
			font.draw(batch, "" + p.get_mass(), p.get_pos().x, p.get_pos().y);
		}*/
		batch.end();
	}
	
	@Override
	public void dispose () {
		mod_bat.dispose();
		model.dispose();
	}

	boolean checkCollision() {
		return false;
	}

	@Override public void pause(){}
	@Override public void resume(){}
	@Override public void resize(int width, int height){}

	public void log_csv(String[] s){
		for (int i = 0; i < s.length; i++) {
			System.out.print(s[i]+" ");
			log_handle.writeString(s[i]+" ", true);
		}
		log_handle.writeString("\n", true);
		System.out.println();
	}

}
