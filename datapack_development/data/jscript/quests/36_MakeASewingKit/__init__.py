# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

REINFORCED_STEEL = 7163
ARTISANS_FRAME = 1891
ORIHARUKON = 1893
SEWING_KIT = 7078

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7847-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   if event == "7847-3.htm" :
     st.takeItems(REINFORCED_STEEL,5)
     st.set("cond","3")
   return htmltext

 def onTalk (Self,npc,st) :
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   cond = int(st.get("cond"))
   if cond == 0 and st.getQuestItemsCount(SEWING_KIT) == 0 :
     fwear=st.getPlayer().getQuestState("37_PleaseMakeMeFormalWear")
     if fwear:
         if fwear.get("cond") == "6" :
           htmltext = "7847-0.htm"
         else:
           st.exitQuest(1)
     else:
       st.exitQuest(1)
   elif st.getQuestItemsCount(REINFORCED_STEEL) == 5 :
     htmltext = "7847-2.htm"
   elif cond == 3 and st.getQuestItemsCount(ORIHARUKON) >= 10 and st.getQuestItemsCount(ARTISANS_FRAME) >= 10 :
     st.takeItems(ORIHARUKON,10)
     st.takeItems(ARTISANS_FRAME,10)
     st.giveItems(SEWING_KIT,1)
     st.playSound("ItemSound.quest_finish")
     htmltext = "7847-4.htm"
     st.exitQuest(1)
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(REINFORCED_STEEL)
   if count < 5 :
     st.giveItems(REINFORCED_STEEL,1)
     if count == 4 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else:
       st.playSound("ItemSound.quest_itemget")	
   return

QUEST       = Quest(36,"36_MakeASewingKit","Make A Sewing Kit")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7847)
CREATED.addTalkId(7847)
STARTED.addTalkId(7847)
STARTED.addKillId(566)
STARTED.addQuestDrop(566,REINFORCED_STEEL,1)

print "importing quests: 36: Make A Sewing Kit"
