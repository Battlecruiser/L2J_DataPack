package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class InquisitorBishop extends L2Transformation
{
	public InquisitorBishop()
	{
		// id, duration (secs), colRadius, colHeight
		super(316, Integer.MAX_VALUE, 8.0, 22.0);
	}
	
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 316 || getPlayer().isCursedWeaponEquipped())
			return;

		// give transformation skills
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		if (getPlayer().getLevel() > 43)
		{
			// Divine Punishment
			getPlayer().addSkill(SkillTable.getInstance().getInfo(1523, getPlayer().getLevel() - 43), false);
			// Divine Flash
			getPlayer().addSkill(SkillTable.getInstance().getInfo(1528, getPlayer().getLevel() - 43), false);
			// Surrender to the Holy
			getPlayer().addSkill(SkillTable.getInstance().getInfo(1524, getPlayer().getLevel() - 43), false);
			// Divine Curse
			getPlayer().addSkill(SkillTable.getInstance().getInfo(1525, getPlayer().getLevel() - 43), false);
			getPlayer().setTransformAllowedSkills(new int[]{838,5491,1523,1528,1524,1525,1430,1043,1042,1400,1418});
		}
		else
			getPlayer().setTransformAllowedSkills(new int[]{838,5491,1430,1043,1042,1400,1418});
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false); 
		// Switch Stance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
	}
	
	public void onUntransform()
	{
		// remove transformation skills
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Divine Punishment
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1523, getPlayer().getLevel() - 43), false);
		// Divine Flash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1528, getPlayer().getLevel() - 43), false);
		// Surrender to the Holy
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1524, getPlayer().getLevel() - 43), false);
		// Divine Curse
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1525, getPlayer().getLevel() - 43), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false); 
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new InquisitorBishop());
	}
}