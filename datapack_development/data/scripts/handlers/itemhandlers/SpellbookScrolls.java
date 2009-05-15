package handlers.itemhandlers;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.TvTEvent;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

/**
* @author Kerberos
*
*/

public class SpellbookScrolls implements IItemHandler
{
	private static final int[] ITEM_IDS =
	{
		10549,10550,10551,10552,10553,10554,10555,10556,10557,10558,
		10559,10560,10561,10562,10563,10564,10565,10566,10567,10568,
		10569,10570,10571,10572,10573,10574,10575,10576,10577,10578,
		10579,10580,10581,10582,10583,10584,10585,10586,10587,10588,
		10589,10591,10592,10593,10594,10595,12768,12769,12770,12771,
		13552,13553,13554,13728,14170,14171,14172,14173,14174,14175,
		14176,14177,14178,14179,14180,14181,14182,14183,14184,14185,
		14186,14187,14188,14189,14190,14191,14192,14193,14194,14195,
		14196,14197,14198,14199,14200,14201,14202,14203,14204,14205,
		14206,14207,14208,14209,14210,14211,14212,14213,14214,14215,
		14216,14217,14218,14219,14220,14221,14222,14223,14224,14225,
		14226,14227,10608,10609,10610
	};
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		L2PcInstance player;
		if (!(playable instanceof L2PcInstance))
			return;

