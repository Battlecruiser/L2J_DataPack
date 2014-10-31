package transformations;

import net.sf.l2j.gameserver.model.L2Transformation;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;

/**
 * This is currently only a test of the java script engine
 * 
 * @author KenM
 *
 */
public class Zariche extends L2Transformation
{
    public Zariche()
    {
        // id, duration (secs), colRadius, colHeight
        // "infinite" duration - ended manually
        super(301, Integer.MAX_VALUE, 9.0, 31.0);
    }
    
    public void onTransform()
    {
        // Set charachter name to transformed name
    	this.getPlayer().getAppearance().setVisibleName("Zariche");
    	this.getPlayer().getAppearance().setVisibleTitle("");
    }
    
    public void onUntransform()
    {
	// set character back to true name.
    	this.getPlayer().getAppearance().setVisibleName(null);
    	this.getPlayer().getAppearance().setVisibleTitle(null);
    }
    
    public static void main(String[] args)
    {
        TransformationManager.getInstance().registerTransformation(new Zariche());
    }
}
