# Maked by Mr. Have fun! Version 0.2
# Updated by ElgarL

print "importing quests: 401: Path To Warrior"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

EINS_LETTER_ID = 1138
WARRIOR_GUILD_MARK_ID = 1139
RUSTED_BRONZE_SWORD1_ID = 1140
RUSTED_BRONZE_SWORD2_ID = 1141
SIMPLONS_LETTER_ID = 1143
POISON_SPIDER_LEG2_ID = 1144
MEDALLION_OF_WARRIOR_ID = 1145
RUSTED_BRONZE_SWORD3_ID = 1142

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "401_1" :
          if st.getPlayer().getClassId().getId() == 0x00 :
            if st.getPlayer().getLevel() >= 19 :
              if st.getQuestItemsCount(MEDALLION_OF_WARRIOR_ID)>0 :
                htmltext = "7010-04.htm"
              else:
                htmltext = "7010-05.htm"
                return htmltext
            else :
              htmltext = "7010-02.htm"
          else:
            if st.getPlayer().getClassId().getId() == 0x01 :
              htmltext = "7010-02a.htm"
            else:
              htmltext = "7010-03.htm"
    elif event == "401_2" :
          htmltext = "7010-10.htm"
    elif event == "401_3" :
            htmltext = "7010-11.htm"
            st.takeItems(SIMPLONS_LETTER_ID,1)
            st.takeItems(RUSTED_BRONZE_SWORD2_ID,1)
            st.giveItems(RUSTED_BRONZE_SWORD3_ID,1)
            st.set("cond","5")
    elif event == "1" :
      st.set("id","0")
      if st.getQuestItemsCount(EINS_LETTER_ID) == 0 :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(EINS_LETTER_ID,1)
        htmltext = "7010-06.htm"
    elif event == "7253_1" :
          htmltext = "7253-02.htm"
          st.takeItems(EINS_LETTER_ID,1)
          st.giveItems(WARRIOR_GUILD_MARK_ID,1)
          st.set("cond","2")
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
   if npcId == 7010 and int(st.get("cond"))==0 :
      htmltext = "7010-01.htm"
   elif npcId == 7010 and int(st.get("cond")) and st.getQuestItemsCount(EINS_LETTER_ID)>0 :
      htmltext = "7010-07.htm"
   elif npcId == 7010 and int(st.get("cond")) and st.getQuestItemsCount(WARRIOR_GUILD_MARK_ID)==1 :
      htmltext = "7010-08.htm"
   elif npcId == 7253 and int(st.get("cond")) and st.getQuestItemsCount(EINS_LETTER_ID) :
      htmltext = "7253-01.htm"
   elif npcId == 7253 and int(st.get("cond")) and st.getQuestItemsCount(WARRIOR_GUILD_MARK_ID) :
      if st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID)<1 :
        htmltext = "7253-03.htm"
      elif st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID)<10 :
        htmltext = "7253-04.htm"
      elif st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID) >= 10 :
        st.takeItems(WARRIOR_GUILD_MARK_ID,1)
        st.takeItems(RUSTED_BRONZE_SWORD1_ID,st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID))
        st.giveItems(RUSTED_BRONZE_SWORD2_ID,1)
        st.giveItems(SIMPLONS_LETTER_ID,1)
        st.set("cond","4")
        htmltext = "7253-05.htm"
   elif npcId == 7253 and int(st.get("cond")) and st.getQuestItemsCount(SIMPLONS_LETTER_ID) :
        htmltext = "7253-06.htm"
   elif npcId == 7010 and int(st.get("cond")) and st.getQuestItemsCount(SIMPLONS_LETTER_ID) and st.getQuestItemsCount(RUSTED_BRONZE_SWORD2_ID) and st.getQuestItemsCount(WARRIOR_GUILD_MARK_ID)==0 and st.getQuestItemsCount(EINS_LETTER_ID)==0 :
        htmltext = "7010-09.htm"
   elif npcId == 7010 and int(st.get("cond")) and st.getQuestItemsCount(RUSTED_BRONZE_SWORD3_ID) and st.getQuestItemsCount(WARRIOR_GUILD_MARK_ID)==0 and st.getQuestItemsCount(EINS_LETTER_ID)==0 :
        if st.getQuestItemsCount(POISON_SPIDER_LEG2_ID)<20 :
          htmltext = "7010-12.htm"
        elif st.getQuestItemsCount(POISON_SPIDER_LEG2_ID)>19 :
          st.takeItems(POISON_SPIDER_LEG2_ID,st.getQuestItemsCount(POISON_SPIDER_LEG2_ID))
          st.takeItems(RUSTED_BRONZE_SWORD3_ID,1)
          st.giveItems(MEDALLION_OF_WARRIOR_ID,1)
          htmltext = "7010-13.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 35 or npcId == 42 :
        st.set("id","0")
        if int(st.get("cond")) == 2 and st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID)<10 :
          if st.getRandom(10)<4 :
            st.giveItems(RUSTED_BRONZE_SWORD1_ID,1)
            if st.getQuestItemsCount(RUSTED_BRONZE_SWORD1_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
              st.set("cond","3")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 43 or npcId == 38 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(POISON_SPIDER_LEG2_ID)<20 and st.getQuestItemsCount(RUSTED_BRONZE_SWORD3_ID) == 1 and st.getItemEquipped(7) == RUSTED_BRONZE_SWORD3_ID:
        st.giveItems(POISON_SPIDER_LEG2_ID,1)
        if st.getQuestItemsCount(POISON_SPIDER_LEG2_ID) == 20 :
          st.playSound("ItemSound.quest_middle")
          st.set("cond","6")
        else:
          st.playSound("ItemSound.quest_itemget")

   return

QUEST       = Quest(401,"401_PathToWarrior","Path To Warrior")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7010)

STARTING.addTalkId(7010)

STARTED.addTalkId(7010)
STARTED.addTalkId(7253)

STARTED.addKillId(35)
STARTED.addKillId(38)
STARTED.addKillId(42)
STARTED.addKillId(43)

STARTED.addQuestDrop(7253,SIMPLONS_LETTER_ID,1)
STARTED.addQuestDrop(7253,RUSTED_BRONZE_SWORD2_ID,1)
STARTED.addQuestDrop(7010,EINS_LETTER_ID,1)
STARTED.addQuestDrop(7253,WARRIOR_GUILD_MARK_ID,1)
STARTED.addQuestDrop(35,RUSTED_BRONZE_SWORD1_ID,1)
STARTED.addQuestDrop(42,RUSTED_BRONZE_SWORD1_ID,1)
STARTED.addQuestDrop(43,POISON_SPIDER_LEG2_ID,1)
STARTED.addQuestDrop(38,POISON_SPIDER_LEG2_ID,1)
STARTED.addQuestDrop(7010,RUSTED_BRONZE_SWORD3_ID,1)
