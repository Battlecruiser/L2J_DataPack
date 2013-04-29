# Made by L2Emu Team
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest
from com.l2jserver.util import Rnd

qn = "40_ASpecialOrder"

# NPC
HELVETIA = 30081
OFULLE = 31572
GESTO = 30511

# Items
OrangeNimbleFish,OrangeUglyFish,OrangeFatFish,FishChest = 6450,6451,6452,12764
GoldenCobol,ThornCobol,GreatCobol,SeedJar = 5079,5082,5084,12765
WondrousCubic = 10632

class Quest (JQuest) :

 def __init__(self,id,name,descr): 
    JQuest.__init__(self,id,name,descr)
    self.questItemIds = [12764,12765]

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30081-02.htm" :
      st.set("cond","1")
      condition = Rnd.get(1,2)
      if condition == 1 :
        st.set("cond","2")
        htmltext = "30081-02a.htm"
      else :
        st.set("cond","5")
        htmltext = "30081-02b.htm"
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "30511-03.htm" :
      st.set("cond","6")
      st.playSound("ItemSound.quest_middle")
    elif event == "31572-03.htm" :
      st.set("cond","3")
      st.playSound("ItemSound.quest_middle")
    elif event == "30081-05a.htm" :
      st.takeItems(FishChest,1)
      st.giveItems(WondrousCubic,1)
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(False)
    elif event == "30081-05b.htm" :
      st.takeItems(SeedJar,1)
      st.giveItems(WondrousCubic,1)
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(False)
    return htmltext

 def onTalk (self,npc,player) :
    htmltext = Quest.getNoQuestMsg(player)
    st = player.getQuestState(qn)
    if not st : return htmltext

    npcId = npc.getNpcId()
    id = st.getState()
    cond = st.getInt("cond")
    if id == State.COMPLETED :
      htmltext = Quest.getAlreadyCompletedMsg(player)
    elif npcId == HELVETIA :
      if cond == 0 :
        if player.getLevel() >= 40 :
          htmltext = "30081-01.htm"
        else :
          htmltext = "30081-00.htm"
          st.exitQuest(1)
      elif cond == 2 or cond == 3 :
        htmltext = "30081-03a.htm"
      elif cond == 4 :
        htmltext = "30081-04a.htm"
      elif cond == 5 or cond == 6 :
        htmltext = "30081-03b.htm"
      elif cond == 7 :
        htmltext = "30081-04b.htm"
    elif npcId == OFULLE :
      if cond == 2 :
        htmltext = "31572-01.htm"
      elif cond == 3 :
        if st.getQuestItemsCount(OrangeNimbleFish) >= 10 and st.getQuestItemsCount(OrangeUglyFish) >= 10 and st.getQuestItemsCount(OrangeFatFish) >= 10 :
          st.set("cond","4")
          st.takeItems(OrangeNimbleFish,10)
          st.takeItems(OrangeUglyFish,10)
          st.takeItems(OrangeFatFish,10)
          st.playSound("ItemSound.quest_middle")
          st.giveItems(FishChest,1)
          htmltext = "31572-04.htm"
        else :
          htmltext = "31572-05.htm"
      elif cond == 4 :
        htmltext = "31572-06.htm"
    elif npcId == GESTO :
      if cond == 5 :
        htmltext = "30511-01.htm"
      elif cond == 6 :
        if st.getQuestItemsCount(GoldenCobol) >= 40 and st.getQuestItemsCount(ThornCobol) >= 40 and st.getQuestItemsCount(GreatCobol) >= 40 :
          st.set("cond","7")
          st.takeItems(GoldenCobol,40)
          st.takeItems(ThornCobol,40)
          st.takeItems(GreatCobol,40)
          st.playSound("ItemSound.quest_middle")
          st.giveItems(SeedJar,1)
          htmltext = "30511-04.htm"
        else :
          htmltext = "30511-05.htm"
      elif cond == 7 :
        htmltext = "30511-06.htm"
    return htmltext

QUEST = Quest(40,qn,"A Special Order")

QUEST.addStartNpc(HELVETIA)

QUEST.addTalkId(HELVETIA)
QUEST.addTalkId(OFULLE)
QUEST.addTalkId(GESTO)