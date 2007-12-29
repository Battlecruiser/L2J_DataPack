package transformations;

import net.sf.l2j.gameserver.model.L2Transformation;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;

/**
 * This is currently only a test of the java script engine
 * 
 * @author durgus
 *
 */
public class Akamanah extends L2Transformation
{
	private String _realName;
    private String _realTitle;
    
    public Akamanah()
    {
        // id, duration (secs), colRadius, colHeight
        super(302, Integer.MAX_VALUE, 10.0, 32.73);
    }
    
    public void onTransform()
    {
        // Store real values
        _realName = this.getPlayer().getName();
        _realTitle = this.getPlayer().getTitle();
        
        // remove title and name
        this.getPlayer().setTitle("");
        this.getPlayer().setName("");
    }
    
    public void onUntransform()
    {
    	this.getPlayer().setTitle(_realTitle);
        this.getPlayer().setName(_realName);
    }
    
    public static void main(String[] args)
    {
        TransformationManager.getInstance().registerTransformation(new Akamanah());
    }
}
