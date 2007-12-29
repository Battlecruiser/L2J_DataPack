package transformations;

import net.sf.l2j.gameserver.model.L2Transformation;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;

/**
 * This is currently only a test of the java script engine
 * 
 * @author durgus
 *
 */
public class Pig extends L2Transformation
{
    public Pig()
    {
        // id, duration (secs), colRadius, colHeight
        super(104, 3600, 15.0, 18.3);
    }
    
    public void onTransform()
    {
    }
    
    public void onUntransform()
    {
    }
    
    public static void main(String[] args)
    {
        TransformationManager.getInstance().registerTransformation(new Pig());
    }
}
