package renderPipeline.renderers;

import renderPipeline.mesh.TexturedModel;
import renderPipeline.objects.Camera;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

public class TexturedModelRenderer extends ModelRenderer{


    public TexturedModelRenderer(){
      model = new TexturedModel();
      cam = new Camera();

    }

    @Override
    public void bind(){
        super.bind();
        shader.setUniform("tex",0);

    }

    @Override
    public void render(){
        shader.start();
        if(!model.isBound()){
            model.bind();

        }
        cam.update();

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glLoadIdentity();

        shader.setUniform("view",cam.getView().mul(model.getModelView()));
        //shader.setUniform("view",model.getModelView().mul(cam.getView()));
        //GIVES OPENGL THE VBO INFO SO IT KNOWS HOW TO DRAW TRIS FOR OBJECT

        glBindBuffer(GL_ARRAY_BUFFER, model.v_id());
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, model.c_id());
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, ((TexturedModel) model).tc_id());
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.i_id());
        //DRAWS ARRAYS



        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        //glDrawArrays(GL_TRIANGLES, 0,draw_count);
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
//        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        shader.stop();

    }

}
