# Maked by Mr. Have fun! Version 0.2
print "importing quests: 331: Arrow For Vengeance"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HARPY_FEATHER_ID = 1452
MEDUSA_VENOM_ID = 1453
WYRMS_TOOTH_ID = 1454
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7125-03.htm"
    elif event == "7125_1" :
            htmltext = "7125-06.htm"
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    elif event == "7125_2" :
            htmltext = "7125-07.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7125 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 32 :
            htmltext = "7125-02.htm"
            return htmltext
          else:
            htmltext = "7125-01.htm"
        else:
          htmltext = "7125-01.htm"
   elif npcId == 7125 and int(st.get("cond"))==1 :
        if st.getQuestItemsCount(HARPY_FEATHER_ID)+st.getQuestItemsCount(MEDUSA_VENOM_ID)+st.getQuestItemsCount(WYRMS_TOOTH_ID)>0 :
          if int(st.get("id")) != 331 :
            st.set("id","331")
            st.giveItems(ADENA_ID,80*st.getQuestItemsCount(HARPY_FEATHER_ID)+90*st.getQuestItemsCount(MEDUSA_VENOM_ID)+100*st.getQuestItemsCount(WYRMS_TOOTH_ID))
            st.takeItems(HARPY_FEATHER_ID,st.getQuestItemsCount(HARPY_FEATHER_ID))
            st.takeItems(MEDUSA_VENOM_ID,st.getQuestItemsCount(MEDUSA_VENOM_ID))
            st.takeItems(WYRMS_TOOTH_ID,st.getQuestItemsCount(WYRMS_TOOTH_ID))
            htmltext = "7125-05.htm"
        else:
          htmltext = "7125-04.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 145 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n<5 :
          st.giveItems(HARPY_FEATHER_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 158 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n<5 :
          st.giveItems(MEDUSA_VENOM_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 176 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n<5 :
          st.giveItems(WYRMS_TOOTH_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(331,"331_ArrowForVengeance","Arrow For Vengeance")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7125)

STARTING.addTalkId(7125)

STARTED.addTalkId(7125)

STARTED.addKillId(145)
STARTED.addKillId(158)
STARTED.addKillId(176)

STARTED.addQuestDrop(145,HARPY_FEATHER_ID,1)
STARTED.addQuestDrop(158,MEDUSA_VENOM_ID,1)
STARTED.addQuestDrop(176,WYRMS_TOOTH_ID,1)
