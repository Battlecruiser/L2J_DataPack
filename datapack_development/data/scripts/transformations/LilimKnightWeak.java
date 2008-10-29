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
public class LilimKnightWeak extends L2Transformation
{
	public LilimKnightWeak()
	{
		// id, duration (secs), colRadius, colHeight
		super(209, 1800, 8.0, 24.4);
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
		// Attack Buster
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(568, 2), false);
		// Attack Storm
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(569, 2), false);
		// Attack Rage
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(570, 2), false);
		// Poison Dust
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(571, 2), false);
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
		// Attack Buster
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(568, 2), false);
		// Attack Storm
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(569, 2), false);
		// Attack Rage
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(570, 2), false);
		// Poison Dust
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(571, 2), false);
		// Transfrom Dispel
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Send a Server->Client packet StatusUpdate to the L2PcInstance.
		this.getPlayer().sendSkillList();
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new LilimKnightWeak());
	}
}
