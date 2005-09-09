# Maked by Mr. Have fun! Version 0.2
print "importing quests: 999: C3 Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RECOMMENDATION_01_ID = 1067
RECOMMENDATION_02_ID = 1068
LEAF_OF_MOTHERTREE_ID = 1069
BLOOD_OF_JUNDIN_ID = 1070
LICENSE_OF_MINER_ID = 1498
VOUCHER_OF_FLAME_ID = 1496
SOULSHOT_NOVICE_ID = 5789
SPIRITSHOT_NOVICE_ID = 5790
BLUE_GEM_ID=6353

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
#humanfight
    if event == "7008_02" :
#      st.showRadar(2,-71424,258336,-3109)
      htmltext = "7008-03.htm"
      if st.getQuestItemsCount(RECOMMENDATION_01_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(RECOMMENDATION_01_ID,1)
        st.giveItems(SOULSHOT_NOVICE_ID,200)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
#humanmage
    if event == "7017_02" :
#      st.showRadar(2,-91036,248044,-3568)
      htmltext = "7017-03.htm"
      if st.getQuestItemsCount(RECOMMENDATION_02_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(RECOMMENDATION_02_ID,1)
        st.giveItems(SPIRITSHOT_NOVICE_ID,100)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")

#elf
    if event == "7370_02" :
#      st.showRadar(2,46112,41200,-3504)
      htmltext = "7370-03.htm"
      if st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(LEAF_OF_MOTHERTREE_ID,1)
	if st.getPlayer().getClassId().getId() == 0x19 :
          st.giveItems(SPIRITSHOT_NOVICE_ID,100)
	else :
          st.giveItems(SOULSHOT_NOVICE_ID,200)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
#darkelf
    if event == "7129_02" :
#      st.showRadar(2,28384,11056,-4233)
      htmltext = "7129-03.htm"
      if st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(BLOOD_OF_JUNDIN_ID,1)
	if st.getPlayer().getClassId().getId() == 0x26 :
          st.giveItems(SPIRITSHOT_NOVICE_ID,100)
	else :
          st.giveItems(SOULSHOT_NOVICE_ID,200)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
#dwarf
    if event == "7528_02" :
#      st.showRadar(2,108567,-173994,-406)
      htmltext = "7573-03.htm"
      if st.getQuestItemsCount(LICENSE_OF_MINER_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(LICENSE_OF_MINER_ID,1)
        st.giveItems(SOULSHOT_NOVICE_ID,200)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
#orc
    if event == "7573_02" :
#      st.showRadar(2,-56736,-113680,-672)
      htmltext = "7573-03.htm"
      if st.getQuestItemsCount(VOUCHER_OF_FLAME_ID) and int(st.get("onlyone")) == 0 :
        st.addExpAndSp(0,50)
        st.takeItems(VOUCHER_OF_FLAME_ID,1)
        st.giveItems(SOULSHOT_NOVICE_ID,200)
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
#humanmage
     if st.getPlayer().getClassId().getId() == 0x0a :
     	if npcId == 7017 :
          htmltext = "7017-01.htm"
	if npcId == 7019 :
          htmltext = "7131-01.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_tutorial")
#humanfight
     if st.getPlayer().getClassId().getId() == 0x00 :
     	if npcId == 7008 :
          htmltext = "7008-01.htm"
	if npcId == 7009 :
          htmltext = "7530-01.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_tutorial")
#elf
     if st.getPlayer().getRace().ordinal() == 1 :
     	if npcId == 7370 :
          htmltext = "7370-01.htm"
	if npcId == 7400 :
	  if st.getPlayer().getClassId().getId() == 0x19 :
            htmltext = "7131-01.htm"
	  else:
            htmltext = "7530-01.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_tutorial")
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
	if npcId == 7009 or npcId == 7019 or npcId == 7317 or npcId == 7400 or npcId == 7575 :
          htmltext = "7575-05.htm"
