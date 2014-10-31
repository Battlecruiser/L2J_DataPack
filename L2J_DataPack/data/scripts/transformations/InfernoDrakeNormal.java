package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2Transformation;

/**
 * Description: <br>
 * This will handle the transformation, giving the skills, and removing them, when the player logs out and is transformed these skills
 * do not save. 
 * When the player logs back in, there will be a call from the enterworld packet that will add all their skills.
 * The enterworld packet will transform a player.
 * 
 * @author durgus
 *
 */
public class InfernoDrakeNormal extends L2Transformation
{
	public InfernoDrakeNormal()
	{
		// id, duration (secs), colRadius, colHeight
		super(214, 1800, 8.0, 22.0);
	}

	public void onTransform()
	{
		// Disable all character skills.
		for (L2Skill sk : this.getPlayer().getAllSkills())
		{
			if (sk != null && !sk.isPassive())
				this.getPlayer().removeSkill(sk, false, false);
		}
		if (this.getPlayer().transformId() > 0 && !this.getPlayer().isCursedWeaponEquipped())
		{
			// give transformation skills
			transformedSkills();
			return;
		}
		// give transformation skills
		transformedSkills();
	}

	public void transformedSkills()
	{
        // Paw Strike
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(576, 3), false);
		// Fire Breath
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(577, 3), false);
		// Blaze Quake
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(578, 3), false);
		// Fire Armor
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(579, 3), false);
		// Transfrom Dispel
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Send a Server->Client packet StatusUpdate to the L2PcInstance.
		this.getPlayer().sendSkillList();
	}

	public void onUntransform()
	{
		// remove transformation skills
		removeSkills();
	}

	public void removeSkills()
	{
        // Paw Strike
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(576, 3), false);
		// Fire Breath
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(577, 3), false);
		// Blaze Quake
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(578, 3), false);
		// Fire Armor
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(579, 3), false);
		// Transfrom Dispel
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Send a Server->Client packet StatusUpdate to the L2PcInstance.
		this.getPlayer().sendSkillList();
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new InfernoDrakeNormal());
	}
}
