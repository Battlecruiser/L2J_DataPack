# This script is part of the L2J Official Datapack Project
# visit us at http://www.l2jdp.com/
# Created by DraX on 08.08.2005
# Updated by DrLecter on 20.06.2007

import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30070_sylvain_occupation_change"

MARK_OF_FAITH    = 1201
ETERNITY_DIAMOND = 1230
LEAF_OF_ORACLE   = 1235
BEAD_OF_SEASON   = 1292
HIGH_PRIEST_SYLVAIN = 30070

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   htmltext = "No Quest"
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()
   if event in ["30070-01.htm","30070-02.htm","30070-03.htm","30070-04.htm","30070-05.htm","30070-06.htm","30070-07.htm",\
                "30070-08.htm","30070-09.htm","30070-10.htm","30070-11.htm","30070-12.htm","30070-13.htm","30070-14.htm"] :
     return event
   elif ClassId == ClassId.elvenMage:
     if event == "class_change_26" :
        item = st.getQuestItemsCount(ETERNITY_DIAMOND)
        if Level <= 19 and not item :
          htmltext = "30070-15.htm"
        elif Level <= 19 and item :
          htmltext = "30070-16.htm"
        elif Level >= 20 and not item :
          htmltext = "30070-17.htm"
        elif Level >= 20 and item :
          st.takeItems(ETERNITY_DIAMOND,1)
          st.getPlayer().setClassId(26)
          st.getPlayer().setBaseClass(26)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-18.htm"
     elif event == "class_change_29":
        item = st.getQuestItemsCount(LEAF_OF_ORACLE)
        if Level <= 19 and not item :
          htmltext = "30070-19.htm"
        elif Level <= 19 and item :
          htmltext = "30070-20.htm"
        elif Level >= 20 and not item :
          htmltext = "30070-21.htm"
        elif Level >= 20 and item :
          st.takeItems(LEAF_OF_ORACLE,1)
          st.getPlayer().setClassId(29)
          st.getPlayer().setBaseClass(29)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-22.htm"
   elif ClassId == ClassId.mage :
     if event == "class_change_11" :
        item = st.getQuestItemsCount(BEAD_OF_SEASON)
        if Level <= 19 and not item :
          htmltext = "30070-23.htm"
        elif Level <= 19 and item :
          htmltext = "30070-24.htm"
        elif Level >= 20 and not item :
          htmltext = "30070-25.htm"
        elif Level >= 20 and item :
          st.takeItems(BEAD_OF_SEASON,1)
          st.getPlayer().setClassId(11)
          st.getPlayer().setBaseClass(11)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-26.htm"
     elif event == "class_change_15":
        item = st.getQuestItemsCount(MARK_OF_FAITH)
        if Level <= 19 and not item :
          htmltext = "30070-27.htm"
        elif Level <= 19 and item :
          htmltext = "30070-28.htm"
        elif Level >= 20 and not item :
          htmltext = "30070-29.htm"
        elif Level >= 20 and item :
          st.takeItems(MARK_OF_FAITH,1)
          st.getPlayer().setClassId(15)
          st.getPlayer().setBaseClass(15)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-30.htm"
   st.exitQuest(1)
   return htmltext


 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   # Elves and Humans are accepted
   if npcId == HIGH_PRIEST_SYLVAIN and Race in [Race.elf, Race.human]:
     if not ClassId.isMage() :      # all elf/human fighters from all occupation levels
       htmltext = "30070-33.htm"
     elif ClassId.level() == 1 :    # first occupation elf/human mages
       htmltext = "30070-31.htm"
     elif ClassId.level() == 2 :    # second occupation elf/human mages
       htmltext = "30070-32.htm"
     elif Race == Race.elf :        # elven mystic
       st.setState(STARTED)
       return "30070-01.htm"
     elif Race == Race.human :      # human mystic
       st.setState(STARTED)
       return "30070-08.htm"
   else:
     htmltext = "30070-33.htm"    # non-elf, non-human races
   st.exitQuest(1)
   return htmltext

QUEST     = Quest(30070,qn,"village_master")
CREATED   = State('Start',       QUEST)
STARTED   = State('Started',     QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(HIGH_PRIEST_SYLVAIN)
QUEST.addTalkId(HIGH_PRIEST_SYLVAIN)
