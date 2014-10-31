# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "407_PathToElvenScout"

REORIA_LETTER2_ID = 1207
PRIGUNS_TEAR_LETTER1_ID = 1208
PRIGUNS_TEAR_LETTER2_ID = 1209
PRIGUNS_TEAR_LETTER3_ID = 1210
PRIGUNS_TEAR_LETTER4_ID = 1211
MORETTIS_HERB_ID = 1212
MORETTIS_LETTER_ID = 1214
PRIGUNS_LETTER_ID = 1215
HONORARY_GUARD_ID = 1216
REORIA_RECOMMENDATION_ID = 1217
RUSTED_KEY_ID = 1293

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      if st.getPlayer().getClassId().getId() == 0x12 :
        if st.getPlayer().getLevel() >= 19 :
          if st.getQuestItemsCount(REORIA_RECOMMENDATION_ID)>0 :
            htmltext = "30328-04.htm"
          else:
            htmltext = "30328-05.htm"
            st.giveItems(REORIA_LETTER2_ID,1)
            st.set("cond","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
        else :
          htmltext = "30328-03.htm"
      else:
        if st.getPlayer().getClassId().getId() == 0x16 :
          htmltext = "30328-02a.htm"
        else:
          htmltext = "30328-02.htm"
    elif event == "30337_1" :
          st.takeItems(REORIA_LETTER2_ID,1)
          st.set("cond","2")
          htmltext = "30337-03.htm"
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30328 and id != STARTED : return htmltext
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30328 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          htmltext = "30328-01.htm"
          return htmltext
        else:
          htmltext = "30328-01.htm"
   elif npcId == 30328 and int(st.get("cond")) and st.getQuestItemsCount(REORIA_LETTER2_ID)>0 :
        htmltext = "30328-06.htm"
   elif npcId == 30328 and int(st.get("cond")) and st.getQuestItemsCount(REORIA_LETTER2_ID)==0 and st.getQuestItemsCount(HONORARY_GUARD_ID)==0 :
        htmltext = "30328-08.htm"
   elif npcId == 30337 and int(st.get("cond")) and st.getQuestItemsCount(REORIA_LETTER2_ID)>0 and st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID)==0 :
        htmltext = "30337-01.htm"
   elif npcId == 30337 and st.getQuestItemsCount(MORETTIS_LETTER_ID)<1 and st.getQuestItemsCount(PRIGUNS_LETTER_ID)==0 and st.getQuestItemsCount(HONORARY_GUARD_ID)==0 :
        if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID)<1 :
          htmltext = "30337-04.htm"
        elif st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID)>0 and st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID)<4 :
          htmltext = "30337-05.htm"
        else:
          htmltext = "30337-06.htm"
          st.takeItems(PRIGUNS_TEAR_LETTER1_ID,1)
          st.takeItems(PRIGUNS_TEAR_LETTER2_ID,1)
          st.takeItems(PRIGUNS_TEAR_LETTER3_ID,1)
          st.takeItems(PRIGUNS_TEAR_LETTER4_ID,1)
          st.giveItems(MORETTIS_HERB_ID,1)
          st.giveItems(MORETTIS_LETTER_ID,1)
          st.set("cond","4")
   elif npcId == 30334 and int(st.get("cond")) :
        htmltext = "30334-01.htm"
   elif npcId == 30426 and int(st.get("cond")) and st.getQuestItemsCount(MORETTIS_LETTER_ID) and st.getQuestItemsCount(MORETTIS_HERB_ID) :
        if st.getQuestItemsCount(RUSTED_KEY_ID)<1 :
          htmltext = "30426-01.htm"
          st.set("cond","5")
        else:
          htmltext = "30426-02.htm"
          st.takeItems(RUSTED_KEY_ID,1)
          st.takeItems(MORETTIS_HERB_ID,1)
          st.takeItems(MORETTIS_LETTER_ID,1)
          st.giveItems(PRIGUNS_LETTER_ID,1)
          st.set("cond","7")
   elif npcId == 30426 and int(st.get("cond")) and st.getQuestItemsCount(PRIGUNS_LETTER_ID) :
        htmltext = "30426-04.htm"
   elif npcId == 30337 and int(st.get("cond")) and st.getQuestItemsCount(PRIGUNS_LETTER_ID)>0 :
        if st.getQuestItemsCount(MORETTIS_HERB_ID) :
          htmltext = "30337-09.htm"
        else:
          htmltext = "30337-07.htm"
          st.takeItems(PRIGUNS_LETTER_ID,1)
          st.giveItems(HONORARY_GUARD_ID,1)
          st.set("cond","8")
   elif npcId == 30337 and int(st.get("cond")) and st.getQuestItemsCount(HONORARY_GUARD_ID)>0 :
        htmltext = "30337-08.htm"
   elif npcId == 30328 and int(st.get("cond")) and st.getQuestItemsCount(HONORARY_GUARD_ID)>0 :
        htmltext = "30328-07.htm"
        st.takeItems(HONORARY_GUARD_ID,1)
        st.giveItems(REORIA_RECOMMENDATION_ID,1)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
   npcId = npc.getNpcId()
   if npcId == 20053 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID) < 4 :
          if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)<1 :
            st.giveItems(PRIGUNS_TEAR_LETTER1_ID,1)
            if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
              st.set("cond","3")
            else:
              st.playSound("ItemSound.quest_itemget")
          else:
            if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)<1 :
              st.giveItems(PRIGUNS_TEAR_LETTER2_ID,1)
              if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID) == 4 :
                st.playSound("ItemSound.quest_middle")
                st.set("cond","3")
              else:
                st.playSound("ItemSound.quest_itemget")
            else:
              if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)<1 :
                st.giveItems(PRIGUNS_TEAR_LETTER3_ID,1)
                if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID) == 4 :
                  st.playSound("ItemSound.quest_middle")
                  st.set("cond","3")
                else:
                  st.playSound("ItemSound.quest_itemget")
              else:
                if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID)<1 :
                  st.giveItems(PRIGUNS_TEAR_LETTER4_ID,1)
                  if st.getQuestItemsCount(PRIGUNS_TEAR_LETTER1_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER2_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER3_ID)+st.getQuestItemsCount(PRIGUNS_TEAR_LETTER4_ID) == 4 :
                    st.playSound("ItemSound.quest_middle")
                    st.set("cond","3")
                  else:
                    st.playSound("ItemSound.quest_itemget")
   elif npcId == 27031 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(MORETTIS_HERB_ID) == 1 and st.getQuestItemsCount(MORETTIS_LETTER_ID) == 1 and st.getQuestItemsCount(RUSTED_KEY_ID) == 0 and st.getRandom(10)<6 :
        st.giveItems(RUSTED_KEY_ID,1)
        st.playSound("ItemSound.quest_middle")
        st.set("cond","6")
   return

QUEST       = Quest(407,qn,"Path To Elven Scout")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30328)

QUEST.addTalkId(30328)

QUEST.addTalkId(30334)
QUEST.addTalkId(30337)
QUEST.addTalkId(30426)


QUEST.addKillId(27031)
QUEST.addKillId(20053)

STARTED.addQuestDrop(30328,REORIA_LETTER2_ID,1)
STARTED.addQuestDrop(20053,PRIGUNS_TEAR_LETTER1_ID,1)
STARTED.addQuestDrop(20053,PRIGUNS_TEAR_LETTER2_ID,1)
STARTED.addQuestDrop(20053,PRIGUNS_TEAR_LETTER3_ID,1)
STARTED.addQuestDrop(20053,PRIGUNS_TEAR_LETTER4_ID,1)
STARTED.addQuestDrop(27031,RUSTED_KEY_ID,1)
STARTED.addQuestDrop(30337,MORETTIS_HERB_ID,1)
STARTED.addQuestDrop(30337,MORETTIS_LETTER_ID,1)
STARTED.addQuestDrop(30426,PRIGUNS_LETTER_ID,1)
STARTED.addQuestDrop(30337,HONORARY_GUARD_ID,1)

print "importing quests: 407: Path To Elven Scout"