# Made by Kerberos
# this script is part of the Official L2J Datapack Project.
# Visit http://forum.l2jdp.com for more details.
import sys
from net.sf.l2j.gameserver.instancemanager import QuestManager
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "141_ShadowFoxPart3"

# NPCs
NATOOLS = 30894

# ITEMs
REPORT = 10350

# MONSTERs
NPC=[20791,20792,20135]

class Quest (JQuest) :

 def __init__(self,id,name,descr):
    JQuest.__init__(self,id,name,descr)
    self.questItemIds = [REPORT]

 def onEvent (self,event,st) :
    htmltext = event
    id = st.getState()
    cond = st.getInt("cond")
    if event == "30894-02.htm" :
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
    elif event == "30894-04.htm" :
       st.set("cond","2")
       st.playSound("ItemSound.quest_middle")
    elif event == "30894-15.htm" :
       st.set("cond","4")
       st.unset("talk")
       st.playSound("ItemSound.quest_middle")
    elif event == "30894-18.htm" :
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(False)
       st.giveItems(57, 88888)
       if st.getPlayer().getLevel() >= 37 and st.getPlayer().getLevel() <= 42:
          st.addExpAndSp(219975,13047)
    elif event == "AngelSelect" :
       qs = player.getQuestState("998_FallenAngelSelect")
       if qs:
          qs.getQuest().onEvent(qs.getQuest(), "30894-01.htm", qs)
          return
    return htmltext

 def onTalk (self,npc,player):
    htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
    st = player.getQuestState(qn)
    if not st : return htmltext

    npcId = npc.getNpcId()
    id = st.getState()
    cond = st.getInt("cond")
    if id == State.CREATED : return htmltext
    if id == State.COMPLETED :
       htmltext = "<html><body>This quest has already been completed.</body></html>"
    elif npcId == NATOOLS :
       if cond == 0 :
          if player.getLevel() >= 37:
             htmltext = "30894-01.htm"
          else:
             htmltext = "30894-00.htm"
             st.exitQuest(1)
       elif cond == 1 :
          htmltext = "30894-02.htm"
       elif cond == 2 :
          htmltext = "30894-05.htm"
       elif cond == 3 :
          if st.getInt("talk"):
             htmltext = "30894-07.htm"
          else:
             htmltext = "30894-06.htm"
             st.takeItems(REPORT, -1)
             st.set("talk","1")
       elif cond == 4 :
          htmltext = "30894-16.htm"
    return htmltext

 def onKill(self,npc,player,isPet):
    st = player.getQuestState(qn)
    if not st : return
    if st.getState() != State.STARTED : return
    if st.getInt("cond")==2 and st.getRandom(100) <= 80 and st.getQuestItemsCount(REPORT)<30:
       st.giveItems(REPORT,1)
       if st.getQuestItemsCount(REPORT)>=30:
          st.set("cond","3")
          st.playSound("ItemSound.quest_middle")
       else:
          st.playSound("ItemSound.quest_itemget")
    return

 def onFirstTalk (self,npc,player):
   st = player.getQuestState(qn)
   if not st :
      st = self.newQuestState(player)
   qs = player.getQuestState("140_ShadowFoxPart2")
   qs2 = player.getQuestState("998_FallenAngelSelect")
   qs3 = player.getQuestState("142_FallenAngelRequestOfDawn")
   qs4 = player.getQuestState("143_FallenAngelRequestOfDusk")
   if qs :
      if qs.getState() == State.COMPLETED :
         if st.getState() == State.CREATED :
            st.setState(State.STARTED)
   if st.getState() == State.COMPLETED and player.getLevel() >= 38:
      if not qs2 :
         q = QuestManager.getInstance().getQuest("998_FallenAngelSelect")
         if q :
            qs2 = q.newQuestState(player)
            qs2.setState(State.STARTED)
      if qs2 :
         if qs2.getState() == State.COMPLETED :
            qs2.setState(State.CREATED)
         if qs2.getState() == State.CREATED :
            if not qs3 and not qs4:
               qs2.setState(State.STARTED)
   npc.showChatWindow(player)
   return

QUEST       = Quest(141,qn,"Shadow Fox - 3")

QUEST.addFirstTalkId(NATOOLS) #this quest doesnt have starter npc, quest will appear in list only when u finish quest 140
QUEST.addTalkId(NATOOLS)
for mob in NPC :
   QUEST.addKillId(mob)