# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
M_NECROMANCER = 8518
ENFEUX = 8519

#ITEMS
SEAL_OF_LIGHT = 7170
GEM_OF_SUBMISSION = 7171
GEM_OF_SAINTS = 7172

#REWARDS
ADENA = 57
MOLD_HARDENER = 41
ENRIA = 4042
ASOFE = 4043
THONS = 4044

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "8518-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "8518-3.htm" :
     st.takeItems(GEM_OF_SUBMISSION,300)
     st.giveItems(SEAL_OF_LIGHT,1)
     st.set("cond","3")
   elif event == "8519-1.htm" :
     st.takeItems(SEAL_OF_LIGHT,1)
     st.giveItems(GEM_OF_SAINTS,1)
     st.set("cond","4")
   elif event == "8518-5.htm" :
     st.takeItems(GEM_OF_SAINTS,1)
   else :
     if event == "8518-6.htm" :
       st.giveItems(ADENA,100000)
     elif event == "8518-7.htm" :
       st.giveItems(ASOFE,13)
     elif event == "8518-8.htm" :
       st.giveItems(THONS,13)
     elif event == "8518-9.htm" :
       st.giveItems(ENRIA,6)
     elif event == "8518-10.htm" :
       st.giveItems(MOLD_HARDENER,6)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if npcId == M_NECROMANCER :
      if cond == 0 :
        if st.getPlayer().getLevel() >= 60 : # and st.getPlayer().getLevel() <= 71
          htmltext = "8518-0.htm"
        else:
          htmltext = "8518-0a.htm"
          st.exitQuest(1)
      elif cond == 1 :
        htmltext = "8518-1a.htm"
      elif st.getQuestItemsCount(GEM_OF_SUBMISSION) == 300 :
        htmltext = "8518-2.htm"
      elif st.getQuestItemsCount(GEM_OF_SAINTS) :
        htmltext = "8518-4.htm"
   elif npcId == ENFEUX and st.getQuestItemsCount(SEAL_OF_LIGHT) :
     htmltext = "8519-0.htm"
   return htmltext

 def onKill (self,npc,st):
  count = st.getQuestItemsCount(GEM_OF_SUBMISSION)
  if int(st.get("cond")) == 1 and count < 300 :
     st.giveItems(GEM_OF_SUBMISSION,1)
     if count == 299 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else:
       st.playSound("ItemSound.quest_itemget")	
  return

QUEST       = Quest(627,"627_HeartInSearchOfPower","Heart In Search Of Power")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(8518)

CREATED.addTalkId(8518)
STARTED.addTalkId(8518)
STARTED.addTalkId(8519)

for mobs in range(1520,1541):
  STARTED.addKillId(mobs)

STARTED.addQuestDrop(1520,GEM_OF_SUBMISSION,1)
print "importing quests: 627: Heart In Search Of Power"