		player = ((L2PcInstance)playable);
		if (player.isAllSkillsDisabled() || player.isCastingNow())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		if (player.isInOlympiadMode())
		{
			player.sendPacket(new SystemMessage(SystemMessageId.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT));
			return;
		}
		if (!TvTEvent.onScrollUse(playable.getObjectId()))
		{
			playable.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		int itemId = item.getItemId();
		switch (itemId)
		{
			case 10549:
				useScroll(player,2441,1);
				break;
			case 10550:
				useScroll(player,2442,1);
				break;
			case 10551:
				useScroll(player,2443,1);
				break;
			case 10552:
				useScroll(player,2444,1);
				break;
			case 10553:
				useScroll(player,2445,1);
				break;
			case 10554:
				useScroll(player,2446,1);
				break;
			case 10555:
				useScroll(player,2447,1);
				break;
			case 10556:
				useScroll(player,2448,1);
				break;
			case 10557:
				useScroll(player,2449,1);
				break;
			case 10558:
				useScroll(player,2450,1);
				break;
			case 10559:
				useScroll(player,2451,1);
				break;
			case 10560:
				useScroll(player,2452,1);
				break;
			case 10561:
				useScroll(player,2453,1);
				break;
			case 10562:
				useScroll(player,2454,1);
				break;
			case 10563:
				useScroll(player,2455,1);
				break;
			case 10564:
				useScroll(player,2456,1);
				break;
			case 10565:
				useScroll(player,2457,1);
				break;
			case 10566:
				useScroll(player,2458,1);
				break;
			case 10567:
				useScroll(player,2459,1);
				break;
			case 10568:
				useScroll(player,2460,1);
				break;
			case 10569:
				useScroll(player,2461,1);
				break;
			case 10570:
				useScroll(player,2462,1);
				break;
			case 10571:
				useScroll(player,2463,1);
				break;
			case 10572:
				useScroll(player,2464,1);
				break;
			case 10573:
				useScroll(player,2465,1);
				break;
			case 10574:
				useScroll(player,2466,1);
				break;
			case 10575:
				useScroll(player,2467,1);
				break;
			case 10576:
				useScroll(player,2468,1);
				break;
			case 10577:
				useScroll(player,2469,1);
				break;
			case 10578:
				useScroll(player,2470,1);
				break;
			case 10579:
				useScroll(player,2471,1);
				break;
			case 10580:
				useScroll(player,2472,1);
				break;
			case 10581:
				useScroll(player,2473,1);
				break;
			case 10582:
				useScroll(player,2474,1);
				break;
			case 10583:
				useScroll(player,2475,1);
				break;
			case 10584:
				useScroll(player,2476,1);
				break;
			case 10585:
				useScroll(player,2477,1);
				break;
			case 10586:
				useScroll(player,2478,1);
				break;
			case 10587:
				useScroll(player,2479,1);
				break;
			case 10588:
				useScroll(player,2480,1);
				break;
			case 10589:
				useScroll(player,2481,1);
				break;
			case 10591:
				useScroll(player,2482,1);
				break;
			case 10592:
				useScroll(player,2483,1);
				break;
			case 10593:
				useScroll(player,2484,1);
				break;
			case 10594:
				useScroll(player,2488,1);
				break;
			case 10595:
				useScroll(player,2489,1);
				break;
			case 10608:
				useScroll(player,2505,1);
				break;
			case 10609:
				useScroll(player,2506,1);
				break;
			case 10610:
				useScroll(player,2507,1);
				break;
			case 12768:
				useScroll(player,2526,1);
				break;
			case 12769:
				useScroll(player,2527,1);
				break;
			case 12770:
				useScroll(player,2528,1);
				break;
			case 12771:
				useScroll(player,2529,1);
				break;
			case 13552:
				useScroll(player,2789,1);
				break;
			case 13553:
				useScroll(player,2790,1);
				break;
			case 13554:
				useScroll(player,2791,1);
				break;
			case 13728:
				useScroll(player,2840,1);
				break;
			case 14170:
				useScroll(player,2803,1);
				break;
			case 14171:
				useScroll(player,2804,1);
				break;
			case 14172:
				useScroll(player,2805,1);
				break;
			case 14173:
				useScroll(player,2806,1);
				break;
			case 14174:
				useScroll(player,2807,1);
				break;
			case 14175:
				useScroll(player,2808,1);
				break;
			case 14176:
				useScroll(player,2809,1);
				break;
			case 14177:
				useScroll(player,2810,1);
				break;
			case 14178:
				useScroll(player,2811,1);
				break;
			case 14179:
				useScroll(player,2812,1);
				break;
			case 14180:
				useScroll(player,2813,1);
				break;
			case 14181:
				useScroll(player,2814,1);
				break;
			case 14182:
				useScroll(player,2815,1);
				break;
			case 14183:
				useScroll(player,2816,1);
				break;
			case 14184:
				useScroll(player,2817,1);
				break;
			case 14185:
				useScroll(player,2818,1);
				break;
			case 14186:
				useScroll(player,2819,1);
				break;
			case 14187:
				useScroll(player,2820,1);
				break;
			case 14188:
				useScroll(player,2821,1);
				break;
			case 14189:
				useScroll(player,2822,1);
				break;
			case 14190:
				useScroll(player,2823,1);
				break;
			case 14191:
				useScroll(player,2824,1);
				break;
			case 14192:
				useScroll(player,2825,1);
				break;
			case 14193:
				useScroll(player,2826,1);
				break;
			case 14194:
				useScroll(player,2827,1);
				break;
			case 14195:
				useScroll(player,2828,1);
				break;
			case 14196:
				useScroll(player,2829,1);
				break;
			case 14197:
				useScroll(player,2830,1);
				break;
			case 14198:
				useScroll(player,2652,1);
				break;
			case 14199:
				useScroll(player,2653,1);
				break;
			case 14200:
				useScroll(player,2650,1);
				break;
			case 14201:
				useScroll(player,2655,1);
				break;
			case 14202:
				useScroll(player,2656,1);
				break;
			case 14203:
				useScroll(player,2831,1);
				break;
			case 14204:
				useScroll(player,2667,1);
				break;
			case 14205:
				useScroll(player,2669,1);
				break;
			case 14206:
				useScroll(player,2668,1);
				break;
			case 14207:
				useScroll(player,2651,1);
				break;
			case 14208:
				useScroll(player,2654,1);
				break;
			case 14209:
				useScroll(player,2657,1);
				break;
			case 14210:
				useScroll(player,2658,1);
				break;
			case 14211:
				useScroll(player,2659,1);
				break;
			case 14212:
				useScroll(player,2660,1);
				break;
			case 14213:
				useScroll(player,2661,1);
				break;
			case 14214:
				useScroll(player,2662,1);
				break;
			case 14215:
				useScroll(player,2663,1);
				break;
			case 14216:
				useScroll(player,2664,1);
				break;
			case 14217:
				useScroll(player,2665,1);
				break;
			case 14218:
				useScroll(player,2666,1);
				break;
			case 14219:
				useScroll(player,2780,1);
				break;
			case 14220:
				useScroll(player,2781,1);
				break;
			case 14221:
				useScroll(player,2782,1);
				break;
			case 14222:
				useScroll(player,2783,1);
				break;
			case 14223:
				useScroll(player,2784,1);
				break;
			case 14224:
				useScroll(player,2785,1);
				break;
			case 14225:
				useScroll(player,2786,1);
				break;
			case 14226:
				useScroll(player,2787,1);
				break;
			case 14227:
				useScroll(player,2788,1);
				break;
		}
	}
	public void useScroll(L2PcInstance activeChar, int magicId, int level)
	{
		L2Skill skill = SkillTable.getInstance().getInfo(magicId, level);
		if (skill != null)
			activeChar.useMagic(skill, true, false);
	}
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#getItemIds()
	 */
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}