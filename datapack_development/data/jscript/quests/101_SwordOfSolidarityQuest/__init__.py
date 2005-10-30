# Maked by Mr. Have fun! Version 0.2
print "importing quests: 101: Sword Of Solidarity Quest"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ROIENS_LETTER_ID = 796
HOWTOGO_RUINS_ID = 937
BROKEN_SWORD_HANDLE_ID = 739
BROKEN_BLADE_BOTTOM_ID = 740
BROKEN_BLADE_TOP_ID = 741
ALLTRANS_NOTE_ID = 742
SWORD_OF_SOLIDARITY_ID = 738

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7008-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(ROIENS_LETTER_ID,1)
    elif event == "101_1" :
          htmltext = "7283-02.htm"
          st.takeItems(ROIENS_LETTER_ID,st.getQuestItemsCount(ROIENS_LETTER_ID))
          st.giveItems(HOWTOGO_RUINS_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7008 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getRace().ordinal() != 0 :
            htmltext = "7008-00.htm"
          elif st.getPlayer().getLevel() >= 9 :
            htmltext = "7008-02.htm"
            return htmltext
          else:
            htmltext = "7008-08.htm"
            st.exitQuest(1)
        else:
          htmltext = "7008-08.htm"
          st.exitQuest(1)
   elif npcId == 7008 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7008 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ROIENS_LETTER_ID)==1) and int(st.get("onlyone"))==0 :
        htmltext = "7008-05.htm"
   elif npcId == 7008 and int(st.get("cond"))==1 and int(st.get("onlyone"))==0 and st.getQuestItemsCount(ROIENS_LETTER_ID)==0 and st.getQuestItemsCount(ALLTRANS_NOTE_ID)==0 :
        if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) and st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) :
          htmltext = "7008-12.htm"
        if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) and st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) == 0 :
          htmltext = "7008-11.htm"
        if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) == 0 and st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) :
          htmltext = "7008-11.htm"
        if st.getQuestItemsCount(BROKEN_SWORD_HANDLE_ID)>0 :
          htmltext = "7008-07.htm"
        if st.getQuestItemsCount(HOWTOGO_RUINS_ID) == 1 and int(st.get("onlyone")) == 0 :
          htmltext = "7008-10.htm"
   elif npcId == 7008 and int(st.get("cond"))==1 and int(st.get("onlyone"))==0 and st.getQuestItemsCount(ROIENS_LETTER_ID)==0 and st.getQuestItemsCount(ALLTRANS_NOTE_ID) :
        if st.getQuestItemsCount(ALLTRANS_NOTE_ID) :
          if int(st.get("id")) != 101 :
            st.set("id","101")
            htmltext = "7008-06.htm"
            st.takeItems(ALLTRANS_NOTE_ID,st.getQuestItemsCount(ALLTRANS_NOTE_ID))
            st.giveItems(BROKEN_SWORD_HANDLE_ID,1)
   elif npcId == 7283 and int(st.get("cond"))==1 and st.getQuestItemsCount(ROIENS_LETTER_ID)>0 :
        htmltext = "7283-01.htm"
   elif npcId == 7283 and int(st.get("cond"))==1 and st.getQuestItemsCount(ROIENS_LETTER_ID)==0 and st.getQuestItemsCount(HOWTOGO_RUINS_ID)>0 :
        if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) and st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) == 0 :
          htmltext = "7283-08.htm"
        if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) == 0 and st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) :
          htmltext = "7283-08.htm"
        if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) == 0 and st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) == 0 :
          htmltext = "7283-03.htm"
        if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) and st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) :
          htmltext = "7283-04.htm"
          st.takeItems(HOWTOGO_RUINS_ID,st.getQuestItemsCount(HOWTOGO_RUINS_ID))
          st.takeItems(BROKEN_BLADE_TOP_ID,st.getQuestItemsCount(BROKEN_BLADE_TOP_ID))
          st.takeItems(BROKEN_BLADE_BOTTOM_ID,st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID))
          st.giveItems(ALLTRANS_NOTE_ID,1)
   elif npcId == 7283 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALLTRANS_NOTE_ID) and st.getQuestItemsCount(HOWTOGO_RUINS_ID)==0 :
        htmltext = "7283-05.htm"
   elif npcId == 7283 and int(st.get("cond"))==1 and st.getQuestItemsCount(BROKEN_SWORD_HANDLE_ID)>0 and int(st.get("onlyone"))==0 :
        st.takeItems(BROKEN_SWORD_HANDLE_ID,st.getQuestItemsCount(BROKEN_SWORD_HANDLE_ID))
        st.giveItems(SWORD_OF_SOLIDARITY_ID,1)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        htmltext = "7283-06.htm"
        st.set("onlyone","1")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 362 :
        st.set("id","0")
        if st.getQuestItemsCount(HOWTOGO_RUINS_ID)>0 :
          if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) == 0 :
            if st.getRandom(5) == 0 :
              st.giveItems(BROKEN_BLADE_TOP_ID,1)
              if st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) == 1 :
                st.playSound("ItemSound.quest_middle")
              else:
                st.playSound("ItemSound.quest_itemget")
          else:
            if st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) == 0 :
              if st.getRandom(5) == 0 :
                st.giveItems(BROKEN_BLADE_BOTTOM_ID,1)
                if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) == 1 :
                  st.playSound("ItemSound.quest_middle")
                else:
                  st.playSound("ItemSound.quest_itemget")
   elif npcId == 361 :
        st.set("id","0")
        if st.getQuestItemsCount(HOWTOGO_RUINS_ID)>0 :
          if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) == 0 :
            if st.getRandom(5) == 0 :
              st.giveItems(BROKEN_BLADE_TOP_ID,1)
              if st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) == 1 :
                st.playSound("ItemSound.quest_middle")
              else:
                st.playSound("ItemSound.quest_itemget")
          else:
            if st.getQuestItemsCount(BROKEN_BLADE_BOTTOM_ID) == 0 :
              if st.getRandom(5) == 0 :
                st.giveItems(BROKEN_BLADE_BOTTOM_ID,1)
                if st.getQuestItemsCount(BROKEN_BLADE_TOP_ID) == 1 :
                  st.playSound("ItemSound.quest_middle")
                else:
                  st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(101,"101_SwordOfSolidarityQuest","Sword Of Solidarity Quest")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7008)

STARTING.addTalkId(7008)

STARTED.addTalkId(7008)
STARTED.addTalkId(7283)

STARTED.addKillId(361)
STARTED.addKillId(362)

STARTED.addQuestDrop(7283,ALLTRANS_NOTE_ID,1)
STARTED.addQuestDrop(7283,HOWTOGO_RUINS_ID,1)
STARTED.addQuestDrop(362,BROKEN_BLADE_TOP_ID,1)
STARTED.addQuestDrop(361,BROKEN_BLADE_TOP_ID,1)
STARTED.addQuestDrop(362,BROKEN_BLADE_BOTTOM_ID,1)
STARTED.addQuestDrop(361,BROKEN_BLADE_BOTTOM_ID,1)
STARTED.addQuestDrop(7008,ROIENS_LETTER_ID,1)
STARTED.addQuestDrop(7008,BROKEN_SWORD_HANDLE_ID,1)
