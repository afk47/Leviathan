package renderPipeline.renderers;

import renderPipeline.Shaders.ShaderProgram;
import renderPipeline.Shaders.StaticShader;
import renderPipeline.mesh.Model;
import renderPipeline.objects.Camera;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

public class ModelRenderer {
    protected Model model;
    protected Camera cam;


    //Shader Setup
    ShaderProgram shader = new StaticShader();



    public ModelRenderer(Model model){
        this.model = model;

        cam = new Camera();
    }

    public ModelRenderer(){
        //model = new Quad();

        cam = new Camera();

    }

    public void render() {
        shader.start();
        if(!model.isBound()){
            model.bind();

        }

        cam.update();

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glLoadIdentity();

        shader.setUniform("view",cam.getView().mul(model.getModelView()));
        //shader.setUniform("view",model.getModelView().mul(cam.getView()));
        //GIVES OPENGL THE VBO INFO SO IT KNOWS HOW TO DRAW TRIS FOR OBJECT

        glBindBuffer(GL_ARRAY_BUFFER, model.v_id());
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, model.c_id());
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.i_id());
        //DRAWS ARRAYS

        glDrawElements(GL_TRIANGLES, model.drawCount(), GL_UNSIGNED_INT, 0);

        //glDrawArrays(GL_TRIANGLES, 0,draw_count);
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
//        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        shader.stop();

    }

    public void bind(){
        model.bind();
    }
}
