package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.scene.Spatial;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author LuisDena
 */

public class Main extends SimpleApplication {
    
    private Node sol_nodo;
    public String[] nombres_planetas;
    public Spatial spatial_var = null;
        
    public static void main(String[] args) {
        
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Sistema Solar");
        
        settings.setSettingsDialogImage("Interface/planet.png");
        
        Main app = new Main();
        
        app.setSettings(settings);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        
        flyCam.setMoveSpeed(50f);
        setDisplayStatView(false);
        
        Geometry sol_geom = new Geometry("sol", new Box(5, 5, 5));
        Material sol_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sol_material.setColor("Color", ColorRGBA.Yellow);
        sol_geom.setMaterial(sol_material);
        sol_nodo = new Node("Sol");
        sol_nodo.attachChild(sol_geom);
        rootNode.attachChild(sol_nodo);

        nombres_planetas = new String[]{"mercurio", "venus", "tierra", "marte", "jupiter", "saturno", "urano", "neptuno"};

        for (int i = 0; i < nombres_planetas.length; i++) {
            Geometry planeta_geom;
            if (i % 2 == 0) { // Verifica si el índice es par
                // Si el índice es par, el planeta es el doble de grande
                planeta_geom = new Geometry(nombres_planetas[i], new Box(2.5f, 2.5f, 2.5f));
            } else {
                planeta_geom = new Geometry(nombres_planetas[i], new Box(1.9f, 1.9f, 1.9f));
            }

            Material planeta_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            planeta_material.setColor("Color", ColorRGBA.randomColor());
            planeta_geom.setMaterial(planeta_material);

            Node planeta_nodo = new Node(nombres_planetas[i] + "_nodo");
            planeta_nodo.attachChild(planeta_geom);
            rootNode.attachChild(planeta_nodo);
            
            planeta_geom.move(0, 15 * (i + 2), 0);
        }

    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code

        // Obtener el nodo del sol
        Spatial sol = rootNode.getChild("sol");

        // Rotar el sol alrededor de su propio eje
        sol.rotate(tpf, tpf*3f,0); 

        // Velocidades de rotación para cada planeta 
        float[] velocidades_orbitales = {0.5f, 0.8f, 1.2f, 1.6f, 2.0f, 2.4f, 2.8f, 3.2f};
        float[] velocidades_rotacionales = {1.2f, 0.8f, 0.2f, 0.5f, 5.0f, 2f, 3f, 0.1f};

                
        // Actualizar la rotación de cada planeta 
        for (int i = 0; i < nombres_planetas.length; i++) {
            String nombre_planeta = nombres_planetas[i];
            Node planeta_nodo = (Node) rootNode.getChild(nombre_planeta + "_nodo");
            
            // Rotar el planeta alrededor del sol
            planeta_nodo.rotate(0, 0, velocidades_orbitales[i]* tpf);
            
            // Rotar el planeta en su eje
            Geometry planeta_geom = (Geometry) planeta_nodo.getChild(nombre_planeta);
            planeta_geom.rotate(velocidades_rotacionales[i]*tpf,velocidades_rotacionales[i]*tpf,0);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}