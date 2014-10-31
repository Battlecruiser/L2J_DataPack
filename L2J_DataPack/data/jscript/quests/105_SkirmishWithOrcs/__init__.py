# Maked by Mr. Have fun! Version 0.2
print "importing quests: 105: Skirmish With Orcs"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

KENDNELLS_ORDER1_ID = 1836
KENDNELLS_ORDER2_ID = 1837
KENDNELLS_ORDER3_ID = 1838
KENDNELLS_ORDER4_ID = 1839
KENDNELLS_ORDER5_ID = 1840
KENDNELLS_ORDER6_ID = 1841
KENDNELLS_ORDER7_ID = 1842
KENDNELLS_ORDER8_ID = 1843
KABOO_CHIEF_TORC1_ID = 1844
KABOO_CHIEF_TORC2_ID = 1845
RED_SUNSET_SWORD_ID = 981
RED_SUNSET_STAFF_ID = 754

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7218-03.htm"
      if st.getQuestItemsCount(KENDNELLS_ORDER1_ID)+st.getQuestItemsCount(KENDNELLS_ORDER2_ID)+st.getQuestItemsCount(KENDNELLS_ORDER3_ID)+st.getQuestItemsCount(KENDNELLS_ORDER4_ID) == 0 :
        n = st.getRandom(100)
        if n < 25 :
          st.giveItems(KENDNELLS_ORDER1_ID,1)
        elif n < 50 :
          st.giveItems(KENDNELLS_ORDER2_ID,1)
        elif n < 75 :
          st.giveItems(KENDNELLS_ORDER3_ID,1)
        else:
          st.giveItems(KENDNELLS_ORDER4_ID,1)
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
   if npcId == 7218 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getLevel() >= 10 and st.getPlayer().getRace().ordinal() == 1 :
          htmltext = "7218-02.htm"
          return htmltext
        elif st.getPlayer().getRace().ordinal() != 1 :
          htmltext = "7218-00.htm"
          st.exitQuest(1)
        else:
          htmltext = "7218-10.htm"
          st.exitQuest(1)
      else:
        htmltext = "7218-10.htm"
        st.exitQuest(1)
   elif npcId == 7218 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7218 and int(st.get("cond")) :
      if st.getQuestItemsCount(KABOO_CHIEF_TORC1_ID) :
        htmltext = "7218-06.htm"
        if st.getQuestItemsCount(KENDNELLS_ORDER1_ID) :
          st.takeItems(KENDNELLS_ORDER1_ID,1)
        if st.getQuestItemsCount(KENDNELLS_ORDER2_ID) :
          st.takeItems(KENDNELLS_ORDER2_ID,1)
        if st.getQuestItemsCount(KENDNELLS_ORDER3_ID) :
          st.takeItems(KENDNELLS_ORDER3_ID,1)
        if st.getQuestItemsCount(KENDNELLS_ORDER4_ID) :
          st.takeItems(KENDNELLS_ORDER4_ID,1)
        st.takeItems(KABOO_CHIEF_TORC1_ID,1)
        n = st.getRandom(100)
        if n < 25 :
          st.giveItems(KENDNELLS_ORDER5_ID,1)
        elif n < 50 :
          st.giveItems(KENDNELLS_ORDER6_ID,1)
        elif n < 75 :
          st.giveItems(KENDNELLS_ORDER7_ID,1)
        else:
          st.giveItems(KENDNELLS_ORDER8_ID,1)
      elif st.getQuestItemsCount(KENDNELLS_ORDER1_ID) or st.getQuestItemsCount(KENDNELLS_ORDER2_ID) or st.getQuestItemsCount(KENDNELLS_ORDER3_ID) or st.getQuestItemsCount(KENDNELLS_ORDER4_ID) :
        htmltext = "7218-05.htm"
      elif st.getQuestItemsCount(KABOO_CHIEF_TORC2_ID) :
        if int(st.get("id")) != 105 :
          st.set("id","105")
          htmltext = "7218-08.htm"
          if st.getQuestItemsCount(KENDNELLS_ORDER5_ID) :
            st.takeItems(KENDNELLS_ORDER5_ID,1)
          if st.getQuestItemsCount(KENDNELLS_ORDER6_ID) :
            st.takeItems(KENDNELLS_ORDER6_ID,1)
          if st.getQuestItemsCount(KENDNELLS_ORDER7_ID) :
            st.takeItems(KENDNELLS_ORDER7_ID,1)
          if st.getQuestItemsCount(KENDNELLS_ORDER8_ID) :
            st.takeItems(KENDNELLS_ORDER8_ID,1)
          st.takeItems(KABOO_CHIEF_TORC2_ID,1)
          if (st.getPlayer().getClassId().getId() == 0x00 or st.getPlayer().getClassId().getId() == 0x12 or st.getPlayer().getClassId().getId() == 0x1f or st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x01 or st.getPlayer().getClassId().getId() == 0x13 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23 or st.getPlayer().getClassId().getId() == 0x04 or st.getPlayer().getClassId().getId() == 0x20 or st.getPlayer().getClassId().getId() == 0x2c or st.getPlayer().getClassId().getId() == 0x2f or st.getPlayer().getClassId().getId() == 0x35 or st.getPlayer().getClassId().getId() == 0x36 or st.getPlayer().getClassId().getId() == 0x38 or st.getPlayer().getClassId().getId() == 0x2d) and int(st.get("onlyone")) == 0 :
            st.giveItems(RED_SUNSET_SWORD_ID,1)
          elif int(st.get("onlyone")) == 0 :
            st.giveItems(RED_SUNSET_STAFF_ID,1)
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
          st.set("cond","0")
      elif st.getQuestItemsCount(KENDNELLS_ORDER5_ID) or st.getQuestItemsCount(KENDNELLS_ORDER6_ID) or st.getQuestItemsCount(KENDNELLS_ORDER7_ID) or st.getQuestItemsCount(KENDNELLS_ORDER8_ID) :
        htmltext = "7218-07.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 5059 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER1_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC1_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC1_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5060 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER2_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC1_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC1_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5061 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER3_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC1_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC1_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5062 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER4_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC1_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC1_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5064 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER5_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC2_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC2_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5065 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER6_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC2_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC2_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5067 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER7_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC2_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC2_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5068 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     if st.getQuestItemsCount(KENDNELLS_ORDER8_ID) and st.getQuestItemsCount(KABOO_CHIEF_TORC2_ID) == 0 :
      st.giveItems(KABOO_CHIEF_TORC2_ID,1)
      st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(105,"105_SkirmishWithOrcs","Skirmish With Orcs")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7218)

STARTING.addTalkId(7218)

STARTED.addTalkId(7218)

STARTED.addKillId(5059)
STARTED.addKillId(5060)
STARTED.addKillId(5061)
STARTED.addKillId(5062)
STARTED.addKillId(5064)
STARTED.addKillId(5065)
STARTED.addKillId(5067)
STARTED.addKillId(5068)

STARTED.addQuestDrop(7218,KENDNELLS_ORDER1_ID,1)
STARTED.addQuestDrop(7218,KENDNELLS_ORDER2_ID,1)
STARTED.addQuestDrop(7218,KENDNELLS_ORDER3_ID,1)
STARTED.addQuestDrop(7218,KENDNELLS_ORDER4_ID,1)
STARTED.addQuestDrop(7218,KENDNELLS_ORDER5_ID,1)
STARTED.addQuestDrop(7218,KENDNELLS_ORDER6_ID,1)
STARTED.addQuestDrop(7218,KENDNELLS_ORDER7_ID,1)
STARTED.addQuestDrop(7218,KENDNELLS_ORDER8_ID,1)
