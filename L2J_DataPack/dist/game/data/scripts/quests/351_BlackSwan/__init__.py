# Made by mtrix
import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest

qn = "351_BlackSwan"

ADENA = 57
ORDER_OF_GOSTA = 4296
LIZARD_FANG = 4297
BARREL_OF_LEAGUE = 4298
BILL_OF_IASON_HEINE = 4310
CHANCE = 10
CHANCE2 = 15
CHANCE_barrel = 0
class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [ORDER_OF_GOSTA, BARREL_OF_LEAGUE, LIZARD_FANG]

 def onEvent (self,event,st) :
     htmltext = event
     amount = st.getQuestItemsCount(LIZARD_FANG)
     amount2 = st.getQuestItemsCount(BARREL_OF_LEAGUE)
     bonus=0
     if event == "30916-03.htm" :
         st.setState(State.STARTED)
         st.set("cond","1")
         st.giveItems(ORDER_OF_GOSTA,1)
         st.playSound("ItemSound.quest_accept")
     elif event == "30969-02a.htm" and amount :
         if amount > 10 :
            bonus=3880
         htmltext = "30969-02.htm"
         st.giveItems(ADENA,amount*20+bonus)
         st.takeItems(LIZARD_FANG,-1)
     elif event == "30969-03a.htm" :
         if amount2 :
             htmltext = "30969-03.htm"
             st.giveItems(ADENA,3880)
             st.rewardItems(BILL_OF_IASON_HEINE,int(amount2))
             st.takeItems(BARREL_OF_LEAGUE,-1)
     elif event == "30969-06.htm" :
         if not (amount + amount2) :
            st.exitQuest(1)
            st.playSound("ItemSound.quest_finish")
            htmltext="30969-07.htm"
     return htmltext

 def onTalk (self,npc,player):
     htmltext = Quest.getNoQuestMsg(player)
     st = player.getQuestState(qn)
     if not st : return htmltext
     npcId = npc.getId()
     id = st.getState()
     if npcId != 30916 and id != State.STARTED : return htmltext
  
     level = player.getLevel()
     cond = st.getInt("cond")
     if npcId==30916 :
         if id == State.CREATED :
             if level>=32 :
                 htmltext = "30916-01.htm"
             else :
                 htmltext = "30916-00.htm"
                 st.exitQuest(1)
         elif cond :
             htmltext = "30916-04.htm"
     elif npcId==30969 and cond :
         htmltext = "30969-01.htm"
     elif npcId == 30897 :
         if st.getQuestItemsCount(BILL_OF_IASON_HEINE):
            htmltext="30897-01.htm"
         else:
            htmltext="30897-02.htm"
     return htmltext

 def onKill(self,npc,player,isPet):
     st = player.getQuestState(qn)
     if not st : return 
     if st.getState() != State.STARTED : return 
     npcId = npc.getId()
     cond = st.getInt("cond")
     random = self.getRandom(20)
     if random<CHANCE :
         st.giveItems(LIZARD_FANG,1)
         st.playSound("ItemSound.quest_itemget")
         if random==CHANCE_barrel :
              st.giveItems(BARREL_OF_LEAGUE,1)
              st.set("cond","2")
     elif random<CHANCE2 :
         st.giveItems(LIZARD_FANG,2)
         st.playSound("ItemSound.quest_itemget")
         if random==CHANCE_barrel :
             st.giveItems(BARREL_OF_LEAGUE,1)
             st.set("cond","2")
     elif self.getRandom(100)<4:
         st.giveItems(BARREL_OF_LEAGUE,1)
         st.playSound("ItemSound.quest_itemget")
         st.set("cond","2")
     return

QUEST       = Quest(351,qn,"Black Swan")

QUEST.addStartNpc(30916)

QUEST.addTalkId(30916)
QUEST.addTalkId(30969)
QUEST.addTalkId(30897)

QUEST.addKillId(20784)
QUEST.addKillId(20785)
QUEST.addKillId(21639)
QUEST.addKillId(21640)
QUEST.addKillId(21642)
QUEST.addKillId(21643)