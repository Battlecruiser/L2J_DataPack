# Maked by Mr. Have fun! Version 0.2
print "importing quests: 316: Destroy Plaguebringers"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WERERAT_FANG_ID = 1042
VAROOL_FOULCLAWS_FANG_ID = 1043
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7155-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    elif event == "316_2" :
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          htmltext = "7155-08.htm"
    elif event == "316_3" :
          htmltext = "7155-09.htm"
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7155 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 1 :
          htmltext = "7155-00.htm"
          st.exitQuest(1)
        elif st.getPlayer().getLevel() >= 18 :
          htmltext = "7155-03.htm"
          return htmltext
        else:
          htmltext = "7155-02.htm"
          st.exitQuest(1)
      else:
        htmltext = "7155-02.htm"
        st.exitQuest(1)
   elif npcId == 7155 and int(st.get("cond")) :
      if st.getQuestItemsCount(WERERAT_FANG_ID) >= 1 or st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG_ID) >= 1 :
        if int(st.get("id")) != 316 :
          st.set("id","316")
          htmltext = "7155-07.htm"
          st.giveItems(ADENA_ID,st.getQuestItemsCount(WERERAT_FANG_ID)*60+st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG_ID)*10000)
          st.takeItems(WERERAT_FANG_ID,st.getQuestItemsCount(WERERAT_FANG_ID))
          st.takeItems(VAROOL_FOULCLAWS_FANG_ID,st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG_ID))
      else:
        htmltext = "7155-05.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 40 :
        st.set("id","0")
        if st.getRandom(10)>5 and int(st.get("cond")) == 1 :
          st.giveItems(WERERAT_FANG_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 47 :
        st.set("id","0")
        if st.getRandom(10)>5 and int(st.get("cond")) == 1 :
          st.giveItems(WERERAT_FANG_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 5020 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG_ID) == 0 :
          if st.getRandom(10)>7 :
            st.giveItems(VAROOL_FOULCLAWS_FANG_ID,1)
            st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(316,"316_DestroyPlaguebringers","Destroy Plaguebringers")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7155)

STARTING.addTalkId(7155)

STARTED.addTalkId(7155)

STARTED.addKillId(40)
STARTED.addKillId(47)
STARTED.addKillId(5020)

STARTED.addQuestDrop(40,WERERAT_FANG_ID,1)
STARTED.addQuestDrop(47,WERERAT_FANG_ID,1)
STARTED.addQuestDrop(5020,VAROOL_FOULCLAWS_FANG_ID,1)