#dwarf
   elif npcId == 7530 and int(st.get("cond"))==1 and st.getQuestItemsCount(LICENSE_OF_MINER_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(LICENSE_OF_MINER_ID,1)
          st.giveItems(SOULSHOT_NOVICE_ID,200)
          htmltext = "7530-03.htm"
          st.set("cond","2")
      else :
          htmltext = "7530-02.htm"
#orc
   elif npcId == 7575 and int(st.get("cond"))==1 and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(VOUCHER_OF_FLAME_ID,1)
          st.giveItems(SOULSHOT_NOVICE_ID,200)
          htmltext = "7575-03.htm"
          st.set("cond","2")
      else :
          htmltext = "7575-02.htm"
#darkelf
   elif npcId == 7131 and int(st.get("cond"))==1 and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(BLOOD_OF_JUNDIN_ID,1)
	  if st.getPlayer().getClassId().getId() == 0x26 :
            st.giveItems(SPIRITSHOT_NOVICE_ID,100)
            htmltext = "7131-03.htm"
	  else :
            st.giveItems(SOULSHOT_NOVICE_ID,200)
            htmltext = "7131-03a.htm"
          st.set("cond","2")
      else :
	  if st.getPlayer().getClassId().getId() == 0x26 :
            htmltext = "7131-02.htm"
	  else:
            htmltext = "7530-02.htm"
#elf
   elif npcId == 7400 and int(st.get("cond"))==1 and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(LEAF_OF_MOTHERTREE_ID,1)
	  if st.getPlayer().getClassId().getId() == 0x19 :
            st.giveItems(SPIRITSHOT_NOVICE_ID,100)
            htmltext = "7400-03.htm"
	  else :
            st.giveItems(SOULSHOT_NOVICE_ID,200)
            htmltext = "7400-03a.htm"
          st.set("cond","2")
      else :
	  if st.getPlayer().getClassId().getId() == 0x19 :
            htmltext = "7131-02.htm"
	  else:
            htmltext = "7530-02.htm"
#humanmage
   elif npcId == 7019 and int(st.get("cond"))==1 and st.getQuestItemsCount(RECOMMENDATION_02_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(RECOMMENDATION_02_ID,1)
          st.giveItems(SPIRITSHOT_NOVICE_ID,100)
          htmltext = "7019-03.htm"
          st.set("cond","2")
      else :
          htmltext = "7131-02.htm"
#humanfight
   elif npcId == 7009 and int(st.get("cond"))==1 and st.getQuestItemsCount(RECOMMENDATION_01_ID)==0 :
      if st.getQuestItemsCount(BLUE_GEM_ID) :
          st.takeItems(BLUE_GEM_ID,st.getQuestItemsCount(BLUE_GEM_ID))
          st.giveItems(RECOMMENDATION_01_ID,1)
          st.giveItems(SOULSHOT_NOVICE_ID,200)
          htmltext = "7009-03.htm"
          st.set("cond","2")
      else :
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
   elif npcId == 7129 and  int(st.get("cond"))==1 :
      htmltext = "7129-01.htm"
   elif npcId == 7129 and  int(st.get("cond"))==2 and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) :
      htmltext = "7129-02.htm"
   elif npcId == 7129 and  int(st.get("cond"))==3 :
      htmltext = "7129-04.htm"
#elf
   elif npcId == 7400 and int(st.get("cond"))==2 and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) :
      htmltext = "7400-04.htm"
   elif npcId == 7370 and  int(st.get("cond"))==1 :
      htmltext = "7370-01.htm"
   elif npcId == 7370 and  int(st.get("cond"))==2 and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) :
      htmltext = "7370-02.htm"
   elif npcId == 7370 and  int(st.get("cond"))==3 :
      htmltext = "7370-04.htm"
#humanmage
   elif npcId == 7019 and int(st.get("cond"))==2 and st.getQuestItemsCount(RECOMMENDATION_02_ID) :
      htmltext = "7019-04.htm"
   elif npcId == 7017 and  int(st.get("cond"))==1 :
      htmltext = "7017-01.htm"
   elif npcId == 7017 and  int(st.get("cond"))==2 and st.getQuestItemsCount(RECOMMENDATION_02_ID) :
      htmltext = "7017-02.htm"
   elif npcId == 7017 and  int(st.get("cond"))==3 :
      htmltext = "7017-04.htm"
#humanmage
   elif npcId == 7009 and int(st.get("cond"))==2 and st.getQuestItemsCount(RECOMMENDATION_01_ID) :
      htmltext = "7009-04.htm"
   elif npcId == 7008 and  int(st.get("cond"))==1 :
      htmltext = "7008-01.htm"
   elif npcId == 7008 and  int(st.get("cond"))==2 and st.getQuestItemsCount(RECOMMENDATION_01_ID) :
      htmltext = "7008-02.htm"
   elif npcId == 7008 and  int(st.get("cond"))==3 :
      htmltext = "7008-04.htm"
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
QUEST.addStartNpc(7008)
QUEST.addStartNpc(7009)
QUEST.addStartNpc(7017)
QUEST.addStartNpc(7019)
QUEST.addStartNpc(7129)
QUEST.addStartNpc(7131)
QUEST.addStartNpc(7370)
QUEST.addStartNpc(7400)
QUEST.addStartNpc(7528)
QUEST.addStartNpc(7530)
QUEST.addStartNpc(7573)
QUEST.addStartNpc(7575)

STARTED.addTalkId(7008)
STARTED.addTalkId(7009)
STARTED.addTalkId(7017)
STARTED.addTalkId(7019)
STARTED.addTalkId(7129)
STARTED.addTalkId(7131)
STARTED.addTalkId(7370)
STARTED.addTalkId(7400)
STARTED.addTalkId(7528)
STARTED.addTalkId(7530)
STARTED.addTalkId(7573)
STARTED.addTalkId(7575)

STARTED.addKillId(5198)

STARTED.addQuestDrop(5198,BLUE_CRYSTAL,1)
STARTED.addQuestDrop(7131,BLOOD_OF_JUNDIN_ID,1)
STARTED.addQuestDrop(7530,LICENSE_OF_MINER_ID,1)
STARTED.addQuestDrop(7575,VOUCHER_OF_FLAME_ID,1)
