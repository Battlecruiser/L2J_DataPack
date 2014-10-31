# Made by mtrix
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "351_BlackSwan"

ADENA = 57
ORDER_OF_GOSTA = 4296
LIZARD_FANG = 4297
BARREL_OF_LEAGUE = 4298
BILL_OF_IASON_HEINE = 4310
CHANCE = 80
CHANCE2 = 7
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     amount = st.getQuestItemsCount(LIZARD_FANG)
     amount2 = st.getQuestItemsCount(BARREL_OF_LEAGUE)
     if event == "30916-03.htm" :
         st.setState(STARTED)
         st.set("cond","1")
         st.giveItems(ORDER_OF_GOSTA,1)
         st.playSound("ItemSound.quest_accept")
     elif event == "30969-02a.htm" :
         if amount:
             htmltext = "30969-02.htm"
             st.giveItems(ADENA,amount*20)
             st.takeItems(LIZARD_FANG,-1)
     elif event == "30969-03a.htm" :
         if amount2 :
             htmltext = "30969-03.htm"
             st.giveItems(ADENA,amount2*3880)
             st.giveItems(BILL_OF_IASON_HEINE,int(amount2*Config.RATE_QUESTS_REWARD))
             st.takeItems(BARREL_OF_LEAGUE,-1)
     elif event == "30969-06.htm" :
         if not (amount + amount2) :
            st.exitQuest(1)
            st.playSound("ItemSound.quest_finish")
            htmltext="30969-07.htm"
     return htmltext

 def onTalk (self,npc,player):
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     st = player.getQuestState(qn)
     if not st : return htmltext
     npcId = npc.getNpcId()
     id = st.getState()
     if npcId != 30916 and id != STARTED : return htmltext
  
     level = st.getPlayer().getLevel()
     cond = st.getInt("cond")
     if npcId==30916 :
         if id == CREATED :
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

 def onKill (self,npc,player):
     st = player.getQuestState(qn)
     if not st : return 
     if st.getState() != STARTED : return 
     npcId = npc.getNpcId()
     cond = st.getInt("cond")
     random = st.getRandom(100)
     if random<=CHANCE :
         st.giveItems(LIZARD_FANG,st.getRandom(5)+1)
         st.playSound("ItemSound.quest_itemget")
     if random<=CHANCE2 :
         st.giveItems(BARREL_OF_LEAGUE,1)
         st.set("cond","2")
     return

QUEST       = Quest(351,qn,"Black Swan")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30916)

QUEST.addTalkId(30916)
QUEST.addTalkId(30969)
QUEST.addTalkId(30897)

STARTED.addQuestDrop(30916,ORDER_OF_GOSTA,1)
STARTED.addQuestDrop(30916,BARREL_OF_LEAGUE,1)
STARTED.addQuestDrop(30916,LIZARD_FANG,1)

QUEST.addKillId(20784)
QUEST.addKillId(20785)
QUEST.addKillId(21639)
QUEST.addKillId(21640)
QUEST.addKillId(21642)
QUEST.addKillId(21643)

print "importing quests: 351: Black Swan"
