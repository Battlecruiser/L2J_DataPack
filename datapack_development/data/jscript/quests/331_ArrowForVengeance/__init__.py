# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HARPY_FEATHER = 1452
MEDUSA_VENOM = 1453
WYRMS_TOOTH = 1454
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7125-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
   elif event == "7125-06.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
      st.set("cond","0")
   if npcId == 7125 and int(st.get("cond"))==0 :
      if st.getPlayer().getLevel() >= 32 :
         htmltext = "7125-02.htm"
         return htmltext
      else:
         htmltext = "7125-01.htm"
         st.exitQuest(1)
   elif npcId == 7125 and int(st.get("cond"))==1 :
     if st.getQuestItemsCount(HARPY_FEATHER)+st.getQuestItemsCount(MEDUSA_VENOM)+st.getQuestItemsCount(WYRMS_TOOTH)>0 :
        st.giveItems(ADENA,80*st.getQuestItemsCount(HARPY_FEATHER)+90*st.getQuestItemsCount(MEDUSA_VENOM)+100*st.getQuestItemsCount(WYRMS_TOOTH))
        st.takeItems(HARPY_FEATHER,-1)
        st.takeItems(MEDUSA_VENOM,-1)
        st.takeItems(WYRMS_TOOTH,-1)
        htmltext = "7125-05.htm"
     else:
        htmltext = "7125-04.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   n = st.getRandom(10)
   if n<5 :
      if npcId == 145 :
         st.giveItems(HARPY_FEATHER,1)
      elif npcId == 158 :
         st.giveItems(MEDUSA_VENOM,1)
      elif npcId == 176 :
         st.giveItems(WYRMS_TOOTH,1)
      st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(331,"331_ArrowForVengeance","Arrow For Vengeance")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7125)
CREATED.addTalkId(7125)
STARTED.addTalkId(7125)

STARTED.addKillId(145)
STARTED.addKillId(158)
STARTED.addKillId(176)

STARTED.addQuestDrop(145,HARPY_FEATHER,1)
STARTED.addQuestDrop(158,MEDUSA_VENOM,1)
STARTED.addQuestDrop(176,WYRMS_TOOTH,1)

print "importing quests: 331: Arrow For Vengeance"
