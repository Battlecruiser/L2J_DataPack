/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.individual;

import net.sf.l2j.gameserver.model.actor.instance.L2NpcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.jython.QuestJython;
import net.sf.l2j.gameserver.model.L2CharPosition;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.util.Rnd;

public class fleeNpc extends QuestJython 
{
   private int[] _npcId = { 20432, 22228 };

   public fleeNpc(int questId, String name, String descr) 
   {
      super(questId, name, descr);
      
      for( int i = 0; i < _npcId.length; i++ )
      {
      this.addEventId(_npcId[i], Quest.QuestEventType.ON_ATTACK);
      }

   }

   public String onAttack(L2NpcInstance npc, L2PcInstance attacker, int damage, boolean isPet) 
   {
      if ( Rnd.get(3) == 2 ) 
      {
         npc.startFear();
         npc.getAI().setIntention( CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition((npc.getClientX() + Rnd.get(-1000, 1000)), (npc.getClientY()+ Rnd.get(-1000, 1000)), npc.getClientZ(), npc.getHeading()));
         return null;
      }
      
      return super.onAttack(npc, attacker, damage, isPet);
   }

   // Register the new Script at the Script System
   public static void main(String[] args) 
   {
      new fleeNpc(-1, "fleeNpc", "Ai for Flee Npcs");
   }
}
