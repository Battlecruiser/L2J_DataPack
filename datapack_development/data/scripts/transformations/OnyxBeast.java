package transformations;

import net.sf.l2j.gameserver.model.L2Transformation;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;

/**
 * This is currently only a test of the java script engine
 * 
 * @author durgus
 *
 */
public class OnyxBeast extends L2Transformation
{
    public OnyxBeast()
    {
        // id, duration (secs), colRadius, colHeight
        super(1, 3600, 14.0, 14.5);
    }
    
    public void onTransform()
    {
    }
    
    public void onUntransform()
    {
    }
    
    public static void main(String[] args)
    {
        TransformationManager.getInstance().registerTransformation(new OnyxBeast());
    }
}
