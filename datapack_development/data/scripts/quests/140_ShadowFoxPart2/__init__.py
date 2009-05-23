# Made by Kerberos
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum/ for more details.
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "140_ShadowFoxPart2"

# NPCs
KLUCK = 30895
XENOVIA = 30912

# ITEMs
CRYSTAL = 10347
OXYDE = 10348
CRYPT = 10349

# MONSTERs
NPC=[20789,20790,20791,20792]

class Quest (JQuest) :

 def __init__(self,id,name,descr):
    JQuest.__init__(self,id,name,descr)
    self.questItemIds = [CRYSTAL,OXYDE,CRYPT]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    id = st.getState()
    cond = st.getInt("cond")
    if event == "30895-02.htm" :
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
    elif event == "30895-05.htm" :
       st.set("cond","2")
       st.playSound("ItemSound.quest_middle")
    elif event == "30895-09.htm" :
       st.playSound("ItemSound.quest_finish")
       st.unset("talk")
       st.exitQuest(False)
       st.giveItems(57, 18775)
       if st.getPlayer().getLevel() >= 37 and st.getPlayer().getLevel() <= 42:
          st.addExpAndSp(30000,2000)
    elif event == "30912-07.htm" :
       st.set("cond","3")
       st.playSound("ItemSound.quest_middle")
    elif event == "30912-09.htm" :
       st.takeItems(CRYSTAL, 5)
       if st.getRandom(100) <= 60 :
          st.giveItems(OXYDE,1)
          if st.getQuestItemsCount(OXYDE) >= 3 :
             htmltext = "30912-09b.htm"
             st.set("cond","4")
             st.playSound("ItemSound.quest_middle")
             st.takeItems(CRYSTAL, -1)
             st.takeItems(OXYDE, -1)
             st.giveItems(CRYPT,1)
       else:
          htmltext = "30912-09a.htm"
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
    elif npcId == KLUCK :
       if cond == 0 :
          if player.getLevel() >= 37:
             htmltext = "30895-01.htm"
          else:
             htmltext = "30895-00.htm"
             st.exitQuest(1)
       elif cond == 1 :
          htmltext = "30895-02.htm"
       elif cond in [2,3] :
          htmltext = "30895-06.htm"
       elif cond == 4 :
          if st.getInt("talk"):
             htmltext = "30895-08.htm"
          else:
             htmltext = "30895-07.htm"
             st.takeItems(CRYPT, -1)
             st.set("talk","1")
    elif npcId == XENOVIA :
       if cond == 2 :
          htmltext = "30912-01.htm"
       elif cond == 3 :
          if st.getQuestItemsCount(CRYSTAL) >= 5 :
             htmltext = "30912-08.htm"
          else:
             htmltext = "30912-07.htm"
       elif cond == 4 :
          htmltext = "30912-10.htm"
    return htmltext

 def onKill(self,npc,player,isPet):
    st = player.getQuestState(qn)
    if not st : return
    if st.getState() != State.STARTED : return
    if st.getInt("cond")==3 and st.getRandom(100) <= 80 :
       st.playSound("ItemSound.quest_itemget")
       st.giveItems(CRYSTAL,1)
    return

 def onFirstTalk (self,npc,player):
   st = player.getQuestState(qn)
   if not st :
      st = self.newQuestState(player)
   qs = st.getPlayer().getQuestState("139_ShadowFoxPart1")
   if qs :
      if qs.getState() == State.COMPLETED and st.getState() == State.CREATED :
          st.setState(State.STARTED)
   npc.showChatWindow(player)
   return

QUEST       = Quest(140,qn,"Shadow Fox - 2")

QUEST.addFirstTalkId(KLUCK) #this quest doesnt have starter npc, quest will appear in list only when u finish quest 139
QUEST.addTalkId(KLUCK)
QUEST.addTalkId(XENOVIA)
for mob in NPC :
   QUEST.addKillId(mob)