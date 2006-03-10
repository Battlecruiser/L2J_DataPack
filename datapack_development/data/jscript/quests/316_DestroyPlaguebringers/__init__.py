# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WERERAT_FANG = 1042
VAROOL_FOULCLAWS_FANG = 1043
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7155-04.htm" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    elif event == "7155-08.htm" :
        st.exitQuest(1)
        st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if int(st.get("cond"))==0 :
     if st.getPlayer().getRace().ordinal() != 1 :
       htmltext = "7155-00.htm"
       st.exitQuest(1)
     elif st.getPlayer().getLevel() >= 18 :
       htmltext = "7155-03.htm"
     else:
       htmltext = "7155-02.htm"
       st.exitQuest(1)
   else :
     rats=st.getQuestItemsCount(WERERAT_FANG)
     varool=st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG)
     if rats or varool :
       htmltext = "7155-07.htm"
       st.giveItems(ADENA,rats*60+varool*10000)
       st.takeItems(WERERAT_FANG,-1)
       st.takeItems(VAROOL_FOULCLAWS_FANG,-1)
     else:
       htmltext = "7155-05.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 5020 :
     if st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG) == 0 and st.getRandom(10)>7:
       st.giveItems(VAROOL_FOULCLAWS_FANG,1)
       st.playSound("ItemSound.quest_middle")
   elif st.getRandom(10)>5 :
     st.giveItems(WERERAT_FANG,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(316,"316_DestroyPlaguebringers","Destroy Plaguebringers")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7155)

CREATED.addTalkId(7155)
STARTING.addTalkId(7155)
STARTED.addTalkId(7155)
COMPLETED.addTalkId(7155)

STARTED.addKillId(40)
STARTED.addKillId(47)
STARTED.addKillId(5020)

STARTED.addQuestDrop(40,WERERAT_FANG,1)
STARTED.addQuestDrop(5020,VAROOL_FOULCLAWS_FANG,1)

print "importing quests: 316: Destroy Plaguebringers"
