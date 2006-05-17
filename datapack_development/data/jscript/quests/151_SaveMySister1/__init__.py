# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

POISON_SAC = 703
FEVER_MEDICINE = 704

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7050-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond = st.getInt("cond")
   sac = st.getQuestItemsCount(POISON_SAC)
   med = st.getQuestItemsCount(FEVER_MEDICINE)
   if npcId == 7050 :
      if id == COMPLETED :
        htmltext = "<html><head><body>This quest have already been completed.</body></html>"
      elif cond == 0 :
        if st.getPlayer().getLevel() >= 15 :
          htmltext = "7050-02.htm"
        else:
          htmltext = "7050-01.htm"
          st.exitQuest(1)
      elif cond == 1 and (sac == med == 0) :
        htmltext = "7050-04.htm"
      elif cond == 2 or sac :
        htmltext = "7050-05.htm"
      elif cond == 3 or med :
        st.giveItems(102,1)
        st.takeItems(FEVER_MEDICINE,1)
        htmltext = "7050-06.htm"
        st.unset("cond")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   elif npcId == 7032 :
      if cond == 2 or sac :
        st.set("cond","3")
        st.takeItems(POISON_SAC,1)
        st.giveItems(FEVER_MEDICINE,1)
        htmltext = "7032-01.htm"
      elif cond == 3 or med :
        htmltext = "7032-02.htm"
   return htmltext

 def onKill (self,npc,st):
   if not st.getQuestItemsCount(POISON_SAC) and st.getInt("cond") == 1 :
      if st.getRandom(5) == 0 :
         st.giveItems(POISON_SAC,1)
         st.playSound("ItemSound.quest_middle")
         st.set("cond","2")
   return

QUEST       = Quest(151,"151_SaveMySister1","Save My Sister1")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7050)

CREATED.addTalkId(7050)
STARTING.addTalkId(7050)
STARTED.addTalkId(7050)
COMPLETED.addTalkId(7050)

STARTED.addTalkId(7032)

for mob in [103,106,108] :
   STARTED.addKillId(mob)

STARTED.addQuestDrop(7032,FEVER_MEDICINE,1)
STARTED.addQuestDrop(108,POISON_SAC,1)

print "importing quests: 151: Save My Sister1"
