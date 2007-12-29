package transformations;

import net.sf.l2j.gameserver.model.L2Transformation;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;

/**
 * This is currently only a test of the java script engine
 * Can be used for screenshot purposes :)
 * 
 * @author KenM
 *
 */
public class Kamael extends L2Transformation
{
    public Kamael()
    {
        // id, duration (secs), colRadius, colHeight
        super(251, 3600, 9.0, 30.0);
    }
    
    public void onTransform()
    {
        this.getPlayer().sendMessage("Proof of concept");
        //this.getPlayer().sendMessage("You become an mutant evil-chicken");
    }
    
    public void onUntransform()
    {
        this.getPlayer().sendMessage("End of transformation");
    }
    
    public static void main(String[] args)
    {
        TransformationManager.getInstance().registerTransformation(new Kamael());
    }
}