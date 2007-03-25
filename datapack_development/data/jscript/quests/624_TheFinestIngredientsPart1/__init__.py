# by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "624_TheFinestIngredientsPart1"

#NPC
JEREMY = 31521

#ITEMS
TRUNK_OF_NEPENTHES,FOOT_OF_BANDERSNATCHLING,SECRET_SPICE,SAUCE=range(7202,7206)
CRYOLITE=7080

#MOBS
MOBS = HOT_SPRINGS_ATROX,HOT_SPRINGS_ATROXSPAWN,HOT_SPRINGS_BANDERSNATCH,HOT_SPRINGS_NEPENTHES = 21321,21317,21322,21319


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   cond = st.getInt("cond")
   htmltext = event
   trunk = st.getQuestItemsCount(TRUNK_OF_NEPENTHES)
   foot = st.getQuestItemsCount(FOOT_OF_BANDERSNATCHLING)
   spice = st.getQuestItemsCount(SECRET_SPICE)
   if event == "31521-1.htm" :
     if st.getPlayer().getLevel() >= 73 : 
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")       
     else:
        htmltext = "31521-0a.htm"
        st.exitQuest(1)
   elif event == "31521-4.htm" :
     if trunk==foot==spice==50 :
       st.takeItems(TRUNK_OF_NEPENTHES,-1)
       st.takeItems(FOOT_OF_BANDERSNATCHLING,-1)
       st.takeItems(SECRET_SPICE,-1)
       st.playSound("ItemSound.quest_finish")
       st.giveItems(SAUCE,1)
       st.giveItems(CRYOLITE,1)
       htmltext = "31521-4.htm"
       st.exitQuest(1)
     else:
       htmltext="31521-5.htm"
       st.set("cond","1")
   return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   if st :
	   npcId = npc.getNpcId()
	   cond = st.getInt("cond")
	   if cond == 0 :
	      htmltext = "31521-0.htm"
	   elif st.getState() == STARTED
		   if cond <> 3 :
		      htmltext = "31521-2.htm"
		   else :
		      htmltext = "31521-3.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if st :
   	   if st.getState() == STARTED :
		   count_trunk = st.getQuestItemsCount(TRUNK_OF_NEPENTHES)
		   count_foot = st.getQuestItemsCount(FOOT_OF_BANDERSNATCHLING)
		   count_spice = st.getQuestItemsCount(SECRET_SPICE)
		   npcId = npc.getNpcId()
		   if st.getInt("cond") == 1:
		     if npcId == HOT_SPRINGS_NEPENTHES and count_trunk < 50 :
		       st.giveItems(TRUNK_OF_NEPENTHES,1)
		       if count_trunk == 49 and count_foot == count_spice == 50 :
		         st.set("cond","3")
		         st.playSound("ItemSound.quest_middle")
		       else:
		         st.playSound("ItemSound.quest_itemget")	
		     elif npcId == HOT_SPRINGS_BANDERSNATCH and count_foot < 50 :
		       st.giveItems(FOOT_OF_BANDERSNATCHLING,1)
		       if count_trunk == 50 and count_foot == count_spice == 50 :
		         st.set("cond","3")
		         st.playSound("ItemSound.quest_middle")
		       else:
		         st.playSound("ItemSound.quest_itemget")	
		     elif npcId in [ HOT_SPRINGS_ATROX,HOT_SPRINGS_ATROXSPAWN ] and count_spice < 50 :
		       st.giveItems(SECRET_SPICE,1)
		       if count_trunk == count_foot == 50 and count_spice == 49 :
		         st.set("cond","3")
		         st.playSound("ItemSound.quest_middle")
		       else:
		         st.playSound("ItemSound.quest_itemget")	
   return

QUEST       = Quest(624,qn,"The Finest Ingredients - Part 1")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(JEREMY)
QUEST.addTalkId(JEREMY)

for i in MOBS :
  QUEST.addKillId(i)

STARTED.addQuestDrop(JEREMY,TRUNK_OF_NEPENTHES,1)
STARTED.addQuestDrop(JEREMY,FOOT_OF_BANDERSNATCHLING,1)
STARTED.addQuestDrop(JEREMY,SECRET_SPICE,1)

print "importing quests: 624: The Finest Ingredients - Part 1"
