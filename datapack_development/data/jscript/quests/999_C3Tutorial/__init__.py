# Maked by Mr. Have fun! Version 0.2
print "importing quests: 999: C3 Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLOOD_OF_JUNDIN_ID = 1070
LICENSE_OF_MINER_ID = 1498
VOUCHER_OF_FLAME_ID = 1496
SOULSHOOT_NOVICE_ID = 5789
BLUE_GEM_ID=6353

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7129_02" :
#      st.showRadar(2,28384,11056,-4233)
      htmltext = "7129-03.htm"
      if st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(BLOOD_OF_JUNDIN_ID,1)
        st.giveItems(SOULSHOOT_NOVICE_ID,200)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
    if event == "7528_02" :
#      st.showRadar(2,108567,-173994,-406)
      htmltext = "7573-03.htm"
      if st.getQuestItemsCount(LICENSE_OF_MINER_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(LICENSE_OF_MINER_ID,1)
        st.giveItems(SOULSHOOT_NOVICE_ID,200)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
    if event == "7573_02" :
#      st.showRadar(2,-56736,-113680,-672)
      htmltext = "7573-03.htm"
      if st.getQuestItemsCount(VOUCHER_OF_FLAME_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(VOUCHER_OF_FLAME_ID,1)
        st.giveItems(SOULSHOOT_NOVICE_ID,200)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have no tasks for you right now.</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel() < 10 :
#darkelf
     if st.getPlayer().getRace().ordinal() == 2 :
     	if npcId == 7129 :
          htmltext = "7129-01.htm"
	if npcId == 7131 :
	  if st.getPlayer().getClassId().getId() == 0x26 :
            htmltext = "7131-01.htm"
	  else:
            htmltext = "7530-01.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_tutorial")
#orc
     if st.getPlayer().getRace().ordinal() == 3 :
     	if npcId == 7573 :
          htmltext = "7573-01.htm"
	if npcId == 7575 :
          htmltext = "7575-01.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_tutorial")
#dwarf
     if st.getPlayer().getRace().ordinal() == 4 :
     	if npcId == 7528 :
          htmltext = "7528-01.htm"
	if npcId == 7530 :
          htmltext = "7530-01.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_tutorial")
   elif st.getPlayer().getLevel() >= 10 or int(st.get("onlyone")):
	if npcId == 7530 or npcId == 7575 :
          htmltext = "7575-05.htm"
#dwarf
   elif npcId == 7530 and int(st.get("cond"))==1 and st.getQuestItemsCount(LICENSE_OF_MINER_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(LICENSE_OF_MINER_ID,1)
          st.giveItems(SOULSHOOT_NOVICE_ID,200)
          htmltext = "7530-03.htm"
          st.set("cond","2")
      else :
          htmltext = "7530-02.htm"
#orc
   elif npcId == 7575 and int(st.get("cond"))==1 and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(VOUCHER_OF_FLAME_ID,1)
          st.giveItems(SOULSHOOT_NOVICE_ID,200)
          htmltext = "7575-03.htm"
          st.set("cond","2")
      else :
          htmltext = "7575-02.htm"
#darkelf
   elif npcId == 7131 and int(st.get("cond"))==1 and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(BLOOD_OF_JUNDIN_ID,1)
          st.giveItems(SOULSHOOT_NOVICE_ID,200)
          htmltext = "7131-03.htm"
          st.set("cond","2")
      else :
	  if st.getPlayer().getClassId().getId() == 0x26 :
            htmltext = "7131-02.htm"
	  else:
            htmltext = "7530-02.htm"

#dwarf
   elif npcId == 7530 and int(st.get("cond"))==2 and st.getQuestItemsCount(LICENSE_OF_MINER_ID) :
      htmltext = "7530-04.htm"
   elif npcId == 7528 and  int(st.get("cond"))==1 :
      htmltext = "7528-01.htm"
   elif npcId == 7528 and  int(st.get("cond"))==2 and st.getQuestItemsCount(LICENSE_OF_MINER_ID) :
      htmltext = "7528-02.htm"
   elif npcId == 7528 and  int(st.get("cond"))==3 :
      htmltext = "7528-04.htm"
#orc
   elif npcId == 7575 and int(st.get("cond"))==2 and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID) :
      htmltext = "7575-04.htm"
   elif npcId == 7573 and  int(st.get("cond"))==1 :
      htmltext = "7573-01.htm"
   elif npcId == 7573 and  int(st.get("cond"))==2 and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID) :
      htmltext = "7573-02.htm"
   elif npcId == 7573 and  int(st.get("cond"))==3 :
      htmltext = "7573-04.htm"
#darkelf
   elif npcId == 7131 and int(st.get("cond"))==2 and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) :
      htmltext = "7131-04.htm"
   elif npcId == 7131 and  int(st.get("cond"))==1 :
      htmltext = "7131-01.htm"
   elif npcId == 7129 and  int(st.get("cond"))==2 and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) :
      htmltext = "7129-02.htm"
   elif npcId == 7129 and  int(st.get("cond"))==3 :
      htmltext = "7129-04.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5198 :
      if int(st.get("cond"))==1 and st.getRandom(100) < 50 :
        if st.getQuestItemsCount(BLUE_GEM_ID) == 0 :
            st.giveItems(BLUE_GEM_ID,1)
            st.playSound("ItemSound.quest_itemget")
            st.playSound("ItemSound.quest_tutorial")
   return

QUEST       = Quest(999,"999_C3Tutorial","C3 Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7129)
QUEST.addStartNpc(7131)
QUEST.addStartNpc(7528)
QUEST.addStartNpc(7530)
QUEST.addStartNpc(7573)
QUEST.addStartNpc(7575)

STARTED.addTalkId(7129)
STARTED.addTalkId(7131)
STARTED.addTalkId(7528)
STARTED.addTalkId(7530)
STARTED.addTalkId(7573)
STARTED.addTalkId(7575)

STARTED.addKillId(5198)

STARTED.addQuestDrop(5198,BLUE_CRYSTAL,1)
STARTED.addQuestDrop(7131,BLOOD_OF_JUNDIN_ID,1)
STARTED.addQuestDrop(7530,LICENSE_OF_MINER_ID,1)
STARTED.addQuestDrop(7575,VOUCHER_OF_FLAME_ID,1)
