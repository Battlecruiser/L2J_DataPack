package transformations;

import net.sf.l2j.gameserver.model.L2Transformation;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;

/**
 * This is currently only a test of the java script engine
 * 
 * @author durgus
 *
 */
public class GrailApostle extends L2Transformation
{
    public GrailApostle()
    {
        // id, duration (secs), colRadius, colHeight
        super(201, 3600, 8.0, 22.0);
    }
    
    public void onTransform()
    {
    }
    
    public void onUntransform()
    {
    }
    
    public static void main(String[] args)
    {
        TransformationManager.getInstance().registerTransformation(new GrailApostle());
    }
}
