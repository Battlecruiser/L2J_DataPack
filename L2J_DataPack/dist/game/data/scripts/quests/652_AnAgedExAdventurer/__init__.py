# Made by Kerb
import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "652_AnAgedExAdventurer"
#Npc
TANTAN = 32012
SARA = 30180

#Items
CSS = 1464

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onAdvEvent (self,event,npc,player) :
    st = player.getQuestState(qn)
    if not st: return
    htmltext = event
    if event == "32012-02.htm" :
      if st.getQuestItemsCount(CSS) > 99 :
        st.set("cond","1")
        st.setState(State.STARTED)
        st.playSound("ItemSound.quest_accept")
        st.takeItems(CSS,100)
        htmltext = "32012-03.htm"
        npc.deleteMe()
    elif event == "32012-02a.htm" :
        st.exitQuest(1)
        st.playSound("ItemSound.quest_giveup")
    return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   htmltext = Quest.getNoQuestMsg(player)
   if not st : return htmltext
   npcId = npc.getNpcId()
   id = st.getState()
   cond=st.getInt("cond")
   if npcId == TANTAN and id == State.CREATED:
       if st.getPlayer().getLevel() >= 46 :
           htmltext = "32012-01.htm"
       else:
           htmltext = "32012-00.htm"
           st.exitQuest(1)
   elif npcId == SARA and st.getInt("cond") :
       htmltext = "30180-01.htm"
       EAD_CHANCE = self.getRandom(100)
       st.giveItems(57,5026)
       if EAD_CHANCE <= 50:
          st.rewardItems(956,1)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
   return htmltext

QUEST       = Quest(652,qn,"AnAgedExAdventurer")

QUEST.addStartNpc(TANTAN)

QUEST.addTalkId(TANTAN)
QUEST.addTalkId(SARA)