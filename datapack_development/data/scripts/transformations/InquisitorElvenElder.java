package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class InquisitorElvenElder extends L2Transformation
{
	public InquisitorElvenElder()
	{
		// id
		super(317);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 317 || getPlayer().isCursedWeaponEquipped())
			return;
		
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
			getPlayer().setTransformAllowedSkills(new int[]{838,1523,1528,1524,1525,1430,1043,1400,1303});
		}
		else
			getPlayer().setTransformAllowedSkills(new int[]{838,1430,1043,1400,1303});
		// Switch Stance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
	}
	
	@Override
	public void onUntransform()
	{
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
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new InquisitorElvenElder());
	}
}
