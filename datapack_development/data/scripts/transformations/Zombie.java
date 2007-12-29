package transformations;

import net.sf.l2j.gameserver.model.L2Transformation;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;

/**
 * This is currently only a test of the java script engine
 * 
 * @author durgus
 *
 */
public class Zombie extends L2Transformation
{
    public Zombie()
    {
        // id, duration (secs), colRadius, colHeight
        super(303, 3600, 11.0, 24.6);
    }
    
    public void onTransform()
    {
    }
    
    public void onUntransform()
    {
    }
    
    public static void main(String[] args)
    {
        TransformationManager.getInstance().registerTransformation(new Zombie());
    }
}
